package com.mikhail.pravilov.mit.ftpClient.model;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that implements {@link FileManager} getting files and directories from external server via ftp.
 */
public class FtpFileManager implements FileManager, AutoCloseable {
    /**
     * Current path where file manager is located now.
     */
    private Path currentPath;
    /**
     * Ftp client to execute requests.
     */
    @NotNull
    private FtpClient ftpClient;
    /**
     * Files in current directory (current path).
     */
    private List<String> currentFiles;
    /**
     * Directories in current directory (current path).
     */
    private List<String> currentDirectories;

    /**
     * Creates ftp client and establishes connection.
     *
     * @param host of the server to connect.
     * @param port of the server to connect.
     * @throws IOException if error occurred during establishing connection.
     */
    public FtpFileManager(@NotNull String host, int port) throws IOException {
        ftpClient = new FtpClient(host, port);
        ftpClient.open();
    }

    /**
     * Updates currentFiles and currentDirectories sending list request.
     * Should be called if current path was changed or long time passed since last update.
     *
     * @throws IOException if error occurred during list request.
     */
    private void update() throws IOException {
        List<Pair<String, Boolean>> filesAndDirectories = ftpClient.list(currentPath);
        currentFiles = filesAndDirectories.stream().filter(stringBooleanPair -> !stringBooleanPair.getValue()).
                map(Pair::getKey).collect(Collectors.toList());
        currentDirectories = filesAndDirectories.stream().filter(Pair::getValue).
                map(Pair::getKey).collect(Collectors.toList());
    }

    @Override
    public void setCurrentPath(@NotNull Path path) throws IOException {
        currentPath = path;
        update();
    }

    @Override
    @NotNull
    public Path getCurrentPath() {
        return currentPath;
    }

    @Override
    @NotNull
    public List<String> getCurrentFiles() {
        return currentFiles;
    }

    @Override
    @NotNull
    public List<String> getCurrentDirectories() {
        return currentDirectories;
    }

    @Override
    public void goDown(String directoryName) throws IOException {
        setCurrentPath(getDownPath(currentPath, directoryName));
    }

    @Override
    public void goUp() throws IOException {
        Path parent = currentPath.getParent();
        if (parent != null) {
            setCurrentPath(parent);
        }
    }

    @Override
    public void copy(@NotNull String file, @NotNull Path destinationDirectory) throws IOException {
        ftpClient.download(getDownPath(currentPath, file), getDownPath(destinationDirectory, file));
    }

    /**
     * Returns child path in files tree.
     *
     * @param path parent path.
     * @param fileName child filename (can be a path).
     * @return path representing the child.
     */
    @NotNull
    private Path getDownPath(@NotNull Path path, @NotNull String fileName) {
        return Paths.get(path.toString(), fileName);
    }

    @Override
    public void close() throws IOException {
        ftpClient.close();
    }
}
