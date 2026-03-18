package com.courtsync.se.ui;

import com.courtsync.se.controller.CourtSyncSystem;
import com.courtsync.se.model.User;

import javax.swing.*;
import java.awt.*;

/**
 * Main application frame with CardLayout for login, register, customer, admin.
 */
public class MainFrame extends JFrame {
    private final CardLayout cards = new CardLayout();
    private final JPanel cardPanel = new JPanel(cards);
    private final CourtSyncSystem controller = new CourtSyncSystem();
    private User currentUser;

    private final LoginPanel loginPanel;
    private final RegistrationPanel registrationPanel;
    private final CustomerDashboardPanel customerPanel;
    private final AdminDashboardPanel adminPanel;

    public MainFrame() {
        super("CourtSync SE - User Registration & Authentication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(UIStyles.createBanner("CourtSync SE - User Registration & Authentication"), BorderLayout.NORTH);

        loginPanel = new LoginPanel(controller, this);
        registrationPanel = new RegistrationPanel(controller, this);
        customerPanel = new CustomerDashboardPanel(controller, this);
        adminPanel = new AdminDashboardPanel(controller, this);

        cardPanel.add(loginPanel, "login");
        cardPanel.add(registrationPanel, "register");
        cardPanel.add(customerPanel, "customer");
        cardPanel.add(adminPanel, "admin");

        add(cardPanel, BorderLayout.CENTER);
    }

    public void showCard(String name) {
        cards.show(cardPanel, name);
    }

    public void setCurrentUser(User u) { this.currentUser = u; }
    public User getCurrentUser() { return currentUser; }
}
