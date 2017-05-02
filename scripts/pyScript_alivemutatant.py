#
# Python script to run SchemaAnalyst
#
import os
import subprocess
import random
import shutil
import glob
import csv

cmd1 = './gradlew compile'
cmd2 = 'export CLASSPATH="build/classes/main:lib/*:build/lib/*:."'
subprocess.check_output(cmd1, shell=True)
subprocess.check_output(cmd2, shell=True)
engines = ["SQLite", "Postgrs"]
databases = ["BankAccount", "BrowserCookies", "ArtistTerm", "ArtistSimilarity", "MozillaPermissions", "Mxm", "Northwind", "SongTrackMetadata"]
coverages = ["APC", "ICC", "AICC", "CondAICC", "ClauseAICC", "UCC", "AUCC", "NCC", "ANCC"]
#generators = ["avsDefaults", "avsDefaultsSelector", "random", "randomSelector", "selector"] # "avsDefaultsLangModelRandom"
generators = ["avsDefaults","avsDefaultsLangModelRandom", "avs", "avsLangModelRandom", "random"]
#print "runs,schema,generator,criterion,time,Test requirements covered,coverage,Num Evaluations (test cases only),Num Evaluations (all)"
for eng in en
for data in databases:
    for gen in generators:
        for cov in coverages:
            #iterations = 5
            for seed in range(1, 11):
                try:
                    # print CMD
                    cmdStringMutation = "java org.schemaanalyst.util.Go -s parsedcasestudy."+ data +" --dbms SQLite --criterion " + cov + " --generator " + gen + " mutation --pipeline AllOperatorsNoFKANormalisedWithClassifiers --technique=mutantTiming --seed " + str(seed)
                    cmdStringGeneration = "java org.schemaanalyst.util.Go -s parsedcasestudy."+ data +" --dbms SQLite --criterion " + cov + " --generator " + gen + " generation --seed=" + str(seed)
                    print cmdStringMutation
                    # Run process
                    subprocess.check_output(cmdStringMutation,shell=True,)

                    
                    if (os.path.isfile('/home/abdullah/workspace/schemaanalyst-main/results/mutanttiming.dat')):
                        # Define command and arguments
                        command = 'Rscript '
                        path2script = '/home/abdullah/workspace/schemaanalyst-main/scripts/find_alive.R'

                        # Build subprocess command
                        cmd = command + path2script
                        subprocess.check_output(cmd, shell=True)
                        # Add generator and Critira at the end of the columns of the file
                        with open('/home/abdullah/workspace/schemaanalyst-main/results/mutanttiming.dat','r') as csvinput:
                            with open('/home/abdullah/workspace/schemaanalyst-main/results/mutanttiming-output.dat', 'w') as csvoutput:
                                writer = csv.writer(csvoutput, lineterminator='\n')
                                reader = csv.reader(csvinput)

                                all = []
                                row = next(reader)
                                row.append('generator')
                                row.append('criterion')
                                all.append(row)

                                for row in reader:
                                    row.append(gen)
                                    row.append(cov)
                                    all.append(row)

                                writer.writerows(all)

                        # reading mutanttiming
                        shutil.copy2('/home/abdullah/workspace/schemaanalyst-main/results/mutanttiming-alive.dat', '/home/abdullah/workspace/schemaanalyst-main/results/alive_mutant/mutant-alive-' + data + '-' + gen + '-' + cov + '-SQLite.dat')
                        shutil.copy2('/home/abdullah/workspace/schemaanalyst-main/results/mutanttiming-output.dat', '/home/abdullah/workspace/schemaanalyst-main/results/alive_mutant/mutant-results-' + data + '-' + gen + '-' + cov + '-SQLite.dat')
                        #remove files
                        subprocess.check_output("rm /home/abdullah/workspace/schemaanalyst-main/results/mutanttiming.dat", shell=True)
                        subprocess.check_output("rm /home/abdullah/workspace/schemaanalyst-main/results/mutanttiming-alive.dat", shell=True)
                        subprocess.check_output("rm /home/abdullah/workspace/schemaanalyst-main/results/mutanttiming-output.dat", shell=True)
                        
                except subprocess.CalledProcessError as e:
                    print e.output





import re

def rename(dir, pattern, titlePattern):
    for pathAndFilename in glob.iglob(os.path.join(dir, pattern)):
        title, ext = os.path.splitext(os.path.basename(pathAndFilename))
        print title
        title2 = re.sub('-SQLite', '', title)
        print title2
        os.rename(pathAndFilename, 
                  os.path.join(dir, titlePattern % title2 + ext))


dir = r'/home/abdullah/workspace/schemaanalyst-main/results/alive_mutant'
pattern = r'*.dat'
titlePattern = r'%s-SQLite'

rename(dir, pattern, titlePattern)
def runit(dir, pattern):
    for pathAndFilename in glob.iglob(os.path.join(dir, pattern)):
        title, ext = os.path.splitext(os.path.basename(pathAndFilename))
        if title.startswith('mutant-alive') and ext == '.dat':
            print pathAndFilename
            cmd = 'java org.schemaanalyst.mutation.analysis.util.MutantFromDatReporter '+ pathAndFilename +' AllOperatorsNoFKANormalisedWithClassifiers'
            print cmd
            # Build subprocess command
            subprocess.check_output(cmd, shell=True)


        #os.rename(pathAndFilename, os.path.join(dir, titlePattern % title + ext))


dir = r'/home/abdullah/workspace/schemaanalyst-main/results/alive_mutant'
pattern = r'*.dat'
#titlePatern = r'%s-SQLite'

runit(dir, pattern)

# Combain alive mutant Dat files into one file
dir = r'/home/abdullah/workspace/schemaanalyst-main/results/alive_mutant'
pattern = r'*.dat'
interesting_files = glob.iglob(os.path.join(dir, pattern)) 

header_saved = False
with open('/home/abdullah/workspace/schemaanalyst-main/results/output.dat','wb') as fout:
    for filename in interesting_files:
        title, ext = os.path.splitext(os.path.basename(filename))
        if title.startswith('mutant-results') and ext == '.dat':
            print filename
            with open(filename) as fin:
                header = next(fin)
                if not header_saved:
                    fout.write(header)
                    header_saved = True
                for line in fin:
                    fout.write(line)

def rename(dir, pattern, titlePattern):
    for pathAndFilename in glob.iglob(os.path.join(dir, pattern)):
        title, ext = os.path.splitext(os.path.basename(pathAndFilename))
        os.rename(pathAndFilename, 
                  os.path.join(dir, titlePattern % title + ext))


dir = r'/home/abdullah/workspace/schemaanalyst-main/results/alive_mutant'
pattern = r'*.dat'
titlePatern = r'%s-SQLite'

rename(dir, pattern, titlePatern)

