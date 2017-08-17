import json
import requests
import argparse

def contrust_json(url, name, path, regex):
    data = {'@type': 'RollingLogsSourceDynamicLiveName', 'id': 0, 'name': str(name), 'pastLogsType': 'NAME',
            'pattern': str(path),
            'reader': {'fieldTypes': {}, 'filters': [], 'supportedServerites': [],
                       'targetReader': {'@type': 'GrokTextReader', 'charset': 'UTF-8',
                                        'fieldTypes': {'lf_raw': 'STRING'},
                                        'grokBean': {'caseInsensitive': True, 'dotAll': True, 'multiline': True,
                                                     'pattern': str(regex), 'subStringSearch': False},
                                        'overflowAttribute': None}}, 'uiSettings': {}}
    json_data = json.dumps(data)
    custom_payload = {'Content-Type': 'application/json;charset=UTF-8'}
    response = requests.post(url, data=json_data, headers=custom_payload)
    if response.status_code == 200:
        print('Log Sniffer has been updated...')

def main():
    print("Sample Input: -u http://localhost:8082/c/sources -n IncortaLog -p ../server/logs/catalina*.* -r (?<Line>.+)")
    parser = argparse.ArgumentParser(description='Description of your LogSniffer Automation', formatter_class=argparse.RawTextHelpFormatter)
    parser.add_argument('-u', '--url', help='LogSniffer URL with Port. \nSample Input: https://localhost:8083/c/sources', default='https://localhost:8083/c/sources', required=True)
    parser.add_argument('-n', '--name', help='LogSniffer Source Name. \nSample Input: IncortaLogSniffer', default='IncortaLogSniffer' ,required=True)
    parser.add_argument('-p', '--path', help='LogSniffer Log Path Pattern. \nSample Input: ../server/logs/catalina*.*', required=True)
    parser.add_argument('-r', '--regex', help='LogSniffer Regex Pattern. \nSample Input: (?<Line>.+)', required=True)
    args = parser.parse_args()
    print('Current Arguments: {}').format(args)
    contrust_json(args.url, args.name, args.path, args.regex)

if __name__ == '__main__':
    main()