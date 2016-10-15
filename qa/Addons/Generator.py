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
-t --> Path to tenant folder for Local Inocrta
"""
Debug = False

random_name_list = []


def random_name():
    randomName = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(15))
    random_name_list.append(randomName)
    return randomName


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


def logout(session):
    """
    Function takes in logout information and attempts to logout through Incorta API
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
        return incorta.logout(session)
    except Exception, e:
        print "Logout Failed"
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
    print casePath
    print dashboards
    dashboardPath = create_directory(casePath, 'dashboards')
    str_tup = dashboards.split('/')
    dashboard_name = str_tup[-1]
    dashboardPath += (os.sep + dashboard_name + '.zip')
    try:
        export_check = incorta.export_dashboards_no_bookmarks(session, dashboardPath, dashboards)
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
    schemaDirPath = create_directory(casePath, 'schemas')
    schema_path = schemaDirPath + os.sep + schemas + '.zip'
    try:
        export_check = incorta.export_schemas(session, schema_path, schemas)
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
    datasourcesPath = casePath + os.sep + datasources + '.zip'
    try:
        export_check = incorta.export_datasources(session, datasourcesPath, datasources)
        if Debug == True:
            print export_check
        return datasourcesPath
    except Exception, e:
        print ('ERROR: DataSources:', datasources, " Not Found")


def export_datafile(file_name, tenant, tenant_path, export_path):
    """
    """
    temp = export_path + os.sep + 'temp'
    tenant_deep_path = tenant_path + os.sep + tenant + os.sep + 'data'
    if os.path.isdir(tenant_deep_path):
        for files in os.listdir(tenant_deep_path):
            if os.path.isdir(temp):
                temp_export_path = export_path + os.sep + 'temp'
            else:
                temp_export_path = create_directory(export_path, 'temp')
            if files == file_name:
                datafile_path = os.path.join(tenant_deep_path, files)
                shutil.copy(datafile_path, temp_export_path)
                return temp_export_path


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


def remove_duplication(outfilename, infilename):
    lines_seen = set()  # holds lines already seen
    outfile = open(outfilename, "w")
    for line in open(infilename, "r"):
        if line not in lines_seen:  # not a duplicate
            outfile.write(line)
            lines_seen.add(line)
    outfile.close()


def info_data_source_folder(data_source_path, temp_path):
    newtempPath = create_directory(temp_path, random_name())
    zip_ref = zipfile.ZipFile(data_source_path, 'r')
    zip_ref.extractall(newtempPath)
    zip_ref.close()
    for root_path, dir, files in os.walk(newtempPath):
        for file in files:
            if file.endswith('.xml'):
                xmlPath = os.path.join(root_path, file)
                try:
                    with open(xmlPath, 'rt') as f:
                        tree = ET.parse(f)
                except Exception, e:
                    print "Unable to open datasource.xml"
                root = tree.getroot()
                for tags in root.iter('datasource'):
                    name = tags.attrib['name']
                    for taglets in tags.iter('property'):
                        name2 = taglets.attrib['name']
                        if 'connection' == name2:
                            jbdc_string = taglets.attrib['value']
                        if 'user' == name2:
                            username = taglets.attrib['value']
    return name, jbdc_string, username


def get_dashboard_guid(dashboardZippath, tempPath):
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
    newtempPath = create_directory(tempPath, random_name())
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
    user_path = "\"" + user_path + "\""
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
    if len(commands[1:]) != 8:
        raise Exception('Argument Error Expected 4. Given %s' % len(commands[1:]))

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

        try:
            if commands[i] == '-t':
                tenantDirectory = commands[i + 1]
        except Exception, e:
            raise ('-t Flag Not Found')
    return incorta_home, csvFile, workingDirectory, tenantDirectory


