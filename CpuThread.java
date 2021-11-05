import java.util.NoSuchElementException;
import java.lang.Runnable;
import java.util.concurrent.Callable;
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
    Future<Integer> future;
    volatile boolean shutdown; 
    LinkedList<String> buffer = new LinkedList<String>();
    LinkedList<Integer> CH = new LinkedList<Integer>();     // CH : cracked hashes
    LinkedList<String> IUH = new LinkedList<String>();      // IUH : initially uncrackable hashes
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
            try {
                if (buffer.size() != 0) {
                    String s = buffer.remove(); 
                    callable.s = s; 
                    busy = true; 
                    try {
                        future = executor.submit(callable);
                        int result; 
                        if (timeout != -1)
                            result = future.get(timeout, TimeUnit.MILLISECONDS); 
                        else
                            result = future.get(); 
                        if (result != -1) 
                            CH.add(result);
                    } catch (TimeoutException e) {
                        callable.uh.stop_task = true; 
                        future.cancel(true);
                        IUH.add(s); 
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