import subprocess, os, time, sys
from shutil import copyfile	
import runIncortaTests
from runIncortaTests import incorta

# July 11 2016
# By Ilyas Reyhanoglu
# Load users from LDAP

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
path_incorta_bin=runIncortaTests.incorta_home+'/bin'
path_dir_export=runIncortaTests.incorta_home+'/dirExport'
path_tmt=runIncortaTests.incorta_home+'/tmt'

#import, export, and update_tenant_ldap commands stored in variables
run_sync_directory=path_incorta_bin+'/./sync_directory_with_ldap.sh'
run_dir_export=path_dir_export+'/./dirExport.sh -ldap '+path_dir_export+'/ldap-config.properties'
run_update_tenant_ldap=path_tmt+'/./tmt.sh -u '+runIncortaTests.tenant+' file '+path_tmt+'/ldap.properties -f'

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
sync_session='session=`$incorta_cmd login '+runIncortaTests.url+' '+runIncortaTests.tenant+' '+runIncortaTests.admin+' '+runIncortaTests.password+'`'
full_sync='$incorta_cmd sync_directory_with_ldap $session true'

replace_line(path_incorta_bin,'session=', sync_session)
replace_line(path_incorta_bin,'$incorta_cmd sync',full_sync)

#import to Incorta
subprocess.call(run_sync_directory,shell = True)

#Update tenant_ldap properties
os.chdir(path_tmt)
subprocess.call(run_update_tenant_ldap,shell=True)

os.chdir(runIncortaTests.incorta_home)

# Kills the instance of Incorta
subprocess.call(runIncortaTests.incorta_home+'/./stop.sh',shell=True)
time.sleep(7)
subprocess.call("ps -ax |grep %s | awk '{print $1}' | xargs kill -9"%runIncortaTests.incorta_home,shell=True)

#Start a new instance of Incorta
subprocess.call('mysql.server start',shell=True)
subprocess.call(runIncortaTests.incorta_home+'/./start.sh',shell=True)

os.chdir(path_incorta_bin)

time.sleep(7)

#Assign roles
session =incorta.login(runIncortaTests.url,runIncortaTests.tenant,runIncortaTests.admin,runIncortaTests.password)
incorta.assign_role_to_group(session, 'executive', 'SuperRole')
incorta.assign_role_to_group(session,'engineering','Analyze User')

#End in original directory
os.chdir(owd)

