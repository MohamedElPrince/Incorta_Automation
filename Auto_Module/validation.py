from lxml import etree
from subprocess import call
import os
import sys
import difflib

import_path = ('/Users/Nadim_Incorta/Desktop/Import_vs_Export_Validation/schema_to_be_imported/schemas/887_schema.xml')
export_path = ('/Users/Nadim_Incorta/Desktop/Import_vs_Export_Validation/exported_schema/schemas/140_schema.xml')
#out_file = open('changes.xml', 'w')

tree1 = etree.parse(import_path)
tree2 = etree.parse(export_path)
root1 = tree1.getroot()
root2 = tree2.getroot()

set1 = set(etree.tostring(i, method='c14n') for i in tree1.getroot())
set2 = set(etree.tostring(i, method='c14n') for i in tree2.getroot())
counter = 0


os.system('echo yeees')
os.system('diff /Users/Nadim_Incorta/Desktop/Import_vs_Export_Validation/exported_schema/schemas/140_schema.xml /Users/Nadim_Incorta/Desktop/Import_vs_Export_Validation/schema_to_be_imported/schemas/887_schema.xml >temp.xml')

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



print "True: Files Match  False: Files are different"
print set1 == set2



im_file = str(set1)
ex_file = str(set2)

print "Printing Changes....."
diff = difflib.ndiff(im_file.splitlines(1),ex_file.splitlines(1))
diff = list(diff)
print ''.join(diff)



















