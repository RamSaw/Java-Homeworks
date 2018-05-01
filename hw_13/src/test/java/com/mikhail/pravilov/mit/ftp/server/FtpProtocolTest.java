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

        String fileName = "testFileForGet";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        String dirPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - fileName.length() - 1);

        ftpProtocol.process("list " + dirPath);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(out.toByteArray()));
        assertEquals(2, dataInputStream.readInt());
        assertEquals("testFileForGet", dataInputStream.readUTF());
        assertFalse(dataInputStream.readBoolean());
        assertEquals("testDir", dataInputStream.readUTF());
        assertTrue(dataInputStream.readBoolean());
        assertEquals(-1, dataInputStream.read());
    }

    @Test
    public void processGet() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FtpProtocol ftpProtocol = new FtpProtocol(new DataOutputStream(out));
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("testFileForGet")).getFile());
        ftpProtocol.process("get " + file.getAbsolutePath());
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(out.toByteArray()));
        assertEquals(4, dataInputStream.readLong());
        assertEquals(116, dataInputStream.readByte());
        assertEquals(101, dataInputStream.readByte());
        assertEquals(115, dataInputStream.readByte());
        assertEquals(116, dataInputStream.readByte());
        assertEquals(-1, dataInputStream.read());
    }
}