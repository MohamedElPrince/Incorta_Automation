import sys, os, subprocess, time, zipfile, logging
import os.path
from sys import argv
from shutil import copyfile
from xml.etree.ElementTree import Element, SubElement, Comment, tostring
import errno



# import Auto_Module
# from Auto_Module import dataLoad
# from Auto_Module import loadUsers
# from Auto_Module import test_suite_import
# from Auto_Module import export


# sys.argv[1] is the config file
config_file = sys.argv[1]

config_defaults = {'schema_name': 'default', 'dash_name': 'default', 'datasource': 'default'}

schema_name = []
dash_name =[]
datasource_name = []

def set_new_values(config_file):
    f = open(config_file, "r")
    lines = f.readlines()
    f.close()
    for line in lines:
        for key, value in config_defaults.items():
            if line[0] != '\n':
                if line[0] != '#':
                    temp_str = line
                    str_tup = temp_str.split("=", 1)
                    if key == str_tup[0]:
                        if key == 'schema_name':
                            schema_name.append(str_tup[1])
                            print str_tup[1]
                        elif key == 'dash_name':
                            dash_name.append(str_tup[1])
                            print str_tup[1]
                        elif key == 'datasource':
                            datasource_name.append(str_tup[1])
                            print str_tup[1]


def create_tenant_xml(guid, dashid, name):
    tenant_attributes = {"enabld": "true", "exportTime": "", "load-mode": "start",
                         "name": "", "partial": "true", "path": "/", "version": "1.0"}
    #tenant_attributes can be hardcoded since there are no parameters that need to be changed

    datasource_attributes = {'id': '', 'name': "", 'owner-id': '200'}
    schema_attributes = {'description': "", 'id': '101', 'name': 'A_01_CASE', 'owner-id': '200', 'type': '0'}
    dash_attributes = {'guid': str(guid), "id": str(dashid), 'name': str(name),
                           'owner-id': "200", 'parent': ""}
    #For the attributes the inputs are the names, ids of each file and the guid for the dashboards.

    data_attribute = {'xmlns:xi': "http://www.w3.org/2001/XInclude"}
    #data_attribute will always be these url

    href_schema = {"href": "schemas/101_schema.xml", "parse": "xml"}
    href_dash = {"href": "dashboards/100_dashboard.xml", "parse": "xml"}
    href_data = {"href": "datasources/100_datasource.xml", "parse": "xml"}
    #Href needs the name of the file with the dir.


    root = Element('tenant', tenant_attributes)  # This is the root tag which is tenant
    description = SubElement(root, 'description')
    config = SubElement(root, 'configurations')
    security = SubElement(root, 'security')
    roles = SubElement(security, 'roles')
    groups = SubElement(security, 'groups')
    users = SubElement(security, 'users')
    datapackage = SubElement(root, 'data-package')
    datafiles = SubElement(datapackage, 'data-files')
    datasources = SubElement(datapackage, 'data-sources')
    data_source = SubElement(datasources, 'data-source', datasource_attributes)
    data = SubElement(data_source, 'data', data_attribute)
    data_xi = SubElement(data, 'xi:include', href_data)
    schemas = SubElement(datapackage, 'schemas')
    schema_def = SubElement(schemas, 'schema-definition', schema_attributes)
    schema_data = SubElement(schema_def, 'schema-data', data_attribute)
    schema_xi = SubElement(schema_data, 'xi:include', href_schema)
    loader = SubElement(schema_def, 'loader-data', data_attribute)
    loader_xi = SubElement(loader, 'xi:include', href_schema)
    sessionvar = SubElement(datapackage, 'session-variables')
    catalog = SubElement(root, 'catalog')
    dashboard = SubElement(catalog, 'dashboard', dash_attributes)
    dash_data = SubElement(dashboard, 'data', data_attribute)
    xi = SubElement(dash_data, 'xi:include', href_dash)
    print tostring(root)

    # output_file = open('/Users/Bashir_Khan/Desktop/sample_tenant.xml', 'w')
    # output_file.write('<?xml version="1.0" encoding="UTF-8"?>')
    # output_file.write(tostring(root))
    # output_file.close()

# def add_schema(root):



def extraction(zip_file):
    fn = zipfile.ZipFile(zip_file, 'r')
    fn.extractall('/Users/Bashir_Khan/Desktop/testingextraction')
    fn.close()

def zip_up(zip_path):
    fn = zipfile.ZipFile(zip_path, 'w', zipfile.ZIP_DEFLATED)


def create_txt_file(contents):
    txt_file = open('/Users/Bashir_Khan/Desktop/content.txt', 'w')
    txt_file.write(contents)
    txt_file.close()



# create_tenant_xml("98e77650-bd45-45dd-b577-b447a781f8c", 100, 'A_04_CASE_BIN Dashboard')
extraction("/Users/Bashir_Khan/Desktop/tmt_export_original.zip")
# create_txt_file("hello this is a sample txt file")
# zip_up('/Users/Bashir_Khan/Desktop/testingextraction')
# set_new_values('/Users/Bashir_Khan/PycharmProjects/ziputil/input.txt')