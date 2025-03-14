package CONTROLLER.ENCRIPTACION;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encriptador {

    private static final String MASTER_KEY = System.getenv("MASTER_KEY"); // Retrieve master key from environment variable

    // Encripta la clave AES usando la clave maestra
    public static String encryptKey(SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec masterKeySpec = new SecretKeySpec(MASTER_KEY.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, masterKeySpec);
        byte[] encryptedKey = cipher.doFinal(key.getEncoded());
        return Base64.getEncoder().encodeToString(encryptedKey);
    }

    // Desencripta la clave AES usando la clave maestra
    public static SecretKey decryptKey(String encryptedKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec masterKeySpec = new SecretKeySpec(MASTER_KEY.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, masterKeySpec);
        byte[] decodedKey = Base64.getDecoder().decode(encryptedKey);
        byte[] decryptedKey = cipher.doFinal(decodedKey);
        return new SecretKeySpec(decryptedKey, "AES");
    }
}