import time

start_time = time.time()
import sys, os, subprocess, zipfile
sys.path.append(os.path.join(os.path.dirname(__file__), '..'))
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
Developer_Mode = True  # Developer flag for stack trace

preCheckScriptPath = os.getcwd() + os.sep + 'BashScripts' + os.sep + 'automationCheck.sh'
preRunScriptPath = os.getcwd() + os.sep + 'BashScripts' + os.sep + 'preRun.sh'
if os.path.isfile(preCheckScriptPath):
    try:
        os.system(preRunScriptPath)
        os.system(preCheckScriptPath)
    except Exception:
        raise Exception("Pre-Check/Run Script Did Not Run")

"""
NEW IMPORTS--Please Retain this order
"""
from qa.config.settings.initialization_global import *

# Create working directory
try:
    os.makedirs(wd_path)
except OSError as exc:
    if exc.errno == errno.EEXIST and os.path.isdir(wd_path):
        pass
    else:
        raise
# Create working directory
if Developer_Mode == False:
    from qa.config.settings.exceptionhandler import exceptionHandler  # Import Custom Exception Handler Stack Trace

    sys.excepthook = exceptionHandler  # Enable Custom Exception Handler Stack Trace

from Auto_Module.customLogger import *

"""
NEW IMPORTS--Please Retain this order
"""

from Auto_Module import *
import Auto_Module.export
import Auto_Module.test_suite_export_wd
import Auto_Module.file_tools
import Auto_Module.test_suite_import

import Auto_Module.validation
import Auto_Module.data_upload
import Auto_Module.json_validation
import Auto_Module.ldap_utilities
import Auto_Module.json_extraction
import Auto_Module.output
from Auto_Module import summary

"""
#################################################### Functions ####################################################
"""


def incorta_api_import(incorta_home):
    incorta_module = incorta_home.rstrip() + os.sep + "bin".rstrip()
    sys.path.append(incorta_module)
    import incorta
    global incorta


def login(url, tenant, username, password):
    try:
        return incorta.login(url, tenant, username, password, True)
    except Exception:
        print "Login Failed"
        writeLogMessage("Login Failed", mainLogger, DebugLevel.CRITICAL)
        raise


def logout(session):
    try:
        incorta.logout(session)
    except Exception:
        print 'Failed to logout'
        writeLogMessage('Failed to logout', mainLogger, DebugLevel.CRITICAL)
        raise Exception


def get_test_suite_path(test_suite):
    test_suite_path = os.getcwd() + os.sep + "TestSuites"
    test_suite_path = test_suite_path + os.sep + test_suite
    return test_suite_path


def grant_user_access(session, user_name, entity_type, entity_name, permission):
    try:
        incorta.grant_user_access(session, user_name, entity_type, entity_name, permission)
        print "Access to ", entity_type, " ", entity_name, " given to ", user_name
        writeLogMessage("Access to %s %s given to %s" % (entity_type, entity_name, user_name), mainLogger,
                        DebugLevel.INFO)
    except Exception:
        print "Failed to grant user access to", user_name
        writeLogMessage("Failed to grant user access to %s" % user_name, mainLogger, DebugLevel.CRITICAL)
        return


"""
#################################################### Functions ####################################################
"""

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
        writeLogMessage('%s %s' % (key, value), mainLogger, DebugLevel.DEBUG)

# Set admin username and admin password to local variables
username = admin_user
password = admin_pass
incorta_api_import(incorta_home)  # Import Incorta API
session = login(url, tenant, username, password)  # Login to Incorta
session_id = session[21:53]
csrf_token = session[63:95]
test_suite_directory_path = os.getcwd() + os.sep + "TestSuites"
SourcesMetadata_path = os.getcwd() + os.sep + "SourcesMetadata"
test_suite_directories = Auto_Module.file_tools.get_subdirectories(test_suite_directory_path)
SourcesMetadata_directories = Auto_Module.file_tools.get_subdirectories(SourcesMetadata_path)
user_dict = {}
if config_defaults['skip_ldap'] == 'False':
    # LOAD USERS FROM LDAP
    print "Checking if instance needs to load users"
    writeLogMessage("Checking if instance needs to load users...", mainLogger, DebugLevel.INFO)
    owd = os.getcwd()
    sync = incorta_home + os.sep + 'sync.txt'

    if os.path.isfile(sync):
        print "Users already Loaded"
        writeLogMessage("Users already Loaded", mainLogger, DebugLevel.INFO)
        user_dict = Auto_Module.ldap_utilities.read_users_from_csv(incorta_home)
        user_list = user_dict.keys()
        print "USER LIST: ", user_list
        writeLogMessage("USER LIST: %s" % user_list, mainLogger, DebugLevel.INFO)
    else:
        print "Preparing to populate users from LDAP"
        writeLogMessage("Preparing to populate users from LDAP", mainLogger, DebugLevel.INFO)

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
        user_dict = Auto_Module.ldap_utilities.read_users_from_csv(incorta_home)
        user_list = user_dict.keys()
        print "USER LIST: ", user_list
        writeLogMessage("USER LIST: %s" % user_list, mainLogger, DebugLevel.INFO)
        # END of LDAP Implementation
