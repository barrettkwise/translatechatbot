from googletrans import Translator, constants
from pprint import pprint
import anvil.server
import openai as ai
import webbrowser

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
    ai.api_key ="open-ai-key"
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
