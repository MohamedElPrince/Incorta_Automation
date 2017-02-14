
# Zenpy accepts an API token
# creds = {
#     'email' : 'youremail',
#     'token' : 'yourtoken',
#     'subdomain': 'yoursubdomain'
# }
#
# # An OAuth token
# creds = {
#   "subdomain": "yoursubdomain",
#   "oauth_token": "youroathtoken"
# }
#
# # Or a password
# creds = {
#     'email' : 'youremail',
#     'password' : 'yourpassword',
#     'subdomain': 'yoursubdomain'
# }
#
#
#
# from zenpy import Zenpy
#
#
# zenpy_client = Zenpy(**creds)


# Using Curl Command
# curl https://{subdomain}.zendesk.com/api/v2/incremental/organizations.json?start_time=1332034771 \
#   -v -u {email_address}:{password}


import requests


# Set the request parameters
url = 'https://incorta.zendesk.com/api/v2/groups.json'
user = 'nadim.sarras@incorta.com'
pwd = 'xyzsharktooth5'

response = requests.get(url, auth=(user, pwd))

if response.status_code != 200:
    print('Status:', response.status_code, 'Problem with the request. Exiting.')
    exit()

data = response.json()

print( 'First group = ', data['groups'][0]['name'] )

group_list = data['groups']
for group in group_list:
    print(group['name'])

