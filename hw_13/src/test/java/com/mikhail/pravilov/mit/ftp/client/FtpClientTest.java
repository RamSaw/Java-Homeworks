package com.mikhail.pravilov.mit.ftp.client;

import com.mikhail.pravilov.mit.ftp.server.FtpServer;
import org.junit.Test;

import java.io.*;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class FtpClientTest {
    @Test
    public void main() throws Exception {
        String filePath = new File("testData/testFileForGet").getAbsolutePath();
        String dirPath = new File("testData").getAbsolutePath();
        ByteArrayInputStream in = new ByteArrayInputStream(("list " + dirPath + "\nget " + filePath + "\n" + "exit\n").getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream outPrintStream = new PrintStream(out, true, "UTF-8");
        System.setIn(in);
        System.setOut(outPrintStream);
        new Thread(() -> FtpServer.main(new String[]{"8989"})).start();
        sleep(1000);
        FtpClient.main(new String[]{"localhost", "8989"});
        String expectedOutput = "2\ntestFileForGet FALSE\ntestDir TRUE\n4\n74 65 73 74 \n";
        assertEquals(expectedOutput, out.toString());
        System.setOut(System.out);
        System.setIn(System.in);
    }
}