import glob, os

def rename(dir, pattern, titlePattern):
    for pathAndFilename in glob.iglob(os.path.join(dir, pattern)):
        title, ext = os.path.splitext(os.path.basename(pathAndFilename))
        os.rename(pathAndFilename, 
                  os.path.join(dir, titlePattern % title + ext))


dir = r'/home/abdullah/alive_mutant'
pattern = r'*.dat'
titlePatern = r'%s-SQLite'

rename(dir, pattern, titlePatern)
