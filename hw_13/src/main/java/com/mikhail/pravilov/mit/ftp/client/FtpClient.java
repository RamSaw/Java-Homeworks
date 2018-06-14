package com.mikhail.pravilov.mit.ftp.client;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

class FtpClient implements AutoCloseable {
    @NotNull
    private final Socket clientSocket;
    /**
     * Stream to write to server.
     */
    @NotNull
    private final DataOutputStream dataOutputStream;
    /**
     * Stream to read from server.
     */
    @NotNull
    private final DataInputStream dataInputStream;

    FtpClient(@NotNull String hostName, int portNumber) throws IOException {
        clientSocket = new Socket(hostName, portNumber);
        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        dataInputStream = new DataInputStream(clientSocket.getInputStream());
    }

    /**
     * Returns list of all files and directories in path. {@link Boolean} indicator is used to distinguish file and directory.
     *
     * @param directory where to look for files and directories.
     * @return list of files and directories.
     * @throws IOException if error occurred during getting list of files and directories.
     */
    @NotNull
    Set<Pair<String, Boolean>> list(@NotNull Path directory) throws IOException {
        dataOutputStream.writeInt(1);
        dataOutputStream.writeUTF(directory.toAbsolutePath().toString());
        dataOutputStream.flush();
        int sizeOfList = dataInputStream.readInt();
        if (sizeOfList == -1) {
            throw new FileNotFoundException("No such directory");
        }
        HashSet<Pair<String, Boolean>> listOfFiles = new HashSet<>();
        for (int i = 0; i < sizeOfList; i++) {
            String file = dataInputStream.readUTF();
            boolean isDir = dataInputStream.readBoolean();
            listOfFiles.add(new Pair<>(file, isDir));
        }
        return listOfFiles;
    }


    /**
     * Downloads file from server to given location.
     *
     * @param pathToFile   file to download.
     * @param saveFilePath location where to save.
     * @throws IOException if error occurred during downloading and saving the file.
     */
    void download(@NotNull Path pathToFile, @NotNull Path saveFilePath) throws IOException {
        dataOutputStream.writeInt(2);
        dataOutputStream.writeUTF(pathToFile.toAbsolutePath().toString());
        dataOutputStream.flush();
        long sizeOfFile = dataInputStream.readLong();
        if (sizeOfFile == -1) {
            throw new FileNotFoundException("No such file");
        }
        try (OutputStream fileWriter = Files.newOutputStream(Files.createFile(saveFilePath))) {
            byte[] buffer = new byte[2048];
            for (long i = 0; i < sizeOfFile; ) {
                int numberOfReadBytes = dataInputStream.read(buffer);
                i += numberOfReadBytes;
                fileWriter.write(buffer, 0, numberOfReadBytes);
            }
            fileWriter.flush();
        } catch (FileAlreadyExistsException e) {
            byte[] buffer = new byte[2048];
            for (long i = 0; i < sizeOfFile; ) {
                int numberOfReadBytes = dataInputStream.read(buffer);
                i += numberOfReadBytes;
            }
            throw e;
        }
    }

    @Override
    public void close() throws IOException {
        clientSocket.close();
    }
}
