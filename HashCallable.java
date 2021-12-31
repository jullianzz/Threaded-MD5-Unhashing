import java.util.concurrent.Callable;

public class HashCallable implements Callable<Integer> {

    String s; 
    UnHash uh;
    final int SEMAPHORE_COUNT;  // The number of semaphores allowed to hold the critical section defined in call()

    public HashCallable(int count) {
        uh = new UnHash(); 
        SEMAPHORE_COUNT = count;
    }

    @Override
    public Integer call() throws Exception {    // Critical Section
        uh.stop_task = false; 
        int val = uh.unhash(s);
        print(val);
        return val;     // If val not -1, unhashing is successful
    }

    void print(int val) {
        if (val != -1) {    // Print if hash is successful
            System.out.println(val);
        } 
        // Don't print if hash unsuccessful
    }

    HashCallable createNew() {
        return new HashCallable(SEMAPHORE_COUNT);
    }

}