#!/bin/bash

# Take the incorta_home, tenant_name, source directory as inputs
# Take the backup and put the backup files into the source directory
# Create subdirectory using the tenant name under the source directory

INCORTA_HOME=/Users/Nadim_Incorta/TEST_incorta
TENANT_NAME=TEST
SOURCE_DIR=/Users/Nadim_Incorta/TEST_incorta/incorta_src

timestamp=`date +%Y%m%d.%H%M%S`

# Create subdirectory if not already exists
if [ ! -d "${SOURCE_DIR}/${TENANT_NAME}" ]; then
  mkdir -p ${SOURCE_DIR}/${TENANT_NAME}

  # Please perform git init manually for each tenant.  This is one time only
  cd ${SOURCE_DIR}/${TENANT_NAME}
  echo "git init"
  git init

fi


# Create the backup directory if not already exists
# This directory will hold all files tenant files
# Ths is NOT under git
# This is not tenant specific.  This directory store data for different tenants
# You can purge files
mkdir -p ${SOURCE_DIR}/tenant_backup
mkdir -p ${SOURCE_DIR}/${TENANT_NAME}.extracted
rm -rf ${SOURCE_DIR}/${TENANT_NAME}.extracted

# Take a new backup
cd $INCORTA_HOME/tmt
./tmt.sh -ex ${TENANT_NAME} ${SOURCE_DIR}/tenant_backup/${TENANT_NAME}_${timestamp}.zip

# Unzip the file into the extract directory 
unzip ${SOURCE_DIR}/tenant_backup/${TENANT_NAME}_${timestamp}.zip -d ${SOURCE_DIR}/${TENANT_NAME}.extracted


# format the XML into the formatted directory
mkdir -p ${SOURCE_DIR}/${TENANT_NAME}.formatted
cd ${SOURCE_DIR}/${TENANT_NAME}.extracted

xmllint -format tenant.xml > ${SOURCE_DIR}/${TENANT_NAME}.formatted/tenant.xml

# format the schema files
mkdir -p ${SOURCE_DIR}/${TENANT_NAME}.formatted/schemas
cd ${SOURCE_DIR}/${TENANT_NAME}.extracted/schemas

for f in *.xml
do
	echo "Formatting file - $f"
        xmllint -format $f > ${SOURCE_DIR}/${TENANT_NAME}.formatted/schemas/$f 
done

 
# format the dashboard files
mkdir -p ${SOURCE_DIR}/${TENANT_NAME}.formatted/dashboards
cd ${SOURCE_DIR}/${TENANT_NAME}.extracted/dashboards

for f in *.xml
do
        echo "Formatting file - $f"
        xmllint -format $f > ${SOURCE_DIR}/${TENANT_NAME}.formatted/dashboards/$f
done

cd ${SOURCE_DIR}

