import time
start_time = time.time()
import sys, os, subprocess, zipfile
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

Debug = False  # Debug flag for print statements

preCheckScriptPAth = os.getcwd() + os.sep + 'BashScripts' + os.sep + 'automationCheck.sh'
if os.path.isfile(preCheckScriptPAth):
    try:
        os.system(preCheckScriptPAth)
    except Exception, e:
        raise Exception("Pre-Check Script Did Not Run")

"""
NEW IMPORTS--Please Retain this order
"""
from config.initialization_global import *

# Create working directory
try:
    os.makedirs(wd_path)
except OSError as exc:
    if exc.errno == errno.EEXIST and os.path.isdir(wd_path):
        pass
    else:
        raise
# Create working directory

from Auto_Module.customLogger import *

"""
NEW IMPORTS--Please Retain this order
"""

import Auto_Module.export
import Auto_Module.test_suite_export_wd
import Auto_Module.file_tools
import Auto_Module.test_suite_import
from Auto_Module import *
import Auto_Module.validation
import Auto_Module.data_upload
import Auto_Module.json_validation
import Auto_Module.ldap_utilities
import Auto_Module.output



"""
#################################################### Functions ####################################################
"""

ldap_url = config_defaults['ldap.base.provider.url']
ldap_base = config_defaults['ldap.base.dn']
ldap_user_mapping_login = config_defaults['ldap.user.mapping.login']
ldap_group_mapping_member = config_defaults['ldap.group.mapping.member']
ldap_group_search_filter = config_defaults['ldap.group.search.filter']

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
        writeLogMessage("Login Failed", mainLogger, str(CRITICAL))
        exit(1)

def logout(session):
    try:
        incorta.logout(session)
    except Exception, e:
        print 'Failed to logout'
        writeLogMessage('Failed to logout', mainLogger, str(CRITICAL))
        exit(1)

def get_test_suite_path(test_suite):
    test_suite_path = os.getcwd() + '/' + "TestSuites"
    test_suite_path = test_suite_path + '/' + test_suite
    return test_suite_path


def grant_user_access(session, user_name, entity_type, entity_name, permission):
    try:
        incorta.grant_user_access(session, user_name, entity_type, entity_name, permission)
        print "Access to ", entity_type, " ", entity_name, " given to ", user_name
        writeLogMessage("Access to %s %s given to %s" % (entity_type, entity_name, user_name), mainLogger, str(INFO))
    except Exception, e:
        print "Failed to grant user access to", user_name
        writeLogMessage("Failed to grant user access to %s" % user_name, mainLogger, str(CRITICAL))
        return


"""
#################################################### Functions ####################################################
"""
# set_block_defaults(commands, config_defaults)
# set_new_defaults(config_file, config_defaults)
# set_new_defaults(config_file, config_defaults)
# Auto_Module..set_block_defaults(commands, config_defaults)
# Auto_Module.initialization.set_new_defaults(config_file, config_defaults)
# orig_wd_path = Auto_Module.initialization.set_new_defaults(config_file, config_defaults)
# converts keys in a dictionary to variables

locals().update(config_defaults)

# Creates Output Directory
output_wd_path = Auto_Module.output.create_output_folder(wd_path)
test_suite_name_list = []
test_case_name_list = []
output_dict = {}
test_suite_name_dict = {}
loader_valid_dict = {}
metadata_suite_dict = {}

if Debug == True:
    for key, value in config_defaults.items():
        print(key, value)
        writeLogMessage('%s %s' % (key, value), mainLogger, str(DEBUG))

incorta_api_import(incorta_home)  # Import Incorta API
session = login(url, tenant, username, password)  # Login to Incorta
session_id = session[21:53]
csrf_token = session[63:95]
test_suite_directory_path = os.getcwd() + '/' + "TestSuites"
test_suite_directories = Auto_Module.file_tools.get_subdirectories(test_suite_directory_path)

