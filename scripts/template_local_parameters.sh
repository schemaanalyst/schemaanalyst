#!/bin/bash

while getopts c:d:t: option
do
	case "${option}"
		in
		c) CASESTUDY=${OPTARG};;
		d) DBMS=${OPTARG};;
		t) TRIALS=${OPTARG};;
	esac
done

if [ -z $CASESTUDY ] || [ -z $DBMS ] || [ -z $TRIALS ] ; then
	echo "local parameters failed - requires -c CASESTUDY, -d DBMS and -t TRIALS arguments"
	exit 1
fi

local=$(sed '
	s|{CASESTUDY}|'"$CASESTUDY"'|g
	s|{DBMS}|'"$DBMS"'|g
	s|{TRIALS}|'"$TRIALS"'|g' config/localexperimentparameters_template.xml)

echo "$local"