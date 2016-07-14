import os, sys
import zipfile
"""
Exports test suites to working directory
Imports datafiles / schemas / dashboards to Incorta

TODO
    Add log file dump for failure and success of each import and extract
    Need more try/catch handling
"""
Debug = True   #Debug flag for print statements

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

def incorta_import(incorta_home):
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

def extract_test_suites(wd_path, test_suite):
    """
    Function extracts all files inside test suit to the working directory
    	args:
    		wd_path: working directory path
    		test_suite: test suite name
    	returns:
            Nothing
    	prints:
            Nothing
    """
    #os.chdir('..')
    python_work_dir = os.getcwd()
    parent_dir = os.path.abspath(python_work_dir + "/../")
    test_suite_path = parent_dir + '/' + test_suite
    extension = '.zip'
    for root, dirs, files in os.walk(test_suite_path):
        for file in files:
            if file.endswith(extension):
                file_raw_dir = os.path.join(root, file)
                zip_ref = zipfile.ZipFile(file_raw_dir)
                zip_ref.extractall(wd_path)
                zip_ref.close()

def import_datafiles(incorta, session, test_suite):
    """
    Function imports all data files to Incorta from the designated test suite
    	args:
    		session: session var returned by login function
    		test_suite: test suite name from config file
    	returns:
            Nothing
    	prints:
            Nothing
    """
    python_work_dir = os.getcwd()
    parent_dir = os.path.abspath(python_work_dir + "/../")
    test_suite_path = parent_dir + '/' + test_suite
    extension = '.zip'
    upload_check = []
    for root, dirs, files in os.walk(test_suite_path):
        for file in files:
            if 'datafile' in file:
                if file.endswith(extension):
                    file_full_path = os.path.join(root, file)
                    upload_check.append(incorta.upload_data_file(session, file_full_path))
    if Debug == True:
        for checks in upload_check:
            print checks,

def import_schema(incorta, session, test_suite):
    """
    Function imports all schemas to Incorta from the designated test suite
    	args:
    		session: session var returned by login function
    		test_suite: test suite name from config file
    	returns:
            Nothing
    	prints:
            Nothing
    """
    python_work_dir = os.getcwd()
    parent_dir = os.path.abspath(python_work_dir + "/../")
    test_suite_path = parent_dir + '/' + test_suite
    extension = '.zip'
    upload_check = []
    for root, dirs, files in os.walk(test_suite_path):
        for file in files:
            if 'schema' in file:
                if file.endswith(extension):
                    file_full_path = os.path.join(root, file)
                    upload_check.append(incorta.import_tenant(session, file_full_path, True))
    if Debug == True:
        for checks in upload_check:
            print checks,

def import_dashboard(incorta, session, test_suite):
    """
    Function imports all dashboards to Incorta from the designated test suite
    	args:
    		session: session var returned by login function
    		test_suite: test suite name from config file
    	returns:
            Nothing
    	prints:
            Nothing
    """
    python_work_dir = os.getcwd()
    parent_dir = os.path.abspath(python_work_dir + "/../")
    test_suite_path = parent_dir + '/' + test_suite
    extension = '.zip'
    upload_check = []
    for root, dirs, files in os.walk(test_suite_path):
        for file in files:
            if 'dashboard' in file:
                if file.endswith(extension):
                    file_full_path = os.path.join(root, file)
                    upload_check.append(incorta.import_tenant(session, file_full_path, True))
    if Debug == True:
        for checks in upload_check:
            print checks,

# Temporary Definitions
test_suite = 'CSV_DataSources'
wd_path = "/Users/anahit/IncortaTesting/tmp/work/testingonly/"

# Calls each function for testing in order
incorta_import("/Users/anahit/Incorta Analytics")

session = login('http://localhost:8080/incorta/', 'super', 'super', 'super')

extract_test_suites(wd_path, test_suite)

import_datafiles(session, test_suite)

import_schema(session, test_suite)

import_dashboard(session, test_suite)
