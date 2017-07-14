#!/bin/bash

# Arguments (: separated)
# -s SCHEMAS
# -r REPEATS
#	-d DBMSS

CLASSPATH='build/classes/main:lib/*:build/lib/*:.'
CLASS='org.schemaanalyst.util.Go'
TECHNIQUE='mutantTiming'

while getopts s:r:d: option
do
  case "${option}" in
    s) SCHEMAS=${OPTARG};;
    r) REPEATS=${OPTARG};;
    d) DBMSS=${OPTARG};;
  esac
done

if [ -z $SCHEMAS ] || [ -z $REPEATS ] || [ -z $DBMSS ]; then
  echo "Experiment failed - requires -s SCHEMAS -r REPEATS -d DBMSS"
  exit 1
fi

IFS=':' read -ra SCHEMA <<< "$SCHEMAS"
IFS=':' read -ra DBMS <<< "$DBMSS"

for d in "${DBMS[@]}"; do
  if [ -f config/database.properties.local ]; then
    scripts/updateproperties.sh -f config/database.properties.local -k dbms -v $d
  else
    scripts/updateproperties.sh -f config/database.properties -k dbms -v $d
  fi

  for (( x=1; x<=$REPEATS; x++ )) do
    for s in "${SCHEMA[@]}"; do
      echo "$d,$x,$s"
      java -cp $CLASSPATH $CLASS --schema parsedcasestudy.$s --dbms $d mutation --technique=$TECHNIQUE
    done
  done
done
