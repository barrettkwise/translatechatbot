package javacode;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class chatinterface {
    public static void main(String args[]) throws IOException {
        boolean flag = true;
        Scanner input = new Scanner(System.in);
        
        //intro and getting lang
        System.out.println("Welcome to TranslateChat!");
        System.out.println("This application allows for you to practice speaking in any language.");
        
        //setting lang1 and lang2
        String x = "en";
        System.out.println("Choose the language you want to practice: ");
        String y = input.nextLine();
        langsel lang = new langsel (y);
        lang.setLang(y);
        System.out.println("Chosen language: (" + lang.getLang2() + ", " + lang.getLang() + ")");
        y = lang.getLang();
        
        //starting python script
        System.out.println("Starting chatbot...\n");

        //main loop 
        do {
            //starts script
            String path = "E:/school/all programming files/translatechatbot/python/chatbot.exe";
            ProcessBuilder pb = new ProcessBuilder(path);
            //below statement starts script
            Process p = pb.start();
            
            System.out.println("Type 'quit' to exit.\nEnter: ");
            String userString = input.nextLine();
            
            if (userString.contains("quit")) {
                break;
            }
            
            //phrases
            else if (userString.contains(" ")) {
                String [] phrasearray = userString.split(" ");
                ArrayList <String> translatedphrase = new ArrayList <String> ();
                
                //translating phrase
                for (String i : phrasearray) {
                    chattranslate translate = new chattranslate(x, y, i);
                    String temp = translate.translate(y, x, i);
                    translatedphrase.add(temp);
                }

                //sending phrase
                sendphrase phrase = new sendphrase(translatedphrase);
                phrase.setPhrase(translatedphrase);
                
                //reading response
                String response = phrase.getResult();
                chattranslate translate = new chattranslate(x, y, response);
                response = translate.translate(x, y, response);
                System.out.println("Computer response: " + response);
            }
            
            //words
            else {
                //translating and sending word
                chattranslate translate = new chattranslate(y, x, userString);
                String translatedtext = translate.translate (y, x, userString);
                sendword word = new sendword(translatedtext);
                word.setWord(translatedtext);
                
                //reading response
                String response = word.getResult();
                response = translate.translate(x, y, response);
                System.out.println("Computer response: " + response);
            }

        } while (flag);
        
    }
}