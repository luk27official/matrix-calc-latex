package cz.lukaspolak.matrixcalc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MatrixPanel extends JPanel {

    private JTextField[][] matrix;

    public JTextField[][] getMatrix() {
        return this.matrix;
    }

    public void setMatrix(double[][] matrix) {
        if(matrix == null) {
            return;
        }
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                String num = String.valueOf(matrix[i][j]);
                //if is integer, remove the .0
                if(num.endsWith(".0")) {
                    num = num.substring(0, num.length() - 2);
                }
                this.matrix[i][j].setText(num);
            }
        }
    }

    public void clearMatrix() {
        for(int i = 0; i < this.matrix.length; i++) {
            for(int j = 0; j < this.matrix[0].length; j++) {
                this.matrix[i][j].setText("");
            }
        }
    }

    public MatrixPanel(double[][] matrix) {
        super();

        this.setLayout(new GridLayout(matrix.length, matrix[0].length));
        this.matrix = new JTextField[matrix.length][matrix[0].length];

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                String num = matrix[i][j] + "";
                if(num.endsWith(".0")) {
                    num = num.substring(0, num.length() - 2);
                }
                JTextField textField = new JTextField(num);
                textField.setFont(new Font("Arial", Font.PLAIN, 20));
                textField.setHorizontalAlignment(JTextField.CENTER);
                textField.setEditable(false);
                this.add(textField);
                this.matrix[i][j] = textField;
            }
        }
    }

    public MatrixPanel(int m, int n) {
        super();

        this.setLayout(new GridLayout(m, n));
        this.matrix = new JTextField[m][n];

        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                String number = i * m + j + "";
                JTextField textField = new JTextField(number);
                textField.setFont(new Font("Arial", Font.PLAIN, 20));
                textField.setHorizontalAlignment(JTextField.CENTER);

                //allow only numbers, dot and minus
                textField.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyTyped(java.awt.event.KeyEvent evt) {
                        char c = evt.getKeyChar();
                        if (!((c >= '0') && (c <= '9') || (c == '.') || (c == '-') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                            evt.consume();
                        }
                    }
                });

                this.add(textField);
                this.matrix[i][j] = textField;
            }
        }
    }
}
