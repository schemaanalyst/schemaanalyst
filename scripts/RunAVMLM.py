#
# Python script to run SchemaAnalyst
#
import sys, os, string
import subprocess
import random
import shutil
import glob
import csv

# log_file configs, then Compile Code and set CLASSPATH
log_file = open("errors.log", "w")
cmd1 = './gradlew compile'
cmd2 = 'export CLASSPATH="build/classes/main:lib/*:build/lib/*:."'
subprocess.check_output(cmd1, shell=True)
subprocess.check_output(cmd2, shell=True)

# Path file config
path_to_results = "/home/abdullah/workspace/schemaanalyst-main/results/"
path_to_scripts = "/home/abdullah/workspace/schemaanalyst-main/scripts/"

# Set-up experiment 
engines = ["SQLite", "Postgres"]
databases = ["BankAccount", "BrowserCookies", "ArtistTerm", "ArtistSimilarity", "MozillaPermissions", "Mxm", "Northwind", "SongTrackMetadata"]
coverages = ["APC", "ICC", "AICC", "CondAICC", "ClauseAICC", "UCC", "AUCC", "NCC", "ANCC"]
generators = ["avsDefaults","avsDefaultsLangModelRandom", "avs", "avsLangModelRandom", "random"]
end_seed = 31

# Testing

#engines = ["SQLite", "Postgres"]
#databases = ["BankAccount"]
#coverages = ["APC"]
#generators = ["avsDefaults"]
#end_seed = 2

#generators = ["avsDefaults", "avsDefaultsSelector", "random", "randomSelector", "selector"] # "avsDefaultsLangModelRandom"
#print "runs,schema,generator,criterion,time,Test requirements covered,coverage,Num Evaluations (test cases only),Num Evaluations (all)"

# Change DBMS in config while running
def change_dbms_config(db):
  from pyjavaproperties import Properties
  p = Properties()
  p.load(open('config/database.properties'))
  print p['dbms']
  print db
  p['dbms'] = db
  p.store(open('config/database.properties','w'))


# Alive mutants srcript runner function
def alive_mutants_script(dir, pattern):
  try:
    for pathAndFilename in glob.iglob(os.path.join(dir, pattern)):
        title, ext = os.path.splitext(os.path.basename(pathAndFilename))
        if title.startswith('mutant-alive') and ext == '.dat':
            print pathAndFilename
            cmd = 'java org.schemaanalyst.mutation.analysis.util.MutantFromDatReporter '+ pathAndFilename +' AllOperatorsNoFKANormalisedWithClassifiers'
            # Build subprocess command
            run_java_command_alive_mutants = subprocess.check_output(cmd, shell=True)
            print(run_java_command_alive_mutants)
  except Exception as e:
    print("ERROR 3")
    print e.output
    log_file.write("ERROR - alive_mutants_script - %s\n" % e.output)

# Combine split mutanttiming files into one
def mutanttiming_combine(results_path, dir, pattern):
  try:
    interesting_files = glob.iglob(os.path.join(dir, pattern)) 
    header_saved = False
    with open(results_path + 'output.dat','wb') as fout:
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
  except Exception as e:
    print("ERROR 2")
    print e.output
    log_file.write("ERROR - mutanttiming_combine - %s\n" % e.output)

# Function that take a file generated for each run and then run the R alive mutant scripts
def alive_mutant_r(scripts_path, resutls_path, data, gen, cov, eng, seed):
  try:
    # Split Alive mutants files
    if (os.path.isfile(resutls_path + 'mutanttiming.dat')):
      # Define command and arguments
      command = 'Rscript '
      path2script = scripts_path+'find_alive.R'

      # Build subprocess command
      cmd = command + path2script
      run_r_command = subprocess.check_output(cmd, shell=True)
      print(run_r_command)
      # Add generator and Critira at the end of the columns of the file
      with open(resutls_path + 'mutanttiming.dat','r') as csvinput:
        with open(resutls_path + 'mutanttiming-output.dat', 'w') as csvoutput:
          writer = csv.writer(csvoutput, lineterminator='\n')
          reader = csv.reader(csvinput)

          all = []
          row = next(reader)
          row.append('generator')
          row.append('criterion')
          row.append('randomseed')
          all.append(row)

          for row in reader:
              row.append(gen)
              row.append(cov)
              row.append(seed)
              all.append(row)

          writer.writerows(all)

      # reading mutanttiming
      shutil.copy2(resutls_path + 'mutanttiming-alive.dat', resutls_path +  'alive_mutant/mutant-alive-' + data + '-' + gen + '-' + cov + '-'+ eng +'.dat')
      shutil.copy2(resutls_path + 'mutanttiming-output.dat', resutls_path + 'alive_mutant/mutant-results-' + data + '-' + gen + '-' + cov + '-'+ eng +'.dat')
      #remove files
      subprocess.check_output("rm "+ resutls_path +"mutanttiming.dat", shell=True)
      subprocess.check_output("rm "+ resutls_path +"mutanttiming-alive.dat", shell=True)
      subprocess.check_output("rm "+ resutls_path +"mutanttiming-output.dat", shell=True)
  except subprocess.CalledProcessError as e:
    print("ERROR 1")
    print e.output
    log_file.write("ERROR - alive_mutant_r - %s\n" % e.output)

# Run experiment
for eng in engines:
  change_dbms_config(eng)
  for data in databases:
    for gen in generators:
      for cov in coverages:
        # Set-up random seed
        for seed in range(1, end_seed):
          try:
            # print CMD
            # Mutation Command
            cmdStringMutation = "java org.schemaanalyst.util.Go -s parsedcasestudy."+ data +" --dbms " + eng + " --criterion " + cov + " --generator " + gen + " mutation --pipeline AllOperatorsNoFKANormalisedWithClassifiers --technique=mutantTiming --seed " + str(seed)
            
            # Generation Command
            cmdStringGeneration = "java org.schemaanalyst.util.Go -s parsedcasestudy."+ data +" --dbms " + eng + " --criterion " + cov + " --generator " + gen + " generation --seed=" + str(seed)

            print cmdStringMutation
            # Run process
            run_java_command = subprocess.check_output(cmdStringMutation,shell=True,)
            
            print(run_java_command)

            # Run R alive script
            alive_mutant_r(path_to_scripts, path_to_results, data, gen, cov, eng, str(seed))

          except subprocess.CalledProcessError as e:
            info = "parsedcasestudy."+ data +"  dbms " + eng + " criterion " + cov + " generator " + gen + " seed " + str(seed)
            if e.returncode==127:
              log_file.write("ERROR - program not found - runner INFO - %s \n" % info)
              log_file.write("ERROR - runner - %s \n" % e.output)
            elif e.returncode<=125:
              log_file.write("ERROR - failed - runner INFO - %s \n" % info)
              log_file.write("ERROR - runner - %s \n" % e.output)
            else:
              # Things get hairy and unportable - different shells return
              # different values for coredumps, signals, etc.
              log_file.write("ERROR - runner - '%s' likely crashed, shell retruned code %d" % (cmd,e.returncode))
          except OSError as e:
            # unlikely, but still possible: the system failed to execute the shell
            # itself (out-of-memory, out-of-file-descriptors, and other extreme cases).
            log_file.write("failed to run shell: '%s'" % (str(e)))

# Functions parameters
dir = r'/home/abdullah/workspace/schemaanalyst-main/results/alive_mutant'
pattern = r'*.dat'

# Runing Alive Mutants scripts
alive_mutants_script(dir, pattern)

# Combain alive mutant Dat files into one file (output.dat)
mutanttiming_combine(path_to_results, dir, pattern)

log_file.close()
