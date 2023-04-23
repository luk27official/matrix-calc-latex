package cz.lukaspolak.matrixcalc;

public class Calculator {

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
