package com.courtsync.se.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractButton;
import java.awt.*;

/**
 * Centralized UI styling for CourtSync SE.
 */
public class UIStyles {
    public static void apply() {
        Font base = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("Label.font", base);
        UIManager.put("Button.font", base.deriveFont(Font.BOLD, 13f));
        UIManager.put("TextField.font", base);
        UIManager.put("PasswordField.font", base);

        Color background = Color.decode("#F6F8FA");
        Color surface = Color.white;
        Color primary = Color.decode("#2D5873");

        UIManager.put("Panel.background", background);
        UIManager.put("TextField.background", surface);
        UIManager.put("PasswordField.background", surface);

        UIManager.put("App.primaryColor", primary);
        UIManager.put("App.bannerBackground", primary);
        UIManager.put("App.cardBackground", surface);
        UIManager.put("App.buttonBackground", surface);
        UIManager.put("App.buttonForeground", Color.black);

        UIManager.put("TextField.border", BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#E0E6ED")),
            new EmptyBorder(6, 8, 6, 8)
        ));
        UIManager.put("PasswordField.border", BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#E0E6ED")),
            new EmptyBorder(6, 8, 6, 8)
        ));
        UIManager.put("Button.border", BorderFactory.createEmptyBorder(6, 12, 6, 12));
        UIManager.put("OptionPane.background", background);
    }

    public static void styleButton(AbstractButton b) {
        if (b == null) return;
        try {
            Color bg = (Color) UIManager.get("App.buttonBackground");
            Color fg = (Color) UIManager.get("App.buttonForeground");
            Color primary = (Color) UIManager.get("App.primaryColor");
            b.setBackground(bg != null ? bg : Color.white);
            b.setForeground(fg != null ? fg : Color.black);
            b.setFocusPainted(false);
            if (primary != null) b.setBorder(BorderFactory.createLineBorder(primary, 2));
            b.setOpaque(true);
        } catch (Exception ignored) {}
    }

    public static JPanel createBanner(String titleText) {
        JPanel header = new JPanel(new GridBagLayout());
        Color banner = (Color) UIManager.get("App.bannerBackground");
        header.setBackground(banner != null ? banner : Color.DARK_GRAY);
        header.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        JLabel title = new JLabel(titleText);
        title.setForeground(Color.white);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 26f));
        header.add(title);
        return header;
    }
}
