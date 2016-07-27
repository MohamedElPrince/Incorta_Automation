import json, file_tools, os
"""
"""
def getContentFromFile(filepath):
    """
    """
    try:
        with open(filepath, 'r') as data_file:
            file_data = json.load(data_file)
        return file_data
    except Exception, e:
        print "ERROR Unable to Open JSON File: ", filepath


def ordered(obj):
    """
    """
    if isinstance(obj, dict):
        return sorted((k, ordered(v)) for k, v in obj.items())
    if isinstance(obj, list):
        return sorted(ordered(x) for x in obj)
    else:
        return obj

def comp(list1, list2):
    """
    """
    new_list = []
    new_list2 = []
    for element in list1:
        if element not in list2:
            new_list.append(element)
    for element in list2:
        if element not in list1:
            new_list2.append(element)
    return new_list, new_list2

def get_paths(test_case_path, test_case_path_wd):
    """
    """
    test_case_json_dict = {}
    test_case_wd_json_dict = {}

    test_case_subdirectories = file_tools.get_subdirectories(test_case_path)
    for dirs in test_case_subdirectories:
        if 'admin' in dirs:
            admin_path = file_tools.get_path(test_case_path, dirs)
            for files in os.listdir(admin_path):
                file_path = file_tools.get_path(admin_path, files)
                test_case_json_dict[(os.path.splitext(files)[0])] = file_path

    test_case_wd_subdirectories = file_tools.get_subdirectories(test_case_path_wd)
    for dirs_wd in test_case_wd_subdirectories:
        if 'admin' in dirs_wd:
            admin_wd_path = file_tools.get_path(test_case_path_wd, dirs_wd)
            for files_wd in os.listdir(admin_wd_path):
                file_wd_path = file_tools.get_path(admin_path, files_wd)
                test_case_wd_json_dict[(os.path.splitext(files_wd)[0])] = file_wd_path

    return test_case_json_dict, test_case_wd_json_dict

def validation(test_case_path, test_case_wd_path):
    """
    """
    test_case_json_dict, test_case_wd_json_dict = get_paths(test_case_path, test_case_wd_path)
    for (key, value), (key2, value2) in zip(test_case_json_dict.items(), test_case_wd_json_dict.items()):
        print key , key2
        file_data1 = getContentFromFile(value)
        file_data2 = getContentFromFile(value2)
        result1 = ordered(file_data1)
        result2 = ordered(file_data2)
        print result1 == result2
        comparison1, comparison2 = comp(result1, result2)
        print comparison1, comparison2




    # test_case_dict_path = test_case_json_dict.values()
    # test_case_wd_dict_path = test_case_wd_json_dict.values()
    # print test_case_dict_path
    # print test_case_wd_dict_path
    # for paths in test_case_dict_path:
    #     file_data = getContentFromFile(paths)
    #     obj = ordered(file_data)
    #     print obj

    #test_case_data, test_case_wd_data = getContentFromFile(test_case_json_dict.values(), test_case_wd_json_dict.values())










# file1 = '/Users/anahit/desktop/admin/98e77650-bd45-45dd-b577-b447a781f8c0.json'
# file2 = '/Users/anahit/desktop/admin-changed/98e77650-bd45-45dd-b577-b447a781f8c0.json'
#
# data, data2 = getContentFromFile(file1, file2)
#
# result1 = ordered(data)
# result2 = ordered(data2)
#
# print result1
# print result2
#
# comparison, comparison2 = comp(result1, result2)
# print "COMPARISON"
# print comparison
# print comparison2
#
#
# if (ordered(data) == ordered(data2)) == True:
#     print True
# else:
#     print False


