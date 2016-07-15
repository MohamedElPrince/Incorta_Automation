import os, sys
import zipfile
"""
Exports test suites to working directory
Imports datafiles / schemas / dashboards to Incorta

TODO
    Add log file dump for failure and success of each import and extract
    Need more try/catch handling
"""
Debug = True  #Debug flag for print statements

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
    wd_path = wd_path + '/pre_export_files'
    python_work_dir = os.getcwd()
    test_suite_path = python_work_dir + '/' + test_suite
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
    test_suite_path = python_work_dir + '/' + test_suite
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
    test_suite_path = python_work_dir+ '/' + test_suite
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
    test_suite_path = python_work_dir + '/' + test_suite
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
