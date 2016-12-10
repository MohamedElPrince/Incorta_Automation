import multiprocessing
import os
import Queue
import threading
import requests
import json
import time
from customLogger import mainLogger, writeLogMessage
from qa.Auto_Module import file_tools

"""
Exports JSONs using multi threading with blocking Queue implementation
in parallel with RESTFULL API calling

TODO
    Add log file dump for failure and success of each import and extract
    Need more try/catch handling
    Needs Documentation
"""

Debug = True  # Debug flag for print statements


class Downloader(threading.Thread):
    # todo- need documentation

    # Downloader class - reads queue and downloads each file in succession
    def __init__(self, payload, output_paths):
        threading.Thread.__init__(self, name=os.urandom(16).encode('hex'))
        self.queue = payload
        self.output_paths = output_paths

    def run(self):
        # todo- need documentation
        while True:
            # Allocate Resources + Begin
            print '%s: Looking for next target' % self.name
            writeLogMessage('%s: Looking for next target' % self.name, mainLogger, 'info')
            url = self.queue.get()

            # request.get JSON file
            print "* Thread " + self.name + " - processing Payload"
            writeLogMessage('* Thread %s - processing Payload' % self.name, mainLogger, 'info')
            self.download_file(url)

            # Clean Up Resources + Shutdown
            self.queue.task_done()

    def download_file(self, url):
        # todo- need documentation
        t_start = time.clock()
        r = requests.get(url[0], params=url[1], headers=url[2], cookies=url[3])
        if (r.status_code == requests.codes.ok):
            t_elapsed = time.clock() - t_start
            print "* Thread: " + self.name + " Downloaded " + str(url[0]) + " in " + str(t_elapsed) + " seconds"
            writeLogMessage("* Thread:  %s  Download: %s in %s seconds" % (self.name, str(url[0]), str(t_elapsed)),
                            mainLogger, 'info')
            with open(url[4], "w") as f:
                f.write(r.content)
        else:
            print "* Thread: " + self.name + " Bad Package: " + str(url[0])
            writeLogMessage("* Thread:  %s  Bad Package: %s" % (self.name, str(url[0])), mainLogger, 'error')


class DownloadManager():
    # todo- need documentation
    def __init__(self, download_dict_input, thread_count=20):  # thread count set to 20 by default
        self.thread_count = thread_count
        self.download_dict = download_dict_input
        self.output_paths = download_dict_input

    def begin_downloads(self):
        # todo- need documentation
        queue = Queue.Queue()

        # Create Thread Pooled Queue
        for i in range(self.thread_count):
            t = Downloader(queue, self.output_paths)
            t.setDaemon(True)
            t.start()

        for node in self.download_dict:
            print 'Queuing: ', node + '\n'
            writeLogMessage('Queuing: %s' % node + '\n', mainLogger, 'info')
            # Insert List of Custom REST_API into Queue
            queue.put([self.download_dict[node][0], self.download_dict[node][1], self.download_dict[node][2],
                       self.download_dict[node][3], self.download_dict[node][4]])

        # Create Blocking Queue Thread Pool
        print '*** Main Thread Initialization ***'
        writeLogMessage('*** Main Thread Initialization ***', mainLogger, 'info')
        queue.join()
        print '*** Main Thread Shutdown ***' + '\n'
        writeLogMessage('*** Main Thread Shutdown ***\n', mainLogger, 'info')
        return


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


def export_dashboards_json(test_case_path_wd, test_case_path, user, session):
    """
    Function uses curl command to export dashboard in JSON format
        args:
            test_case_path_wd: path to test case of working directory
            test_case_path: path to test case of benchmark
            user: string of user currently being tested
            url: url for incorta session
            session: give session string returned by login API
        returns:
            Nothing
        prints:
            Messages for debugging and code completion
    """
    guid_Names = get_guid(test_case_path, user)
    thread_count = (multiprocessing.cpu_count() / 2)  # Use Half of max threads possible
    print '------------------Download------------------'
    print '--------------------------------------------'
    print '# of GUID:             ', len(guid_Names)
    print 'Output Directory:      ', test_case_path_wd
    print 'File list:             ', len(guid_Names)
    print "Current configuration indicated %s CPU/Cores...Optimizing" % thread_count
    print '--------------------------------------------'
    writeLogMessage('------------------Download------------------', mainLogger, 'INFO')
    writeLogMessage('--------------------------------------------', mainLogger, 'INFO')
    writeLogMessage('# of GUID:             ' + str(len(guid_Names)), mainLogger, 'INFO')
    writeLogMessage('Output Directory:      ' + test_case_path_wd, mainLogger, 'INFO')
    writeLogMessage('File list:             ' + str(len(guid_Names)), mainLogger, 'INFO')
    writeLogMessage('--------------------------------------------', mainLogger, 'INFO')

    payload_dict = {}
    session_altered_string = session.replace("'", "\"")
    session_dict = json.loads(session_altered_string)
    header_strike = {'Connection': 'keep-alive', 'Upgrade-Insecure-Requests': '1',
                     'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36',
                     'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
                     'Accept-Encoding': 'gzip, deflate, sdch, br',
                     'Accept-Language': 'en-US,en;q=0.8'}
    cookies = {'JSESSIONID': session_dict['id'], 'XSRF-TOKEN': session_dict['csrf']}

    for guid in guid_Names:
        payload = {'layout': guid, 'prompts': '', 'outputFormat': 'json', 'odbc': 'false', 'Save': 'View'}
        custom_url = session_dict['server'] + '/service/viewer'
        json_path = test_case_path_wd + os.sep + 'Baseline_Files' + os.sep + user + os.sep + guid + '.json'

        payload_dict[guid] = [custom_url, payload, header_strike, cookies,
                              json_path]  # list format follows [0]url, [1]get_param, [2]get_header [3]get_cookies [4]json_output_path

    # If there are no URLs to download then exit now, nothing to do!
    if len(payload_dict) is 0:
        print 'No URLs to download'
        writeLogMessage('No URLs to download', mainLogger, 'error')
        raise Exception('No URLS to download')
    else:
        pass
        download_manager = DownloadManager(payload_dict, thread_count)  # Create queue.pool with thread_count
        download_manager.begin_downloads()  # Start Threaded Download
