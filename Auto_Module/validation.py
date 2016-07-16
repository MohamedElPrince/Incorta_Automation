from xml.etree import ElementTree
from lxml import etree
import os
import file_tools





"""
Takes in schema names,
searches through import/export directory for schema names
Implements file-to-file comparison, and prints if files are same
If not all changes are written in an external log file
"""

def schema_validaiton(schema_names):

	import_path = ('/Users/Nadim_Incorta/Desktop/Import_vs_Export_Validation/schema_to_be_imported/schemas/887_schema.xml')
	export_path = ('/Users/Nadim_Incorta/Desktop/Import_vs_Export_Validation/exported_schema/schemas/140_schema.xml')


	tree1 = etree.parse(import_path)
	tree2 = etree.parse(export_path)
	root1 = tree1.getroot()
	root2 = tree2.getroot()


	tree1_string = etree.tostring(tree1, encoding="unicode", method="xml")
	tree2_string = etree.tostring(tree2, encoding="unicode", method="xml")

	tree1_string = tree1_string.replace("u'", "")
	tree2_string = tree2_string.replace("u'", "")


	elem1 = [x for x in tree1_string.split()]
	elem2 = [x for x in tree2_string.split()]


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









# def grab_schema_names(unziped_path):
#
# 	schema_list = []
# 	load = '_loader'
# 	schema_names = []
#
# 	for root, dirs, files in os.walk(unziped_path):
# 		for directories in dirs:
# 			temp = unziped_path+'/'+directories
# 			if directories == 'dashboards':
# 				dash_files = os.listdir(temp)
# 			if directories == 'schemas':
# 				schema_files = os.listdir(temp)
#
# 	for x in schema_files:
# 		if load not in x:
# 			schema_list.append(x)
#
# 	schema_path = unziped_path+'/'+'schemas'
# 	print schema_path
#
# 	for item in schema_list:
# 		full_path = schema_path+'/'+item
#
# 		try:
# 			with open(full_path, 'rt') as f:
# 				test_tree = ElementTree.parse(f)
# 		except Exception, e:
# 			print "Unable to open file", item
#
# 		try:
# 			for node in test_tree.iter('schema'):
# 				name = node.attrib.get('name')
# 			schema_names.append(name)
# 		except Exception, e:
# 			print "Unable to Read File", item
#
# 	print "\n Returning names of schemas"
# 	print schema_names
# 	return schema_names

"""
Returns both Export and Import paths per test case
"""

def grab_import_path(wd_test_case_path):

	sub_path  = file_tools.get_subdirectories(wd_test_case_path)
	for x in sub_path:
		if x == 'Import_Files':
			import_path = x
			import_path = wd_test_case_path+'/'+import_path
		if x == 'Export_Files':
			export_path = x
			export_path = wd_test_case_path+'/'+export_path
	return import_path, export_path

"""
Returns dashboard Ids in a dictionary depending on if export or import path is given
Id serves as the key and the file name becomes the value
"""

def get_dashboard_ids(path):
	data = file_tools.get_subdirectories(path)
	ids = {}
	#Dashboard/Schema/Data Sources Level
	for x in data:
		if x == 'dashboards':
			dashboards_path = path+'/'+x
			dashboard_names = file_tools.get_subdirectories(dashboards_path)
			#Dashboard Zip File names Level
			for y in dashboard_names:
				dashboard_names_path = dashboards_path + '/' + y
				dashboards = file_tools.get_subdirectories(dashboard_names_path)
				#Dashboards/ Tenant Level
				for z in dashboards:
					if z == 'dashboards':
						full_dash_path = dashboard_names_path+ '/' + z
					dash_file_names =file_tools.get_subdirectories(full_dash_path)
					#Dashboard File Name Level
					for names in dash_file_names:
						#print names
						temp_full_path = full_dash_path+'/'+names
						try:
							with open(temp_full_path, 'rt') as f:
								dash_tree = ElementTree.parse(f)
						except Exception, e:
							print "Unable to open dashboard", names
						try:
							#Inside of Dashboard
							for node in dash_tree.iter('table'):
								ids[node.attrib.get('id')] = names
						except Exception, e:
							print "Unable to read dashboard", names

	print ids
	return ids



"""
Grabs Schema Names from Schema Directory,
Returns as list to be implemented in validation function
"""

def grab_schema_names(path):

	data_types = file_tools.get_subdirectories(path)
	schema_names = {}

	for folders in data_types:
		if folders == 'schemas':
			schemas_path = path + '/' + folders
			







import_dash_ids = {}
export_dash_ids = {}

import_path, export_path = grab_import_path('/Users/Nadim_Incorta/IncortaTesting/07:14:2016-13:57:00/CSV_DataSources/BinFunction')
print "Extracted Dash IDS from IMPORTS"
import_dash_ids = get_dashboard_ids(import_path)
print "Extracted Dash IDS from EXPORTS"
export_dash_ids = get_dashboard_ids(export_path)









