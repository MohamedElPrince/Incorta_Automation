import os, sys
import zipfile
"""
Exports test suites to working directory
Imports datafiles / schemas / dashboards to Incorta

TODO
    Add log file dump for failure and success of each import and extract
    Need more try/catch handling
"""
Debug = False  #Debug flag for print statements

def import_datafiles(incorta, session, test_case_path):
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
    extension = '.zip'
    upload_check = []
    for root, dirs, files in os.walk(test_case_path):
        for file in files:
            if 'datafile' in file:
                if file.endswith(extension):
                    file_full_path = os.path.join(root, file)
                    upload_check.append(incorta.upload_data_file(session, file_full_path))
    if Debug == True:
        for checks in upload_check:
            print checks,

def import_schema(incorta, session, test_case_path):
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
    extension = '.zip'
    upload_check = []
    for root, dirs, files in os.walk(test_case_path):
        for file in files:
            if 'schema' in file:
                if file.endswith(extension):
                    file_full_path = os.path.join(root, file)
                    upload_check.append(incorta.import_tenant(session, file_full_path, True))
    if Debug == True:
        for checks in upload_check:
            print checks,

def import_dashboard(incorta, session, test_case_path):
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
    extension = '.zip'
    upload_check = []
    for root, dirs, files in os.walk(test_case_path):
        for file in files:
            if 'dashboard' in file:
                if file.endswith(extension):
                    file_full_path = os.path.join(root, file)
                    upload_check.append(incorta.import_tenant(session, file_full_path, True))
    if Debug == True:
        for checks in upload_check:
            print checks,
