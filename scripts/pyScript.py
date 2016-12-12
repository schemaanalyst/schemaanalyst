#
# Python script to run SchemaAnalyst
#
#import os
import subprocess
import time

cmd1 = './gradlew compile'
cmd2 = 'export CLASSPATH="build/classes/main:lib/*:build/lib/*:."'
subprocess.check_output(cmd1, shell=True)
subprocess.check_output(cmd2, shell=True)

# "AdmissionsPatient", "MozillaPlaces"
#databases = ["BankAccount", "BrowserCookies", "ArtistTerm", "ArtistSimilarity", "MozillaPermissions", "Mxm", "Northwind", "SongTrackMetadata"]
databases = ["BankAccount", "BrowserCookies", "ArtistTerm", "ArtistSimilarity", "MozillaPermissions", "Mxm", "Northwind", "SongTrackMetadata"]
#databases = ["Northwind", "SongTrackMetadata"]
coverages = ["ClauseAICC", "CondAICC", "AICC", "APC", "ICC", "AUCC", "UCC", "ANCC", "NCC"]
generators = ["directedRandom", "avs", "random", "selector"]
#iterations = 5

print "runs,schema,generator,criterion,time,Test requirements covered,coverage,Num Evaluations (test cases only),Num Evaluations (all)"

for data in databases:
    for gen in generators:
        for cov in coverages:
            #for _ in range(iterations):
            #print "================================================================================================================================================"
            #print "Runs == " + str(_) + " Schema == " + data + "Generator == " + gen + " Coverage == " + cov
            #start = time.time()
            result = subprocess.check_output("java org.schemaanalyst.util.Go -s parsedcasestudy."+ data +" --criterion " + cov + " --generator " + gen + " generation",shell=True,)
            #end = time.time()
            #print str(_) + "," + data + "," + gen + "," + cov + "," + str(end - start) + "," +result
            #print os.system("java org.schemaanalyst.util.Go -s parsedcasestudy."+ data +" --criterion " + cov + " --generator " + gen + " generation")
