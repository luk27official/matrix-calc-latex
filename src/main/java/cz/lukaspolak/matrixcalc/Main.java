package cz.lukaspolak.matrixcalc;

/**
 * Main class of the application.
 */
public class Main {

    /**
     * An empty constructor of the class.
     */
    public Main() {}

    /**
     * Main method of the application. Creates the main menu. Serves as an entry point of the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.createUIComponents();
        });
    }
}