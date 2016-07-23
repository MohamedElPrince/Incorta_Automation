import os, file_tools, zipfile

"""
Exports schema/dashboards from Incorta to temporary folder to hold zip files
Exports zip files to working directory while retaining proper folder structure

TODO
    Add log file dump for failure and success of each import and extract
    Need more try/catch handling
"""

Debug = True  # Debug flag for print statements


def export_dashboards(incorta, session, export_zips_path, dashboards):
    """
    Function loads all dashboards to Incorta from the list of dashboards
        args:
            incorta: Incorta API module
            session: session var returned by login function
            export_zips_path: temporary zip file directory path
            dashboards: list of dashboards to upload
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """

    for names in dashboards:
        temp_name = names
        temp_path = export_zips_path + os.sep + temp_name + '.zip'
        export_check = incorta.export_dashboards(session, temp_path, temp_name)
        if Debug == False:
            print export_check


def export_schemas(incorta, session, export_zips_path, schemas):
    """
    Function loads all schemas to Incorta from the list of schemas
        args:
            incorta: Incorta API module
            session: session var returned by login function
            export_zips_path: temporary zip file directory path
            schemas: list of schemas to upload
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """
    #cleaned_export_schema = [x.replace(' ', '_') for x in schemas]
    for names in schemas:
        temp_name = names
        temp_path = export_zips_path + os.sep + temp_name + '.zip'
        export_check = incorta.export_schemas(session, temp_path, temp_name)
        if Debug == False:
            print export_check


def export_zip(export_zips_path, test_case_export_path_wd, export_file_name):
    """
    Function extracts zips files from working directory to export path
        args:
            export_zips_file_path: temporary zip file directory path
            test_case_export_path_wd: path of zip uncompress and extraction
            export_file_name: names of files to be extracted from temp to working directory
        returns:
            Nothing
        prints:
            Nothing
    """
    #cleaned_export_file_name = [x.replace(' ', '_') for x in export_file_name]
    extension = '.zip'
    for files in export_file_name:
        file_path_wd = file_tools.create_directory(test_case_export_path_wd, files)
        file_raw_path = os.path.join(export_zips_path, files + extension)
        zip_ref = zipfile.ZipFile(file_raw_path)
        zip_ref.extractall(file_path_wd)
        zip_ref.close()


def create_temp_directory(test_case_path_wd):
    temp_path = file_tools.create_directory(test_case_path_wd, 'zip_export')
    return temp_path