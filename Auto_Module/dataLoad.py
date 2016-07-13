import runIncortaTests
import os, sys

def incorta_import(incorta_home):
	incorta_module = incorta_home.rstrip() + os.sep + "bin".rstrip()
	sys.path.append(incorta_module)
	import incorta
	global incorta

def load_schema(session,schema_name):
	return_value = incorta.load_schema(session,schema_name)
	print return_value
	

incorta_import('/Users/ilyasreyhanoglu/IncortaAnalytics')

session= incorta.login(runIncortaTests.url,runIncortaTests.tenant,runIncortaTests.admin,runIncortaTests.password)
schema_names=['Sales','HR','Sales2','A_06_HIERARCHY']

for schema_name in schema_names:
	load_schema(session,schema_name)