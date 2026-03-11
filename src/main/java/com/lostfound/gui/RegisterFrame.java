package com.lostfound.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.lostfound.controller.AuthController;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;
    private AuthController authController;

    private static final Color PRIMARY_COLOR = new Color(33, 150, 243);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(33, 33, 33);

    public RegisterFrame() {
        authController = new AuthController();
        initUI();
    }

    private void initUI() {
        setTitle("Lost and Found - Register");
        setSize(420, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PRIMARY_COLOR);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(420, 110));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel iconLabel = new JLabel("📝", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 35));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Join the Lost & Found community");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(200, 230, 255));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(iconLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(3));
        headerPanel.add(subtitleLabel);

        // Card
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Name
        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        nameLabel.setForeground(TEXT_COLOR);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameField = createStyledTextField();

        // Email
        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 12));
        emailLabel.setForeground(TEXT_COLOR);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField = createStyledTextField();

        // Phone
        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 12));
        phoneLabel.setForeground(TEXT_COLOR);
        phoneLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        phoneField = createStyledTextField();

        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 12));
        passwordLabel.setForeground(TEXT_COLOR);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Register button
        registerButton = new JButton("CREATE ACCOUNT");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(PRIMARY_COLOR);
        registerButton.setForeground(Color.WHITE);
        registerButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setOpaque(true);
        registerButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Back button
        backButton = new JButton("Already have an account? Login");
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backButton.setBackground(CARD_COLOR);
        backButton.setForeground(PRIMARY_COLOR);
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setOpaque(true);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        cardPanel.add(nameLabel);
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(nameField);
        cardPanel.add(Box.createVerticalStrut(12));
        cardPanel.add(emailLabel);
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(emailField);
        cardPanel.add(Box.createVerticalStrut(12));
        cardPanel.add(phoneLabel);
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(phoneField);
        cardPanel.add(Box.createVerticalStrut(12));
        cardPanel.add(passwordLabel);
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(passwordField);
        cardPanel.add(Box.createVerticalStrut(25));
        cardPanel.add(registerButton);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(backButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);

        registerButton.addActionListener(e -> handleRegister());
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private void handleRegister() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = new String(passwordField.getPassword());

        boolean success = authController.register(name, email, phone, password);
        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
            new LoginFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed! Email may already exist.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}