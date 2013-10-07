#!/bin/bash

CLASSPATH='lib/*:build/'
DEFAULT_PIPELINE='ICST2013'
DEFAULT_TO_DATABASE='false'

while getopts a:c:t:p:d: option
do
	case "${option}"
		in
		c) CASESTUDY=${OPTARG};;
		t) TRIALS=${OPTARG};;
		a) APPROACH=${OPTARG};;
		p) PIPELINE=${OPTARG:=$DEFAULT_PIPELINE};;
		d) TODATABASE=${OPTARG:=$DEFAULT_TO_DATABASE};;
	esac
done

if [ -z $CASESTUDY ] || [ -z $TRIALS ] || [ -z $APPROACH ] || [ -z $PIPELINE ] || [ -z $TODATABASE ] ; then
	echo "Experiment failed - requires -c CASESTUDY -t TRIALS -a APPROACH -p PIPELINE* -d TODATABASE* (* use '' to default)"
	exit 1
fi

# Generate once
java -Xmx3G -cp $CLASSPATH org.schemaanalyst.mutation.analysis.util.GenerateResultsFromGenerator $CASESTUDY

# Test multiple times
for (( t=1; t<=$TRIALS; t++ )) do
		java -Xmx3G -cp $CLASSPATH $APPROACH $CASESTUDY $t '--mutationPipeline='$PIPELINE '--resultsToDatabase='$TODATABASE
done
