#!/bin/bash

# Arguments (: separated)
# 	-s SCHEMAS
# 	-c CRITERION
# 	-t TECHNIQUES
# 	-r REPEATS
# 	-u USETRANSACTIONS
# 	-v VIRTUAL
# 	-p PIPELINES
#	-d DATAGENS

CLASSPATH='lib/*:build/'
CLASS='org.schemaanalyst.mutation.analysis.executor.MutationAnalysis'
VIRTCLASS='org.schemaanalyst.mutation.analysis.executor.MutationAnalysisVirtual'

while getopts s:c:t:r:u:v:p:d:b: option
do
	case "${option}"
		in
			s) SCHEMAS=${OPTARG};;
			c) CRITERION=${OPTARG};;
			t) TECHNIQUES=${OPTARG};;
			r) REPEATS=${OPTARG};;
			u) USETRANSACTIONS=${OPTARG};;
			v) VIRTUAL=${OPTARG};;
			p) PIPELINES=${OPTARG};;
			d) DATAGENS=${OPTARG};;
			b) BEGINNING=${OPTARG};;
	esac
done

if [ -z $SCHEMAS ] || [ -z $CRITERION ] || [ -z $TECHNIQUES ] || [ -z $REPEATS ] || [ -z $USETRANSACTIONS ] || [ -z $PIPELINES ] || [ -z $DATAGENS ] || [ -z $BEGINNING ]; then
	echo "Experiment failed - requires -s SCHEMAS -c CRITERION -t TECHNIQUES -r REPEATS -u USETRANSACTIONS -v VIRTUAL -p PIPELINES -d DATAGENS -b BEGINNING_SEED"
	exit 1
fi

IFS=':' read -ra CRITERIA <<< "$CRITERION"
IFS=':' read -ra SCHEMA <<< "$SCHEMAS"
IFS=':' read -ra TECHNIQUE <<< "$TECHNIQUES"
IFS=':' read -ra USETRANSACTION <<< "$USETRANSACTIONS"
IFS=':' read -ra PIPELINE <<< "$PIPELINES"
IFS=':' read -ra DATAGEN <<< "$DATAGENS"

for (( x=1; x<=$REPEATS; x++ )) do
	SEED=$(($BEGINNING+$x-1))
	for d in "${DATAGEN[@]}"; do
		for s in "${SCHEMA[@]}"; do
			for c in "${CRITERIA[@]}"; do
				for p in "${PIPELINE[@]}"; do
					for t in "${TECHNIQUE[@]}"; do			
						for u in "${USETRANSACTION[@]}"; do
							echo "$d,$x,$t,$s,$c,$u,$SEED"
							java -cp $CLASSPATH $CLASS parsedcasestudy.$s --criterion=$c --technique=$t --useTransactions=$u --mutationPipeline=$p --randomseed=$SEED --dataGenerator=$d
						done
					done
					if [ $VIRTUAL == "true" ] ; then
						echo "$d,$x,virtual,$s,$c,false,$SEED"
						java -cp $CLASSPATH $VIRTCLASS parsedcasestudy.$s --criterion=$c --randomseed=$SEED --dataGenerator=$d
					fi
				done
			done
		done
	done
done
