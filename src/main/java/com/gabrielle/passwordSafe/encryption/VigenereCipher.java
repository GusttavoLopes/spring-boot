package com.gabrielle.passwordSafe.encryption;

// http://www.facom.ufu.br/~albertini/1sem2017/infosec/Vigenere.java

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service("vigenereCipher")
public class VigenereCipher implements ICipherAlgorithm {

    public String encrypt(String password, String masterPassword)  {
        byte[] bytes = password.getBytes();
        int k = 0;
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] ^= masterPassword.charAt(k);
            k = (++k) % masterPassword.length();
        }
        return Util.byteArrayToHexString(bytes);
    }

    public String decrypt(String password, String masterPassword) {
        byte[] bytes = Util.hexStringToByteArray(password);
        int k = 0;
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] ^= masterPassword.charAt(k);
            k = (++k) % masterPassword.length();
        }
        return new String(bytes, StandardCharsets.US_ASCII);
    }
}

class Util {

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String byteArrayToHexString(byte[] bytes) {

        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}