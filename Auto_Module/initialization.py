import runIncortaTests, sys, os, time

config_file = runIncortaTests.config_file
config_defaults = runIncortaTests.config_defaults

def set_block_defaults(commands):
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




def set_new_defaults(config_file):
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
        runIncortaTests.add_time_stamp_to_wd(timestamp)
        return orig_wd_path