import java.util.ArrayList;

public class CompoundHintCallable extends HashCallable {

    static ArrayList<Integer> ch;     // Compound hints

    public CompoundHintCallable(ArrayList<Integer> ch_arry) {
        // System.out.println("new com hint callbale created");
        ch = ch_arry;
    }
    
    @Override
    public Integer call() throws Exception {
        // System.out.println(s + "KRAKEN ");
        uh.stop_task = false; 
        int k = -1; 
        int alpha = ch.get(0); 
        int beta = ch.get(1); 
        // int i = 0; 
        for (int i = 0 ; i < ch.size()-1; i++) {
            alpha = ch.get(i);
            for (int j = 1; j < ch.size(); j++) {
                beta = ch.get(j); 
                k = uh.unhashWithCompoundHint(s, alpha, beta);
                if (k != -1) {    // Hash successful
                    // System.out.println("succ!");
                    print(k, alpha, beta); 
                    // ch.remove(i);
                    // ch.remove(i+1);
                    return k; // if k not -1, unhashing is successful
                }
            }
            // if (s.equals("600a65004f52ffa44302865e32980e30")) {
            //     System.out.println("alpha: " + alpha);
            //     System.out.println("beta: " + beta);
            // }
        }
        // System.out.println("here!");
        print(k, alpha, beta); 
        return -1; // if k not -1, unhashing is successful
    }

    void print(int k, int alpha, int beta) {
        // System.out.println("Printing!");
        if (k != -1) {
            System.out.println(alpha + ";" + k + ";" + beta);
        } else {
            // System.out.println("come here!");
            System.out.println(s);
        }
    }

    CompoundHintCallable createNew() {
        return new CompoundHintCallable(ch);
    }

}
