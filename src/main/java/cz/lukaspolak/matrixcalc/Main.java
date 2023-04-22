package cz.lukaspolak.matrixcalc;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.createUIComponents();
        });

        System.out.println("Main menu loaded.");
    }
}