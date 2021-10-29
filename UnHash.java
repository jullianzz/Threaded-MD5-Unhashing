
public class UnHash {

    static int unhash (String to_unhash) {
        int i = 0; 
        while (true) {
            if (to_unhash.equals(Hash.hash(i))) {
                return i; 
            } 
            i++;
        }
    }
    public static void main(String[] args) {
        String to_unhash = args[0]; 
        System.out.println(UnHash.unhash(to_unhash)); 
    }
}