# LOAD USERS FROM LDAP
print "Checking if instance needs to load users"
writeLogMessage("Checking if instance needs to load users", mainLogger, str(INFO))
owd = os.getcwd()
sync = orig_wd_path + os.sep + 'sync.txt'

if os.path.isfile(sync):
    print "Users already Loaded"
    writeLogMessage("Users already Loaded", mainLogger, str(INFO))
else:
    print "Preparing to populate users from LDAP"
    writeLogMessage("Preparing to populate users from LDAP", mainLogger, str(INFO))
    Auto_Module.ldap_utilities.ldap_property_setup(incorta_home, ldap_url, ldap_base, ldap_user_mapping_login,
                                                   ldap_group_mapping_member, ldap_group_search_filter)
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
    Auto_Module.file_tools.move_file(owd + os.sep + 'users.csv', dirExport_path)
    Auto_Module.file_tools.move_file(owd + os.sep + 'user-groups.csv', dirExport_path)
    Auto_Module.file_tools.move_file(owd + os.sep + 'groups.csv', dirExport_path)

user_dict = {}
user_dict = Auto_Module.ldap_utilities.read_users_from_csv(incorta_home)
user_list = user_dict.keys()
print "\n USER LIST: ", user_list
writeLogMessage("USER LIST: %s" % user_list, mainLogger, str(INFO))

