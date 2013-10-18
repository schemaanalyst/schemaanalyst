#!/bin/bash

CLASSPATH='lib/*:build/'

while getopts t:a:s: option
do
	case "${option}"
		in
		t) TRIALS=${OPTARG};;
		a) APPROACHES=${OPTARG};;
		s) SCALES=${OPTARG};;
	esac
done

if [ -z $TRIALS ] || [ -z $APPROACHES ] || [ -z $SCALES ] ; then
	echo "Experiment failed - requires -t TRIALS -a APPROACHES -s SCALES"
	exit 1
fi

IFS=':' read -ra SCALE <<< "$SCALES"
IFS=':' read -ra APPROACH <<< "$APPROACHES"

ARGUMENTS='-mutationPipeline=Mutation2013'

for (( t=1; t<=$TRIALS; t++ )) do
	for s in "${SCALE[@]}"; do
	    for a in "${APPROACH[@]}"; do
	    	java -cp $CLASSPATH org.schemaanalyst.mutation.analysis.util.DatabaseInteractorTimer $a --scalingFactor=$s
	    done
	done
done
