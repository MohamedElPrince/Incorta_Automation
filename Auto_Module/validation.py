from xml.etree import ElementTree
from lxml import etree
import os
import file_tools






def data_validaiton(schema_names, import_path, export_path):
	"""
	Takes in schema names,
	searches through import/export directory for schema names
	Implements file-to-file comparison, and prints if files are same
	If not all changes are written in an external log file
	"""

	#import_path = ('/Users/Nadim_Incorta/Desktop/Import_vs_Export_Validation/schema_to_be_imported/schemas/887_schema.xml')
	#export_path = ('/Users/Nadim_Incorta/Desktop/Import_vs_Export_Validation/exported_schema/schemas/140_schema.xml')


	tree1 = etree.parse(import_path)
	tree2 = etree.parse(export_path)

	tree1_string = etree.tostring(tree1, encoding="unicode", method="xml")
	tree2_string = etree.tostring(tree2, encoding="unicode", method="xml")

	tree1_string = tree1_string.replace("u'", "")
	tree2_string = tree2_string.replace("u'", "")


	#elem1 = [x for x in tree1_string.split()]
	#elem2 = [x for x in tree2_string.split()]


	set1 = set(etree.tostring(i, method='c14n') for i in tree1.getroot())
	set2 = set(etree.tostring(i, method='c14n') for i in tree2.getroot())

	if set1 != set2:
		print "Data Corruption"
		f = open('VALID_LOG_FILE.log', 'w')
		log_header = ('Import_File ------------ Export File\n')
		f.write('Tracked Differences...\n')
		f.writelines(log_header)
		import_list = tree1_string.split()
		export_list = tree2_string.split()
		C = []
		D = []
		for i in range(len(import_list)):
			if import_list[i] != export_list[i]:
				C.append(import_list[i])
				D.append(export_list[i])

		for i in range(len(C)):
			temp1 = C[i].replace("u'", "")
			temp2 = D[i].replace("u'", "")
			temp_string = temp1+'-------'+temp2
			f.write(temp_string)
			f.write('\n')

		f.close()

	else:
		print "Data Validated"


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
	"""

	data = file_tools.get_subdirectories(path)
	dash_ids = {}
	dashboard_name_list = []
	dash_tenants = {}
	tenant_name = ""
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
						try:

							for node in tenant_tree.iter('dashboard'):
								tenant_name = dash_data
						except Exception, e:
							print "Unable to read Dashboard Tenant"

				dash_tenants[tenant_id] = tenant_full_path

	return dash_ids, dash_tenants, dashboard_name_list


def get_schemas_info(path):
	"""
	Grabs Schema Names from Schema Directory,
	Returns as list to be implemented in validation function
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



import_dash_ids = {}
import_dash_tenants = {}
export_dash_ids = {}

import_schema_names = {}
import_schema_loaders = {}
import_schema_tenants = {}
export_schema_names = {}

dashboard_names_list = []
schema_names_list = []


import_path, export_path = grab_import_export_path('/Users/Nadim_Incorta/IncortaTesting/07:14:2016-13:57:00/CSV_DataSources/BinFunction')

print "Extracted from IMPORTS\n"
import_dash_ids, import_dash_tenants, dashboard_names_list = get_dashboards_info(import_path)
import_schema_names, import_schema_loaders, import_schema_tenants, schema_names_list = get_schemas_info(import_path)

print "-------Dashboards--------\n"
print "Imported dashboards: \n", import_dash_ids
print "Imported Dashboard Tenants: \n", import_dash_tenants
print "\n-------Schemas---------\n"
print "Imported Schemas: \n", import_schema_names
print "Imported Schema Loaders: \n", import_schema_loaders
print "Imported Schema Tenants: \n", import_schema_tenants

print "\nPrinting Dashboard Names \n"
print dashboard_names_list
print "\n Printing Schema Names List \n"
print schema_names_list










