import java.util.LinkedList;

public class CompoundHintCallable extends HashCallable {

    static LinkedList<Integer> ch; 

    public CompoundHintCallable(int count) {
        super(count);
    }
    
    @Override
    public Integer call() throws Exception {    // Critical Section
        uh.stop_task = false; 
        int k = -1; 
        int alpha = ch.get(0); 
        int beta = ch.get(1); 

        for (int i = 0 ; i < ch.size()-1; i++) {
            alpha = ch.get(i);
            for (int j = 1; j < ch.size(); j++) {
                beta = ch.get(j); 
                k = uh.unhashWithCompoundHint(s, alpha, beta);
                if (k != -1) {    // Hash successful
                    // Remove beta before alpha so indices are conserved. Assumption: index of alpha always < index of beta.
                    ch.remove(j);   // Remove beta 
                    ch.remove(i);   // Remove alpha
                    print(k, alpha, beta); 
                    return k; // if k not -1, unhashing is successful
                }
            }
        }
        
        print(k, alpha, beta);
        return -1; // if k not -1, unhashing is successful
    }

    void print(int k, int alpha, int beta) {
        if (k != -1) {
            System.out.println(alpha + ";" + k + ";" + beta);
        } else {
            System.out.println(s);
        }
    }

    CompoundHintCallable createNew() {
        return new CompoundHintCallable(SEMAPHORE_COUNT);
    }

}
