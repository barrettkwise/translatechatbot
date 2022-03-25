import re
import socket
import requests
import os
import urllib
import openai as ai

def airesponse(word: str) -> str:
    ai.api_key ="api-key"
    start_sequence = "\nAI:"
    restart_sequence = "\nHuman: "
    exprompt = "The following is a conversation with an AI assistant. The assistant is helpful, creative, clever, and very friendly.\n\nHuman: {} "
    exprompt = exprompt.format(word)

    response = ai.Completion.create(
    engine="text-davinci-001",
    prompt=exprompt,
    temperature=0.9,
    max_tokens=150,
    top_p=1,
    frequency_penalty=0,
    presence_penalty=0.6,
    stop=[" Human:", " AI:"]
    )
    response = str(response)

    startresponse = response[111::]
    endresponse = startresponse.find('"')
    result = startresponse[0:endresponse]
    result = result.split(" ")
    del result[0]
    final = " ".join(result)

    return final


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
            response = airesponse(word)
            response = bytes(response, "utf-8")
            connection.send(response)

main()
