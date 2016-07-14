# July 13 2016
# By Ilyas Reyhanoglu
# Full load schemas

def load_schema(incorta,session,schema_names):
	for schema_name in schema_names:
		return_value = incorta.load_schema(session,schema_name)
		print return_value
	
