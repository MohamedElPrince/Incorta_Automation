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
    Function creates directory
        args:
            path: any path can be given
            folder_name: name of the directory
        returns:
            The path of the new directory
        prints:
            Nothing
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
    Function gets all directories in a path
        args:
            path: any path can be given
        returns:
            List of the subdirectories
        prints:
            Nothing
    """
    subdirectories = os.listdir(path)
    for x in subdirectories:
        if x == '.DS_Store':
            subdirectories.remove(x)
    return subdirectories

def get_path (path, directory):
    """
    Function can be used to return a path, does not create the directory
        args:
            path: any path can be given
            folder_name: name of the directory
        returns:
            The path of the new directory
        prints:
            Nothing
    """
    appended_path = path + os.sep + directory
    return appended_path

def convert_dict_keys_to_list(dictionary):
	"""
	Converts Keys of Dictionary to List
	"""

	list = dictionary.keys()
	return list


def convert_dict_values_to_list(dictionary):
	"""
	Converts Values of Dictionary to List
	"""

	list = dictionary.values()
	return list