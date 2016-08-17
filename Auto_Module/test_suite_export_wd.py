import os, zipfile, file_tools
from customLogger import mainLogger, writeLogMessage
"""
Exports test cases from a test suite to working directory
Retains same file structure of the test case and suite to working directory

TODO
    Add log file dump for failure and success of each import and extract
    Need more try/catch handling
"""
Debug = True  # Debug flag for print statements

def extract_test_case(test_case_path, test_case_path_wd):
    """
    Function extracts all files inside test case to the working directory
        args:
            test_case_path: test case directory path
            test_case_path_wd: test case working directory path
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """
    if Debug == False:
        print test_case_path, test_case_path_wd
        writeLogMessage('%s %s' % (test_case_path, test_case_path_wd), mainLogger, 'debug')

    # Need to fix this hard code
    test_case_path_wd = test_case_path_wd + os.sep + 'Import_Files'
    extension = '.zip'
    test_case_subdirectories = file_tools.get_subdirectories(test_case_path)
    for dir in test_case_subdirectories:
        if 'datafiles' != dir:
            test_case_subdirectories_path = file_tools.get_path(test_case_path, dir)
            test_case_subdirectories_wd = file_tools.create_directory(test_case_path_wd, dir)
            for files in os.listdir(test_case_subdirectories_path):
                if not files.startswith('.'):
                    if files.endswith(extension):
                        file_name = os.path.splitext(files)[0]
                        file_path_wd = file_tools.create_directory(test_case_subdirectories_wd, file_name)
                        file_raw_path = os.path.join(test_case_subdirectories_path, files)
                        zip_ref = zipfile.ZipFile(file_raw_path)
                        zip_ref.extractall(file_path_wd)
                        zip_ref.close()


def create_standard_directory(test_case_path_wd):
    """
    Function adds standard folders: Export_Files and Import_Files to working directory
    Also handles exception case
    args:
        test_case_path_wd: test case working directory path
    returns:
        Nothing
    prints:
        Nothing
    """
    try:
        file_tools.create_directory(test_case_path_wd, 'Export_Files')
        file_tools.create_directory(test_case_path_wd, 'Import_Files')
    except OSError as e:
        raise Exception('Can not Create Export/Import Folders')
