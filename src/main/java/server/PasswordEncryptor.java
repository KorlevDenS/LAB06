package server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordEncryptor {

    public static String generateString(int length) {
        Random rng = new Random();
        String characters = "()#^*&1234567890ZXCVBNM.,QWERTYUIsdfghjjk";
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    public static String getHash(String nudeString, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance(algorithm);
        String pepper = "*63&^mVLC(#";
        String updatedString = pepper + nudeString;
        byte[] hash = mDigest.digest(updatedString.getBytes());
        StringBuilder strongPassword = new StringBuilder();
        for (byte b : hash) strongPassword.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return strongPassword.toString();
    }
}
