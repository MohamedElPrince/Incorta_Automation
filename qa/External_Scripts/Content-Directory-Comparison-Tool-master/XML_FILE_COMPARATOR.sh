#!/bin/bash
# ************************************************************************
# By : Anahit Sarao
# Last Update: Teusday August 2, 2016
#
# (c) Copyright Incorta 2016. All rights reserved.
# ************************************************************************
# Bash Script to scan through changes of two XML files
# ************************************************************************
usage() { echo "Usage: $0 [-o <old_file>] [-f <new_file>] [-w <working_directory>]" 1>&2; exit 1; }

while getopts :n:o:w: FLAG; do
    case "${FLAG}" in
        n)
            s=${OPTARG}
            ;;
        o)
            p=${OPTARG}
            ;;
        w)
			w=${OPTARG}
			;;
        *)
            usage
            ;;
    esac
done
shift $((OPTIND-1))

SOURCE1=${s}
SOURCE2=${p}

#EXPORT_DIR is desired output directory of this script
EXPORT_DIR=${w}

hash python &> /dev/null
if [ $? -eq 1 ]; then
    echo >&2 "python not found."
fi

#Checks for installed programs on machine
function programInstalled {
  local returning=1
  type $1 >/dev/null 2>&1 || { local returning=0; }
  echo "$returning"
}

#Fucntion Displays Green Check for installed programs
#Else Displays Red Cross for Missing Programs
function check {
	if [ $1 == 1 ]; then
    	printf "\e[32m✔"
  		echo $(tput sgr0)
	else
    	printf "\e[31m✘"
  		echo $(tput sgr0)
  	fi
}

echo "Path for SOURCE1" $FILE_SRC1
echo "Path for SOURCE2" $FILE_SRC2


#Checks for temp directory, if not founds makes directory
if [ ! -d "${EXPORT_DIR}/tracked_changes" ]; then
	mkdir -p ${EXPORT_DIR}/tracked_changes
	mkdir -p ${EXPORT_DIR}/tracked_changes/schema_tracked_changes
fi

TEMP_DIR=${EXPORT_DIR}/tracked_changes
TEMP_DIR_SCHEMA=${EXPORT_DIR}/schema_tracked_changes

CHOICE=0
while [  "$CHOICE" -ne "2" ]; do
	echo " "
	echo -n "1-Compare XMLs "
	echo -n "2-Exit "
	read CHOICE

	if [[ "$CHOICE" -eq "1" ]]; then
# Retrieves the file names within the specificed source directories
		if [[ "$CHOICE" -eq "1" ]]; then

			echo "Enter name of first file you would like to compare"
			read schema_raw_1

			echo "Enter name of second file you would like to compare"
			read schema_raw_2

			XML_FILE1=${FILE_SRC1}/${schema_raw_1}
			XML_FILE2=${FILE_SRC2}/${schema_raw_2}

				if [[ ! -f "${XML_FILE1}" || ! -f "${XML_FILE2}" ]]; then
					echo "Files do not exist"
				else 
				# Locates the targeted files
				echo "Finding Location of Files"
				echo $XML_FILE1
				echo $XML_FILE2
				# Takes the canonical form of the xml file and formats it
				xmllint --c14n ${XML_FILE1} > ${TEMP_DIR}/FILE_SRC1_conc.xml
				xmllint --format ${TEMP_DIR}/FILE_SRC1_conc.xml > ${TEMP_DIR}/FILE_SRC1_formatted.xml
				xmllint --c14n ${XML_FILE2} > ${TEMP_DIR}/FILE_SRC2_conc.xml
				xmllint --format ${TEMP_DIR}/FILE_SRC2_conc.xml > ${TEMP_DIR}/FILE_SRC2_formatted.xml
				# Full Sizes the terminal window to allow for an easier visual comparison
				echo "Tracking Changes"
				resize -s 0 0
				printf '\e[3;0;0t'
				vimdiff -c ":i" -c ":colorscheme elflord" -c ":winc =" ${TEMP_DIR}/FILE_SRC2_formatted.xml ${TEMP_DIR}/FILE_SRC1_formatted.xml
				# Removes temporary files created to format the original targeted files			
				rm ${TEMP_DIR}/FILE_SRC1_conc.xml
				rm ${TEMP_DIR}/FILE_SRC1_formatted.xml
				rm ${TEMP_DIR}/FILE_SRC2_conc.xml
				rm ${TEMP_DIR}/FILE_SRC2_formatted.xml
				echo "Done"
				fi
		fi
	fi

done #End of WHILE loop
rm -rf ${TEMP_DIR}
echo "Finished... "
