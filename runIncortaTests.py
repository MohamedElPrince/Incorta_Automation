import sys, os, subprocess, time, zipfile
import os.path
from sys import argv
from shutil import copyfile


import errno

"""
------------------------------------------Initialization----------------------------------------
"""
"""
Arguments
-------------------------------------------
-d: import datasource/schema/dashboard
-l: load data
-x: extract files
-------------------------------------------
"""

Debug = True  # Debug flag for print statements


preCheckScriptPAth = os.getcwd() + os.sep + 'BashScripts' + os.sep + 'automationCheck.sh'
if os.path.isfile(preCheckScriptPAth):
    try:
        os.system(preCheckScriptPAth)
    except Exception, e:
        raise Exception ("Pre-Check Script Did Not Run")

import Auto_Module.export
import Auto_Module.test_suite_export_wd
import Auto_Module.file_tools
import Auto_Module.test_suite_import
from Auto_Module import *
import Auto_Module.validation
import Auto_Module.data_upload
import Auto_Module.json_validation
import Auto_Module.output
import Auto_Module.initialization
import Auto_Module.ldap_utilities
from Auto_Module import customLogger






"""
------------------------------------------Initialization----------------------------------------
"""

# All the arguments which are passed in command line
# sys.argv[0:] = argv

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



# def set_block_defaults(commands):
#     """
#     Function checks if an import, data load, or extract file argument is passed after the config file
#     If so, the key corresponding to the action will be set to True
#         args:
#             commands: the arguments after the config file
#         returns:
#             Nothing
#         prints:
#             Nothing
#     """
#     # If no arguments come after the config file, import, data load, and extract file keys will all be set to True
#     if len(sys.argv) == 2:
#         config_defaults['data_load'] = True
#         config_defaults['extract_csv'] = True
#         config_defaults['import_object'] = True
#     else:
#         for command in commands:
#             if command == '-d':
#                 config_defaults['import_object'] = True
#             if command == '-l':
#                 config_defaults['data_load'] = True
#             if command == '-x':
#                 config_defaults['extract_csv'] = True


# def set_new_defaults(config_file):
#     """
#     Function parses config file for keys and values and stores them in new_config_defualts
#         args:
#             config_file: config file holds keys and values to be used
#         returns:
#             Nothing
#         prints:
#             Nothing
#     """
#     f = open(config_file, "r")
#     lines = f.readlines()
#     f.close()
#     for line in lines:
#         for key, value in config_defaults.items():
#             if line[0] != '#' or " ":
#                 str = line
#                 str_tup = str.split("=", 1)
#                 if key == str_tup[0]:
#                     var = key + '='
#                     what_is_after_var = line[len(var):]
#                     what_is_after_key = line[len(key):]
#                     # if there is nothing after an = of a key in config file, the default value of the key will be assigned
#                     # Ex: admin=________ -> admin=Super
#                     if len(what_is_after_var) == 1:
#                         config_defaults[key] = value.rstrip()
#                     # if there is nothing after the name of a key in config file, the default value of the key will be assigned
#                     # Ex: admin____ -> admin=Super
#                     elif len(what_is_after_key) == 1:
#                         config_defaults[key] = value.rstrip()
#                     else:
#                         config_defaults[key] = what_is_after_var.rstrip()
#
#     # If a key from config_defaults is missing in the config file, the code below will create the key
#     # in config_defaults and will assign that key its default value
#     new_key_list = []
#     old_key_list = []
#
#     for new_key in config_defaults.keys():
#         new_key_list.append(new_key)
#
#     for old_key in config_defaults.keys():
#         old_key_list.append(old_key)
#
#     for key in old_key_list:
#         if key not in new_key_list:
#             config_defaults[key] = config_defaults[key]
#
#
#
#
#     # If a custom working directory path is specified, /IncortaTesting/tmp/work will
#     # be added to the end of the custom working directory path
#     if config_defaults['wd_path'] == '/IncortaTesting':
#         pass
#     else:
#         timestamp = ''
#         config_defaults['wd_path'] += '/IncortaTesting'
#
#         orig_wd_path = config_defaults['wd_path']
#         add_time_stamp_to_wd(timestamp)
#         return orig_wd_path

