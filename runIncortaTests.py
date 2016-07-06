import sys
from sys import argv
import os.path
import time

# July 1 2016
# By Ilyas Reyhanoglu

#Arguments
#-------------------------------------------
#-d: import datasource/schema/dashboard
#-l: load data
#-x: extract files
#-------------------------------------------

#All the arguments which are passed in command line
sys.argv[0:] = argv

#sys.argv[1] is the config file
config_file=sys.argv[1] 

#Includes rest of arguments passed by user
commands = sys.argv[2:]

#Default variables and their values stored in a dictionary

config_defaults = {'incorta_home':'/home/Incorta', 'tenant_home':'/home/tenants',
				   'admin':'Super','password':'none', 'load_users': 'No', 'test_suite': 'MySQL',
				   'skip_validation': 'Yes','import_object':'False','data_load':'False',
				   'extract_csv':'False','wd_path':'/IncortaTesting/tmp/work', 'tenant':'Demo',
				   'url':'http://localhost:8080/incorta/'}

# The new_config_defaults dictionary stores the variables and their values from
# the config file

# The variables corresponding to importing, loading data, and extracting files are by
# default False

new_config_defaults={'import_object':'False','data_load':'False','extract_csv':'False'}

# Checks which arguments after config file were passed. If an import, data load, or extract
# file argument is passed, the variable corresponding to the action will be set to True. 

def set_command_value(commands):

	# if no arguments other than config file are passed, importing, data loading, and extracting
	# variables will be set to True
	if len(sys.argv) ==2:
		new_config_defaults['data_load']=True
		new_config_defaults['extract_csv']=True
		new_config_defaults['import_object']=True

	else:
		for command in commands:
			if command == '-d':
				new_config_defaults['import_object']=True
			if command == '-l':
				new_config_defaults['data_load']=True
			if command =='-x':
				new_config_defaults['extract_csv']=True


# Parses variables and their values from the config file and stores them in a dictionairy

def set_new_defaults(config_file):	
	
	f = open(config_file, "r")
	lines = f.readlines()
	f.close()

	for line in lines:
		for key, value in config_defaults.items():
			#ignores comments in config file
			if line[0]== '#':
				pass
			elif key in line:
				var = key+'='
				what_is_after_var= line[len(var):]
				what_is_after_key= line[len(key):]
				# if there is nothing after the equal sign of a variable in config file, the
				# default value of the variable will be assigned
				# Ex: admin=  
				if len(what_is_after_var) == 1:
					new_config_defaults[key]=value.rstrip()
				# if there is nothing after the variable name in config file, the default value 
				# of the variable will be assigned
				# Ex: admin
				elif len(what_is_after_key) == 1:
					new_config_defaults[key]=value.rstrip()
				else:
					new_config_defaults[key]=what_is_after_var.rstrip()
	
	#if a default variable is missing in the config file, the variable will be created in the
	# new_config_defaults dictionary and will be assigned its default value

	new_key_list=[]
	old_key_list=[]				
	
	for new_key in new_config_defaults.keys():
		new_key_list.append(new_key)
	
	for old_key in config_defaults.keys():
		old_key_list.append(old_key)

	for key in old_key_list:
		if key not in new_key_list:
			new_config_defaults[key]=config_defaults[key]

	#creates the entire working directory path	

	if new_config_defaults['wd_path'] == '/IncortaTesting/tmp/work':
		pass
	else:
		timestamp=''
		new_config_defaults['wd_path']+='/IncortaTesting/tmp/work'
		add_time_stamp_to_wd(timestamp)


def add_time_stamp_to_wd(timestamp):
	date_and_time = time.strftime("%m/%d/%Y-%H:%M:%S")
	new_config_defaults['wd_path']+='/'+date_and_time

set_command_value(commands)
set_new_defaults(config_file)

# converts keys in a dictionary to variables
locals().update(new_config_defaults)

# importing the incorta module
incorta_module=incorta_home.rstrip()+os.sep+"bin".rstrip()
sys.path.append(incorta_module)
import incorta

for key,value in new_config_defaults.items():
	print(key,value)
