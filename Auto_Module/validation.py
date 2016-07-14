from lxml import etree
import os
import sys



import_path = ('/Users/Nadim_Incorta/Desktop/Import_vs_Export_Validation/schema_to_be_imported/schemas/887_schema.xml')
export_path = ('/Users/Nadim_Incorta/Desktop/Import_vs_Export_Validation/exported_schema/schemas/140_schema.xml')


tree1 = etree.parse(import_path)
tree2 = etree.parse(export_path)
root1 = tree1.getroot()
root2 = tree2.getroot()


tree1_string = etree.tostring(tree1, encoding="unicode", method="xml")
tree2_string = etree.tostring(tree2, encoding="unicode", method="xml")

tree1_string = tree1_string.replace("u'", "")
tree2_string = tree2_string.replace("u'", "")


elem1 = [x for x in tree1_string.split()]
elem2 = [x for x in tree2_string.split()]



# for i in range(len(tree1_string)):
# 	if tree1_string[i] != tree2_string[i]: print i, tree1_string[i], tree2_string[i]


# COUNT = 0
# elem1_item = None
# elem2_item = None
# for item in elem1:
#     if item not in elem2:
#         elem1_item = item
#        	for diff in elem2:
#        		if diff not in elem1:
#        			elem2_item = diff
#        	print (elem1_item,"---",elem2_item)





set1 = set(etree.tostring(i, method='c14n') for i in tree1.getroot())
set2 = set(etree.tostring(i, method='c14n') for i in tree2.getroot())
#sorted(string1.split()) == sorted(string2.split())

#os.system('diff /Users/Nadim_Incorta/Desktop/Import_vs_Export_Validation/exported_schema/schemas/140_schema.xml /Users/Nadim_Incorta/Desktop/Import_vs_Export_Validation/schema_to_be_imported/schemas/887_schema.xml')

if set1 != set2:
	print "Data Corruption"
	f = open('VALID_LOG_FILE.log', 'w')
	log_header = ('Import_File ------------ Export File\n')
	f.write('Tracked Differences...\n')
	f.writelines(log_header)
	A = tree1_string.split()
	B = tree2_string.split()
	C = []
	D = []
	for i in range(len(A)):
		if A[i] != B[i]:
			C.append(A[i])
			D.append(B[i])

	for i in range(len(C)):
		temp1 = C[i].replace("u'", "")
		temp2 = D[i].replace("u'", "")
		temp_string = temp1+'-------'+temp2
		f.write(temp_string)
		f.write('\n')

	f.close()

else:
	print "Data Validated"

















