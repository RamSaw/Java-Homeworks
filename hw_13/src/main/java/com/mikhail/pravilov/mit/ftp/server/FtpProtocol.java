package com.mikhail.pravilov.mit.ftp.server;

import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * Implementation of FTP protocol: parses client requests and writes to given output ({@link DataOutputStream}) response data.
 */
class FtpProtocol {
    /**
     * Data output stream to client.
     */
    @NotNull
    private final DataOutputStream dataOutputStream;

    /**
     * Constructor, saves client output stream.
     *
     * @param dataOutputStream output stream to client.
     */
    FtpProtocol(@NotNull DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    /**
     * Processes client requests.
     *
     * @param request client request.
     * @throws IOException if data cannot be written to dataOutputStream.
     */
    void process(int requestType, @NotNull String request) throws IOException {
        switch (requestType) {
            case 1:
                processListRequest(request);
                break;
            case 2:
                processGetRequest(request);
                break;
        }
    }

    private void processListRequest(@NotNull String path) throws IOException {
        File folder = new File(path);
        if (!folder.exists()) {
            dataOutputStream.writeInt(-1);
            return;
        }
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            dataOutputStream.writeInt(listOfFiles.length);
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    dataOutputStream.writeUTF(listOfFile.getName());
                    dataOutputStream.writeBoolean(false);
                } else if (listOfFile.isDirectory()) {
                    dataOutputStream.writeUTF(listOfFile.getName());
                    dataOutputStream.writeBoolean(true);
                }
            }
        } else {
            dataOutputStream.writeInt(0);
        }
    }

    private void processGetRequest(@NotNull String path) throws IOException {
        File file = new File(path);
        try (InputStream fileInputStream = new FileInputStream(file)) {
            byte[] readBytes = new byte[2048];
            dataOutputStream.writeLong(file.length());
            int numberOfReadBytes;
            while ((numberOfReadBytes = fileInputStream.read(readBytes)) != -1) {
                dataOutputStream.write(readBytes, 0, numberOfReadBytes);
            }
        } catch (FileNotFoundException e) {
            dataOutputStream.writeLong(-1);
        }
    }
}
