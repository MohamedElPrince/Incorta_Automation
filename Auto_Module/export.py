import os
import zipfile

import file_tools

"""
"""
Debug = True  # Debug flag for print statements


def export_dashboards(incorta, session, export_zips_path, dashboards):
    """
    """
    for names in dashboards:
        temp_name = names
        temp_path = export_zips_path + os.sep + temp_name + '.zip'
        export_check = incorta.export_dashboards(session, temp_path, temp_name)
        if Debug == False:
            print export_check


def export_schemas(incorta, session, export_zips_path, schemas):
    """
    """
    for names in schemas:
        temp_name = names
        temp_path = export_zips_path + os.sep + temp_name + '.zip'
        export_check = incorta.export_schemas(session, temp_path, temp_name)
        if Debug == False:
            print export_check

def export_zip(export_zips_path, test_case_export_path_wd, export_file_name):
    """
    Documentation to come
    Anahit Sarao
    """
    extension = '.zip'
    for files in export_file_name:
        file_path_wd = file_tools.create_directory(test_case_export_path_wd, files)
        file_raw_path = os.path.join(export_zips_path, files + extension)
        zip_ref = zipfile.ZipFile(file_raw_path)
        zip_ref.extractall(file_path_wd)
        zip_ref.close()

    # filelist = [f for f in os.listdir(export_zips_path) if f.endswith(".zip")]
    # for f in filelist:
    #     file_deletion = file_tools.get_path(export_zips_path, f)
    #     os.remove(file_deletion)

    # for files in file_tools.get_subdirectories(test_case_export_path_wd):
    #     os.re


def create_temp_directory(test_case_path_wd):
    temp_path = file_tools.create_directory(test_case_path_wd, 'zip_export')
    return temp_path