#!/bin/bash

CLASSPATH='lib/*:build/'

while getopts c:d: option
do
	case "${option}"
		in
			c) CASESTUDIES=${OPTARG};;
			d) DBMSS=${OPTARG};;
	esac
done

if [ -z $CASESTUDIES ] || [ -z $DBMSS ] ; then
	echo "Experiment failed - requires -c CASESTUDIES -d DBMSS"
	exit 1
fi

IFS=':' read -ra DBMS <<< "$DBMSS"
IFS=':' read -ra CASESTUDY <<< "$CASESTUDIES"

for d in "${DBMS[@]}"; do
	for c in "${CASESTUDY[@]}"; do
		java -cp $CLASSPATH org.schemaanalyst.mutation.analysis.util.AnalysePipeline parsedcasestudy.$c $d
	done
done
