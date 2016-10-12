import json
import time
import os
import subprocess
import sys
from customLogger import mainLogger, writeLogMessage

"""
Loads all data on to Incorta Software

TODO
    Add log file dump for failure and success of each import and extract
    Need more try/catch handling
"""

Debug = False  # Debug flag for print statements


def Load_data(incorta, session, test_case_path):
    """
    Function loads all schemas to Incorta from the list of schemas
        args:
            incorta: Incorta API module
            session: session var returned by login function
            test_case_path: Path for test case within test suite
            names_list: list of names to upload
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """

    fileFound = False
    for files in os.listdir(test_case_path):
        if files == 'schema.txt':
            try:
                with open(os.path.join(test_case_path, files)) as text_file:
                    schema_file_list = [line.rstrip('\n') for line in text_file]
                fileFound = True
            except Exception, e:
                print ("schema.txt Not Found Inside Test Case")
                writeLogMessage('schema.txt Not Found Inside Test Case %s ' % (test_case_path), mainLogger,
                                'critical')

    if fileFound == True:
        schema_names = get_load_status(incorta, session, schema_file_list, command='pre_check')
        for key, value in schema_names.iteritems():
            count = 0
            if value < 0:
                print ("Schema %s status errno: %s" % (key, str(value)))
                writeLogMessage("Schema %s status errno: %s" % key, str(value), 'error')
            else:
                if value != 1:
                    while value != 1:
                        # print "Value Position1: ", value
                        if value == 0:
                            # print "Value Position2: ", value
                            upload_check = incorta.load_schema(session, key)
                            if Debug == True:
                                print upload_check, "For:", key
                                writeLogMessage('Upload Check %s, For: %s ' % (upload_check, key), mainLogger, 'info')
                            value = get_load_status(incorta, session, schema_file_list, key, command='status')
                            # print "Value Position3: ", value
                            if value != 1:
                                while (value == 2 and count < 60):
                                    time.sleep(5)
                                    value = get_load_status(incorta, session, schema_file_list, key, command='status')
                                    count += 1
                                    print "Loading schema ", key, count * 5, "seconds.."
                                    writeLogMessage('%s %s' % (count * 5, "seconds.."), mainLogger, 'info')
                            elif value == 1:
                                print "Loaded schema ", key, count * 5, "seconds.."
                                writeLogMessage('%s %s' % (count * 5, "seconds.."), mainLogger, 'info')
                    print 'New Schema loaded: ', key
                else:
                    print 'Schema Was Loaded Already: ', key
                    writeLogMessage('Schema loaded: %s ' % key, mainLogger, 'info')


def get_load_status(incorta, session, schema_file_list, schema_name=None, command='status'):
    """
    Function returns either status of single schema or returns a dictionary of common schemas between the passed in list
    and the $GET request sent to ENDPOINT which retrieves all schemas that are imported loaded into Incorta and there Status
        args:
            incorta: Incorta API module
            session: session var returned by login function
            schema_file_list: List of schema names from schema.txt file
            schema_name: Default None, only needed when command=status
            command: Default=status. pre_check command returns list of common schemas between
        returns:
            Return with pre_check comand
            dict = Key:Schema_Name, value:Status
            return dict

            Return with status command[Requires a valid schema_name]
            dict = key:Schema_Name, value:Status
        return value
            prints:
                Can print debug statements if needed
    """
    pre_check = incorta.get(session, "/service/schema/getSchemas")
    json_dict = {}
    for json_schema in json.loads(pre_check.content)['schemas']:
        json_dict[json_schema['name']] = json_schema['status']
    schema_names = {key: json_dict[key] for key in schema_file_list if key in json_dict}
    if command == 'pre_check':
        return schema_names
    elif command == 'status':
        try:
            for key, value in schema_names.iteritems():
                if key == schema_name:
                    return value
        except Exception:
            print "The GET request can not find: %s, check Schema.txt file" % schema_name
            writeLogMessage("The GET request can not find: %s, check Schema.txt file" % schema_name, mainLogger,
                            'error')


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