package com.mikhail.pravilov.mit.ftp.server;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Implementation of FTP server which processes client requests (in separate thread).
 */
public class FtpServer {
    /**
     * Main method of FTP server program. Accepts clients and creates threads for each of them.
     *
     * @param args port number where to start listening.
     */
    public static void main(@NotNull String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java FtpServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientSocket.getInputStream(), clientSocket.getOutputStream());
                Thread threadForClientHandler = new Thread(clientHandler);
                threadForClientHandler.setDaemon(true);
                threadForClientHandler.start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Client handler: runs in separate thread and listens to client request and responses.
     */
    private static class ClientHandler implements Runnable {
        @NotNull
        private final Socket clientSocket;
        @NotNull
        private final DataInputStream dataInputStream;
        @NotNull
        private final DataOutputStream dataOutputStream;
        @NotNull
        private final FtpProtocol ftpProtocol;

        ClientHandler(@NotNull Socket clientSocket, @NotNull InputStream inputStream, @NotNull OutputStream outputStream) {
            this.clientSocket = clientSocket;
            dataInputStream = new DataInputStream(inputStream);
            dataOutputStream = new DataOutputStream(outputStream);
            ftpProtocol = new FtpProtocol(dataOutputStream);
        }

        @Override
        public void run() {
            try {
                while (!clientSocket.isClosed()) {
                    int requestType = dataInputStream.readInt();
                    String request = dataInputStream.readUTF();
                    ftpProtocol.process(requestType, request);
                    dataOutputStream.flush();
                }
            } catch (IOException e) {
                System.err.println("Cannot write or read to client, error: " + e.getLocalizedMessage());
            }
        }
    }
}
