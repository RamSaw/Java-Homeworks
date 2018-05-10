package com.mikhail.pravilov.mit.ftpClient.model;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of FTP client. Sends two requests: list and get.
 */
class FtpClient {
    /**
     * Server name.
     */
    @NotNull
    private String host;
    /**
     * Server's port.
     */
    private int port;
    /**
     * Socket for communication with server.
     */
    private Socket clientSocket;
    /**
     * Input from server.
     */
    private DataInputStream dataInputStream;
    /**
     * Output to server.
     */
    private DataOutputStream dataOutputStream;

    FtpClient(@NotNull String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Connects to server and opens streams for reading and writing.
     *
     * @throws IOException if connection or opening fails.
     */
    void open() throws IOException {
        clientSocket = new Socket(host, port);
        dataInputStream = new DataInputStream(clientSocket.getInputStream());
        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
    }

    /**
     * Closes read and write streams and connection with server.
     *
     * @throws IOException if something cannot be closed.
     */
    void close() throws IOException {
        dataOutputStream.close();
        dataInputStream.close();
        clientSocket.close();
    }

    /**
     * Returns list of all files and directories in path. {@link Boolean} indicator is used to distinguish file and directory.
     *
     * @param path where to look for files and directories.
     * @return list of files and directories.
     * @throws IOException if error occurred during getting list of files and directories.
     */
    @NotNull
    List<Pair<String, Boolean>> list(@NotNull Path path) throws IOException {
        String command = "list " + path.toAbsolutePath().toString();
        dataOutputStream.writeUTF(command);
        dataOutputStream.flush();
        int sizeOfList = dataInputStream.readInt();
        List<Pair<String, Boolean>> listOfFiles = new LinkedList<>();
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
     * @param pathToFile file to download.
     * @param saveFilePath location where to save.
     * @throws IOException if error occurred during downloading and saving the file.
     */
    void download(@NotNull Path pathToFile, @NotNull Path saveFilePath) throws IOException {
        String command = "get " + pathToFile.toAbsolutePath().toString();
        dataOutputStream.writeUTF(command);
        dataOutputStream.flush();
        long sizeOfFile = dataInputStream.readLong();
        OutputStream fileWriter = Files.newOutputStream(Files.createFile(saveFilePath));
        byte[] buffer = new byte[2048];
        for (long i = 0; i < sizeOfFile;) {
            int numberOfReadBytes = dataInputStream.read(buffer);
            i += numberOfReadBytes;
            fileWriter.write(buffer, 0, numberOfReadBytes);
        }
        fileWriter.flush();
    }
}
