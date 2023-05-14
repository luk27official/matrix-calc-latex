package cz.lukaspolak.matrixcalc;

import javax.swing.*;
import java.util.ArrayList;

/**
 * This class provides methods for parsing matrices from LaTeX format and JTextField[][] matrix.
 */
public class MatrixParser {

    /**
     * Private constructor to prevent instantiation without parameters.
     */
    MatrixParser() {
        //private constructor to prevent instantiation without parameters
    }

    /**
     * Parses matrix from LaTeX format.
     * @param m LaTeX matrix
     * @return matrix
     */
    public static double[][] parseLaTeXMatrix(String m) {
        if(m == null) {
            return null;
        }

        String[] lines = m.split("\\r?\\n"); //split by new line
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
        try {
            for(int row = beginIdx + 1; row < endIdx; row++) {
                String line = lines[row];
                String newLine = line.trim().replace("\\\\", "");
                String[] numbers = newLine.split("&");
                Double[] rowDouble = new Double[numbers.length];
                for(int i = 0; i < numbers.length; i++) {
                    // handle fractions
                    if(numbers[i].contains("\\frac")) {
                        String[] fraction = numbers[i].split("\\{");
                        if(fraction.length != 3) {
                            return null;
                        }
                        String firstNum = fraction[1].replace("}", "");
                        String secondNum = fraction[2].replace("}", "");
                        rowDouble[i] = Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
                    }
                    else {
                        rowDouble[i] = Double.parseDouble(numbers[i]);
                    }
                }
                listDouble.add(rowDouble);
            }
        } catch (NumberFormatException e) {
            return null;
        }


        double[][] matrix = new double[listDouble.size()][listDouble.get(0).length];
        for(int i = 0; i < listDouble.size(); i++) {
            for(int j = 0; j < listDouble.get(0).length; j++) {
                matrix[i][j] = listDouble.get(i)[j];
            }
        }

        return matrix;
    }

    /**
     * Converts matrix to LaTeX format.
     * @param m matrix
     * @param withFractions if true, format numbers as fractions
     * @return LaTeX matrix
     */
    public static String toLaTeXMatrix(double[][] m, boolean withFractions) {
        if(m == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\\begin{pmatrix}\n");
        for (double[] doubles : m) {
            for (int j = 0; j < m[0].length - 1; j++) {
                //if the number is not an integer, format it as a fraction
                if (withFractions && doubles[j] % 1 != 0) {
                    double[] fraction = Calculator.doubleToFraction(doubles[j]);
                    sb.append("\\frac{").append(fraction[0]).append("}{").append(fraction[1]).append("}").append(" & ");
                } else {
                    sb.append((int)doubles[j]).append(" & ");
                }
            }
            if (withFractions && doubles[m[0].length - 1] % 1 != 0) {
                double[] fraction = Calculator.doubleToFraction(doubles[m[0].length - 1]);
                sb.append("\\frac{").append(fraction[0]).append("}{").append(fraction[1]).append("}").append("\\\\\n");
            } else {
                sb.append((int)doubles[m[0].length - 1]).append("\\\\\n");
            }
        }
        sb.append("\\end{pmatrix}");

        return sb.toString();
    }

    /**
     * Parses matrix from JTextField[][] matrix and converts it to a double[][] matrix.
     * @param m matrix in JTextField[][] format
     * @return matrix in double[][] format
     */
    public static double[][] parseTextFieldMatrix(JTextField[][] m) {
        double[][] matrix = new double[m.length][m[0].length];

        try {
            for(int i = 0; i < m.length; i++) {
                for(int j = 0; j < m[0].length; j++) {
                    String text = m[i][j].getText();
                    // handle fractions
                    if(text.contains("/")) {
                        String[] fraction = text.split("/");
                        if(fraction.length != 2) {
                            return null;
                        }
                        matrix[i][j] = Double.parseDouble(fraction[0]) / Double.parseDouble(fraction[1]);
                    }
                    else {
                        matrix[i][j] = Double.parseDouble(text);
                    }
                }
            }
        } catch (NumberFormatException e) {
            return null;
        }

        return matrix;
    }
}
