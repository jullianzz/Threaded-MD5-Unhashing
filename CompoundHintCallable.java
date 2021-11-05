import java.util.ArrayList;

public class CompoundHintCallable extends HashCallable {

    ArrayList<Integer> CH;     // Compound hints
    
    @Override
    public Integer call() throws Exception {
        uh.stop_task = false; 
        int k = -1; 
        int alpha = CH.get(0); 
        int beta = CH.get(1); 
        for (int i = 0 ; i < CH.size()-1; i++) {
            k = uh.unhash(s); 
            alpha = CH.get(i);
            beta = CH.get(i+1); 
            k = uh.unhashWithCompoundHint(s, alpha, beta);
            if (k != -1) {    // Hash successful
                break; 
            }
        }
        print(k, alpha, beta); 
        return k; // if k not -1, unhashing is successful
    }

    void print(int k, int alpha, int beta) {
        if (k != -1) {
            System.out.println(alpha + ";" + k + ";" + beta);
        } else {
            System.out.println(s);
        }
    }

}
