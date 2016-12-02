import threading
from multiprocessing.pool import ThreadPool
import urllib
import pycurl
from customLogger import mainLogger, writeLogMessage
from time import time as timer
from cStringIO import StringIO
import os, file_tools, zipfile, requests, json

"""
OLD CODE FOR JSON EXTRACTION -- SOME DEFS ARE STILL BEING USED

"""

Debug = True  # Debug flag for print statements


def export_dashboards(incorta, session, export_zips_path, dashboards):
    """
    Function loads all dashboards to Incorta from the list of dashboards
        args:
            incorta: Incorta API module
            session: session var returned by login function
            export_zips_path: temporary zip file directory path
            dashboards: list of dashboards to upload
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """
    for dash_full_path in dashboards:

        str_tup = dash_full_path.split('/')
        dash_name = str_tup[-1]
        temp_path = export_zips_path + os.sep + dash_name + '.zip'

        try:
            export_check = incorta.export_dashboards(session, temp_path, dash_full_path)
        except Exception, e:
            print ('ERROR: Dashboard:', names, " Not Found")
            writeLogMessage('%s %s %s' % ('ERROR: Dashboard:', names, " Not Found"), mainLogger, 'info')
            dashboards.remove(names)
            export_dashboards(incorta, session, export_zips_path, dashboards)
        if Debug == False:
            print export_check
            writeLogMessage(export_check, mainLogger, 'debug')
    return dashboards


def export_schemas(incorta, session, export_zips_path, schemas):
    """
    Function loads all schemas to Incorta from the list of schemas
        args:
            incorta: Incorta API module
            session: session var returned by login function
            export_zips_path: temporary zip file directory path
            schemas: list of schemas to upload
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """

    # cleaned_export_schema = [x.replace(' ', '_') for x in schemas]
    for names in schemas:
        temp_name = names
        temp_path = export_zips_path + os.sep + temp_name + '.zip'
        try:
            export_check = incorta.export_schemas(session, temp_path, temp_name)
        except Exception, e:
            print ('ERROR: Dashboard:', names, " Not Found")
            writeLogMessage('%s %s %s' % ('ERROR: Dashboard:', names, " Not Found"), mainLogger, 'critical')
            schemas.remove(names)
            export_schemas(incorta, session, export_zips_path, schemas)
        if Debug == False:
            print export_check
            writeLogMessage(export_check, mainLogger, 'debug')
    return schemas


def export_zip(export_zips_path, test_case_export_path_wd, export_file_name):
    """
    Function extracts zips files from working directory to export path
        args:
            export_zips_file_path: temporary zip file directory path
            test_case_export_path_wd: path of zip uncompress and extraction
            export_file_name: names of files to be extracted from temp to working directory
        returns:
            Nothing
        prints:
            Nothing
    """
    # cleaned_export_file_name = [x.replace(' ', '_') for x in export_file_name]
    for files in export_file_name:
        str_tup = files.split('/')
        zip_name = str_tup[-1]
        extension = '.zip'
        file_path_wd = file_tools.create_directory(test_case_export_path_wd, zip_name)
        file_raw_path = os.path.join(export_zips_path, zip_name + extension)
        zip_ref = zipfile.ZipFile(file_raw_path)
        zip_ref.extractall(file_path_wd)
        zip_ref.close()


def create_temp_directory(test_case_path_wd):
    temp_path = file_tools.create_directory(test_case_path_wd, 'zip_export')
    return temp_path


"""KEEP CODE IN CURRENT CONDITION DO NO MANIPULATE"""
# todo-code moved to json_extraction.py as of December 1,2016; callsign:slumpy
"""KEEP CODE IN CURRENT CONDITION DO NO MANIPULATE"""


def get_guid(test_case_path, user):
    """
    Function gets GUID names from JSON file name
        args:
            test_case_path: path to test case in working directory
            user: string of user currently being tested
        returns:
            The GUID of the dashboard
        prints:
            Nothing
    """
    guid_Names = []
    test_case_subdirectories = file_tools.get_subdirectories(test_case_path)
    for dirs in test_case_subdirectories:
        if user in dirs:
            user_path = file_tools.get_path(test_case_path, dirs)
            for files in os.listdir(user_path):
                guid_Names.append(os.path.splitext(files)[0])
    return guid_Names


