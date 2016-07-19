import os, errno

"""
Tools and utilities for file and folder manipulation

TODO
    Add log file dump for failure and success
    Need more try/catch handling
"""
Debug = True  #Debug flag for print statements

def create_directory(path, folder_name):
    """
    Documentation to come
    Anahit Sarao
    """
    appended_path = path + os.sep + folder_name
    try:
        os.makedirs(appended_path)
        return appended_path
    except OSError as exc:
        if exc.errno == errno.EEXIST and os.path.isdir(appended_path):
            pass
        else:
            raise

def get_subdirectories(path):
    """
    Documentation to come
    Anahit Sarao
    """
    subdirectories = os.listdir(path)
    for x in subdirectories:
        if x == '.DS_Store':
            subdirectories.remove(x)
    return subdirectories

def create_subdirectories_wd(path, subdirectory):
    """
    Documentation to come
    Anahit Sarao
    """
    try:
            os.makedirs(path + os.sep + subdirectory)
    except OSError as exc:
        if exc.errno == errno.EEXIST and os.path.isdir(path + os.sep + dir):
            pass
        else:
            raise

def get_path (path, directory):
    """
    Documentation to come
    Anahit Sarao
    """
    appended_path = path + os.sep + directory
    return appended_path