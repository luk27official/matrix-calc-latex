package cz.lukaspolak.matrixcalc;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainMenu extends JFrame {
    private JPanel mainPanel;

    private JTextArea inputTextArea1;
    private JTextArea inputTextArea2;

    private MatrixPanel inputMatrixPanel1;
    private MatrixPanel inputMatrixPanel2;

    public MainMenu() {
        this.mainPanel = new JPanel();
    }

    private JPanel cards;
    private final static String PANEL1 = "LaTeX input";
    private final static String PANEL2 = "Graphic input";

    private final static String OUTPUT1 = "LaTeX";
    private final static String OUTPUT2 = "Graphic";

    private String inputType = PANEL1;
    private String outputType = OUTPUT1;

    private int heightM = 4;
    private int widthN = 3;

    private void addComponentToPane(Container pane) {
        JPanel comboBoxPane = new JPanel();
        String[] comboBoxItems = {PANEL1, PANEL2};
        JComboBox<String> cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, (String) e.getItem());
            this.inputType = (String)e.getItem();
        });
        comboBoxPane.add(cb);

        JPanel card1 = new JPanel();

        card1.setLayout(new GridLayout(1, 2));

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        Border b = BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputTextArea1 = new JTextArea();
        JScrollPane scroll1 = new JScrollPane(inputTextArea1);
        inputTextArea1.setBorder(b);
        inputTextArea1.setFont(new Font("Arial", Font.PLAIN, 20));
        inputTextArea1.setLineWrap(true);

        card1.add(scroll1);

        inputTextArea2 = new JTextArea();
        JScrollPane scroll2 = new JScrollPane(inputTextArea2);
        inputTextArea2.setBorder(b);
        inputTextArea2.setFont(new Font("Arial", Font.PLAIN, 20));
        inputTextArea2.setLineWrap(true);

        card1.add(scroll2);

        JPanel card2 = new JPanel();

        card2.setLayout(new GridLayout(1, 2));

        createMatrixInputPanels(this.heightM, this.widthN, card2);

        cards = new JPanel(new CardLayout());
        cards.add(card1, PANEL1);
        cards.add(card2, PANEL2);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(3, 5));

        JButton additionBtn = new JButton("A+B");
        additionBtn.addActionListener(e -> {
            double[][] result = Calculator.add(getFirstMatrix(), getSecondMatrix());
            displayMatrixResult(result);
        });

        JButton subtractBtn = new JButton("A-B");
        subtractBtn.addActionListener(e -> {
            double[][] result = Calculator.subtract(getFirstMatrix(), getSecondMatrix());
            displayMatrixResult(result);
        });

        JButton multiplyBtn = new JButton("A*B");
        multiplyBtn.addActionListener(e -> {
            double[][] result = Calculator.multiply(getFirstMatrix(), getSecondMatrix());
            displayMatrixResult(result);
        });

        JButton switchBtn = new JButton("Switch");
        switchBtn.addActionListener(e -> {
            if(inputType.equals(PANEL1)) {
                String tmp = inputTextArea1.getText();
                inputTextArea1.setText(inputTextArea2.getText());
                inputTextArea2.setText(tmp);
            } else {
                double[][] matrix1 = getFirstMatrix();
                double[][] matrix2 = getSecondMatrix();

                inputMatrixPanel1.setMatrix(matrix2);
                inputMatrixPanel2.setMatrix(matrix1);
            }
        });

        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> {
            inputTextArea1.setText("");
            inputTextArea2.setText("");
            inputMatrixPanel1.clearMatrix();
            inputMatrixPanel2.clearMatrix();
        });

        buttons.add(additionBtn);
        buttons.add(subtractBtn);
        buttons.add(multiplyBtn);
        buttons.add(switchBtn);
        buttons.add(clearBtn);

        JTextField mSizeTextField = new JTextField("Enter m:");
        mSizeTextField.addActionListener(e -> {
            try {
                this.heightM = Integer.parseInt(mSizeTextField.getText());
                if(this.heightM < 1) {
                    this.heightM = 1;
                }
                if(this.heightM > 15) {
                    this.heightM = 15;
                }
                createMatrixInputPanels(this.heightM, this.widthN, card2);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number format.");
            }
        });

        JTextField nSizeTextField = new JTextField("Enter n:");
        nSizeTextField.addActionListener(e -> {
            try {
                this.widthN = Integer.parseInt(nSizeTextField.getText());
                if(this.widthN < 1) {
                    this.widthN = 1;
                }
                if(this.widthN > 15) {
                    this.widthN = 15;
                }
                createMatrixInputPanels(this.heightM, this.widthN, card2);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number format.");
            }
        });

        buttons.add(mSizeTextField);
        buttons.add(nSizeTextField);
        String[] outputCBItems = {OUTPUT1, OUTPUT2};
        JComboBox<String> cb2 = new JComboBox<>(outputCBItems);
        cb2.setEditable(false);
        cb2.addItemListener(e -> {
            this.outputType = (String)e.getItem();
        });
        buttons.add(cb2);

        JButton transposeBtn = new JButton("A^T");
        transposeBtn.addActionListener(e -> {
            double[][] result = Calculator.transpose(getFirstMatrix());
            displayMatrixResult(result);
        });
        buttons.add(transposeBtn);

        JButton determinantBtn = new JButton("det(A)");
        determinantBtn.addActionListener(e -> {
            if(this.heightM != this.widthN) {
                displayErrorMessage("Matrix must be square.");
                return;
            }
            double result = Calculator.determinant(getFirstMatrix());
            displayNumberResult(result);
        });
        buttons.add(determinantBtn);

        JButton inverseBtn = new JButton("A^-1");
        inverseBtn.addActionListener(e -> {
            if(this.heightM != this.widthN) {
                displayErrorMessage("Matrix must be square.");
                return;
            }
            double[][] result = Calculator.inverse(getFirstMatrix());
            if(result == null) {
                displayErrorMessage("Matrix is not invertible (has a determinant of 0).");
                return;
            }
            displayMatrixResult(result);
        });
        buttons.add(inverseBtn);

        JButton gaussBtn = new JButton("TODO Gauss(A)");
        gaussBtn.addActionListener(e -> {
            if(this.heightM != this.widthN) {
                displayErrorMessage("Matrix must be square.");
                return;
            }
            double[][] result = Calculator.gauss(getFirstMatrix());
            displayMatrixResult(result);
        });
        buttons.add(gaussBtn);

        buttons.add(new JButton("Next"));
        buttons.add(new JButton("Next"));
        buttons.add(new JButton("Next"));

        pane.add(comboBoxPane, BorderLayout.NORTH);
        pane.add(cards, BorderLayout.CENTER);
        pane.add(buttons, BorderLayout.SOUTH);
    }

    private double[][] getFirstMatrix() {
        if(this.inputType.equals(PANEL1)) {
            return MatrixParser.parseLaTeXMatrix(inputTextArea1.getText());
        }
        return MatrixParser.parseTextFieldMatrix(inputMatrixPanel1.getMatrix());
    }

    private double[][] getSecondMatrix() {
        if(this.inputType.equals(PANEL1)) {
            return MatrixParser.parseLaTeXMatrix(inputTextArea2.getText());
        }
        return MatrixParser.parseTextFieldMatrix(inputMatrixPanel2.getMatrix());
    }

    private void createMatrixInputPanels(int m, int n, JPanel card) {
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        Border b = BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputMatrixPanel1 = new MatrixPanel(m, n);
        inputMatrixPanel2 = new MatrixPanel(m, n);

        inputMatrixPanel1.setBorder(b);
        inputMatrixPanel1.setBackground(Color.RED);

        inputMatrixPanel2.setBorder(b);
        inputMatrixPanel2.setBackground(Color.BLUE);

        card.removeAll();
        card.add(inputMatrixPanel1);
        card.add(inputMatrixPanel2);

        this.revalidate();
        this.repaint();
    }

    private void displayErrorMessage(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
    private void displayNumberResult(double result) {
        String num = String.valueOf(result);
        //if is integer, remove the .0
        if(num.endsWith(".0")) {
            num = num.substring(0, num.length() - 2);
        }
        JOptionPane.showMessageDialog(this, "The result is " + num, "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayMatrixResult(double[][] matrix) {
        if(matrix == null) {
            displayErrorMessage("Invalid input.");
            return;
        }
        MatrixPanel resultPanel = new MatrixPanel(matrix);
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        JOptionPane.showMessageDialog(this, scrollPane, "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public void createUIComponents() {
        this.setContentPane(this.mainPanel);
        this.setTitle("Main Menu");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        mainPanel.setLayout(new BorderLayout());
        addComponentToPane(mainPanel);

        //DONE 2 matice
        //DONE výpočet součtu, rozdílu, násobení

        //1 matice
        //DONE transpozice, determinant, inverze
        //výpočet ranku, Gauss. eliminace
        //umocneni, vynasobeni

        //TODO: add LaTeX parsing and output
        //TODO: how to handle fractions?
        //TODO: add unit tests for calculator?
    }
}