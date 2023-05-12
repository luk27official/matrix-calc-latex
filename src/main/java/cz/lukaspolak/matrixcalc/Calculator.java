package cz.lukaspolak.matrixcalc;

import java.util.ArrayList;

public class Calculator {

    public static double[][] transpose(double[][] m) {
        if(m == null) {
            return null;
        }

        double[][] result = new double[m[0].length][m.length];

        for(int i = 0; i < m[0].length; i++) {
            for(int j = 0; j < m.length; j++) {
                result[i][j] = m[j][i];
            }
        }

        return result;
    }

    public static double[][] multiply(double[][] m1, double[][] m2) {
        if(m1 == null || m2 == null) {
            return null;
        }

        double[][] result = new double[m1.length][m2[0].length];

        for(int i = 0; i < m1.length; i++) {
            for(int j = 0; j < m2[0].length; j++) {
                for(int k = 0; k < m1[0].length; k++) {
                    result[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }

        return result;
    }

    public static double[][] subtract(double[][] m1, double[][] m2) {
        if(m1 == null || m2 == null) {
            return null;
        }

        for(int i = 0; i < m1.length; i++) {
            for(int j = 0; j < m1[0].length; j++) {
                m1[i][j] -= m2[i][j];
            }
        }
        return m1;
    }

    public static double[][] add(double[][] m1, double[][] m2) {
        if(m1 == null || m2 == null) {
            return null;
        }

        for(int i = 0; i < m1.length; i++) {
            for(int j = 0; j < m1[0].length; j++) {
                m1[i][j] += m2[i][j];
            }
        }
        return m1;
    }

    public static double determinant(double[][] m) {
        if(m == null) {
            return 0;
        }

        if(m.length == 1) {
            return m[0][0];
        }

        if(m.length == 2) {
            return m[0][0] * m[1][1] - m[0][1] * m[1][0];
        }

        double result = 0;

        for(int i = 0; i < m.length; i++) {
            double[][] tmp = new double[m.length - 1][m.length - 1];

            for(int j = 1; j < m.length; j++) {
                for(int k = 0; k < m.length; k++) {
                    if(k < i) {
                        tmp[j - 1][k] = m[j][k];
                    } else if(k > i) {
                        tmp[j - 1][k - 1] = m[j][k];
                    }
                }
            }

            result += m[0][i] * Math.pow(-1, i) * determinant(tmp);
        }

        return result;
    }

    public static double[][] inverse(double[][] m) {
        if(m == null) {
            return null;
        }

        double det = determinant(m);

        if(det == 0) {
            return null;
        }

        double[][] result = new double[m.length][m.length];

        for(int i = 0; i < m.length; i++) {
            for(int j = 0; j < m.length; j++) {
                double[][] tmp = new double[m.length - 1][m.length - 1];

                for(int k = 0; k < m.length; k++) {
                    for(int l = 0; l < m.length; l++) {
                        if(k < i && l < j) {
                            tmp[k][l] = m[k][l];
                        } else if(k < i && l > j) {
                            tmp[k][l - 1] = m[k][l];
                        } else if(k > i && l < j) {
                            tmp[k - 1][l] = m[k][l];
                        } else if(k > i && l > j) {
                            tmp[k - 1][l - 1] = m[k][l];
                        }
                    }
                }

                result[j][i] = Math.pow(-1, i + j) * determinant(tmp) / det;
            }
        }

        return result;
    }

    public static double[][] gauss(double[][] m) {
        if(m == null) {
            return null;
        }

        int numRows = m.length;
        int numCols = m[0].length;

        double[][] result = new double[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                result[i][j] = m[i][j];
            }
        }

        // Gaussian elimination with partial pivoting
        for (int col = 0; col < numCols; col++) {
            // find the row with the largest absolute value in the current column
            int maxRow = col;
            if(col == numRows) { // for rectangular matrices
                break;
            }
            double maxValue = Math.abs(result[col][col]);
            for (int row = col + 1; row < numRows; row++) {
                double value = Math.abs(result[row][col]);
                if (value > maxValue) {
                    maxRow = row;
                    maxValue = value;
                }
            }

            // if all values in this column are zero, move on to the next column
            if (maxValue == 0) {
                continue;
            }

            // swap the current row with the row containing the largest absolute value
            if (maxRow != col) {
                double[] tempRow = result[col];
                result[col] = result[maxRow];
                result[maxRow] = tempRow;
            }

            // perform row operations to eliminate all values below the pivot element
            double pivot = result[col][col];
            for (int row = col + 1; row < numRows; row++) {
                double factor = result[row][col] / pivot;
                for (int col2 = col; col2 < numCols; col2++) {
                    result[row][col2] -= factor * result[col][col2];
                }
            }
        }

        // convert the result matrix to reduced row echelon form (RREF)
        for (int row = numRows - 1; row >= 0; row--) {
            // find the pivot element in this row
            int pivotCol = -1;
            for (int col = 0; col < numCols; col++) {
                if (result[row][col] != 0) {
                    pivotCol = col;
                    break;
                }
            }

            // if no pivot element was found, move on to the next row
            if (pivotCol == -1) {
                continue;
            }

            // divide the pivot row by the pivot element to make it equal to 1
            double pivot = result[row][pivotCol];
            for (int col = pivotCol; col < numCols; col++) {
                result[row][col] /= pivot;
            }

            // subtract the pivot row from all rows above it to eliminate all values above the pivot element
            for (int row2 = row - 1; row2 >= 0; row2--) {
                double factor = result[row2][pivotCol];
                for (int col = pivotCol; col < numCols; col++) {
                    result[row2][col] -= factor * result[row][col];
                }
            }
        }

        // make sure that all non-zero rows are above any rows of all zeros
        ArrayList<double[]> nonZeroRows = new ArrayList<>();
        for(int i = 0; i < numRows; i++) {
            boolean allZero = true;
            for(int j = 0; j < numCols; j++) {
                if(Math.abs(result[i][j]) > 1e-10) {
                    allZero = false;
                    break;
                }
            }
            if(!allZero) {
                nonZeroRows.add(result[i]);
            }
        }

        result = new double[numRows][numCols];
        for(int i = 0; i < nonZeroRows.size(); i++) {
            result[i] = nonZeroRows.get(i);
        }

        return result;
    }

    public static int rank(double[][] m) {
        if(m == null) {
            return 0;
        }

        double reduced[][] = gauss(m);
        int rank = 0;

        for (int i = 0; i < reduced.length; i++) {
            boolean allZero = true;
            for (int j = 0; j < reduced[0].length; j++) {
                if (reduced[i][j] != 0) {
                    allZero = false;
                    break;
                }
            }
            if (!allZero) {
                rank++;
            }
        }

        return rank;
    }

    public static double[][] scalarMultiply(double[][] m, double scalar) {
        if(m == null) {
            return null;
        }

        double[][] result = new double[m.length][m[0].length];

        for(int i = 0; i < m.length; i++) {
            for(int j = 0; j < m[0].length; j++) {
                result[i][j] = m[i][j] * scalar;
            }
        }

        return result;
    }

    public static double[][] scalarAdd(double[][] m, double scalar) {
        double[][] result = new double[m.length][m[0].length];

        for(int i = 0; i < m.length; i++) {
            for(int j = 0; j < m[0].length; j++) {
                result[i][j] = m[i][j] + scalar;
            }
        }

        return result;
    }

    public static double[][] exponent(double[][] m, double scalar) {
        if(m == null) {
            return null;
        }

        double[][] result = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                result[i][j] = m[i][j];
            }
        }

        for(int i = 0; i < scalar - 1; i++) {
            result = multiply(result, m);
        }

        return result;
    }
}
