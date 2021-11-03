import java.util.NoSuchElementException;
import java.lang.Runnable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.LinkedList;


public class CpuThread implements Runnable {
    Thread thr;
    long timeout; 
    boolean busy;
    ExecutorService executor;
    Future<?> future;
    volatile boolean shutdown; 
    LinkedList<String> buffer = new LinkedList<String>();

    public CpuThread(int i, long timeout) {
        thr = new Thread(this, String.valueOf(i)); 
        this.timeout = timeout; 
        executor = Executors.newSingleThreadExecutor();
        busy = false; 
        shutdown = false; 
    }


    @Override
    public void run() {

        while (!shutdown) {
            // System.out.println("kill me2");
            try {
                if (buffer.size() != 0) {
                    UnHash uh = new UnHash(); 
                    // System.out.println("kill me3");
                    String s = buffer.remove(); 
                    busy = true; 
                    try {
                        future = executor.submit(()->{
                            int val = uh.unhash(s); 
                            if (val != -1) {
                                // System.out.println("Thread " + thr.getName() + " hash of " + s + " returns " + val);
                                System.out.println(val);
                            }
                        });
                        if (timeout != -1) {
                            future.get(timeout, TimeUnit.MILLISECONDS); 
                        }
                    } catch (TimeoutException e) {
                        uh.stop_task = true; 
                        future.cancel(true); 
                        // System.out.println("Thread " + thr.getName() + " timed out on " + s);
                        System.out.println(s);
                    } catch (InterruptedException e) {
                        future.cancel(true); 
                    } catch (ExecutionException e) {
                        future.cancel(true); 
                    } finally {
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