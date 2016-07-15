import os
import zipfile
"""
Exports test suites to working directory

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

def create_subdirectories_wd(test_suite_path, subdirectories):
    print subdirectories
    print test_suite_path