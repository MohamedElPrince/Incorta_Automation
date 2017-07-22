import requests
import json
import os
import errno
import time

ROOT_DIR = os.path.dirname(__file__) # This is your Project Root


def create_directory(path, folder_name):
    """
    Function creates directory
        args:
            path: any path can be given
            folder_name: name of the directory
        returns:
            The path of the new directory
        prints:
            Nothing
    """
    appended_path = path + os.sep + folder_name
    try:
        os.makedirs(appended_path)
        return appended_path
    except OSError as exc:
        if exc.errno == errno.EEXIST and os.path.isdir(appended_path):
            return appended_path
            pass
        else:
            return appended_path

class ZendeskUser(object):
    """A User of Zendesk for Incorta Zendesk"""

    def __init__(self, credentials, zendesk_url):
        self.credentials = credentials
        self.zendesk_url = zendesk_url
        self.user_url = None
        self.user_id = []
        self.user_total = None
        self.data = None
        self.session = self.create_auth_session()

    def create_auth_session(self):
        """Generate Session"""

        session = requests.Session()
        session.auth = self.credentials
        return session

    def get_ticket_id(self):
        """Get Singular Ticket Id"""

        self.user_url = self.zendesk_url + '/api/v2/tickets/'
        self.new_url = self.user_url
        count = 0
        while self.new_url != None:
            response = self.session.get(self.user_url)
            data = response.json()
            self.user_total = data['count']
            self.parser(data)
            self.new_url = data['next_page']
            print self.new_url
            if response.status_code != 200 or response.status_code != 304:
                self.user_url = data['next_page']
                count = count +1
            else:
                self.new_url = None

    def parser(self, data):
        """Parse all Ids"""
        max = len(data['tickets'])
        for count in range(0,max):
            self.user_id.append(data['tickets'][count]['id'])

    def dump_jsons(self):
        """Dump json for kafka consumer"""
        max = len(self.user_id)
        path = create_directory(ROOT_DIR,'json_tickets')
        for counter in range(0,max):
            self.user_url = self.zendesk_url + '/api/v2/tickets/' + str(self.user_id[counter])
            response = self.session.get(self.user_url)
            if response.status_code == 429:
                print('Rate limited! Please wait.')
                time.sleep(int(response.headers['retry-after']))
                continue
            if response.status_code != 404:
                file_name = str(self.user_id[counter] +'.json')
                file_path = os.path.join(path,file_name)
                with open(file_path,'w') as output_file:
                    json.dump(response.json(), output_file)

def main():
    """Main"""
    credentials = 'anahit.sarao@incorta.com' + '/token', '4Lua8UzUHPTIJKuy7ilUkxvWM6KbSsCGayTTOciX'
    zendesk_url = 'https://incorta.zendesk.com'
    z1 = ZendeskUser(credentials, zendesk_url)
    z1.get_ticket_id()
    z1.dump_jsons()

if __name__ == '__main__':
    main()
