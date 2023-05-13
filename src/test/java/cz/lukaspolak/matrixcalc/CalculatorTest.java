package cz.lukaspolak.matrixcalc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    static double[][][] matrices = new double[][][] {
        new double[][] {{1, 2, 3}, {4, 5, 6}},
        new double[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
        new double[][] {{2, 2, -1, 5}, {4, 5, 0, 9}, {0, 1, 2, 2}},
        new double[][] {{2, 2, -1, 5}, {4, 5, 0, 9}, {0, 1, 2, 2}, {2, 4, 3, 7}},
        new double[][] {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}},
        new double[][] {{2, 2, -1}, {4, 5, 0}, {0, 1, 2}, {2, 4, 3}},
        new double[][] {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}},
        new double[][] {{2, 6, -5, 32}, {3, 9, -8, 48}, {1, 3, 2, 16}, {5, 15, -13, 80}},
        new double[][] {{0, 0, 1}, {0, 1, 0}, {1, 0, 0}},
        new double[][] {{2, 3}, {2, 2}},
        new double[][] {{ 3, 7, 2, 1 }, { 5, 4, 6, 8 }, { 9, 0, 2, 4 }, { 1, 6, 5, 3 }}
    };

    static Stream<Arguments> rankMatrixProvider() {
        return Stream.of(
            Arguments.of(matrices[0], 2),
            Arguments.of(matrices[1], 2),
            Arguments.of(matrices[2], 3),
            Arguments.of(matrices[3], 3),
            Arguments.of(matrices[4], 2),
            Arguments.of(matrices[5], 2),
            Arguments.of(matrices[6], 1)
        );
    }

    @ParameterizedTest
    @MethodSource("rankMatrixProvider")
    void rankTests(double[][] matrix, int expectedRank) {
        assertEquals(expectedRank, Calculator.rank(matrix));
    }

    static Stream<Arguments> transposeMatrixProvider() {
        return Stream.of(
            Arguments.of(matrices[0], new double[][] {{1, 4}, {2, 5}, {3, 6}}),
            Arguments.of(matrices[1], new double[][] {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}}),
            Arguments.of(matrices[2], new double[][] {{2, 4, 0}, {2, 5, 1}, {-1, 0, 2}, {5, 9, 2}}),
            Arguments.of(matrices[3], new double[][] {{2, 4, 0, 2}, {2, 5, 1, 4}, {-1, 0, 2, 3}, {5, 9, 2, 7}}),
            Arguments.of(matrices[4], new double[][] {{0, 3, 6}, {1, 4, 7}, {2, 5, 8}}),
            Arguments.of(matrices[5], new double[][] {{2, 4, 0, 2}, {2, 5, 1, 4}, {-1, 0, 2, 3}}),
            Arguments.of(matrices[6], new double[][] {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}})
        );
    }

    @ParameterizedTest
    @MethodSource("transposeMatrixProvider")
    void transposeTests(double[][] matrix, double[][] expectedMatrix) {
        assertArrayEquals(expectedMatrix, Calculator.transpose(matrix));
    }

    static Stream<Arguments> multiplyMatrixProvider() {
        return Stream.of(
            Arguments.of(matrices[0], matrices[0], null),
            Arguments.of(matrices[0], matrices[2], new double[][] {{10, 15, 5, 29}, {28, 39, 8, 77}}),
            Arguments.of(matrices[4], matrices[2], new double[][] {{4, 7, 4, 13}, {22, 31, 7, 61}, {40, 55, 10, 109}}),
            Arguments.of(matrices[6], matrices[4], new double[][] {{9, 12, 15}, {18, 24, 30}, {27, 36, 45}}),
            Arguments.of(matrices[0], matrices[3], null)
        );
    }

    @ParameterizedTest
    @MethodSource("multiplyMatrixProvider")
    void multiplyTests(double[][] matrix1, double[][] matrix2, double[][] expectedMatrix) {
        assertArrayEquals(expectedMatrix, Calculator.multiply(matrix1, matrix2));
    }

    static Stream<Arguments> subtractMatrixProvider() {
        return Stream.of(
            Arguments.of(matrices[0], matrices[0], new double[][] {{0, 0, 0}, {0, 0, 0}}),
            Arguments.of(matrices[0], matrices[1], null),
            Arguments.of(matrices[0], matrices[2], null),
            Arguments.of(matrices[1], matrices[4], new double[][] {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}),
            Arguments.of(matrices[3], matrices[7], new double[][] {{0, -4, 4, -27}, {1, -4, 8, -39}, {-1, -2, 0, -14}, {-3, -11, 16, -73}}),
            Arguments.of(matrices[0], matrices[4], null),
            Arguments.of(matrices[0], matrices[3], null)
        );
    }

    @ParameterizedTest
    @MethodSource("subtractMatrixProvider")
    void subtractTests(double[][] matrix1, double[][] matrix2, double[][] expectedMatrix) {
        assertArrayEquals(expectedMatrix, Calculator.subtract(matrix1, matrix2));
    }

    static Stream<Arguments> addMatrixProvider() {
        return Stream.of(
            Arguments.of(matrices[0], matrices[0], new double[][] {{2, 4, 6}, {8, 10, 12}}),
            Arguments.of(matrices[0], matrices[1], null),
            Arguments.of(matrices[0], matrices[2], null),
            Arguments.of(matrices[1], matrices[4], new double [][] {{1, 3, 5}, {7, 9, 11}, {13, 15, 17}}),
            Arguments.of(matrices[3], matrices[7], new double [][] {{4, 8, -6, 37}, {7, 14, -8, 57}, {1, 4, 4, 18}, {7, 19, -10, 87}}),
            Arguments.of(matrices[0], matrices[4], null),
            Arguments.of(matrices[0], matrices[3], null)
        );
    }

    @ParameterizedTest
    @MethodSource("addMatrixProvider")
    void addTests(double[][] matrix1, double[][] matrix2, double[][] expectedMatrix) {
        assertArrayEquals(expectedMatrix, Calculator.add(matrix1, matrix2));
    }

    static Stream<Arguments> determinantMatrixProvider() {
        return Stream.of(
            Arguments.of(matrices[0], 0),
            Arguments.of(matrices[1], 0),
            Arguments.of(matrices[2], 0),
            Arguments.of(matrices[3], 0),
            Arguments.of(matrices[4], 0),
            Arguments.of(matrices[5], 0),
            Arguments.of(matrices[6], 0),
            Arguments.of(matrices[7], 0),
            Arguments.of(matrices[8], -1),
            Arguments.of(matrices[9], -2),
            Arguments.of(matrices[10], -628)
        );
    }

    @ParameterizedTest
    @MethodSource("determinantMatrixProvider")
    void determinantTests(double[][] matrix, double expectedDeterminant) {
        assertEquals(expectedDeterminant, Calculator.determinant(matrix));
    }

    static Stream<Arguments> inverseMatrixProvider() {
        return Stream.of(
            Arguments.of(matrices[0], null),
            Arguments.of(matrices[1], null),
            Arguments.of(matrices[2], null),
            Arguments.of(matrices[3], null),
            Arguments.of(matrices[4], null),
            Arguments.of(matrices[5], null),
            Arguments.of(matrices[6], null),
            Arguments.of(matrices[7], null),
            Arguments.of(matrices[8], new double[][] {{-0.0, 0, 1}, {0, 1, 0}, {1, 0, -0.0}}),
            Arguments.of(matrices[9], new double[][] {{-1, 1.5}, {1, -1}}),
            Arguments.of(matrices[10], new double[][] {{2d/157, -31d/314, 49d/314, 8d/157}, {34d/157, 45d/628, -61d/628, -21d/157}, {-55d/157, -179d/628, 131d/628, 94d/157}, {23d/157, 229d/628, -129d/628, -65d/157}})
        );
    }

    @ParameterizedTest
    @MethodSource("inverseMatrixProvider")
    void inverseTests(double[][] matrix, double[][] expectedMatrix) {
        assertArrayEquals(expectedMatrix, Calculator.inverse(matrix));
    }

    static Stream<Arguments> gaussEliminationTests() {
        return Stream.of(
            Arguments.of(matrices[0], new double[][] {{1, 0, -1}, {0, 1, 2}}),
            Arguments.of(matrices[1], new double[][] {{1, 0, -1}, {0, 1, 2}, {0, 0, 0}}),
            Arguments.of(matrices[2], new double[][] {{1, 0, -2.5, 0}, {0, 1, 2, 0}, {0, 0, 0, 1}}),
            Arguments.of(matrices[3], new double[][] {{1, 0, -2.5, 0}, {0, 1, 2, 0}, {0, 0, 0, 1}, {0, 0, 0, 0}}),
            Arguments.of(matrices[4], new double[][] {{1, 0, -1}, {0, 1, 2}, {0, 0, 0}}),
            Arguments.of(matrices[5], new double[][] {{1, 0, -2.5}, {0, 1, 2}, {0, 0, 0}, {0, 0, 0}}),
            Arguments.of(matrices[6], new double[][] {{1, 1, 1}, {0, 0, 0}, {0, 0, 0}}),
            Arguments.of(matrices[7], new double[][] {{1, 3, 0, 16}, {0, 0, 1, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}),
            Arguments.of(matrices[8], new double[][] {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}),
            Arguments.of(matrices[9], new double[][] {{1, 0}, {0, 1}}),
            Arguments.of(matrices[10], new double[][] {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}})
        );
    }

    @ParameterizedTest
    @MethodSource("gaussEliminationTests")
    void gaussEliminationTests(double[][] matrix, double[][] expectedMatrix) {
        assertArrayEquals(expectedMatrix, Calculator.gauss(matrix));
    }


    static Stream<Arguments> scalarMultiplicationTests() {
        return Stream.of(
            Arguments.of(matrices[0], 2, new double[][] {{2, 4, 6}, {8, 10, 12}}),
            Arguments.of(matrices[0], 0, new double[][] {{0, 0, 0}, {0, 0, 0}}),
            Arguments.of(matrices[1], 2, new double[][] {{2, 4, 6}, {8, 10, 12}, {14, 16, 18}}),
            Arguments.of(matrices[2], -2, new double[][] {{-4, -4, 2, -10}, {-8, -10, -0.0, -18}, {-0.0, -2, -4, -4}}),
            Arguments.of(matrices[8], 1d/2, new double[][] {{0, 0, 1d/2}, {0, 1d/2, 0}, {1d/2, 0, 0}}),
            Arguments.of(matrices[9], 1, new double[][] {{2, 3}, {2, 2}})
        );
    }

    @ParameterizedTest
    @MethodSource("scalarMultiplicationTests")
    void scalarMultiplicationTests(double[][] matrix, double scalar, double[][] expectedMatrix) {
        assertArrayEquals(expectedMatrix, Calculator.scalarMultiply(matrix, scalar));
    }

    static Stream<Arguments> scalarAddTests() {
        return Stream.of(
            Arguments.of(matrices[0], 2, new double[][] {{3, 4, 5}, {6, 7, 8}}),
            Arguments.of(matrices[0], 0, new double[][] {{1, 2, 3}, {4, 5, 6}}),
            Arguments.of(matrices[2], 2, new double[][] {{4, 4, 1, 7}, {6, 7, 2, 11}, {2, 3, 4, 4}}),
            Arguments.of(matrices[2], -2, new double[][] {{0, 0, -3, 3}, {2, 3, -2, 7}, {-2, -1, 0, 0}}),
            Arguments.of(matrices[8], 1d/2, new double[][] {{1d/2, 1d/2, 3d/2}, {1d/2, 3d/2, 1d/2}, {3d/2, 1d/2, 1d/2}}),
            Arguments.of(matrices[9], 1, new double[][] {{3, 4}, {3, 3}})
        );
    }

    @ParameterizedTest
    @MethodSource("scalarAddTests")
    void scalarAddTests(double[][] matrix, double scalar, double[][] expectedMatrix) {
        assertArrayEquals(expectedMatrix, Calculator.scalarAdd(matrix, scalar));
    }

    static Stream<Arguments> exponentTests() {
        return Stream.of(
            Arguments.of(matrices[0], 2, null),
            Arguments.of(matrices[0], 1, null),
            Arguments.of(matrices[1], 2, new double[][] {{30, 36, 42}, {66, 81, 96}, {102, 126, 150}}),
            Arguments.of(matrices[2], 2, null),
            Arguments.of(matrices[2], -2, null),
            Arguments.of(matrices[9], 3, new double[][] {{44, 54}, {36, 44}}),
            Arguments.of(matrices[6], 0, null),
            Arguments.of(matrices[5], 0, null)
        );
    }

    @ParameterizedTest
    @MethodSource("exponentTests")
    void exponentTests(double[][] matrix, int exponent, double[][] expectedMatrix) {
        assertArrayEquals(expectedMatrix, Calculator.exponent(matrix, exponent));
    }

    static Stream<Arguments> doubleToFractionTests() {
        return Stream.of(
            Arguments.of(0.5, new double[] {1, 2}),
            Arguments.of(0, new double[] {0, 1}),
            Arguments.of(1.25, new double[] {5, 4}),
            Arguments.of(-0.4, new double[] {-2, 5}),
            Arguments.of(-12.1, new double[] {-121, 10})
        );
    }

    @ParameterizedTest
    @MethodSource("doubleToFractionTests")
    void doubleToFractionTests(double decimal, double[] expectedFraction) {
        assertArrayEquals(expectedFraction, Calculator.doubleToFraction(decimal));
    }
}