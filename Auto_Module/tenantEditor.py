import os, platform
"""
"""
def tenant_editor(path):
	"""
	Uses Regex Commands too suppress dynamic lines in tenant files
	This is done to ensure the validator does not throw unnecessary
	diffs
	"""
	# Takes Import or Export Path as parameter
	system = platform.system()
	if 'Darvin' in system:
		for root, dir, files in os.walk(path):
			for file in files:
				if file == 'tenant.xml':
					exportTime_arg = """ sed -i "" 's/\(exportTime=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(exportTime_arg)

					id_arg = """ sed -i "" 's/\( id=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(id_arg)

					owner_id_arg = """ sed -i "" 's/\(owner-id=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(owner_id_arg)

					PATH_arg = """ sed -i "" 's/\(path=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(PATH_arg)

					Href_arg = """ sed -i "" 's/\(href=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(Href_arg)

					Parent_arg = """ sed -i "" 's/\(parent=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(Parent_arg)
	elif 'Linux' in system:
		for root, dir, files in os.walk(path):
			for file in files:
				if file == 'tenant.xml':
					exportTime_arg = """ sed -i 's/\(exportTime=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(exportTime_arg)

					id_arg = """ sed -i 's/\( id=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(id_arg)

					owner_id_arg = """ sed -i 's/\(owner-id=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(owner_id_arg)

					PATH_arg = """ sed -i 's/\(path=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(PATH_arg)

					Href_arg = """ sed -i 's/\(href=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(Href_arg)

					Parent_arg = """ sed -i 's/\(parent=\)"[^"]*"/\\1" "/g' """ + "\"" + os.path.join(root, file) + "\""
					os.system(Parent_arg)
