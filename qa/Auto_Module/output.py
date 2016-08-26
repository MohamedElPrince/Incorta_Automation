import file_tools, os


#Initializes Output Directories

def create_output_folder(wd_path):
    output_path = file_tools.create_directory(wd_path, 'Output')
    return output_path

def create_test_suite_output_folder(output_path, test_suite_name):
    test_suite_output_folder_name = test_suite_name + '_' + 'Summary'
    test_suite_output_path = file_tools.create_directory(output_path, test_suite_output_folder_name)
    return test_suite_output_path

def create_test_suite_validation_folders(test_suite_output_path):
    Data_Validation_Path = file_tools.create_directory(test_suite_output_path, 'Data_Validation')
    Loader_Validation_Path = file_tools.create_directory(test_suite_output_path, 'Loader_Validation')
    XML_MetaData_Validation_Path = file_tools.create_directory(test_suite_output_path, 'XML_Metadata_Validation')
    return Data_Validation_Path, Loader_Validation_Path, XML_MetaData_Validation_Path

def create_test_case_output_path(Data_Validation_Path, test_case_name):
    output_test_case_path = file_tools.create_directory(Data_Validation_Path, test_case_name)
    return output_test_case_path

def create_output_user_path(output_test_case_path, user_name):
    output_user_path = file_tools.create_directory(output_test_case_path, user_name)
    return output_user_path

def create_log_folder(path):
    log_folder_path = file_tools.create_directory(path, 'Logs')
    return log_folder_path

def data_validation_generate_suc_dif_file_names(test_suite_output_type_path, test_case_name):
    file_name_list = []
    #print test_suite_output_type_path, " ", test_case_name
    for dirName, subdirList, fileList in os.walk(test_suite_output_type_path):
        #print('Found directory: %s' % dirName)
        #print "PRINTING PARAMETERS: ", dirName, " ", subdirList, " ", fileList
        for fname in fileList:
           #print "PRINTING FILE NAME"
            if test_case_name in fname:
                #print "%%%%%"
                #print('\t%s' % fname)
                file_name_list.append(fname)
    return file_name_list

def loader_validation_generate_suc_dif_file_names(test_suite_output_type_path):
    file_name_list = []
    for root, dirs, files in os.walk(test_suite_output_type_path):
        for file in files:
            file_name_list.append(file)
    return file_name_list

def meta_data_validation_generate_suc_dif_file_names(test_suite_output_type_path, object_type):
    file_name_list = []
    search_path = test_suite_output_type_path + os.sep + object_type
    for root, dirs, files in os.walk(search_path):
        for file in files:
            file_name_list.append(file)
    return file_name_list



