#!/bin/bash

# Arguments (: separated)
# 	-s SCHEMAS
# 	-r REPEATS
#	-d DBMSS
# 	-p PIPELINES

CLASSPATH='lib/*:build/'
CLASS='org.schemaanalyst.mutation.analysis.util.AnalysePipeline'

while getopts s:r:d:p: option
do
	case "${option}"
		in
			s) SCHEMAS=${OPTARG};;
			r) REPEATS=${OPTARG};;
			d) DBMSS=${OPTARG};;
			p) PIPELINES=${OPTARG};;
	esac
done

if [ -z $SCHEMAS ] || [ -z $REPEATS ] || [ -z $DBMSS ] || [ -z $PIPELINES ]; then
	echo "Experiment failed - requires -s SCHEMAS -r REPEATS -d DBMSS -p PIPELINES"
	exit 1
fi

IFS=':' read -ra SCHEMA <<< "$SCHEMAS"
IFS=':' read -ra DBMS <<< "$DBMSS"
IFS=':' read -ra PIPELINE <<< "$PIPELINES"

for p in "${PIPELINE[@]}"; do
	for d in "${DBMS[@]}"; do
		if [ -f config/database.properties.local ]; then
			scripts/updateproperties.sh -f config/database.properties.local -k dbms -v $d
		else
			scripts/updateproperties.sh -f config/database.properties -k dbms -v $d
		fi

		for (( x=1; x<=$REPEATS; x++ )) do
			for s in "${SCHEMA[@]}"; do
					echo "$d,$x,$s"
					java -cp $CLASSPATH $CLASS parsedcasestudy.$s $d --mutationPipeline=$p
			done
		done
	done
done
