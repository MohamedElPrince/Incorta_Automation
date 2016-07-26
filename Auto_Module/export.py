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
        try:
            export_check = incorta.export_dashboards(session, temp_path, temp_name)
        except Exception, e:
            print ('ERROR: Dashboard:', names, " Not Found")
            dashboards.remove(names)
            export_dashboards(incorta, session, export_zips_path, dashboards)
        if Debug == False:
            print export_check
    return dashboards


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
        try:
            export_check = incorta.export_schemas(session, temp_path, temp_name)
        except Exception, e:
            print ('ERROR: Dashboard:', names, " Not Found")
            schemas.remove(names)
            export_schemas(incorta, session, export_zips_path, schemas)
        if Debug == False:
            print export_check
    return schemas


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


# def export_dashboards_json(session_id, dashboard_id, csrf_token):
#
#     cmd = """curl 'http://localhost:8080/incorta/service/viewer?layout=""" + dashboard_id \
#     + """&prompts=&outputFormat=json&odbc="""


    # curl
    # 'http://localhost:8080/incorta/service/viewer?layout=98e77650-bd45-45dd-b577-b447a781f8c0&prompts=&outputFormat=json&odbc=false&Save=View' - H
    # 'Cookie: JSESSIONID=616A28AEE1CB0EBE153C8B4E8173C5E8; XSRF-TOKEN=45A25A5EE21DA8237F992520AB6F6FD4' - -compressed > table.json




