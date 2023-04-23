package cz.lukaspolak.matrixcalc;

import javax.swing.*;

public class MatrixParser {

    public static double[][] parseLaTeXMatrix(String m) {
        // TODO: add parsing
        return null;
    }

    public static double[][] parseTextFieldMatrix(JTextField[][] m) {
        double[][] matrix = new double[m.length][m[0].length];

        try {
            for(int i = 0; i < m.length; i++) {
                for(int j = 0; j < m[0].length; j++) {
                    matrix[i][j] = Double.parseDouble(m[i][j].getText());
                }
            }
        } catch (NumberFormatException e) {
            return null;
        }

        return matrix;
    }

}
