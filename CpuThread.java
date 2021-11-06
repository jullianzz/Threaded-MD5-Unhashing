import java.util.NoSuchElementException;
import java.lang.Runnable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.HashSet;
import java.util.LinkedList;


public class CpuThread implements Runnable {
    Thread thr;
    long timeout; 
    boolean busy;
    ExecutorService executor;
    Future<Integer> future;
    volatile boolean shutdown; 
    LinkedList<String> buffer = new LinkedList<String>();
    HashSet<Integer> ch = new HashSet<Integer>();     // ch : cracked hashes
    LinkedList<String> iuh = new LinkedList<String>();      // iuh : initially uncrackable hashes
    HashCallable callable; 

    public CpuThread(int i, long timeout, HashCallable cb) {
        thr = new Thread(this, String.valueOf(i)); 
        this.timeout = timeout; 
        executor = Executors.newSingleThreadExecutor();
        busy = false; 
        shutdown = false; 
        callable = cb; 
    }


    @Override
    public void run() {

        while (!shutdown) {
            System.out.print("");
            try {
                if (buffer.size() != 0) {
                    String s = buffer.remove(); 
                    callable.s = s; 
                    busy = true; 
                    try {
                        future = executor.submit(callable);
                        int result; 
                        result = future.get(timeout, TimeUnit.MILLISECONDS);
                        // System.out.println("It does return!");
                        if (result != -1) 
                            ch.add(result);
                        busy = false; 
                    } catch (TimeoutException e) {
                        callable.uh.stop_task = true; 
                        // System.out.println("Adding " + s);
                        iuh.add(s); 
                    } catch (InterruptedException e) {
                        // InterruptedException
                    } catch (ExecutionException e) {
                        // ExecutionException
                    } finally {
                        try {
                            future.get(); // put here for blocking purposes, need to get the return time from this thread
                        } catch (InterruptedException e) {
                            // InterruptedException
                        } catch (ExecutionException e) {
                            // ExecutionException
                        }
                        future.cancel(true);
                        busy = false; 
                    }
                }
            } catch (NoSuchElementException e) {
                // NoSuchElementException
            }
        }

        executor.shutdown();
    }

    void start() {
        thr.start();
    }
    
    void addToBuffer(String s) {
        buffer.add(s); 
    }

}