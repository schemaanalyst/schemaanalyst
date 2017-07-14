#!/bin/bash

CLASSPATH='build/lib/*:build/classes/main/'
VIRTCLASS='org.schemaanalyst.mutation.analysis.executor.MutationAnalysisVirtualWithTiming'
DATAGENERATOR='avsDefaults'
PIPELINE='AllOperatorsNoFKANormalisedWithClassifiers'

while getopts s:c:r:b: option
do
  case "${option}"
    in
      s) SCHEMAS=${OPTARG};;
      c) CRITERION=${OPTARG};;
      r) REPEATS=${OPTARG};;
      b) BEGINNING=${OPTARG};;
  esac
done

if [ -z $SCHEMAS ] || [ -z $CRITERION ] || [ -z $REPEATS ] ; then
  echo "Experiment failed - requires -s SCHEMAS -c CRITERION -r REPEATS -b BEGINNING_SEED"
  exit 1
fi

IFS=':' read -ra CRITERIA <<< "$CRITERION"
IFS=':' read -ra SCHEMA <<< "$SCHEMAS"

for (( x=1; x<=$REPEATS; x++ )) do
  SEED=$(($BEGINNING+$x-1))
  for s in "${SCHEMA[@]}"; do
    for c in "${CRITERIA[@]}"; do
      echo "$x,virtual,$s,$c,false"
      java -cp $CLASSPATH $VIRTCLASS parsedcasestudy.$s --criterion=$c --randomseed=$SEED --dataGenerator=$DATAGENERATOR --mutationPipeline=$PIPELINE
    done
  done
done

