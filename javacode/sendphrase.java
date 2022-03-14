package javacode; 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class sendphrase {
    private ArrayList <String> phrase = new ArrayList <String> ();
    private String result;

    public sendphrase() {
        this.phrase = null;
        this.result = "";
    }

    public sendphrase(ArrayList <String> phrase) {
        try {
            InetAddress localhost = InetAddress.getByName("localhost"); 
            Socket s = new Socket(localhost, 9000); 
            OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream()); 
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //sending phrase
            for (String word : phrase) {
                out.write(word);
            }
            out.flush();
            
            //getting result
            String result = in.readLine();
            this.result = result;
        
        } catch (Exception e) { 
            System.err.println("Connection Error"); 
            e.printStackTrace(); 
        }  
    }

    public void setPhrase(ArrayList <String> phrase) {
        this.phrase = phrase;
    }
    
    public String getResult() {
        return this.result;
    }

}