import sys, os, subprocess, time, zipfile
import os.path

from sys import argv
from shutil import copyfile

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

# Default variables and their values stored in a dictionary

config_defaults = {'incorta_home': '/home/Incorta', 'tenant_home': '/home/tenants',
                   'admin': 'Super', 'password': 'none', 'load_users': 'No', 'test_suite': 'CSV_DataSource',
                   'skip_validation': 'Yes', 'import_object': 'False', 'data_load': 'False',
                   'extract_csv': 'False', 'wd_path': '~/IncortaTesting/tmp/work', 'tenant': 'Demo',
                   'url': 'http://localhost:8080/incorta'}

# The new_config_defaults dictionary stores the variables and their values from the config file

# The variables corresponding to importing, loading data, and extracting files are by default False

new_config_defaults = {'import_object': 'False', 'data_load': 'False', 'extract_csv': 'False'}


# Checks which arguments after config file were passed. If an import, data load, or extract
# file argument is passed, the variable corresponding to the action will be set to True. 

def set_command_value(commands):
    # if no arguments other than config file are passed, importing, data loading, and extracting variables will be set to True
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


# Parses variables and their values from the config file and stores them in a dictionary

def set_new_defaults(config_file):
    f = open(config_file, "r")
    lines = f.readlines()
    f.close()

    for line in lines:
        for key, value in config_defaults.items():
            # ignores comments in config file
            if line[0] == '#':
                pass
            elif key in line:
                var = key + '='
                what_is_after_var = line[len(var):]
                what_is_after_key = line[len(key):]
                # if there is nothing after the equal sign of a variable in config file, the
                # default value of the variable will be assigned
                # Ex: admin=
                if len(what_is_after_var) == 1:
                    new_config_defaults[key] = value.rstrip()
                # if there is nothing after the variable name in config file, the default value
                # of the variable will be assigned
                # Ex: admin
                elif len(what_is_after_key) == 1:
                    new_config_defaults[key] = value.rstrip()
                else:
                    new_config_defaults[key] = what_is_after_var.rstrip()

    # if a default variable is missing in the config file, the variable will be created in the
    # new_config_defaults dictionary and will be assigned its default value

    new_key_list = []
    old_key_list = []

    for new_key in new_config_defaults.keys():
        new_key_list.append(new_key)

    for old_key in config_defaults.keys():
        old_key_list.append(old_key)

    for key in old_key_list:
        if key not in new_key_list:
            new_config_defaults[key] = config_defaults[key]

    # creates the entire working directory path

    if new_config_defaults['wd_path'] == '/IncortaTesting/tmp/work':
        pass
    else:
        timestamp = ''
        new_config_defaults['wd_path'] += '/IncortaTesting/tmp/work'
        add_time_stamp_to_wd(timestamp)


def add_time_stamp_to_wd(timestamp):
    date_and_time = time.strftime("%m/%d/%Y-%H:%M:%S")
    new_config_defaults['wd_path'] += '/' + date_and_time


set_command_value(commands)
set_new_defaults(config_file)

# converts keys in a dictionary to variables
locals().update(new_config_defaults)

for key, value in new_config_defaults.items():
    print(key, value)

"""
#################################################### Functions ####################################################
"""

Debug = False   #Debug flag for print statements

"""
#######################################
Import Incorta
#######################################
"""
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
"""
#######################################
Import Incorta
#######################################
"""

"""
#######################################
Login
#######################################
"""
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
"""
#######################################
Login
#######################################
"""

"""
#######################################
Test Suite Export To Working Directory
#######################################
"""
def extract_test_suites(wd_path, test_suite):
    """
    Function extracts all files inside test suit to the working directory
        args:
            wd_path: working directory path
            test_suite: test suite name
        returns:
            Nothing
        prints:
            Nothing
    """
    test_suite = '/'+ test_suite
    python_work_dir = os.getcwd()
    test_suite_path = python_work_dir + test_suite
    extension = '.zip'
    for root, dirs, files in os.walk(test_suite_path):
        for file in files:
            if file.endswith(extension):
                file_raw_dir = os.path.join(root, file)
                zip_ref = zipfile.ZipFile(file_raw_dir)
                zip_ref.extractall(wd_path)
                zip_ref.close()
"""
#######################################
Test Suite Export To Working Directory
#######################################
"""

"""
#######################################
Import DataFiles To Incorta
#######################################
"""
def import_datafiles(session, test_suite):
    """
    Function imports all data files to Incorta from the designated test suite
        args:
            session: session var returned by login function
            test_suite: test suite name from config file
        returns:
            Nothing
        prints:
            Nothing
    """
    python_work_dir = os.getcwd()
    test_suite_path = python_work_dir + test_suite
    extension = '.zip'
    upload_check = []
    for root, dirs, files in os.walk(test_suite_path):
        for file in files:
            if 'datafile' in file:
                if file.endswith(extension):
                    file_full_path = os.path.join(root, file)
                    upload_check.append(incorta.upload_data_file(session, file_full_path))
    if Debug == True:
        for checks in upload_check:
            print checks,
"""
#######################################
Import DataFiles To Incorta
#######################################
"""

