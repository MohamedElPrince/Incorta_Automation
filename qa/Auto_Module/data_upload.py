import json
import time
import sys
import os
from customLogger import mainLogger, writeLogMessage

"""
Loads all data on to Incorta Software

TODO
    Add log file dump for failure and success of each import and extract
    Need more try/catch handling
"""

Debug = False  # Debug flag for print statements


def load_data(incorta, session, test_case_path, loader_Validation_Path):
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
    schema_file_list, schema_file_dict = schema_file(test_case_path)
    print schema_file_list, 'list'
    if schema_file_list == None:
        pass
    else:
        try:
            for items in schema_file_list:
                if None != items:
                    fileFound = True
        except IndexError:
            writeLogMessage('Invalid Entry inside schema.txt %s ' % (test_case_path), mainLogger,
                            'critical')
            print IndexError("Invalid Entry inside schema.txt %s" % test_case_path)
        except:
            print "Unexpected error:", sys.exc_info()
            raise

    if fileFound == True:
        schema_names = get_load_status(incorta, session, schema_file_list, command='pre_check')
        max_count = 0
        count = 0
        for key, value in schema_names.iteritems():
            print key, value
            for schemaName, schemaTimer in schema_file_dict.iteritems():
                if key in schemaName:
                    if schemaTimer < 300:
                        max_count = schemaTimer
                    else:
                        max_count = 300
            if value == 1:
                print 'Schema Was Loaded Already: ', key
                writeLogMessage('Schema loaded: %s ' % key, mainLogger, 'info')
                log_name = loader_Validation_Path + os.sep + str(key) + '.suc'
                f = open(log_name, 'w')
                f.close()
            elif value <= 0:
                print ("Schema %s status errno: %s" % (key, str(value)))
                writeLogMessage("Schema %s status errno: %s" % (key, str(value)), mainLogger, 'info')
                if value < 0:
                    print "SCHEMA: ", key, " Load Failed...Reinitializing Load\n"
                    writeLogMessage("SCHEMA: %s. Load Failed...Reinitializing Load\n" % key, mainLogger, 'error')
                while value != 1:
                    if value != 1:
                        upload_check = incorta.load_schema(session, key)
                        if Debug == True:
                            print upload_check, "For:", key
                            writeLogMessage('Upload Check %s, For: %s ' % (upload_check, key), mainLogger, 'info')

                        value = get_load_status(incorta, session, schema_file_list, key, command='status')
                        if value != 1:
                            while (value == 2 and count < max_count):
                                time.sleep(1)
                                value = get_load_status(incorta, session, schema_file_list, key, command='status')
                                count += 1
                                print "Loading schema: ", key, count, "seconds.."
                                writeLogMessage('Loading Schema: %s %s %s' % (key, count, "seconds.."), mainLogger,
                                                'info')
                        if value == 1:
                            print 'New Schema loaded: ', key, count, "seconds..."
                            writeLogMessage('Loaded schema: %s %s %s' % (key, count, "seconds.."), mainLogger, 'info')
                            log_name = loader_Validation_Path + os.sep + str(key) + '.suc'
                            f = open(log_name, 'w')
                            f.close()
                            break
                        elif (value < 0) or (count >= max_count):
                            if count >= max_count:
                                print 'The schema is denoted as faulty...exiting'
                                exit(1)
                            else:
                                print ("Schema %s status errno: %s" % (key, str(value)))
                                writeLogMessage("Schema %s status errno: %s" % (key, str(value)), mainLogger, 'error')
                                print "SCHEMA ", key, " FAILED TO LOAD...Will not Reload Till Next Run \n"
                                writeLogMessage("SCHEMA %s FAILED TO LOAD...Will not Reload Till Next Run \n" % key,
                                                mainLogger, 'error')
                                log_name = loader_Validation_Path + os.sep + str(key) + '.dif'
                                f = open(log_name, 'w')
                                f.close()
                                break
                    elif value < 0:
                        print ("Schema %s status errno: %s" % (key, str(value)))
                        writeLogMessage("Schema %s status errno: %s" % (key, str(value)), mainLogger, 'error')
                        print "SCHEMA ", key, " FAILED TO LOAD...Will not Reload Till Next Run \n"
                        writeLogMessage("SCHEMA %s FAILED TO LOAD...Will Not Reload Till Next Run \n" % key, mainLogger,
                                        'error')
                        break


def schema_file(test_case_path):
    """
    """
    schema_file_list = []
    schema_dict = {}
    for files in os.listdir(test_case_path):
        if files == 'schema.txt':
            try:
                with open(os.path.join(test_case_path, files)) as text_file:
                    for line in text_file:
                        # schema_file_list = [line.rstrip('\n') for line in text_file]
                        schemaName, timerValue = line.split(':')
                        print schemaName, timerValue
                        schema_file_list.append(schemaName)
                        schema_dict[schemaName] = timerValue
                    return schema_file_list, schema_dict
            except IOError as e:
                print "\nI/O error({0}): {1}".format(e.errno, e.strerror)
                writeLogMessage('\nschema.txt Not Found Inside Test Case %s' % (test_case_path),
                                mainLogger, 'critical')
                print 'schema.txt Not Found Inside Test Case %s' % test_case_path
            except ValueError as ve:
                writeLogMessage(('\nError within schema.txt file; %s' % ve), mainLogger, 'critical')
                print ValueError("\nError within schema.txt file; %s" % ve)
            except:
                print "Unexpected error:", sys.exc_info()
                raise


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
            Exception("The GET request can not find: %s, check Schema.txt file" % schema_name)
            writeLogMessage("The GET request can not find: %s, check Schema.txt file" % schema_name, mainLogger,
                            'error')
