import dateutil.parser
import pandas as pd
import pickle
import time
import requests
import datetime
import pprint
import calendar

credentials = 'anahit.sarao@incorta.com' + '/token', '4Lua8UzUHPTIJKuy7ilUkxvWM6KbSsCGayTTOciX'
session = requests.Session()
session.auth = credentials
zendesk = 'https://incorta.zendesk.com'


# url = zendesk + '/api/v2/community/topics/' + str(topic_id) + '/posts.json?include=users'
# url = zendesk + '/api/v2/community/topics/' + str(topic_id) + '/posts.json?include=users'


def parser():
    id_list = []
    count = 0
    url = zendesk + '/api/v2/tickets/0.json'
    while count != 100:
        response = session.get(url)
        if response.status_code == 429:
            print('Rate limited! Please wait.')
            time.sleep(int(response.headers['retry-after']))
            continue
        if response.status_code == 200 or response.status_code == 304:
            data = response.json()
            id_list.append(str(data['ticket']['id']))
            print('Exporting: {}'.format(count, url))
        if response.status_code != 200:
            print('Error with status code {} {}'.format(response.status_code, url))
        count = count + 1
        url = zendesk + '/api/v2/tickets/' + str(count) + '.json'

def latest():
    update_list = []
    yesterday = datetime.datetime.now() - datetime.timedelta(days=1)
    print (yesterday)
    start_time=calendar.timegm(yesterday.timetuple())
    poll_url = zendesk + '/api/v2/incremental/tickets.json?start_time=' + str(start_time)
    print (poll_url)
    while poll_url:
        response = session.get(poll_url)
        if response.status_code == 200 or response.status_code == 304:
            poll_data = response.json()
            pprint.pprint(poll_data)
            update_list.append(str(poll_data['count']))
            poll_url = poll_data['next_page']
        if response.status_code == 429:
            print('Rate limited! Please wait.')
            time.sleep(int(response.headers['retry-after']))
            continue
        if response.status_code != 200:
            print('Error with status code {} {}'.format(response.status_code, poll_url))
    return update_list

new_list = latest()
print (new_list)

topic_data = {'posts': topic_posts, 'users': user_list}
with open('my_serialized_data_file', mode='wb') as f:
    pickle.dump(topic_data, f)

with open('my_serialized_data_file', mode='rb') as f:
    topic = pickle.load(f)

for post in topic['posts']:
    author = 'anonymous'
    for user in topic['users']:
        if user['id'] == post['author_id']:
            author = user['name']
            break
    print('"{}" by {}'.format(post['title'], author))

topic = pd.read_pickle('my_serialized_data')
posts_df = pd.DataFrame(topic['posts'], columns=['id', 'title', 'created_at', 'author_id'])
users_df = pd.DataFrame(topic['users'], columns=['id', 'name']).drop_duplicates(subset=['id'])

posts_df['created_at'] = posts_df['created_at'].apply(lambda x: dateutil.parser.parse(x).date())

merged_df = pd.merge(posts_df, users_df, how='left', left_on='author_id', right_on='id')
merged_df.rename(columns={'id_x': 'post_id'}, inplace=True)
merged_df.drop(['id_y', 'author_id'], axis=1, inplace=True)

merged_df.to_excel('topic_posts.xlsx', index=False)
print('Spreadsheet saved.')



# credentials = 'anahit.sarao@incorta.com' + '/token', '4Lua8UzUHPTIJKuy7ilUkxvWM6KbSsCGayTTOciX'
# zendesk = 'incorta.zendesk.com'
# session = requests.Session()
# session.auth = credentials
#
# topic_id = 123456
# topic_posts = []
# user_list = []
# url = zendesk + '/api/v2/community/topics/' + str(topic_id) + '/posts.json?include=users'
# while url:
#     response = session.get(url)
#     if response.status_code == 429:
#         print('Rate limited! Please wait.')
#         time.sleep(int(response.headers['retry-after']))
#         continue
#     if response.status_code != 200:
#         print('Error with status code {}'.format(response.status_code))
#         exit()
#     data = response.json()
#     topic_posts.extend(data['posts'])
#     user_list.extend(data['users'])
#     url = data['next_page']
#
# topic_data = {'posts': topic_posts, 'users': user_list}
# with open('my_serialized_data_file', mode='wb') as f:
#     pickle.dump(topic_data, f)
#
# with open('my_serialized_data_file', mode='rb') as f:
#     topic = pickle.load(f)
#
# for post in topic['posts']:
#     author = 'anonymous'
#     for user in topic['users']:
#         if user['id'] == post['author_id']:
#             author = user['name']
#             break
#     print('"{}" by {}'.format(post['title'], author))