else:
    user_dict = Auto_Module.ldap_utilities.read_users_from_csv(incorta_home)
    user_list = user_dict.keys()
    print "USER LIST: ", user_list
    writeLogMessage("USER LIST: %s" % user_list, mainLogger, DebugLevel.INFO)

# Import Files from SourcesMetadata folder

for sources in SourcesMetadata_directories:
    print "\nImporting Current Source: %s" % sources
    writeLogMessage("\nImporting Current Source: %s" % sources, mainLogger, DebugLevel.INFO)
    curr_source_path = SourcesMetadata_path + os.sep + sources
    sources_subdirectories = Auto_Module.file_tools.get_subdirectories(curr_source_path)

    print "Current Source Path: ", curr_source_path
    print "Source Sub directories ", sources_subdirectories

    for import_type in sources_subdirectories:
        if import_type == 'datasources':
            datasource_path = curr_source_path + os.sep + import_type
            datasources = Auto_Module.file_tools.get_subdirectories(datasource_path)
            print "Importing DataSources ", datasources
            for datasource in datasources:
                if 'datafiles' in datasource:
                    source_path = datasource_path + os.sep + datasource
                    print "Datafile to be imported", datasource
                    try:
                        incorta.upload_data_file(session, source_path)
                    except Exception:
                        print "Data File already imported ", datasource
                else:
                    source_path = datasource_path + os.sep + datasource
                    print "Datasource to be imported", datasource
                    try:
                        incorta.import_datasources(session, source_path)
                    except Exception:
                        print "Data Source Already Imported", datasource

        if import_type == 'schemas':
            schema_path = curr_source_path + os.sep + import_type
            schemas = Auto_Module.file_tools.get_subdirectories(schema_path)
            print "Importing Schemas ", schemas
            for schema in schemas:
                print "Importing ", schema
                source_path = schema_path + os.sep + schema
                try:
                    incorta.import_schemas(session, source_path)
                except Exception:
                    print Exception("Schema Already Imported")

        if import_type == 'session_variables':
            session_var_path = curr_source_path + os.sep + import_type
            session_variables = Auto_Module.file_tools.get_subdirectories(session_var_path)
            print "Importing Session Variables ", session_variables
            for var in session_variables:
                print "Importing ", var
                source_path = session_var_path + os.sep + var
                try:
                    incorta.import_session_variables(session, source_path)
                except Exception:
                    print Exception("Session Variable ", var, " already imported")

