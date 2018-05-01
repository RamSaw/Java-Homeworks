package com.mikhail.pravilov.mit.ftp.client;

import com.mikhail.pravilov.mit.ftp.server.FtpServer;
import org.junit.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class FtpClientTest {
    @Test
    public void main() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("testFileForGet")).getFile());
        String fileName = "testFileForGet";
        String dirPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - fileName.length() - 1);
        ByteArrayInputStream in = new ByteArrayInputStream(("list " + dirPath + "\nget " + file.getAbsolutePath() + "\n" + "exit\n").getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream outPrintStream = new PrintStream(out, true, "UTF-8");
        System.setIn(in);
        System.setOut(outPrintStream);
        new Thread(() -> FtpServer.main(new String[]{"8989"})).start();
        sleep(1000);
        FtpClient.main(new String[]{"localhost", "8989"});
        String expectedOutput = "2\ntestFileForGet FALSE\ntestDir TRUE\n4\n74 65 73 74 \n";
        assertEquals(expectedOutput, out.toString());
        System.setIn(System.in);
        System.setIn(System.in);
    }
}