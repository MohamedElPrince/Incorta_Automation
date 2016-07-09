import os, sys
import zipfile
import json


def incorta_import(incorta_home):
    incorta_module = incorta_home.rstrip() + os.sep + "bin".rstrip()
    sys.path.append(incorta_module)
    import incorta
    global incorta


def login(url, tenant, admin, password):
    try:
        return incorta.login(url, tenant, admin, password, True)
    except Exception, e:
        print "Login Failed"
        exit(1)

def extract_test_suites(wd_path, test_suite):
    python_work_dir = os.getcwd()
    test_suite_path = python_work_dir + test_suite
    extension = '.zip'
    for root, dirs, files in os.walk(test_suite_path):
        for file in files:
            if file.endswith(extension):
                file_raw_dir = os.path.join(root, file)
                zip_ref = zipfile.ZipFile(file_raw_dir)
                zip_ref.extractall(wd_path)
                zip_ref.close()

def import_datafiles(session, test_suite):
    python_work_dir = os.getcwd()
    test_suite_path = python_work_dir + test_suite
    extension = '.zip'
    upload_check = []
    for root, dirs, files in os.walk(test_suite_path):
        for file in files:
            if 'datafile' in file:
                if file.endswith(extension):
                    file_full_path = os.path.join(root, file)
                    upload_check.append(incorta.upload_data_file(session, file_full_path))
    for check in upload_check:
        print check,

def import_schema(session, test_suite):
    python_work_dir = os.getcwd()
    test_suite_path = python_work_dir + test_suite
    extension = '.zip'
    upload_check = []
    for root, dirs, files in os.walk(test_suite_path):
        for file in files:
            if 'schema' in file:
                if file.endswith(extension):
                    file_full_path = os.path.join(root, file)
                    upload_check.append(incorta.import_tenant(session, file_full_path, True))
    for checks in upload_check:
        print checks,

def import_dashboard(session, test_suite):
    python_work_dir = os.getcwd()
    test_suite_path = python_work_dir + test_suite
    extension = '.zip'
    upload_check = []
    for root, dirs, files in os.walk(test_suite_path):
        for file in files:
            if 'dashboard' in file:
                if file.endswith(extension):
                    file_full_path = os.path.join(root, file)
                    upload_check.append(incorta.import_tenant(session, file_full_path, True))
    for checks in upload_check:
        print checks,


def logout(session):
    try:
        incorta.logout(session)
    except Exception, e:
        print 'Failed to logout'
        exit(1)


test_suite = '/CSV_DataSources'
wd_path = "/Users/anahit/IncortaTesting/tmp/work/testingonly/"

incorta_import("/Users/anahit/Incorta Analytics")
session = login('http://localhost:8080/incorta/', 'super', 'super', 'super')
extract_test_suites(wd_path, test_suite)
import_datafiles(session, test_suite)
import_schema(session, test_suite)
import_dashboard(session, test_suite)


logout(session)
session_id = session[21:53]
