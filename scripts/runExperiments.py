#
# Python script to run SchemaAnalyst
# Please install pyjavaproperties -- pip intall pyjavaproperties
#
import sys, os, string, errno
import subprocess
import random
import shutil
import glob
import csv


############## START: Set-up experiment (Configs) ##################### 
# DB Engines:  SQLite, Postgres, HyperSQL
engines = ["SQLite", "HyperSQL"]


# databases are the case studies :
"""
["AdmissionsPatient", "ArtistSimilarity", "ArtistTerm", "BankAccount", "BioSQL","BookTown","BrowserCookies","ChromeDB","Cloc",
"CoffeeOrders","Crafts2002Repaired","CustomerOrder","DavilaDjango","DellStore","DHDBookstoreRepaired","Employee","Examination","Flav_R03_1Repaired",
"Flights","FrenchTowns","GeoMetaDB","H1EFileFY2007Repaired",,"HydatRepaired","Inventory","Iso3166","IsoFlav_R2Repaired","iTrust","JWhoisServer",
"MozillaExtensions","MozillaPermissions","MozillaPlaces","Mxm","NistDML181","NistDML182","NistDML183","NistDML183Ints","NistDML183IntsNotNulls",
"NistDML183NotNulls","NistDML183Varchars","NistDML183VarcharsNotNulls","NistWeather","NistXTS748","NistXTS749","Northwind","Person","Products",
"ProductSalesRepaired","RiskIt","Skype","SongTrackMetadata","SRAMetadb","StackOverflow","StudentResidence","Test","TweetComplete","UnixUsage",
"Usda","WordNet","World"]
"""
databases = ["ArtistSimilarity"]


# Coverage you want to run:
# ["APC", "ICC", "AICC", "CondAICC", "ClauseAICC", "UCC", "AUCC", "NCC", "ANCC"]
# The best is this combainations = ClauseAICC+AUCC+ANCC
coverages = ["ClauseAICC+AUCC+ANCC"]

# Data generators of your choice:
# ["avs", "avsDefaults", "random", "dominoRandom"]
generators = ["dominoRandom"]

# Number of runs you want
# Default random seed will start at 1 until end_seed
# NOTE: the end_seed will be off by one.
# So, if you require 30 runs please type 31
end_seed = 10

############## END: Set-up experiment (Configs) ##################### 

############## Running experiment ##################### 
# log_file configs, then Compile Code and set CLASSPATH
log_file = open("errors.log", "w")

# Compile SchemaAnalyst
cmd1 = './gradlew compile'
cmd2 = 'export CLASSPATH="build/classes/main:lib/*:build/lib/*:."'
subprocess.check_output(cmd1, shell=True)
subprocess.check_output(cmd2, shell=True)

# Path file config
cwd = os.getcwd()
path_to_results = cwd + "/results/"
path_to_scripts = cwd + "/scripts/"

# Find alive_mutant folder and if not create one
# results/alive_mutant/
try:
    os.makedirs(path_to_results + "/alive_mutant/")
except OSError as e:
    if e.errno != errno.EEXIST:
        raise

# Change DBMS in config while running
def change_dbms_config(db):
  from pyjavaproperties import Properties
  p = Properties()
  p.load(open('config/database.properties'))
  print p['dbms']
  print db
  p['dbms'] = db
  p.store(open('config/database.properties', 'w'))


