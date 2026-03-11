package com.lostfound.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.lostfound.controller.ItemController;
import com.lostfound.model.User;

public class ReportLostItemFrame extends JFrame {

    private JTextField itemNameField;
    private JTextField categoryField;
    private JTextField locationField;
    private JTextField contactField;
    private JTextArea descriptionArea;
    private JButton submitButton;
    private JButton backButton;
    private User currentUser;
    private ItemController itemController;

    private static final Color PRIMARY_COLOR = new Color(33, 150, 243);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color TEXT_COLOR = new Color(33, 33, 33);

    public ReportLostItemFrame(User user) {
        this.currentUser = user;
        this.itemController = new ItemController();
        initUI();
    }

    private void initUI() {
        setTitle("Report Lost Item");
        setSize(460, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(460, 90));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JPanel headerLeft = new JPanel();
        headerLeft.setBackground(PRIMARY_COLOR);
        headerLeft.setLayout(new BoxLayout(headerLeft, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("📦 Report Lost Item");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Fill in the details of your lost item");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(200, 230, 255));

        headerLeft.add(titleLabel);
        headerLeft.add(Box.createVerticalStrut(4));
        headerLeft.add(subtitleLabel);
        headerPanel.add(headerLeft, BorderLayout.WEST);

        // Form card
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        // Fields
        cardPanel.add(createFieldLabel("Item Name *"));
        cardPanel.add(Box.createVerticalStrut(5));
        itemNameField = createStyledTextField();
        cardPanel.add(itemNameField);
        cardPanel.add(Box.createVerticalStrut(15));

        cardPanel.add(createFieldLabel("Category"));
        cardPanel.add(Box.createVerticalStrut(5));
        categoryField = createStyledTextField();
        cardPanel.add(categoryField);
        cardPanel.add(Box.createVerticalStrut(15));

        cardPanel.add(createFieldLabel("Location Lost *"));
        cardPanel.add(Box.createVerticalStrut(5));
        locationField = createStyledTextField();
        cardPanel.add(locationField);
        cardPanel.add(Box.createVerticalStrut(15));

        cardPanel.add(createFieldLabel("Contact Info"));
        cardPanel.add(Box.createVerticalStrut(5));
        contactField = createStyledTextField();
        cardPanel.add(contactField);
        cardPanel.add(Box.createVerticalStrut(15));

        cardPanel.add(createFieldLabel("Description"));
        cardPanel.add(Box.createVerticalStrut(5));
        descriptionArea = new JTextArea(3, 10);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 13));
        descriptionArea.setLineWrap(true);
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.add(scrollPane);
        cardPanel.add(Box.createVerticalStrut(25));

        // Buttons
        submitButton = new JButton("SUBMIT REPORT");
        submitButton.setFont(new Font("Arial", Font.BOLD, 13));
        submitButton.setBackground(PRIMARY_COLOR);
        submitButton.setForeground(Color.WHITE);
        submitButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        submitButton.setFocusPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.setOpaque(true);
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        backButton = new JButton("← Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backButton.setBackground(CARD_COLOR);
        backButton.setForeground(PRIMARY_COLOR);
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setOpaque(true);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        cardPanel.add(submitButton);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(backButton);

        JScrollPane mainScroll = new JScrollPane(cardPanel);
        mainScroll.setBorder(null);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(mainScroll, BorderLayout.CENTER);
        setContentPane(mainPanel);

        submitButton.addActionListener(e -> handleSubmit());
        backButton.addActionListener(e -> {
            new DashboardFrame(currentUser).setVisible(true);
            dispose();
        });
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private void handleSubmit() {
        String itemName = itemNameField.getText();
        String category = categoryField.getText();
        String location = locationField.getText();
        String contact = contactField.getText();
        String description = descriptionArea.getText();
        String date = LocalDate.now().toString();

        boolean success = itemController.reportLostItem(
            itemName, category, description,
            location, date, currentUser.getUserId(), contact
        );

        if (success) {
            JOptionPane.showMessageDialog(this, "Lost item reported successfully!");
            new DashboardFrame(currentUser).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to report item!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}