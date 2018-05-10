package com.mikhail.pravilov.mit.ftpClient.model;

import com.mikhail.pravilov.mit.ftp.server.FtpServer;
import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class FtpClientTest {
    private static final int PORT = 4949;
    private static final String HOST = "localhost";
    private static final Thread serverThread = new Thread(() -> FtpServer.main(new String[]{String.valueOf(PORT)}));
    private FtpClient ftpClient;

    @Before
    public void setUp() throws Exception {
        ftpClient = new FtpClient(HOST, PORT);
        ftpClient.open();
    }

    @After
    public void tearDown() throws Exception {
        ftpClient.close();
    }

    @BeforeClass
    public static void setUpBeforeClass() throws InterruptedException {
        serverThread.setDaemon(true);
        serverThread.start();
        sleep(1000);
    }

    @Test
    public void openNotFails() throws IOException {
        FtpClient ftpClient = new FtpClient(HOST, PORT);
        ftpClient.open();
    }

    @Test(expected = IOException.class)
    public void openInvalidPortThrowsException() throws IOException {
        FtpClient ftpClient = new FtpClient(HOST, 0);
        ftpClient.open();
    }

    @Test(expected = IOException.class)
    public void openInvalidHostThrowsException() throws IOException {
        FtpClient ftpClient = new FtpClient("NO_SUCH_HOST", PORT);
        ftpClient.open();
    }

    @Test
    public void closeNotFails() throws IOException {
        FtpClient ftpClient = new FtpClient(HOST, PORT);
        ftpClient.open();
        ftpClient.close();
    }

    @Test
    public void list() throws IOException {
        List<Pair<String, Boolean>> expected = Arrays.asList(new Pair<>("testDir", true), new Pair<>("testFileForGet", false));
        List<Pair<String, Boolean>> result = ftpClient.list(Paths.get("src/test/resources"));
        assertTrue(expected.containsAll(result));
        assertTrue(result.containsAll(expected));
    }

    @Test
    public void download() throws IOException {
        ftpClient.download(Paths.get("src/test/resources/testFileForGet"), Paths.get("src/test/resources/testDir/testFileForGet"));
        File source = Paths.get("src/test/resources/testFileForGet").toFile();
        File copied = Paths.get("src/test/resources/testDir/testFileForGet").toFile();
        copied.deleteOnExit();
        assertTrue(FileUtils.contentEquals(source, copied));
    }
}