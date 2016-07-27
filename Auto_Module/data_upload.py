import shutil, time
import os
import subprocess


"""
Loads all data on the Incorta Software

TODO
    Add log file dump for failure and success of each import and extract
    Need more try/catch handling
"""

Debug = False  # Debug flag for print statements


def Load_data(incorta, session, names_list):
    """
    Function loads all schemas to Incorta from the list of schemas
        args:
            incorta: Incorta API module
            session: session var returned by login function
            names_list: list of names to upload
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """
    for names in names_list:
        upload_check = incorta.load_schema(session, names)
    if Debug == True:
        print upload_check, "For:", names

def load_validator(incorta_home, export_schema_names_list, full_schema_export_list):
    COUNT = 0
    loaded = False
    while loaded == False and COUNT < 60:
        time.sleep(5)
        schema_list = []
        int_size = len(full_schema_export_list)
        size = str(int_size)
        size = '-' + size
        cat_string = "\"" + incorta_home + os.sep + 'server' + os.sep + 'logs' + os.sep + 'catalina.out' + "\""
        cmd = 'cat ' + cat_string + ' | tr -d \'\\000\' | ' + 'grep "\*\*\* Load" ' + """ | tail """ + size
        print cmd
        p = subprocess.check_output(cmd, stderr=subprocess.STDOUT, shell=True)
        output = p.split('\n')
        print output
        for item in output:
            string = item.split()
            for index in range(len(string)):
                if string[index] == 'completed!':
                    schema_list.append(string[index-1])

        if len(schema_list) == int_size:
            loaded = True
        COUNT += 1
# Loaded schemas are stored in the variable schema_list
    return schema_list

def schema_load_validatior(export_schema_names_list, full_schema_export_list):
    if set(export_schema_names_list) == set(full_schema_export_list):
        print "LOADED DATA VALIDATED\n"
        print "Successfully Loaded Schemas: ", export_schema_names_list
    else:
        print "ERROR IN LOADING DATA"
        diff_set = set(full_schema_export_list) - set(export_schema_names_list)
        diff_list = list(diff_set)
        print diff_list

def backup_catalina(incorta_home):
    catalina_out = 'catalina.out'
    timestr = time.strftime("%m%d%Y_%H%M%S")
    catalina_path = incorta_home + os.sep + 'server' + os.sep + 'logs' + os.sep + catalina_out
    catalina_out_timestamp = 'catalina-' + timestr + '.out'
    catalina_backup_path = incorta_home + os.sep + 'server' + os.sep + 'logs' + os.sep + catalina_out_timestamp
    catalina_cmd_path = "\"" + incorta_home + os.sep + 'server' + os.sep + 'logs' + os.sep + catalina_out_timestamp + "\""
    shutil.copyfile(catalina_path, catalina_backup_path)
    cmd = '\\cp /dev/null ' + catalina_path
    subprocess.call(cmd, shell=True)