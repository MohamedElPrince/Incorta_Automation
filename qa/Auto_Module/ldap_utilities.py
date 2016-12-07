import os, os.path
import subprocess, shutil
import time
from customLogger import mainLogger, writeLogMessage

"""
"""


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


def tmt_ldap_property_setup(incorta_home, ldap_url, ldap_user_mapping_login, ldap_base):
    """
    """
    tmt_path = incorta_home + os.sep + 'tmt'
    tmt_ldap_path = tmt_path + os.sep + 'ldap.properties'
    url_modifier = 'ldap.base.provider.url=' + ldap_url
    login_modifier = 'ldap.user.mapping.login=' + ldap_user_mapping_login
    base_modifier = 'ldap.base.dn=' + ldap_base
    lines = []
    try:
        with open(tmt_ldap_path, 'r') as ldap_prop_in:
            file_lines = ldap_prop_in.readlines()
            for line in file_lines:
                # Ldap URL setup
                if 'ldap.base.provider.url=' in line:
                    url_line_to_replace = line.rstrip()
                    lines.append(line.replace(url_line_to_replace, url_modifier))

                # LDAP User Mapping Login Setup
                elif 'ldap.user.mapping.login=' in line:
                    login_line_to_replace = line.rstrip()
                    lines.append(line.replace(login_line_to_replace, login_modifier))

                # LDAP Base dn setup
                elif 'ldap.base.dn=' in line:
                    base_line_to_replace = line.rstrip()
                    lines.append(line.replace(base_line_to_replace, base_modifier))
                else:
                    lines.append(line)

        with open(tmt_ldap_path, 'w') as ldap_prop_out:
            for line in lines:
                ldap_prop_out.write(line)

    except EnvironmentError:
        print "Unable to Modify LDAP Properties File Under /tmt"
        writeLogMessage('Unable to Modify LDAP Properties File Under /tmt', mainLogger, 'error')


def ldap_property_setup(incorta_home, ldap_url, ldap_base, ldap_user_mapping_login, ldap_group_mapping_member,
                        ldap_group_search_filter):
    """
    """
    print "Setting up LDAP"
    writeLogMessage('Setting up LDAP', mainLogger, 'info')
    dirExport_path = incorta_home + os.sep + 'dirExport'
    incorta_bin_path = incorta_home + os.sep + 'bin'
    dirExport_ldap_path = dirExport_path + os.sep + 'ldap-config.properties'
    incorta_bin_ldap_path = incorta_bin_path + os.sep + 'ldap-config.properties'

    url_modifier = 'ldap.base.provider.url=' + ldap_url
    login_modifier = 'ldap.user.mapping.login=' + ldap_user_mapping_login
    base_modifier = 'ldap.base.dn=' + ldap_base
    lines = []
    group_map_modifier = 'ldap.group.mapping.member=' + ldap_group_mapping_member
    group_search_modifier = 'ldap.group.search.filter=' + ldap_group_search_filter
    lines = []
    try:
        with open(dirExport_ldap_path, 'r') as ldap_export_in:
            file_lines = ldap_export_in.readlines()

            for line in file_lines:
                # Ldap URL setup
                if 'ldap.base.provider.url=' in line:
                    url_line_to_replace = line.rstrip()
                    lines.append(line.replace(url_line_to_replace, url_modifier))

                # LDAP User Mapping Login Setup
                elif 'ldap.user.mapping.login=' in line:
                    login_line_to_replace = line.rstrip()
                    lines.append(line.replace(login_line_to_replace, login_modifier))

                # LDAP Base dn setup
                elif 'ldap.base.dn=' in line:
                    base_line_to_replace = line.rstrip()
                    lines.append(line.replace(base_line_to_replace, base_modifier))

                # SETTING UP ldap_group_mapping_member
                elif 'ldap.group.mapping.member=' in line:
                    group_map_line_replace = line.rstrip()
                    lines.append(line.replace(group_map_line_replace, group_map_modifier))

                # SETTING UP ldap_group_search_filter
                elif 'ldap.group.search.filter=' in line:
                    group_search_replace = line.rstrip()
                    lines.append(line.replace(group_search_replace, group_search_modifier))

                else:
                    lines.append(line)

        with open(incorta_bin_ldap_path, 'w') as ldap_bin_out:
            for line in lines:
                ldap_bin_out.write(line)

        with open(dirExport_ldap_path, 'w') as ldap_bin_out:
            for line in lines:
                ldap_bin_out.write(line)

    except EnvironmentError:
        print "Unable to Modify LDAP Properties File Under /bin"
        writeLogMessage('Unable to Modify LDAP Properties File Under /bin', mainLogger, 'error')


