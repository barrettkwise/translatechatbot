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
        
        //translating user input and sending
        System.out.println("Starting chatbot...");
        InetAddress localhost = InetAddress.getByName("localhost");
        Socket s = new Socket(localhost, 9000);  
        OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream()); 
        BufferedReader in = new BufferedReader(new 
        InputStreamReader(s.getInputStream()));
        String path = "E:/school/all programming files//translatechatbot/python/chatbot.exe";
        ProcessBuilder pb = new ProcessBuilder(path);
        do {
            pb.start();  
            System.out.println("Enter: ");
            String phrase = input.nextLine();
            //allows user to quit
            if (phrase.equalsIgnoreCase("quit") || phrase.equalsIgnoreCase("bye")) {
                s.close();
                flag = false;
            }
            
            //phrases
            else if (phrase.contains(" ")) {
                String [] phrasearray = phrase.split(" ");
                ArrayList <String> translatedphrase = new ArrayList <String> ();
                for (String i : phrasearray) {
                    chattranslate translate = new chattranslate(x, y, i);
                    String temp = translate.translate(y, x, i);
                    translatedphrase.add(temp);
                }
                
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
        input.close();
    }
}