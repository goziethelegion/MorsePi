#!/usr/bin/python

import MySQLdb

def connect2db():
    #Open database connection
    conn = MySQLdb.connect(
                         user="MorseAdmin",
                         passwd="MorseAdmin",
                         host="165.227.44.224",
                         port=3306,
                         db="Morse"
                         )

def connectionclose(connection) :
    connection.close()


def msgdb (connection, msg, user) :
    cursor = connection.cursor()

    cursor.execute("""INSERT INTO conversation (`message`, `user_one`) VALUES(" + msg +", " + user +") """)
    connection.commit()
