import sys, os, subprocess, time, zipfile
import os.path
from sys import argv
from shutil import copyfile

import errno

# import Auto_Module
# from Auto_Module import dataLoad
# from Auto_Module import loadUsers
# from Auto_Module import test_suite_import
# from Auto_Module import export

import Auto_Module.export
import Auto_Module.test_suite_export_wd
import Auto_Module.file_tools
import Auto_Module.test_suite_import
from Auto_Module import *
import Auto_Module.validation
import Auto_Module.data_upload
import Auto_Module.json_validation





"""
------------------------------------------Initialization----------------------------------------
"""

Debug = True  # Debug flag for print statements

"""
Arguments
-------------------------------------------
-d: import datasource/schema/dashboard
-l: load data
-x: extract files
-------------------------------------------
"""
# All the arguments which are passed in command line
# sys.argv[0:] = argv
#
# print "argv = %r" % argv
# print "sysargv = %r" % sys.argv[0:]



# sys.argv[1] is the config file
config_file = sys.argv[1]

# Includes rest of arguments passed by user
commands = sys.argv[2:]

config_defaults = {'incorta_home': '/home/Incorta', 'tenant_home': '/home/tenants',
                   'admin': 'Super', 'password': 'none', 'load_users': 'No', 'test_suite': 'CSV_DataSource',
                   'skip_validation': 'False', 'import_object': 'False', 'data_load': 'False',
                   'extract_csv': 'False', 'wd_path': '~/IncortaTesting/tmp/work', 'tenant': 'Demo',
                   'url': 'http://localhost:8080/incorta'}

# config_defaults will hold all of the keys from the above dictionary
# The values of the keys in config_defaults will be parsed from the config file


"""
------------------------------------------Initialization----------------------------------------
"""

"""
#################################################### Functions ####################################################
"""


def set_block_defaults(commands):
    """
    Function checks if an import, data load, or extract file argument is passed after the config file
    If so, the key corresponding to the action will be set to True
        args:
            commands: the arguments after the config file
        returns:
            Nothing
        prints:
            Nothing
    """
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
    """
    Function parses config file for keys and values and stores them in new_config_defualts
        args:
            config_file: config file holds keys and values to be used
        returns:
            Nothing
        prints:
            Nothing
    """
    f = open(config_file, "r")
    lines = f.readlines()
    f.close()
    for line in lines:
        for key, value in config_defaults.items():
            if line[0] == '#':
                pass
            elif key in line:
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
        add_time_stamp_to_wd(timestamp)


def add_time_stamp_to_wd(timestamp):
    """
    Function adds a timestamp to the end of the working directory path
        args:
            timestamp: MM/DD/YY-HR/MIN/SEC
        returns:
            Nothing
        prints:
            Nothing
    """
    date_and_time = str(time.strftime("%m:%d:%Y-%H:%M:%S"))
    config_defaults['wd_path'] += '/%s' % date_and_time


def incorta_api_import(incorta_home):
    """
    Function takes the incorta installation path to import Incorta API
        args:
            incorta_home: Incorta Home Directory Path
        returns:
            Nothing
        prints:
            Nothing
    """
    incorta_module = incorta_home.rstrip() + os.sep + "bin".rstrip()
    sys.path.append(incorta_module)
    import incorta
    global incorta


def login(url, tenant, admin, password):
    """
    Function takes in login information and attempts to login through Incorta API
    	args:
    		url: Url for the Incorta instance
    		tenant: Tenant name for instance
    		admin: Username for instance
    		password: Password for instance
    	returns:
            The session for the Incorta instance is returned
    	prints:
            Handles exception case of login fails
    """
    try:
        return incorta.login(url, tenant, admin, password, True)
    except Exception, e:
        print "Login Failed"
        exit(1)


def logout(session):
    """
    Function logs out of the instance of Incorta
    	args:
    		session: session var returned by login function
    	returns:
            Nothing
    	prints:
            Handles exception case of login fails
    """
    try:
        incorta.logout(session)
    except Exception, e:
        print 'Failed to logout'
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

"""
#################################################### Functions ####################################################
"""

set_block_defaults(commands)
set_new_defaults(config_file)

# for key, value in config_defaults.items():
#     print(key, value)

print config_defaults['skip_validation']

if Debug == False:
    for key, value in config_defaults.items():
        print(key, value)

