package com.manojkhannakm.nlptoolkit.util;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.GeneralSecurityException;

/**
 * @author Manoj Khanna
 */

public class IoUtils {

    private static final int BUFFER_SIZE = 1024;
    private static final byte[] KEY_BYTES = "MdFd67GgCMREeLD8".getBytes();

    public static byte[] read(InputStream inputStream) throws IOException {
        return read(inputStream, null);
    }

    public static void write(InputStream inputStream, OutputStream outputStream) throws IOException {
        write(inputStream, outputStream, null);
    }

    public static byte[] read(InputStream inputStream, ReadListener readListener) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        write(inputStream, byteArrayOutputStream, bytesWritten -> {
            if (readListener != null) {
                readListener.read(bytesWritten);
            }
        });
        return byteArrayOutputStream.toByteArray();
    }

    public static void write(InputStream inputStream, OutputStream outputStream, WriteListener writeListener) throws IOException {
        byte[] bufferBytes = new byte[BUFFER_SIZE];
        int bytesRead;
        long bytesWritten = 0;
        while ((bytesRead = inputStream.read(bufferBytes)) != -1) {
            outputStream.write(bufferBytes, 0, bytesRead);

            if (writeListener != null) {
                bytesWritten += bytesRead;
                writeListener.written(bytesWritten);
            }
        }
    }

    public static String readFile(String fileName) throws IOException {
        return readFile(fileName, null);
    }

    public static void writeFile(String fileName, String data) throws IOException {
        writeFile(fileName, data, null);
    }

    public static String readFile(String fileName, ReadListener readListener) throws IOException {
        return new String(read(new FileInputStream(fileName), readListener));
    }

    public static void writeFile(String fileName, String data, WriteListener writeListener) throws IOException {
        write(new ByteArrayInputStream(data.getBytes()), new FileOutputStream(fileName), writeListener);
    }

    public static CipherInputStream readEncryptedFile(String fileName) throws GeneralSecurityException, IOException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY_BYTES, "AES"));
        return new CipherInputStream(new FileInputStream(fileName), cipher);
    }

    public static void writeEncryptedFile(String fileName, String content) throws GeneralSecurityException, IOException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY_BYTES, "AES"));
        try (CipherOutputStream cipherOutputStream = new CipherOutputStream(new FileOutputStream(fileName), cipher)) {
            cipherOutputStream.write(content.getBytes());
        }
    }

    public interface ReadListener {

        void read(long bytesRead);

    }

    public interface WriteListener {

        void written(long bytesWritten);

    }

}
