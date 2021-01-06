#!/bin/bash
./gradlew compile
export CLASSPATH="build/classes/java/main:lib/*:build/lib/*:."


function output {
  echo "$1 ($(date))" >> log
}

function captureOn {
  ./scripts/updateproperties.sh -f config/capture.properties -k enabledCapturers -v removedmutants,classifiedmutants
}

function captureOff {
  ./scripts/updateproperties.sh -f config/capture.properties -k enabledCapturers
}

function loggingOn {
  ./scripts/updateproperties.sh -f config/logging.properties -k java.util.logging.ConsoleHandler.level -v FINE
  ./scripts/updateproperties.sh -f config/logging.properties -k org.schemaanalyst.mutation.redundancy.EquivalentMutantDetector.level -v FINE
  ./scripts/updateproperties.sh -f config/logging.properties -k org.schemaanalyst.mutation.redundancy.RedundantMutantDetector.level -v FINE
  ./scripts/updateproperties.sh -f config/logging.properties -k org.schemaanalyst.mutation.quasimutant.StaticDBMSDetector.level -v FINE
}

function loggingOff {
  ./scripts/updateproperties.sh -f config/logging.properties -k java.util.logging.ConsoleHandler.level -v WARNING
  ./scripts/updateproperties.sh -f config/logging.properties -k org.schemaanalyst.mutation.redundancy.EquivalentMutantDetector.level -v WARNING
  ./scripts/updateproperties.sh -f config/logging.properties -k org.schemaanalyst.mutation.redundancy.RedundantMutantDetector.level -v WARNING
  ./scripts/updateproperties.sh -f config/logging.properties -k org.schemaanalyst.mutation.quasimutant.StaticDBMSDetector.level -v WARNING
}

function dbms {
  ./scripts/updateproperties.sh -f config/database.properties -k dbms -v $1
}

schemasTest="ArtistSimilarity:ArtistTerm:BrowserCookies"
#schemasAll="ArtistSimilarity:ArtistTerm:BankAccount:BookTown:BrowserCookies:Cloc:CoffeeOrders:CustomerOrder:DellStore:Employee:Examination:Flights:FrenchTowns:Inventory:Iso3166:IsoFlav_R2Repaired:iTrust:JWhoisServer:MozillaExtensions:MozillaPermissions:NistDML181:NistDML182:NistDML183:NistWeather:NistXTS748:NistXTS749:Person:Products:RiskIt:StackOverflow:StudentResidence:UnixUsage:Usda:WordNet"
#schemasiTrust="iTrust"
#schemasRest="ArtistSimilarity:ArtistTerm:BankAccount:BookTown:BrowserCookies:Cloc:CoffeeOrders:CustomerOrder:DellStore:Employee:Examination:Flights:FrenchTowns:Inventory:Iso3166:IsoFlav_R2Repaired:JWhoisServer:MozillaExtensions:MozillaPermissions:NistDML181:NistDML182:NistDML183:NistWeather:NistXTS748:NistXTS749:Person:Products:RiskIt:StackOverflow:StudentResidence:UnixUsage:Usda:WordNet"
#schemaNoneFullAVS="BrowserCookies:Flights"
#schemaNoneFullAll="BookTown:Products"

testingSchemas="ArtistSimilarity:ArtistTerm:BankAccount:BrowserCookies:Cloc:CoffeeOrders:CustomerOrder:DellStore:Employee:Examination:Flights:FrenchTowns:Inventory"
schemasRest="Iso3166:IsoFlav_R2Repaired:JWhoisServer:MozillaExtensions:MozillaPermissions:NistDML181:NistDML182:NistDML183:NistWeather:NistXTS748:NistXTS749:Person:Products:RiskIt:StackOverflow:StudentResidence:UnixUsage:Usda:WordNet:BookTown"
schemasiTrust="iTrust"
requiredSchemas="ArtistSimilarity:ArtistTerm:BankAccount:BrowserCookies:Cloc:CoffeeOrders:CustomerOrder:DellStore:Employee:Examination:Flights:FrenchTowns:Inventory:Iso3166:IsoFlav_R2Repaired:JWhoisServer:MozillaExtensions:MozillaPermissions:NistDML181:NistDML182:NistDML183:NistWeather:NistXTS748:NistXTS749:Person:Products:RiskIt:StackOverflow:StudentResidence:UnixUsage:Usda:WordNet:BookTown:iTrust"

