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
    HashSet<Integer> ch = new HashSet<Integer>();           // ch : cracked hashes
    LinkedList<String> iuh = new LinkedList<String>();      // iuh : initially uncrackable hashes
    HashCallable callable; 

    // Critical Section stuff
    static LinkedList<Integer> semaphore_queue;     // Queue that holds the thread ids that are waiting for the semaphore
    static int turn_id;             // id of the thread that gets to run the critical section
    final int thr_id;                // id of this thread
    static int numThrRunningCS;     // keeps track of number of threads holding a semaphore


    public CpuThread(int i, long timeout, HashCallable cb) {
        thr = new Thread(this, String.valueOf(i)); 
        this.timeout = timeout; 
        executor = Executors.newSingleThreadExecutor();
        busy = false; 
        shutdown = false; 
        callable = cb; 
        thr_id = i; 
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
                        acquire();          // Acquire semaphore before entering critical section
                        future = executor.submit(callable);
                        int result = future.get(timeout, TimeUnit.MILLISECONDS);
                        release();          // Release the semaphore/critical section
                        if (result != -1) 
                            ch.add(result);
                        busy = false; 
                    } catch (TimeoutException e) {
                        callable.uh.stop_task = true; 
                        release();          // Release the semaphore/critical section and set next thread to use semaphore
                        // System.out.println("Timed out");
                        iuh.add(s); 
                    } catch (InterruptedException e) {
                        // InterruptedException
                    } catch (ExecutionException e) {
                        // ExecutionException
                    } finally {
                        try {
                            // Block to get the return time from this thread so that callable finishes executing before while loops goes back to top
                            future.get();
                            future.cancel(true);
                        } catch (Exception e) {
                            // Exception
                        }
                        // } catch (InterruptedException e) {
                        //     // InterruptedException
                        // } catch (ExecutionException e) {
                        //     // ExecutionException
                        // } catch (NullPointerException e) {
                        //     // NullPointerException
                        // }
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

    void acquire() {    // Blocking function that acquires semaphore/critical section
        if (CpuThread.numThrRunningCS >= callable.SEMAPHORE_COUNT) {    
            semaphore_queue.add(thr_id);    // Add thread id to the queue of threads waiting for semaphore
            while (turn_id != thr_id);      // Block until turn_id = thr_id
        } 
        // If the the number of holding threads is fewer than available semaphores, just execute critical path w/o waiting
        numThrRunningCS ++;
        return;
    }

    void release() {    // Release semaphore/critical section
        numThrRunningCS --;
        // turn_id = semaphore_queue.remove();
    }

}