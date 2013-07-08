#!/bin/bash

while getopts f:p:e: option
do
	case "${option}"
		in
		f) PROJECT_FILEPATH=${OPTARG};;
		p) PORT=${OPTARG};;
		e) EXECUTION_CLASS=${OPTARG};;
	esac
done

if [ -z $PROJECT_FILEPATH ] || [ -z $PORT ] || [ -z $EXECUTION_CLASS ] ; then
	echo "global parameters failed - requires -f PROJECT_FILEPATH, -e EXECUTION_CLASS and -p PORT arguments"
	exit 1
fi

global=$(sed '
	s|{PROJECT}|'"$PROJECT_FILEPATH"'/|g
	s|{PORT}|'"$PORT"'|g
	s|{CLASS}|'"$EXECUTION_CLASS"'|g' config/globalexperimentparameters_template.xml)

echo "$global"