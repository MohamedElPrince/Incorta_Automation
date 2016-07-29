import codecs
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

def compare_json(x,y):
    for x_key in x:
        if x_key in y and x[x_key] == y[x_key]:
            print 'Match'
        else:
            print 'Not a match'
    if any(k not in x for k in y):
        print 'Not a match'

def printDiffs(x,y):
    diff = False
    for x_key in x:
        if x_key not in y:
            diff = True
            print "key %s in x, but not in y" %x_key
        elif x[x_key] != y[x_key]:
            diff = True
            print "key in x and in y, but values differ (%s in x and %s in y)" %(x[x_key], y[x_key])
    if not diff:
        print "both files are identical"

def dict_compare(d1, d2):
    d1_keys = set(d1.keys())
    d2_keys = set(d2.keys())
    intersect_keys = d1_keys.intersection(d2_keys)
    added = d1_keys - d2_keys
    removed = d2_keys - d1_keys
    modified = {o : (d1[o], d2[o]) for o in intersect_keys if d1[o] != d2[o]}
    same = set(o for o in intersect_keys if d1[o] == d2[o])
    return added, removed, modified, same

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
                if files.startswith('.'):
                    pass
                else:
                    file_path = file_tools.get_path(admin_path, files)
                    test_case_json_dict[(os.path.splitext(files)[0])] = file_path

    test_case_wd_subdirectories = file_tools.get_subdirectories(test_case_path_wd)
    for dirs_wd in test_case_wd_subdirectories:
        if 'admin' in dirs_wd:
            admin_wd_path = file_tools.get_path(test_case_path_wd, dirs_wd)
            for files_wd in os.listdir(admin_wd_path):
                if files_wd.startswith('.'):
                    pass
                else:
                    file_wd_path = file_tools.get_path(admin_wd_path, files_wd)
                    test_case_wd_json_dict[(os.path.splitext(files_wd)[0])] = file_wd_path

    return test_case_json_dict, test_case_wd_json_dict

def validation(test_case_path, test_case_wd_path, output_wd_path, test_suite, admin_wd_path):
    """
    """

    #test_case_json_dict, test_case_wd_json_dict = get_paths(test_case_path, test_case_wd_path)
    #print test_case_json_dict
    #temp = output_wd_path + os.sep + test_suite + '_Summary'
    #file_tools.create_directory(temp, 'admin')
    #print test_case_wd_json_dict
    for (key, value), (key2, value2) in zip(test_case_json_dict.items(), test_case_wd_json_dict.items()):
        file_data1 = getContentFromFile(value)
        file_data2 = getContentFromFile(value2)
        result1 = ordered(file_data1)
        result2 = ordered(file_data2)

        temp_path_list = test_case_json_dict[key].split('/')
        temp_name = (temp_path_list[10].split('.'))[0]
        file_name = temp_path_list[7] + '_' + temp_path_list[8] + '_' + temp_name
        file_path = admin_wd_path + os.sep + file_name
        #print file_name
        if value2 != None:
            if (result1 == result2) == True:
                file_path = file_path + '.suc'
                try:
                    sucFile = open(file_path, 'w')
                    sucFile.close()
                except Exception, e:
                    print "Error Unable to Create SUC File"
            else:
                file_path = file_path + '.diff'
                diffFile = open(file_path, 'w')
                header_string = 'Imported File: \n' + test_case_json_dict[key] + '\nExported File: \n' + \
                                test_case_wd_json_dict[key]
                diffFile.write(header_string)
                diffFile.write('\n\n')
                header2_string = "\n\n Outputting Differences.... \n\n"
                diffFile.write(header2_string)
                header3_string = ">>> IMPORT CONTENT"
                header_newline = '\n'
                header4_string = "<<< EXPORT CONTENT"
                diffFile.write(header3_string)
                diffFile.write(header_newline)
                diffFile.write(header4_string)
                diffFile.write('\n\n')
                comparison1, comparison2 = comp(result1, result2)
                temp_string = '>>> ' + str(comparison1) + '\n'
                temp_string2 = '<<< ' + str(comparison2) + '\n'
                diffFile.write(temp_string)
                diffFile.write(temp_string2)
                diffFile.close()
        else:
            file_path = file_path + '_' + 'NF' + '.diff'
            try:
                ndiffFile = open(file_path, 'w')
                ndiffFile.close()
            except Exception, e:
                print "Error Unable to Create NF-DIFF File"

def validator ():
    file1 = '/Users/anahit/desktop/admin/98e77650-bd45-45dd-b577-b447a781f8c0.json'
    file2 = '/Users/anahit/desktop/admin-changed/98e77650-bd45-45dd-b577-b447a781f8c0.json'
    json1 = getContentFromFile(file1)
    json2 = getContentFromFile(file2)
    # print json1
    # print json2
    json_temp_export_list = []
    json_temp_import_list = []
    for key, value in json1.iteritems():
        json_temp_export_list.append(json1[key])
    string = str(json_temp_export_list)
    for key, value in json1.iteritems():
        json_temp_import_list.append(json2[key])
    temp_import_string = str(json_temp_import_list)
    temp_export_string = str(json_temp_export_list)
    json_import_list = temp_import_string.split(',')
    json_export_list = temp_export_string.split(',')
    for item in json_import_list:
        print item
    for items in json_export_list:
        print items



validator()
# test_case_dict_path = test_case_json_dict.values()
    # test_case_wd_dict_path = test_case_wd_json_dict.values()
    # print test_case_dict_path
    # print test_case_wd_dict_path
    # for paths in test_case_dict_path:
    #     file_data = getContentFromFile(paths)
    #     obj = ordered(file_data)
    #     print obj

    #test_case_data, test_case_wd_data = getContentFromFile(test_case_json_dict.values(), test_case_wd_json_dict.values())











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


