#!/usr/bin/python

import MySQLdb

# Open database connection
db = MySQLdb.connect("ifedavid_partscrib","ifedavid","Rapperholikah1997","gator4235.hostgator.com" )

# prepare a cursor object using cursor() method
cursor = db.cursor()

# Drop table if it already exist using execute() method.
cursor.execute("DROP TABLE IF EXISTS EMPLOYEE")

# disconnect from server
db.close()