from googletrans import Translator, constants
from pprint import pprint
import anvil.server
import openai as ai
import webbrowser
import json

anvil.server.connect("EU6FPDGEFY4PYHMVHICGR5OU-FZNFM3JBU7LMK4DR")

##translates human
@anvil.server.callable
def intranslate(text: str) -> str:
    translator = Translator()
    detector = translator.detect(text)
    translation = translator.translate(text, dest = "en")
    
    return translation.text, detector.lang

##translates bot 
@anvil.server.callable
def outtranslate(text: str, lang: str) -> str:
    translator = Translator()
    translation = translator.translate(text, src = "en", dest = lang)

    return translation.text

@anvil.server.callable
def airesponse(word: str) -> str:
    word = word.replace("\n", "")
    ai.api_key = "api-key"
    start_sequence = "\nAssistant:"
    restart_sequence = "\nHuman:"
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
    response = json.loads(response)
    response = response.get("choices")
    response = get_value(response, "text")
    response = response.replace("AI:", "")
    response = response.replace("Assistant:", "")
    return response

def get_value(listOfDicts, key):
    for subVal in listOfDicts:
        if key in subVal:
            return subVal[key]
