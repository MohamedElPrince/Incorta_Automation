import sys, os, subprocess, time
import os.path
from sys import argv
from shutil import copyfile

"""
------------------------------------------Initialization----------------------------------------
"""

Debug = False #Debug flag for print statements

"""
Arguments
-------------------------------------------
-d: import datasource/schema/dashboard
-l: load data
-x: extract files
-------------------------------------------
"""
# All the arguments which are passed in command line
sys.argv[0:] = argv

# sys.argv[1] is the config file
config_file = sys.argv[1]

# Includes rest of arguments passed by user
commands = sys.argv[2:]

config_defaults = {'incorta_home': '/home/Incorta', 'tenant_home': '/home/tenants',
			   'admin': 'Super', 'password': 'none', 'load_users': 'No', 'test_suite': 'CSV_DataSource',
			   'skip_validation': 'Yes', 'import_object': 'False', 'data_load': 'False',
			   'extract_csv': 'False', 'wd_path': '~/IncortaTesting/tmp/work', 'tenant': 'Demo',
			   'url': 'http://localhost:8080/incorta'}

# new_config_defaults will hold all of the keys from the above dictionary
# The values of the keys in new_config_defaults will be parsed from the config file

# The keys corresponding to importing, loading data, and extracting files are by default False
new_config_defaults = {'import_object': 'False', 'data_load': 'False', 'extract_csv': 'False'}

"""
------------------------------------------Initialization----------------------------------------
"""


"""
#################################################### Functions ####################################################
"""
def set_command_value(commands):
	"""
	Function checks if an import, data load, or extract file argument is passed after the config file
	If so, the key corresponding to the action will be set to True
		args:
			commands: the arguments after the config file
		returns:
			Nothing
		prints:
			Nothing
	"""
	# If no arguments come after the config file, import, data load, and extract file keys will all be set to True
	if len(sys.argv) == 2:
		new_config_defaults['data_load'] = True
		new_config_defaults['extract_csv'] = True
		new_config_defaults['import_object'] = True
	else:
		for command in commands:
			if command == '-d':
				new_config_defaults['import_object'] = True
			if command == '-l':
				new_config_defaults['data_load'] = True
			if command == '-x':
				new_config_defaults['extract_csv'] = True

def set_new_defaults(config_file):
	"""
	Function parses config file for keys and values and stores them in new_config_defualts 
		args:
			config_file: config file holds keys and values to be used
		returns:
			Nothing
		prints:
			Nothing
	"""
	f = open(config_file, "r")
	lines = f.readlines()
	f.close()
	for line in lines:
		for key, value in config_defaults.items():
			if line[0] == '#':
				pass
			elif key in line:
				var = key + '='
				what_is_after_var = line[len(var):]
				what_is_after_key = line[len(key):]
				# if there is nothing after an = of a key in config file, the default value of the key will be assigned
				# Ex: admin=________ -> admin=Super
				if len(what_is_after_var) == 1:
					new_config_defaults[key] = value.rstrip()
				# if there is nothing after the name of a key in config file, the default value of the key will be assigned
				# Ex: admin____ -> admin=Super
				elif len(what_is_after_key) == 1:
					new_config_defaults[key] = value.rstrip()
				else:
					new_config_defaults[key] = what_is_after_var.rstrip()
	
	#If a key from config_defaults is missing in the config file, the code below will create the key 
	# in new_config_defaults and will assign that key its default value
	new_key_list = []
	old_key_list = []

	for new_key in new_config_defaults.keys():
		new_key_list.append(new_key)

	for old_key in config_defaults.keys():
		old_key_list.append(old_key)

	for key in old_key_list:
		if key not in new_key_list:
			new_config_defaults[key] = config_defaults[key]

	# If a custom working directory path is specified, /IncortaTesting/tmp/work will
	# be added to the end of the custom working directory path
	if new_config_defaults['wd_path'] == '/IncortaTesting/tmp/work':
		pass
	else:
		timestamp = ''
		new_config_defaults['wd_path'] += '/IncortaTesting/tmp/work'
		add_time_stamp_to_wd(timestamp)

def add_time_stamp_to_wd(timestamp):
	"""
	Function adds a timestamp to the end of the working directory path
		args:
			timestamp: MM/DD/YY-HR/MIN/SEC
		returns:
			Nothing
		prints:
			Nothing
	"""
	date_and_time = str(time.strftime("%m:%d:%Y-%H:%M:%S"))
	new_config_defaults['wd_path'] += '/%s'%date_and_time

def incorta_import(incorta_home):
	"""
	Function takes the incorta installation path to import Incorta API
		args:
			incorta_home: Incorta Home Directory Path
		returns:
			Nothing
		prints:
			Nothing
	"""
	incorta_module = incorta_home.rstrip() + os.sep + "bin".rstrip()
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
            Handles exception case of login fails
    """
    try:
        return incorta.login(url, tenant, admin, password, True)
    except Exception, e:
        print "Login Failed"
        exit(1)

def logout(session):
    """
    Function logs out of the instance of Incorta
    	args:
    		session: session var returned by login function
    	returns:
            Nothing
    	prints:
            Handles exception case of login fails
    """
    try:
        incorta.logout(session)
    except Exception, e:
        print 'Failed to logout'
        exit(1)

"""
#################################################### Functions ####################################################
"""

set_command_value(commands)
set_new_defaults(config_file)

if Debug == True: 
	for key, value in new_config_defaults.items():
		print(key, value)

# converts keys in a dictionary to variables
locals().update(new_config_defaults)

incorta_import(incorta_home)
session=incorta.login(url, tenant, admin, password, True)
session_id = session[21:53] # Session ID stripped from session var



