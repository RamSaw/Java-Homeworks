package com.mikhail.pravilov.mit.ftp.client;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Implementation of FTP client. Sends two requests: list and get.
 */
public class FtpClientConsoleApplication {
    private final static String DOWNLOADING_DIR = "./downloadedFiles/";

    /**
     * Main function for FTP client program. Parses input, then sends requests and then outputs responses.
     *
     * @param args arguments for client program: host name and port number.
     */
    public static void main(@NotNull String[] args) {
        if (args.length != 2) {
            printUsageMessage();
            return;
        }

        String hostName = args[0];
        int portNumber;
        try {
            portNumber = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            printUsageMessage();
            return;
        }
        printCommands();
        try (FtpClient ftpClient = new FtpClient(hostName, portNumber)) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String[] params = stdIn.readLine().split(" ");

                switch (params[0]) {
                    case "exit":
                        return;
                    case "list":
                        if (params.length != 2) {
                            printBadParametersMessage();
                            continue;
                        }
                        Set<Pair<String, Boolean>> filesAndDirectories;
                        try {
                            filesAndDirectories = ftpClient.list(Paths.get(params[1]));
                        } catch (IOException e) {
                            System.out.println("IO Error during list request: " + e.getMessage());
                            continue;
                        }
                        System.out.println(filesAndDirectories.size());
                        for (Pair<String, Boolean> file : filesAndDirectories) {
                            System.out.println(file.getKey() + (file.getValue() ? " DIRECTORY" : " FILE"));
                        }
                        break;
                    case "get":
                        if (params.length != 3) {
                            printBadParametersMessage();
                            continue;
                        }
                        try {
                            ftpClient.download(Paths.get(params[1]), Paths.get(DOWNLOADING_DIR, params[2]));
                            System.out.println("File downloaded");
                        } catch (IOException e) {
                            System.out.println("IO Error during get request: " + e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Unknown command");
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
        }
    }

    private static void printUsageMessage() {
        System.err.println("Usage: java FtpClientConsoleApplication <host name> <port number>");
    }

    private static void printBadParametersMessage() {
        System.out.println("Bad params");
        printCommands();
    }

    private static void printCommands() {
        System.out.println("list <path>");
        System.out.println("get <pathToFile> <fileNameToSave>");
    }
}
