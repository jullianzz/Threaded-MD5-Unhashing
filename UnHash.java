
public class UnHash {

    boolean stop_task;

    int unhash (String to_unhash) {
        // System.out.println("String to unhash: " + to_unhash);
        int i = 0; 
        while (true) {
            if (stop_task) {
                // System.out.println("String to unhash: " + to_unhash);
                return -1; 
            }
            if (to_unhash.equals(Hash.hash(String.valueOf(i)))) {
                // System.out.println("String to unhash: " + to_unhash);
                return i; 
            } 
            i++;
        }
    }

    // this method only holds if the timeout is shared across the set of hints
    int unhashWithCompoundHint(String to_unhash, int alpha, int beta) { 
        // System.out.println("String to unhash: " + to_unhash + " poopity");
        int i = alpha + 1; 
        while (i <= beta-1) {
            if (stop_task) {
                break; 
            }
            String s = String.valueOf(alpha) + ";" + String.valueOf(i) + ";" + String.valueOf(beta);
            // System.out.println("Hash " + s);
            if (to_unhash.equals(Hash.hash(s))) {
                // System.out.println("Hash " + s);
                return i; 
            } 
            i++;
        }
        // code path comes here if there is no hash found or no timeout occurs, or both
        return -1; 
    }
    public static void main(String[] args) {
        String to_unhash = args[0]; 
        UnHash uh = new UnHash(); 
        System.out.println(uh.unhash(to_unhash));
    }
}
