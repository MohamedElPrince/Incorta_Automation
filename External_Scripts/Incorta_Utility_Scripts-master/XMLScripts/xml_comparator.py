from xml.etree import ElementTree
from lxml import etree
import os, ConfigParser
from xml.etree.ElementTree import Element
from xml.etree.ElementTree import SubElement
import xml.dom.minidom

def ConfigSectionMap(section):
	dict1 = {}
	options = config.options(section)
	for option in options:
		try:
			dict1[option] = config.get(section, option)
			if dict1[option] == -1:
				DebugPrint("skip: %s" % option)
		except:
			print("exception on %s!" % option)
			dict1[option] = None
	return dict1

config = ConfigParser.ConfigParser()
config.readfp(open('config.cfg'))
old_path = ConfigSectionMap("SectionOne")['old_path']
new_path = ConfigSectionMap("SectionOne")['new_path']

"""
etree implementation of reading and outputting xml rile
By: Anahit Sarao

"""
document_old = etree.parse(old_path)
document_new = etree.parse(new_path)
pretty_xml_file_old = etree.tostring(document_old, pretty_print = True)
pretty_xml_file_new = etree.tostring(document_new, pretty_print = True)

"""
dom.minidom implementation of reading and outputtinging xml file
By: Anahit Sarao

"""
# xml_old = xml.dom.minidom.parse(old_path)
# xml_new = xml.dom.minidom.parse(new_path)
# pretty_xml_file_old = xml_old.toprettyxml()
# pretty_xml_file_new = xml_new.toprettyxml()

"""
Outputs new XML in pretty print
"""
output_file_old = open('output_old.xml','w')
output_file_old.write(pretty_xml_file_old)
output_file_old.close()
output_file_new = open('output_new.xml','w')
output_file_new.write(pretty_xml_file_new)
output_file_new.close()