# Run Through Test Suites
for sub_dir in test_suite_directories:
    if sub_dir in config_defaults['run_tests'] or config_defaults['run_tests'] == 'ALL':
        print "Tests to be run: ", config_defaults['run_tests']

        temp_list = config_defaults['run_tests'].split(',')
        run_test_case = []
        for item in temp_list:
            if '/' in item:
                run_test_case.append(item.split('/')[1])
            else:
                run_test_case.append('ALL_test_cases')
        print "Test Cases to be run: ", run_test_case

        loaded_schemas = []
        test_case_name_dict = {}
        meta_data_case_dict = {}

        print "Current Test Suite: ", sub_dir
        writeLogMessage("Current Test Suite: %s" % sub_dir, mainLogger, DebugLevel.INFO)
        test_suite_wd_path = Auto_Module.file_tools.create_directory(wd_path,
                                                                     sub_dir)  # Working Directory test suite path
        test_suite_path = get_test_suite_path(sub_dir)  # Path of test suite
        test_cases_dir = Auto_Module.file_tools.get_subdirectories(test_suite_path)
        temp_path = Auto_Module.file_tools.get_path(test_suite_path, test_cases_dir[0])
        temp_dir = Auto_Module.file_tools.get_subdirectories(temp_path)
        # Creating Output Structure
        test_suite_output_path = Auto_Module.output.create_test_suite_output_folder(output_wd_path, sub_dir)
        # Validation Sub Directories
        Data_Validation_Path, Loader_Validation_Path, XML_MetaData_Validation_Path = Auto_Module.output.create_test_suite_validation_folders(
            test_suite_output_path)

        test_suite_subdirectories = Auto_Module.file_tools.get_subdirectories(test_suite_path)

        if Debug == True:
            print test_suite_subdirectories
            writeLogMessage(test_suite_subdirectories, mainLogger, DebugLevel.DEBUG)
        current_test_suite = sub_dir

        full_schema_export_list = []

        # ENTERING TEST CASES
        for dir in test_suite_subdirectories:  # For loop for each test case inside test suite
            print "Current Test Case: ", dir
            if dir in run_test_case or 'ALL_test_cases' in run_test_case:

                writeLogMessage("Current Test Case: %s" % dir, mainLogger, DebugLevel.INFO)

                # Get path of test_case in test_suite
                test_case_path = Auto_Module.file_tools.get_path(test_suite_path, dir)
                if Debug == True:
                    print test_case_path
                    writeLogMessage(test_case_path, mainLogger, DebugLevel.DEBUG)

                # Get subdirectories of test case
                test_case_subdirectories = Auto_Module.file_tools.get_subdirectories(test_case_path)
                if Debug == True:
                    print test_case_subdirectories
                    writeLogMessage(test_case_subdirectories, mainLogger, DebugLevel.DEBUG)

                # Creates test_suite folder in WD
                test_case_path_wd = Auto_Module.file_tools.create_directory(test_suite_wd_path, dir)
                if Debug == True:
                    print test_case_path_wd
                    writeLogMessage(test_case_path_wd, mainLogger, DebugLevel.DEBUG)

                # Creates Import and Export Folders in WD test case folder
                Auto_Module.test_suite_export_wd.create_standard_directory(test_case_path_wd)

                # Extracts test suite to WD
                Auto_Module.test_suite_export_wd.extract_test_case(test_case_path, test_case_path_wd)

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
                if config_defaults['include_schemas'] == 'True':
                    import_schema_names, import_schema_loaders, import_schema_tenants, import_schema_names_list = Auto_Module.validation.get_schemas_info(
                        import_path)

                # TENANT EDITOR
                Auto_Module.validation.tenant_editor(import_path)

                # EXPORTS
                test_case_wd_subdirectories = Auto_Module.file_tools.get_subdirectories(test_case_path_wd)
                for test_case_wd_dirs in test_case_wd_subdirectories:
                    if 'Output_Files' in test_case_wd_dirs:
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
                if config_defaults['skip_xml_validation'] == 'False':
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

                if config_defaults['skip_data_load'] == 'False':
                    if config_defaults['include_schemas'] == 'True':
                        # Load Data
                        table = None
                        incremental = False
                        snapshot = False
                        staging = False

                        Auto_Module.data_upload.load_data(incorta, session, test_case_path, Loader_Validation_Path)

                # Exported Dashboard ID's per test case
                test_case_dashboard_export_list = export_dash_ids.keys()

                # GRANT PERMISSIONS
                for dashboards in export_dashboard_names_list:
                    print "Preparing to Export: ", dashboards
                    writeLogMessage("Preparing to Export: %s" % dashboards, mainLogger, DebugLevel.INFO)

                for user in user_list:
                    for dashboard_name in export_dashboard_names_list:
                        grant_user_access(session, user, 'dashboard', os.sep + dashboard_name, 'edit')

                # LOG OUT SUPER USER
                time.sleep(2)
                try:
                    logout(session)
                    print "Logged out Super User successfully"
                    writeLogMessage("Logged out Super User successfully", mainLogger, DebugLevel.INFO)
                except Exception:
                    print Exception("unable to logout")
                    writeLogMessage("Unable to Logout", mainLogger, DebugLevel.CRITICAL)
                time.sleep(2)

                # JSON DASHBOARD EXPORT
                if Debug == True:
                    print "session: ", session, " \n\n"
                    writeLogMessage("Session: %s\n\n" % session, mainLogger, DebugLevel.DEBUG)
                    print "session id: ", session_id
                    writeLogMessage("Session id: %s" % session_id, mainLogger, DebugLevel.DEBUG)
                    print "dashboard id: ", test_case_dashboard_export_list
                    writeLogMessage("Dashboard id: %s" % test_case_dashboard_export_list, mainLogger, DebugLevel.DEBUG)
                    print "CSRF TOKEN", csrf_token
                    writeLogMessage("CSRF TOKEN: %s" % csrf_token, mainLogger, DebugLevel.DEBUG)
                    print "Test Case Path", test_case_path_wd
                    writeLogMessage("Test Case Path: %s" % test_case_path_wd, mainLogger, DebugLevel.DEBUG)
                    print "Entering JSON DASH EXPORT"
                    writeLogMessage("Entering JSON DASH EXPORT", mainLogger, DebugLevel.DEBUG)

                # USER TESTING
                if config_defaults['include_user_testing'] == 'True':
                    user_pass = 'superpass'
                    print "TESTING USER LOGIN"
                    writeLogMessage("TESTING USER LOGIN", mainLogger, DebugLevel.INFO)
                    for user in user_list:
                        # Check if test case utilizes user from LDAP
                        test_case_directories = os.listdir(test_case_path)
                        # print "Files for current test case", test_case_directories, " checking with user ", user
                        if user in test_case_directories:
                            session = login(url, tenant, user, user_pass)
                            time.sleep(2)
                            print "Logged in user.. ", user
                            writeLogMessage("Logged in user.. %s" % user, mainLogger, DebugLevel.INFO)
                            session_id = session[21:53]
                            csrf_token = session[63:95]
                            Auto_Module.json_extraction.export_dashboards_json(test_case_path_wd, test_case_path, user,
                                                                               session)
                            logout(session)
                            time.sleep(2)
                            print "Logged out user.. ", user
                            writeLogMessage("Logged out user.. %s" % user, mainLogger, DebugLevel.INFO)

                if Debug == True:
                    print "\nFinished JSON DASH EXPORT"
                    writeLogMessage("Finished JSON DASH EXPORT", mainLogger, DebugLevel.DEBUG)

                # LOGGING IN SUPER USER
                try:
                    time.sleep(2)
                    session = login(url, tenant, username, password)
                    time.sleep(2)
                    print "Logged in Super User"
                    writeLogMessage("Logged in Super User", mainLogger, DebugLevel.INFO)
                except Exception:
                    print "Unable to log in Super User"
                    writeLogMessage("Unable to log in Super User", mainLogger, DebugLevel.CRITICAL)

                if Debug == True:
                    print "\nFinished JSON DASH EXPORT"
                    writeLogMessage("Finished JSON DASH EXPORT", mainLogger, DebugLevel.DEBUG)
                # DATA VALIDATION
                if config_defaults['skip_validation'] == 'False':
                    for user in user_list:
                        print "Validating data for user - ", user, " test case - ", dir
                        writeLogMessage(("Validating data for user - %s test case - %s" % (user, dir)), mainLogger,
                                        DebugLevel.INFO)
                        output_test_case_path = Data_Validation_Path + os.sep + dir
                        output_user_path = Auto_Module.output.create_output_user_path(output_test_case_path, user)
                        Auto_Module.json_validation.validation(test_case_path, test_case_path_wd, output_wd_path,
                                                               current_test_suite, output_user_path, user)
                        # Removes user folders not being tested within the current test case
                        if os.listdir(output_user_path) == []:
                            os.rmdir(output_user_path)
                print "SEARCHING FOR SUCS AND DIFFS"
                writeLogMessage('SEARCHING FOR SUCS AND DIFFS', mainLogger, 'info')
                test_case_name_dict[dir] = Auto_Module.output.data_validation_generate_suc_dif_file_names(
                    Data_Validation_Path,
                    dir)
                meta_data_case_dict[
                    'dashboard_tenants'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(
                    XML_MetaData_Validation_Path, 'dashboard_tenants')
                meta_data_case_dict['dashboards'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(
                    XML_MetaData_Validation_Path, 'dashboards')
                meta_data_case_dict[
                    'schema_loaders'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(
                    XML_MetaData_Validation_Path, 'schema_loaders')
                meta_data_case_dict[
                    'schema_tenants'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(
                    XML_MetaData_Validation_Path, 'schema_tenants')
                meta_data_case_dict['schemas'] = Auto_Module.output.meta_data_validation_generate_suc_dif_file_names(
                    XML_MetaData_Validation_Path, 'schemas')

        loader_valid_dict[sub_dir] = Auto_Module.output.loader_validation_generate_suc_dif_file_names(
            Loader_Validation_Path)
        test_suite_name_dict[sub_dir] = test_case_name_dict
        metadata_suite_dict[sub_dir] = meta_data_case_dict
        test_suite_name_list.append(sub_dir)

minute_timer_custom = (time.time() - start_time) / 60
seconds_timer_custom = time.time() - start_time
time_list = [seconds_timer_custom, minute_timer_custom]
summary.print_table(*time_list, **test_suite_name_dict)
time.sleep(2)
shutdown_logger(mainLogger)
shutdown_logger(summaryLogger)
