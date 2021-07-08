package com.mikhail.pravilov.mit.checkSum;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ForkJoinPool;

public class CheckSum {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Path path = Paths.get(System.getProperty("user.dir"));
        System.out.println("One thread:");
        long startTime = System.nanoTime();
        byte[] checkSum = CheckSummer.getCheckSum(path);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Time: " + totalTime);
        System.out.println("Result: " + new String(checkSum));
        System.out.println("-----------------------------");
        System.out.println("Fork Join:");
        startTime = System.nanoTime();
        checkSum = ForkJoinCheckSummer.getCheckSum(path);
        endTime   = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Time: " + totalTime);
        System.out.println("Result: " + new String(checkSum));
    }
}
