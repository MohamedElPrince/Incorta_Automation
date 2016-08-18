import sys, os, subprocess, time, zipfile, logging
import os.path
from sys import argv
from shutil import copyfile
import errno

import Auto_Module.export
import Auto_Module.test_suite_export_wd
import Auto_Module.file_tools
import Auto_Module.test_suite_import
from Auto_Module import *
import Auto_Module.validation
import Auto_Module.data_upload
import Auto_Module.json_validation
import Auto_Module.output

import Auto_Module.ldap_utilities


start_time = time.time()


"""
Arguments
-------------------------------------------
-d: import datasource/schema/dashboard
-l: load data
-x: extract files
-------------------------------------------
"""
Debug = False  # Debug flag for print statements

"""
------------------------------------------Dependency Check----------------------------------------
"""
preCheckScriptPAth = os.getcwd() + os.sep + 'BashScripts' + os.sep + 'automationCheck.sh'
if os.path.isfile(preCheckScriptPAth):
    try:
        os.system(preCheckScriptPAth)
    except Exception, e:
        raise Exception ("Pre-Check Script Did Not Run")
"""
------------------------------------------Initialization----------------------------------------
"""

# sys.argv[1] is the config file
config_file = sys.argv[1]

# Includes rest of arguments passed by user
commands = sys.argv[2:]

config_defaults = {'incorta_home': '/home/Incorta', 'tenant_home': '/home/tenants',
                   'username': 'Super', 'password': 'none', 'load_users': 'No', 'test_suite': 'CSV_DataSource',
                   'skip_validation': 'False', 'import_object': 'False', 'data_load': 'False',
                   'extract_csv': 'False', 'wd_path': '~/IncortaTesting/tmp/work', 'tenant': 'Demo',
                   'url': 'http://localhost:8080/incorta', 'ldap.base.provider.url': 'ldap://dev01.incorta.com:389',
                   'ldap.base.dn': 'dc=dev01,dc=incorta,dc=com', 'ldap.user.mapping.login': 'uid',
                   'ldap.group.mapping.member': 'member', 'ldap.group.search.filter': '(objectClass=groupOfNames)'}

# config_defaults will hold all of the keys from the above dictionary
# The values of the keys in config_defaults will be parsed from the config file

"""
------------------------------------------Initialization----------------------------------------
"""

"""
#################################################### Functions ####################################################
"""

ldap_url = config_defaults['ldap.base.provider.url']
ldap_base =  config_defaults['ldap.base.dn']
ldap_user_mapping_login = config_defaults['ldap.user.mapping.login']
ldap_group_mapping_member = config_defaults['ldap.group.mapping.member']
ldap_group_search_filter = config_defaults['ldap.group.search.filter']

def set_block_defaults(commands):
    # If no arguments come after the config file, import, data load, and extract file keys will all be set to True
    if len(sys.argv) == 2:
        config_defaults['data_load'] = True
        config_defaults['extract_csv'] = True
        config_defaults['import_object'] = True
    else:
        for command in commands:
            if command == '-d':
                config_defaults['import_object'] = True
            if command == '-l':
                config_defaults['data_load'] = True
            if command == '-x':
                config_defaults['extract_csv'] = True

def set_new_defaults(config_file):
    f = open(config_file, "r")
    lines = f.readlines()
    f.close()
    for line in lines:
        for key, value in config_defaults.items():
            if line[0] != '#' or " ":
                str = line
                str_tup = str.split("=", 1)
                if key in str_tup[0]:
                    var = key + '='
                    what_is_after_var = line[len(var):]
                    what_is_after_key = line[len(key):]
                    # if there is nothing after an = of a key in config file, the default value of the key will be assigned
                    # Ex: admin=________ -> admin=Super
                    if len(what_is_after_var) == 1:
                        config_defaults[key] = value.rstrip()
                    # if there is nothing after the name of a key in config file, the default value of the key will be assigned
                    # Ex: admin____ -> admin=Super
                    elif len(what_is_after_key) == 1:
                        config_defaults[key] = value.rstrip()
                    else:
                        config_defaults[key] = what_is_after_var.rstrip()
    # If a key from config_defaults is missing in the config file, the code below will create the key
    # in config_defaults and will assign that key its default value
    new_key_list = []
    old_key_list = []
    for new_key in config_defaults.keys():
        new_key_list.append(new_key)
    for old_key in config_defaults.keys():
        old_key_list.append(old_key)
    for key in old_key_list:
        if key not in new_key_list:
            config_defaults[key] = config_defaults[key]
    # If a custom working directory path is specified, /IncortaTesting/tmp/work will
    # be added to the end of the custom working directory path
    if config_defaults['wd_path'] == '/IncortaTesting':
        pass
    else:
        timestamp = ''
        config_defaults['wd_path'] += '/IncortaTesting'
        orig_wd_path = config_defaults['wd_path']
        add_time_stamp_to_wd(timestamp)
        return orig_wd_path

