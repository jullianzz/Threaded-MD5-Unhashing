import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class Dispatcher {

    static ArrayList<CpuThread> threads;

    static boolean done = false; 

    static void dispatch(String path, int N, long timeout, HashCallable cb) {
        // Declare Global wq
        LinkedList<String> wq = new LinkedList<String>();

        // Read the input file of path and populate wq
        BufferedReader reader; 
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            // String to_unhash; 
            while (line != null) {
                // to_unhash = line.replace("\\n","");
                // wq.add(to_unhash);
                wq.add(line);
                line = reader.readLine(); 
            }
            reader.close(); 
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        // Create the N threads and start run() for each so it remains idle
        threads = new ArrayList<CpuThread>(N); 
        for (int i = 0; i < N; i++) {
            threads.add(new CpuThread(i, timeout, cb.createNew()));
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
                    } catch (NoSuchElementException e) {
                        // NoSuchElementException
                    }
                }
            }
        }

        // Shutdown all threads once wq is empty
        while (true) {
            boolean all_shutdown = true; 
            if (wq.size() == 0) {
                for (int i = 0; i < N; i++) {
                    if (!threads.get(i).busy && threads.get(i).buffer.size() == 0) {
                        threads.get(i).shutdown = true; 
                    } else {
                        all_shutdown = false;
                    }
                }
                if (all_shutdown) {
                    boolean dispatcher_complete = true;
                    for (int i = 0; i < N; i++) {
                        if (!threads.get(i).executor.isShutdown()) {
                            dispatcher_complete = false; 
                            break;
                        }
                    }
                    if (dispatcher_complete) {
                        done = true;
                        // System.out.println("complete");
                        return;
                    }
                }                
            }
        }

    }
    
}
