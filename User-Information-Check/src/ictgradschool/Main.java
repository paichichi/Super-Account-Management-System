package ictgradschool;

import ictgradschool.ui.UserPanel;

import javax.swing.*;

public class Main extends JFrame{

    /**
     * the function will start here.......
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserPanel::new);
    }

}