# def add_time_stamp_to_wd(timestamp):
#     """
#     Function adds a timestamp to the end of the working directory path
#         args:
#             timestamp: MM/DD/YY-HR/MIN/SEC
#         returns:
#             Nothing
#         prints:
#             Nothing
#     """
#     date_and_time = str(time.strftime("%m:%d:%Y-%H:%M:%S"))
#     config_defaults['wd_path'] += '/%s' % date_and_time

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
        customLogger.writeLogMessage("Login Failed", logObject, str(CRITICAL))
        exit(1)


def logout(session):
    try:
        incorta.logout(session)
    except Exception, e:
        print 'Failed to logout'
        customLogger.writeLogMessage('Failed to logout', logObject, str(CRITICAL))
        exit(1)


def get_test_suite_path(test_suite):
    """
    Function gets path of test suite within the AF
    	args:
    		test_suite: pass test suite name as string
    	returns:
            Path of test suite located within the AF
    	prints:
            Nothing
    """
    test_suite_path = os.getcwd() + '/' + "TestSuites"
    test_suite_path = test_suite_path + '/' + test_suite
    return test_suite_path

def grant_user_access(session, user_name, entity_type, entity_name, permission):
    try:
        incorta.grant_user_access(session, user_name, entity_type, entity_name, permission)
        print "Access to ", entity_type, " ", entity_name, " given to ", user_name
        customLogger.writeLogMessage("Access to %s %s given to %s" % (entity_type, entity_name, user_name), logObject, str(INFO))
    except Exception, e:
        print "Failed to grant user access to", user_name
        customLogger.writeLogMessage("Failed to grant user access to %s" % user_name, logObject, str(CRITICAL))
        return

"""
#################################################### Functions ####################################################
"""

Auto_Module.initialization.set_block_defaults(commands, config_defaults)
Auto_Module.initialization.set_new_defaults(config_file, config_defaults)
orig_wd_path = Auto_Module.initialization.set_new_defaults(config_file, config_defaults)

# converts keys in a dictionary to variables
locals().update(config_defaults)

#Different levels of logging to pass to writeLogMessage
DEBUG = 'debug'
INFO = 'info'
WARNING = 'warning'
ERROR = 'error'
CRITICAL = 'critical'

# Creates Output Directory
output_wd_path = Auto_Module.output.create_output_folder(wd_path)

customLogger.setup_logger('output', wd_path + os.sep + r'output.log')
if Debug == False:
    for key, value in config_defaults.items():
        print(key, value)
        customLogger.writeLogMessage('%s %s' % (key, value),logObject, str(INFO))

incorta_api_import(incorta_home)  # Import Incorta API
session = login(url, tenant, username, password)  # Login to Incorta
session_id = session[21:53]
csrf_token = session[63:95]
test_suite_directory_path = os.getcwd() + '/' + "TestSuites"
test_suite_directories = Auto_Module.file_tools.get_subdirectories(test_suite_directory_path)

# LOAD USERS FROM LDAP
print "Checking if instance needs to load users"
customLogger.writeLogMessage("Checking if instance needs to load users", logObject, str(INFO))
owd = os.getcwd()
sync = orig_wd_path + os.sep + 'sync.txt'

if os.path.isfile(sync):
    print "Users already Loaded"
    customLogger.writeLogMessage("Users already Loaded", logObject, str(INFO))
else:
    print "Preparing to populate users from LDAP"
    customLogger.writeLogMessage("Preparing to populate users from LDAP", logObject, str(INFO))
    Auto_Module.ldap_utilities.ldap_property_setup(incorta_home, ldap_url, ldap_base, ldap_user_mapping_login, ldap_group_mapping_member, ldap_group_search_filter)
    Auto_Module.ldap_utilities.dirExport(incorta_home)
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
customLogger.writeLogMessage("USER LIST: %s" % user_list, logObject, str(INFO))

