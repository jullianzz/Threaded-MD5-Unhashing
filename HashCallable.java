import java.util.concurrent.Callable;

public class HashCallable implements Callable<Integer> {

    String s; 
    UnHash uh;

    public HashCallable() {
        uh = new UnHash(); 
        System.out.println("new callable  created");
    }

    @Override
    public Integer call() throws Exception {
        uh.stop_task = false; 
        // System.out.println(" unhashing: " + s);
        int val = uh.unhash(s);
        // System.out.println(" unhashed: " + s);
        print(val);
        return val; // if val not -1, unhashing is successful
    }

    void print(int val) {
        if (val != -1) {
            // System.out.println(s + " unhashed is " + val);
            System.out.println(val);
        } else {
            // System.out.println(s + " is unhashable");
            System.out.println(s);
        }
    }

    HashCallable createNew() {
        return new HashCallable();
    }
    
}
