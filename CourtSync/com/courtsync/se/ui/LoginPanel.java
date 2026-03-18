package com.courtsync.se.ui;

import com.courtsync.se.controller.CourtSyncSystem;
import com.courtsync.se.model.User;
import com.courtsync.se.security.InputValidator;

import javax.swing.*;
import java.awt.*;

/**
 * Login panel. Displays "Invalid username or password" on failed login (UC2 Extension).
 */
public class LoginPanel extends JPanel {
    private final CourtSyncSystem controller;
    private final MainFrame mainFrame;

    public LoginPanel(CourtSyncSystem controller, MainFrame mainFrame) {
        this.controller = controller;
        this.mainFrame = mainFrame;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());

        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setBackground(Color.white);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        form.setBackground(card.getBackground());

        GridBagConstraints f = new GridBagConstraints();
        f.insets = new Insets(10, 10, 10, 10);
        f.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblEmail = new JLabel("Email:");
        JLabel lblPass = new JLabel("Password:");
        JTextField txtEmail = new JTextField(24);
        JPasswordField txtPass = new JPasswordField(24);
        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("New Account");

        f.gridx = 0; f.gridy = 0; f.weightx = 0.2; f.anchor = GridBagConstraints.LINE_END; form.add(lblEmail, f);
        f.gridx = 1; f.weightx = 0.8; f.anchor = GridBagConstraints.LINE_START; form.add(txtEmail, f);
        f.gridx = 0; f.gridy = 1; f.weightx = 0.2; f.anchor = GridBagConstraints.LINE_END; form.add(lblPass, f);
        f.gridx = 1; f.weightx = 0.8; f.anchor = GridBagConstraints.LINE_START; form.add(txtPass, f);

        f.gridx = 0; f.gridy = 2; f.gridwidth = 2; f.anchor = GridBagConstraints.CENTER;
        JPanel btnRow = new JPanel(new GridBagLayout());
        btnRow.setBackground(form.getBackground());
        GridBagConstraints b = new GridBagConstraints();
        b.insets = new Insets(12, 12, 12, 12);
        b.gridx = 0; btnLogin.setPreferredSize(new Dimension(260, 40)); btnRow.add(btnLogin, b);
        b.gridx = 1; btnRegister.setPreferredSize(new Dimension(260, 40)); btnRow.add(btnRegister, b);
        form.add(btnRow, f);

        UIStyles.styleButton(btnLogin);
        UIStyles.styleButton(btnRegister);

        card.add(form, BorderLayout.CENTER);
        add(card, BorderLayout.CENTER);

        btnRegister.addActionListener(e -> mainFrame.showCard("register"));

        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String pwd = new String(txtPass.getPassword());
            if (!InputValidator.isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!InputValidator.isValidPassword(pwd)) {
                JOptionPane.showMessageDialog(this, "Password must be at least 6 characters and include a digit", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            User u = controller.login(email, pwd);
            if (u == null) {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (u.getClass().getSimpleName().equals("Administrator")) {
                mainFrame.setCurrentUser(u);
                mainFrame.showCard("admin");
            } else {
                mainFrame.setCurrentUser(u);
                mainFrame.showCard("customer");
            }
        });
    }
}
