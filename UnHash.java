
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
    public static void main(String[] args) {
        String to_unhash = args[0]; 
        UnHash uh = new UnHash(); 
        System.out.println(uh.unhash(to_unhash));
    }
}
