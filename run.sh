#!/bin/sh

# This should really be converted to a rolling file appending using Log4j

nohup java -cp uber-amazon-interface-0.0.1-SNAPSHOT.jar net.grosinger.bookmetasearch.amazoninterface.HttpListener > amazon-interface.log &