package cz.lukaspolak.matrixcalc;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * This class provides the main menu of the application. It extends the JFrame class and contains all the components.
 */
public class MainMenu extends JFrame {

    /**
     * A constructor for the MainMenu class. Creates a new JPanel.
     */
    public MainMenu() {
        this.mainPanel = new JPanel();
    }

    /**
     * The main JPanel of the application. Contains all the other components.
     */
    private final JPanel mainPanel;

    /**
     * The first text area for matrix input.
     */
    private JTextArea inputTextArea1;

    /**
     * The second text area for matrix input.
     */
    private JTextArea inputTextArea2;

    /**
     * The first matrix input panel. Contains the matrix values.
     */
    private MatrixPanel inputMatrixPanel1;

    /**
     * The second matrix input panel. Contains the matrix values.
     */
    private MatrixPanel inputMatrixPanel2;

    /**
     * JPanel that allows the user to choose the input format.
     */
    private JPanel cards;

    /**
     * First input type.
     */
    private final static String PANEL1 = "LaTeX input";

    /**
     * Second input type.
     */
    private final static String PANEL2 = "Graphic input";

    /**
     * First output type.
     */
    private final static String OUTPUT1 = "LaTeX";

    /**
     * Second output type.
     */
    private final static String OUTPUT2 = "Graphic";

    /**
     * First number output type.
     */
    private final static String OUT_NUMBERS = "Numbers";

    /**
     * Second number output type.
     */
    private final static String OUT_FRACTIONS = "Fractions";

    /**
     * Contains the current input type.
     */
    private String inputType = PANEL1;

    /**
     * Contains the current output type.
     */
    private String outputType = OUTPUT1;

    /**
     * Contains the current number output type.
     */
    private String outputNumberType = OUT_FRACTIONS;

    /**
     * The height of the first matrix.
     */
    private int heightM = 4;

    /**
     * The width of the first matrix.
     */
    private int widthN = 3;

    /**
     * The current value of the scalar (provided by the user).
     */
    private double scalar = 1;

    /**
     * This method creates the components of the application and adds them to the main pane.
     * @param pane the main pane
     */
    private void addComponentsToPane(Container pane) {
        // input type switch
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

        // textareas for input
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

        // matrix panels for input
        JPanel card2 = new JPanel();
        card2.setLayout(new GridLayout(1, 2));

        createMatrixInputPanels(this.heightM, this.widthN, card2);

        cards = new JPanel(new CardLayout());
        cards.add(card1, PANEL1);
        cards.add(card2, PANEL2);

        // buttons panel
        // most of the buttons have an action listener that calls a method from the Calculator class
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(4, 5));

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

        JButton gaussBtn = new JButton("Gauss(A)");
        gaussBtn.addActionListener(e -> {
            double[][] result = Calculator.gauss(getFirstMatrix());
            displayMatrixResult(result);
        });
        buttons.add(gaussBtn);

        JButton rankBtn = new JButton("rank(A)");
        rankBtn.addActionListener(e -> {
            if(this.heightM != this.widthN) {
                displayErrorMessage("Matrix must be square.");
                return;
            }
            int result = Calculator.rank(getFirstMatrix());
            displayNumberResult(result);
        });
        buttons.add(rankBtn);

        buttons.add(new JButton(""));
        JButton scalarMultiplyBtn = new JButton("A*scalar");
        scalarMultiplyBtn.addActionListener(e -> {
            double[][] result = Calculator.scalarMultiply(getFirstMatrix(), this.scalar);
            displayMatrixResult(result);
        });
        buttons.add(scalarMultiplyBtn);

        JButton scalarAddBtn = new JButton("A+scalar");
        scalarAddBtn.addActionListener(e -> {
            double[][] result = Calculator.scalarAdd(getFirstMatrix(), this.scalar);
            displayMatrixResult(result);
        });
        buttons.add(scalarAddBtn);

        JButton exponentBtn = new JButton("A^scalar");
        exponentBtn.addActionListener(e -> {
            double[][] result = Calculator.exponent(getFirstMatrix(), this.scalar);
            displayMatrixResult(result);
        });
        buttons.add(exponentBtn);
        buttons.add(new JButton(""));


