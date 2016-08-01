import json, file_tools, os

"""
JSON validation code to comapare two JSON files and return the differences
Then to create a .suc file is no changes, .diff file if changes found, NF.diff if one file is not found

TODO
    Add log file dump for failure and success
    Need more try/catch handling
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

def printDiffs(x, y):
    """
    """
    diff = False
    for x_key in x:
        if x_key not in y:
            diff = True
            print "key %s in x, but not in y" % x_key
        elif x[x_key] != y[x_key]:
            diff = True
            print "key in x and in y, but values differ (%s in x and %s in y)" % (x[x_key], y[x_key])
    if not diff:
        print "both files are identical"

def dict_compare(d1, d2):
    """
    """
    d1_keys = set(d1.keys())
    d2_keys = set(d2.keys())
    intersect_keys = d1_keys.intersection(d2_keys)
    added = d1_keys - d2_keys
    removed = d2_keys - d1_keys
    modified = {o: (d1[o], d2[o]) for o in intersect_keys if d1[o] != d2[o]}
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
    Function gets paths for JSON files from benchmark JSONs and export JSONs
        args:
            test_case_path: Test case path from benchmarks
            test_case_wd_path: Test case path from working directory
        returns:
            Dictionary of JSON files such as: key -> GUID, Value -> File Path
        prints:
            Nothing
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


def validator(import_file_path, export_file_path):
    """
    Function compares two JSON line by line to find differences
        args:
            import_file_path: Path for the JSON benchmark
            export_file_path: Path for the exported JSON
        returns:
            Two lists of the changes inside the Import and Export Json Files
            Two bools that either return True or False
        prints:
            Nothing
    """
    import_dict = getContentFromFile(import_file_path)
    export_dict = getContentFromFile(export_file_path)
    json_temp_export_list = []
    json_temp_import_list = []
    for key, value in import_dict.iteritems():
        json_temp_import_list.append(import_dict[key])
    for key, value in export_dict.iteritems():
        json_temp_export_list.append(export_dict[key])
    temp_import_string = str(json_temp_import_list)
    temp_export_string = str(json_temp_export_list)
    json_import_list = temp_import_string.split(',')
    json_export_list = temp_export_string.split(',')
    import_diff = set(json_import_list) - set(json_export_list)
    import_diff_bool = set(json_import_list) == set(json_export_list)
    export_diff = set(json_export_list) - set(json_import_list)
    export_diff_bool = set(json_export_list) == set(json_import_list)
    return import_diff, export_diff, import_diff_bool, export_diff_bool


def validation(test_case_path, test_case_wd_path, output_wd_path, test_suite, admin_wd_path):
    """
    Function creates suc, diff, and NF.diff files after comparing the benchmark JSON and
    exported JSON.
        args:
            test_case_path: Test case path from benchmarks
            test_case_wd_path: Test case path from working directory
            output_wd_path: Ouput folder path from working directory
            test_suite: Current test suite names
            admin_wd_path: Admin path for bechmark
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """
    test_case_json_dict, test_case_wd_json_dict = get_paths(test_case_path, test_case_wd_path)
    temp = output_wd_path + os.sep + test_suite + '_Summary'
    admin_testcase_name = file_tools.create_directory(temp, 'admin')

    for key in test_case_json_dict:
        export_file_path = test_case_wd_json_dict.get(key, None)
        if export_file_path != None:
            temp_path_list = test_case_json_dict[key].split('/')
            temp_path_list_size = len(temp_path_list)
            temp_name = (temp_path_list[temp_path_list_size - 1].split('.'))[0]
            file_name = temp_path_list[temp_path_list_size - 3] + '_' + temp_path_list[
                temp_path_list_size - 2] + '_' + temp_name
            file_path = admin_wd_path + os.sep + file_name
            import_diff, export_diff, import_diff_bool, export_diff_bool = validator(test_case_json_dict[key],
                                                                                     test_case_wd_json_dict[key])

            if import_diff_bool == True and export_diff_bool == True:
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
                temp_string = '>>> ' + str(import_diff) + '\n'
                temp_string2 = '<<< ' + str(export_diff) + '\n'
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
