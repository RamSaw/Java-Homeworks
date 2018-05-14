package com.mikhail.pravilov.mit.ftpClient.model;

import com.mikhail.pravilov.mit.ftp.server.FtpServer;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class FtpFileManagerTest {
    private static final int PORT = 4949;
    private static final String HOST = "localhost";
    private static final Thread serverThread = new Thread(() -> FtpServer.main(new String[]{String.valueOf(PORT)}));
    private FtpFileManager ftpFileManager;

    @Before
    public void setUp() throws Exception {
        ftpFileManager = new FtpFileManager(HOST, PORT);
    }

    @After
    public void tearDown() throws Exception {
        ftpFileManager.close();
    }

    @BeforeClass
    public static void setUpBeforeClass() throws InterruptedException {
        serverThread.setDaemon(true);
        serverThread.start();
        sleep(1000);
    }



    @Test(expected = IOException.class)
    public void testConstructorThrowsException() throws IOException {
        new FtpFileManager(HOST, 0);
    }

    @Test
    public void setGetCurrentPath() throws IOException {
        ftpFileManager.setCurrentPath(Paths.get("src/test/resources/"));
        assertEquals(Paths.get("src/test/resources/"), ftpFileManager.getCurrentPath());
        ftpFileManager.setCurrentPath(Paths.get("src/test/resources/testDir"));
        assertEquals(Paths.get("src/test/resources/testDir"), ftpFileManager.getCurrentPath());
    }

    @Test
    public void getCurrentFiles() throws IOException {
        ftpFileManager.setCurrentPath(Paths.get("src/test/resources/"));
        assertEquals(Collections.singletonList("testFileForGet"), ftpFileManager.getCurrentFiles());
    }

    @Test
    public void getCurrentDirectories() throws IOException {
        ftpFileManager.setCurrentPath(Paths.get("src/test/resources/"));
        assertEquals(Collections.singletonList("testDir"), ftpFileManager.getCurrentDirectories());
    }

    @Test
    public void goDownGoUp() throws IOException {
        ftpFileManager.setCurrentPath(Paths.get("src/test/resources/"));
        ftpFileManager.goDown("testDir");
        assertEquals(Paths.get("src/test/resources/testDir"), ftpFileManager.getCurrentPath());
        ftpFileManager.goUp();
        assertEquals(Paths.get("src/test/resources/"), ftpFileManager.getCurrentPath());
    }

    @Test
    public void copy() throws IOException {
        ftpFileManager.setCurrentPath(Paths.get("src/test/resources/"));
        ftpFileManager.copy("testFileForGet", Paths.get("src/test/resources/testDir"));
        File source = Paths.get("src/test/resources/testFileForGet").toFile();
        File copied = Paths.get("src/test/resources/testDir/testFileForGet").toFile();
        assertTrue(FileUtils.contentEquals(source, copied));
        if (!copied.delete()) {
            throw new IllegalStateException("File is not deleted");
        }
    }

    @Test
    public void closeNotFails() throws IOException {
        FtpFileManager ftpFileManager = new FtpFileManager(HOST, PORT);
        ftpFileManager.close();
    }
}