# converts keys in a dictionary to variables
locals().update(config_defaults)

incorta_api_import(incorta_home)    #Import Incorta API

session = login(url, tenant, admin, password)   # Login to Incorta
session_id = session[21:53]
csrf_token = session [63:95]
test_suite_directory_path = os.getcwd() + '/' + "TestSuites"

test_suite_directories = Auto_Module.file_tools.get_subdirectories(test_suite_directory_path)
output_wd_path = Auto_Module.file_tools.create_directory(wd_path, 'Output')
admin_wd_path = Auto_Module.file_tools.create_directory(output_wd_path, 'admin')

for sub_dir in test_suite_directories:

    print "Current Test Suite: ", sub_dir

    test_suite_wd_path = Auto_Module.file_tools.create_directory(wd_path, sub_dir)   #Working Directory test suite path
    test_suite_path = get_test_suite_path(sub_dir)   #Path of test suite
    test_cases_dir = Auto_Module.file_tools.get_subdirectories(test_suite_path)
    temp_path = Auto_Module.file_tools.get_path(test_suite_path, test_cases_dir[0])
    temp_dir = Auto_Module.file_tools.get_subdirectories(temp_path)
    for names in temp_dir:



        if 'datafiles' == names:

            print "Entering datafiles"
            test_suite_subdirectories = Auto_Module.file_tools.get_subdirectories(test_suite_path)

            if Debug == False:
                print test_suite_subdirectories

            current_test_suite = sub_dir

            full_schema_export_list = []
            # ENTERING TEST CASES
            for dir in test_suite_subdirectories:  # For loop for each test case inside test suite
                # Get path of test_case in test_suite

                print "Current Test Case: ", dir

                test_case_path = Auto_Module.file_tools.get_path(test_suite_path, dir)
                if Debug == False:
                    print test_case_path

                # Get subdirectories of test case
                test_case_subdirectories = Auto_Module.file_tools.get_subdirectories(test_case_path)
                if Debug == False:
                    print test_case_subdirectories

                # Creates test_suite folder in WD
                test_case_path_wd = Auto_Module.file_tools.create_directory(test_suite_wd_path, dir)
                if Debug == False:
                    print test_case_path_wd

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

                import_path, export_path = Auto_Module.validation.grab_import_export_path(test_case_path_wd)

                # Define Import DATA STRUCTURES

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
                        # if 'Import_Files' in test_case_wd_dirs:

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


                # VALIDATION IMPLEMENTATION

                if config_defaults['skip_validation'] == 'False':

                    # Comparing Dashboard Items
                    Auto_Module.validation.validation(import_dash_ids, export_dash_ids, wd_path, current_test_suite,
                                                      'dashboards')
                    Auto_Module.validation.validation(import_dash_tenants, export_dash_tenants, wd_path, current_test_suite,
                                                      'dashboard_tenants')

                    # Comparing Schema Items
                    Auto_Module.validation.validation(import_schema_names, export_schema_names, wd_path, current_test_suite,
                                                      'schemas')
                    Auto_Module.validation.validation(import_schema_loaders, export_schema_loaders, wd_path,
                                                      current_test_suite, 'schema_loaders')
                    Auto_Module.validation.validation(import_schema_tenants, export_schema_tenants, wd_path,
                                                      current_test_suite, 'schema_tenants')

                # Load Data
                table = None
                incremental = False
                snapshot = False
                staging = False
                Auto_Module.data_upload.Load_data(incorta, session, export_schema_names_list)

                # LOAD VALIDATION
                # Appends to list of loaded schemas as for loop goes through every test case
                # full_schema_export_list.extend(export_schema_names_list)
                # schema_list = Auto_Module.data_upload.load_validator(incorta_home, export_schema_names_list,
                #                                                      full_schema_export_list)

                # JSON DASHBOARD EXPORT
                test_case_dashboard_export_list = export_dash_ids.keys()
                if Debug == False:
                    print "session: ", session, " \n\n"
                    print "session id: ", session_id
                    print "dashboard id: ", test_case_dashboard_export_list
                    print "CSRF TOKEN", csrf_token
                    print "Test Case Path", test_case_path_wd
                    print "Entering JSON DASH EXPORT"

                Auto_Module.export.export_dashboards_json(session_id, test_case_dashboard_export_list, csrf_token,
                                                          test_case_path_wd, test_case_path)

                if Debug == False:
                    print "\nFinished JSON DASH EXPORT"


                if config_defaults['skip_validation'] == 'False':

                    print "JSON Validation"
                    Auto_Module.json_validation.validation(test_case_path, test_case_path_wd, output_wd_path, current_test_suite, admin_wd_path)

                # LOAD VALIDATION

                # Appends to list of loaded schemas as for loop goes through every test case
                full_schema_export_list.extend(export_schema_names_list)
                schema_list = Auto_Module.data_upload.load_validator(incorta_home, export_schema_names_list,
                                                                     full_schema_export_list)

            Auto_Module.data_upload.schema_load_validatior(schema_list, full_schema_export_list)

            # TO BE USED FOR DEBUGGING PURPOSES
            # print "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"
            #
            #
            # print "Extracted from IMPORTS\n"
            #
            #
            # print "-------Dashboards--------\n"
            # print "Imported dashboards: \n", import_dash_ids
            # print "Imported Dashboard Tenants: \n", import_dash_tenants
            # print "\n-------Schemas---------\n"
            # print "Imported Schemas: \n", import_schema_names
            # print "Imported Schema Loaders: \n", import_schema_loaders
            # print "Imported Schema Tenants: \n", import_schema_tenants
            #
            # print "\nPrinting Dashboard Names \n"
            # print import_dashboard_names_list
            # print "\n Printing Schema Names List \n"
            # print import_schema_names_list
            #
            #
            # print "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"
            # print "\nExtracted from EXPORTS\n"
            # print "-------Dashboards--------\n"
            # print "Exported dashboards: \n", export_dash_ids
            # print "Exported Dashboard Tenants: \n", export_dash_tenants
            # print "\n-------Schemas---------\n"
            # print "Exported Schemas: \n", export_schema_names
            # print "Exported Schema Loaders: \n", export_schema_loaders
            # print "Exported Schema Tenants: \n", export_schema_tenants
            #
            # print "\nPrinting Dashboard Names \n"
            # print export_dashboard_names_list
            # print "\n Printing Schema Names List \n"
            # print export_schema_names_list
            #
            # print "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"
            # session_id = session[21:53]

        if 'datasources' == names:
            print names
            test_suite_subdirectories = Auto_Module.file_tools.get_subdirectories(test_suite_path)

            if Debug == False:
                print test_suite_subdirectories

            current_test_suite = sub_dir

            full_schema_export_list = []
            # ENTERING TEST CASES
            for dir in test_suite_subdirectories:  # For loop for each test case inside test suite
                # Get path of test_case in test_suite


                test_case_path = Auto_Module.file_tools.get_path(test_suite_path, dir)
                if Debug == False:
                    print test_case_path

                # Get subdirectories of test case
                test_case_subdirectories = Auto_Module.file_tools.get_subdirectories(test_case_path)
                if Debug == False:
                    print test_case_subdirectories

                # Creates test_suite folder in WD
                test_case_path_wd = Auto_Module.file_tools.create_directory(test_suite_wd_path, dir)
                if Debug == False:
                    print test_case_path_wd

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

                import_path, export_path = Auto_Module.validation.grab_import_export_path(test_case_path_wd)

                # IMPORT DATA STRUCTURES

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
                        # if 'Import_Files' in test_case_wd_dirs:

                # EXPORT DATA STRUCTURES

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

                # VALIDATION IMPLEMENTATION

                # Comparing Dashboard Items
                Auto_Module.validation.validation(import_dash_ids, export_dash_ids, wd_path, current_test_suite,
                                                  'dashboards')
                Auto_Module.validation.validation(import_dash_tenants, export_dash_tenants, wd_path, current_test_suite,
                                                  'dashboard_tenants')

                # Comparing Schema Items
                Auto_Module.validation.validation(import_schema_names, export_schema_names, wd_path, current_test_suite,
                                                  'schemas')
                Auto_Module.validation.validation(import_schema_loaders, export_schema_loaders, wd_path,
                                                  current_test_suite, 'schema_loaders')
                Auto_Module.validation.validation(import_schema_tenants, export_schema_tenants, wd_path,
                                                  current_test_suite, 'schema_tenants')

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
            Auto_Module.data_upload.schema_load_validatior(schema_list, full_schema_export_list)