package com.mikhail.pravilov.mit.ftp.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Implementation of FTP client. Sends two requests: list and get.
 */
public class FtpClient {
    /**
     * Main function for FTP client program. Parses input, then sends requests and then outputs responses.
     * @param args arguments for client program: host name and port number.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println(
                    "Usage: java FtpClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket clientSocket = new Socket(hostName, portNumber);
                DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String command = stdIn.readLine();
                String[] params = command.split(" ");
                switch (params[0]) {
                    case "exit":
                        return;
                    case "list":
                        dataOutputStream.writeUTF(command);
                        dataOutputStream.flush();
                        int sizeOfList = dataInputStream.readInt();
                        System.out.println(sizeOfList);
                        for (int i = 0; i < sizeOfList; i++) {
                            String file = dataInputStream.readUTF();
                            boolean isDir = dataInputStream.readBoolean();
                            System.out.println(file + (isDir ? " TRUE" : " FALSE"));
                        }
                        break;
                    case "get":
                        dataOutputStream.writeUTF(command);
                        dataOutputStream.flush();
                        long sizeOfFile = dataInputStream.readLong();
                        System.out.println(sizeOfFile);
                        for (long i = 0; i < sizeOfFile; i++) {
                            System.out.print(String.format("%02X ", dataInputStream.readByte()));
                        }
                        System.out.print("\n");
                        break;
                    default:
                        System.out.println("Unknown command");
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}
