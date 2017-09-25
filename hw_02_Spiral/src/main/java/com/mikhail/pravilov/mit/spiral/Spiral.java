package com.mikhail.pravilov.mit.spiral;

import java.util.Arrays;

/** Class to store matrix that allows to print it in spiral and sort by column first elements. */
public class Spiral {
    private int[][] matrix;

    /**
     * Constructor for Spiral class. Checks if size of given matrix isn't even and it is square matrix.
     * @param matrix matrix to store in class
     */
    public Spiral(int[][] matrix) throws IllegalArgumentException {
        if (matrix.length % 2 == 0 ||
                (matrix.length > 0 && matrix[0].length % 2 == 0))
            throw new IllegalArgumentException("Matrix height or width is even.");
        for (int[] row : matrix)
            if (row.length != matrix.length)
                throw new IllegalArgumentException("Matrix isn't square.");

        this.matrix = matrix;
    }


    /**
     * Method prints the border in spiral order starting from (x, y - 1), where x and y is coordinates of right top corner
     * @param x x coordinate of right top corner
     * @param y y coordinate of right top corner
     * @param side width and height of square
     */
    private void printSquareBorder(int x, int y, int side) throws IndexOutOfBoundsException{
        if (side == 1) {
            System.out.print(matrix[y][x] + " ");
            return;
        }

        for (int offset = 1; offset < side; offset++)
            System.out.print(matrix[y + offset][x] + " ");

        for (int offset = 1; offset < side; offset++)
            System.out.print(matrix[y + side - 1][x - offset] + " ");

        for (int offset = side - 2; offset >= 0; offset--)
            System.out.print(matrix[y + offset][x - (side - 1)] + " ");

        for (int offset = side - 2; offset >= 0; offset--)
            System.out.print(matrix[y][x - offset] + " ");
    }

    /** Prints matrix in spiral starting in center */
    public void print() {
        for (int side = 1, x = (matrix.length - 1) / 2, y = x; side <= matrix.length; side += 2, x++, y--) {
            printSquareBorder(x, y, side);
        }
    }

    private void transpose() {
        int[][] transposed = new int[matrix.length][matrix.length];

        for (int i = 0; i < transposed.length; i++)
            for (int j = 0; j < transposed.length; j++)
                transposed[i][j] = matrix[j][i];
        matrix = transposed;
    }

    /** Sorts elements in matrix by first column elements in matrix. */
    public void sortColumn() {
        transpose();
        Arrays.sort(matrix, (column1, column2) -> column1[0] - column2[0]);
        transpose();
    }


    /**
     * @param row
     * @param column
     * @return element in specific row and column
     * @throws IndexOutOfBoundsException
     */
    public int getElement(int row, int column) throws IndexOutOfBoundsException {
        if (row < 0 || row >= matrix.length || column < 0 || column >= matrix[row].length)
            throw new IndexOutOfBoundsException("No such element in matrix");

        return matrix[row][column];
    }
}