for sub_dir in test_suite_directories:

    loaded_schemas = []
    test_case_name_dict = {}
    meta_data_case_dict = {}

    print "Current Test Suite: ", sub_dir
    writeLogMessage("Current Test Suite: %s" % sub_dir, mainLogger, str(INFO))
    test_suite_wd_path = Auto_Module.file_tools.create_directory(wd_path, sub_dir)  # Working Directory test suite path
    test_suite_path = get_test_suite_path(sub_dir)  # Path of test suite
    test_cases_dir = Auto_Module.file_tools.get_subdirectories(test_suite_path)
    temp_path = Auto_Module.file_tools.get_path(test_suite_path, test_cases_dir[0])
    temp_dir = Auto_Module.file_tools.get_subdirectories(temp_path)
    # Creating Output Structure
    test_suite_output_path = Auto_Module.output.create_test_suite_output_folder(output_wd_path, sub_dir)
    # Validation Sub Directories
    Data_Validation_Path, Loader_Validation_Path, XML_MetaData_Validation_Path = Auto_Module.output.create_test_suite_validation_folders(
        test_suite_output_path)

    for names in temp_dir:
        if 'datafiles' == names:
            print "-----------------------------------------------------------------------------"
            print "ENTERING DATA FILES"
            writeLogMessage("-----------------------------------------------------------------------------", mainLogger,
                            str(INFO))
            writeLogMessage("ENTERING DATA FILES", mainLogger, str(INFO))
            test_suite_subdirectories = Auto_Module.file_tools.get_subdirectories(test_suite_path)

            if Debug == True:
                print test_suite_subdirectories
                writeLogMessage(test_suite_subdirectories, mainLogger, str(DEBUG))
            current_test_suite = sub_dir

            full_schema_export_list = []
            # ENTERING TEST CASES
            for dir in test_suite_subdirectories:  # For loop for each test case inside test suite
                print "Current Test Case: ", dir
                writeLogMessage("Current Test Case: %s" % dir, mainLogger, str(INFO))
                # Get path of test_case in test_suite
                test_case_path = Auto_Module.file_tools.get_path(test_suite_path, dir)
                if Debug == True:
                    print test_case_path
                    writeLogMessage(test_case_path, mainLogger, str(DEBUG))

                # Get subdirectories of test case
                test_case_subdirectories = Auto_Module.file_tools.get_subdirectories(test_case_path)
                if Debug == True:
                    print test_case_subdirectories
                    writeLogMessage(test_case_subdirectories, mainLogger, str(DEBUG))
                # Creates test_suite folder in WD
                test_case_path_wd = Auto_Module.file_tools.create_directory(test_suite_wd_path, dir)
                if Debug == True:
                    print test_case_path_wd
                    writeLogMessage(test_case_path_wd, mainLogger, str(DEBUG))
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
                # Defining Import / Export Paths
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
                # Obtain Exported Information
                export_dash_ids, export_dash_tenants, export_dashboard_names_list = Auto_Module.validation.get_dashboards_info(
                    export_path)
                export_schema_names, export_schema_loaders, export_schema_tenants, export_schema_names_list = Auto_Module.validation.get_schemas_info(
                    export_path)

                # TENANT EDITOR
                Auto_Module.validation.tenant_editor(export_path)

                # META DATA VALIDATION IMPLEMENTATION
                if config_defaults['skip_validation'] == 'False':
                    # Comparing Dashboard Items
                    Auto_Module.validation.validation(sub_dir, import_dash_ids, export_dash_ids,
                                                      XML_MetaData_Validation_Path, 'dashboards')
                    Auto_Module.validation.validation(sub_dir, import_dash_tenants, export_dash_tenants,
                                                      XML_MetaData_Validation_Path, 'dashboard_tenants')
                    # Comparing Schema Items
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
                writeLogMessage("Preparing to Export: %s" % export_dashboard_names_list, mainLogger, str(INFO))
                for user in user_list:
                    for dashboard_name in export_dashboard_names_list:
                        grant_user_access(session, user, 'dashboard', os.sep + dashboard_name, 'edit')

                # LOG OUT SUPER USER
                time.sleep(2)
                try:
                    logout(session)
                    print "Logged out Super User successfully"
                    writeLogMessage("Logged out Super User successfully", mainLogger, str(INFO))
                except Exception, e:
                    print "unable to logout"
                    writeLogMessage("Unable to Logout", mainLogger, str(CRITICAL))
                time.sleep(2)

                # JSON DASHBOARD EXPORT
                if Debug == True:
                    print "session: ", session, " \n\n"
                    writeLogMessage("Session: %s\n\n" % session, mainLogger, str(DEBUG))
                    print "session id: ", session_id
                    writeLogMessage("Session id: %s" % session_id, mainLogger, str(DEBUG))
                    print "dashboard id: ", test_case_dashboard_export_list
                    writeLogMessage("Dashboard id: %s" % test_case_dashboard_export_list, mainLogger, str(DEBUG))
                    print "CSRF TOKEN", csrf_token
                    writeLogMessage("CSRF TOKEN: %s" % csrf_token, mainLogger, str(DEBUG))
                    print "Test Case Path", test_case_path_wd
                    writeLogMessage("Test Case Path: %s" % test_case_path_wd, mainLogger, str(DEBUG))
                    print "Entering JSON DASH EXPORT"
                    writeLogMessage("Entering JSON DASH EXPORT", mainLogger, str(DEBUG))

                user_pass = 'superpass'
                print "TESTING USER LOGIN"
                writeLogMessage("TESTING USER LOGIN", mainLogger, str(INFO))

                for user in user_list:
                    session = login(url, tenant, user, user_pass)
                    time.sleep(2)
                    print "Logged in user.. ", user
                    writeLogMessage("Logged in user.. %s" % user, mainLogger, str(INFO))
                    session_id = session[21:53]
                    csrf_token = session[63:95]
                    Auto_Module.export.export_dashboards_json(session_id, test_case_dashboard_export_list, csrf_token,
                                                              test_case_path_wd, test_case_path, user, url)
                    logout(session)
                    time.sleep(2)
                    print "Logged out user.. ", user
                    writeLogMessage("Logged out user.. %s" % user, mainLogger, str(INFO))

                if Debug == True:
                    print "\nFinished JSON DASH EXPORT"
                    writeLogMessage("Finished JSON DASH EXPORT", mainLogger, str(DEBUG))

                # LOGGING IN SUPER USER
                try:
                    time.sleep(2)
                    session = login(url, tenant, username, password)
                    time.sleep(2)
                    print "Logged in Super User"
                    writeLogMessage("Logged in Super User", mainLogger, str(INFO))
                except Exception, e:
                    print "Unable to log in Super User"
                    writeLogMessage("Unable to log in Super User", mainLogger, str(CRITICAL))

                if Debug == True:
                    print "\nFinished JSON DASH EXPORT"
                    writeLogMessage("Finished JSON DASH EXPORT", mainLogger, str(DEBUG))
                # DATA VALIDATION
                if config_defaults['skip_validation'] == 'False':
                    for user in user_list:
                        print "Validating data for user - ", user, " test case - ", dir
                        writeLogMessage(("Validating data for user - %s test case - %s" % (user, dir)), mainLogger,
                                        str(INFO))
                        output_test_case_path = Data_Validation_Path + os.sep + dir
                        output_user_path = Auto_Module.output.create_output_user_path(output_test_case_path, user)
                        Auto_Module.json_validation.validation(test_case_path, test_case_path_wd, output_wd_path,
                                                               current_test_suite, output_user_path, user)
                        # Removes user folders not being tested within the current test case
                        if os.listdir(output_user_path) == []:
                            os.rmdir(output_user_path)
                print "SEARCHING FOR SUCS AND DIFFS"
                writeLogMessage('SEARCHING FOR SUCS AND DIFFS', mainLogger, 'info')
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

        if 'datasources' == names:
            print "-----------------------------------------------------------------------------"
            writeLogMessage(
                "-----------------------------------------------------------------------------", mainLogger, str(INFO))
            print "ENTERING DATA SOURCES"
            writeLogMessage("ENTERING DATA FILES", mainLogger, str(INFO))
            test_suite_subdirectories = Auto_Module.file_tools.get_subdirectories(test_suite_path)

            if Debug == True:
                print test_suite_subdirectories
                writeLogMessage(test_suite_subdirectories, mainLogger, str(DEBUG))
            current_test_suite = sub_dir

            full_schema_export_list = []
            # ENTERING TEST CASES
            for dir in test_suite_subdirectories:  # For loop for each test case inside test suite
                print "Current Test Case: ", dir
                writeLogMessage("Current Test Case: %s" % dir, mainLogger, str(INFO))
                # Get path of test_case in test_suite
                test_case_path = Auto_Module.file_tools.get_path(test_suite_path, dir)
                if Debug == True:
                    print test_case_path
                    writeLogMessage(test_case_path, mainLogger, str(DEBUG))
                # Get subdirectories of test case
                test_case_subdirectories = Auto_Module.file_tools.get_subdirectories(test_case_path)
                if Debug == True:
                    print test_case_subdirectories
                    writeLogMessage(test_case_subdirectories, mainLogger, str(DEBUG))
                # Creates test_suite folder in WD
                test_case_path_wd = Auto_Module.file_tools.create_directory(test_suite_wd_path, dir)
                if Debug == True:
                    print test_case_path_wd
                    writeLogMessage(test_case_path_wd, mainLogger, str(DEBUG))
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
                    # Comparing Dashboard Items
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
                print "Preparing to Export: ", export_dashboard_names_list
                writeLogMessage("Preparing to Export: %s" % export_dashboard_names_list, mainLogger, str(INFO))
                for user in user_list:
                    for dashboard_name in export_dashboard_names_list:
                        #print "Granting permission to access dashboard ", dashboard_name
                        grant_user_access(session, user, 'dashboard', os.sep + dashboard_name, 'edit')

                # LOG OUT SUPER USER
                time.sleep(2)
                try:
                    logout(session)
                    print "Logged out Super User successfully"
                    writeLogMessage("Logged out Super User successfully", mainLogger, str(INFO))
                except Exception, e:
                    print "unable to logout"
                    writeLogMessage("Unable To Logout", mainLogger, str(CRITICAL))
                time.sleep(2)

                # JSON DASHBOARD EXPORT
                test_case_dashboard_export_list = export_dash_ids.keys()
                if Debug == True:
                    print "session: ", session, " \n\n"
                    writeLogMessage("Session: %s\n\n" % session, mainLogger, str(DEBUG))
                    print "session id: ", session_id
                    writeLogMessage("Session id: %s" % session_id, mainLogger, str(DEBUG))
                    print "dashboard id: ", test_case_dashboard_export_list
                    writeLogMessage("Dashboard id: %s" % test_case_dashboard_export_list, mainLogger, str(DEBUG))
                    print "CSRF TOKEN", csrf_token
                    writeLogMessage("CSRF TOKEN: %s" % csrf_token, mainLogger, str(DEBUG))
                    print "Test Case Path", test_case_path_wd
                    writeLogMessage("Test Case Path: $s" % test_case_path_wd, mainLogger, str(DEBUG))
                    print "Entering JSON DASH EXPORT"
                    writeLogMessage("Entering JSON DASH EXPORT", mainLogger, str(DEBUG))

                user_pass = 'superpass'
                print "TESTING USER LOGIN"
                writeLogMessage("TESTING USER LOGIN", mainLogger, str(INFO))
                for user in user_list:
                    session = login(url, tenant, user, user_pass)
                    time.sleep(2)
                    print "Logged in user.. ", user
                    writeLogMessage(("Logged in user.. ", user), mainLogger, str(INFO))
                    session_id = session[21:53]
                    csrf_token = session[63:95]
                    Auto_Module.export.export_dashboards_json(session_id, test_case_dashboard_export_list, csrf_token,
                                                              test_case_path_wd, test_case_path, user, url)
                    logout(session)
                    time.sleep(2)
                    print "Logged out user.. ", user
                    writeLogMessage("Logged out user.. %s" % user, mainLogger, str(INFO))

                if Debug == True:
                    print "\nFinished JSON DASH EXPORT"
                    writeLogMessage("Finished JSON DASH EXPORT", mainLogger, str(DEBUG))
                # LOGGING IN SUPER USER
                try:
                    time.sleep(2)
                    session = login(url, tenant, username, password)
                    time.sleep(2)
                    print "Logged in Super User"
                    writeLogMessage("Logged in Super User", mainLogger, str(INFO))
                except Exception, e:
                    print "Unable to log in Super User"
                    writeLogMessage("Unable to log in Super User", mainLogger, str(CRITICAL))

                if Debug == True:
                    print "\nFinished JSON DASH EXPORT"
                    writeLogMessage("Finished JSON DASH EXPORT", mainLogger, str(DEBUG))

                # DATA VALIDATION
                if config_defaults['skip_validation'] == 'False':
                    for user in user_list:
                        print "Validating data for user - ", user, " test case - ", dir
                        writeLogMessage(("Validating data for user - %s test case - %s" % (user, dir)), mainLogger,
                                        str(INFO))
                        output_test_case_path = Data_Validation_Path + os.sep + dir
                        output_user_path = Auto_Module.output.create_output_user_path(output_test_case_path, user)
                        Auto_Module.json_validation.validation(test_case_path, test_case_path_wd, output_wd_path,
                                                               current_test_suite, output_user_path, user)
                        # Removes user folders not being tested within the current test case
                        if os.listdir(output_user_path) == []:
                            os.rmdir(output_user_path)

                print "SEARCHING FOR SUCS AND DIFFS"
                writeLogMessage('SEARCHING FOR SUCS AND DIFFS', mainLogger, 'info')
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
writeLogMessage(test_suite_name_list, summaryLogger, 'info')
print "------------------------------------------Summary------------------------------------------------------"
writeLogMessage("------------------------------------------Summary------------------------------------------------------", summaryLogger, 'info')

