from imp import C_EXTENSION
import socket
import requests
import urllib
import cleverbot

def cleverbot(word):
    cb = cleverbot.Cleverbot('YOUR_API_KEY', timeout=60)
    try:
        result = cb.say(word)
    except cleverbot.APIError as error:
        print(error.error, error.status)
    except cleverbot.CleverbotError as error:
        print(error)
    finally:
        cb.close()
    
    return result

def main():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind(("localhost", 9000))
        s.listen(10) 
        connection, address = s.accept()
        with connection:
           print("Connected to: ", address)
           word = connection.recv(1024)
           print ("Word(s) received: ", word)
           word = str(word)
           word = word.replace('b', '', 1)
           word.rstrip()
           result = cleverbot(word)
           connection.send(result)
           connection.close()

main()
