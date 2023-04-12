package cz.lukaspolak.matrixcalc;

import javax.swing.*;
public class MainMenu extends JFrame {
    private JPanel mainPanel;

    public MainMenu() {
        this.mainPanel = new JPanel();
    }

    public void createUIComponents() {
        this.setContentPane(this.mainPanel);
        this.setTitle("Main Menu");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}