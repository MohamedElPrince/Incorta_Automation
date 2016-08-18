import sys, os, subprocess, time, zipfile
import os.path
from sys import argv
from shutil import copyfile
from xml.etree.ElementTree import Element, SubElement, Comment, tostring
import errno
import xml.etree.ElementTree as ET
import shutil


# import Auto_Module
# from Auto_Module import dataLoad
# from Auto_Module import loadUsers
# from Auto_Module import test_suite_import
# from Auto_Module import export

def set_new_values(config_file):
    """
    Function is used to parse the input file for appropriate values and puts them into various objects
        args:
            config_file: name of the input text file
        returns:
            nothing
        prints:
            Debug statements
    """
    try:
        f = open(config_file, "r")
        lines = f.readlines()
        f.close()
    except Exception, e:
        print "Incorrect path to config file given"
        exit(1)
    for line in lines:
        for key, value in config_defaults.items():
            if line[0] != '\n':
                if line[0] != '#':
                    temp_str = line
                    str_tup = temp_str.split("=", 1)
                    if str_tup[1].strip() != '':
                        if key == str_tup[0]:
                            if key == 'schema_name':
                                schema_name.append(str_tup[1].strip())
                            elif key == 'dash_name':
                                dash_name.append(str_tup[1].strip())
                            elif key == 'datasource':
                                datasource_name.append(str_tup[1].strip())

def parse(full_tenant_path):
    """
    Function is used to parse the large tenant.xml file and retrieve the necessary values
        args:
            full_tenant_path: the path to the tenant file
        returns:
            nothing
        prints:
            Debug statements
    """
    full_tenant_path = full_tenant_path + os.sep + 'tenant.xml'
    try:
        tree = ET.parse(full_tenant_path)
        root = tree.getroot()
    except Exception, e:
        print 'Incorrect path to folder'
        exit(1)
    for child in root.iter('schema-definition'):
        for schema in schema_name:
            if child.attrib['name'] == schema:
                schema_attributes.append(child.attrib)
                for sub in child.iter():
                    if 'schema-data' == sub.tag:
                        for sd in sub.iter():
                            if bool(sd.attrib):
                                schema_href[schema] = sd.attrib
                    if 'loader-data' == sub.tag:
                        for ld in sub.iter():
                            if bool(ld.attrib):
                                loader_href[schema] = ld.attrib
    for child in root.iter('dashboard'):
        for dash in dash_name:
            if child.attrib['name'] == dash:
                dashboard_attributes.append(child.attrib)
                for sub in child.iter():
                    if 'data' == sub.tag:
                        for dbn in sub.iter():
                            if bool(dbn.attrib):
                                dashboard_href[dash] = dbn.attrib
    for child in root.iter('data-source'):
        for datasource in datasource_name:
            if child.attrib['name'] == datasource:
                datasource_attributes.append(child.attrib)
                for sub in child.iter():
                    if 'data' == sub.tag:
                        for dn in sub.iter():
                            if bool(dn.attrib):
                                datasource_href[datasource] = dn.attrib

