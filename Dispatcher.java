import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class Dispatcher {

    static void dispatch(String path, int N, long timeout) {
        // Declare Global wq
        LinkedList<String> wq = new LinkedList<String>();

        // Read the input file of path and populate wq
        BufferedReader reader; 
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            String to_unhash; 
            while (line != null) {
                to_unhash = line.replace("\\n","");
                wq.add(to_unhash);
                line = reader.readLine(); 
            }
            reader.close(); 
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        // Create the N threads and start run() for each so it remains idle
        ArrayList<CpuThread> threads = new ArrayList<CpuThread>(N); 
        for (int i = 0; i < N; i++) {
            threads.add(new CpuThread(i, timeout));
            // Start thread so it remains idle
            threads.get(i).start(); 
        }

        // Use a while loop to pass strings to threads
        while (wq.size() != 0) {
            // Find idle processor by looping through all processors and checking workAvailable
            for (int i = 0; i < N; i++) {
                if (!threads.get(i).workAvailable) {
                    try {
                        // Set s to new line
                        threads.get(i).s = wq.remove(); 
                        // Set work Available to true
                        threads.get(i).workAvailable = true; 
                    } catch (NoSuchElementException e) {
                        // NoSuchElementException
                    }
                }
            }
        }

        // This while loop acts as another thread
        // in the main execution. Shutdown all threads once wq is empty
        while (true) {
            boolean allDown = true; 
            if (wq.size() == 0) {
                for (int i = 0; i < N; i++) {
                    if (!threads.get(i).workAvailable) {
                        threads.get(i).shutdown = true; 
                    } else {
                        allDown = false;
                    }
                }
                if (allDown) {
                    return; 
                }
            }
        }

    }

    public static void main(String[] args) {
        String path = args[0];      // Path of input file
        int N = Integer.parseInt(args[1]); // Number of CPUs on the machine, N

        try {
            long timeout = Long.parseLong(args[2]);
            Dispatcher.dispatch(path, N, timeout); 
        } catch (ArrayIndexOutOfBoundsException e) {
            Dispatcher.dispatch(path, N, -1); 
        }

    }
    
}
