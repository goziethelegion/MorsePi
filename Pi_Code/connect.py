#!/usr/bin/python

import mysql.connector

def connect2db():
    #Open database connection
    conn = mysql.connector.connect(
                         user="MorseAdmin",
                         passwd="MorseAdmin",
                         host="165.227.44.224",
                         port=3306,
                         db="Morse"
                         )
    return conn
def connectionclose(connection) :
    connection.close()


def msgdb (connection, msg, user,user2) :
    cursor = connection.cursor()
    #print(msg)
    query = "INSERT INTO conversation (`message`, `user_one`,`user_two`) VALUES (\'" +msg+ "\', \'"+ str(user)+"\', \'"+ str(user2)+"\')"
    #print(query)
    cursor.execute(query)
    connection.commit()