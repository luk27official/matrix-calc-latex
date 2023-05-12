package cz.lukaspolak.matrixcalc;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    //TODO: add more tests, refactor

    @org.junit.jupiter.api.Test
    void rank_1() {
        double[][] matrix = new double[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};

        assertEquals(2, Calculator.rank(matrix));
    }

    @org.junit.jupiter.api.Test
    void rank_2() {
        double[][] matrix = new double[][] {
                {2, 2, -1, 5},
                {4, 5, 0, 9},
                {0, 1, 2, 2}};

        assertEquals(3, Calculator.rank(matrix));
    }

    @org.junit.jupiter.api.Test
    void rank_3() {
        double[][] matrix = new double[][] {
                {2, 2, -1, 5},
                {4, 5, 0, 9},
                {0, 1, 2, 2},
                {2, 4, 3, 7}};

        assertEquals(3, Calculator.rank(matrix));
    }

    @org.junit.jupiter.api.Test
    void gaussSquare_1() {
        double[][] matrix = new double[][] {
                {2, 2, -1, 5},
                {4, 5, 0, 9},
                {0, 1, 2, 2},
                {2, 4, 3, 7}};

        double[][] result = new double[][] {
            {1, 0, -2.5, 0},
            {0, 1, 2, 0},
            {0, 0, 0, 1},
            {0, 0, 0, 0}};

        assertArrayEquals(result, Calculator.gauss(matrix));
    }

    @org.junit.jupiter.api.Test
    void gaussSquare_2() {
        double[][] matrix = new double[][] {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}};

        double[][] result = new double[][] {
                {1, 0, -1},
                {0, 1, 2},
                {0, 0, 0}};

        assertArrayEquals(result, Calculator.gauss(matrix));
    }

    @org.junit.jupiter.api.Test
    void gauss3by4_1() {
        double[][] matrix = new double[][] {
                {2, 2, -1, 5},
                {4, 5, 0, 9},
                {0, 1, 2, 2}};

        double[][] result = new double[][] {
                {1, 0, -2.5, 0},
                {0, 1, 2, 0},
                {0, 0, 0, 1}};

        assertArrayEquals(result, Calculator.gauss(matrix));
    }

    @org.junit.jupiter.api.Test
    void gauss4by3_1() {
        double[][] matrix = new double[][] {
                {2, 2, -1},
                {4, 5, 0},
                {0, 1, 2},
                {2, 4, 3}};

        double[][] result = new double[][] {
                {1, 0, -2.5},
                {0, 1, 2},
                {0, 0, 0},
                {0, 0, 0}};

        assertArrayEquals(result, Calculator.gauss(matrix));
    }
}