def add_time_stamp_to_wd(timestamp):
    date_and_time = str(time.strftime("%m:%d:%Y-%H:%M:%S"))
    config_defaults['wd_path'] += '/%s' % date_and_time

def incorta_api_import(incorta_home):
    incorta_module = incorta_home.rstrip() + os.sep + "bin".rstrip()
    sys.path.append(incorta_module)
    import incorta
    global incorta

def login(url, tenant, username, password):
    try:
        return incorta.login(url, tenant, username, password, True)
    except Exception, e:
        print "Login Failed"
        logging.critical("Login Failed")
        exit(1)

def logout(session):
    try:
        incorta.logout(session)
    except Exception, e:
        print 'Failed to logout'
        logging.critical('Failed to logout')
        exit(1)

def get_test_suite_path(test_suite):
    test_suite_path = os.getcwd() + '/' + "TestSuites"
    test_suite_path = test_suite_path + '/' + test_suite
    return test_suite_path

def grant_user_access(session, user_name, entity_type, entity_name, permission):
    try:
        incorta.grant_user_access(session, user_name, entity_type, entity_name, permission)
        print "Access to ", entity_type, " ", entity_name, " given to ", user_name
    except Exception, e:
        print "Failed to grant user access to", user_name
        return

"""
#################################################### Functions ####################################################
"""

test_suite_name_list = []
test_case_name_list = []
output_dict = {}

test_suite_name_dict = {}
loader_valid_dict = {}
metadata_suite_dict = {}


set_block_defaults(commands)
orig_wd_path = set_new_defaults(config_file)

if Debug == True:
    for key, value in config_defaults.items():
        print(key, value)

# converts keys in a dictionary to variables
locals().update(config_defaults)

incorta_api_import(incorta_home)  # Import Incorta API
session = login(url, tenant, username, password)  # Login to Incorta
session_id = session[21:53]
csrf_token = session[63:95]
test_suite_directory_path = os.getcwd() + '/' + "TestSuites"
test_suite_directories = Auto_Module.file_tools.get_subdirectories(test_suite_directory_path)

# Creates Output Directory
output_wd_path = Auto_Module.output.create_output_folder(wd_path)

# LOAD USERS FROM LDAP
print "Checking if instance needs to load users"
owd = os.getcwd()
sync = orig_wd_path + os.sep + 'sync.txt'

if os.path.isfile(sync):
    print "Users already Loaded"
else:
    print "Preparing to populate users from LDAP"
    Auto_Module.ldap_utilities.ldap_property_setup(incorta_home, ldap_url, ldap_base, ldap_user_mapping_login, ldap_group_mapping_member, ldap_group_search_filter)
    Auto_Module.ldap_utilities.dirExport(incorta_home)
    Auto_Module.ldap_utilities.tmt_ldap_property_setup(incorta_home, ldap_url, ldap_user_mapping_login, ldap_base)
    Auto_Module.ldap_utilities.sync_directory_setup(incorta_home, tenant, username, password, url)
    Auto_Module.ldap_utilities.sync_directory(incorta_home, orig_wd_path)

    Auto_Module.ldap_utilities.tenant_updater(incorta_home, tenant)
    Auto_Module.ldap_utilities.restart_incorta(incorta_home)
    Auto_Module.ldap_utilities.assign_roles_to_groups(incorta, session)

    dirExport_path = incorta_home + os.sep + 'dirExport'
    directory_zip_path = dirExport_path + os.sep + 'directory.zip'
    Auto_Module.file_tools.unzip(directory_zip_path)
    Auto_Module.file_tools.move_file(owd+os.sep+'users.csv', dirExport_path)
    Auto_Module.file_tools.move_file(owd + os.sep + 'user-groups.csv', dirExport_path)
    Auto_Module.file_tools.move_file(owd + os.sep + 'groups.csv', dirExport_path)

