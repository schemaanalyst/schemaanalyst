#!/bin/bash

CLASSPATH='lib/*:build/'
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
DEFAULT_TO_DATABASE='false'

while getopts p:a:c:t:d: option
do
	case "${option}"
		in
		p) PIPELINES=${OPTARG};;
		c) CASESTUDIES=${OPTARG};;
		t) TRIALS=${OPTARG};;
		a) APPROACHES=${OPTARG};;
		d) TODATABASE=${OPTARG:=$DEFAULT_TO_DATABASE};;
	esac
done

if [ -z $CASESTUDIES ] || [ -z $TRIALS ] || [ -z $APPROACHES ] || [ -z $TODATABASE ] || [ -z $PIPELINES ] ; then
	echo "Experiment failed - requires -c CASESTUDIES -t TRIALS -a APPROACHES -d TODATABASE* -p PIPELINES (* use '' to default)"
	exit 1
fi

IFS=':' read -ra CASESTUDY <<< "$CASESTUDIES"
IFS=':' read -ra APPROACH <<< "$APPROACHES"
IFS='-' read -ra PIPELINE <<< "$PIPELINES"

ARGUMENTS='-mutationPipeline=Mutation2013'

for p in "${PIPELINE[@]}"; do
	for c in "${CASESTUDY[@]}"; do
	    for a in "${APPROACH[@]}"; do
	    	# Generate once
			java -Xmx3G -cp $CLASSPATH org.schemaanalyst.mutation.analysis.util.GenerateResultsFromGenerator 'parsedcasestudy.'$c

			# Test multiple times
			for (( t=1; t<=$TRIALS; t++ )) do
					java -Xmx3G -cp $CLASSPATH 'org.schemaanalyst.mutation.analysis.technique.'$a 'parsedcasestudy.'$c $t '--mutationPipeline='$p '--resultsToDatabase='$TODATABASE '--resultsToOneFile'
			done
	    done
	done
done
