package org.uinme.tools.azdatachecker.csvutil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class FileDigest {
    public byte[] getDigest(Path path) {
        byte[] digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try (DigestInputStream input = new DigestInputStream(new FileInputStream(path.toFile()), md)) {
                while (input.read() != -1) {}
                digest = md.digest();
            } catch (FileNotFoundException e) {
                throw new RuntimeException();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
        return digest;
    }
}
