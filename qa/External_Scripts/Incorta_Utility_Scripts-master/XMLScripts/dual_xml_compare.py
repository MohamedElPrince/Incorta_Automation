
#import xml.etree.ElementTree as ET
# tree = ET.parse('2734_dashboard.xml')
# root = tree.getroot()
# print ET.tostring(root, pretty_print=True)
import sys, getopt
from lxml import etree



#to run python script 
#python xml_compare.py -i old_dash.xml -o 2734_dashboard.xml
#python xml_compare.py -i old_file.xml -o new_file.xml

def main(argv):
	inputfile = ''
	outputfile = ''
	try:
	  opts, args = getopt.getopt(argv,"hi:o:",["ifile=","ofile="])
	except getopt.GetoptError:
	  print ('test.py -i <inputfile> -o <outputfile>')
	  sys.exit(2)
	for opt, arg in opts:
		if opt == '-h':
			print ('test.py -i <inputfile> -o <outputfile>')
			sys.exit()
		elif opt in ("-i", "--ifile"):
			inputfile = arg
		elif opt in ("-o", "--ofile"):
			outputfile = arg
	print ('Old file is "', inputfile)
	print ('New file is "', outputfile)


	print ("-----------------------Printing out Old File------------------------")
	print ("--------------------------------------------------------------------")
	old_xml = etree.parse(inputfile)
	old_xml_pretty = etree.tostring(old_xml, pretty_print = True)
	print (old_xml_preatty)
	print ("--------------------------------------------------------------------")
	print ("-----------------------Printing Recent Version----------------------")


	new_xml = etree.parse(outputfile)
	new_xml_pretty = etree.tostring(new_xml, pretty_print = True)
	print (new_xml_pretty)









if __name__ == "__main__":
   main(sys.argv[1:])