"""
#######################################
Import Schema To Incorta
#######################################
"""
def import_schema(session, test_suite):
    """
    Function imports all schemas to Incorta from the designated test suite
        args:
            session: session var returned by login function
            test_suite: test suite name from config file
        returns:
            Nothing
        prints:
            Nothing
    """
    python_work_dir = os.getcwd()
    test_suite_path = python_work_dir + test_suite
    extension = '.zip'
    upload_check = []
    for root, dirs, files in os.walk(test_suite_path):
        for file in files:
            if 'schema' in file:
                if file.endswith(extension):
                    file_full_path = os.path.join(root, file)
                    upload_check.append(incorta.import_tenant(session, file_full_path, True))
    if Debug == True:
        for checks in upload_check:
            print checks,
"""
#######################################
Import Schema To Incorta
#######################################
"""

"""
#######################################
Import Dashboard To Incorta
#######################################
"""
def import_dashboard(session, test_suite):
    """
    Function imports all dashboards to Incorta from the designated test suite
        args:
            session: session var returned by login function
            test_suite: test suite name from config file
        returns:
            Nothing
        prints:
            Nothing
    """
    python_work_dir = os.getcwd()
    test_suite_path = python_work_dir + test_suite
    extension = '.zip'
    upload_check = []
    for root, dirs, files in os.walk(test_suite_path):
        for file in files:
            if 'dashboard' in file:
                if file.endswith(extension):
                    file_full_path = os.path.join(root, file)
                    upload_check.append(incorta.import_tenant(session, file_full_path, True))
    if Debug == True:
        for checks in upload_check:
            print checks,
"""
#######################################
Import Dashboard To Incorta
#######################################
"""

"""
#######################################
Logout
#######################################
"""
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
#######################################
Logout
#######################################
"""

"""
#######################################
Load Users From LDAP
#######################################
"""
def load_users_ldap():

    #Modifies sync_directory_with_ldap.sh
    def replace_line(file_path, string_to_query, modification):

        os.chdir(file_path)

        #If the string searched is in a line, the entire line will be selected for replacement
        f= open(path_incorta_bin+'/sync_directory_with_ldap.sh','r')
        lines = f.readlines()
        for line in lines:
            if string_to_query in line:
                line_to_replace=line.rstrip()
        f.close()

        f = open(path_incorta_bin+'/sync_directory_with_ldap.sh','r')
        filedata=f.read()
        update= filedata.replace(line_to_replace,modification)
        f.close()

        f = open(path_incorta_bin+'/sync_directory_with_ldap.sh','w')
        f.write(update)
        f.close()

    #Variables hold entire paths of the directories
    path_incorta_bin=incorta_home+'/bin'
    path_dir_export=incorta_home+'/dirExport'
    path_tmt=incorta_home+'/tmt'

    #import, export, and update_tenant_ldap commands stored in variables
    run_sync_directory=path_incorta_bin+'/./sync_directory_with_ldap.sh'
    run_dir_export=path_dir_export+'/./dirExport.sh -ldap '+path_dir_export+'/ldap-config.properties'
    run_update_tenant_ldap=path_tmt+'/./tmt.sh -u '+tenant+' file '+path_tmt+'/ldap.properties -f'

    # Original working directory
    owd=os.getcwd()

    #To determine the directory which will hold the content from run_dir_export
    os.chdir(path_dir_export)

    #Executes run_dir_export
    subprocess.call(run_dir_export,shell = True)

    #Creates backup of sync_directory_with_ldap.sh called sync_directory_with_ldap.sh.bak in Incorta bin
    copyfile(path_incorta_bin+'/sync_directory_with_ldap.sh',path_incorta_bin+'/sync_directory_with_ldap.sh.bak')

    #Creates a copy of exported zip file from dirExport in Incorta bin
    copyfile(path_dir_export+'/directory.zip',path_incorta_bin+'/directory.zip')

    #Creates a copy of ldap-config.properties files in Incorta bin
    copyfile(path_dir_export+'/ldap-config.properties',path_incorta_bin+'/ldap-config.properties')

    #Variables hold the necessary changes to make to sync_directory_with_ldap.sh
    sync_session='session=`$incorta_cmd login '+url+' '+tenant+' '+admin+' '+password+'`'
    full_sync='$incorta_cmd sync_directory_with_ldap $session true'

    replace_line(path_incorta_bin,'session=', sync_session)
    replace_line(path_incorta_bin,'$incorta_cmd sync',full_sync)

    #import to Incorta
    subprocess.call(run_sync_directory,shell = True)

    #Update tenant_ldap properties
    os.chdir(path_tmt)
    subprocess.call(run_update_tenant_ldap,shell=True)

    os.chdir(incorta_home)

    # Kills the instance of Incorta
    subprocess.call(incorta_home+'/./stop.sh',shell=True)
    time.sleep(7)
    subprocess.call("ps -ax |grep %s | awk '{print $1}' | xargs kill -9"%incorta_home,shell=True)

    #Start a new instance of Incorta
    subprocess.call('mysql.server start',shell=True)
    subprocess.call(incorta_home+'/./start.sh',shell=True)

    os.chdir(path_incorta_bin)

    time.sleep(7)

    #Assign roles
    session = incorta.login(url,tenant,admin,password)
    incorta.assign_role_to_group(session, 'executive', 'SuperRole')
    incorta.assign_role_to_group(session,'engineering','Analyze User')

    #End in original directory
    os.chdir(owd)
"""
#######################################
Load Users From LDAP
#######################################
"""

"""
#################################################### Functions ####################################################
"""

incorta_import(incorta_home)
session=login(url, tenant, admin, password)
print session
print wd_path
extract_test_suites(wd_path, test_suite)










