package com.manojkhannakm.nlptoolkit.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.Base64;

/**
 * @author Manoj Khanna
 */

public class StringUtils {

    private static final byte[] KEY_BYTES = "DhRya5eaGJJxyufW".getBytes();
    private static final char TAB = '\t';
    private static final char NEW_LINE = '\n';
    private static final char CARRIAGE_RETURN = '\r';
    private static final char SPACE = ' ';

    public static String encrypt(String string) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY_BYTES, "AES"));
        return Base64.getEncoder().withoutPadding().encodeToString(cipher.doFinal(string.getBytes()));
    }

    public static String decrypt(String string) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY_BYTES, "AES"));
        return new String(cipher.doFinal(Base64.getDecoder().decode(string.getBytes())));
    }

    public static boolean isDelimiter(char c) {
        return isBlankSpace(c) || isLineBreak(c);
    }

    public static boolean isBlankSpace(char c) {
        return c == TAB || c == SPACE;
    }

    public static boolean isLineBreak(char c) {
        return c == NEW_LINE || c == CARRIAGE_RETURN;
    }

    public static boolean isUrl(char[] chars, int l, int r) {   //TODO: Finish
        return false;
    }

    public static boolean isEmail(char[] chars, int l, int r) { //TODO: Finish
        return false;
    }

}
