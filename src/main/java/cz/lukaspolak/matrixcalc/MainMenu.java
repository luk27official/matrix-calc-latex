package cz.lukaspolak.matrixcalc;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MainMenu extends JFrame implements ItemListener {
    private JPanel mainPanel;

    private JTextArea inputTextArea1;
    private JTextArea inputTextArea2;

    private JPanel inputMatrixPanel1;
    private JPanel inputMatrixPanel2;

    public MainMenu() {
        this.mainPanel = new JPanel();
    }

    private JPanel cards;
    private final static String PANEL1 = "TEXT INPUT";
    private final static String PANEL2 = "GRAPHIC INPUT";

    private void addComponentToPane(Container pane) {
        JPanel comboBoxPane = new JPanel();
        String[] comboBoxItems = {PANEL1, PANEL2};
        JComboBox<String> cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
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

        inputMatrixPanel1 = new JPanel();
        inputMatrixPanel1.setBorder(b);
        inputMatrixPanel1.setBackground(Color.RED);

        card2.add(inputMatrixPanel1);


        inputMatrixPanel2 = new JPanel();
        inputMatrixPanel2.setBorder(b);
        inputMatrixPanel2.setBackground(Color.BLUE);

        card2.add(inputMatrixPanel2);


        cards = new JPanel(new CardLayout());
        cards.add(card1, PANEL1);
        cards.add(card2, PANEL2);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2, 5));

        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        JButton button3 = new JButton("Button 3");
        JButton button4 = new JButton("Button 4");
        JButton button5 = new JButton("Button 5");

        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);

        buttons.add(new JButton("Next"));
        buttons.add(new JButton("Next"));
        buttons.add(new JButton("Next"));
        buttons.add(new JButton("Next"));
        buttons.add(new JButton("Next"));

        pane.add(comboBoxPane, BorderLayout.NORTH);
        pane.add(cards, BorderLayout.CENTER);
        pane.add(buttons, BorderLayout.SOUTH);
    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, (String) evt.getItem());
    }

    public void createUIComponents() {
        this.setContentPane(this.mainPanel);
        this.setTitle("Main Menu");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        mainPanel.setLayout(new BorderLayout());
        addComponentToPane(mainPanel);

        //výpočet determinantu, transpozice, ranku, Gauss. eliminace, inverze
        //umocneni, vynasobeni

        //2 matice
        //výpočet součtu, rozdílu, násobení
    }
}