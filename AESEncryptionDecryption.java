import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
* The AESEncryptionDecryption class provides methods for encrypting and decrypting
messages using the AES algorithm.
*/
public class AESEncryptionDecryption {
    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String ALGORITHM = "AES";

    /**
    * Generates a secret key based on the given string.
    * @param myKey the string to use as a base for generating the secret key
    */
    public void prepareSecretKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    } 

    /**
    Encrypts a message using the given secret key and returns the encrypted message
    as a Base64-encoded string.
    @param msgToEncrypt the message to encrypt
    @param secret the secret key to use for encryption
    @return the encrypted message as a Base64-encoded string
    */
    public String encrypt(String msgToEncrypt, String secret) {
        try {
            prepareSecretKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(msgToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    /**
    Decrypts a message using the given secret key and returns the decrypted message
    as a string.
    @param msgToDecrypt the message to decrypt as a Base64-encoded string
    @param secret the secret key to use for decryption
    @return the decrypted message as a string
    */
    public String decrypt(String msgToDecrypt, String secret) {
        try {
            prepareSecretKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(msgToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}