print "PERFORMANCE: "
writeLogMessage("PERFORMANCE: ", summaryLogger, 'info')
print "     Time Taken To Run Framework: ", (time.time()-start_time), " seconds"
writeLogMessage("     Time Taken To Run Framework: %s seconds" % (time.time()-start_time), summaryLogger, 'info')
minute_timer = (time.time()-start_time) / 60
print "         In minutes... ", minute_timer
writeLogMessage("         In minutes... %s" % minute_timer, summaryLogger, 'info')

Total_Suite_Count = 0
Passed_Suite_Count = 0
Passed_Suite_List = []
Failed_Suite_Count = 0
Failed_Suite_List = []
for suite in test_suite_name_list:
    Total_Suite_Count += 1
    print "\n|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
    writeLogMessage("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||", summaryLogger, 'info')
    print "\n"
    writeLogMessage('\n', summaryLogger, 'info')
    print "-------------------------------------------------"
    writeLogMessage("-------------------------------------------------", summaryLogger, 'info')

    print suite, " ", "***RESULTS***"
    temp_str = suite +  " " + "***RESULTS***"
    writeLogMessage(temp_str, summaryLogger, 'info')
    print "-------------------------------------------------"
    writeLogMessage("-------------------------------------------------", summaryLogger, 'info')
    print "\n"
    writeLogMessage('\n', summaryLogger, 'info')
    print "             DATA VALIDATION"
    writeLogMessage("             DATA VALIDATION", summaryLogger, 'info')
    json_suc_count = 0
    json_dif_count = 0
    json_total_count = 0
    test_suite_check = True
    #DATA_VALID_SUCC = True
    print "\n"
    writeLogMessage('\n', summaryLogger, 'info')
    if suite in test_suite_name_dict.keys():
        if test_suite_check == False:
            suite_result = 'FAILED'
        else:
            suite_result = 'SUCCESSFUL'
        temp_dict = test_suite_name_dict[suite]
        case_check = True
        print "TEST SUITE: ", suite, ' > ', suite_result
        writeLogMessage("TEST SUITE: %s > %s" % (suite, suite_result), summaryLogger, 'info')
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
            writeLogMessage("Test Case: %s > %s" % (item, result), summaryLogger, 'info')
            if result == 'failed':
                print "     FAILED FILES: ", failed_files
                writeLogMessage("     FAILED FILES: %s" % failed_files, summaryLogger, 'warning')
            json_total_count += 1
        if case_check == False:
            #DATA_VALID_SUCC = False
            test_suite_check = False

        DATA_VALID_SUCC = test_suite_check


    json_pass_rate = (json_suc_count / json_total_count) * 100
    print "\nJSON DATA VALIDATION TEST SUITE ", suite, ": ", "Failure Count: ",json_dif_count, " Success Count: ", json_suc_count, "   DATA PASS RATE: ", json_pass_rate, "%\n"
    temp_str = "JSON DATA VALIDATION TEST SUITE " + str(suite) + ": " + "Failure Count: " + str(json_dif_count) + " Success Count: " + str(json_suc_count) + "   DATA PASS RATE: " + str(json_pass_rate) + "%\n"
    writeLogMessage(temp_str, summaryLogger, 'info')
    print "**************************************************"
    writeLogMessage("**************************************************", summaryLogger, 'info')
    print '\n'
    writeLogMessage('\n', summaryLogger, 'info')
    print "             METADATA VALIDATION\n"
    writeLogMessage("             METADATA VALIDATION\n", summaryLogger, 'info')
    test_suite_check = True

    if suite in metadata_suite_dict.keys():
        if test_suite_check == False:
            suite_result = 'FAILED'
        else:
            suite_result = 'SUCCESSFUL'
        temp_dict = metadata_suite_dict[suite]
        case_check = True
        print "TEST SUITE: ", suite, ' > ', suite_result
        writeLogMessage("TEST SUITE: %s > %s" % (suite,suite_result), summaryLogger, 'info')
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
            writeLogMessage("MetaData Type: %s > %s" % (item, result), summaryLogger, 'info')
            if result == 'failed':
                print "     FAILED FILES: ", failed_files
                writeLogMessage("     FAILED FILES: %s" % failed_files, summaryLogger, 'warning')
        if case_check == False:
            test_suite_check = False

        METADATA_VALID_SUCC = test_suite_check

    print "\n**************************************************"
    writeLogMessage("**************************************************", summaryLogger, 'info')
    print "\n"
    writeLogMessage('\n', summaryLogger, 'info')
    print "             LOADER VALIDATION\n"
    writeLogMessage("             LOADER VALIDATION\n", summaryLogger, 'info')
    loader_test_suite_check = True
    failed_schemas = []
    loader_result = ''
    if suite in loader_valid_dict.keys():
        for schema in loader_valid_dict[suite]:
            if '.dif' in schema:
                loader_test_suite_check = False
                failed_schemas.append(failed_schemas)
        if loader_test_suite_check == False:
            loader_result = 'FAILED'
        else:
            loader_result = 'SUCCESSFUL'
    else:
        loader_result = 'MISSING'
    print "TEST SUITE: ", suite, " ", loader_result
    writeLogMessage("TEST SUITE: %s %s" % (suite,loader_result), summaryLogger, 'info')
    if loader_result == 'FAILED':
        print "Schemas Failed to Load... ", failed_schemas
        writeLogMessage("Schemas Failed to Load... %s" % failed_schemas, summaryLogger, 'warning')
    LOAD_VALID_SUCC = loader_test_suite_check

    print "\n**************************************************"
    writeLogMessage("**************************************************", summaryLogger, 'info')
    SUITE_STATUS = ''
    if DATA_VALID_SUCC and METADATA_VALID_SUCC and LOAD_VALID_SUCC == True:
        SUITE_STATUS = 'PASSED'
    else:
        SUITE_STATUS = 'FAILED'


    print "\n             ", suite, " Summary\n"
    writeLogMessage("             %s Summary\n" % suite,  summaryLogger, 'info')
    print "Overall test suite ", suite, " ", SUITE_STATUS, "\n"
    writeLogMessage("Overall test suite %s %s \n" % (suite,SUITE_STATUS), summaryLogger, 'info')
    failed_phases = []
    if SUITE_STATUS == 'FAILED':
        if not DATA_VALID_SUCC:
            failed_phases.append('Data Validation Phase')
        if not METADATA_VALID_SUCC:
            failed_phases.append('MetaData Validation Phase')
        if not LOAD_VALID_SUCC:
            failed_phases.append('Schema Loader Validation Phase')
        print suite, " failed in these validation phases; ", failed_phases
        writeLogMessage(" failed in these validation phases; %s" % failed_phases, summaryLogger, 'info')
        Failed_Suite_Count += 1
        Failed_Suite_List.append(suite)
    else:
        Passed_Suite_Count += 1
        Passed_Suite_List.append(suite)



