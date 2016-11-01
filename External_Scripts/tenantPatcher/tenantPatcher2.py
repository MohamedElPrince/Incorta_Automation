#! /usr/bin/env python
# ************************************************************************
#  Licensed Material - Property of Incorta.
#
#  (c) Copyright Incorta 2014, 2015. All rights reserved.
# ************************************************************************
# Incorta Tenant Patch Tool
# ************************************************************************
import sys, os, subprocess, time, zipfile
import os.path
from sys import argv
from shutil import copyfile
from xml.etree.ElementTree import Element, SubElement, Comment, tostring
import errno
import xml.etree.ElementTree as ET
import shutil
import datetime

def get_all_dash(map):
    """
    Function is used to check for all dashboards that have the same name and add them into the corresponding list
        args:
            map: parent, child tree of the dashboards and folders
        returns:
            nothing
        prints:
            nothing
    """
    for dash in dash_middle:
        for child, parent in map.iteritems():
            if child.attrib.has_key('name'):
                if child.attrib['name'] == dash:
                    if child.attrib not in dashboard_attributes:
                        dash_confirmed.append(dash)
                        dashboard_attributes.append(child.attrib)

def get_parent(map, key):
    """
    Function is used to traverse a tree given a map and key
        args:
            map: parent, child tree of the dashboards and folders
            key: dashboard of which parents we want to find
        returns:
            list containing the traversal of the tree up to the root folder
        prints:
            nothing
    """
    structure = []
    for child, parent in map.iteritems():
        if child.attrib == key:
            c = child
            p = parent
    if c.attrib['parent'] != c.attrib['owner-id']:
        while p.attrib['parent'] != p.attrib['owner-id']:
            structure.append(p.attrib)
            c = p
            p = map[p]
        structure.append(p.attrib)
    return structure

def confirm_input(full_tenant_path):
    """
    Function is used to confirm the existence of schemas, dashboards, and datasources
        args:
            full_tenant_path: the path to the tenant.xml
        returns:
            nothing
        prints:
            nothing
    """
    full_tenant_path = full_tenant_path + os.sep + 'tenant.xml'
    try:
        tree = ET.parse(full_tenant_path)
        root = tree.getroot()
    except Exception, e:
        print 'Incorrect path to Tenant'
        exit(1)
    sch_folder = []
    das_folder = []
    dat_folder = []
    for child in root.iter('schema-definition'):
        for schema in schema_name:
            if child.attrib['name'] == schema:
                sch_folder.append(schema)
    for schema in sch_folder:
        if schema not in schema_confirmed:
            schema_confirmed.append(schema)
    for child in root.iter('dashboard'):
        for dash in dash_name:
            if child.attrib['name'] == dash:
                das_folder.append(dash)
    for dash in das_folder:
        if dash not in dash_confirmed:
            if dash not in dash_middle:
                dash_middle.append(dash)
    for child in root.iter('data-source'):
        for ds in datasource_name:
            if child.attrib['name'] == ds:
                dat_folder.append(ds)
    for ds in dat_folder:
        if ds not in datasource_confirmed:
            datasource_confirmed.append(ds)

