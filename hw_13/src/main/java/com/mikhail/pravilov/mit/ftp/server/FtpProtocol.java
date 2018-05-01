package com.mikhail.pravilov.mit.ftp.server;

import java.io.*;

/**
 * Implementation of FTP protocol: parses client requests and writes to given output ({@link DataOutputStream}) response data.
 */
class FtpProtocol {
    /**
     * Data output stream to client.
     */
    private DataOutputStream dataOutputStream;

    /**
     * Constructor, saves client output stream.
     * @param dataOutputStream output stream to client.
     */
    FtpProtocol(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    /**
     * Processes client requests.
     * @param request client request.
     * @throws IOException if data cannot be written to dataOutputStream.
     */
    void process(String request) throws IOException {
        String[] params = request.split(" ");
        switch (params[0]) {
            case "list":
                processListRequest(params[1]);
                break;
            case "get":
                processGetRequest(params[1]);
                break;
        }
    }

    private void processListRequest(String path) throws IOException {
        File folder = new File(path);
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
        }
        else {
            dataOutputStream.writeInt(0);
        }
    }

    private void processGetRequest(String path) throws IOException {
        File file = new File(path);
        try (InputStream fileInputStream = new FileInputStream(file)) {
            byte[] readBytes = new byte[2048];
            dataOutputStream.writeLong(file.length());
            int numberOfReadBytes;
            while ((numberOfReadBytes = fileInputStream.read(readBytes)) != -1) {
                dataOutputStream.write(readBytes, 0, numberOfReadBytes);
            }
        } catch (FileNotFoundException e) {
            dataOutputStream.writeLong(0);
        }
    }
}