print "************************************** TEST RESULTS *************************************"
writeLogMessage("************************************** TEST RESULTS *************************************", summaryLogger, 'info')

print "Out of ", Total_Suite_Count, " tested suites,   ", Passed_Suite_Count, "passed testing      ", Failed_Suite_Count, "failed testing ", "\n"
temp_str = "Out of " + str(Total_Suite_Count) + " tested suites,   " + str(Passed_Suite_Count) + " passed testing      " + str(Failed_Suite_Count) + " failed testing " + "\n"
writeLogMessage(temp_str, summaryLogger, 'info')
if Passed_Suite_Count > 0:
    print "List of successful test suites: ", Passed_Suite_List
    writeLogMessage("List of successful test suites: %s" % Passed_Suite_List, summaryLogger, 'info')

if Failed_Suite_Count > 0:
    print "List of failed test suites: ", Failed_Suite_List
    writeLogMessage("List of failed test suites: %s" % Failed_Suite_List, summaryLogger, 'warning')

Suite_pass_rate = (Passed_Suite_Count / Total_Suite_Count) * 100

print "Incorta Build has a ", Suite_pass_rate, "% success rate"
temp_str = "Incorta Build has a " + str(Suite_pass_rate) + "% success rate"
writeLogMessage(temp_str, summaryLogger, 'info')


time.sleep(2)
shutdown_logger(mainLogger)
shutdown_logger(summaryLogger)
