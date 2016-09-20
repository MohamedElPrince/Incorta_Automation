import time
import os
import subprocess
from customLogger import mainLogger, writeLogMessage

"""
Loads all data on to Incorta Software

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
        writeLogMessage('Upload Check %s, For: %s ' % (upload_check, names), mainLogger, 'info')


def load_validator(incorta_home, export_schema_names_list, full_schema_export_list):
    COUNT = 0
    LOAD_COUNTER = 0
    loaded = False
    while loaded == False and COUNT < 60:
        time.sleep(5)
        schema_list = []
        int_size = len(full_schema_export_list)
        size = str(int_size)
        size = '-' + size
        cat_string = "\"" + incorta_home + os.sep + 'server' + os.sep + 'logs' + os.sep + 'catalina.out' + "\""
        cmd = 'cat ' + cat_string + ' | tr -d \'\\000\' | ' + 'grep "\*\*\* Load" ' + """ | tail """ + size
        p = subprocess.check_output(cmd, stderr=subprocess.STDOUT, shell=True)
        output = p.split('\n')

        # Waits for Schema to Load
        if full_schema_export_list[-1] not in output[int_size-1]:
            print "Still waiting for schema to load..", full_schema_export_list[-1]
            writeLogMessage('%s %s' % ("Still waiting for schema to load..", full_schema_export_list[-1]), mainLogger, 'info')
            LOAD_COUNTER += 1
            if len(schema_list) == int_size:
                loaded = True
        # If schema is loaded, the schema name will be appended to schema_list
        else:
            for item in output:
                string = item.split()
                for index in range(len(string)):
                    if string[index] == 'completed!':
                        schema_list.append(string[index-1])

            if len(schema_list) == int_size:
                loaded = True
        COUNT += 1
        print "Loading schema ", full_schema_export_list[-1], COUNT*5, "seconds.."
        writeLogMessage('%s %s' % (COUNT*5, "seconds.."), mainLogger, 'info')
    # Loaded schemas are stored in the variable schema_list
    return schema_list

def schema_load_validatior(export_schema_names_list, full_schema_export_list, Loader_Validation_Path):
    """
    Determines The full list of successfully loaded schemas, dif and suc files are generated in the loaded_validator function
    """
    if set(export_schema_names_list) == set(full_schema_export_list):
        print "LOADED DATA VALIDATED\n"
        writeLogMessage("LOADED DATA VALIDATED\n", mainLogger, 'info')
        print "Successfully Loaded Schemas: ", export_schema_names_list
        writeLogMessage('%s %s' % ("Successfully Loaded Schemas: ", export_schema_names_list), mainLogger, 'info')

    else:
        print "ERROR IN LOADING DATA"
        writeLogMessage('ERROR IN LOADING DATA', mainLogger, 'warning')
        diff_set = set(full_schema_export_list) - set(export_schema_names_list)
        diff_list = list(diff_set)
        print diff_list
        writeLogMessage(diff_list, mainLogger, 'warning')


def loaded_validator(loaded_schema_names, loaded_schema, Loader_Validation_Path):
    for schema in loaded_schema:
        if schema in loaded_schema_names:
            print "LOAD SUCCESSFUL: ", loaded_schema, "\n"
            log_name = Loader_Validation_Path + os.sep + str(schema) + '.suc'
            f = open(log_name, 'w')
            f.close()
        else:
            print "SCHEMA ", schema, " FAILED TO LOAD \n"
            log_name = Loader_Validation_Path + os.sep + str(schema) + '.dif'
            f = open(log_name, 'w')
            f.close()







