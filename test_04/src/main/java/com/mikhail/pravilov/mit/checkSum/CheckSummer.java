package com.mikhail.pravilov.mit.checkSum;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.stream.Collectors;

public class CheckSummer {
    public static byte[] getCheckSum(Path path) throws NoSuchAlgorithmException, IOException {
        MessageDigest md5Hash = MessageDigest.getInstance("MD5");
        if (!Files.isDirectory(path)) {
            byte[] bufferJustToRead = new byte[1024];
            try (InputStream is = Files.newInputStream(path);
                 DigestInputStream dis = new DigestInputStream(is, md5Hash)) {
                while (dis.read(bufferJustToRead) != -1) {
                }
            }
        }
        else {
            md5Hash.update(path.getFileName().toString().getBytes());
            Collection<Path> subFiles = Files.walk(path).filter(Files::isRegularFile).collect(Collectors.toList());
            for (Path subFile : subFiles) {
                md5Hash.update(getCheckSum(subFile));
            }
        }
        return md5Hash.digest();
    }
}
