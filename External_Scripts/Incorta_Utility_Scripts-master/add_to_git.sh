#!/bin/bash

# This script run after the files are processed:
# - The XML files should be formatted
# - If necessary, the unnecessary data should be removed for adding into the source 
# Take the tenant_name, source directory as inputs
# Copy the files from the source directory into git directory 
# Add the files into git repository
# git will compare the files and only add the files that are different!
# This is one of the channel to add source files into source

TENANT_NAME=TEST
SOURCE_DIR=/Users/Nadim_Incorta/TEST_incorta/incorta_src
echo "copy files to tenant source directory"
cp -u -v -R ${SOURCE_DIR}/${TENANT_NAME}.formatted/* ${SOURCE_DIR}/${TENANT_NAME} > /dev/stdout

cd ${SOURCE_DIR}/${TENANT_NAME}
git add *
git status


