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
        for (int i = 0 ; i < ch.size()-1; i++) {
            alpha = ch.get(i)+1;
            beta = ch.get(i+1)-1; 
            System.out.println("alpha: " + alpha);
            System.out.println("beta: " + beta);
            k = uh.unhashWithCompoundHint(s, alpha, beta);
            if (k != -1) {    // Hash successful
                ch.remove(i);
                ch.remove(i+1);
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

    CompoundHintCallable createNew() {
        return new CompoundHintCallable(ch);
    }

}
