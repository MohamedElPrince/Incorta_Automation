import os, sys

import errno

"""
"""
Debug = True    #Debug flag for print statements

def export_dashboards(incorta, session, wd_path, dashboards):
    """
    """
    export_check = incorta.export_dashboards(session, wd_path, dashboards)
    if Debug == True:
        print export_check

def export_schemas(incorta, session, wd_path, schemas):
    """
    """
    wd_path = wd_path + '/exported_files'
    try:
        os.makedirs(wd_path)
    except OSError as exc:
        if exc.errno == errno.EEXIST and os.path.isdir(wd_path):
            pass
        else:
            raise
    wd_path += '/A_1_schema.zip'
    export_check = incorta.export_schemas(session, wd_path, schemas)
    if Debug == True:
        print export_check

# Temporary Definitions
# test_suite = 'CSV_DataSources'
# wd_path = "/Users/anahit/IncortaTesting/tmp/work/07:14:2016-12:03:11/"
#
# # Calls each function for testing in order
# incorta_import("/Users/anahit/Incorta Analytics")
#
# session = login('http://localhost:8080/incorta/', 'super', 'super', 'super')
# dashboards = "A_01_CASE Dashboard"
# schemas = 'A_01_CASE'
#export_dashboards(session, wd_path, dashboards)
#export_schemas(session, wd_path, schemas)