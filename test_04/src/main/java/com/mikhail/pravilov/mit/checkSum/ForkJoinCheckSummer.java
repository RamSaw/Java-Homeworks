package com.mikhail.pravilov.mit.checkSum;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * Class that implements static method to get get check sum of path with {@link ForkJoinPool}.
 */
public class ForkJoinCheckSummer {
    /**
     * Calculates check sum with MD5.
     * @param path where to calculate check sum.
     * @return check sum.
     */
    public static byte[] getCheckSum(Path path) {
        return new ForkJoinPool().invoke(new ForkJoinCheckSummerTask(path));
    }

    private static class ForkJoinCheckSummerTask extends RecursiveTask<byte[]> {
        private Path path;

        public ForkJoinCheckSummerTask(Path path) {
            this.path = path;
        }

        @Override
        protected byte[] compute() {
            MessageDigest md5Hash;
            try {
                md5Hash = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                completeExceptionally(e);
                return null;
            }
            if (!Files.isDirectory(path)) {
                byte[] bufferJustToRead = new byte[1024];
                try (InputStream is = Files.newInputStream(path);
                     DigestInputStream dis = new DigestInputStream(is, md5Hash)) {
                    while (dis.read(bufferJustToRead) != -1) {
                    }
                } catch (IOException e) {
                    completeExceptionally(e);
                    return null;
                }
                return md5Hash.digest();
            }
            else {
                ArrayList<ForkJoinCheckSummerTask> tasks = new ArrayList<>();
                md5Hash.update(path.getFileName().toString().getBytes());
                Collection<Path> subFiles;
                try {
                    subFiles = Files.walk(path).filter(Files::isRegularFile).collect(Collectors.toList());
                } catch (IOException e) {
                    completeExceptionally(e);
                    return null;
                }
                for (Path subFile : subFiles) {
                    ForkJoinCheckSummerTask newTask = new ForkJoinCheckSummerTask(subFile);
                    newTask.fork();
                    tasks.add(newTask);
                }
                for (ForkJoinCheckSummerTask task : tasks) {
                    byte[] checkSumInSubFile = task.join();
                    md5Hash.update(checkSumInSubFile);
                }
            }
            return md5Hash.digest();
        }
    }
}
