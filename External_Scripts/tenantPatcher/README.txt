----------------------------------
Configuration File for Zip Utility

Please Read This Before Execution
Known Supported Systems: Mac OS
----------------------------------
Purpose: The purpose of this utility is to be able to restore certain schemas and dashboards onto Incorta from a backup
         without having to restore all the schemas and dashboards.
----------------------------------
Output: Creates a temp directory and puts a zipfile and content.log inside it
        The zipfile contains the desired schemas,dashboards, and/or datasources.
        The content.log tells you which files are included in the zip.
----------------------------------
Dependency:
        Xml.etree.ElementTree, shutil, and zipfile must be imported.
----------------------------------
Instructions To Run Script:
    1. Write desired schemas, dashboards, and datasources in the input.txt in the given format
        The Wildchar search gives you all the files that start with a given name. If you give it just a star, it will grab all
        the files of that type. You can also give a folder name and all the dashboards under that folder will be grabbed.
    2. Run the ziputil script
        a. On the command line you need to enter 3 arguments;
        the path to the input.txt, the path to the tenant backup zip, and the path to the output directory
        b. The following two lines show proper format for entering the arguments:
            -f /path/to/input.txt -o /path/to/Working_Directory -z /path/to/the/tenant_backup.zip
            -i SchemaName,DashboardName,DatasourceName -o /path/to/Working_Directory -z /path/to/the/tenant_backup.zip

        c. Extra Info
            Required Flags: -o /path/to/Working_Directory -z /path/to/the/tenant_backup.zip
            Conditional Flags. Either: -i SchemaName,DashboardName,DatasourceName Or: -f /path/to/input.txt

------------------------------------




