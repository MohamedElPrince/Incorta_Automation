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

def login(url, tenant, admin, password):
    """
    Function takes in login information and attempts to login through Incorta API
    	args:
    		url: Url for the Incorta instance
    		tenant: Tenant name for instance
    		admin: Username for instance
    		password: Password for instance
    	returns:
            The session for the Incorta instance is returned
    	prints:
            Handles exception case of login fails
    """
    try:
        return incorta.login(url, tenant, admin, password, True)
    except Exception, e:
        print "Login Failed"
        exit(1)

def export_sources():






















# Temporary Definitions
test_suite = 'CSV_DataSources'
wd_path = "/Users/anahit/IncortaTesting/tmp/work/testingonly/"

# Calls each function for testing in order
incorta_import("/Users/anahit/Incorta Analytics")

session = login('http://localhost:8080/incorta/', 'super', 'super', 'super')

