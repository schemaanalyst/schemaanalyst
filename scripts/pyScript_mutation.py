#
# Python script to run SchemaAnalyst
#
#import os
import subprocess
import random

#cmd1 = './gradlew compile'
#cmd2 = 'export CLASSPATH="build/classes/main:lib/*:build/lib/*:."'
#subprocess.check_output(cmd1, shell=True)
#subprocess.check_output(cmd2, shell=True)

# "AdmissionsPatient", "MozillaPlaces"
databases = ["ArtistTerm", "ArtistSimilarity", "BankAccount", "BrowserCookies", "MozillaPermissions", "Mxm", "Northwind", "SongTrackMetadata"]
#databases = ["BankAccounts", "ArtistTerm"]
#databases = ["Northwind", "SongTrackMetadata"]
coverages = ["APC", "ICC", "AICC", "CondAICC", "ClauseAICC", "UCC", "AUCC", "NCC", "ANCC"]
generators = ["directedRandom", "avs", "random", "selector"]
#generators = ["selector"]
techniques = ["original", "fullSchemata", "minimalSchemata", "minimalSchemata2", "upFrontSchemata", "justInTimeSchemata", "parallelMinimalSchemata", "partialParallelMinimalSchemata", "minimalMinimalSchemata", "minimalMinimalSchemata2", "mutantTiming", "checks", "dummy"]
iterations = 5

#print "runs,schema,generator,criterion,time,Test requirements covered,coverage,Num Evaluations (test cases only),Num Evaluations (all)"

for data in databases:
    for gen in generators:
        for cov in coverages:
            for _ in range(iterations):
                #print
                #print "Runs == " + str(_) + " Schema == " + data + "Generator == " + gen + " Coverage == " + cov
                print "java org.schemaanalyst.util.Go -s parsedcasestudy."+ data +" --dbms SQLite --criterion " + cov + " --generator " + gen + " mutation --seed " + str(random.randint(0, 100000))
                result = subprocess.check_output("java org.schemaanalyst.util.Go -s parsedcasestudy."+ data +" --dbms SQLite --criterion " + cov + " --generator " + gen + " mutation --seed " + str(random.randint(0, 100000)),shell=True,)
                print result
                #print os.system("java org.schemaanalyst.util.Go -s parsedcasestudy."+ data +" --criterion " + cov + " --generator " + gen + " generation")
