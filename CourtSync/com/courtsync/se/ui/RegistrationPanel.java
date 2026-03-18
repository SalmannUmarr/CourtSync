package com.courtsync.se.ui;

import com.courtsync.se.controller.CourtSyncSystem;
import com.courtsync.se.model.Customer;
import com.courtsync.se.security.InputValidator;

import javax.swing.*;
import java.awt.*;

/**
 * Registration panel. UC1: Validates data, checks duplicate email, shows
 * success or error via JOptionPane.
 */
public class RegistrationPanel extends JPanel {
    private final CourtSyncSystem controller;
    private final MainFrame mainFrame;

    public RegistrationPanel(CourtSyncSystem controller, MainFrame mainFrame) {
        this.controller = controller;
        this.mainFrame = mainFrame;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());

        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setBackground((Color) UIManager.get("App.cardBackground"));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        form.setBackground(card.getBackground());

        JLabel lblName = new JLabel("Name:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblPhone = new JLabel("Phone:");
        JLabel lblPass = new JLabel("Password:");
        JTextField txtName = new JTextField(24);
        JTextField txtEmail = new JTextField(24);
        JTextField txtPhone = new JTextField(24);
        JPasswordField txtPass = new JPasswordField(24);
        JButton btnRegister = new JButton("Register");
        JButton btnBack = new JButton("Back");

        GridBagConstraints f = new GridBagConstraints();
        f.insets = new Insets(8, 8, 8, 8);
        f.fill = GridBagConstraints.HORIZONTAL;
        f.gridx = 0; f.gridy = 0; f.weightx = 0.2; f.anchor = GridBagConstraints.LINE_END; form.add(lblName, f);
        f.gridx = 1; f.weightx = 0.8; f.anchor = GridBagConstraints.LINE_START; form.add(txtName, f);
        f.gridx = 0; f.gridy = 1; f.weightx = 0.2; f.anchor = GridBagConstraints.LINE_END; form.add(lblEmail, f);
        f.gridx = 1; f.weightx = 0.8; f.anchor = GridBagConstraints.LINE_START; form.add(txtEmail, f);
        f.gridx = 0; f.gridy = 2; f.weightx = 0.2; f.anchor = GridBagConstraints.LINE_END; form.add(lblPhone, f);
        f.gridx = 1; f.weightx = 0.8; f.anchor = GridBagConstraints.LINE_START; form.add(txtPhone, f);
        f.gridx = 0; f.gridy = 3; f.weightx = 0.2; f.anchor = GridBagConstraints.LINE_END; form.add(lblPass, f);
        f.gridx = 1; f.weightx = 0.8; f.anchor = GridBagConstraints.LINE_START; form.add(txtPass, f);

        f.gridx = 0; f.gridy = 4; f.gridwidth = 2;
        JPanel btnRow = new JPanel(new GridBagLayout());
        btnRow.setBackground(form.getBackground());
        GridBagConstraints b = new GridBagConstraints();
        b.insets = new Insets(12, 12, 12, 12);
        b.gridx = 0; btnRegister.setPreferredSize(new Dimension(260, 40)); btnRow.add(btnRegister, b);
        b.gridx = 1; btnBack.setPreferredSize(new Dimension(260, 40)); btnRow.add(btnBack, b);
        form.add(btnRow, f);

        UIStyles.styleButton(btnRegister);
        UIStyles.styleButton(btnBack);

        card.add(form, BorderLayout.CENTER);
        add(card, BorderLayout.CENTER);

        btnBack.addActionListener(e -> mainFrame.showCard("login"));

        btnRegister.addActionListener(e -> {
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String pwd = new String(txtPass.getPassword());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name required", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!InputValidator.isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Valid email required", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (phone != null && !phone.isEmpty() && !InputValidator.isValidPhone(phone)) {
                JOptionPane.showMessageDialog(this, "Invalid phone format", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!InputValidator.isValidPassword(pwd)) {
                JOptionPane.showMessageDialog(this, "Password must be at least 6 characters and include a digit", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Customer cst = controller.registerCustomer(name, email, phone, pwd);
                JOptionPane.showMessageDialog(this, "Registration successful. You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                mainFrame.showCard("login");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