"""Orginal method of json extraction using subprocess calling method"""
# def export_dashboards_json(session_id, test_case_dashboard_export_list, csrf_token, test_case_path_wd, test_case_path, user, url, session):
#     """
#     Function uses curl command to export dashboard in JSON format
#         args:
#             session_id: session id returned by login API
#             dashboard_id: dashbaord GUID
#             csrf_token: csrf_token returned by login API
#             test_case_path_wd: path to test case of working directory
#             test_case_path: path to test case of benchmark
#             user: string of user currently being tested
#             url: url for incorta session
#         returns:
#             Nothing
#         prints:
#             Nothing
#     """
#     start = timer()
#     guid_Names = get_guid(test_case_path, user)
#     for guid in guid_Names:
#         dash_id = str(guid)
#         user_path = test_case_path_wd + os.sep + 'Baseline_Files' + os.sep + user + os.sep
#         json_name = dash_id + '.json'
#         json_path = user_path + json_name
#         cmd = """curl \'""" + url + """/service/viewer?layout=""" + dash_id \
#         + """&prompts=&outputFormat=json&odbc=false&Save=View' -H 'Cookie: JSESSIONID=""" + session_id \
#         + """; XSRF-TOKEN=""" + csrf_token + """' --compressed > """ + json_path
#         subprocess.call(cmd, shell=True)
#     print "Elapsed Time: %s" % (timer() - start)
'''Export Json Multi Thread using requests method 1'''


def export_dashboards_json(session_id, test_case_dashboard_export_list, csrf_token, test_case_path_wd, test_case_path,
                           user, url, session):
    start = timer()
    guid_Names = get_guid(test_case_path, user)

    results = ThreadPool(20).imap_unordered(get_json1, guid_Names, session)

    for url, html, error in results:
        if error is None:
            print("%r fetched in %ss" % (url, timer() - start))
        else:
            print("error fetching %r: %s" % (url, error))
    print("Elapsed Time: %s" % (timer() - start,))

    print("Elapsed Time: %s" % (timer() - start,))


def get_json1(guid_Names, session):
    """
    Function uses curl command to export dashboard in JSON format
        args:
        returns:
            Nothing
        prints:
            Nothing
    """
    session_altered_string = session.replace("'", "\"")
    session_dict = json.loads(session_altered_string)
    header_strike = {'Connection': 'keep-alive', 'Upgrade-Insecure-Requests': '1',
                     'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36',
                     'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
                     'Accept-Encoding': 'gzip, deflate, sdch, br',
                     'Accept-Language': 'en-US,en;q=0.8'}
    cookies = {'JSESSIONID': session_dict['id'], 'XSRF-TOKEN': session_dict['csrf']}
    for guid in guid_Names:
        payload = {'layout': guid, 'prompts': '', 'outputFormat': 'json',
                   'odbc': 'false', 'Save': 'View'}
        response = requests.get(session_dict['server'] + '/service/viewer', params=payload, headers=header_strike,
                                cookies=cookies)
        return response.content


'''Export Json Multi Thread using requests method 1'''

'''Export Json Multi Thread using requests method 2'''
response_dict = {}


def export_dashboards_json2(session_id, dashboard_id, csrf_token, test_case_path_wd, test_case_path, user, url,
                            session):
    start = timer()
    global start
    guid_names = get_guid(test_case_path, user)
    threads = [threading.Thread(target=get_json2, args=(guid, session, user, test_case_path_wd)) for guid in guid_names]

    for thread in threads:
        thread.start()
    for thread in threads:
        thread.join()

    threads2 = [threading.Thread(target=write_json2, args=(res, path)) for res, path in response_dict.iteritems()]
    for thread2 in threads2:
        thread2.start()
    for thread2 in threads2:
        thread2.join()

    print "Elapsed Time: %s" % (timer() - start)


def get_json2(guid_names, session, user, test_case_path_wd):
    """
    Function uses curl command to export dashboard in JSON format
        args:
            guid_names: dashbaord GUID
            session: session id returned by login API
            user: string of user currently being tested
            test_case_path_wd: path to test case of working directory
        returns:
            Nothing
        prints:
            Time taken to export single Json & Total time taken to export all Json
    """
    session_altered_string = session.replace("'", "\"")
    session_dict = json.loads(session_altered_string)
    header_strike = {'Connection': 'keep-alive', 'Upgrade-Insecure-Requests': '1',
                     'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36',
                     'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
                     'Accept-Encoding': 'gzip, deflate, sdch, br',
                     'Accept-Language': 'en-US,en;q=0.8'}
    cookies = {'JSESSIONID': session_dict['id'], 'XSRF-TOKEN': session_dict['csrf']}
    payload = {'layout': guid_names, 'prompts': '', 'outputFormat': 'json', 'odbc': 'false', 'Save': 'View'}
    response = requests.get(session_dict['server'] + '/service/viewer', params=payload, headers=header_strike,
                            cookies=cookies)
    print "'%s\' fetched in %ss" % (guid_names, (timer() - start))
    print 'Output: %s' % (response.status_code)
    dash_id = str(guid_names)
    json_path = test_case_path_wd + os.sep + 'Baseline_Files' + os.sep + user + os.sep + dash_id + '.json'
    response_dict[response] = json_path


