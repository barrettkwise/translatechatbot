package javacode;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
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
        System.out.println("Starting chatbot...");
        String path = "E:/school/all programming files/translatechatbot/python/chatbot.exe";
        ProcessBuilder pb = new ProcessBuilder(path);
        //below statement starts script
        Process p = pb.start();
        
        //starting java server
        InetAddress localhost = InetAddress.getByName("localhost");
        Socket s = new Socket(localhost, 9000); 
        OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream()); 
        BufferedReader in = new BufferedReader(new 
        InputStreamReader(s.getInputStream()));
        
        //translating user input and sending
        do {
            //need to start new instance of script
            //after first cycle of loop
            System.out.println("Enter: ");
            String phrase = input.nextLine();
            
            //phrases
            if (phrase.contains(" ")) {
                String [] phrasearray = phrase.split(" ");
                ArrayList <String> translatedphrase = new ArrayList <String> ();
                
                //translating phrase
                for (String i : phrasearray) {
                    chattranslate translate = new chattranslate(x, y, i);
                    String temp = translate.translate(y, x, i);
                    translatedphrase.add(temp);
                }
                
                //sending phrase
                for (String j : translatedphrase) {
                    out.write(j + " ");
                }
                out.flush();
                
                //reading reponse
                String response = in.readLine();
                chattranslate translate = new chattranslate(x, y, response);
                response = translate.translate(x, y, response);
                System.out.println("Computer response: " + response);
            }
            
            //words
            else {
                //translating and sending word
                chattranslate translate = new chattranslate(y, x, phrase);
                String translatedtext = translate.translate (y, x, phrase);
                out.write(translatedtext);
                out.flush();
                
                //reading response
                String response = in.readLine();
                response = translate.translate(x, y, response);
                System.out.println("Computer response: " + response);
            }
        } while (flag);
        
        //closing server
        out.close();
        s.close();
        in.close();
        input.close();
        p.destroyForcibly();
    }
}