user_dict = {}
user_dict = Auto_Module.ldap_utilities.read_users_from_csv(incorta_home)
user_list = user_dict.keys()
print "\n USER LIST: ", user_list

for sub_dir in test_suite_directories:

    loaded_schemas = []
    test_case_name_dict = {}
    meta_data_case_dict = {}

    print "Current Test Suite: ", sub_dir
    #logging.info("Current Test Suite: " + sub_dir)
    test_suite_wd_path = Auto_Module.file_tools.create_directory(wd_path, sub_dir)  # Working Directory test suite path
    test_suite_path = get_test_suite_path(sub_dir)  # Path of test suite
    test_cases_dir = Auto_Module.file_tools.get_subdirectories(test_suite_path)
    temp_path = Auto_Module.file_tools.get_path(test_suite_path, test_cases_dir[0])
    temp_dir = Auto_Module.file_tools.get_subdirectories(temp_path)
    # Creating Output Structure
    test_suite_output_path = Auto_Module.output.create_test_suite_output_folder(output_wd_path, sub_dir)
    # Validation Sub Directories
    Data_Validation_Path, Loader_Validation_Path, XML_MetaData_Validation_Path = Auto_Module.output.create_test_suite_validation_folders(test_suite_output_path)

    for names in temp_dir:
        if 'datafiles' == names:
            print "-----------------------------------------------------------------------------"
            print "ENTERING DATA FILES"
            #logging.info("-----------------------------------------------------------------------------")
            #logging.info("ENTERING DATA FILES")
            test_suite_subdirectories = Auto_Module.file_tools.get_subdirectories(test_suite_path)

            if Debug == True:
                print test_suite_subdirectories
                #logging.debug(test_suite_subdirectories)

            current_test_suite = sub_dir

            full_schema_export_list = []
            # ENTERING TEST CASES
            for dir in test_suite_subdirectories:  # For loop for each test case inside test suite
                print "Current Test Case: ", dir
                #logging.info("Current Test Case: " + dir)
                # Get path of test_case in test_suite
                test_case_path = Auto_Module.file_tools.get_path(test_suite_path, dir)
                if Debug == True:
                    print test_case_path
                    #logging.debug(test_case_path)
                # Get subdirectories of test case
                test_case_subdirectories = Auto_Module.file_tools.get_subdirectories(test_case_path)
                if Debug == True:
                    print test_case_subdirectories
                    #logging.debug(test_case_subdirectories)
                # Creates test_suite folder in WD
                test_case_path_wd = Auto_Module.file_tools.create_directory(test_suite_wd_path, dir)
                if Debug == True:
                    print test_case_path_wd
                    #logging.debug(test_case_path_wd)
                # Creates Import and Export Folders in WD test case folder
                Auto_Module.test_suite_export_wd.create_standard_directory(test_case_path_wd)
                # Extracts test suite to WD
                Auto_Module.test_suite_export_wd.extract_test_case(test_case_path, test_case_path_wd)
                # Import Datafiles to Incorta
                Auto_Module.test_suite_import.import_datafiles(incorta, session, test_case_path)
                # Import Schema to Incorta
                Auto_Module.test_suite_import.import_schema(incorta, session, test_case_path)
                # Import Dashboards to Incorta
                Auto_Module.test_suite_import.import_dashboard(incorta, session, test_case_path)
                #Defining Import / Export Paths
                import_path, export_path = Auto_Module.validation.grab_import_export_path(test_case_path_wd)

                # Define Import DATA STRUCTURES

                import_dash_ids = {}
                import_dash_tenants = {}
                import_dashboard_names_list = []
                import_schema_names = {}
                import_schema_loaders = {}
                import_schema_tenants = {}
                import_schema_names_list = []
                # Grab Imported Information
                import_dash_ids, import_dash_tenants, import_dashboard_names_list = Auto_Module.validation.get_dashboards_info(
                    import_path)
                import_schema_names, import_schema_loaders, import_schema_tenants, import_schema_names_list = Auto_Module.validation.get_schemas_info(
                    import_path)

                # TENANT EDITOR
                Auto_Module.validation.tenant_editor(import_path)

                # EXPORTS
                test_case_wd_subdirectories = Auto_Module.file_tools.get_subdirectories(test_case_path_wd)
                for test_case_wd_dirs in test_case_wd_subdirectories:
                    if 'Export_Files' in test_case_wd_dirs:
                        test_case_export_path_wd = Auto_Module.file_tools.get_path(test_case_path_wd, test_case_wd_dirs)
                        export_zips_path = Auto_Module.export.create_temp_directory(test_case_export_path_wd)
                        test_case_wd_dashboard_path = Auto_Module.file_tools.create_directory(test_case_export_path_wd,
                                                                                              'dashboards')
                        test_case_wd_schema_path = Auto_Module.file_tools.create_directory(test_case_export_path_wd,
                                                                                           'schemas')
                        custom_export_dashboard_names_list = Auto_Module.export.export_dashboards(incorta, session,
                                                                                                  export_zips_path,
                                                                                                  import_dashboard_names_list)
                        Auto_Module.export.export_zip(export_zips_path, test_case_wd_dashboard_path,
                                                      custom_export_dashboard_names_list)
                        custom_export_schema_names_list = Auto_Module.export.export_schemas(incorta, session,
                                                                                            export_zips_path,
                                                                                            import_schema_names_list)
                        Auto_Module.export.export_zip(export_zips_path, test_case_wd_schema_path,
                                                      custom_export_schema_names_list)

                # EXPORT DATA STRUCTURES
                export_dash_ids = {}
                export_dash_tenants = {}
                export_dashboard_names_list = []
                export_schema_names = {}
                export_schema_loaders = {}
                export_schema_tenants = {}
                export_schema_names_list = []
                #Obtain Exported Information
                export_dash_ids, export_dash_tenants, export_dashboard_names_list = Auto_Module.validation.get_dashboards_info(
                    export_path)
                export_schema_names, export_schema_loaders, export_schema_tenants, export_schema_names_list = Auto_Module.validation.get_schemas_info(
                    export_path)

                # TENANT EDITOR
                Auto_Module.validation.tenant_editor(export_path)

                # META DATA VALIDATION IMPLEMENTATION
                if config_defaults['skip_validation'] == 'False':
                    # Comparing Dashboard Items
                    Auto_Module.validation.validation(sub_dir, import_dash_ids, export_dash_ids, XML_MetaData_Validation_Path, 'dashboards')
                    Auto_Module.validation.validation(sub_dir, import_dash_tenants, export_dash_tenants, XML_MetaData_Validation_Path, 'dashboard_tenants')
                    # Comparing Schema Items
                    Auto_Module.validation.validation(sub_dir, import_schema_names, export_schema_names, XML_MetaData_Validation_Path, 'schemas')
                    Auto_Module.validation.validation(sub_dir, import_schema_loaders, export_schema_loaders, XML_MetaData_Validation_Path, 'schema_loaders')
                    Auto_Module.validation.validation(sub_dir, import_schema_tenants, export_schema_tenants, XML_MetaData_Validation_Path, 'schema_tenants')

                # Load Data
                table = None
                incremental = False
                snapshot = False
                staging = False
                Auto_Module.data_upload.Load_data(incorta, session, export_schema_names_list)

                # LOADER VALIDATION
                # Appends to list of loaded schemas as for loop goes through every test case
                full_schema_export_list.extend(export_schema_names_list)
                schema_list = Auto_Module.data_upload.load_validator(incorta_home, export_schema_names_list,
                                                                     full_schema_export_list)
                Auto_Module.data_upload.loaded_validator(schema_list, export_schema_names_list, Loader_Validation_Path)
                loaded_schemas = full_schema_export_list
                # Exported Dashboard ID's per test case
                test_case_dashboard_export_list = export_dash_ids.keys()
                # GRANT PERMISSIONS
                print "Preparing to Export: ", export_dashboard_names_list
                for user in user_list:
                    for dashboard_name in export_dashboard_names_list:
                        #print "Granting permission to access dashboard ", dashboard_name
                        grant_user_access(session, user, 'dashboard', os.sep + dashboard_name, 'edit')

                # LOG OUT SUPER USER
                time.sleep(2)
                try:
                    logout(session)
                    print "Logged out Super User successfully"
                except Exception, e:
                    print "unable to logout"
                time.sleep(2)

                # JSON DASHBOARD EXPORT
                if Debug == True:
                    print "session: ", session, " \n\n"
                    #logging.debug("session: " + session + " \n\n")
                    print "session id: ", session_id
                    #logging.debug("session id: " + session_id)
                    print "dashboard id: ", test_case_dashboard_export_list
                    #logging.debug("dashboard id:" + " ".join(test_case_dashboard_export_list))
                    print "CSRF TOKEN", csrf_token
                    #logging.debug("CSRF TOKEN" + csrf_token)
                    print "Test Case Path", test_case_path_wd
                    #logging.debug("Test Case Path" + test_case_path_wd)
                    print "Entering JSON DASH EXPORT"
                    #logging.debug("Entering JSON DASH EXPORT")

                user_pass = 'superpass'
                print "TESTING USER LOGIN"
                for user in user_list:
                    session = login(url, tenant, user, user_pass)
                    time.sleep(2)
                    print "Logged in user.. ", user
                    session_id = session[21:53]
                    csrf_token = session[63:95]
                    Auto_Module.export.export_dashboards_json(session_id, test_case_dashboard_export_list, csrf_token,
                                                             test_case_path_wd, test_case_path, user)
                    logout(session)
                    time.sleep(2)
                    print "Logged out user.. ", user

                #LOGGING IN SUPER USER
                try:
                    time.sleep(2)
                    session = login(url, tenant, username, password)
                    time.sleep(2)
                    print "Logged in Super User"
                except Exception, e:
                    print "Unable to log in Super User"
                if Debug == False:
                    print "\nFinished JSON DASH EXPORT"
                    #logging.debug("\nFinished JSON DASH EXPORT")

                # DATA VALIDATION
                if config_defaults['skip_validation'] == 'False':
                    for user in user_list:
                        output_test_case_path = Data_Validation_Path + os.sep + dir
                        output_user_path = Auto_Module.output.create_output_user_path(output_test_case_path, user)
                        Auto_Module.json_validation.validation(test_case_path, test_case_path_wd, output_wd_path, current_test_suite, output_user_path, user)
                        # Removes user folders not being tested within the current test case
                        if os.listdir(output_user_path) == []:
                            os.rmdir(output_user_path)
                print "SEARCHING FOR SUCS AND DIFFS"
                test_case_name_dict[dir] = Auto_Module.output.data_validation_generate_suc_dif_file_names(Data_Validation_Path, dir)

                meta_data_case_dict['dashboard_tenants'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(XML_MetaData_Validation_Path, 'dashboard_tenants')
                meta_data_case_dict['dashboards'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(XML_MetaData_Validation_Path, 'dashboards')
                meta_data_case_dict['schema_loaders'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(XML_MetaData_Validation_Path, 'schema_loaders')
                meta_data_case_dict['schema_tenants'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(XML_MetaData_Validation_Path, 'schema_tenants')
                meta_data_case_dict['schemas'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(XML_MetaData_Validation_Path, 'schemas')

            # Compares Loaded Schema List to Exported Schema List
            Auto_Module.data_upload.schema_load_validatior(schema_list, full_schema_export_list, Loader_Validation_Path)
            loader_valid_dict[sub_dir] = Auto_Module.output.loader_validation_generate_suc_dif_file_names(Loader_Validation_Path)
            test_suite_name_dict[sub_dir] = test_case_name_dict
            metadata_suite_dict[sub_dir] = meta_data_case_dict
    #TEST SUITE
                #TEST CASE

# XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

        if 'datasources' == names:
            print "-----------------------------------------------------------------------------"
            #logging.info("-----------------------------------------------------------------------------")
            print "ENTERING DATA SOURCES"
            #logging.info("ENTERING DATA SOURCES")
            test_suite_subdirectories = Auto_Module.file_tools.get_subdirectories(test_suite_path)

            if Debug == True:
                print test_suite_subdirectories
                #logging.debug(test_suite_subdirectories)
            current_test_suite = sub_dir

            full_schema_export_list = []
            # ENTERING TEST CASES
            for dir in test_suite_subdirectories:  # For loop for each test case inside test suite
                print "Current Test Case: ", dir
                #logging.info("Current Test Case: " + dir)
                # Get path of test_case in test_suite
                test_case_path = Auto_Module.file_tools.get_path(test_suite_path, dir)
                if Debug == True:
                    print test_case_path
                    #logging.debug(test_case_path)
                # Get subdirectories of test case
                test_case_subdirectories = Auto_Module.file_tools.get_subdirectories(test_case_path)
                if Debug == True:
                    print test_case_subdirectories
                    #logging.debug(test_case_subdirectories)
                # Creates test_suite folder in WD
                test_case_path_wd = Auto_Module.file_tools.create_directory(test_suite_wd_path, dir)
                if Debug == True:
                    print test_case_path_wd
                    #logging.debug(test_case_path_wd)
                # Creates Import and Export Folders in WD test case folder
                Auto_Module.test_suite_export_wd.create_standard_directory(test_case_path_wd)
                # Extracts test suite to WD
                Auto_Module.test_suite_export_wd.extract_test_case(test_case_path, test_case_path_wd)
                # Import DataSources to Incorta
                Auto_Module.test_suite_import.import_datasources(incorta, session, test_case_path)
                # Import Schema to Incorta
                Auto_Module.test_suite_import.import_schema(incorta, session, test_case_path)
                # Import Dashboards to Incorta
                Auto_Module.test_suite_import.import_dashboard(incorta, session, test_case_path)
                # Defining Import / Export Paths
                import_path, export_path = Auto_Module.validation.grab_import_export_path(test_case_path_wd)

                # Define IMPORT DATA STRUCTURES
                import_dash_ids = {}
                import_dash_tenants = {}
                import_dashboard_names_list = []
                import_schema_names = {}
                import_schema_loaders = {}
                import_schema_tenants = {}
                import_schema_names_list = []

                import_dash_ids, import_dash_tenants, import_dashboard_names_list = Auto_Module.validation.get_dashboards_info(
                    import_path)
                import_schema_names, import_schema_loaders, import_schema_tenants, import_schema_names_list = Auto_Module.validation.get_schemas_info(
                    import_path)

                # TENANT EDITOR
                Auto_Module.validation.tenant_editor(import_path)

                # EXPORTS
                test_case_wd_subdirectories = Auto_Module.file_tools.get_subdirectories(test_case_path_wd)
                for test_case_wd_dirs in test_case_wd_subdirectories:
                    if 'Export_Files' in test_case_wd_dirs:
                        test_case_export_path_wd = Auto_Module.file_tools.get_path(test_case_path_wd, test_case_wd_dirs)
                        export_zips_path = Auto_Module.export.create_temp_directory(test_case_export_path_wd)
                        test_case_wd_dashboard_path = Auto_Module.file_tools.create_directory(test_case_export_path_wd,
                                                                                              'dashboards')
                        test_case_wd_schema_path = Auto_Module.file_tools.create_directory(test_case_export_path_wd,
                                                                                           'schemas')
                        custom_export_dashboard_names_list = Auto_Module.export.export_dashboards(incorta, session,
                                                                                                  export_zips_path,
                                                                                                  import_dashboard_names_list)
                        Auto_Module.export.export_zip(export_zips_path, test_case_wd_dashboard_path,
                                                      custom_export_dashboard_names_list)
                        custom_export_schema_names_list = Auto_Module.export.export_schemas(incorta, session,
                                                                                            export_zips_path,
                                                                                            import_schema_names_list)
                        Auto_Module.export.export_zip(export_zips_path, test_case_wd_schema_path,
                                                      custom_export_schema_names_list)

                # Define EXPORT DATA STRUCTURES

                export_dash_ids = {}
                export_dash_tenants = {}
                export_dashboard_names_list = []
                export_schema_names = {}
                export_schema_loaders = {}
                export_schema_tenants = {}
                export_schema_names_list = []

                export_dash_ids, export_dash_tenants, export_dashboard_names_list = Auto_Module.validation.get_dashboards_info(
                    export_path)
                export_schema_names, export_schema_loaders, export_schema_tenants, export_schema_names_list = Auto_Module.validation.get_schemas_info(
                    export_path)

                # TENANT EDITOR
                Auto_Module.validation.tenant_editor(export_path)

                # META DATA VALIDATION IMPLEMENTATION
                if config_defaults['skip_validation'] == 'False':
                #Comparing Dashboard Items
                    Auto_Module.validation.validation(sub_dir, import_dash_ids, export_dash_ids,
                                                      XML_MetaData_Validation_Path, 'dashboards')
                    Auto_Module.validation.validation(sub_dir, import_dash_tenants, export_dash_tenants,
                                                      XML_MetaData_Validation_Path, 'dashboard_tenants')
                    Auto_Module.validation.validation(sub_dir, import_schema_names, export_schema_names,
                                                      XML_MetaData_Validation_Path, 'schemas')
                    Auto_Module.validation.validation(sub_dir, import_schema_loaders, export_schema_loaders,
                                                      XML_MetaData_Validation_Path, 'schema_loaders')
                    Auto_Module.validation.validation(sub_dir, import_schema_tenants, export_schema_tenants,
                                                      XML_MetaData_Validation_Path, 'schema_tenants')

                # Load Data
                table = None
                incremental = False
                snapshot = False
                staging = False
                Auto_Module.data_upload.Load_data(incorta, session, export_schema_names_list)

                # LOAD VALIDATION
                # Appends to list of loaded schemas as for loop goes through every test case

                full_schema_export_list.extend(export_schema_names_list)
                schema_list = Auto_Module.data_upload.load_validator(incorta_home, export_schema_names_list,
                                                                     full_schema_export_list)
                Auto_Module.data_upload.loaded_validator(schema_list, export_schema_names_list, Loader_Validation_Path)

                # GRANT PERMISSIONS
                for user in user_list:
                    for dashboard_name in export_dashboard_names_list:
                        #print "Granting permission to access dashboard ", dashboard_name
                        grant_user_access(session, user, 'dashboard', os.sep + dashboard_name, 'edit')

                # LOG OUT SUPER USER
                time.sleep(2)
                try:
                    logout(session)
                    print "Logged out Super User successfully"
                except Exception, e:
                    print "unable to logout"
                time.sleep(2)

                # JSON DASHBOARD EXPORT
                test_case_dashboard_export_list = export_dash_ids.keys()
                if Debug == True:
                    print "session: ", session, " \n\n"
                    #logging.debug("session: " + session + " \n\n")
                    print "session id: ", session_id
                    #logging.debug("session id: " + session_id)
                    print "dashboard id: ", test_case_dashboard_export_list
                    #logging.debug("dashboard id:" + " ".join(test_case_dashboard_export_list))
                    print "CSRF TOKEN", csrf_token
                    #logging.debug("CSRF TOKEN" + csrf_token)
                    print "Test Case Path", test_case_path_wd
                    #logging.debug("Test Case Path" + test_case_path_wd)
                    print "Entering JSON DASH EXPORT"
                    #logging.debug("Entering JSON DASH EXPORT")

                user_pass = 'superpass'
                print "TESTING USER LOGIN"
                for user in user_list:
                    session = login(url, tenant, user, user_pass)
                    time.sleep(2)
                    print "Logged in user.. ", user
                    session_id = session[21:53]
                    csrf_token = session[63:95]
                    Auto_Module.export.export_dashboards_json(session_id, test_case_dashboard_export_list, csrf_token,
                                                              test_case_path_wd, test_case_path, user)
                    logout(session)
                    time.sleep(2)
                    print "Logged out user.. ", user

                if Debug == True:
                    print "\nFinished JSON DASH EXPORT"
                    #logging.debug("\nFinished JSON DASH EXPORT")

                # LOGGING IN SUPER USER
                try:
                    time.sleep(2)
                    session = login(url, tenant, username, password)
                    time.sleep(2)
                    print "Logged in Super User"
                except Exception, e:
                    print "Unable to log in Super User"
                if Debug == False:
                    print "\nFinished JSON DASH EXPORT"
                    #logging.debug("\nFinished JSON DASH EXPORT")

                # DATA VALIDATION
                if config_defaults['skip_validation'] == 'False':
                    for user in user_list:
                        output_test_case_path = Data_Validation_Path + os.sep + dir
                        output_user_path = Auto_Module.output.create_output_user_path(output_test_case_path, user)
                        Auto_Module.json_validation.validation(test_case_path, test_case_path_wd, output_wd_path,
                                                           current_test_suite, output_user_path, user)
                        # Removes user folders not being tested within the current test case
                        if os.listdir(output_user_path) == []:
                            os.rmdir(output_user_path)

                print "SEARCHING FOR SUCS AND DIFFS"
                test_case_name_dict[dir] = Auto_Module.output.data_validation_generate_suc_dif_file_names(Data_Validation_Path, dir)
            # Verify the List of Loaded Schemas

                meta_data_case_dict['dashboard_tenants'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(XML_MetaData_Validation_Path, 'dashboard_tenants')
                meta_data_case_dict['dashboards'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(XML_MetaData_Validation_Path, 'dashboards')
                meta_data_case_dict['schema_loaders'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(XML_MetaData_Validation_Path, 'schema_loaders')
                meta_data_case_dict['schema_tenants'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(XML_MetaData_Validation_Path, 'schema_tenants')
                meta_data_case_dict['schemas'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(XML_MetaData_Validation_Path, 'schemas')

            Auto_Module.data_upload.schema_load_validatior(schema_list, full_schema_export_list, Loader_Validation_Path)
            loader_valid_dict[sub_dir] = Auto_Module.output.loader_validation_generate_suc_dif_file_names(Loader_Validation_Path)
            test_suite_name_dict[sub_dir] = test_case_name_dict
            metadata_suite_dict[sub_dir] = meta_data_case_dict
    test_suite_name_list.append(sub_dir)

print test_suite_name_list




print "------------------------------------------Summary------------------------------------------------------"

print "PERFORMANCE: "
print "     Time Taken To Run Framework: ", (time.time()-start_time), " seconds"
minute_timer = (time.time()-start_time) / 60
print "         In minutes... ", minute_timer


for suite in test_suite_name_list:
    print "\n"
    print suite, " ", "RESULTS"
    print "-------------------------------------------------"
    print "\n"
    print "             DATA VALIDATION"
    json_suc_count = 0
    json_dif_count = 0
    json_total_count = 0
    test_suite_check = True
    print "\n"
    if suite in test_suite_name_dict.keys():
        if test_suite_check == False:
            suite_result = 'FAILED'
        else:
            suite_result = 'SUCCESSFUL'
        temp_dict = test_suite_name_dict[suite]
        case_check = True
        print "TEST SUITE: ", suite, ' > ', suite_result
        for item, value in temp_dict.items():
            failed_files = []
            for file in value:
                if '.dif' in file:
                    case_check = False
                    failed_files.append(file)
            if case_check == False:
                result = 'failed'
                json_dif_count += 1
            else:
                result = 'successful'
                json_suc_count += 1
            print "Test Case: ", item, " > ", result
            if result == 'failed':
                print "     FAILED FILES: ", failed_files
            json_total_count += 1
        if case_check == False:
            test_suite_check = False
    json_pass_rate = (json_suc_count / json_total_count) * 100
    print "\nJSON DATA VALIDATION TEST SUITE ", suite, ": ", "Failure Count: ",json_dif_count, " Success Count: ", json_suc_count, "DATA PASS RATE: ", json_pass_rate
    #print "-------------------------------------------------"
    print '\n'
    print "             METADATA VALIDATION\n"
    test_suite_check = True
    if suite in metadata_suite_dict.keys():
        if test_suite_check == False:
            suite_result = 'FAILED'
        else:
            suite_result = 'SUCCESSFUL'
        temp_dict = metadata_suite_dict[suite]
        case_check = True
        print "TEST SUITE: ", suite, ' > ', suite_result
        for item, value in temp_dict.items():
            failed_files = []
            for file in value:
                if '.dif' in file:
                    case_check = False
                    failed_files.append(file)
            if case_check == False:
                result = 'failed'
            else:
                result = 'successful'
            print "MetaData Type: ", item, " > ", result
            if result == 'failed':
                print "     FAILED FILES: ", failed_files
        if case_check == False:
            test_suite_check = False

    #print "-------------------------------------------------"
    print "\n"
    print "             LOADER VALIDATION\n"
    loader_test_suite_check = True
    if suite in loader_valid_dict.keys():
        for schema in loader_valid_dict[suite]:
            if '.dif' in schema:
                loader_test_suite_check = False
        if loader_test_suite_check == False:
            loader_result = 'FAILED'
        else:
            loader_result = 'SUCCESSFUL'
    else:
        loader_result = 'MISSING'
    print "TEST SUITE: ", suite, " ", loader_result
    try:
        print loader_valid_dict[suite]
    except Exception:
        print suite, "Does not contain loaded schemas"