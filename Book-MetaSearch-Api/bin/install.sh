#!/bin/bash

serviceName="book-metasearch-api"
serviceHome="/opt/book-metasearch"

logDir="/var/log/book-metasearch"
pidDir="/var/run/book-metasearch"

if [ ! -d ${serviceHome} ]; then
    mkdir ${serviceHome}
fi;

# Temporarily stage the api-keys.properties file if it exists
if [ -f ${serviceHome}/api/config/api-keys.properties ]; then
    echo "Temporarily storing api-keys file"
    mv ${serviceHome}/api/config/api-keys.properties .
fi;

echo "Creating ${serviceHome} and moving files"
mv Book-MetaSearch-Api ${serviceHome}/api
mv ${serviceHome}/api/bin/${serviceName} /etc/init.d/.
chmod +x /etc/init.d/${serviceName}
chmod +x ${serviceHome}/api/bin/uninstall.sh

# Replace the api-keys.properties file if it was staged
if [ -f api-keys.properties ]; then
    echo "Replacing API keys file"
    mv api-keys.properties ${serviceHome}/api/config/api-keys.properties
fi;

if [ ! -d ${logDir} ]; then
    echo "Creating ${logDir}"
    mkdir ${logDir}
fi;

if [ ! -d ${pidDir} ]; then
    echo "Creating ${pidDir}"
    mkdir ${pidDir}
fi;
