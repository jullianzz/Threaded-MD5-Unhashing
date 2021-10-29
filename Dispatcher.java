import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException;

public class Dispatcher {

    public static void main(String[] args) {
        // Path of input file
        String path = args[0]; 
        // Read the input file
        BufferedReader reader; 
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            String to_unhash; 
            while (line != null) {
                // Invoke unhash
                to_unhash = line.replace("\\n","");
                // System.out.println(to_unhash); 
                System.out.println(UnHash.unhash(to_unhash)); 
                line = reader.readLine(); 
            }
            reader.close(); 
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
