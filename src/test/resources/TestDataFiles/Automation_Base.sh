#!/usr/bin/env bash

# ************************************************************************
#  Licensed Material - Property of Incorta.
#
#  (c) Copyright Incorta 2014, 2015. All rights reserved.
# ************************************************************************
# Automation Base Script for Incorta Bash API [Created by Mohab Mohie]
# ************************************************************************

# get current directory and set path to python script
cur_dir=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
incorta_cmd="python $cur_dir/incorta.py"

# login to incorta and save session for future use
session=`$incorta_cmd login $1`

# Stop if login failed
if [ -z "$session" ]; then
        exit 1
fi

# mange passed variables
declare commandParams
set -f
for thing in "$@"
do
if [[ "$thing" != "$1" ]] && [[ "$thing" != "$2" ]]; then
        commandParams="$commandParams "$thing""
fi
done

# perform command
$incorta_cmd "$2" $session $commandParams

set +f

# logut out and end session
$incorta_cmd logout $session
