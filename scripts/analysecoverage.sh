#!/bin/bash

CLASSPATH='lib/*:build/'

while getopts s:c: option
do
	case "${option}"
		in
			s) SCHEMAS=${OPTARG};;
			c) CRITERION=${OPTARG};;
	esac
done

if [ -z $SCHEMAS ] || [ -z $CRITERION ] ; then
	echo "Experiment failed - requires -s SCHEMAS -c CRITERION"
	exit 1
fi

IFS=':' read -ra CRITERIA <<< "$CRITERION"
IFS=':' read -ra SCHEMA <<< "$SCHEMAS"
IFS=':' read -ra REUSE <<< "true:false"

for s in "${SCHEMA[@]}"; do
	echo $s
	for c in "${CRITERIA[@]}"; do
		for r in "${REUSE[@]}"; do
			echo $r
			java -cp $CLASSPATH org.schemaanalyst.coverage.CoverageTester parsedcasestudy.$s $c --reuse=$r
		done
	done
done
