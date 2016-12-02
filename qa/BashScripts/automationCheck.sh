#!/bin/bash

# ************************************************************************
# By : Anahit Sarao
# Last Update: Teusday August 2, 2016
#
# (c) Copyright Incorta 2016. All rights reserved.
# ************************************************************************
# Automation Test Framework Pre Check Bash Script
# ************************************************************************

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

#Case Statement for Different Operating System Types
case $( uname -s ) in
	Linux)	#Linux Case
		echo Operating System: Linux
		echo "python        $(check $(programInstalled python))"
		echo "pip           $(check $(programInstalled pip))"
		problem=$(rpm -qa | grep python-lxml)
		echo Checking for libxml: $problem
		if [ "" == "$problem" ]; then
     		echo "No libxml. Setting up libxml"
  			sudo yum install python-lxml
  			sudo pip install ptable
  			echo "Success..."
		fi
		exit 1
		;;
	Darwin)	#Mac Case
		echo "Operating System: Mac OSX Darvin"
		echo "python        $(check $(programInstalled python))"
		echo "pip           $(check $(programInstalled pip))"
		lxml_check=$(python -c 'import pkgutil; print(1 if pkgutil.find_loader("lxml") else 0)')
		if [[ $lxml_check -eq "1" ]]; then
			echo "Lxml Already Installed on Machine"
		
		elif [[ $lxml_check -eq "0" ]]; then
			echo "Lxml Not Installed; Installing..."
			sudo pip install lxml
			echo "Success..."
		fi
		ptable_check=$(python -c 'import pkgutil; print(1 if pkgutil.find_loader("ptable") else 0)')
		if [[ $ptable_check -eq "1" ]]; then
			echo "Ptable Already Installed on Machine"

		elif [[ $ptable_check -eq "0" ]]; then
			echo "Ptable Not Installed; Installing..."
			pip install ptable
			echo "Success..."
		fi
		exit 1
		;;
	*)	#Default if Operatin System other than Mac or Linux
		echo Other
		exit 1
		;;
esac




