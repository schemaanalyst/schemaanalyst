#!/bin/bash

CLASSPATH='lib/*:build/'
DEFAULT_PIPELINE='ICST2013'
DEFAULT_TO_DATABASE='false'

while getopts a:c:t:p:d:s: option
do
	case "${option}"
		in
		c) CASESTUDIES=${OPTARG};;
		t) TRIALS=${OPTARG};;
		a) APPROACHES=${OPTARG};;
		s) THREADPOOLSIZES=${OPTARG};;
		p) PIPELINE=${OPTARG:=$DEFAULT_PIPELINE};;
		d) TODATABASE=${OPTARG:=$DEFAULT_TO_DATABASE};;
	esac
done

if [ -z $CASESTUDIES ] || [ -z $TRIALS ] || [ -z $APPROACHES ] || [ -z $THREADPOOLSIZES ] || [ -z $PIPELINE ] || [ -z $TODATABASE ] ; then
	echo "Experiment failed - requires -c CASESTUDIES -t TRIALS -a APPROACHES -s THREADPOOLSIZES -p PIPELINE* -d TODATABASE* (* use '' to default)"
	exit 1
fi

IFS=':' read -ra THREADPOOLSIZE <<< "$THREADPOOLSIZES"
IFS=':' read -ra CASESTUDY <<< "$CASESTUDIES"
IFS=':' read -ra APPROACH <<< "$APPROACHES"

for c in "${CASESTUDY[@]}"; do
	java -Xmx3G -cp $CLASSPATH org.schemaanalyst.mutation.analysis.util.GenerateResultsFromGenerator parsedcasestudy.$c
    for a in "${APPROACH[@]}"; do
    	for s in "${THREADPOOLSIZE[@]}"; do
    		for (( t=1; t<=$TRIALS; t++ )) do
				java -Xmx3G -cp $CLASSPATH org.schemaanalyst.mutation.analysis.technique.$a parsedcasestudy.$c $t '--threads='$s '--mutationPipeline='$PIPELINE '--resultsToDatabase='$TODATABASE
			done
		done
    done
done