def csv_file_handler(records, workingDirectory, tenant_path):
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
    data_file_path_list = []
    tempPath1 = create_directory(workingDirectory, 'Generator_Output')
    metadata_path = create_directory(tempPath1, 'SourcesMetadata')
    testsuite_path = create_directory(tempPath1, 'TestSuites')
    for rows in records:
        session = login(rows['Url'], rows['Tenant'], rows['User'], rows['Password'])
        session_id = session[21:53]
        csrf_token = session[63:95]
        suiteName = rows['Test_Suite_Name']
        caseName = rows['Test_Case_Name']
        root_suitePath = create_directory(testsuite_path, suiteName)
        root_casePath = create_directory(root_suitePath, caseName)

        if rows['Dashboard_Name'] == '':
            print 'No Dashboard found'
        else:
            dashboardZippath = export_dashboards(incorta, session, root_casePath, rows['Dashboard_Name'])
            guid = get_dashboard_guid(dashboardZippath, tempPath1)
            export_dashboards_json(session_id, guid, csrf_token, rows['User'], root_casePath, rows['Url'])

        if rows['Datasource_Name'] == '':
            print 'No Datasource found for TestSuite: %s, TestCase: %s' % (suiteName, caseName)
        else:
            datasourceZippath = export_datasources(incorta, session, metadata_path, rows['Datasource_Name'])
            name, jbdc_string, username = info_data_source_folder(datasourceZippath, tempPath1)
            jbdc_string = jbdc_string[5:]

            for c in ['\\', '`', '*', '@', ':', '{', '}', '[', ']', '(', ')', '>', '#', '+', '-', '.', '!', '$', '\'',
                      '/', '//', '___', '__']:
                if c in jbdc_string:
                    jbdc_string = jbdc_string.replace(c, '_')

            data_source_folder_name = name + '_' + jbdc_string + '_' + username
            metadata_dir_path = create_directory(metadata_path, data_source_folder_name)
            metadata_datasource_folder_path = create_directory(metadata_dir_path, 'datasources')
            shutil.copy(datasourceZippath, metadata_datasource_folder_path)
            os.remove(datasourceZippath)

            # schema export only occurs on datasource export success
            if rows['Schema_Name'] == '':
                print 'No Schema found'
            else:
                export_schemas(incorta, session, metadata_dir_path, rows['Schema_Name'])
                # Datasource export
            if rows['Datafile'] == '':
                print 'No Datafile not found'
            else:
                data_file_path_list.append(
                    export_datafile(rows['Datafile'], rows['Tenant'], tenant_path, metadata_datasource_folder_path))

        dashboard_temp_path = root_casePath + os.sep + 'dashboards'
        if os.path.isdir(dashboard_temp_path):
            text_file_path = root_casePath + os.sep + 'schema.txt'
            tmp_file_path = root_casePath + os.sep + 'temp.txt'
            try:
                with open(tmp_file_path, "a") as text_file:
                    appended_text = rows['Schema_Name'] + '\n'
                    text_file.write(appended_text)
                remove_duplication(text_file_path, tmp_file_path)
            except Exception:
                print "Can not create Schema Text File"
        else:
            print "Dashboard Folder can not be found"
        logout(session)

    for root, dirs, files in os.walk(testsuite_path):
        for file in files:
            if 'temp.txt' in file:
                file_path = os.path.join(root, file)
                os.remove(file_path)

    data_file_path_list_dupes = list(set(data_file_path_list))
    data_file_path_list_stripped = list([element for element in data_file_path_list_dupes if element is not None])
    for path in data_file_path_list_stripped:
        upper_path = os.path.dirname(path) + os.sep + 'datafiles'
        shutil.make_archive(upper_path, 'zip', path)
        shutil.rmtree(path)
    return tempPath1


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
    incortaHome, csvFile, workingDirectory, tenantDirectory = get_input_arguments()
    incorta_api_import(incortaHome)
    print 'Incorta Home: ', incortaHome
    print 'CSV File Path: ', csvFile
    print 'Working Directory Path: ', workingDirectory + '\n'
    with open(csvFile) as file:
        records = csv.DictReader(file)
        tempPath = csv_file_handler(records, workingDirectory, tenantDirectory)
    print '\nExtraction Complete...'
    print 'Cleaning up...'
    for random_names in random_name_list:
        shutil.rmtree(os.path.join(tempPath, random_names))


if __name__ == '__main__':
    main()
