package com.mikhail.pravilov.mit.ftp.server;

import org.junit.Test;

import java.io.*;
import java.util.Objects;

import static org.junit.Assert.*;

public class FtpProtocolTest {
    @Test
    public void processGet() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FtpProtocol ftpProtocol = new FtpProtocol(new DataOutputStream(out));
        File file = new File("testData/testFileForGet");
        ftpProtocol.process("get " + file.getAbsolutePath());
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(out.toByteArray()));
        assertEquals(4, dataInputStream.readLong());
        assertEquals(116, dataInputStream.readByte());
        assertEquals(101, dataInputStream.readByte());
        assertEquals(115, dataInputStream.readByte());
        assertEquals(116, dataInputStream.readByte());
        assertEquals(-1, dataInputStream.read());
    }

    @Test
    public void processList() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FtpProtocol ftpProtocol = new FtpProtocol(new DataOutputStream(out));

        String fileName = "testData";
        File file = new File(fileName);
        ftpProtocol.process("list " + file.getAbsolutePath());
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(out.toByteArray()));
        assertEquals(2, dataInputStream.readInt());
        assertEquals("testFileForGet", dataInputStream.readUTF());
        assertFalse(dataInputStream.readBoolean());
        assertEquals("testDir", dataInputStream.readUTF());
        assertTrue(dataInputStream.readBoolean());
        assertEquals(-1, dataInputStream.read());
    }
}