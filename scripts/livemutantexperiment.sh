#!/bin/bash

CLASSPATH='lib/*:build/'

while getopts c:p: option
do
	case "${option}"
		in
		c) CASESTUDIES=${OPTARG};;
		p) PIPELINE=${OPTARG};;
	esac
done

if [ -z $CASESTUDIES ] || [ -z $PIPELINE ]; then
	echo "Experiment failed - requires -c CASESTUDIES -p PIPELINE"
	exit 1
fi

IFS=':' read -ra CASESTUDY <<< "$CASESTUDIES"

ARGUMENTS='-mutationPipeline=Mutation2013'

for c in "${CASESTUDY[@]}"; do
	java -cp $CLASSPATH org.schemaanalyst.mutation.analysis.util.GenerateResultsFromGenerator parsedcasestudy.$c
	java -cp $CLASSPATH org.schemaanalyst.mutation.analysis.technique.DebuggingOriginal parsedcasestudy.$c 0 --mutationPipeline=$PIPELINE --outputNotKilled=true
done
