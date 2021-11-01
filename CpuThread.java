import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.lang.Runnable;

public class CpuThread implements Runnable {
    private Thread thr;
    LinkedList<String> wq; 

    public CpuThread(LinkedList<String> wq, int i) {
        thr = new Thread(this, String.valueOf(i)); 
        this.wq = wq; 
    }

    @Override
    public void run() {
        // System.out.println("Running " + thr.getName()); 
        while (wq.size() != 0) {
            // Invoke UnHash.unhash method
            try {
                System.out.println(UnHash.unhash(wq.remove())); 
            } catch (NoSuchElementException e) {
                // It is possible that while running the thread, the 
                // while loop is entered (i.e. size != 0), but 
                // during between that condition check and the unhash method
                // another thread handles the last wq element and size becomes 0
            }
        }

    }

    void start() {
        // System.out.println("Starting " + thr.getName());
        thr.start();
    }
    
}