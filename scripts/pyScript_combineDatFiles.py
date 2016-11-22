import glob
import os

dir = r'/home/abdullah/workspace/SchemaAnalyst/results/alive_mutant'
pattern = r'*.dat'
interesting_files = glob.iglob(os.path.join(dir, pattern)) 

header_saved = False
with open('/home/abdullah/workspace/SchemaAnalyst/results/output.dat','wb') as fout:
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
