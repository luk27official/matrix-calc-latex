package cz.lukaspolak.matrixcalc;

public class Calculator {

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
}
