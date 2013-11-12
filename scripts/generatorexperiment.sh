#!/bin/bash

CLASSPATH='lib/*:build/'

while getopts c:s:n: option
do
	case "${option}"
		in
			c) CASESTUDY=${OPTARG};;
			s) SATIFIES=${OPTARG};;
			n) NEGATES=${OPTARG};;
	esac
done

if [ -z $CASESTUDY ] || [ -z $SATIFIES ] || [ -z $NEGATES ] ; then
	echo "Experiment failed - requires -c CASESTUDY -s SATIFIES -n NEGATES"
	exit 1
fi

IFS=',' read -ra NEGATE <<< "$NEGATES"
IFS=',' read -ra SATISFY <<< "$SATIFIES"

for s in "${SATISFY[@]}"; do
	for n in "${NEGATE[@]}"; do
		java -cp $CLASSPATH org.schemaanalyst.mutation.analysis.util.GenerateResultsFromGenerator parsedcasestudy.$CASESTUDY --writeReport --randomseed=0 --negaterows=$n --satisfyrows=$s
	done
done
