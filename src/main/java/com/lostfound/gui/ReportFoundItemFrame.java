package com.lostfound.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.lostfound.controller.ItemController;
import com.lostfound.model.User;
import com.lostfound.util.CloudinaryConfig;

public class ReportFoundItemFrame extends JFrame {

    private JTextField itemNameField;
    private JComboBox<String> categoryField;
    private JTextField locationField;
    private JTextField foundLocationField;
    private JTextArea descriptionArea;
    private JButton submitButton;
    private JButton backButton;
    private JButton chooseImageButton;
    private JLabel imageStatusLabel;
    private User currentUser;
    private ItemController itemController;
    private String selectedImagePath = "";

    private static final Color PRIMARY_COLOR = new Color(76, 175, 80);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color TEXT_COLOR = new Color(33, 33, 33);

    public ReportFoundItemFrame(User user) {
        this.currentUser = user;
        this.itemController = new ItemController();
        initUI();
    }

    private void initUI() {
        setTitle("Report Found Item");
        setSize(460, 680);
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

        JLabel titleLabel = new JLabel("\u2705 Report Found Item");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Help someone find their lost item");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(200, 240, 200));

        headerLeft.add(titleLabel);
        headerLeft.add(Box.createVerticalStrut(4));
        headerLeft.add(subtitleLabel);
        headerPanel.add(headerLeft, BorderLayout.WEST);

        // Form card
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        cardPanel.add(createFieldLabel("Item Name *"));
        cardPanel.add(Box.createVerticalStrut(5));
        itemNameField = createStyledTextField();
        cardPanel.add(itemNameField);
        cardPanel.add(Box.createVerticalStrut(15));

        cardPanel.add(createFieldLabel("Category"));
        cardPanel.add(Box.createVerticalStrut(5));
        categoryField = createStyledCategoryComboBox();
        cardPanel.add(categoryField);
        cardPanel.add(Box.createVerticalStrut(15));

        cardPanel.add(createFieldLabel("Location *"));
        cardPanel.add(Box.createVerticalStrut(5));
        locationField = createStyledTextField();
        cardPanel.add(locationField);
        cardPanel.add(Box.createVerticalStrut(15));

        cardPanel.add(createFieldLabel("Found At"));
        cardPanel.add(Box.createVerticalStrut(5));
        foundLocationField = createStyledTextField();
        cardPanel.add(foundLocationField);
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
        cardPanel.add(Box.createVerticalStrut(15));

        // Image upload section
        cardPanel.add(createFieldLabel("Item Image (optional)"));
        cardPanel.add(Box.createVerticalStrut(5));

        chooseImageButton = new JButton("\uD83D\uDCF7 Choose Image");
        chooseImageButton.setFont(new Font("Arial", Font.PLAIN, 12));
        chooseImageButton.setBackground(new Color(240, 240, 240));
        chooseImageButton.setForeground(TEXT_COLOR);
        chooseImageButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        chooseImageButton.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        chooseImageButton.setFocusPainted(false);
        chooseImageButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chooseImageButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        chooseImageButton.setOpaque(true);

        imageStatusLabel = new JLabel("No image selected");
        imageStatusLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        imageStatusLabel.setForeground(Color.GRAY);
        imageStatusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Opens file chooser and stores selected image path
        chooseImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "gif"
            ));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedImagePath = selectedFile.getAbsolutePath();
                imageStatusLabel.setText("\u2705 " + selectedFile.getName());
                imageStatusLabel.setForeground(new Color(76, 175, 80));
            }
        });

        cardPanel.add(chooseImageButton);
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(imageStatusLabel);
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

        backButton = new JButton("\u2190 Back to Dashboard");
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

    private JComboBox<String> createStyledCategoryComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[] {
            "Select Category",
            "Electronics",
            "Bags",
            "ID Cards",
            "Clothing",
            "Keys",
            "Wallet",
            "Books",
            "Other"
        });
        comboBox.setFont(new Font("Arial", Font.PLAIN, 13));
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        return comboBox;
    }

    private void handleSubmit() {
        String itemName = itemNameField.getText().trim();
        String selectedCategory = (String) categoryField.getSelectedItem();
        String category = "Select Category".equals(selectedCategory) ? "" : selectedCategory;
        String location = locationField.getText().trim();
        String foundLocation = foundLocationField.getText().trim();
        String description = descriptionArea.getText().trim();
        String date = LocalDate.now().toString();

        if (itemName.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Item Name and Location are required!",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Disable button and show uploading message
        submitButton.setEnabled(false);
        submitButton.setText("Uploading...");

        // Upload image in background thread so UI doesn't freeze
        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                if (!selectedImagePath.isEmpty()) {
                    return CloudinaryConfig.uploadImage(selectedImagePath);
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    String imageUrl = get();
                    boolean success = itemController.reportFoundItem(
                        itemName, category, description,
                        location, date, currentUser.getUserId(), foundLocation, imageUrl
                    );

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Found item reported successfully!");
                        new DashboardFrame(currentUser).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to report item!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        submitButton.setEnabled(true);
                        submitButton.setText("SUBMIT REPORT");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Something went wrong: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                    submitButton.setEnabled(true);
                    submitButton.setText("SUBMIT REPORT");
                }
            }
        };
        worker.execute();
    }
}
