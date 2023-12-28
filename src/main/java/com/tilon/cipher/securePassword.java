package com.example.springtest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class securePassword {

    public String salt(String password) throws NoSuchAlgorithmException {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[15];
        sr.nextBytes(salt);
        StringBuffer sb = new StringBuffer();
        for(byte b : salt) {
            sb.append(String.format("%02x",b));
        }
        return sb.toString();
    }

    public String getEncrypt(String pwd, String salt) throws NoSuchAlgorithmException {
        String result = "";
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update((pwd + salt).getBytes());
        byte[] pwdSalt = md.digest();

        StringBuffer sb = new StringBuffer();
        for(byte b : pwdSalt) {
            sb.append(String.format("%02x", b));
        }
        result = sb.toString();
      return result;
    }
}
