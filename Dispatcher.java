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

        // Distribute work to threads until there is no work left
        while (wq.size() != 0) {
            // Find idle processor by looping through all processors and checking busy
            for (int i = 0; i < N; i++) {
                if (!threads.get(i).busy) {
                    try {
                        // Add work to thread buffer
                        threads.get(i).addToBuffer(wq.remove()); 
                        // System.out.println("kill me1");
                    } catch (NoSuchElementException e) {
                        // NoSuchElementException
                    }
                }
            }
        }

        // Shutdown all threads once wq is empty
        while (true) {
            boolean allDown = true; 
            if (wq.size() == 0) {
                for (int i = 0; i < N; i++) {
                    if (!threads.get(i).busy && threads.get(i).buffer.size() == 0) {
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
