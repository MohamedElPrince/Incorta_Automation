import os, os.path
import subprocess
import file_tools


incorta_home = '/Users/Nadim_Incorta/Incorta_Framework'

def dirExport(incorta_home):
    """
    Exports User / Group Lists from Remote Incorta Environment
    """

    dirExport_path = incorta_home + os.sep + 'dirExport'
    ldap_config_path = dirExport_path + os.sep + 'ldap-config.properties'
    dirExport_cmd = dirExport_path + os.sep + 'dirExport.sh' + ' -ldap ' + ldap_config_path + ' [--debug]'
    subprocess.call(dirExport_cmd, shell=True)

    move_cmd = 'mv directory.zip ' + dirExport_path
    subprocess.call(move_cmd, shell=True)


def sync_directory_setup(incorta_home, tenant_name, admin_username, admin_password, url):

    incort_bin_path = incorta_home + os.sep + 'bin'
    sync_script = 'sync_directory_with_ldap.sh'
    sync_script_backup = 'sync_directory_with_ldap.sh.bak'

    sync_script_path = incort_bin_path + os.sep + sync_script
    sync_script_backup_path = incort_bin_path + os.sep + sync_script_backup


    if os.path.isfile(sync_script_backup_path):
        print "Backup Already Created"

    else:
        print "Creating Backup"
        # Make backup
        backup_cmd = 'cp ' + sync_script_path + ' ' + sync_script_backup_path
        subprocess.call(backup_cmd, shell=True)

        login_info = tenant_name + ' ' + admin_username + ' ' + admin_password
        modified_session_login = 'session=`$incorta_cmd login '+ url + ' ' + login_info + '`'
        modified_sync_enable = '$incorta_cmd sync_directory_with_ldap $session true'

        #Editing Connection Info
        #Input Login Paramaters
        r = open(sync_script_path).read()
        session_login = r.replace('session=`$incorta_cmd login http://localhost:8080/incorta demo a a`', modified_session_login)
        f = open(sync_script_path, 'w')
        f.write(session_login)
        f.close()
        #Enable full sync by adding true
        r = open(sync_script_path).read()
        enable_full_sync = r.replace('$incorta_cmd sync_directory_with_ldap $session', modified_sync_enable)
        f = open(sync_script_path, 'w')
        f.write(enable_full_sync)
        f.close()



def sync_directory(incorta_home, orig_wd_path):
    incort_bin_path = incorta_home + os.sep + 'bin'
    dirExport_path = incorta_home + os.sep + 'dirExport'
    sync_script = 'sync_directory_with_ldap.sh'
    sync_script_path = incort_bin_path + os.sep + sync_script

    try:
        print "Populating Incorta Instance from LDAP"
        owd = os.getcwd()
        os.chdir(dirExport_path)
        run_sync_cmd = sync_script_path
        subprocess.call(run_sync_cmd, shell=True)
        os.chdir(owd)

    except Exception, e:
        print "Failed to run sync with LDAP and local Incorta instance"
        return

    # Creates Sync tag if LDAP populate was successful
    sync_tag = orig_wd_path + os.sep + 'sync.txt'
    f = open(sync_tag, 'w')
    f.close()


# TESTING PURPOSES
# dirExport(incorta_home)
# sync_directory_setup(incorta_home, 'Demo', 'admin', 'incorta', 'http://localhost:8080/incorta')
# sync_directory(incorta_home, '/Users/Nadim_Incorta/IncortaTesting')