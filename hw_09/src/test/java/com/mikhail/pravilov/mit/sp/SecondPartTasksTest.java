package com.mikhail.pravilov.mit.sp;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() throws IOException {
        List<String> rightAnswer = new ArrayList<>();

        File textFile = File.createTempFile("textFile", null);
        textFile.deleteOnExit();

        // textFile
        PrintWriter writer = new PrintWriter(textFile);

        String line = "asd kek sad x";
        writer.println(line);
        rightAnswer.add(line);

        line = "asdkek27389grewoiuadsfjcxkvnlscxjvnpi;sdjklx'zcm";
        writer.println(line);
        rightAnswer.add(line);

        line = "nothing";
        writer.println(line);

        line = "";
        writer.println(line);
        writer.flush();

        // emptyFile
        File emptyFile = File.createTempFile("emptyFile", null);
        emptyFile.deleteOnExit();

        // binaryFile
        File binaryFile = File.createTempFile("binaryFile", null);
        binaryFile.deleteOnExit();

        FileOutputStream fileOutputStream = new FileOutputStream(binaryFile);
        line = "sdplfkkek asdxc\n";
        fileOutputStream.write(line.getBytes());
        rightAnswer.add(line.substring(0, line.length() - 1));

        line = "noKEK\n";
        fileOutputStream.write(line.getBytes());

        line = "ss\n";
        fileOutputStream.write(line.getBytes());
        fileOutputStream.flush();

        String quoteToFind = "kek";

        assertEquals(rightAnswer, SecondPartTasks.findQuotes(Arrays.asList(textFile.getAbsolutePath(),
                emptyFile.getAbsolutePath(), binaryFile.getAbsolutePath()), quoteToFind));
    }

    @Test
    public void testPiDividedBy4() {
        Double precision = 0.001, expected = Math.PI / 4;
        Double testResult = SecondPartTasks.piDividedBy4();

        assertTrue(testResult <= expected + precision);
        assertTrue(testResult >= expected - precision);
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> compositions = new HashMap<>();
        compositions.put("Olya", Arrays.asList("asf", "asfldf", "as"));
        compositions.put("Sasha", Collections.emptyList());
        compositions.put("Galya", Arrays.asList("asfdasklfgjdkslhsd;jkhsdjkhsgkjsdhgfkjsdhfas"));
        compositions.put("Kukushka", Arrays.asList("a"));

        assertEquals("Galya", SecondPartTasks.findPrinter(compositions));
    }

    @Test
    public void testCalculateGlobalOrder() {
        List<Map<String, Integer>> orders = new ArrayList<>();

        Map<String, Integer> shop1 = new HashMap<>();
        shop1.put("Coca", 2);
        shop1.put("Beluga", 1);
        shop1.put("Tar-tar", 0);

        Map<String, Integer> shop2 = new HashMap<>();
        shop2.put("Coca", 2);
        shop2.put("Beluga", 1);
        shop2.put("Sandwich", 10);

        Map<String, Integer> shop3 = new HashMap<>();
        shop3.put("Coca", 200);
        shop3.put("Beluga", 1);
        shop3.put("Sandwich", 10);

        Map<String, Integer> shop4 = new HashMap<>();
        shop4.put("Cocos", 199);

        Map<String, Integer> shop5 = new HashMap<>();

        orders.add(shop1);
        orders.add(shop2);
        orders.add(shop3);
        orders.add(shop4);
        orders.add(shop5);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("Coca", 204);
        expected.put("Beluga", 3);
        expected.put("Sandwich", 20);
        expected.put("Cocos", 199);
        expected.put("Tar-tar", 0);

        assertEquals(expected, SecondPartTasks.calculateGlobalOrder(orders));
    }
}