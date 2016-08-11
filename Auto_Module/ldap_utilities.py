import os, os.path
import subprocess
import time
import  file_tools

incorta_home = '/Users/Nadim_Incorta/incorta_testing'

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

def ldap_property_setup(incorta_home, ldap_url, ldap_base, ldap_user_mapping_login, ldap_group_mapping_member, ldap_group_search_filter):
    print "Setting up LDAP"
    dirExport_path = incorta_home + os.sep + 'dirExport'
    incorta_bin_path = incorta_home + os.sep + 'bin'
    dirExport_ldap_path = dirExport_path + os.sep + 'ldap-config.properties'
    incorta_bin_ldap_path = incorta_bin_path + os.sep + 'ldap-config.properties'

    #SETTING LDAP URL
    modifier = 'ldap.base.provider.url=' + ldap_url
    lines = open(dirExport_ldap_path).readlines()
    for line in lines:
        if 'ldap.base.provider.url=' in line:
            line_to_replace = line.rstrip()
    f = open(dirExport_ldap_path, 'r')
    filedata = f.read()
    update = filedata.replace(line_to_replace, modifier)
    f.close()
    f = open(dirExport_ldap_path, 'w')
    f.write(update)
    f.close()
    modifier = 'ldap.base.provider.url=' + ldap_url
    lines = open(incorta_bin_ldap_path).readlines()
    for line in lines:
        if 'ldap.base.provider.url=' in line:
            line_to_replace = line.rstrip()
    f = open(incorta_bin_ldap_path, 'r')
    filedata = f.read()
    update = filedata.replace(line_to_replace, modifier)
    f.close()
    f = open(incorta_bin_ldap_path, 'w')
    f.write(update)
    f.close()

    #SETTING UP LDAP BASE
    modifier = 'ldap.base.dn=' + ldap_base
    lines = open(dirExport_ldap_path).readlines()
    for line in lines:
        if 'ldap.base.dn=' in line:
            line_to_replace = line.rstrip()
    f = open(dirExport_ldap_path, 'r')
    filedata = f.read()
    update = filedata.replace(line_to_replace, modifier)
    f.close()
    f = open(dirExport_ldap_path, 'w')
    f.write(update)
    f.close()
    modifier = 'ldap.base.dn=' + ldap_base
    lines = open(incorta_bin_ldap_path).readlines()
    for line in lines:
        if 'ldap.base.dn=' in line:
            line_to_replace = line.rstrip()
    f = open(incorta_bin_ldap_path, 'r')
    filedata = f.read()
    update = filedata.replace(line_to_replace, modifier)
    f.close()
    f = open(incorta_bin_ldap_path, 'w')
    f.write(update)
    f.close()

    #SETTING UP ldap_user_mapping_login
    modifier = 'ldap.user.mapping.login=' + ldap_user_mapping_login
    lines = open(dirExport_ldap_path).readlines()
    for line in lines:
        if 'ldap.user.mapping.login=' in line:
            line_to_replace = line.rstrip()
    f = open(dirExport_ldap_path, 'r')
    filedata = f.read()
    update = filedata.replace(line_to_replace, modifier)
    f.close()
    f = open(dirExport_ldap_path, 'w')
    f.write(update)
    f.close()
    modifier = 'ldap.user.mapping.login=' + ldap_user_mapping_login
    lines = open(incorta_bin_ldap_path).readlines()
    for line in lines:
        if 'ldap.user.mapping.login=' in line:
            line_to_replace = line.rstrip()
    f = open(incorta_bin_ldap_path, 'r')
    filedata = f.read()
    update = filedata.replace(line_to_replace, modifier)
    f.close()
    f = open(incorta_bin_ldap_path, 'w')
    f.write(update)
    f.close()

    #SETTING UP ldap_group_mapping_member
    modifier = 'ldap.group.mapping.member=' + ldap_group_mapping_member
    lines = open(dirExport_ldap_path).readlines()
    for line in lines:
        if 'ldap.group.mapping.member=' in line:
            line_to_replace = line.rstrip()
    f = open(dirExport_ldap_path, 'r')
    filedata = f.read()
    update = filedata.replace(line_to_replace, modifier)
    f.close()
    f = open(dirExport_ldap_path, 'w')
    f.write(update)
    f.close()
    modifier = 'ldap.group.mapping.member=' + ldap_group_mapping_member
    lines = open(incorta_bin_ldap_path).readlines()
    for line in lines:
        if 'ldap.group.mapping.member=' in line:
            line_to_replace = line.rstrip()
    f = open(incorta_bin_ldap_path, 'r')
    filedata = f.read()
    update = filedata.replace(line_to_replace, modifier)
    f.close()
    f = open(incorta_bin_ldap_path, 'w')
    f.write(update)
    f.close()

    #SETTING UP ldap_group_search_filter
    modifier = 'ldap.group.search.filter=' + ldap_group_search_filter
    lines = open(dirExport_ldap_path).readlines()
    for line in lines:
        if 'ldap.group.search.filter=' in line:
            line_to_replace = line.rstrip()
    f = open(dirExport_ldap_path, 'r')
    filedata = f.read()
    update = filedata.replace(line_to_replace, modifier)
    f.close()
    f = open(dirExport_ldap_path, 'w')
    f.write(update)
    f.close()
    modifier = 'ldap.group.search.filter=' + ldap_group_search_filter
    lines = open(incorta_bin_ldap_path).readlines()
    for line in lines:
        if 'ldap.group.search.filter=' in line:
            line_to_replace = line.rstrip()
    f = open(incorta_bin_ldap_path, 'r')
    filedata = f.read()
    update = filedata.replace(line_to_replace, modifier)
    f.close()
    f = open(incorta_bin_ldap_path, 'w')
    f.write(update)
    f.close()

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
        string_query = '$incorta_cmd sync_directory_with_ldap $session'
        a = open(sync_script_path, 'r')
        lines = a.readlines()
        for line in lines:
            if string_query in line:
                line_to_replace = line.rstrip()
        a.close()
        b = open(sync_script_path, 'r')
        filedata = b.read()
        update = filedata.replace(line_to_replace, modified_sync_enable)
        b.close()
        c = open(sync_script_path, 'w')
        c.write(update)
        c.close()

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
        print run_sync_cmd
        subprocess.call(run_sync_cmd, shell=True)
        os.chdir(owd)
    except Exception, e:
        print "Failed to run sync with LDAP and local Incorta instance"
        return

    # Creates Sync tag if LDAP populate was successful
    sync_tag = orig_wd_path + os.sep + 'sync.txt'
    f = open(sync_tag, 'w')
    f.close()

