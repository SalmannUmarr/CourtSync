package com.courtsync.se;

import com.courtsync.se.datastore.DataStore;
import com.courtsync.se.ui.MainFrame;
import com.courtsync.se.ui.UIStyles;

import javax.swing.*;

/**
 * CourtSync SE - Standalone User Registration & Authentication.
 * Entry point.
 */
public class Main {
    public static void main(String[] args) {
        DataStore.getInstance();
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            try {
                UIStyles.apply();
            } catch (Exception ignored) {}
            MainFrame mf = new MainFrame();
            mf.setVisible(true);
        });
    }
}
