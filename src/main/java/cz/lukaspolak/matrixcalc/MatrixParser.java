package cz.lukaspolak.matrixcalc;

import javax.swing.*;
import java.util.ArrayList;

public class MatrixParser {

    public static double[][] parseLaTeXMatrix(String m) {
        if(m == null) {
            return null;
        }

        String lines[] = m.split("\\r?\\n"); //split by new line
        int beginIdx = -1, endIdx = -1;
        for(int i = 0; i < lines.length; i++) {
            if(lines[i].trim().contains("\\begin")) {
                beginIdx = i;
            } else if (lines[i].trim().contains("\\end")) {
                endIdx = i;
            }
        }

        if(beginIdx == -1 || endIdx == -1) {
            return null;
        }

        ArrayList<Double[]> listDouble = new ArrayList<>();

        for(int row = beginIdx + 1; row < endIdx; row++) {
            String line = lines[row];
            String newLine = line.trim().replace("\\\\", "");
            String[] numbers = newLine.split("&");
            Double[] rowDouble = new Double[numbers.length];
            for(int i = 0; i < numbers.length; i++) {
                try {
                    rowDouble[i] = Double.parseDouble(numbers[i]);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            listDouble.add(rowDouble);
        }

        double[][] matrix = new double[listDouble.size()][listDouble.get(0).length];
        for(int i = 0; i < listDouble.size(); i++) {
            for(int j = 0; j < listDouble.get(0).length; j++) {
                matrix[i][j] = listDouble.get(i)[j];
            }
        }

        return matrix;
    }

    public static String toLaTeXMatrix(double[][] m) {
        if(m == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\\begin{pmatrix}\n");
        for(int i = 0; i < m.length; i++) {
            for(int j = 0; j < m[0].length - 1; j++) {
                sb.append(m[i][j]).append(" & ");
            }
            sb.append(m[i][m[0].length - 1]).append("\\\\\n");
        }
        sb.append("\\end{pmatrix}");

        return sb.toString();
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
