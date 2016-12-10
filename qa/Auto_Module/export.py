from customLogger import mainLogger, writeLogMessage
import os, file_tools, zipfile

"""
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
    for dash_full_path in dashboards:
        str_tup = dash_full_path.split('/')
        dash_name = str_tup[-1]
        temp_path = export_zips_path + os.sep + dash_name + '.zip'
        try:
            export_check = incorta.export_dashboards(session, temp_path, dash_full_path)
        except Exception, e:
            print ('ERROR: Dashboard:', names, " Not Found")
            writeLogMessage('%s %s %s' % ('ERROR: Dashboard:', names, " Not Found"), mainLogger, 'info')
            dashboards.remove(names)
            export_dashboards(incorta, session, export_zips_path, dashboards)
        if Debug == False:
            print export_check
            writeLogMessage(export_check, mainLogger, 'debug')
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
    for names in schemas:
        temp_name = names
        temp_path = export_zips_path + os.sep + temp_name + '.zip'
        try:
            export_check = incorta.export_schemas(session, temp_path, temp_name)
        except Exception, e:
            print ('ERROR: Dashboard:', names, " Not Found")
            writeLogMessage('%s %s %s' % ('ERROR: Dashboard:', names, " Not Found"), mainLogger, 'critical')
            schemas.remove(names)
            export_schemas(incorta, session, export_zips_path, schemas)
        if Debug == False:
            print export_check
            writeLogMessage(export_check, mainLogger, 'debug')
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
    for files in export_file_name:
        str_tup = files.split('/')
        zip_name = str_tup[-1]
        extension = '.zip'
        file_path_wd = file_tools.create_directory(test_case_export_path_wd, zip_name)
        file_raw_path = os.path.join(export_zips_path, zip_name + extension)
        zip_ref = zipfile.ZipFile(file_raw_path)
        zip_ref.extractall(file_path_wd)
        zip_ref.close()


def create_temp_directory(test_case_path_wd):
    temp_path = file_tools.create_directory(test_case_path_wd, 'zip_export')
    return temp_path
