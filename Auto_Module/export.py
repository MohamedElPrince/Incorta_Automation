import os, file_tools, zipfile, subprocess, logging

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
            logging.critical('%s %s %s','ERROR: Dashboard:', names, " Not Found")
            dashboards.remove(names)
            export_dashboards(incorta, session, export_zips_path, dashboards)
        if Debug == False:
            print export_check
            logging.info(export_check)
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
            logging.critical('%s %s %s','ERROR: Dashboard:', names, " Not Found")
            schemas.remove(names)
            export_schemas(incorta, session, export_zips_path, schemas)
        if Debug == False:
            print export_check
            logging.info(export_check)
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

def get_guid(test_case_path, user):
    """
    Function gets GUID names from JSON file name
        args:
            test_case_path: path to test case in working directory
            user: string of user currently being tested
        returns:
            The GUID of the dashboard
        prints:
            Nothing
    """
    guid_Names = []
    test_case_subdirectories = file_tools.get_subdirectories(test_case_path)
    for dirs in test_case_subdirectories:
        if user in dirs:
            user_path = file_tools.get_path(test_case_path, dirs)
            for files in os.listdir(user_path):
                 guid_Names.append(os.path.splitext(files)[0])
    return guid_Names

def export_dashboards_json(session_id, dashboard_id, csrf_token, test_case_path_wd, test_case_path, user, url):
    """
    Function uses curl command to export dashboard in JSON format
        args:
            session_id: session id returned by login API
            dashboard_id: dashbaord GUID
            csrf_token: csrf_token returned by login API
            test_case_path_wd: path to test case of working directory
            test_case_path: path to test case of benchmark
            user: string of user currently being tested
            url: url for incorta session
        returns:
            Nothing
        prints:
            Nothing
    """
    guid_Names = get_guid(test_case_path, user)
    for guid in guid_Names:
        dash_id = str(guid)
        user_path = test_case_path_wd + os.sep + 'Import_Files' + os.sep + user + os.sep
        json_name = dash_id + '.json'
        json_path = user_path + json_name
        cmd = """curl \'""" + url + """/service/viewer?layout=""" + dash_id \
        + """&prompts=&outputFormat=json&odbc=false&Save=View' -H 'Cookie: JSESSIONID=""" + session_id \
        + """; XSRF-TOKEN=""" + csrf_token + """' --compressed > """ + json_path
        subprocess.call(cmd, shell=True)


