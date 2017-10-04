package com.mikhail.pravilov.mit.spiral;

import com.mikhail.pravilov.mit.spiral.Spiral;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpiralTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private void assertMatrixEquals(int[][] expectedMatrix, Spiral spiral) {
        for (int row = 0; row < expectedMatrix.length; row++)
            for (int column = 0; column < expectedMatrix[row].length; column++)
                assertEquals(expectedMatrix[row][column], spiral.getElement(row, column));
    }

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    void matrixIllegalSize() {
        int[][] illegalMatrix0on0 = {{}};
        assertThrows(IllegalArgumentException.class, () -> new Spiral(illegalMatrix0on0));

        int[][] illegalMatrix2on2 = {{1, 2},
                {3, 4}};
        assertThrows(IllegalArgumentException.class, () -> new Spiral(illegalMatrix2on2));

        int[][] illegalMatrix2on1 = {{1, 2}};
        assertThrows(IllegalArgumentException.class, () -> new Spiral(illegalMatrix2on1));

        int[][] illegalMatrix1on2 = {{1},
                {2}};
        assertThrows(IllegalArgumentException.class, () -> new Spiral(illegalMatrix1on2));
    }

    @Test
    void print5on5() {
        int[][] matrix = {{21, 22, 23, 24, 25},
                {20, 7, 8, 9, 10},
                {19, 6, 1, 2, 11},
                {18, 5, 4, 3, 12},
                {17, 16, 15, 14, 13}};
        Spiral spiral = new Spiral(matrix);
        spiral.print();
        assertEquals("1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 ", outContent.toString());
    }

    @Test
    void print3on3() {
        int[][] matrix = {{7, 8, 9},
                {6, 1, 2},
                {5, 4, 3}};
        Spiral spiral = new Spiral(matrix);
        spiral.print();
        assertEquals("1 2 3 4 5 6 7 8 9 ", outContent.toString());
    }

    @Test
    void print1on1() {
        int[][] matrix = {{0}};
        Spiral spiral = new Spiral(matrix);
        spiral.print();
        assertEquals("0 ", outContent.toString());
    }

    @Test
    void sortColumn3on3() {
        int[][] correctMatrix = {{0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}};
        int[][] matrix = {{2, 0, 1},
                {5, 3, 4},
                {8, 6, 7}};

        Spiral spiral = new Spiral(matrix);
        spiral.sortColumn();
        assertMatrixEquals(correctMatrix, spiral);
    }

    @Test
    void sortColumn1on1() {
        int[][] correctMatrix = {{0}};
        int[][] matrix = {{0}};

        Spiral spiral = new Spiral(matrix);
        spiral.sortColumn();
        assertMatrixEquals(correctMatrix, spiral);
    }

}