# Log Parser
## Operating System Supported

Built for **Debian** based systems, **Mac OSX**

## Change Log

### Update 10.25.2017
* Release
* Sample Input: -f /path/to/log/file/Incorta.2017-09-24.log -s jira-incremental
* Run Command: python incortalog.py -f ./Incorta.2017-09-22.log -s jira-incremental
    * -f, --file, Logfile to be Parsed. Sample Input: /home/incorta/incorta.2017.log'
    * -s, --schema, Schema name. Sample Input: jira-incremental
* Works on Incorta tenant logs
    * Outputs CSV of Successful loads