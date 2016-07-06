"""

"""
import os.path, sys


def incorta_import_python(incorta_home):
    incorta_module = incorta_home.rstrip() + os.sep + "bin".rstrip()
    sys.path.append(incorta_module)
    import incorta

def login (url, server, tenant, admin, password):
    try:
        incorta.login(server, admin, admin, password, True)
        incorta.login('http://localhost:8080/incorta/', 'super', 'super', 'super', True)
    except Exception, e:
        print "Login Failed"
        exit(1)



incorta_import_python()
login()