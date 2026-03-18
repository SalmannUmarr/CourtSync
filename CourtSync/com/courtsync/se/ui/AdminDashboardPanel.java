package com.courtsync.se.ui;

import com.courtsync.se.controller.CourtSyncSystem;
import com.courtsync.se.model.User;

import javax.swing.*;
import java.awt.*;

/**
 * Admin dashboard - appropriate interface for Administrator role.
 */
public class AdminDashboardPanel extends JPanel {
    private final CourtSyncSystem controller;
    private final MainFrame mainFrame;

    public AdminDashboardPanel(CourtSyncSystem controller, MainFrame mainFrame) {
        this.controller = controller;
        this.mainFrame = mainFrame;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground((Color) UIManager.get("App.cardBackground"));

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground((Color) UIManager.get("App.cardBackground"));
        center.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.gridx = 0; g.gridy = 0;

        User u = mainFrame.getCurrentUser();
        String welcome = u != null ? "Welcome, " + u.getName() + "!" : "Welcome, Administrator!";
        JLabel lblWelcome = new JLabel(welcome);
        lblWelcome.setFont(lblWelcome.getFont().deriveFont(Font.BOLD, 24f));
        center.add(lblWelcome, g);

        g.gridy = 1;
        JLabel lblInfo = new JLabel("You are logged in as an Administrator.");
        center.add(lblInfo, g);

        g.gridy = 2;
        JButton btnLogout = new JButton("Logout");
        UIStyles.styleButton(btnLogout);
        center.add(btnLogout, g);

        add(center, BorderLayout.CENTER);

        btnLogout.addActionListener(e -> {
            mainFrame.setCurrentUser(null);
            mainFrame.showCard("login");
        });
    }
}
