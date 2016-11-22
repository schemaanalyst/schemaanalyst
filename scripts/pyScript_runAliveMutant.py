import glob, os
import subprocess

cmd1 = './gradlew compile'
cmd2 = 'export CLASSPATH="build/classes/main:lib/*:build/lib/*:."'
subprocess.check_output(cmd1, shell=True)
subprocess.check_output(cmd2, shell=True)

def runit(dir, pattern):
    for pathAndFilename in glob.iglob(os.path.join(dir, pattern)):
        title, ext = os.path.splitext(os.path.basename(pathAndFilename))
        if title.startswith('mutant-alive') and ext == '.dat':
            print pathAndFilename
            cmd = 'java org.schemaanalyst.mutation.analysis.util.MutantFromDatReporter '+ pathAndFilename +' AllOperatorsWithRemovers'
            print cmd
            # Build subprocess command
            subprocess.check_output(cmd, shell=True)


        #os.rename(pathAndFilename, os.path.join(dir, titlePattern % title + ext))


dir = r'/home/abdullah/alive_mutant'
pattern = r'*.dat'
#titlePatern = r'%s-SQLite'

runit(dir, pattern)
