#! /usr/bin/env python

#Portrays all table attributes of a specified schema
#Check for Table Name, Extract Query Source, Type, Incremental, Query, Data Source, Key, Columns with a Key
#
# July 12, 2016 by 
# NADIM SARRAS



import csv, sys, incorta, json as json, re as regex, xml.etree.ElementTree as xml; 


"""
SCHEMAS [] contains a list of all schemas to be analyzed from the command line 
Writes all schema information to a csv file 
"""

SCHEMAS = []
f = open('schemas.csv', 'wt')
writer = csv.writer(f)
writer.writerow(('SCHEMA NAME', ' ', ' ', 'TABLE_INFORMATION '))
writer.writerow((' ', 'TABLE_NAME', ' EXTRACT_QUERY_SOURCE', ' TYPE', ' INCREMENTAL', ' QUERY?', ' DATA SOURCE/FILE', ' KEY?', ' KEY_COLUMN_NAMES'))

"""
Function takes table input and parses table for pattern matching XML
	args:
		session(string): The session to incorta
		schema(xml.etree.ElementTree): The schema object
	returns: 
		Nothing
	prints:
		Prints out the table with Table Name, Type, Incremental, Query, Key, Columns with a Key
"""

def display_schema(session, schema):
	r = incorta.get(session, "/service/schema/getSchema", {"schemaId": schema["id"]})
	try:
		schema_xml = xml.fromstring(r.content)
		table_to_key_columns = which_table_has_key(schema_xml)
		key_val = check_table_key(schema_xml)
		table_sources = get_source_of_table(schema_xml)
		extract_sources = get_extract_query_list(schema_xml)
		counter = 0


		for table in schema_xml.findall(".//datasets/*"):
			inc = "incremental" in table.attrib and "true" == str(table.attrib["incremental"]).lower()
			query_xml = table.find("queryUpdate")
			correct_query = Q_REGEX.match(xml.tostring(query_xml)) if not query_xml is None else False
			table_name = table.attrib['table']
			table_has_key = table_to_key_columns.has_key(table_name.split('.')[1])
			if counter == 0:
				hold = 0
				first = False
				counter += 1
			else:	
				hold = counter - 1
				first = True

			try:
				writer.writerow((' ',[table_name], extract_sources[hold] if first else extract_sources[0], table.tag, 'Yes' if inc else 'No', '-' if not inc else 'Yes' if correct_query else 'No', str(table_sources[table_name.split('.')[1]]), 'Yes' if key_val[table_name.split('.')[1]] else 'No', str(table_to_key_columns[table_name.split('.')[1]]) if table_has_key else '-'))
			except Exception, e:
				writer.writerow((' ',['Unable to read table']))
				continue

			counter += 1

	except Exception, e:
		writer.writerow((' ',['No data in schema/tables']))
		return session

"""
Function is used to extract the source from the query. It reads between the 'from' and 'where' (checks for both lower and upper case)
Then splits by , into substrings
The substrings are then split by space and the aliases are removed
The stripped sources are joined together into a single string and pushed back into the list
"""

def get_extract_query_list(schema_xml):

	extract_list = []

	try:
		loads = schema_xml.find('schemaData').find('loader')
		tables = loads.find('datasets')
		tables = tables.findall('sql')
	except Exception, e:
		writer.writerow((' ', ['No data in tables']))
		return extract_list
	
	for table in tables:
		x = table.find('query')
		query = ''.join(x.itertext())

		try:
			src = query.split("FROM ", 1)[1]
			if "where" in src:
				src = src.split("where", 1)[0]

			elif "WHERE" in src:
				src = src.split("WHERE", 1)[0]
			#REMOVES ALIASES
			if "," in src:

				sub_list = []
				src = src.replace('\n', ' ')
				src1 = src.split(",")

				#nested for loops
				for y in range(len(src1)):

					counter = 0
					main_string = None
					alias = None
					compare =' '
					new_strings = src1[y].split(" ")
					new_size = len(new_strings)

					for x in range(len(new_strings)):
						if new_strings[x] in compare:
							pass
						else:
							if counter == 0:
								main_string = new_strings[x]
								counter += 1
								sub_list.append(main_string)
							else:
								alias = new_strings[x]

				src = ', '.join(sub_list)

			src = src.replace('\n', ' ')
			extract_list.append(src)


		except Exception, e:
			src = query.split("from", 1)[1]
			if "where" in src:
				src = src.split("where", 1)[0]

			elif "WHERE" in src:
				src = src.split("WHERE", 1)[0]

			if "," in src:

				sub_list = []
				src = src.replace('\n', ' ')
				src1 = src.split(",")

				#nested for loops
				for y in range(len(src1)):

					counter = 0
					main_string = None
					alias = None
					compare =' '
					new_strings = src1[y].split(" ")
					new_size = len(new_strings)

					for x in range(len(new_strings)):
						if new_strings[x] in compare:
							pass
						else:
							if counter == 0:
								main_string = new_strings[x]
								counter += 1
								sub_list.append(main_string)
							else:
								alias = new_strings[x]

				src = ', '.join(sub_list)

			src = src.replace('\n', ' ')
			extract_list.append(src)
	
	return extract_list


