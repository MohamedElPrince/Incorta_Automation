from xml.etree import ElementTree
from lxml import etree
import os
import file_tools
import subprocess

"""
This file contains an array of functions to pull schema, dashboard, and tenant data.
This file contains the validation between import and export test cases
*NOTE* Many functions return multiple data structures
"""



def validation(import_dictionary, export_dictionary, wd_path):

	"""
	Compares import and export files. If any differences are found
	they are outputted to the log file as a .dif file. If there
	are no changes between the import and export files they will be
	outputted to a log file as a .suc file. All log files will be
	contained within the Output folder
	"""

	print "WD PATH", wd_path
	output_path = wd_path+os.sep+'Output/'
	print "OUTPUT PATH", output_path

	for key in import_dictionary:
		export_file_path = export_dictionary.get(key, None)
		if export_file_path != None:

			import_tree = etree.parse(import_dictionary[key])
			export_tree = etree.parse(export_dictionary[key])

			import_tree_string = etree.tostring(import_tree, encoding="unicode", method="xml")
			export_tree_string = etree.tostring(export_tree, encoding="unicode", method="xml")

			import_tree_string = import_tree_string.replace("u'", "")
			export_tree_string = export_tree_string.replace("u'", "")

			set1 = set(etree.tostring(i, method='c14n') for i in import_tree.getroot())
			set2 = set(etree.tostring(i, method='c14n') for i in export_tree.getroot())

			path_list = export_dictionary[key].split('/')
			temp_string = path_list[5] + '_' + path_list[6] + '_' + path_list[9]

			if set1 != set2:
				# print "Data Corruption between"
				# print "Import File: ", os.path.basename(import_dictionary[key]), "Export File: ", os.path.basename(export_dictionary[key])
				# print "\n"
				temp_string = temp_string + '.dif'
				log_name = output_path + temp_string

				import_list = import_tree_string.split()
				export_list = export_tree_string.split()
				import_contents = []
				export_contents = []

				f = open(log_name, 'w')
				header_string = 'Imported File: \n' + import_dictionary[key] + '\nExported File: \n' + export_dictionary[key]
				f.write(header_string)
				f.write('\n\n')
				header2_string = "\n\n Outputting Differences.... \n\n"
				f.write(header2_string)
				header3_string = "IMPORT CONTENT:  " + '-----------------------------------------------' + " EXPORT CONTENT: "
				f.write(header3_string)
				f.write('\n\n')

				for i in range(len(import_list)):
					if import_list[i] != export_list[i]:
						import_contents.append(import_list[i])
						export_contents.append(export_list[i])
				for i in range(len(import_contents)):
					import_temp = import_contents[i].replace("u'", "")
					export_temp = export_contents[i].replace("u'", "")
					diff_string = import_temp + '------------------------' + export_temp
					f.write(diff_string)
					f.write('\n')
				f.close()

				# diff_command = 'diff ' + import_dictionary[key] + ' ' + export_dictionary[key] + ' > ' + log_name
				# subprocess.call(diff_command, shell = True)

			else:
				# print "Data Validated"
				# print "Import File: ", os.path.basename(import_dictionary[key]), "Export File: ", os.path.basename(export_dictionary[key])
				# print "\n"
				temp_string = temp_string + '.suc'
				print "TEMP STRING", temp_string
				log_name = output_path + temp_string
				print "LOG NAME", log_name
				f = open(log_name, 'w')
				f.close()

		else:
			path_list = import_dictionary[key].split('/')
			temp_string = path_list[5] + '_' + path_list[6] + '_' + path_list[9] + '_' + 'NF' + '.dif'
			log_name = output_path + temp_string

			f = open(log_name, 'w')
			f.close()


def grab_import_export_path(wd_test_case_path):
	"""
	Returns both Export and Import paths per test case
	"""

	sub_path  = file_tools.get_subdirectories(wd_test_case_path)
	for x in sub_path:
		if x == 'Import_Files':
			import_path = x
			import_path = wd_test_case_path+'/'+import_path
		if x == 'Export_Files':
			export_path = x
			export_path = wd_test_case_path+'/'+export_path
	return import_path, export_path


def get_dashboards_info(path):
	"""
	Returns dashboard Ids and Dashboard Tenants in a dictionary depending on if export or import path is given
	Id serves as the key and the file name becomes the value
	A list of dashboard names is also returned to allow the main script to run the export dashboards command
	"""

	data = file_tools.get_subdirectories(path)
	dash_ids = {}
	dashboard_name_list = []
	dash_tenants = {}
	tenant_id = ""
	tenant_full_path = ""

	for folder in data:
		if folder == 'dashboards':
			dashboards_path = path+os.sep+folder
			dashboards = file_tools.get_subdirectories(dashboards_path)


			for dashboard in dashboards:
				dashboard_cases_path = dashboards_path + os.sep + dashboard
				dashboard_cases = file_tools.get_subdirectories(dashboard_cases_path)


				for dash_data in dashboard_cases:
					if dash_data == 'dashboards':
						full_dash_path = dashboard_cases_path+ os.sep + dash_data
						dash_file_names =file_tools.get_subdirectories(full_dash_path)

						for names in dash_file_names:
							temp_full_path = full_dash_path + os.sep + names
							try:
								with open(temp_full_path, 'rt') as f:
									dash_tree = ElementTree.parse(f)
							except Exception, e:
								print "Unable to open dashboard", names
							try:

								for node in dash_tree.iter('table'):
									dash_ids[node.attrib.get('id')] = temp_full_path
									tenant_id = node.attrib.get('id')

							except Exception, e:
								print "Unable to read dashboard", names

							try:
								for node in dash_tree.iter('report'):
									dashboard_name_list.append(node.attrib.get('name'))
							except Exception, e:
								print "Unable to read name from dashboard", names
					elif dash_data == 'tenant.xml':
						tenant_full_path = dashboard_cases_path + os.sep + dash_data
						try:
							with open(tenant_full_path, 'rt') as f:
								tenant_tree = ElementTree.parse(f)
						except Exception, e:
							print "Unable to open Dashboard tenant", dash_data


				dash_tenants[tenant_id] = tenant_full_path

	return dash_ids, dash_tenants, dashboard_name_list


