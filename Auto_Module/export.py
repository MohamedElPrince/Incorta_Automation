import os, sys
"""
"""
Debug = True    #Debug flag for print statements

def incorta_import(incorta_home):
    """
    Function takes the incorta installation path to import Incorta API
    	args:
    		incorta_home: Incorta Home Directory Path
    	returns:
            Nothing
    	prints:
            Nothing
    """
    incorta_module = incorta_home.rstrip() + os.sep + "bin".rstrip()
    sys.path.append(incorta_module)
    import incorta
    global incorta






















# Temporary Definitions
test_suite = 'CSV_DataSources'
wd_path = "/Users/anahit/IncortaTesting/tmp/work/testingonly/"

# Calls each function for testing in order
incorta_import("/Users/anahit/Incorta Analytics")

session = login('http://localhost:8080/incorta/', 'super', 'super', 'super')

extract_test_suites(wd_path, test_suite)

import_datafiles(session, test_suite)

import_schema(session, test_suite)

import_dashboard(session, test_suite)