import subprocess, os, time, sys
from shutil import copyfile	
import runIncortaTests
from runIncortaTests import incorta

# July 8 2016
# By Ilyas Reyhanoglu
# Load users from LDAP

#Modifies sync_directory_with_ldap.sh
def edit_sync_directory(string_to_query, modification):

	f= open(path_incorta_bin+'/sync_directory_with_ldap.sh','r')
	lines = f.readlines()
	for line in lines:
		if string_to_query in line:
			replace_line=line.rstrip()
	f.close()

	f = open(path_incorta_bin+'/sync_directory_with_ldap.sh','r')
	filedata=f.read()
	update= filedata.replace(replace_line,modification)
	f.close()

	f = open(path_incorta_bin+'/sync_directory_with_ldap.sh','w')
	f.write(update)
	f.close()

path_incorta_bin=runIncortaTests.incorta_home+'/bin'
path_dir_export=runIncortaTests.incorta_home+'/dirExport'
path_tmt=runIncortaTests.incorta_home+'/tmt'

#import, export, and update tenant commands stored in variables
run_sync_directory=path_incorta_bin+'/./sync_directory_with_ldap.sh'
run_dir_export=path_dir_export+'/./dirExport.sh -ldap '+path_dir_export+'/ldap-config.properties'
run_update_tenant=path_tmt+'/./tmt.sh -u '+runIncortaTests.tenant+' file '+path_tmt+'/ldap.properties -f'

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

#Need to get into Incorta bin directory
os.chdir(path_incorta_bin)

#Holds the necessary changes to sync_directory_with_ldap.sh
sync_session='session=`$incorta_cmd login '+runIncortaTests.url+' '+runIncortaTests.tenant+' '+runIncortaTests.admin+' '+runIncortaTests.password+'`'
full_sync='$incorta_cmd sync_directory_with_ldap $session true'

edit_sync_directory('session=', sync_session)
edit_sync_directory('$incorta_cmd sync',full_sync)

#import to Incorta
subprocess.call(run_sync_directory,shell = True)

#Update tenant properties
os.chdir(path_tmt)
subprocess.call(run_update_tenant,shell=True)

os.chdir(runIncortaTests.incorta_home)

#Kills the instance of Incorta
subprocess.call('kill -9 ps aux | grep '+runIncortaTests.incorta_home,shell=True)

#Start a new instance of Incorta
subprocess.call('mysql.server start',shell=True)
subprocess.call(runIncortaTests.incorta_home+'/./start.sh',shell=True)

os.chdir(path_incorta_bin)

#Assign roles
session =incorta.login(runIncortaTests.url,runIncortaTests.tenant,runIncortaTests.admin,runIncortaTests.password)
incorta.assign_role_to_group(session, 'executive', 'SuperRole')
incorta.assign_role_to_group(session,'engineering','Analyze User')

#End in original directory
os.chdir(owd)