def get_schemas_info(path):
	"""
	Returns schemas, schema loaders, and schema tenants in separate dictionaries depending on if export or import path is given
	Schema Name serves as the key and the file name becomes the value
	A list of schema names is also returned to allow the main script to run the export schemas command
	"""

	data_types = file_tools.get_subdirectories(path)
	schema_names = {}
	schema_loaders = {}
	schema_tenants = {}
	schema_name_list = []

	for folders in data_types:
		if folders == 'schemas':
			schemas_path = path + '/' + folders
			schemas = file_tools.get_subdirectories(schemas_path)

			for schema in schemas:
				schema_cases_path = schemas_path+os.sep+schema
				schema_cases = file_tools.get_subdirectories(schema_cases_path)

				for schema_data in schema_cases:
					if schema_data == 'schemas':
						schema_full_path = schema_cases_path+os.sep+schema_data
						schema_files = file_tools.get_subdirectories(schema_full_path)

						for file in schema_files:
							if 'loader' not in file:
								temp_full_path = schema_full_path + os.sep + file
								try:
									with open(temp_full_path, 'rt') as f:
										schema_tree = ElementTree.parse(f)
								except Exception, e:
									print "Unable to open schema", file
								try:
									for node in schema_tree.iter('schema'):
										schema_names[node.attrib.get('name')] = temp_full_path
								except Exception, e:
									print "Unable to read schema"
							elif 'loader' in file:
								temp_full_path = schema_full_path + os.sep + file
								try:
									with open(temp_full_path, 'rt') as f:
										schema_tree = ElementTree.parse(f)
								except Exception, e:
									print "Unable to open schema", file
								try:

									for node in schema_tree.iter('loader'):
										schema_loaders[node.attrib.get('name')] = temp_full_path
										schema_name_list.append(node.attrib.get('name'))
								except Exception, e:
									print "Unable to read schema"
					elif schema_data == 'tenant.xml':
						tenant_full_path = schema_cases_path+os.sep+schema_data
						try:
							with open(tenant_full_path, 'rt') as f:
								tenant_tree = ElementTree.parse(f)
						except Exception, e:
							print "Unable to open schema", schema_data
						try:
							for node in tenant_tree.iter('schema-definition'):
								schema_tenants[node.attrib.get('name')] = tenant_full_path
						except Exception, e:
							print "Unable to read schema"

	return schema_names, schema_loaders, schema_tenants, schema_name_list


#-----------------------------------------------TESTING------------------------------------------------------

#IMPORT DATA STRUCTURES

import_dash_ids = {}
import_dash_tenants = {}
import_dashboard_names_list = []

import_schema_names = {}
import_schema_loaders = {}
import_schema_tenants = {}
import_schema_names_list = []

#EXPORT DATA STRUCTURES

export_dash_ids = {}
export_dash_tenants = {}
export_dashboard_names_list = []

export_schema_names = {}
export_schema_loaders = {}
export_schema_tenants = {}
export_schema_names_list = []



import_path, export_path = grab_import_export_path('/Users/Nadim_Incorta/IncortaTesting/07:14:2016-13:57:00/CSV_DataSources/BinFunction')


import_dash_ids, import_dash_tenants, import_dashboard_names_list = get_dashboards_info(import_path)
import_schema_names, import_schema_loaders, import_schema_tenants, import_schema_names_list = get_schemas_info(import_path)



export_dash_ids, export_dash_tenants, export_dashboard_names_list = get_dashboards_info(export_path)
export_schema_names, export_schema_loaders, export_schema_tenants, export_schema_names_list = get_schemas_info(export_path)


validation(import_dash_ids, export_dash_ids, '/Users/Nadim_Incorta/IncortaTesting/07:14:2016-13:57:00')



# print "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"

#
# print "Extracted from IMPORTS\n"
#
#
# print "-------Dashboards--------\n"
# print "Imported dashboards: \n", import_dash_ids
# print "Imported Dashboard Tenants: \n", import_dash_tenants
# print "\n-------Schemas---------\n"
# print "Imported Schemas: \n", import_schema_names
# print "Imported Schema Loaders: \n", import_schema_loaders
# print "Imported Schema Tenants: \n", import_schema_tenants
#
# print "\nPrinting Dashboard Names \n"
# print import_dashboard_names_list
# print "\n Printing Schema Names List \n"
# print import_schema_names_list
#
#
# print "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"
# print "\nExtracted from EXPORTS\n"
# print "-------Dashboards--------\n"
# print "Exported dashboards: \n", export_dash_ids
# print "Exported Dashboard Tenants: \n", export_dash_tenants
# print "\n-------Schemas---------\n"
# print "Exported Schemas: \n", export_schema_names
# print "Exported Schema Loaders: \n", export_schema_loaders
# print "Exported Schema Tenants: \n", export_schema_tenants
#
# print "\nPrinting Dashboard Names \n"
# print export_dashboard_names_list
# print "\n Printing Schema Names List \n"
# print export_schema_names_list
#
# print "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"






