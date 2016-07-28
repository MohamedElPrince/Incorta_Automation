import shutil, time
import select
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

def tail_lines(filename,linesback=10,returnlist=0):
    """Does what "tail -10 filename" would have done
       Parameters:
            filename   file to read
            linesback  Number of lines to read from end of file
            returnlist Return a list containing the lines instead of a string

    """
    avgcharsperline=75

    file = open(filename,'r')
    while 1:
        try: file.seek(-1 * avgcharsperline * linesback,2)
        except IOError: file.seek(0)
        if file.tell() == 0: atstart=1
        else: atstart=0

        lines=file.read().split("\n")
        if (len(lines) > (linesback+1)) or atstart: break
        #The lines are bigger than we thought
        avgcharsperline=avgcharsperline * 1.3 #Inc avg for retry
    file.close()

    if len(lines) > linesback: start=len(lines)-linesback -1
    else: start=0
    if returnlist: return lines[start:len(lines)-1]

    out=""
    for l in lines[start:len(lines)-1]: out=out + l + "\n"
    return out

def load_validator(incorta_home, export_schema_names_list, full_schema_export_list):
    COUNT = 0
    loaded = False
    cat_string = incorta_home + os.sep + 'server' + os.sep + 'logs' + os.sep + 'catalina.out'

    while True:
        if loaded == False:
            tail_list = tail_lines(cat_string, 1)
            print tail_list
        elif loaded == True:
            break








#     while loaded == False and COUNT < 60:
#         time.sleep(5)
#         schema_list = []
#         int_size = len(full_schema_export_list)
#         size = str(int_size)
#         size = '-' + size
#         cat_string = "\"" + incorta_home + os.sep + 'server' + os.sep + 'logs' + os.sep + 'catalina.out' + "\""
#         cmd = 'cat ' + cat_string + ' | tr -d \'\\000\' | ' + 'grep "\*\*\* Load" ' + """ | tail """ + size
#         p = subprocess.check_output(cmd, stderr=subprocess.STDOUT, shell=True)
#         output = p.split('\n')
#         print output
#         for item in output:
#             string = item.split()
#             for index in range(len(string)):
#                 if string[index] == 'completed!':
#                     schema_list.append(string[index-1])
#
#         if len(schema_list) == int_size:
#             loaded = True
#         COUNT += 1
# # Loaded schemas are stored in the variable schema_list
#     return schema_list

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