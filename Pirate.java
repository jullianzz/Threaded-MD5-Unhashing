import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

public class Pirate {

    static void findTreasure(String path, int N, long timeout) {

        // First UnHashing run
        HashCallable hc = new HashCallable(N);      // SEMAPHORE_COUNT = N
        Dispatcher.dispatch(path, N, timeout, hc); 

        // Iterate through CpuThreads in Dispatcher.threads and create ch and iuh data structures 
        HashSet<Integer> ch = new HashSet<Integer>();
        LinkedList<String> iuh = new LinkedList<String>();
        for (CpuThread thr : Dispatcher.threads) { 
            ch.addAll(thr.ch);      // Compile list of master array of cracked hashes
            iuh.addAll(thr.iuh);    // Compile list of master array of initially uncracked hashes
        }

        // System.out.println("\nSecond run:");

        // Sort ch into ch_list
        LinkedList<Integer> ch_list = new LinkedList<Integer>(ch);
        Collections.sort(ch_list);


        // Create new path input file for second UnHashing run
        try {
            FileWriter fw = new FileWriter("intially_uncrackable.txt");
            while (iuh.size() != 0) {
                String s = iuh.remove(); 
                fw.write(s + "\n");
            }
            fw.close();

        } catch (IOException e) {
            // IOException
        }

        CompoundHintCallable chc = new CompoundHintCallable(1);    // SEMAPHORE_COUNT = 1
        CompoundHintCallable.ch = ch_list; 
 
        Dispatcher.dispatch("intially_uncrackable.txt", N, timeout, chc); 
        
    }

    public static void main(String[] args) {
        String path = args[0];                  // Path of input file
        int N = Integer.parseInt(args[1]);      // Number of CPUs on the machine, N
        long timeout = Long.parseLong(args[2]);

        Pirate.findTreasure(path, N, timeout);
    }
}
