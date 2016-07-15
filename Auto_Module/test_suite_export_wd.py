import os
import zipfile

"""
Exports test suites to working directory

TODO
    Add log file dump for failure and success of each import and extract
    Need more try/catch handling
"""
Debug = True  #Debug flag for print statements

def extract_test_suites(test_suite_path, subdirectories):
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
    print test_suite_path
    extract_test_suite_path = test_suite_path + '/pre_export_files'
    python_work_dir = os.getcwd()
    print extract_test_suite_path
    extension = '.zip'
    for root, dirs, files in os.walk(test_suite_path):
        for file in files:
            if file.endswith(extension):
                file_raw_dir = os.path.join(root, file)
                zip_ref = zipfile.ZipFile(file_raw_dir)
                zip_ref.extractall(extract_test_suite_path)
                zip_ref.close()
