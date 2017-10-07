package com.mikhail.pravilov.mit.zipFile;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Class with static methods to realize a program ZipFile!.
 * Program gets path to directory and regular expression.
 * It extracts all files that fit the given regular expression and are located in zip files which are located in given directory.
 */
public class UnzipFileOnRegex {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Wrong format. Expected: path regex");
            return;
        }

        try {
            ArrayList<String> zipFilenames = findAllZipFiles(args[0]);
            for (String zipFilename : zipFilenames) {
                extractFilesThatFitRegEx(zipFilename, args[1], args[0]);
            }
        } catch (NotDirectoryException e) {
            System.err.println("Invalid directory: " + args[0]);
        } catch (IOException e) {
            System.err.println("Error occurred during extracting");
        }
    }

    /**
     * Finds all zip files in directory specified by path.
     * @param path to directory.
     * @return names of files in directory that are zip files.
     * @throws NotDirectoryException if path doesn't specify a directory.
     */
    @NotNull
    public static ArrayList<String> findAllZipFiles(@NotNull String path) throws NotDirectoryException {
        File directory = new File(path);
        if (!directory.isDirectory())
            throw new NotDirectoryException(path);

        File[] files = directory.listFiles();

        ArrayList<String> zipFilenames = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                try (ZipFile zipFile = new ZipFile(file)){
                    zipFilenames.add(zipFile.getName());
                } catch (IOException ignored) {
                }
            }
        }

        return zipFilenames;
    }

    /**
     * Extracts all files which names fit given regular expression.
     * @param zipFilename from which extract files
     * @param regularExpression to check if fits
     * @param pathToExtract where files will be extracted
     * @throws IOException if there is any problems with zipFilename or created files.
     */
    public static void extractFilesThatFitRegEx(String zipFilename, String regularExpression,
                                                String pathToExtract) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilename))) {
            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (!zipEntry.isDirectory() && Pattern.matches(regularExpression, zipEntry.getName())) {
                    File fileToExtract = new File(pathToExtract + File.separator + zipEntry.getName());
                    new File(fileToExtract.getParent()).mkdirs();

                    try (FileOutputStream extractedFile = new FileOutputStream(fileToExtract)){
                        int read;
                        byte[] buffer = new byte[1024];

                        while ((read = zipInputStream.read(buffer)) > 0) {
                            extractedFile.write(buffer, 0, read);
                        }
                    }
                }
            }
        }
    }
}