def write_json2(response, path):
    with open(path, 'w') as jdump:
        jdump.write(response.content)


'''Export Json Multi Thread using requests method 2'''

# todo - Commented out Pycurl code not needed as of now (Tuesday Nov,29,2016)
'''Export Json Multi Thread using requests method 3'''


def export_dashboards_json3(session_id, test_case_dashboard_export_list, csrf_token, test_case_path_wd, test_case_path,
                            user, url, session):
    """
    Function uses curl command to export dashboard in JSON format
        args:
            session_id: session id returned by login API
            dashboard_id: dashbaord GUID
            csrf_token: csrf_token returned by login API
            test_case_path_wd: path to test case of working directory
            test_case_path: path to test case of benchmark
            user: string of user currently being tested
            url: url for incorta session
        returns:
            Nothing
        prints:
            Nothing
    """
    start = timer()
    urls = []  # list of urls
    # reqs: List of individual requests.
    # Each list element will be a 3-tuple of url (string), response string buffer
    # (cStringIO.StringIO), and request handle (pycurl.Curl object).
    reqs = []
    cookies = []
    payload = []
    header = []
    guid_Names = get_guid(test_case_path, user)
    for guid in guid_Names:
        dash_id = str(guid)
        user_path = test_case_path_wd + os.sep + 'Baseline_Files' + os.sep + user + os.sep
        json_name = dash_id + '.json'
        json_path = user_path + json_name
        cmd = url + '?'
        cmd2 = """&prompts=&outputFormat=json&odbc=false&Save=View' -H 'Cookie: JSESSIONID=""" + session_id \
               + """; XSRF-TOKEN=""" + csrf_token + """' --compressed > """ + json_path
        payload_params = {'layout': dash_id, 'prompts': '', 'outpuFormat': 'json', 'odbc': 'fasle', 'Save': 'View'}
        cookies_param = 'JSESSIONID=%s; XSRF-TOKEN=%s' % (session_id, csrf_token)
        cookies.append(cookies_param)
        payload.append(payload_params)
        header_params = ['--compressed > %s' % json_path]
        header.append(header_params)
        urls.append(cmd)
    for url in urls:
        print url
    reqs = multicurl(urls, reqs, payload, cookies, header)

    for req in reqs:
        print req[1].getvalue()  # contains response content

    print "Elapsed Time: %s" % (timer() - start)


def multicurl(urls, reqs, params, cookies, header):
    # Build multi-request object.
    m = pycurl.CurlMulti()
    for url, param, cook, head in zip(urls, params, cookies, header):
        response = StringIO()
        handle = pycurl.Curl()
        if param:
            url += urllib.urlencode(param)
        handle.setopt(pycurl.URL, url)
        handle.setopt(handle.COOKIE, cook)
        handle.setopt(handle.FAILONERROR, True)
        handle.setopt(handle.HTTPHEADER, head)
        # handle.set_option(pycurl.HTTPGET, 1)
        handle.setopt(pycurl.WRITEFUNCTION, response.write)
        req = (url, response, handle)

        # handle.setopt(handle.URL, url + urllib.urlencode(param))
        # handle.setopt(handle.CONNECTTIMEOUT, 5)
        # handle.setopt(handle.TIMEOUT, 8)

        # Note that the handle must be added to the multi object
        # by reference to the req tuple (threading?).
        m.add_handle(req[2])
        reqs.append(req)

    # Perform multi-request.
    # set num_handles before the outer while loop.
    SELECT_TIMEOUT = 5.0
    num_handles = len(reqs)
    while num_handles:
        ret = m.select(SELECT_TIMEOUT)
        if ret == -1:
            continue
        while 1:
            ret, num_handles = m.perform()
            if ret != pycurl.E_CALL_MULTI_PERFORM:
                break
    return reqs


"""KEEP CODE IN CURRENT CONDITION DO NO MANIPULATE"""
# todo - Commented out Pycurl code not needed as of now (Tuesday Nov,29,2016)
# todo-code moved to json_extraction.py as of December 1,2016; callsign:slumpy
"""KEEP CODE IN CURRENT CONDITION DO NO MANIPULATE"""
