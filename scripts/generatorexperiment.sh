#!/bin/bash

CLASSPATH='lib/*:build/'

while getopts c:s:n: option
do
	case "${option}"
		in
			c) CASESTUDIES=${OPTARG};;
			s) SATIFIES=${OPTARG};;
			n) NEGATES=${OPTARG};;
	esac
done

if [ -z $CASESTUDIES ] || [ -z $SATIFIES ] || [ -z $NEGATES ] ; then
	echo "Experiment failed - requires -c CASESTUDIES -s SATIFIES -n NEGATES"
	exit 1
fi

IFS=',' read -ra NEGATE <<< "$NEGATES"
IFS=',' read -ra SATISFY <<< "$SATIFIES"
IFS=':' read -ra CASESTUDY <<< "$CASESTUDIES"

for c in "${CASESTUDY[@]}"; do
	for s in "${SATISFY[@]}"; do
		for n in "${NEGATE[@]}"; do
			java -cp $CLASSPATH org.schemaanalyst.mutation.analysis.util.GenerateResultsFromGenerator parsedcasestudy.$c --writeReport --randomseed=0 --negaterows=$n --satisfyrows=$s
		done
	done
done
