package cz.lukaspolak.matrixcalc;

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
}
