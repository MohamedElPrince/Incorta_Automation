import random
import string
import subprocess
import sys, os, csv
import errno
import zipfile
import xml.etree.ElementTree as ET

import shutil

"""
How to run file in terminal
python -f /home/example.csv -w /home/ -h /home/Incorta Analytics

Where ARGS NEEDED ARE:
-f --> CSV FILE PATH
-w --> Disired output path(creates a suites folder)
-h --> Path to Inocorta Installation
"""
Debug = False
randomName = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(15))


def incorta_api_import(incortaHome):
    """
    Function takes the incorta installation path to import Incorta API
        args:
            incortaHome: Incorta Home Directory Path
        returns:
            Nothing
        prints:
            Nothing
    """
    incorta_module = incortaHome.rstrip() + os.sep + "bin".rstrip()
    sys.path.append(incorta_module)
    import incorta
    global incorta


def login(url, tenant, admin, password):
    """
    Function takes in login information and attempts to login through Incorta API
    	args:
    		url: Url for the Incorta instance
    		tenant: Tenant name for instance
    		admin: Username for instance
    		password: Password for instance
    	returns:
            The session for the Incorta instance is returned
    	prints:
            Prints exception case
    """
    try:
        return incorta.login(url, tenant, admin, password, True)
    except Exception, e:
        print "Login Failed"
        exit(1)


def export_dashboards(incorta, session, casePath, dashboards):
    """
    Function loads all dashboards to Incorta from the list of dashboards
        args:
            incorta: Incorta API module
            session: session var returned by login function
            casePath: path to test case in working directory
            dashboards: list of dashboards to upload
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """
    dashboardPath = create_directory(casePath, 'dashboards')
    str_tup=dashboards.split('/')
    dashboard_name=str_tup[-1]
    dashboardPath+= (os.sep + dashboard_name +'.zip')
    try:
        export_check = incorta.export_dashboards(session, dashboardPath, dashboards)
        if Debug == True:
            print export_check
        return dashboardPath
    except Exception, e:
        print ('ERROR: Dashboard:', dashboards, " Not Found")


def export_schemas(incorta, session, casePath, schemas):
    """
    Function loads all schemas to Incorta from the list of schemas
        args:
            incorta: Incorta API module
            session: session var returned by login function
            casePath: path to test case in working directory
            schemas: list of schemas to upload
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """
    schemaPath = create_directory(casePath, 'schemas')
    temp_path = schemaPath + os.sep + schemas + '.zip'
    try:
        export_check = incorta.export_schemas(session, temp_path, schemas)
        if Debug == True:
            print export_check
    except Exception, e:
        print ('ERROR: Dashboard:', schemas, " Not Found")


def export_datasources(incorta, session, casePath, datasources):
    """
    Function loads all schemas to Incorta from the list of schemas
        args:
            incorta: Incorta API module
            session: session var returned by login function
            casePath: path to test case in working directory
            schemas: list of schemas to upload
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """
    datasourcesPath = create_directory(casePath, 'datasources')
    temp_path = datasourcesPath + os.sep + datasources + '.zip'
    try:
        export_check = incorta.export_datasources(session, temp_path, datasources)
        if Debug == True:
            print export_check
    except Exception, e:
        print ('ERROR: DataSources:', datasources, " Not Found")


def create_directory(path, folderName):
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
    appended_path = path + os.sep + folderName
    try:
        if not os.path.exists(appended_path):
            os.makedirs(appended_path)
        return appended_path
    except OSError as exc:
        if exc.errno == errno.EEXIST and os.path.isdir(appended_path):
            pass
        else:
            raise


def get_dashboard_guid(casePath, dashboardZippath, tempPath):
    """
    Function parses tenant.xml from dashboard.zip
        args:
            casePath: path to test case in working directory
            dashboardZippath: path to the dashboard.zip file
        returns:
            The GUID of the dashboard
        prints:
            Prints exception case
    """
    newtempPath = create_directory(tempPath, randomName)
    zip_ref = zipfile.ZipFile(dashboardZippath, 'r')
    zip_ref.extractall(newtempPath)
    zip_ref.close()
    tempPathfiles = os.listdir(newtempPath)
    for files in tempPathfiles:
        if 'tenant.xml' == files:
            tenantxmlPath = os.path.join(newtempPath, files)
            try:
                with open(tenantxmlPath, 'rt') as f:
                    tree = ET.parse(f)
            except Exception, e:
                print "Unable to open tenant.xml"
            root = tree.getroot()
            for tags in root.iter('dashboard'):
                guid = tags.attrib['guid']
                return guid