for sub_dir in test_suite_directories:
    print "Current Test Suite: ", sub_dir
    customLogger.writeLogMessage("Current Test Suite: %s" % sub_dir, logObject, str(INFO))
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
            customLogger.writeLogMessage("-----------------------------------------------------------------------------", logObject, str(INFO))
            customLogger.writeLogMessage("ENTERING DATA FILES", logObject, str(INFO))
            test_suite_subdirectories = Auto_Module.file_tools.get_subdirectories(test_suite_path)

            if Debug == True:
                print test_suite_subdirectories
                customLogger.writeLogMessage(test_suite_subdirectories, logObject, str(DEBUG))
            current_test_suite = sub_dir

            full_schema_export_list = []
            # ENTERING TEST CASES
            for dir in test_suite_subdirectories:  # For loop for each test case inside test suite
                print "Current Test Case: ", dir
                customLogger.writeLogMessage("Current Test Case: %s" % dir, logObject, str(INFO))
                # Get path of test_case in test_suite
                test_case_path = Auto_Module.file_tools.get_path(test_suite_path, dir)
                if Debug == True:
                    print test_case_path
                    customLogger.writeLogMessage(test_case_path, logObject, str(DEBUG))
                # Get subdirectories of test case
                test_case_subdirectories = Auto_Module.file_tools.get_subdirectories(test_case_path)
                if Debug == True:
                    print test_case_subdirectories
                    customLogger.writeLogMessage(test_case_subdirectories, logObject, str(DEBUG))
                # Creates test_suite folder in WD
                test_case_path_wd = Auto_Module.file_tools.create_directory(test_suite_wd_path, dir)
                if Debug == True:
                    print test_case_path_wd
                    customLogger.writeLogMessage(test_case_path_wd, logObject, str(DEBUG))
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
                # Exported Dashboard ID's per test case
                test_case_dashboard_export_list = export_dash_ids.keys()
                # GRANT PERMISSIONS
                print "DASHBOARD LIST: ", export_dashboard_names_list
                customLogger.writeLogMessage("DASHBOARD LIST: %s" % export_dashboard_names_list, logObject, str(INFO))
                for user in user_list:
                    for dashboard_name in export_dashboard_names_list:
                        grant_user_access(session, user, 'dashboard', os.sep + dashboard_name, 'edit')

                # LOG OUT SUPER USER
                time.sleep(2)
                try:
                    logout(session)
                    print "Logged out Super User successfully"
                    customLogger.writeLogMessage("Logged out Super User successfully", logObject, str(INFO))
                except Exception, e:
                    print "unable to logout"
                    customLogger.writeLogMessage("Unable to Logout", logObject, str(CRITICAL))
                time.sleep(2)

                # JSON DASHBOARD EXPORT
                if Debug == True:
                    print "session: ", session, " \n\n"
                    customLogger.writeLogMessage("Session: %s\n\n" % session, logObject, str(DEBUG))
                    print "session id: ", session_id
                    customLogger.writeLogMessage("Session id: %s" % session_id, logObject, str(DEBUG))
                    print "dashboard id: ", test_case_dashboard_export_list
                    customLogger.writeLogMessage("Dashboard id: %s" % test_case_dashboard_export_list, logObject, str(DEBUG))
                    print "CSRF TOKEN", csrf_token
                    customLogger.writeLogMessage("CSRF TOKEN: %s" % csrf_token, logObject, str(DEBUG))
                    print "Test Case Path", test_case_path_wd
                    customLogger.writeLogMessage("Test Case Path: %s" % test_case_path_wd, logObject, str(DEBUG))
                    print "Entering JSON DASH EXPORT"
                    customLogger.writeLogMessage("Entering JSON DASH EXPORT", logObject, str(DEBUG))

                user_pass = 'superpass'
                print "TESTING USER LOGIN"
                customLogger.writeLogMessage("TESTING USER LOGIN", logObject, str(INFO))
                for user in user_list:
                    session = login(url, tenant, user, user_pass)
                    time.sleep(2)
                    print "Logged in user.. ", user
                    customLogger.writeLogMessage("Logged in user.. %s" % user, logObject, str(INFO))
                    session_id = session[21:53]
                    csrf_token = session[63:95]
                    Auto_Module.export.export_dashboards_json(session_id, test_case_dashboard_export_list, csrf_token,
                                                             test_case_path_wd, test_case_path, user)
                    logout(session)
                    time.sleep(2)
                    print "Logged out user.. ", user
                    customLogger.writeLogMessage("Logged out user.. %s" % user, logObject, str(INFO))

                if Debug == True:
                    print "\nFinished JSON DASH EXPORT"
                    customLogger.writeLogMessage("\nFinished JSON DASH EXPORT", logObject, str(DEBUG))

                #LOGGING IN SUPER USER
                try:
                    time.sleep(2)
                    session = login(url, tenant, username, password)
                    time.sleep(2)
                    print "Logged in Super User"
                    customLogger.writeLogMessage("Logged in Super User", logObject, str(INFO))
                except Exception, e:
                    print "Unable to log in Super User"
                    customLogger.writeLogMessage("Unable to log in Super User", logObject, str(CRITICAL))

                if Debug == False:
                    print "\nFinished JSON DASH EXPORT"
                    customLogger.writeLogMessage("\nFinished JSON DASH EXPORT", logObject, str(DEBUG))

                # DATA VALIDATION
                if config_defaults['skip_validation'] == 'False':
                    for user in user_list:
                        print "Validating data for user - ", user, " test case - ", dir
                        customLogger.writeLogMessage(("Validating data for user - %s test case - %s" % (user, dir)), logObject, str(INFO))
                        output_test_case_path = Data_Validation_Path + os.sep + dir
                        output_user_path = Auto_Module.output.create_output_user_path(output_test_case_path, user)
                        Auto_Module.json_validation.validation(test_case_path, test_case_path_wd, output_wd_path, current_test_suite, output_user_path, user)
                        # Removes user folders not being tested within the current test case
                        if os.listdir(output_user_path) == []:
                            os.rmdir(output_user_path)

            # Compares Loaded Schema List to Exported Schema List
            Auto_Module.data_upload.schema_load_validatior(schema_list, full_schema_export_list, Loader_Validation_Path)

        if 'datasourcess' == names:
            print "-----------------------------------------------------------------------------"
            customLogger.writeLogMessage(
                "-----------------------------------------------------------------------------", logObject, str(INFO))
            print "ENTERING DATA SOURCES"
            customLogger.writeLogMessage("ENTERING DATA FILES", logObject, str(INFO))
            test_suite_subdirectories = Auto_Module.file_tools.get_subdirectories(test_suite_path)

            if Debug == True:
                print test_suite_subdirectories
                customLogger.writeLogMessage(test_suite_subdirectories, logObject, str(DEBUG))
            current_test_suite = sub_dir

            full_schema_export_list = []
            # ENTERING TEST CASES
            for dir in test_suite_subdirectories:  # For loop for each test case inside test suite
                print "Current Test Case: ", dir
                customLogger.writeLogMessage("Current Test Case: %s" % dir, logObject, str(INFO))
                # Get path of test_case in test_suite
                test_case_path = Auto_Module.file_tools.get_path(test_suite_path, dir)
                if Debug == True:
                    print test_case_path
                    customLogger.writeLogMessage(test_case_path, logObject, str(DEBUG))
                # Get subdirectories of test case
                test_case_subdirectories = Auto_Module.file_tools.get_subdirectories(test_case_path)
                if Debug == True:
                    print test_case_subdirectories
                    customLogger.writeLogMessage(test_case_subdirectories, logObject, str(DEBUG))
                # Creates test_suite folder in WD
                test_case_path_wd = Auto_Module.file_tools.create_directory(test_suite_wd_path, dir)
                if Debug == True:
                    print test_case_path_wd
                    customLogger.writeLogMessage(test_case_path_wd, logObject, str(DEBUG))
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

                # GRANT PERMISSIONS
                print "DASHBOARD LIST: ", export_dashboard_names_list
                customLogger.writeLogMessage("DASHBOARD LIST: %s" % export_dashboard_names_list, logObject, str(INFO))
                for user in user_list:
                    for dashboard_name in export_dashboard_names_list:
                        grant_user_access(session, user, 'dashboard', os.sep + dashboard_name, 'edit')

                # LOG OUT SUPER USER
                time.sleep(2)
                try:
                    logout(session)
                    print "Logged out Super User successfully"
                    customLogger.writeLogMessage("Logged out Super User successfully",logObject, str(INFO))
                except Exception, e:
                    print "unable to logout"
                    customLogger.writeLogMessage("Unable To Logout", logObject, str(CRITICAL))
                time.sleep(2)

                # JSON DASHBOARD EXPORT
                test_case_dashboard_export_list = export_dash_ids.keys()
                if Debug == True:
                    print "session: ", session, " \n\n"
                    customLogger.writeLogMessage("Session: %s\n\n" % session, logObject, str(DEBUG))
                    print "session id: ", session_id
                    customLogger.writeLogMessage("Session id: %s" % session_id, logObject, str(DEBUG))
                    print "dashboard id: ", test_case_dashboard_export_list
                    customLogger.writeLogMessage("Dashboard id: %s" % test_case_dashboard_export_list, logObject, str(DEBUG))
                    print "CSRF TOKEN", csrf_token
                    customLogger.writeLogMessage("CSRF TOKEN: %s" % csrf_token, logObject, str(DEBUG))
                    print "Test Case Path", test_case_path_wd
                    customLogger.writeLogMessage("Test Case Path: $s" % test_case_path_wd, logObject, str(DEBUG))
                    print "Entering JSON DASH EXPORT"
                    customLogger.writeLogMessage("Entering JSON DASH EXPORT", logObject, str(DEBUG))

                user_pass = 'superpass'
                print "TESTING USER LOGIN"
                customLogger.writeLogMessage("TESTING USER LOGIN", logObject, str(INFO))
                for user in user_list:
                    session = login(url, tenant, user, user_pass)
                    time.sleep(2)
                    print "Logged in user.. ", user
                    customLogger.writeLogMessage(("Logged in user.. ", user), logObject, str(INFO))
                    session_id = session[21:53]
                    csrf_token = session[63:95]
                    Auto_Module.export.export_dashboards_json(session_id, test_case_dashboard_export_list, csrf_token,
                                                              test_case_path_wd, test_case_path, user)
                    logout(session)
                    time.sleep(2)
                    print "Logged out user.. ", user
                    customLogger.writeLogMessage("Logged out user.. %s" % user, logObject, str(INFO))

                if Debug == True:
                    print "\nFinished JSON DASH EXPORT"
                    customLogger.writeLogMessage("\nFinished JSON DASH EXPORT", logObject, str(DEBUG))

                # LOGGING IN SUPER USER
                try:
                    time.sleep(2)
                    session = login(url, tenant, username, password)
                    time.sleep(2)
                    print "Logged in Super User"
                    customLogger.writeLogMessage("Logged in Super User", logObject, str(INFO))
                except Exception, e:
                    print "Unable to log in Super User"
                    customLogger.writeLogMessage("Unable to log in Super User", logObject, str(CRITICAL))

                if Debug == False:
                    print "\nFinished JSON DASH EXPORT"
                    customLogger.writeLogMessage("\nFinished JSON DASH EXPORT", logObject, str(DEBUG))

                # DATA VALIDATION
                if config_defaults['skip_validation'] == 'False':
                    for user in user_list:
                        print "Validating data for user - ", user, " test case - ", dir
                        customLogger.writeLogMessage(("Validating data for user - %s test case - %s" % (user, dir)), logObject, str(INFO))
                        output_test_case_path = Data_Validation_Path + os.sep + dir
                        output_user_path = Auto_Module.output.create_output_user_path(output_test_case_path, user)
                        Auto_Module.json_validation.validation(test_case_path, test_case_path_wd, output_wd_path,
                                                           current_test_suite, output_user_path, user)
                        # Removes user folders not being tested within the current test case
                        if os.listdir(output_user_path) == []:
                            os.rmdir(output_user_path)
            # Verify the List of Loaded Schemas
            Auto_Module.data_upload.schema_load_validatior(schema_list, full_schema_export_list, Loader_Validation_Path)
customLogger.shutdown_logger(logObject)
