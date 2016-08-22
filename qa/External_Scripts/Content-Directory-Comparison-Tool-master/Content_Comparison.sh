#!/bin/bash
# ************************************************************************
# By : Anahit Sarao
# Last Update: Teusday August 19, 2016
#
# (c) Copyright Incorta 2016. All rights reserved.
# ************************************************************************
# Bash Script to scan through changes of Directories then  files
# ************************************************************************

#SOURCE1 must point to one export folder

#SOURCE2 must point to second export folder

usage() { echo "Usage: $0 [-n <old_tenant>] [-o <new_tenant>] [-w <working_directory>]" 1>&2; exit 1; }

while getopts ":n:o:w:" FLAG; do
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

echo ""
echo $"Path for SOURCE1" $SOURCE1
echo "Path for SOURCE2" $SOURCE2
echo "working Directory" $EXPORT_DIR
echo ""

#mac check for installed
function programInstalled {
  local returning=1
  type $1 >/dev/null 2>&1 || { local returning=0; }
  echo "$returning"
}

#yum check for isntalled
function isinstalled {
	if yum list installed "$@" >/dev/null 2>&1; then
  		local returns=1
	else
		local returns=0
	fi
	echo $returns
}

#yum check for isntalled--no echo print
function isinstallednoecho {
	if yum list installed "$@" >/dev/null 2>&1; then
  		local returns=1
	else
		local returns=0
	fi
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

case $( uname -s ) in
	Linux)	#Linux Case
		pkg="libxml2"
		pfk2="vim"
		echo Operating System: Linux
		echo "libxml2 $(check $(isinstalled $pkg))"
		echo "vim     $(check $(isinstalled $pkg))"
		if isinstallednoecho $pkg; then
			echo "Libxml2 is installed"; 
		else
			echo "No libxml2. Setting up libxml2"
			sudo yum install libxml2
			echo "Success..."
		fi
			
		if isinstallednoecho $pkg2; then
			echo "Vim is installed"; 
		else
			echo "No Vim. Setting up Vim"
			sudo yum install vim
			echo "Success..."
		fi
		;;
	Darwin)	#Mac Case
		echo Operating System: Mac OSX Darvin
		echo "xmllint 		$(check $(programInstalled xmllint))"
		echo "vim     		$(check $(programInstalled vim))"
		xcheck=$(pkgutil --pkgs=.\+xquartz.\+)
		if [[ $xcheck == "org.macosforge.xquartz.pkg" ]]; then
			echo xquartz Already Installed on Machine
		
		elif [[ $xcheck == "" ]]; then
			echo "xmllint Not Installed; Please Install xmllint..."
			sudo pip install lxml
			echo "Success..."
		fi
		;;
	*)	#Default if Operatin System other than Mac or Linux
		echo Other
		exit 1
		;;
esac


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
			echo -n "Enter Dashboard Name? > "
			read dash_name
			FILE_SRC1=$(grep -rl "$dash_name" ${SOURCE1}/dashboards)
			FILE_SRC2=$(grep -rl "$dash_name" ${SOURCE2}/dashboards)
			echo "Finding Location of Files"
			if [[ ! -f "${FILE_SRC1}" || ! -f "${FILE_SRC2}" ]]; then
					echo "Files do not exist"
			else 
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
		fi

		#Schema Comparison
		if [[ "$CHOICE" -eq "2" ]]; then			
			echo -n "Enter Schema Name? > "
			read schema_raw
			schema_name=(name="\"""${schema_raw}""\"")

			FILE_SRC1_RAW=$(grep -rl "$schema_name" ${SOURCE1}/schemas)
			FILE_SRC1_ARR=( $FILE_SRC1_RAW )
			FILE_SRC2_RAW=$(grep -rl "$schema_name" ${SOURCE2}/schemas)
			FILE_SRC2_ARR=( $FILE_SRC2_RAW )

			if [[ ! -f "${FILE_SRC1_RAW}" || ! -f "${FILE_SRC2_RAW}" ]]; then
					echo "Files do not exist"
			else 
				if [[ "${FILE_SRC1_ARR[0]}" == *schema.xml ]]; then
					FILE_SRC1_SCHEMA="${FILE_SRC1_ARR[0]}"
				else
					FILE_SRC1_SCHEMA="${FILE_SRC1_ARR[1]}"
				fi
				
				if [[ "${FILE_SRC2_ARR[0]}" == *schema.xml ]]; then
					FILE_SRC2_SCHEMA="${FILE_SRC2_ARR[0]}"
				else
					FILE_SRC2_SCHEMA="${FILE_SRC2_ARR[1]}"
				fi
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
	fi

done #End of WHILE loop
rm -rf ${TEMP_DIR}
echo "Finished... "






