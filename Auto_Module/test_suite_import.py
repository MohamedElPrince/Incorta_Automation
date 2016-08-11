import os, file_tools

"""
Imports datafiles / schemas / dashboards to Incorta

TODO
    Add log file dump for failure and success of each import and extract
    Need more try/catch handling
"""
Debug = False  # Debug flag for print statements

def import_datasources(incorta, session, test_case_path):
    """
    """
    extension = '.zip'
    upload_check = []
    test_case_subdirectory = file_tools.get_subdirectories(test_case_path)
    for dirs in test_case_subdirectory:
        if 'datasources' in dirs:
            test_case_subdirectory_path = file_tools.get_path(test_case_path, dirs)
            for files in os.listdir(test_case_subdirectory_path):
                if not files.startswith('.'):
                    if files.endswith(extension):
                        file_full_path = os.path.join(test_case_subdirectory_path, files)
                        upload_check.append(incorta.import_tenant(session, file_full_path, True))
    if Debug == True:
        for checks in upload_check:
            print checks,

def import_datafiles(incorta, session, test_case_path):
    """
    Function imports all data files to Incorta from the designated test suite
        args:
            incorta: Incorta API module
            session: session var returned by login function
            test_case_path: test suite directory path
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """
    extension = '.zip'
    upload_check = []
    test_case_subdirectory = file_tools.get_subdirectories(test_case_path)
    for dirs in test_case_subdirectory:
        if 'datafiles' in dirs:
            test_case_subdirectory_path = file_tools.get_path(test_case_path, dirs)
            for files in os.listdir(test_case_subdirectory_path):
                if not files.startswith('.'):
                    if files.endswith(extension):
                        file_full_path = os.path.join(test_case_subdirectory_path, files)
                        upload_check.append(incorta.upload_data_file(session, file_full_path))
    if Debug == True:
        for checks in upload_check:
            print checks,

def import_schema(incorta, session, test_case_path):
    """
    Function imports all schemas to Incorta from the designated test suite
        args:
            incorta: Incorta API module
            session: session var returned by login function
            test_case_path: test suite directory path
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """
    extension = '.zip'
    upload_check = []
    test_case_subdirectory = file_tools.get_subdirectories(test_case_path)
    for dirs in test_case_subdirectory:
        if 'schema' in dirs:
            test_case_subdirectory_path = file_tools.get_path(test_case_path, dirs)
            for files in os.listdir(test_case_subdirectory_path):
                if not files.startswith('.'):
                    if files.endswith(extension):
                        file_full_path = os.path.join(test_case_subdirectory_path, files)
                        upload_check.append(incorta.import_tenant(session, file_full_path, True))
    if Debug == True:
        for checks in upload_check:
            print checks

def import_dashboard(incorta, session, test_case_path):
    """
    Function imports all dashboards to Incorta from the designated test suite
        args:
            incorta: Incorta API module
            session: session var returned by login function
            test_case_path: test suite directory path
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """
    extension = '.zip'
    upload_check = []
    test_case_subdirectory = file_tools.get_subdirectories(test_case_path)
    for dirs in test_case_subdirectory:
        if 'dashboards' in dirs:
            test_case_subdirectory_path = file_tools.get_path(test_case_path, dirs)
            for files in os.listdir(test_case_subdirectory_path):
                if not files.startswith('.'):
                    if files.endswith(extension):
                        file_full_path = os.path.join(test_case_subdirectory_path, files)
                        upload_check.append(incorta.import_tenant(session, file_full_path, True))
    if Debug == True:
        for checks in upload_check:
            print checks,
