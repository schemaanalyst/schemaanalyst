#!/bin/bash

CLASSPATH='lib/*:build/'

while getopts c:d:p: option
do
	case "${option}"
		in
			c) CASESTUDIES=${OPTARG};;
			d) DBMSS=${OPTARG};;
			p) PIPELINE=${OPTARG};;
	esac
done

if [ -z $CASESTUDIES ] || [ -z $DBMSS ] || [ -z $PIPELINE ] ; then
	echo "Experiment failed - requires -c CASESTUDIES -d DBMSS -p PIPELINE"
	exit 1
fi

IFS=':' read -ra DBMS <<< "$DBMSS"
IFS=':' read -ra CASESTUDY <<< "$CASESTUDIES"

for d in "${DBMS[@]}"; do
	for c in "${CASESTUDY[@]}"; do
		java -cp $CLASSPATH org.schemaanalyst.mutation.analysis.util.AnalysePipeline parsedcasestudy.$c $d --mutationPipeline=$PIPELINE
	done
done
