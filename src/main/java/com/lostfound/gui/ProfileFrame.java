package com.lostfound.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.lostfound.controller.ItemController;
import com.lostfound.model.User;
import com.lostfound.repository.ItemRepository;
import com.lostfound.repository.UserRepository;

public class ProfileFrame extends JFrame {

    private static final Color PRIMARY_COLOR = new Color(33, 150, 243);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(33, 33, 33);
    private static final Color PROFILE_COLOR = new Color(0, 121, 107);
    private static final Color LOGOUT_COLOR = new Color(211, 47, 47);

    private final User currentUser;
    private final ItemController itemController;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JLabel totalItemsValueLabel;
    private JLabel totalClaimsValueLabel;
    private JButton editButton;
    private JButton saveButton;

    public ProfileFrame(User user) {
        this.currentUser = user;
        this.itemController = new ItemController();
        this.itemRepository = new ItemRepository();
        this.userRepository = new UserRepository();
        initUI();
    }

    private void initUI() {
        setTitle("Profile");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(500, 95));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));

        JPanel headerLeft = new JPanel();
        headerLeft.setBackground(PRIMARY_COLOR);
        headerLeft.setLayout(new BoxLayout(headerLeft, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("View and manage your details");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(200, 230, 255));

        headerLeft.add(titleLabel);
        headerLeft.add(Box.createVerticalStrut(4));
        headerLeft.add(subtitleLabel);
        headerPanel.add(headerLeft, BorderLayout.WEST);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel detailsCard = createCardPanel();
        detailsCard.add(createSectionTitle("Personal Details"));
        detailsCard.add(Box.createVerticalStrut(16));

        detailsCard.add(createFieldLabel("Full Name"));
        nameField = createStyledTextField(currentUser.getName());
        detailsCard.add(nameField);
        detailsCard.add(Box.createVerticalStrut(12));

        detailsCard.add(createFieldLabel("Email"));
        emailField = createStyledTextField(currentUser.getEmail());
        emailField.setEditable(false);
        detailsCard.add(emailField);
        detailsCard.add(Box.createVerticalStrut(12));

        detailsCard.add(createFieldLabel("Phone"));
        phoneField = createStyledTextField(currentUser.getPhone());
        detailsCard.add(phoneField);
        detailsCard.add(Box.createVerticalStrut(18));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actionPanel.setBackground(CARD_COLOR);
        actionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        editButton = createFilledButton("Edit Profile", PROFILE_COLOR);
        saveButton = createFilledButton("Save", PRIMARY_COLOR);
        saveButton.setVisible(false);

        actionPanel.add(editButton);
        actionPanel.add(saveButton);
        detailsCard.add(actionPanel);

        JPanel statsCard = createCardPanel();
        statsCard.setMinimumSize(new Dimension(0, 150));
        statsCard.setPreferredSize(new Dimension(450, 150));
        statsCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        statsCard.add(createSectionTitle("Your Stats"));
        statsCard.add(Box.createVerticalStrut(16));

        totalItemsValueLabel = createStatLabel("Total Items Reported: 0");
        totalClaimsValueLabel = createStatLabel("Total Claims Made: 0");

        statsCard.add(totalItemsValueLabel);
        statsCard.add(Box.createVerticalStrut(10));
        statsCard.add(totalClaimsValueLabel);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        bottomPanel.setBackground(BACKGROUND_COLOR);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.PLAIN, 13));
        backButton.setBackground(CARD_COLOR);
        backButton.setForeground(PRIMARY_COLOR);
        backButton.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 1, true),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setOpaque(true);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 13));
        logoutButton.setBackground(LOGOUT_COLOR);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(LOGOUT_COLOR, 1, true),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setOpaque(true);

        bottomPanel.add(backButton);
        bottomPanel.add(logoutButton);

        contentPanel.add(detailsCard);
        contentPanel.add(Box.createVerticalStrut(18));
        contentPanel.add(statsCard);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);

        setEditMode(false);
        loadStats();

        editButton.addActionListener(e -> setEditMode(true));
        saveButton.addActionListener(e -> handleSave());
        backButton.addActionListener(e -> {
            new DashboardFrame(currentUser).setVisible(true);
            dispose();
        });
        logoutButton.addActionListener(e -> handleLogout());
    }

    private JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    private JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createStyledTextField(String value) {
        JTextField field = new JTextField(value);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JLabel createStatLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JButton createFilledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        return button;
    }

    private void setEditMode(boolean editable) {
        nameField.setEditable(editable);
        phoneField.setEditable(editable);
        saveButton.setVisible(editable);
        editButton.setVisible(!editable);
    }

    private void loadStats() {
        int totalItems = itemController.getItemsByUserId(currentUser.getUserId()).size();
        int totalClaims = itemRepository.getClaimsCountByUserId(currentUser.getUserId());
        totalItemsValueLabel.setText("Total Items Reported: " + totalItems);
        totalClaimsValueLabel.setText("Total Claims Made: " + totalClaims);
    }

    private void handleSave() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Name and phone cannot be empty.",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = userRepository.updateUser(currentUser.getUserId(), name, phone);
        if (success) {
            currentUser.setName(name);
            currentUser.setPhone(phone);
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            setEditMode(false);
        } else {
            JOptionPane.showMessageDialog(this,
                "Failed to update profile.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            for (Window window : Window.getWindows()) {
                window.dispose();
            }
            new LoginFrame().setVisible(true);
        }
    }
}
