#!/bin/bash

CLASSPATH='lib/*:build/'
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

while getopts a:c:t: option
do
	case "${option}"
		in
		c) CASESTUDIES=${OPTARG};;
		t) TRIALS=${OPTARG};;
		a) APPROACHES=${OPTARG};;
	esac
done

if [ -z $CASESTUDIES ] || [ -z $TRIALS ] || [ -z $APPROACHES ] ; then
	echo "Experiment failed - requires -c CASESTUDIES -t TRIALS -a APPROACHES"
	exit 1
fi

IFS=':' read -ra CASESTUDY <<< "$CASESTUDIES"
IFS=':' read -ra APPROACH <<< "$APPROACHES"

ARGUMENTS='-mutationPipeline=Mutation2013'

for c in "${CASESTUDY[@]}"; do
    for a in "${APPROACH[@]}"; do
    	$DIR'/'experiment.sh -c 'parsedcasestudy.'$c -a 'org.schemaanalyst.mutation.analysis.technique.'$a -t $TRIALS -p 'Mutation2013'
    done
done