# Alive mutants scripts runner function
def alive_mutants_script(dir, pattern):
  try:
    for pathAndFilename in glob.iglob(os.path.join(dir, pattern)):
        title, ext = os.path.splitext(os.path.basename(pathAndFilename))
        if title.startswith('mutant-alive') and ext == '.dat':
            print pathAndFilename
            cmd = 'java org.schemaanalyst.mutation.analysis.util.MutantFromDatReporter ' + pathAndFilename + ' AllOperatorsNoFKANormalisedWithClassifiers'
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
    with open(results_path + 'mutanttiming.dat', 'wb') as fout:
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
      path2script = scripts_path + 'find_alive.R '
      target_file = path_to_results + "mutanttiming.dat"

      # Build subprocess command
      cmd = command + path2script + target_file
      run_r_command = subprocess.check_output(cmd, shell=True)
      print(run_r_command)

      # Add generator and Critira at the end of the columns of the file
      with open(resutls_path + 'mutanttiming.dat', 'r') as csvinput:
        with open(resutls_path + 'mutanttiming-output.dat', 'w') as csvoutput:
          writer = csv.writer(csvoutput, lineterminator='\n')
          reader = csv.reader(csvinput)

          all = []
          row = next(reader)
          #print row

          row.append('generator')
          row.append('criterion')
          row.append('randomseed')
          all.append(row)

          #print row
          for row in reader:
              row.append(gen)
              row.append(cov)
              row.append(seed)
              all.append(row)

          #print all

          writer.writerows(all)

      # if "+" in cov:
      #    cov = cov.replace("+", "-")
      # reading mutanttiming
      shutil.copy2(resutls_path + 'mutanttiming-alive.dat', resutls_path + 'alive_mutant/mutant-alive-' + data + '-' + gen + '-' + cov + '-' + seed + '-' + eng + '.dat')
      shutil.copy2(resutls_path + 'mutanttiming-output.dat', resutls_path + 'alive_mutant/mutant-results-' + data + '-' + gen + '-' + cov + '-' + seed + '-' + eng + '.dat')
      # remove files
      subprocess.check_output("rm " + resutls_path + "mutanttiming.dat", shell=True)
      subprocess.check_output("rm " + resutls_path + "mutanttiming-alive.dat", shell=True)
      subprocess.check_output("rm " + resutls_path + "mutanttiming-output.dat", shell=True)
  except subprocess.CalledProcessError as e:
    print("ERROR 1")
    print e.output
    log_file.write("ERROR - alive_mutant_r - %s\n" % e.output)

# Run experiment
for eng in engines:
  if len(engines) > 1:
    change_dbms_config(eng)
  for data in databases:
    for gen in generators:
      for cov in coverages:
        # Set-up random seed
        for seed in range(1, end_seed):
          try:
            # Mutation Command
            cmdStringMutation = "java org.schemaanalyst.util.Go -s parsedcasestudy." + data + " --dbms " + eng + " --criterion " + cov + " --generator " + gen + " mutation --pipeline AllOperatorsNoFKANormalisedWithClassifiers --technique=mutantTiming --seed " + str(seed)
            
            # Generation Command
            cmdStringGeneration = "java org.schemaanalyst.util.Go -s parsedcasestudy." + data + " --dbms " + eng + " --criterion " + cov + " --generator " + gen + " generation --seed=" + str(seed)

            print cmdStringMutation
            # Run process
            run_java_command = subprocess.check_output(cmdStringMutation, shell=True,)
            
            print(run_java_command)

            # Run R alive script
            alive_mutant_r(path_to_scripts, path_to_results, data, gen, cov, eng, str(seed))

          except subprocess.CalledProcessError as e:
            info = "parsedcasestudy." + data + "  dbms " + eng + " criterion " + cov + " generator " + gen + " seed " + str(seed)
            if e.returncode == 127:
              log_file.write("ERROR - program not found - runner INFO - %s \n" % info)
              log_file.write("ERROR - runner - %s \n" % e.output)
            elif e.returncode <= 125:
              log_file.write("ERROR - failed - runner INFO - %s \n" % info)
              log_file.write("ERROR - runner - %s \n" % e.output)
            else:
              # Things get hairy and unportable - different shells return
              # different values for coredumps, signals, etc.
              log_file.write("ERROR - runner - '%s' likely crashed, shell retruned code %d" % (cmd, e.returncode))
          except OSError as e:
            # unlikely, but still possible: the system failed to execute the shell
            # itself (out-of-memory, out-of-file-descriptors, and other extreme cases).
            log_file.write("failed to run shell: '%s'" % (str(e)))

# Functions parameters
dynamicDir = cwd + '/results/alive_mutant'
dir = r'' + dynamicDir
pattern = r'*.dat'
print dir

# Runing Alive Mutants scripts
alive_mutants_script(dir, pattern)

# Combain alive mutant Dat files into one file (output.dat)
mutanttiming_combine(path_to_results, dir, pattern)

log_file.close()
