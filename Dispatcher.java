import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException;
import java.util.LinkedList;
import java.util.ArrayList;

public class Dispatcher {

    static void dispatch(String path, int N) {
        // Declare Global wq
        LinkedList<String> wq = new LinkedList<String>();

        // Read the input file and populate wq
        BufferedReader reader; 
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            String to_unhash; 
            while (line != null) {
                to_unhash = line.replace("\\n","");
                // Add to_unhash to Global Work Queue
                wq.add(to_unhash);
                line = reader.readLine(); 
            }
            reader.close(); 
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        // Create the N threads and start run() for each
        ArrayList<CpuThread> threads = new ArrayList<CpuThread>(N); 
        for (int i = 0; i < N; i++) {
            threads.add(new CpuThread(wq,i));
            threads.get(i).start(); 
        }
    }

    public static void main(String[] args) {
        // Path of input file
        String path = args[0]; 
        // Number of CPUs on the machine, N
        int N = Integer.parseInt(args[1]); 
        // Call dispatch method
        Dispatcher.dispatch(path, N); 
        
    }
    
}
