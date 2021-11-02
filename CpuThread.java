import java.util.NoSuchElementException;
import java.lang.Runnable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CpuThread implements Runnable {
    Thread thr;
    long timeout; 
    String s; 
    boolean workAvailable;
    ExecutorService executor;
    Future<?> future;
    volatile boolean shutdown; 

    public CpuThread(int i, long timeout) {
        thr = new Thread(this, String.valueOf(i)); 
        this.timeout = timeout; 
        executor = Executors.newSingleThreadExecutor();
        workAvailable = false; 
        shutdown = false; 
    }


    @Override
    public void run() {

        while (!shutdown) {
            try {

                future = executor.submit(()->{
                    if (workAvailable) {
                        System.out.println(UnHash.unhash(s));
                    }
                    workAvailable = false; 
                }); 
                if (timeout != -1) {
                    Object result = future.get(timeout, TimeUnit.MILLISECONDS); 
                }

            } catch (TimeoutException e) {  // Thrown if unhashing takes too long (a.k.a thr.run() times out)
                System.out.println(s);
                future.cancel(true); 
            } catch (NoSuchElementException e) {
                future.cancel(true); 
            } catch (InterruptedException e) {
                future.cancel(true); 
            } catch (ExecutionException e) {
                future.cancel(true); 
            }  
        }

        // System.out.println("Terminate thread**");
        future.cancel(true);
        executor.shutdownNow();
    }

    void start() {
        thr.start();
    }
    
}