
public class UnHash {

    boolean stop_task = false; 

    int unhash (String to_unhash) {
        int i = 0; 
        while (true) {
            if (stop_task) {
                return -1; 
            }
            if (to_unhash.equals(Hash.hash(i))) {
                return i; 
            } 
            i++;
        }
    }

    // this method only holds if the timeout is shared across the set of hints
    int unhashWithCompoundHint(String to_unhash, int alpha, int beta) { 
        int i = alpha; 
        while (i <= beta) {
            if (stop_task) {
                break; 
            }
            if (to_unhash.equals(Hash.hash(i))) {
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
