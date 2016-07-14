#!/bin/bash
# Bash Script to scan through changes of Directories then  files.

#SOURCE1 must point to one export folder
SOURCE1=/Users/Nadim_Incorta/Desktop/demo_06132016

#SOURCE2 must point to second export folder
SOURCE2=/Users/Nadim_Incorta/Desktop/demo_06132016-new

#EXPORT_DIR is desired output directory of this script
EXPORT_DIR=/Users/Nadim_Incorta/Desktop

echo "Path for SOURCE1" $SOURCE1
echo "Path for SOURCE2" $SOURCE2

#Checks if specified source directories contain required schemas/dashboards
if [[ -d "${SOURCE1}/schemas" && -d "${SOURCE1}/dashboards" && -f "${SOURCE1}/tenant.xml" ]]; then
	echo "Directory for SOURCE1 Contains Needed Files"
else
	echo "Exiting. SOURCE1 Path Missing Files";
	exit 1
fi

if [[ -d "${SOURCE2}/schemas" && -d "${SOURCE2}/dashboards" && -f "${SOURCE2}/tenant.xml" ]]; then
	echo "Directory for SOURCE2 Contains Needed Files"
else
	echo "Exiting. SOURCE2 Path Missing Files";
	exit 1
fi

#Checks for temp directory, if not founds makes directory
if [ ! -d "${EXPORT_DIR}/tracked_changes" ]; then
	mkdir -p ${EXPORT_DIR}/tracked_changes
	mkdir -p ${EXPORT_DIR}/tracked_changes/schema_tracked_changes
fi

TEMP_DIR=${EXPORT_DIR}/tracked_changes
TEMP_DIR_SCHEMA=${EXPORT_DIR}/schema_tracked_changes

CHOICE=0
while [  "$CHOICE" -ne "3" ]; do
	echo " "
	echo -n "1-Dashboard "
	echo -n "2-Schema "
	echo -n "3-Exit "
	read CHOICE

	if [[ "$CHOICE" -eq "1" || "$CHOICE" -eq "2" ]]; then

		#Dashboard Comparison
		if [[ "$CHOICE" -eq "1" ]]; then
			echo -n "Enter Dashboard GUI_ID? > "
			read dash_name
			FILE_SRC1=$(grep -rl "$dash_name" ${SOURCE1}/dashboards)
			FILE_SRC2=$(grep -rl "$dash_name" ${SOURCE2}/dashboards)
			echo "Finding Location of Files"
			echo $FILE_SRC1
			echo $FILE_SRC2

			xmllint --c14n ${FILE_SRC1} > ${TEMP_DIR}/FILE_SRC1_conc.xml
			xmllint --format ${TEMP_DIR}/FILE_SRC1_conc.xml > ${TEMP_DIR}/FILE_SRC1_formatted.xml
			xmllint --c14n ${FILE_SRC2} > ${TEMP_DIR}/FILE_SRC2_conc.xml
			xmllint --format ${TEMP_DIR}/FILE_SRC2_conc.xml > ${TEMP_DIR}/FILE_SRC2_formatted.xml

			echo "Tracking Changes"
			resize -s 0 0
			printf '\e[3;0;0t'
			vimdiff -c ":i" -c ":colorscheme elflord" -c ":winc =" ${TEMP_DIR}/FILE_SRC2_formatted.xml ${TEMP_DIR}/FILE_SRC1_formatted.xml
			rm ${TEMP_DIR}/FILE_SRC1_conc.xml
			rm ${TEMP_DIR}/FILE_SRC1_formatted.xml
			rm ${TEMP_DIR}/FILE_SRC2_conc.xml
			rm ${TEMP_DIR}/FILE_SRC2_formatted.xml
			echo "Done"
		fi

		#Schema Comparison
		if [[ "$CHOICE" -eq "2" ]]; then			
			echo -n "Enter Schema Name? > "
			read schema_raw
			schema_name=(name="\"""${schema_raw}""\"")
			FILE_SRC1_RAW=$(grep -rl "$schema_name" ${SOURCE1}/schemas)
			FILE_SRC2_RAW=$(grep -rl "$schema_name" ${SOURCE2}/schemas)
			FILE_SRC1_ARR=( $FILE_SRC1_RAW )
			FILE_SRC2_ARR=( $FILE_SRC2_RAW )
			FILE_SRC1_LOADER="${FILE_SRC1_ARR[0]}"
			FILE_SRC1_SCHEMA="${FILE_SRC1_ARR[1]}"
			FILE_SRC2_LOADER="${FILE_SRC2_ARR[0]}"
			FILE_SRC2_SCHEMA="${FILE_SRC2_ARR[1]}"
			echo "Finding Location of Files"
			echo $FILE_SRC2_SCHEMA
			echo $FILE_SRC1_SCHEMA
			xmllint --c14n ${FILE_SRC1_SCHEMA} > ${TEMP_DIR}/FILE_SRC1_conc.xml
			xmllint --format ${TEMP_DIR}/FILE_SRC1_conc.xml > ${TEMP_DIR}/FILE_SRC1_formatted.xml
			xmllint --c14n ${FILE_SRC2_SCHEMA} > ${TEMP_DIR}/FILE_SRC2_conc.xml
			xmllint --format ${TEMP_DIR}/FILE_SRC2_conc.xml > ${TEMP_DIR}/FILE_SRC2_formatted.xml

			echo "Tracking Changes"
			resize -s 0 0
			printf '\e[3;0;0t'
			vimdiff -c ":i" -c ":colorscheme elflord" -c ":winc =" ${TEMP_DIR}/FILE_SRC1_formatted.xml ${TEMP_DIR}/FILE_SRC2_formatted.xml
			rm ${TEMP_DIR}/FILE_SRC1_conc.xml
			rm ${TEMP_DIR}/FILE_SRC1_formatted.xml
			rm ${TEMP_DIR}/FILE_SRC2_conc.xml
			rm ${TEMP_DIR}/FILE_SRC2_formatted.xml
		fi
	fi



done #End of WHILE loop
rm -rf ${TEMP_DIR}
echo "Finished... "