def get_names_from_folder(full_tenant_path):
    """
    Function is used to grab the names of the dashboards under a given folder
        args:
            full_tenant_path: the path to the tenant.xml
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
        print 'Incorrect path to Tenant'
        exit(1)
    dash_folder = []
    for child in root.iter('folder'):
        for f in folder_name:
            if f == child.attrib['name']:
                for sub in child.iter():
                    if sub.tag == 'dashboard':
                        dash_folder.append(sub.attrib)
    for dash in dash_folder:
        if dash not in dashboard_attributes:
            dash_confirmed.append(dash['name'])
            dashboard_attributes.append(dash)

def parse_folder_structure(full_tenant_path):
    """
    Function is used to make parent, child tree of the dashboards
        args:
            full_tenant_path: path to the tenant.xml
        returns:
            the tree which is a dictionary where the child is the key and the parent is the value
        prints:
            Debug statements
    """
    full_tenant_path = full_tenant_path + os.sep + 'tenant.xml'
    try:
        tree = ET.parse(full_tenant_path)
        root = tree.getroot()
        parent = dict((ch, pa) for pa in tree.getiterator() for ch in pa)
        return parent
    except Exception, e:
        print 'Incorrect path to Tenant'
        exit(1)

def get_names_from_wild_char(full_tenant_path):
    """
    Functions is used to grab all the dashboards, schemas wanted using wildchar
        args:
            full_tenant_path: the path to the tenant.xml
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
        print 'Incorrect path to Tenant'
        exit(1)
    test_dash = []
    test_schema = []
    for child in root.iter('dashboard'):
        for char in dash_char_name:
            char = char[:-1]
            if str(child.attrib['name']).startswith(char):
                test_dash.append(child.attrib)
    for test in test_dash:
        if test not in dashboard_attributes:
            dash_confirmed.append(test['name'])
            dashboard_attributes.append(test)
    for child in root.iter('schema-definition'):
        for char in schema_char_name:
            char = char[:-1]
            if str(child.attrib['name']).startswith(char):
                test_schema.append(child.attrib['name'])
    for schema in test_schema:
        if schema not in schema_confirmed:
            schema_confirmed.append(schema)

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
                                if str_tup[1].strip()[-1] == '*':
                                    schema_char_name.append(str_tup[1].strip())
                                else:
                                    schema_name.append(str_tup[1].strip())
                            elif key == 'dash_name':
                                if str_tup[1].strip()[-1] == '*':
                                    dash_char_name.append(str_tup[1].strip())
                                else:
                                    dash_name.append(str_tup[1].strip())
                            elif key == 'datasource':
                                datasource_name.append(str_tup[1].strip())
                            elif key == 'dash_folder':
                                folder_name.append(str_tup[1].strip())

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
        for schema in schema_confirmed:
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
        for dash in dashboard_attributes:
            if child.attrib['guid'] == dash['guid']:
                for sub in child.iter():
                    if 'data' == sub.tag:
                        for dbn in sub.iter():
                            if bool(dbn.attrib):
                                dashboard_href[dash['guid']] = dbn.attrib
    for child in root.iter('data-source'):
        for datasource in datasource_confirmed:
            if child.attrib['name'] == datasource:
                datasource_attributes.append(child.attrib)
                for sub in child.iter():
                    if 'data' == sub.tag:
                        for dn in sub.iter():
                            if bool(dn.attrib):
                                datasource_href[datasource] = dn.attrib

def create_tenant_xml(path, map):
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
    # tenant_attributes can be hardcoded since there are no parameters that need to be changed

    data_attribute = {'xmlns:xi': "http://www.w3.org/2001/XInclude"}
    # data_attribute will always be these url

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
    for dash in datasource_confirmed:
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
    for schema in schema_confirmed:
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

    #######################################################################
    dash_attributes = {}
    for dash in dashboard_attributes:
        dash_attributes = dash
        if dash_attributes['parent'] == dash_attributes['owner-id']:
            dashboard = SubElement(catalog, 'dashboard', dash_attributes)
            dash_data = SubElement(dashboard, 'data', data_attribute)
            for guid in dashboard_href:
                if guid == dash['guid']:
                    href_dash['href'] = dashboard_href[dash['guid']]['href']
            xi = SubElement(dash_data, 'xi:include', href_dash)
        if dash_attributes['parent'] != dash_attributes['owner-id']:
            struct1 = get_parent(map, dash_attributes)
            for f in reversed(struct1):
                folder = SubElement(catalog, 'folder',f)
            dashboard = SubElement(folder, 'dashboard', dash_attributes)
            dash_data = SubElement(dashboard, 'data', data_attribute)
            for guid in dashboard_href:
                if guid == dash['guid']:
                    href_dash['href'] = dashboard_href[dash['guid']]['href']
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

