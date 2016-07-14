from lxml import etree
from subprocess import call
import os
import sys
import difflib

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

print "PRINTING DIFFERENCES"

#alist = tree1_string.split()
A = tree1_string.split()
B = tree2_string.split()
C = []
D = []
for i in range(len(A)):
	if A[i] != B[i]:
		C.append(A[i])
		D.append(B[i])

for i in range(len(C)):
	C[i].replace("u'", "")
	D[i].replace("u'", "")
	print (C[i],"~", D[i])


# C = [item for item in B if item not in A]

# print C



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
counter = 0

dfs = list(set2 - set1)

#print dfs

#os.system('diff /Users/Nadim_Incorta/Desktop/Import_vs_Export_Validation/exported_schema/schemas/140_schema.xml /Users/Nadim_Incorta/Desktop/Import_vs_Export_Validation/schema_to_be_imported/schemas/887_schema.xml')

#print "PRINTING ORIGINAL XML"






# print "True: Files Match  False: Files are different"
# print set1 == set2

if set1 != set2:
	print "Data Corruption"
else:
	print "Data Validated"



# for child in root1:
#     if root1[counter] != root2[counter]:
#         diff = set(etree.tostring(root1[counter]))
#         # out_file.write('Difference')
#         #out_file.write(diff)
#         print (diff)
#         out_file.write('----------')
#     counter = counter+1

#out_file.close()

#os.system('diff set1 set2 >differences.xml')

# for elem in tree1.iter():
# 	counter = counter +1

# for x in tree1.iter():

# 	if (tree1[x] != tree2[x]):
# 		print "Differnece noted"
# 		print tree2[x]





# im_file = str(set1)
# ex_file = str(set2)

# print "Printing Changes....."
# diff = difflib.ndiff(im_file.splitlines(1),ex_file.splitlines(1))
# diff = list(diff)
# print ''.join(diff)



















