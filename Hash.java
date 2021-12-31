import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    static String hash(String to_hash_string) {
        try {
            // String to_hash_string = String.valueOf(to_hash); 
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(to_hash_string.getBytes()); 
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; ++i) {
                sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString(); 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "Error: No hash equivalent"; 
        }

    }

    public static void main(String[] args) {
        // int to_hash = Integer.parseInt(args[0]); 
        System.out.println(Hash.hash(args[0]));
    }
}