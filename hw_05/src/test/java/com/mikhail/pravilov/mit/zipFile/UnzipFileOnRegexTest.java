package com.mikhail.pravilov.mit.zipFile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.*;

public class UnzipFileOnRegexTest {
    private final String currentDirAbsolutePath = Paths.get("").toAbsolutePath().toString();

    @Before
    public void createZipFiles() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("Test String");

        File f = new File("test1.zip");
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f))) {
            ZipEntry e = new ZipEntry("kek.txt");
            out.putNextEntry(e);

            byte[] data = sb.toString().getBytes();
            out.write(data, 0, data.length);
            out.closeEntry();

            e = new ZipEntry("dir/kek.txt");
            out.putNextEntry(e);
        }

        f = new File("test2.txt.zip");
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f))) {
            /* Add kuk.txt */
            ZipEntry e = new ZipEntry("kuk.txt");
            out.putNextEntry(e);
            byte[] data = sb.toString().getBytes();
            out.write(data, 0, data.length);
            out.closeEntry();

            /* Add kek.txt */
            e = new ZipEntry("kek.txt");
            out.putNextEntry(e);
            out.closeEntry();

            /* Add aaa.txt */
            e = new ZipEntry("aaa.txt");
            out.putNextEntry(e);
            out.closeEntry();
        }
    }

    @Test
    public void findAllZipFiles() throws Exception {
        ArrayList<String> zipFiles = UnzipFileOnRegex.findAllZipFiles(currentDirAbsolutePath);
        assertTrue(zipFiles.contains(currentDirAbsolutePath + "/test1.zip"));
        assertTrue(zipFiles.contains(currentDirAbsolutePath + "/test2.txt.zip"));
    }

    @Test(expected = NotDirectoryException.class)
    public void findAllZipFilesThrowsNotDirectoryException() throws Exception {
        UnzipFileOnRegex.findAllZipFiles("notDirectory&^%*&@! PIWSUJDUQH SDl; dskj cxvn");
    }

    private void checkFilesOnExistenceAndDelete(ArrayList<String> filePaths) throws IOException {
        for (String filePath : filePaths) {
            File file = new File(filePath);
            assertTrue(file.exists());
            Files.delete(FileSystems.getDefault().getPath(filePath));
        }
    }

    @Test
    public void extractFilesThatFitRegEx() throws Exception {
        UnzipFileOnRegex.extractFilesThatFitRegEx(currentDirAbsolutePath + "/test1.zip", ".*", currentDirAbsolutePath);
        checkFilesOnExistenceAndDelete(new ArrayList<>(Arrays.asList(currentDirAbsolutePath + "/dir/kek.txt",
                currentDirAbsolutePath + "/kek.txt")));
        Files.delete(FileSystems.getDefault().getPath(currentDirAbsolutePath + "/dir"));

        UnzipFileOnRegex.extractFilesThatFitRegEx(currentDirAbsolutePath + "/test2.txt.zip", ".*",
                currentDirAbsolutePath);
        checkFilesOnExistenceAndDelete(new ArrayList<>(Arrays.asList(currentDirAbsolutePath + "/kuk.txt",
                currentDirAbsolutePath + "/kek.txt", currentDirAbsolutePath + "/aaa.txt")));

        UnzipFileOnRegex.extractFilesThatFitRegEx(currentDirAbsolutePath + "/test2.txt.zip",
                "kuk.txt", currentDirAbsolutePath);
        checkFilesOnExistenceAndDelete(new ArrayList<>(Arrays.asList(currentDirAbsolutePath + "/kuk.txt")));

        UnzipFileOnRegex.extractFilesThatFitRegEx(currentDirAbsolutePath + "/test2.txt.zip",
                "k.k.txt", currentDirAbsolutePath);
        checkFilesOnExistenceAndDelete(new ArrayList<>(Arrays.asList(currentDirAbsolutePath + "/kuk.txt",
                currentDirAbsolutePath + "/kek.txt")));
    }

    @After
    public void deleteCreatedZipFiles() throws Exception {
        Files.deleteIfExists(FileSystems.getDefault().getPath(currentDirAbsolutePath + "/test1.zip"));
        Files.deleteIfExists(FileSystems.getDefault().getPath(currentDirAbsolutePath + "/test2.txt.zip"));
    }
}