def sync_directory_setup(incorta_home, tenant_name, admin_username, admin_password, url):
    """
    """
    incort_bin_path = incorta_home + os.sep + 'bin'
    sync_script = 'sync_directory_with_ldap.sh'
    sync_script_backup = 'sync_directory_with_ldap.sh.bak'
    sync_script_path = incort_bin_path + os.sep + sync_script
    sync_script_backup_path = incort_bin_path + os.sep + sync_script_backup

    if os.path.isfile(sync_script_backup_path):
        print "Backup Already Created"
        writeLogMessage("Backup Already Created", mainLogger, 'info')
    else:
        # Make backup
        print "Creating Backup"
        writeLogMessage("Creating Backup", mainLogger, 'info')
        shutil.copyfile(sync_script_path, sync_script_backup_path)

        # todo removed subprocess thread call for superior pythonic method shutil 10/25/16
        # backup_cmd = 'cp ' + sync_script_path + ' ' + sync_script_backup_path
        # subprocess.call(backup_cmd, shell=True)
        # modified_session_login = 'session=`$incorta_cmd login ' + url + ' ' + login_info + '`'
        # modified_sync_enable = '$incorta_cmd sync_directory_with_ldap $session true'
        # todo removed subprocess thread call for superior pythonic method shutil 10/25/16

        # Editing Connection Info for input login arguments
        lines = []
        login_info = ' ' + tenant_name + ' ' + admin_username + ' ' + admin_password
        old_session_string = 'session=`$incorta_cmd login'
        new_session_string = 'session=`$incorta_cmd login ' + url + login_info + '`'
        old_sync_string = '$incorta_cmd sync_directory_with_ldap $session'
        new_sync_string = '$incorta_cmd sync_directory_with_ldap $session true'
        try:
            with open(sync_script_path, 'r') as sync_script_in:
                file_lines = sync_script_in.readlines()
                for line in file_lines:
                    if old_session_string in line:
                        session_replace = line.rstrip()
                        lines.append(line.replace(session_replace, new_session_string))

                    elif old_sync_string in line:
                        sync_repalce = line.rstrip()
                        lines.append(line.replace(sync_repalce, new_sync_string))

                    else:
                        lines.append(line)

            with open(sync_script_path, 'w') as sync_script_out:
                for line in lines:
                    sync_script_out.write(line)

        except EnvironmentError:
            print "Unable to Modify LDAP Sync Bash File Under /bin"
            writeLogMessage('Unable to Modify LDAP Sync Bash File Under /bin', mainLogger, 'error')


def sync_directory(incorta_home, orig_wd_path):
    """
    """
    incort_bin_path = incorta_home + os.sep + 'bin'
    dirExport_path = incorta_home + os.sep + 'dirExport'
    sync_script = 'sync_directory_with_ldap.sh'
    sync_script_path = incort_bin_path + os.sep + sync_script

    try:
        print "Populating Incorta Instance from LDAP"
        writeLogMessage("Populating Incorta Instance from LDAP", mainLogger, 'info')
        owd = os.getcwd()
        os.chdir(dirExport_path)
        run_sync_cmd = sync_script_path
        print run_sync_cmd
        writeLogMessage(run_sync_cmd, mainLogger, 'info')
        subprocess.call(run_sync_cmd, shell=True)
        os.chdir(owd)
    except Exception:
        Exception("Failed to run sync with LDAP and local Incorta instance")
        writeLogMessage("Failed to run sync with LDAP and local Incorta instance", mainLogger, 'critical')
        return

    # Creates Sync tag if LDAP populate was successful
    # sync_tag = orig_wd_path + os.sep + 'sync.txt'
    sync_tag = incorta_home + os.sep + 'sync.txt'
    f = open(sync_tag, 'w')
    f.close()


def tenant_updater(incorta_home, tenant):
    """
    """
    try:
        print "Updating Tenant"
        writeLogMessage('Updating Tenant', mainLogger, 'info')
        path_tmt = incorta_home + os.sep + 'tmt'
        tenant_update_ldap = './tmt.sh -u ' + tenant + ' file ldap.properties -f'
        owd = os.getcwd()
        os.chdir(path_tmt)
        subprocess.call(tenant_update_ldap, shell=True)
        os.chdir(owd)
    except Exception, e:
        print "Failed to update Tenant: ", tenant
        writeLogMessage("Failed to update Tenant: " % tenant, mainLogger, 'critical')
        return


def restart_incorta(incorta_home):
    """
    """
    try:
        print "Restarting Incorta"
        writeLogMessage('Restarting Incorta', mainLogger, 'info')
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
        writeLogMessage('Unable to restart Incorta instance', mainLogger, 'critical')
        return


def assign_roles_to_groups(incorta, session):
    """
    """
    # Assign roles
    # session =incorta.login(url,tenant,admin,password)
    try:
        print "Assigning Roles"
        writeLogMessage('Assigning Roles', mainLogger, 'info')
        incorta.assign_role_to_group(session, 'executive', 'SuperRole')
        incorta.assign_role_to_group(session, 'engineering', 'Analyze User')
        print "Assigned Roles Successfully"
        writeLogMessage('Assigned Roles Successfully', mainLogger, 'info')
    except Exception, e:
        print "Unable to assign Roles, Roles already assigned"
        writeLogMessage('Unable to assign Roles, Roles already assigned', mainLogger, 'warning')
        return


def read_users_from_csv(incorta_home):
    """
    """
    dirExport_path = incorta_home + os.sep + 'dirExport'
    user_groups_csv_path = dirExport_path + os.sep + 'user-groups.csv'

    print "Incorta Home: ", incorta_home
    writeLogMessage('%s %s' % ("Incorta Home: ", incorta_home), mainLogger, 'info')
    owd = os.getcwd()
    os.chdir(dirExport_path)
    print "Reading CSV files from: ", os.getcwd()
    writeLogMessage('%s: %s' % ("Reading CSV files from: ", os.getcwd()), mainLogger, 'info')
    user_list = {}
    COUNT = 0
    print "Extracting users from csv"
    writeLogMessage('Extracting users from csv', mainLogger, 'info')
    with open(user_groups_csv_path, "rb") as file:
        for col in file:
            if COUNT > 0:
                user = col.split(",")
                user[1] = user[1].strip('\n')
                user_list[user[1]] = user[0]
            COUNT += 1

    os.chdir(owd)
    return user_list