"""
Parses through all tables within the schema. Extract the name of the 
source per table. Append to the dictionary sources and return sources
"""


def get_source_of_table(schema_xml):
	
	sources = {}

	try:
		tables = schema_xml.find('schemaData').find('schema').find('tables')
		tables = tables.findall('table')
	except Exception, e:
		return sources
	for table in tables:
		table_name = table.get('name')
		origin = table.get('source')	
		add_source_to_sourcelist(sources, table.get('name'), origin)
	return sources



"""
Parses through all tables within the schema. Within every table, all columns are traversed through 
and checked to see if they contain a key. If so, the name of the column is appended to a dictionary
which is later returned. 
"""
def which_table_has_key(schema_xml):
	columns_with_key = {} 
	
	try:
		tables = schema_xml.find('schemaData').find('schema').find('tables')
		tables = tables.findall('table')
	except Exception, e:
		return columns_with_key
	for table in tables:
		column_list = []
		for column in table.iter():
			if column.get('function') == 'key':
				column_list.append(column.get('name'))
		add_column_to_table_list(columns_with_key, table.get('name'), column_list)
	return columns_with_key

"""
Parses through all columns within all tables. Checks if any columns have a key, if none then the
key_check boolean is set to false. If any of the columns have a key the key_check boolean is set
to true. Boolean value is then appened to a dictionary. Dictionary is then returned.
"""

def check_table_key(schema_xml):

	check_key = {}
	try:
		tables = schema_xml.find('schemaData').find('schema').find('tables')
		tables = tables.findall('table')
	except Exception, e:
		return check_key
	for table in tables:
		key_check = False
		for column in table.iter():
			if column.get('function') == 'key':
				key_check = True			
		add_bool_to_key_list(check_key, table.get('name'), key_check)
	return check_key

"""
Add column_list as a single element to the columns_with_key dictionary. Column_list is mapped
as a value to the correlating key of the dictionary, i.e. table_name 
"""
def add_column_to_table_list(table_to_columns_dict, table_name, column_with_key_name):


	if table_to_columns_dict.has_key('table_name'):
		table_to_columns_dict[table_name] = table_to_columns_dict[table_name].append(column_with_key_name)
	else:
		table_to_columns_dict[table_name] = [column_with_key_name]
"""
Add key_boolean as a single element to the columns_with_key dictionary. Boolean of key is mapped
as a value to the correlating key of the dictionary, i.e. table_name  
"""

def add_bool_to_key_list(table_to_columns_dict, table_name, key_bool_name):

	if table_to_columns_dict.has_key('table_name'):
		table_to_columns_dict[table_name] = table_to_columns_dict[table_name].append(key_bool_name)
	else:
		table_to_columns_dict[table_name] = key_bool_name

"""
Add source to sources dictionary 
"""

def add_source_to_sourcelist(table_to_columns_dict, table_name,source_name):
	if table_to_columns_dict.has_key('table_name'):
		table_to_columns_dict[table_name] = table_to_columns_dict[table_name].append(source_name)
	else:
		table_to_columns_dict[table_name] = source_name

"""
Add extract query to extract dictionary 
"""

def add_query_to_extractlist(table_to_columns_dict, table_name, extract_name):
	if table_to_columns_dict.has_key('table_name'):
		table_to_columns_dict[table_name] = table_to_columns_dict[table_name].append(extract_name)
	else:
		table_to_columns_dict[table_name] = extract_name	



"""
Allows pattern matching for schema, print out header statement for print output
	args:
		session(string): The session to incorta
		schema_list(xml.etree.ElementTree): The schema object
		schema_pattern(): Pattern of XML parsed schema
	returns: 
		Nothing
	prints:
		Has top level header print statement
"""

def list_schemas(session, schema_list, schema_pattern):
	schema_found = False
	#loops through schema list and displays output
	for schema in schema_list:
		schema_name = schema["name"]
		#SCHEMAS.append(schema_name)
		if schema_pattern.match(schema_name) :
			schema_found = True
			writer.writerow((' '))
			writer.writerow(([schema_name]))
			display_schema(session, schema)
	if not schema_found:
		print ("WARNNING: Could not find schemas matching your criteria, please "
		"try '%' if you want to include all schemas.")
	f.close()
print "Data exported to schemas.csv"
if len(sys.argv) < 5 :
	print ("Invalid arguments are: server tenant user pass schema_pattern")
	print ("example:")
	print ("\t http://localhost:8080/incorta demo super@incorta.com password EBS_%")

else :
	args = sys.argv[1:]
	server = args[0] #Arugument for Server 
	tenant = args[1] #Arugument for Tenant
	user = args[2] #Arugument for User
	password = args[3] #Arugument for Password 
	schema_pattern = regex.compile(str(args[4]).replace("%", ".*"))
	Q_REGEX = regex.compile("[^\\?]*\\?[^\\?]*")
	
	session = incorta.login(server, tenant, user, password)
	
	r = incorta.get(session, "/service/schema/getSchemas")
	try :
		list_schemas(session, json.loads(r.content)['schemas'], schema_pattern)
	finally :
		incorta.logout(session)