def create_tenant_xml(path):
    """
    Function is used to create a new tenant.xml file for the various files retrieved
        args:
            path: the path to the new directory containing the files retrieved
        returns:
            nothing
        prints:
            Debug statements
    """
    tenant_attributes = {"enabld": "true", "exportTime": "", "load-mode": "start",
                         "name": "", "partial": "true", "path": "/", "version": "1.0"}
    #tenant_attributes can be hardcoded since there are no parameters that need to be changed

    data_attribute = {'xmlns:xi': "http://www.w3.org/2001/XInclude"}
    #data_attribute will always be these url

    href_schema = {"href": "", "parse": "xml"}
    href_loader = {"href": "", "parse": "xml"}
    href_dash = {"href": "", "parse": "xml"}
    href_data = {"href": "", "parse": "xml"}

    root = Element('tenant', tenant_attributes)
    description = SubElement(root, 'description')
    config = SubElement(root, 'configurations')
    security = SubElement(root, 'security')
    roles = SubElement(security, 'roles')
    groups = SubElement(security, 'groups')
    users = SubElement(security, 'users')
    datapackage = SubElement(root, 'data-package')
    datafiles = SubElement(datapackage, 'data-files')
    datasources = SubElement(datapackage, 'data-sources')

    data_source_attributes = {}
    for dash in datasource_name:
        for attrib in datasource_attributes:
            if attrib['name'] == dash:
                data_source_attributes = attrib
        data_source = SubElement(datasources, 'data-source', data_source_attributes)
        data = SubElement(data_source, 'data', data_attribute)
        for name in datasource_href:
            if name == dash:
                href_data['href'] = datasource_href[dash]['href']
        data_xi = SubElement(data, 'xi:include', href_data)
    schemas = SubElement(datapackage, 'schemas')
    schema_attribute = {}
    for schema in schema_name:
        def_element = 'schema' + '_def'
        for attrib in schema_attributes:
            if attrib['name'] == schema:
                schema_attribute = attrib
        def_element = SubElement(schemas, 'schema-definition', schema_attribute)
        data_element = 'schema' + '_data'
        data_element = SubElement(def_element, 'schema-data', data_attribute)
        xi_element = 'schema' + '_xi'
        for name in schema_href:
            if name == schema:
                href_schema['href'] = schema_href[schema]['href']
        xi_element = SubElement(data_element, 'xi:include', href_schema)
        loader_element = 'schema' + '_loader'
        loader_element = SubElement(def_element, 'loader-data', data_attribute)
        lx_element = 'schema' + "_lx"
        for name in loader_href:
            if name == schema:
                href_loader['href'] = loader_href[schema]['href']
        lx_element = SubElement(loader_element, 'xi:include', href_loader)

    sessionvar = SubElement(datapackage, 'session-variables')
    catalog = SubElement(root, 'catalog')
    dash_attributes = {}
    for dash in dash_name:
        for attrib in dashboard_attributes:
            if attrib['name'] == dash:
                dash_attributes = attrib
        dashboard = SubElement(catalog, 'dashboard', dash_attributes)
        dash_data = SubElement(dashboard, 'data', data_attribute)
        for name in dashboard_href:
            if name == dash:
                href_dash['href'] = dashboard_href[dash]['href']
        xi = SubElement(dash_data, 'xi:include', href_dash)
    path = path + os.sep + 'tenant.xml'

    try:
        output_file = open(path, 'w')
        output_file.write('<?xml version="1.0" encoding="UTF-8"?>')
        output_file.write(tostring(root))
        output_file.close()
    except Exception, e:
        print 'Wrong path to new folder'
        exit(1)

def extraction(zip_file, unzip):
    """
    Function is used to extract the zip file contents to a new file
        args:
            zip_file: path to the zip file
            unzip: path to new file
        returns:
            nothing
        prints:
            Debug statements
    """
    if os.path.isfile(zip_file):
        try:
            fn = zipfile.ZipFile(zip_file, 'r')
        except Exception, e:
            print "Incorrect path given to zip file"
            exit(1)
    try:
        fn.extractall(unzip)
        fn.close()
    except Exception, e:
        print 'Incorrect path to output folder'
        exit(1)

def zip_up(zip_path):
    """
    Function is used to zip the new folder
        args:
            zip_path: the path to the folder
        returns:
            nothing
        prints:
            Debug statements
    """
    new_zipfile_name = zip_path + '.zip'
    fn = zipfile.ZipFile(new_zipfile_name, 'w', zipfile.ZIP_DEFLATED)
    rootlen = len(zip_path) + 1
    try:
        for root, folders, files in os.walk(zip_path):
            for file in files:
                fp = os.path.join(root, file)
                fn.write(fp, fp[rootlen:])
        fn.close()
    except Exception, e:
        print "Unable to zip file"
        exit(1)

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

def move_files(src, dest):
    """
    Function is used to move the files wanted from the extracted zip files to the new folder that will be zipped
        args:
            src: source of the extracted zip files
            dest: destination of the wanted files
        returns:
            nothing
        prints:
            Debug statements
    """

    for schema in schema_name:
        if bool(schema_href):
            sn = schema_href[schema]['href']
            try:
                os.rename(src + os.sep + sn, dest + os.sep + sn)
            except Exception, e:
                print 'Unable to move schema files'
                exit(1)
    for dashboard in dash_name:
        if bool(dashboard_href):
            dn = dashboard_href[dashboard]['href']
            try:
                os.rename(src + os.sep + dn, dest + os.sep + dn)
            except Exception, e:
                print 'Unable to move dashboard files'
                exit(1)
    for datasource in datasource_name:
        if bool(datasource_href):
            dsn = datasource_href[datasource]['href']
            try:
                os.rename(src + os.sep + dsn, dest + os.sep + dsn)
            except Exception, e:
                print 'Unable to move datasource files'
                exit(1)
    for schema in schema_name:
        if bool(loader_href):
            rn = loader_href[schema]['href']
            try:
                os.rename(src + os.sep + rn, dest + os.sep + rn)
            except Exception, e:
                print 'Unable to move schema loader files'
                exit(1)

