# SchemaParsingScript

#Portrays all table attributes of a specified schema to a CSV FILE
#Check for Table Name, Type, Incremental, Query, Data Sources, Key, Columns with a Key

Make sure to have file located in the directory of where Incorta is installed, within the bin folder.
Example Path: /Users/User_Name/Incorta_Home/bin



To Run Script: 

python file_name.py serverurl tenant_name username password schemapattern% true

More Information:
You provide the server URL, tenant name, schema pattern, table pattern, column pattern, data type to change and to which value.

The patterns works as normal SQL like statements (e.g. %_CODE will match all items with suffix _code). It applies to schemas, tables and columns. 
You may use %Schema_Name to select all schemas with the given name. 

There's an optional last parameter (value true) to change the matching column to dimension.


After running the script open the csv file generated with numbers or excel to view your results!
