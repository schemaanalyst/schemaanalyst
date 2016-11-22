import os
import subprocess
import random
import shutil
import glob

generators = ["directedRandom", "avs", "random", "selector"]


dir = r'/home/abdullah/workspace/SchemaAnalyst/results/alive_mutant'
pattern = r'*.dat'



for data in generators:
    header_saved = False
    with open('/home/abdullah/workspace/SchemaAnalyst/results/output/'+ data +'.dat','wb') as fout:
        interesting_files = glob.iglob(os.path.join(dir, pattern)) 
        for filename in interesting_files:
            title, ext = os.path.splitext(os.path.basename(filename))
            if title.startswith('mutant-results') and data in title and ext == '.dat':
                print filename
                with open(filename) as fin:
                    header = next(fin)
                    if not header_saved:
                        fout.write(header)
                        header_saved = True
                    for line in fin:
                        fout.write(line)


import csv

for data in generators:
    with open('/home/abdullah/workspace/SchemaAnalyst/results/output/'+ data +'.dat','r') as csvinput:
        with open('/home/abdullah/workspace/SchemaAnalyst/results/output/'+ data +'-output.dat', 'w') as csvoutput:
            writer = csv.writer(csvoutput, lineterminator='\n')
            reader = csv.reader(csvinput)

            all = []
            row = next(reader)
            row.append('generator')
            all.append(row)

            for row in reader:
                row.append(data)
                all.append(row)

            writer.writerows(all)


dir = r'/home/abdullah/workspace/SchemaAnalyst/results/output'
pattern = r'*.dat'
interesting_files = glob.iglob(os.path.join(dir, pattern)) 

header_saved = False
with open('/home/abdullah/workspace/SchemaAnalyst/results/output/output.dat','wb') as fout:
    for filename in interesting_files:
        title, ext = os.path.splitext(os.path.basename(filename))
        if "-output" in title and ext == '.dat':
            print filename
            with open(filename) as fin:
                header = next(fin)
                if not header_saved:
                    fout.write(header)
                    header_saved = True
                for line in fin:
                    fout.write(line)


