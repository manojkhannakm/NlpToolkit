package com.manojkhannakm.nlptoolkit;

import com.manojkhannakm.nlptoolkit.commandline.CommandLine;
import com.manojkhannakm.nlptoolkit.swing.Swing;

import javax.swing.*;

/**
 * @author Manoj Khanna
 */

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
                e.printStackTrace();
            }

            SwingUtilities.invokeLater(Swing::getInstance);
        } else {
            CommandLine.getInstance().parse(args);
        }
    }

}
