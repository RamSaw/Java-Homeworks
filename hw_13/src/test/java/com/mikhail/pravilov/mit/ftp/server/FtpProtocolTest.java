package com.mikhail.pravilov.mit.ftp.server;

import org.junit.Test;

import java.io.*;
import java.util.Objects;

import static org.junit.Assert.*;

public class FtpProtocolTest {
    @Test
    public void processList() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FtpProtocol ftpProtocol = new FtpProtocol(new DataOutputStream(out));
        File dir = new File("src/test/resources");
        ftpProtocol.process(1, dir.getAbsolutePath());
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(out.toByteArray()));
        assertEquals(2, dataInputStream.readInt());
        String read = dataInputStream.readUTF();
        switch (read) {
            case "testFileForGet":
                assertEquals("testFileForGet", read);
                assertFalse(dataInputStream.readBoolean());
                assertEquals("testDir", dataInputStream.readUTF());
                assertTrue(dataInputStream.readBoolean());
                break;
            case "testDir":
                assertEquals("testDir", read);
                assertTrue(dataInputStream.readBoolean());
                assertEquals("testFileForGet", dataInputStream.readUTF());
                assertFalse(dataInputStream.readBoolean());
                break;
            default:
                assertTrue(read.equals("testFileForGet") || read.equals("testDir"));
        }
        assertEquals(-1, dataInputStream.read());
    }

    @Test
    public void processGet() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FtpProtocol ftpProtocol = new FtpProtocol(new DataOutputStream(out));
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("testFileForGet")).getFile());
        ftpProtocol.process(2, file.getAbsolutePath());
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(out.toByteArray()));
        assertEquals(4, dataInputStream.readLong());
        assertEquals(116, dataInputStream.readByte());
        assertEquals(101, dataInputStream.readByte());
        assertEquals(115, dataInputStream.readByte());
        assertEquals(116, dataInputStream.readByte());
        assertEquals(-1, dataInputStream.read());
    }
}