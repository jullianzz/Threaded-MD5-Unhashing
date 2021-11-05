import java.util.concurrent.Callable;

public class HashCallable implements Callable<Integer> {

    String s; 
    UnHash uh = new UnHash(); 

    @Override
    public Integer call() throws Exception {
        uh.stop_task = false; 
        int val = uh.unhash(s); 
        print(val);
        return val; // if val not -1, unhashing is successful
    }

    void print(int val) {
        if (val != -1) {
            System.out.println(val);
        } else {
            System.out.println(s);
        }
    }
    
}
