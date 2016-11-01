import sys, os, time
from qa.Auto_Module.file_tools import create_directory

"""
------------------------------------------Initialization----------------------------------------
"""
#ROOT_DIR = sys.Path(__file__) - 2
ROOT_DIR = os.path.dirname(os.path.dirname(os.path.dirname(__file__))) # This is your Project Root

# All the arguments which are passed in command line
# sys.argv[0:] = argv

# sys.argv[1] is the config file
config_file = sys.argv[1]

# Includes rest of arguments passed by user
commands = sys.argv[2:]

config_defaults = {'incorta_home': '/home/Incorta', 'tenant_home': '/home/tenants',
                   'admin_user': 'Super', 'admin_pass': 'none',
                   'skip_validation': 'False', 'skip_xml_validation': 'False',
                   'extract_csv': 'False', 'wd_path': '~/IncortaTesting/tmp/work', 'tenant': 'Demo',
                   'url': 'http://localhost:8080/incorta', 'ldap_url': 'ldap://dev01.incorta.com:389',
                   'ldap_base': 'dc=dev01,dc=incorta,dc=com', 'ldap_user_mapping_login': 'uid',
                   'ldap_group_mapping_member': 'member', 'ldap_group_search_filter': '(objectClass=groupOfNames)',
                   'include_schemas': 'True', 'include_user_testing': 'True', 'skip_data_load': 'True', 'run_tests': 'ALL'}


# config_defaults will hold all of the keys from the above dictionary
# The values of the keys in config_defaults will be parsed from the config file

def set_block_defaults(commands, config_defaults):
    """
    Function checks if an import, data load, or extract file argument is passed after the config file
    If so, the key corresponding to the action will be set to True
        args:
            commands: the arguments after the config file
        returns:
            Nothing
        prints:
            Nothing
    """
    # If no arguments come after the config file, import, data load, and extract file keys will all be set to True
    if len(sys.argv) == 2:
        config_defaults['data_load'] = True
        config_defaults['extract_csv'] = True
        config_defaults['import_object'] = True
    else:
        for command in commands:
            if command == '-d':
                config_defaults['import_object'] = True
            if command == '-l':
                config_defaults['data_load'] = True
            if command == '-x':
                config_defaults['extract_csv'] = True


def set_new_defaults(config_file, config_defaults):
    """
    Function parses config file for keys and values and stores them in new_config_defualts
        args:
            config_file: config file holds keys and values to be used
        returns:
            Nothing
        prints:
            Nothing
    """
    f = open(config_file, "r")
    lines = f.readlines()
    f.close()
    for line in lines:
        for key, value in config_defaults.items():
            if line[0] != '#' or " ":
                str = line
                str_tup = str.split("=", 1)
                if key == str_tup[0]:
                    var = key + '='
                    what_is_after_var = line[len(var):]
                    what_is_after_key = line[len(key):]
                    # if there is nothing after an = of a key in config file, the default value of the key will be assigned
                    # Ex: admin=________ -> admin=Super
                    if len(what_is_after_var) == 1:
                        config_defaults[key] = value.rstrip()
                    # if there is nothing after the name of a key in config file, the default value of the key will be assigned
                    # Ex: admin____ -> admin=Super
                    elif len(what_is_after_key) == 1:
                        config_defaults[key] = value.rstrip()
                    else:
                        if key == 'incorta_home':
                            if what_is_after_var[0].strip() != '/':
                                what_is_after_var = '/' + what_is_after_var.strip()
                        if key == 'wd_path':
                            if what_is_after_var[0].strip() != '/':
                                what_is_after_var = '/' + what_is_after_var.strip()
                        config_defaults[key] = what_is_after_var.rstrip()

    # If a key from config_defaults is missing in the config file, the code below will create the key
    # in config_defaults and will assign that key its default value
    new_key_list = []
    old_key_list = []

    for new_key in config_defaults.keys():
        new_key_list.append(new_key)

    for old_key in config_defaults.keys():
        old_key_list.append(old_key)

    for key in old_key_list:
        if key not in new_key_list:
            config_defaults[key] = config_defaults[key]

    # If a custom working directory path is specified, /IncortaTesting/tmp/work will
    # be added to the end of the custom working directory path
    if config_defaults['wd_path'] == '/IncortaTesting':
        pass
    else:
        timestamp = ''
        config_defaults['wd_path'] += '/IncortaTesting'

        orig_wd_path = config_defaults['wd_path']
        add_time_stamp_to_wd(timestamp, config_defaults)
        return orig_wd_path


def add_time_stamp_to_wd(timestamp, config_defaults):
    """
    Function adds a timestamp to the end of the working directory path
        args:
            timestamp: MM/DD/YY-HR/MIN/SEC
        returns:
            Nothing
        prints:
            Nothing
    """
    date_and_time = str(time.strftime("%m:%d:%Y-%H:%M:%S"))
    config_defaults['wd_path'] += '/%s' % date_and_time


full_config_path = ROOT_DIR + os.sep + config_file

set_block_defaults(commands, config_defaults)
set_new_defaults(full_config_path, config_defaults)
orig_wd_path = set_new_defaults(full_config_path, config_defaults)
# converts keys in a dictionary to variables
globals().update(config_defaults)
"""
------------------------------------------Initialization----------------------------------------
"""