def create_txt_file(path):
    """
    Function is used to create a txt file describing what's in the newly zipped file
        args:
            path: path to where you want to place the txt file
        returns:
            nothing
        prints:
            Debug statements
    """
    path += os.sep + 'content.log'
    try:
        txt_file = open(path, 'w')
    except Exception, e:
        print 'Incorrect path'
        exit(1)
    txt_file.write('This file contains the names of the requested files.\n\n')
    txt_file.write('The following schemas are included\n')
    txt_file.write('\n'.join(schema_name))
    txt_file.write('\n\n')
    txt_file.write('The following dashboards are included\n')
    txt_file.write('\n'.join(dash_name))
    txt_file.write('\n\n')
    txt_file.write('The following datasources are included\n')
    txt_file.write('\n'.join(datasource_name))
    txt_file.close()

# Dictionary for parsing the input.txt file
config_defaults = {'schema_name': 'default', 'dash_name': 'default', 'datasource': 'default',
                   'zipfile_home': 'default', 'testfile_home': 'default', 'unzipped_home': "default", 'txt_home': 'default'}

# Lists the contain the wanted names of schemas/dashboards/datasources
schema_name = []
dash_name =[]
datasource_name = []

# These are lists but contain dictionaries in each node where the key is the wanted file name and value is its xml attributes
schema_attributes = []
dashboard_attributes = []
datasource_attributes = []

# Dictionaries of dictionaries that contain the href as the value and key is the wanted file name
schema_href = {}
loader_href = {}
dashboard_href = {}
datasource_href = {}

def get_input_arguments():
    """
    Function checks for three arguments given when script is ran -f,-o,-z
    Stores them into variables and returns them
        args:
        returns:
            Arguments stored in variables:
            inputFile: Path to input.txt file
            outputPath: Path to output folder
            zipPath: Path to zip file
        prints:
            Nothing
    """
    commands = sys.argv
    if len(commands[1:]) != 6:
        raise Exception('Incorrect Amount of Args Expected 6. Given %s' % len(commands[1:]))

    for i in range(len(commands)):
        try:
            if commands[i] == '-f':
                inputFile = commands[i + 1]
        except Exception, e:
            raise ('-f Flag Not Found')

        try:
            if commands[i] == '-o':
                outputPath = commands[i + 1]
        except Exception, e:
            raise ('-o Flag Not Found')

        try:
            if commands[i] == '-z':
                zipPath = commands[i + 1]
        except Exception, e:
            raise ('-z Flag Not Found')
    return inputFile, outputPath, zipPath

def main():
    print "Entering script..."
    inputFile, outputPath, zipPath = get_input_arguments()
    set_new_values(inputFile)
    config_defaults['testfile_home'] = outputPath
    config_defaults['txt_home'] = outputPath
    print config_defaults['testfile_home']

    wdPath = create_directory((config_defaults['testfile_home']), 'temp')
    tmpPath = create_directory(wdPath, 'tmp')
    create_directory(tmpPath, 'schemas')
    create_directory(tmpPath, 'dashboards')
    create_directory(tmpPath, 'datasources')
    config_defaults['zipfile_home'] = zipPath
    print config_defaults['zipfile_home']

    zipName = os.path.basename(zipPath)
    extractPath = wdPath + os.sep + zipName[:-4]
    config_defaults['unzipped_home'] = extractPath
    print config_defaults['unzipped_home']

    extraction(config_defaults['zipfile_home'], config_defaults['unzipped_home'])

    parse(config_defaults['unzipped_home'])
    create_tenant_xml(tmpPath)
    move_files(config_defaults['unzipped_home'], tmpPath)
    zip_up(tmpPath)
    create_txt_file(wdPath)
    print 'Cleaning Up...'
    shutil.rmtree(tmpPath)
    shutil.rmtree(extractPath)
    print "Exiting script..."

if __name__ =='__main__':
    main()