        String[] outputNumberFormatOptions = {OUT_FRACTIONS, OUT_NUMBERS};
        JComboBox<String> outputNumberFormatCB = new JComboBox<>(outputNumberFormatOptions);
        outputNumberFormatCB.setEditable(false);
        outputNumberFormatCB.addItemListener(e -> this.outputNumberType = (String)e.getItem());
        buttons.add(outputNumberFormatCB);

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
        buttons.add(mSizeTextField);

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
        buttons.add(nSizeTextField);

        JTextField scalarTextField = new JTextField("Enter scalar:");
        scalarTextField.addActionListener(e -> {
            try {
                this.scalar = Double.parseDouble(scalarTextField.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number format.");
            }
        });
        buttons.add(scalarTextField);

        String[] outputCBItems = {OUTPUT1, OUTPUT2};
        JComboBox<String> cb2 = new JComboBox<>(outputCBItems);
        cb2.setEditable(false);
        cb2.addItemListener(e -> this.outputType = (String)e.getItem());
        buttons.add(cb2);

        pane.add(comboBoxPane, BorderLayout.NORTH);
        pane.add(cards, BorderLayout.CENTER);
        pane.add(buttons, BorderLayout.SOUTH);
    }

    /**
     * Returns the matrix from the active input panel. If the input is invalid, returns null.
     * @param textArea the text area to parse the matrix from
     * @param matrixPanel the matrix panel to parse the matrix from
     * @return the matrix from the active input panel
     */
    private double[][] getMatrix(JTextArea textArea, MatrixPanel matrixPanel) {
        if(this.inputType.equals(PANEL1)) {
            double[][] result = MatrixParser.parseLaTeXMatrix(textArea.getText());
            if(result == null) {
                return null;
            }
            this.heightM = result.length;
            this.widthN = result[0].length;
            return result;
        }
        return MatrixParser.parseTextFieldMatrix(matrixPanel.getMatrix());
    }

    /**
     * Returns the value of the first matrix (on the left).
     * @return the value of the first matrix
     */
    private double[][] getFirstMatrix() {
        return getMatrix(inputTextArea1, inputMatrixPanel1);
    }

    /**
     * Returns the value of the second matrix (on the right).
     * @return the value of the second matrix
     */
    private double[][] getSecondMatrix() {
        return getMatrix(inputTextArea2, inputMatrixPanel2);
    }

    /**
     * Creates two MatrixPanel instances and adds them to the given card.
     * @param m the height of the matrix (number of rows)
     * @param n the width of the matrix (number of columns)
     * @param card the card to add the MatrixPanels to
     */
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

    /**
     * Displays an error message.
     * @param error the error message to display
     */
    private void displayErrorMessage(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays the result of a number operation.
     * @param result the result of the operation
     */
    private void displayNumberResult(double result) {
        String num = String.valueOf(result);
        //if is integer, remove the .0
        if(num.endsWith(".0")) {
            num = num.substring(0, num.length() - 2);
        }
        JOptionPane.showMessageDialog(this, "The result is " + num, "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays the result of a matrix operation.
     * @param matrix the result of the operation
     */
    private void displayMatrixResult(double[][] matrix) {
        if(matrix == null) {
            displayErrorMessage("Invalid input.");
            return;
        }
        switch(outputType) {
            case OUTPUT1 -> displayLaTeXMatrixResult(matrix);
            case OUTPUT2 -> displayGraphicMatrixResult(matrix);
        }
    }

    /**
     * Displays the result of a matrix operation in a LaTeX format.
     * @param matrix the result of the operation
     */
    private void displayLaTeXMatrixResult(double[][] matrix) {
        String result = MatrixParser.toLaTeXMatrix(matrix, this.outputNumberType.equals(OUT_FRACTIONS));
        JTextArea resultTextArea = new JTextArea(result);
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("Arial", Font.PLAIN, 20));

        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays the result of a matrix operation in a graphic format.
     * @param matrix the result of the operation
     */
    private void displayGraphicMatrixResult(double[][] matrix) {
        MatrixPanel resultPanel = new MatrixPanel(matrix);
        if(this.outputNumberType.equals(OUT_FRACTIONS)) {
            resultPanel = resultPanel.withFractions();
        }
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        JOptionPane.showMessageDialog(this, scrollPane, "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Creates the UI components. Should be called after initializing this class.
     */
    public void createUIComponents() {
        this.setContentPane(this.mainPanel);
        this.setTitle("Matrix Parser");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null); // center the window on the screen

        mainPanel.setLayout(new BorderLayout());
        addComponentsToPane(mainPanel);
    }
}