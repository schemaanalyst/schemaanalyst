#!/bin/bash

CLASSPATH='lib/*:build/'
APPROACH='org.schemaanalyst.mutation.analysis.technique.Original'

function append() {
	unamestr=`uname`
	if [[ "$unamestr" == 'Linux' ]]; then
	   sed -i '$s|$|'$1'|' $2
	elif [[ "$unamestr" == 'Darwin' ]]; then
	   sed -i '' '$s|$|'$1'|' $2
	fi
}

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
	for c in "${CRITERIA[@]}"; do
		for r in "${REUSE[@]}"; do
			java -Xmx3G -cp $CLASSPATH org.schemaanalyst.mutation.analysis.util.GenerateResultsFromCoverageGenerator parsedcasestudy.$s $c --reuse=$r
			java -Xmx3G -cp $CLASSPATH $APPROACH parsedcasestudy.$s 1 '--mutationPipeline=AllOperatorsWithRemovers' '--resultsToOneFile'
			append ,$c,$r results/mutationanalysis.dat
		done
	done
done