pipelineClassifiers="AllOperatorsNoFKANormalisedWithClassifiers"
pipelineRemoversDBMS="AllOperatorsNoFKANormalisedWithRemoversDBMSRemovers"
pipelineRemoversDBMSTransacted="AllOperatorsNoFKANormalisedWithRemoversTransactedDBMSRemovers"
criterion="ClauseAICC+AUCC+ANCC"

function analyse {
  # 1: repeats, 2: pipeline, 3: dbms, 4: schemas, 5: output file
  ./run-analysepipeline.sh -r $1 -p $2 -d $3 -s $4 > $5 2>&1
  mv results/capture.dat $5-capture.dat
}

function mutation {
  # 1: repeats, 2: start seed, 3: schemas, 4: datagens, 5: description, 6: dbms
  dbms $6
  ./run-options-random.sh -r $1 -b $2 -s $3 -c $criterion -t mutantTiming -v false -u true -p $pipelineClassifiers -d $4
  mv results/mutanttiming.dat results/$1-$6-$4-$5-mutanttiming.dat
  #mv results/mutationanalysis.dat results/$1-$6-$4-$5-mutationanalysis.dat
}

function mutationfullreduce {
  # 1: repeats, 2: start seed, 3: schemas, 4: datagens, 5: description, 6: dbms, 7: reductionTechnique
  dbms $6
  echo "$1,$2,$3,$4,$5,$5,$6,$7"
  ./run-options-random-fullreduce.sh -r $1 -b $2 -s $3 -c $criterion -t mutantTiming -v false -u true -p $pipelineClassifiers -d $4 -y $7
  mv results/mutanttiming.dat results/$1-$6-$4-$5-$7-fullreduce-mutanttiming.dat
  #mv results/mutationanalysis.dat results/$1-$6-$4-$5-$7-fullreduce-mutationanalysis.dat
}

output "Starting"
############################ This only for testing
output "testingSchemas"
output "DOMINO-RANDOM"
output "30x dominoRandom requiredSchemas SQLite fullreduce additionalGreedy"
mutationfullreduce 30 1 $requiredSchemas dominoRandom requiredSchemas SQLite sticcerD

#output "30x dominoRandom testingSchemas Schemas SQLite"
#mutation 30 1 $schemasTest dominoRandom testingSchemas SQLite

#output "30x dominoRandom testingSchemas SQLite fullreduce additionalGreedy"
#mutationfullreduce 30 1 $schemasTest dominoRandom testingSchemas SQLite additionalGreedy

#output "30x dominoRandom testingSchemas SQLite fullreduce random"
#mutationfullreduce 30 1 $schemasTest dominoRandom testingSchemas SQLite random

#output "30x dominoRandom testingSchemas SQLite fullreduce HGS"
#mutationfullreduce 30 1 $schemasTest dominoRandom testingSchemas SQLite HGS

#output "30x dominoRandom testingSchemas SQLite fullreduce sticcer"
#mutationfullreduce 30 1 $schemasTest dominoRandom testingSchemas SQLite sticcer

output "testingSchemas"
output "AVM-D"
output "30x avsDefaults requiredSchemas SQLite fullreduce additionalGreedy"
mutationfullreduce 30 1 $requiredSchemas avsDefaults requiredSchemas SQLite sticcerD

#output "30x avsDefaults testingSchemas Schemas SQLite"
#mutation 30 1 $schemasTest avsDefaults testingSchemas SQLite

#output "30x avsDefaults testingSchemas SQLite fullreduce additionalGreedy"
#mutationfullreduce 30 1 $schemasTest avsDefaults testingSchemas SQLite additionalGreedy

#output "30x avsDefaults testingSchemas SQLite fullreduce random"
#mutationfullreduce 30 1 $schemasTest avsDefaults testingSchemas SQLite random

#output "30x avsDefaults testingSchemas SQLite fullreduce HGS"
#mutationfullreduce 30 1 $schemasTest avsDefaults testingSchemas SQLite HGS

#output "30x avsDefaults testingSchemas SQLite fullreduce sticcer"
#mutationfullreduce 30 1 $schemasTest avsDefaults testingSchemas SQLite sticcer
############################ End for testing

