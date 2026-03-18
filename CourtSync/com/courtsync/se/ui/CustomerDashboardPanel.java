package com.courtsync.se.ui;

import com.courtsync.se.controller.CourtSyncSystem;
import com.courtsync.se.model.User;

import javax.swing.*;
import java.awt.*;

/**
 * Customer dashboard - appropriate interface for Customer role.
 */
public class CustomerDashboardPanel extends JPanel {
    private final CourtSyncSystem controller;
    private final MainFrame mainFrame;

    public CustomerDashboardPanel(CourtSyncSystem controller, MainFrame mainFrame) {
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
        String welcome = u != null ? "Welcome, " + u.getName() + "!" : "Welcome, Customer!";
        JLabel lblWelcome = new JLabel(welcome);
        lblWelcome.setFont(lblWelcome.getFont().deriveFont(Font.BOLD, 24f));
        center.add(lblWelcome, g);

        g.gridy = 1;
        JLabel lblInfo = new JLabel("You are logged in as a Customer.");
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
