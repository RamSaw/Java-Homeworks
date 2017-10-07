package com.mikhail.pravilov.mit.maybe;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MaybeTest {
    @Test
    public void just() throws Exception {
        assertEquals(5, (int)Maybe.just(5).get());
        assertEquals("Hallelujah", Maybe.just("Hallelujah").get());
    }

    @Test(expected = NullStoredValueException.class)
    public void nothing() throws Exception {
        Maybe.nothing().get();
    }

    @Test
    public void isPresent() throws Exception {
        assertTrue(Maybe.just(5).isPresent());
        assertTrue(Maybe.just("Hallelujah").isPresent());
        assertFalse(Maybe.nothing().isPresent());
    }

    @Test(expected = NullStoredValueException.class)
    public void map() throws Exception {
        assertEquals(10, (int)Maybe.just(5).map(x -> x + 5).get());
        assertEquals("Ops", Maybe.just(5).map(x -> "Ops").get());
        Maybe.nothing().map(x -> "SS").get();
    }


    /**
     * Methods opens file, wraps lines, that represent integers, in Maybe<Integer>,
     * if line is not an integer then stored value is null.
     * @param filename from where get integers.
     * @return null if exception happened during reading file otherwise array of wrapped integers and nulls.
     */
    private ArrayList<Maybe<Integer>> readIntegersInLineFromFile(String filename) {
        ArrayList<Maybe<Integer>> readLines;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            readLines = new ArrayList<>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                try {
                    readLines.add(Maybe.just(Integer.parseInt(line)));
                } catch (NumberFormatException e) {
                    readLines.add(Maybe.nothing());
                }
            }

        } catch (IOException e) {
            readLines = null;
        }

        return readLines;
    }

    /**
     * Writes integers wrapped in Maybe to file.
     * @param wrappedIntegers to write.
     * @param filename where to write.
     * @return true if no exception happened otherwise false.
     */
    private boolean writeIntegersWrappedInMaybe(ArrayList<Maybe<Integer>> wrappedIntegers, String filename) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            for (Maybe<Integer> wrappedInteger : wrappedIntegers) {
                if (wrappedInteger.isPresent()) {
                    fileWriter.write(wrappedInteger.get() + "\n");
                }
                else {
                    fileWriter.write("null\n");
                }
            }
        } catch (IOException | NullStoredValueException e) {
            return false;
        }

        return true;
    }

    @Test
    public void readWriteIntegersWrappedInMaybe() {
        ArrayList<Maybe<Integer>> wrappedIntegers = readIntegersInLineFromFile("integers.in");
        assertNotNull(wrappedIntegers);

        ArrayList<Maybe<Integer>> wrappedSquareIntegers = new ArrayList<>();
        for (Maybe<Integer> wrappedInteger : wrappedIntegers)
            wrappedSquareIntegers.add(wrappedInteger.map(x -> x * x));
        assertTrue(writeIntegersWrappedInMaybe(wrappedSquareIntegers, "wrappedIntegers.out"));
    }

}