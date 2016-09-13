
from xml.etree import ElementTree
from lxml import etree
import os, platform
import file_tools
from customLogger import mainLogger, writeLogMessage

"""
This file contains an array of functions to pull schema, dashboard, and tenant data.
This file contains the validation between import and export test cases
*NOTE* Many functions return multiple data structures
"""

def tenant_editor(path):
	"""
	Uses Regex Commands too suppress dynamic lines in tenant files
	This is done to ensure the validator does not throw unnecessary
	diffs
	Added functionality for multiple operating systems. Code now checks
	for type of operating system to run distinct set of commands.
	"""
	# Takes Import or Export Path as parameter
	system = platform.system()
	if 'Darwin' in system:
		for root, dir, files in os.walk(path):
			for file in files:
				if file == 'tenant.xml':
					exportTime_arg = """ sed -i "" 's/\(exportTime=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(exportTime_arg)

					id_arg = """ sed -i "" 's/\( id=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(id_arg)

					owner_id_arg = """ sed -i "" 's/\(owner-id=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(owner_id_arg)

					PATH_arg = """ sed -i "" 's/\(path=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(PATH_arg)

					Href_arg = """ sed -i "" 's/\(href=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(Href_arg)

					Parent_arg = """ sed -i "" 's/\(parent=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(Parent_arg)
	elif 'Linux' in system:
		for root, dir, files in os.walk(path):
			for file in files:
				if file == 'tenant.xml':
					exportTime_arg = """ sed -i 's/\(exportTime=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(exportTime_arg)

					id_arg = """ sed -i 's/\( id=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(id_arg)

					owner_id_arg = """ sed -i 's/\(owner-id=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(owner_id_arg)

					PATH_arg = """ sed -i 's/\(path=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(PATH_arg)

					Href_arg = """ sed -i 's/\(href=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(Href_arg)

					Parent_arg = """ sed -i 's/\(parent=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(Parent_arg)

