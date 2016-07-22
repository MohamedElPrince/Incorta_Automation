
from xml.etree import ElementTree
from lxml import etree
import os
import file_tools
import subprocess
import json
import glob
import re
"""
This file contains an array of functions to pull schema, dashboard, and tenant data.
This file contains the validation between import and export test cases
*NOTE* Many functions return multiple data structures
"""

def tenant_editor(path):

	#print "Editing tenants"
	#print import_path



	for root, dir, files in os.walk(path):
		for file in files:
			if file == 'tenant.xml':
				a = re.escape('sed -i ""')
				b = re.escape(' \'s/\(exportTime=\)"[^"]*"/\\1" "/g\' ')

				c = a + b
				# raw_path = os.path.join(root, file)
				# print raw_path
				# sub = subprocess.call(["sed"," -i "" ", ' \'s/\(exportTime=\)"[^"]*"/\\1" "/g\' ', os.path.join(root, file)], shell=True)
				# print sub
				#subprocess.call(c + os.path.join(root, file), shell=True)
				#outfile.close()
				#os.system(c + os.path.join(root, file))

				#subprocess.check_call(['sed', ' -i "" \'s/\(exportTime=\)"[^"]*"/\\1" "/g\' ' + os.path.join(root, file)])

				#subprocess.call(["sed", "-i "" ", 's/\(exportTime=\)"[^"]*"/\1" "/g ', os.path.join(root, file)])

				z = """ -i "" 's/\(exportTime=\)"[^"]*"/\\1" "/g' """ + os.path.join(root, file)

				#print z
				w = "sed" + z
				print "PRINTING PATH", os.path.join(root, file)
				os.system(w)

	print "-------------------------------------------------"







def validation(import_dictionary, export_dictionary, wd_path, test_suite_name, dictionary_type):

	"""
	Compares import and export files. If any differences are found
	they are outputted to the log file as a .dif file. If there
	are no changes between the import and export files they will be
	outputted to a log file as a .suc file. All log files will be
	contained within the Output folder
	"""

	# #exportTime
	# sed -i "" 's/\(exportTime=\)"[^"]*"/\1" "/g' tenant.xml
	#
	# DONT USE #Works for id -- need fix
	# sed -i "" 's/\(id=\)"[^"]*"/\1" "/g' tenant.xml
	#
	# #fix
	# sed -i "" 's/\( id=\)"[^"]*"/\1" "/g' tenant.xml
	#
	# #Owner-id
	# sed -i "" 's/\(owner-id=\)"[^"]*"/\1" "/g' tenant.xml
	#
	# #PATH
	# sed -i "" 's/\(path=\)"[^"]*"/\1" "/g' tenant.xml
	#
	# #HREF
	# sed -i "" 's/\(href=\)"[^"]*"/\1" "/g' tenant.xml




	output_path = wd_path+os.sep+'Output' + os.sep + test_suite_name + '_Summary' + os.sep
	file_tools.create_directory(output_path, dictionary_type)
	output_path = output_path + dictionary_type + os.sep

	for key in import_dictionary:


		export_file_path = export_dictionary.get(key, None)
		if export_file_path != None:

			import_tree = etree.parse(import_dictionary[key])
			export_tree = etree.parse(export_dictionary[key])

			import_tree_string = etree.tostring(import_tree, encoding="unicode", method="xml")
			export_tree_string = etree.tostring(export_tree, encoding="unicode", method="xml")

			import_tree_string = import_tree_string.replace("u'", "")
			export_tree_string = export_tree_string.replace("u'", "")

			# if dictionary_type == 'dashboard_tenants' || dictionary_type == 'schema_tenants':
			# 	print dictionary_type
			# 	file_name = export_dictionary[key]
			# 	print file_name
			# 	cmd = """sed -i "" 's/\(exportTime=\)"[^"]*"/\1" "/g' """
			# 	cmd_string = cmd + file_name
			# 	subprocess.call(cmd_string, shell=True)
            #





#continue validation
			set1 = set(etree.tostring(i, method='c14n') for i in import_tree.getroot())
			set2 = set(etree.tostring(i, method='c14n') for i in export_tree.getroot())

			path_list = export_dictionary[key].split('/')
			temp_string = path_list[5] + '_' + path_list[6] + '_' + path_list[9]

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
				header4_string = "<<< EXPORT CONTENT"
				f.write(header3_string)
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
					f.write('\n')
					diff_exp_string = '<<< ' + export_temp
					f.write(diff_imp_string)
					f.write(diff_exp_string)
					f.write('\n\n')
				f.close()

				# diff_command = 'diff ' + import_dictionary[key] + ' ' + export_dictionary[key] + ' > ' + log_name
				# subprocess.call(diff_command, shell = True)

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

# TESTING
