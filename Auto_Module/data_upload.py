"""
Loads all data on the Incorta Software

TODO
    Add log file dump for failure and success of each import and extract
    Need more try/catch handling
"""
Debug = False  # Debug flag for print statements

def Load_data(incorta , session, names_list):
    """
    Function loads all schemas to Incorta from the list of schemas
        args:
            incorta: Incorta API module
            session: session var returned by login function
            names_list: list of names to upload
        returns:
            Nothing
        prints:
            Can print debug statements if needed
    """
    for names in names_list:
        upload_check = incorta.load_schema(session, names)
    if Debug == True:
        print upload_check, "For:", names
