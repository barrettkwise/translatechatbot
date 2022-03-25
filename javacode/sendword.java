import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

//this program sends the word
//to program script
public class sendword {
    private String word;
    private String result;

    public sendword() {
        this.word = "";
        this.result = "";
    }

    public sendword(String word) {
        try {
            InetAddress localhost = InetAddress.getByName("localhost"); 
            Socket s = new Socket(localhost, 9000); 
            OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream()); 
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); 
            //sending word 
            out.write(word); 
            out.flush(); 

            //getting result
            String result = in.readLine();
            this.result = result;
        
        } catch (Exception e) { 
            System.err.println("Connection Error"); 
            e.printStackTrace(); 
        }  
    }

    public void setWord(String word) {
        this.word = word;
    }
    
    public String getResult() {
        return this.result;
    }

}