def tenant_updater(incorta_home, tenant):
    try:
        print "Updating Tenant"
        path_tmt = incorta_home + os.sep + 'tmt'
        tenant_update_ldap = './tmt.sh -u ' + tenant + ' file ldap.properties -f'
        owd = os.getcwd()
        os.chdir(path_tmt)
        subprocess.call(tenant_update_ldap, shell=True)
        os.chdir(owd)
    except Exception, e:
        print "Failed to update Tenant: ", tenant
        return

def restart_incorta(incorta_home):
    try:
        print "Restarting Incorta"
        owd = os.getcwd()
        os.chdir(incorta_home)
        subprocess.call(incorta_home + '/./stop.sh', shell=True)
        time.sleep(7)
        subprocess.call("ps -ax |grep %s | awk '{print $1}' | xargs kill -9" % incorta_home, shell=True)
        time.sleep(2)
        subprocess.call(incorta_home + '/./start.sh', shell=True)
        time.sleep(7)
        os.chdir(owd)
    except Exception, e:
        print "Unable to restart Incorta instance"
        return

def assign_roles_to_groups(incorta, session):
    # Assign roles
    # session =incorta.login(url,tenant,admin,password)
    try:
        print "Assigning Roles"
        incorta.assign_role_to_group(session, 'executive', 'SuperRole')
        incorta.assign_role_to_group(session, 'engineering', 'Analyze User')
        print "Assigned Roles Successfully"
    except Exception, e:
        print "Unable to assign Roles, Roles already assigned"
        return

def read_users_from_csv(incorta_home):
    dirExport_path = incorta_home + os.sep + 'dirExport'
    user_groups_csv_path = dirExport_path + os.sep + 'user-groups.csv'

    print "Incorta Home: ", incorta_home
    owd = os.getcwd()
    os.chdir(dirExport_path)
    print "Reading CSV files from: ", os.getcwd()
    user_list = {}
    COUNT = 0
    print "Extracting users from csv"

    with open(user_groups_csv_path, "rb") as file:
        for col in file:
            if COUNT > 0:
                user = col.split(",")
                user[1] = user[1].strip('\n')
                user_list[user[1]] = user[0]
            COUNT += 1

    os.chdir(owd)
    return user_list
