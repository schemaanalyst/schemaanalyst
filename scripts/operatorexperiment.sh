#!/bin/bash

CLASSPATH='lib/*:build/'

while getopts c:o: option
do
	case "${option}"
		in
		c) CASESTUDIES=${OPTARG};;
		o) OPERATORS=${OPTARG};;
	esac
done

if [ -z $CASESTUDIES ] || [ -z $OPERATORS ] ; then
	echo "Experiment failed - requires -c CASESTUDIES -o OPERATORS"
	exit 1
fi

IFS=':' read -ra CASESTUDY <<< "$CASESTUDIES"
IFS=':' read -ra OPERATOR <<< "$OPERATORS"

for c in "${CASESTUDY[@]}"; do
	for x in "${!OPERATOR[@]}"; do
		for (( y=$x+1; y<${#OPERATOR[@]}; y++ )); do
			java -cp build/:lib/* org.schemaanalyst.mutation.analysis.util.ComparePipelines parsedcasestudy.$c Programmatic:${OPERATOR[$x]} Programmatic:${OPERATOR[$y]}
		done
	done
done
