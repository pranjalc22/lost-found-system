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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import com.lostfound.controller.ItemController;
import com.lostfound.model.Item;
import com.lostfound.model.User;

public class ClaimItemFrame extends JFrame {

    private static final Color PRIMARY_COLOR = new Color(33, 150, 243);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(33, 33, 33);

    private final User currentUser;
    private final Item item;
    private final ItemController itemController;

    private JTextArea claimMessageArea;
    private JButton submitButton;
    private JButton backButton;

    public ClaimItemFrame(User user, Item item) {
        this.currentUser = user;
        this.item = item;
        this.itemController = new ItemController();
        initUI();
    }

    private void initUI() {
        setTitle("Claim Item");
        setSize(460, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(460, 95));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));

        JPanel headerLeft = new JPanel();
        headerLeft.setBackground(PRIMARY_COLOR);
        headerLeft.setLayout(new BoxLayout(headerLeft, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Claim This Item");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Tell us why this item belongs to you");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(200, 230, 255));

        headerLeft.add(titleLabel);
        headerLeft.add(Box.createVerticalStrut(4));
        headerLeft.add(subtitleLabel);
        headerPanel.add(headerLeft, BorderLayout.WEST);

        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(28, 35, 28, 35));

        JLabel itemHeaderLabel = new JLabel(item.getItemName());
        itemHeaderLabel.setFont(new Font("Arial", Font.BOLD, 18));
        itemHeaderLabel.setForeground(PRIMARY_COLOR);
        itemHeaderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel itemDetailsLabel = new JLabel(item.getCategory() + " | " + item.getLocation());
        itemDetailsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        itemDetailsLabel.setForeground(TEXT_COLOR);
        itemDetailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel messageLabel = new JLabel("Why do you believe this is your item? *");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 12));
        messageLabel.setForeground(TEXT_COLOR);
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        claimMessageArea = new JTextArea(7, 10);
        claimMessageArea.setFont(new Font("Arial", Font.PLAIN, 13));
        claimMessageArea.setLineWrap(true);
        claimMessageArea.setWrapStyleWord(true);
        claimMessageArea.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JScrollPane scrollPane = new JScrollPane(claimMessageArea);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        submitButton = new JButton("SUBMIT CLAIM");
        submitButton.setFont(new Font("Arial", Font.BOLD, 13));
        submitButton.setBackground(PRIMARY_COLOR);
        submitButton.setForeground(Color.WHITE);
        submitButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        submitButton.setFocusPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.setOpaque(true);
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backButton.setBackground(CARD_COLOR);
        backButton.setForeground(PRIMARY_COLOR);
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setOpaque(true);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        cardPanel.add(itemHeaderLabel);
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(itemDetailsLabel);
        cardPanel.add(Box.createVerticalStrut(25));
        cardPanel.add(messageLabel);
        cardPanel.add(Box.createVerticalStrut(8));
        cardPanel.add(scrollPane);
        cardPanel.add(Box.createVerticalStrut(25));
        cardPanel.add(submitButton);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(backButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);

        submitButton.addActionListener(e -> handleSubmit());
        backButton.addActionListener(e -> {
            new BrowseItemsFrame(currentUser).setVisible(true);
            dispose();
        });
    }

    private void handleSubmit() {
        String message = claimMessageArea.getText().trim();
        boolean success = itemController.submitClaim(item.getItemId(), currentUser.getUserId(), message);
        if (success) {
            JOptionPane.showMessageDialog(this, "Claim submitted successfully!");
            new FounderDetailsFrame(currentUser, item).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Could not submit your claim. Please check your message and try again.",
                "Claim Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
