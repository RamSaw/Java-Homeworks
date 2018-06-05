package com.mikhail.pravilov.mit.ftp.client;

import com.mikhail.pravilov.mit.ftp.server.FtpServer;
import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class FtpClientTest {
    private static Integer availablePort;
    @NotNull
    private final static String HOST_NAME = "localhost";

    @BeforeClass
    public static void SetUpServer() throws Exception {
        availablePort = getAvailablePort();
        new Thread(() -> FtpServer.main(new String[]{availablePort.toString()})).start();
        sleep(1000);
    }

    private static Integer getAvailablePort() throws IOException {
        ServerSocket serverToGetPort = new ServerSocket(0);
        Integer availablePort = serverToGetPort.getLocalPort();
        serverToGetPort.close();
        return availablePort;
    }

    @Test
    public void listDirectoryExists() throws IOException {
        FtpClient ftpClient = new FtpClient(HOST_NAME, availablePort);
        Set<Pair<String, Boolean>> expectedListResponse = new HashSet<>();
        expectedListResponse.add(new Pair<>("testDir", true));
        expectedListResponse.add(new Pair<>("testFileForGet", false));
        Set<Pair<String, Boolean>> listResponse = ftpClient.list(Paths.get("src/test/resources"));
        assertEquals(expectedListResponse, listResponse);
    }

    @Test(expected = FileNotFoundException.class)
    public void listDirectoryNotExists() throws IOException {
        FtpClient ftpClient = new FtpClient(HOST_NAME, availablePort);
        ftpClient.list(Paths.get("src/test/resources/NO_SUCH_DIRECTORY"));
    }

    @Test
    public void downloadFileExists() throws IOException {
        FtpClient ftpClient = new FtpClient(HOST_NAME, availablePort);
        Path pathTo = Paths.get("src/test/resources/testDir/downloadedFile");
        pathTo.toFile().deleteOnExit();
        Path pathFrom = Paths.get("src/test/resources/testFileForGet");
        ftpClient.download(pathFrom, pathTo);
        assertTrue(FileUtils.contentEquals(pathFrom.toFile(), pathTo.toFile()));
    }

    @Test(expected = FileNotFoundException.class)
    public void downloadFileNotExists() throws IOException {
        FtpClient ftpClient = new FtpClient(HOST_NAME, availablePort);
        ftpClient.download(Paths.get("src/test/resources/NO_SUCH_FILE"),
                Paths.get("src/test/resources/testDir/NO_SUCH_FILE"));
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void downloadFileAlreadyExists() throws IOException {
        FtpClient ftpClient = new FtpClient(HOST_NAME, availablePort);
        ftpClient.download(Paths.get("src/test/resources/testFileForGet"),
                Paths.get("src/test/resources/testDir/fileAlreadyExists"));
    }
}