def zip_up(zip_path, tempstamp):
    """
    Function is used to zip the new folder
        args:
            zip_path: the path to the folder
        returns:
            nothing
        prints:
            Debug statements
    """
    new_zipfile_name = zip_path[:-3] + tempstamp + '.zip'
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
    for schema in schema_confirmed:
        if bool(schema_href):
            if bool(schema_href[schema]):
                sn = schema_href[schema]['href']
                try:
                    os.rename(src + os.sep + sn, dest + os.sep + sn)
                except Exception, e:
                    print 'Unable to move schema files'
                    exit(1)
    for dashboard in dashboard_attributes:
        if bool(dashboard_href):
            dn = dashboard_href[dashboard['guid']]['href']
            try:
                os.rename(src + os.sep + dn, dest + os.sep + dn)
            except Exception, e:
                print 'Unable to move dashboard files'
                exit(1)
    for datasource in datasource_confirmed:
        if bool(datasource_href):
            if bool(datasource_href[datasource]):
                dsn = datasource_href[datasource]['href']
                try:
                    os.rename(src + os.sep + dsn, dest + os.sep + dsn)
                except Exception, e:
                    print 'Unable to move datasource files'
                    exit(1)
    for schema in schema_confirmed:
        if bool(loader_href):
            if bool(loader_href[schema]):
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
    txt_file.write('This file contains the names of the requested files.')
    if bool(schema_confirmed):
        txt_file.write('\n\nThe following schemas are included\n')
        txt_file.write('\n'.join(schema_confirmed))
    if bool(dash_confirmed):
        txt_file.write('\n\n')
        txt_file.write('The following dashboards are included\n')
        txt_file.write('\n'.join(dash_confirmed))
    if bool(datasource_confirmed):
        txt_file.write('\n\n')
        txt_file.write('The following datasources are included\n')
        txt_file.write('\n'.join(datasource_confirmed))
    txt_file.close()

def filecreation(list, filename):
    mydir = os.path.join(os.getcwd(), datetime.datetime.now().strftime('%Y-%m-%d_%H-%M-%S'))
    try:
        os.makedirs(mydir)
        return mydir
    except OSError, e:
        if e.errno != 17:
            raise # This was not a "directory exist" error..


# Dictionary for parsing the input.txt file
config_defaults = {'schema_name': 'default', 'dash_name': 'default', 'datasource': 'default',
                   'zipfile_home': 'default', 'testfile_home': 'default', 'unzipped_home': "default",
                   'txt_home': 'default', 'dash_folder': 'default'}


# Lists the contain the wanted names of schemas/dashboards/datasources
schema_name = []
dash_name = []
datasource_name = []
dash_middle = []
schema_confirmed = []
dash_confirmed = []
datasource_confirmed = []
dash_char_name = []
schema_char_name = []


# These are lists but contain dictionaries in each node where the key is the wanted file name and value is its xml attributes
schema_attributes = []
dashboard_attributes = []
datasource_attributes = []

# Dictionaries of dictionaries that contain the href as the value and key is the wanted file name
schema_href = {}
loader_href = {}
dashboard_href = {}
datasource_href = {}

folder_name = []


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
    tempStamp = datetime.datetime.now().strftime('%Y-%m-%d_%H-%M-%S')
    wdPath = create_directory((config_defaults['testfile_home']), tempStamp)
    print wdPath
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
    parent_tree = parse_folder_structure(config_defaults['unzipped_home'])
    print parent_tree
    confirm_input(config_defaults['unzipped_home'])
    get_all_dash(parent_tree)
    get_names_from_folder(config_defaults['unzipped_home'])
    get_names_from_wild_char(config_defaults['unzipped_home'])
    parse(config_defaults['unzipped_home'])
    create_tenant_xml(tmpPath, parent_tree)
    move_files(config_defaults['unzipped_home'], tmpPath)
    zip_up(tmpPath, tempStamp)
    create_txt_file(wdPath)
    print 'Cleaning Up...'
    shutil.rmtree(tmpPath)
    shutil.rmtree(extractPath)
    print "Exiting script..."


if __name__ == '__main__':
    main()
