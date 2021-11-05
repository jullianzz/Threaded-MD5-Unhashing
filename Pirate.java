import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Pirate {

    static void findTreasure(String path, int N, long timeout) {

        HashCallable hc = new HashCallable(); 
        // First unhashing run
        Dispatcher.dispatch(path, N, timeout, hc); 

        // Iterate through CpuThreads in Dispatcher.threads and compile list of master array of cracked hashes
        LinkedList<String> IUH = new LinkedList<String>(); 
        // remove duplicate elements in array
        // sort LL 

        // Create new path
        try {
            FileWriter fw = new FileWriter("intially_uncrackable.txt",true);
            while (IUH.size() != 0) {
                String s = IUH.remove(); 
                fw.write(s+"\\n");
            }
            fw.close();

        } catch (IOException e) {
            // IOException
        }

        CompoundHintCallable chc = new CompoundHintCallable(); 

        Dispatcher.dispatch("intially_uncrackable.txt", N, timeout, chc); 
        
    }

    static void main(String[] args) {
        String path = args[0];      // Path of input file
        int N = Integer.parseInt(args[1]); // Number of CPUs on the machine, N
        long timeout = Long.parseLong(args[2]);

        Pirate.findTreasure(path, N, timeout);
    }
}