def export_dashboards_json(session_id, guid, csrf_token, user, casePath, url):
    """
    Function uses curl command to export dashboard in JSON format
        args:
            session_id: session id returned by login API
            guid: dashbaord GUID
            csrf_token: csrf_token returned by login API
            user: string of user currently being tested
            casePath: path to test case in working directory
            url: url for incorta session
        returns:
            Nothing
        prints:
            Nothing
    """
    user_path = create_directory(casePath, user)
    jsonName = guid + '.json'
    jsonPath = user_path + os.sep + jsonName
    cmd = """curl \'""" + url + """/service/viewer?layout=""" + guid \
          + """&prompts=&outputFormat=json&odbc=false&Save=View' -H 'Cookie: JSESSIONID=""" + session_id \
          + """; XSRF-TOKEN=""" + csrf_token + """' --compressed > """ + jsonPath
    subprocess.call(cmd, shell=True)


def get_input_arguments():
    """
    Function checks for three arguments given when script is ran -h,-f,-l
    Stores them into variables and returns them
        args:
        returns:
            Arguments stored in variables:
            incortaHome: Path to Incorta
            csvFile: Path to CSV File
            workingDirectory: Path to extraction output
        prints:
            Nothing
    """
    commands = sys.argv
    if len(commands[1:]) != 6:
        raise Exception('Too Many Commands Given Expected 3. Given %s' % len(commands[1:]))

    for i in range(len(commands)):
        try:
            if commands[i] == '-h':
                incorta_home = commands[i + 1]
        except Exception, e:
            raise ('-h Flag Not Found')

        try:
            if commands[i] == '-f':
                csvFile = commands[i + 1]
        except Exception, e:
            raise ('-f Flag Not Found')

        try:
            if commands[i] == '-w':
                workingDirectory = commands[i + 1]
        except Exception, e:
            raise ('-w Flag Not Found')
    return incorta_home, csvFile, workingDirectory


def csv_file_handler(records, workingDirectory):
    """
    Function handles the records from the main function, calls upon external
        definitions to extract to the working directory
        args:
            records: CSV as an object
            workingDirectory: Path to the extraction location
        returns:
            Nothing
        prints:
            Prints 'Not Found' statements when certain objects are not found
    """
    tempPath = create_directory(workingDirectory, 'suites')
    for rows in records:
        session = login(rows['Url'], rows['Tenant'], rows['User'], rows['Password'])
        session_id = session[21:53]
        csrf_token = session[63:95]
        suiteName = rows['Test_Suite_Name']
        caseName = rows['Test_Case_Name']
        suitePath = create_directory(tempPath, suiteName)
        casePath = create_directory(suitePath, caseName)
        if rows['Dashboard_Name'] == '':
            print 'No Dashboard found'
        else:
            dashboardZippath = export_dashboards(incorta, session, casePath, rows['Dashboard_Name'])
            guid = get_dashboard_guid(casePath, dashboardZippath, tempPath)
            export_dashboards_json(session_id, guid, csrf_token, rows['User'], casePath, rows['Url'])
        if rows['Datasource_Name'] == '':
            print 'No Datasource found for TestSuite: %s, TestCase: %s' % (suiteName, caseName)
        else:
            export_datasources(incorta, session, casePath, rows['Datasource_Name'])
        if rows['Schema_Name'] == '':
            print 'No Schema found'
        else:
            export_schemas(incorta, session, casePath, rows['Schema_Name'])
    return tempPath


def main():
    """
    Function handles the the calling of external defs, controls the flow of the script
        args:
            Nothing
        returns:
            Nothing
        prints:
            Prints the three args to console
    """
    incortaHome, csvFile, workingDirectory = get_input_arguments()
    incorta_api_import(incortaHome)
    print 'Incorta Home: ', incortaHome
    print 'CSV File Path: ', csvFile
    print 'Working Directory Path: ', workingDirectory + '\n'
    with open(csvFile) as file:
        records = csv.DictReader(file)
        tempPath = csv_file_handler(records, workingDirectory)
    print '\nExtraction Complete...'
    print 'Cleaning up...'
    shutil.rmtree(os.path.join(tempPath, randomName))


if __name__ == '__main__':
    main()
