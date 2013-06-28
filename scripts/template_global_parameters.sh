#!/bin/bash

while getopts f:p: option
do
	case "${option}"
		in
		f) PROJECT_FILEPATH=${OPTARG};;
		p) PORT=${OPTARG};;
	esac
done

if [ -z $PROJECT_FILEPATH ] || [ -z $PORT ] ; then
	echo "global parameters failed - requires -f PROJECT_FILEPATH and -p PORT arguments"
	exit 1
fi

global=$(sed '
	s|{PROJECT}|'"$PROJECT_FILEPATH"'/|g
	s|{PORT}|'"$PORT"'|g' config/globalexperimentparameters_template.xml)

echo "$global"