def validation(test_suite_name, import_dictionary, export_dictionary, XML_MetaData_Validation_Path, dictionary_type):
	"""
	Compares import and export files. If any differences are found
	they are outputted to the log file as a .dif file. If there
	are no changes between the import and export files they will be
	outputted to a log file as a .suc file. All log files will be
	contained within the Output folder
	"""
	meta_data_directory = file_tools.create_directory(XML_MetaData_Validation_Path, dictionary_type)
	if meta_data_directory == None:
		meta_data_path = file_tools.get_path(XML_MetaData_Validation_Path, dictionary_type)
	else:
		meta_data_path = str(meta_data_directory)
	output_path = meta_data_path + os.sep

	for key in import_dictionary:
		export_file_path = export_dictionary.get(key, None)

		try:
			if export_file_path != None:
				try:
					import_tree = etree.parse(import_dictionary[key])
					import_tree_string = etree.tostring(import_tree, encoding="unicode", method="xml")
					import_tree_string = import_tree_string.replace("u'", "")
					set1 = set(etree.tostring(i, method='c14n') for i in import_tree.getroot())
				except Exception:
					print import_dictionary[key], "MISSING"

				try:
					export_tree = etree.parse(export_dictionary[key])
					export_tree_string = etree.tostring(export_tree, encoding="unicode", method="xml")
					export_tree_string = export_tree_string.replace("u'", "")
					set2 = set(etree.tostring(i, method='c14n') for i in export_tree.getroot())
				except Exception:
					print export_dictionary[key], "MISSING"

				#VALIDATION

				path_list = export_dictionary[key].split('/')
				COUNT = 0
				for path in path_list:
					if test_suite_name == path:
						temp_start_index = COUNT
					COUNT += 1
				temp_string = path_list[temp_start_index] + '_' + path_list[temp_start_index+1] + '_' + path_list[temp_start_index+4]
				if set1 != set2:
					# FILES ARE DIFFERENT
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
					header3_string = ">>> IMPORT CONTENT"
					header_newline = '\n'
					header4_string = "<<< EXPORT CONTENT"
					f.write(header3_string)
					f.write(header_newline)
					f.write(header4_string)
					f.write('\n\n')

					for i in range(len(import_list)):
						if import_list[i] != export_list[i]:
							import_contents.append(import_list[i])
							export_contents.append(export_list[i])
					for i in range(len(import_contents)):
						import_temp = import_contents[i].replace("u'", "")
						export_temp = export_contents[i].replace("u'", "")
						diff_imp_string = '>>> ' + import_temp
						f.write(header_newline)
						diff_exp_string = '<<< ' + export_temp
						f.write(diff_imp_string)
						f.write(header_newline)
						f.write(diff_exp_string)
						f.write('\n\n')
					f.close()
				else:
					# FILES ARE THE SAME
					temp_string = temp_string + '.suc'
					log_name = output_path + temp_string
					f = open(log_name, 'w')
					f.close()
			else:
				path_list = import_dictionary[key].split('/')
				temp_string = path_list[5] + '_' + path_list[6] + '_' + path_list[9] + '_' + 'NF' + '.dif'
				log_name = output_path + temp_string
				try:
					f = open(log_name, 'w')
					f.close()
				except Exception, e:
					print "CANT CREATE FILE", log_name
		except Exception:
			print "MISSING FILE"
			path_list = import_dictionary[key].split('/')
			temp_string = path_list[5] + '_' + path_list[6] + '_' + path_list[9] + '_' + 'NF' + '.dif'
			log_name = output_path + temp_string
			try:
				f = open(log_name, 'w')
				f.close()
			except Exception, e:
				print "CANT CREATE FILE", log_name
				writeLogMessage('%s %s' % ("CANT CREATE FILE", log_name), mainLogger, 'critical')

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
								tree = ElementTree.parse(dashboard_cases_path + os.sep + 'tenant.xml')
								root = tree.getroot()
							except Exception, e:
								print 'Incorrect path to Tenant'
								exit(1)
							file_path = ''
							for child in root.iter('folder'):
								file_path = file_path + os.sep + child.attrib['name']
							for child in root.iter('dashboard'):
								file_path += os.sep + child.attrib['name']
								# print file_path
								dashboard_name_list.append(file_path[1:])

							try:
								with open(temp_full_path, 'rt') as f:
									dash_tree = ElementTree.parse(f)
							except Exception, e:
								print "Unable to open dashboard", names
								writeLogMessage('%s %s' % ("Unable to open dashboard", names), mainLogger, 'critical')
							try:
								for node in dash_tree.iter('table'):
									dash_ids[node.attrib.get('id')] = temp_full_path
									tenant_id = node.attrib.get('id')
							except Exception, e:
								print "Unable to read dashboard", names
								writeLogMessage('%s %s' % ("Unable to read dashboard", names), mainLogger, 'critical')
							# try:
							# 	for node in dash_tree.iter('report'):
							# 		dashboard_name_list.append(node.attrib.get('name'))
							# except Exception, e:
							# 	print "Unable to read name from dashboard", names
							# 	writeLogMessage('%s %s' % ("Unable to read name from dashboard", names), mainLogger, 'critical')
					elif dash_data == 'tenant.xml':
						tenant_full_path = dashboard_cases_path + os.sep + dash_data
						try:
							with open(tenant_full_path, 'rt') as f:
								tenant_tree = ElementTree.parse(f)
						except Exception, e:
							print "Unable to open Dashboard tenant", dash_data
							writeLogMessage('%s %s' % ("Unable to open Dashboard tenant", dash_data), mainLogger, 'critical')
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
									writeLogMessage('%s %s' % ("Unable to open schema", file), mainLogger,'critical')
								try:
									for node in schema_tree.iter('schema'):
										schema_names[node.attrib.get('name')] = temp_full_path
								except Exception, e:
									print "Unable to read schema"
									writeLogMessage('Unable to Read Schema', mainLogger, 'critical')
							elif 'loader' in file:
								temp_full_path = schema_full_path + os.sep + file
								try:
									with open(temp_full_path, 'rt') as f:
										schema_tree = ElementTree.parse(f)
								except Exception, e:
									print "Unable to open schema", file
									writeLogMessage('%s %s' % ("Unable to open schema", file), mainLogger, 'critical')
								try:
									for node in schema_tree.iter('loader'):
										schema_loaders[node.attrib.get('name')] = temp_full_path
										schema_name_list.append(node.attrib.get('name'))
								except Exception, e:
									print "Unable to read schema"
									writeLogMessage('Unable to Read Schema', mainLogger, 'critical')
					elif schema_data == 'tenant.xml':
						tenant_full_path = schema_cases_path+os.sep+schema_data
						try:
							with open(tenant_full_path, 'rt') as f:
								tenant_tree = ElementTree.parse(f)
						except Exception, e:
							print "Unable to open schema", schema_data
							writeLogMessage('%s %s' % ("Unable to open schema", schema_data), mainLogger, 'critical')
						try:
							for node in tenant_tree.iter('schema-definition'):
								schema_tenants[node.attrib.get('name')] = tenant_full_path
						except Exception, e:
							print "Unable to read schema"
							writeLogMessage('Unable to Read Schema', mainLogger, 'critical')
	return schema_names, schema_loaders, schema_tenants, schema_name_list
