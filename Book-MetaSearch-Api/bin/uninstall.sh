#!/bin/bash

echo "Deleting log directory"
rm -rf /var/log/book-metasearch

echo "Deleting pid directory"
rm -rf /var/run/book-metasearch

echo "Deleting api install directory"
rm -rf /opt/book-metasearch/api

if [ ! "$(ls -A /opt/book-metasearch)" ]; then
    echo "Book-metasearch install directory is empty, deleting"
    rm -rf /opt/book-metasearch
fi

echo "Uninstallation complete"
