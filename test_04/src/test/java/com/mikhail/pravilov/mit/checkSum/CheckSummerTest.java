package com.mikhail.pravilov.mit.checkSum;

import org.junit.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

import static org.junit.Assert.*;

public class CheckSummerTest {
    @Test
    public void getCheckSumEmptyDir() throws Exception {
        Path path = Paths.get("testDirs/emptyDir/");
        byte[] oneThreadResult = CheckSummer.getCheckSum(path);
        byte[] forkJointResult = ForkJoinCheckSummer.getCheckSum(path);
        assertArrayEquals(oneThreadResult, forkJointResult);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update("emptyDir".getBytes());
        assertArrayEquals(md.digest(), forkJointResult);
    }

    @Test
    public void getCheckSumOneEmptyFile() throws Exception {
        Path path = Paths.get("testDirs/oneEmptyFile");
        byte[] oneThreadResult = CheckSummer.getCheckSum(path);
        byte[] forkJointResult = ForkJoinCheckSummer.getCheckSum(path);
        assertArrayEquals(oneThreadResult, forkJointResult);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(Files.readAllBytes(path));
        assertArrayEquals(md.digest(), forkJointResult);
    }

    @Test
    public void getCheckSumOneFileWithContent() throws Exception {
        Path path = Paths.get("testDirs/oneFileWithContent");
        byte[] oneThreadResult = CheckSummer.getCheckSum(path);
        byte[] forkJointResult = ForkJoinCheckSummer.getCheckSum(path);
        assertArrayEquals(oneThreadResult, forkJointResult);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(Files.readAllBytes(path));
        assertArrayEquals(md.digest(), forkJointResult);
    }

    @Test
    public void getCheckSumDirWithOneFile() throws Exception {
        Path path = Paths.get("testDirs/dirWithOneFile");
        byte[] oneThreadResult = CheckSummer.getCheckSum(path);
        byte[] forkJointResult = ForkJoinCheckSummer.getCheckSum(path);
        assertArrayEquals(oneThreadResult, forkJointResult);
        MessageDigest md1 = MessageDigest.getInstance("MD5");
        MessageDigest md2 = MessageDigest.getInstance("MD5");
        md2.update(Files.readAllBytes(Paths.get("testDirs/dirWithOneFile/file")));
        md1.update("dirWithOneFile".getBytes());
        md1.update(md2.digest());
        assertArrayEquals(md1.digest(), oneThreadResult);
    }

    @Test
    public void getCheckSumHardTest() throws Exception {
        Path path = Paths.get("testDirs");
        byte[] oneThreadResult = CheckSummer.getCheckSum(path);
        byte[] forkJointResult = ForkJoinCheckSummer.getCheckSum(path);
        assertArrayEquals(oneThreadResult, forkJointResult);
    }
}