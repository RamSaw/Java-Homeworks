package com.mikhail.pravilov.mit.ftpClient.model;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Interface that describes must-have methods for any file manager.
 */
public interface FileManager {
    /**
     * Sets current path and receives all information about this new path.
     *
     * @param path new current path.
     * @throws IOException if error occurred during receiving information.
     */
    void setCurrentPath(@NotNull Path path) throws IOException;

    /**
     * Getter for current path.
     *
     * @return current path.
     */
    @NotNull
    Path getCurrentPath();

    /**
     * Getter for list of files in current directory.
     *
     * @return files in current directory specified by current path.
     */
    @NotNull
    List<String> getCurrentFiles();

    /**
     * Getter for list of directories in current directory.
     *
     * @return directories in current directory specified by current path.
     */
    @NotNull
    List<String> getCurrentDirectories();

    /**
     * Goes down in child directory in files tree.
     *
     * @param directoryName to go down.
     * @throws IOException if error occurred during getting info about down path.
     */
    void goDown(String directoryName) throws IOException;

    /**
     * Goes up in parent directory in files tree.
     *
     * @throws IOException if error occurred during getting info about parent path.
     */
    void goUp() throws IOException;

    /**
     * Copies file in current path to given path.
     *
     * @param file name in current directory (path).
     * @param destinationDirectory where to copy the file.
     * @throws IOException if error occurred during copying.
     */
    void copy(@NotNull String file, @NotNull Path destinationDirectory) throws IOException;
}
