#!/usr/bin/python    
from pyjavaproperties import Properties
p = Properties()
p.load(open('config/database.properties'))

print p['dbms']


p['dbms'] = 'Postgres'
p.store(open('config/database.properties','w'))
