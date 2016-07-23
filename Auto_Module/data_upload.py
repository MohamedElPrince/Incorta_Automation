import time
import os
import subprocess
from subprocess import PIPE, Popen
import commands
from sys import stdout

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

def load_validator(incorta_home, export_schema_names_list):
    schema_list = []
    failed_schemas = []
    cat_string = "\"" + incorta_home + os.sep + 'server' + os.sep + 'logs' + os.sep + 'catalina.out' + "\""


    cmd = 'grep "\*\*\* Load" ' + cat_string + """  | tail -5"""


    p = subprocess.check_output(cmd, stderr=subprocess.STDOUT, shell=True)
    print "Printing Return Value..", p, "\n"
    print "DONE PRINTING REUTRN VALUE"

    output = p.split('\n')

    #print output

    # for item in output:
    #     string = item.split()
    #     for piece in string:
    #         if 'completed!' in piece:
    #             schema_list.append(string[piece - 1])
    #
    #     print schema_list

    contained = True

    for item in output:

        string = item.split()
        print string
        for index in range(len(string)):

            if string[index] == 'completed!':
                print string[index-1]
                schema_list.append(string[index-1])


    print "Printing successfully Loaded Schemas", schema_list
    print "-------------------------------------------------------------------"
    print "Printing exported schema list ", export_schema_names_list


    # if contained:
    #     print "Schema Loading Successful"
    # else:
    #     print "Schemas contained loading errors", failed_schemas


    print "-------------------------------------------------------------------"
    print "END OF FUNCTION\n\n"

#def list_comparator():