package com.lostfound.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.lostfound.model.User;

public class DashboardFrame extends JFrame {

    private final User currentUser;

    private static final Color PRIMARY_COLOR = new Color(33, 150, 243);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(33, 33, 33);
    private static final Color GREEN_COLOR = new Color(76, 175, 80);
    private static final Color ORANGE_COLOR = new Color(255, 152, 0);
    private static final Color RED_COLOR = new Color(244, 67, 54);
    private static final Color INDIGO_COLOR = new Color(103, 58, 183);
    private static final Color ALERT_RED_COLOR = new Color(211, 47, 47);

    public DashboardFrame(User user) {
        this.currentUser = user;
        initUI();
    }

    private void initUI() {
        setTitle("Dashboard - " + currentUser.getName());
        setSize(520, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(520, 100));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel headerLeft = new JPanel();
        headerLeft.setBackground(PRIMARY_COLOR);
        headerLeft.setLayout(new BoxLayout(headerLeft, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome back,");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        welcomeLabel.setForeground(new Color(200, 230, 255));

        JLabel nameLabel = new JLabel(currentUser.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setForeground(Color.WHITE);

        headerLeft.add(welcomeLabel);
        headerLeft.add(nameLabel);

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerRight.setBackground(PRIMARY_COLOR);

        String initial = currentUser.getName() != null && !currentUser.getName().trim().isEmpty()
                ? currentUser.getName().trim().substring(0, 1).toUpperCase()
                : "U";

        JButton profileButton = new JButton(initial);
        profileButton.setFont(new Font("Arial", Font.BOLD, 18));
        profileButton.setForeground(Color.WHITE);
        profileButton.setPreferredSize(new Dimension(45, 45));
        profileButton.setFocusPainted(false);
        profileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profileButton.setContentAreaFilled(false);
        profileButton.setOpaque(false);
        profileButton.setBorder(new LineBorder(Color.WHITE, 2, true));
        profileButton.addActionListener(e -> {
            new ProfileFrame(currentUser).setVisible(true);
            dispose();
        });

        headerRight.add(profileButton);

        headerPanel.add(headerLeft, BorderLayout.WEST);
        headerPanel.add(headerRight, BorderLayout.EAST);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel sectionLabel = new JLabel("What would you like to do?");
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 15));
        sectionLabel.setForeground(TEXT_COLOR);
        sectionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        gridPanel.setBackground(BACKGROUND_COLOR);

        gridPanel.add(createMenuCard("L", "Report Lost Item",
                "Lost something? Report it here", ALERT_RED_COLOR, e -> {
            new ReportLostItemFrame(currentUser).setVisible(true);
            dispose();
        }));

        gridPanel.add(createMenuCard("F", "Report Found Item",
                "Found something? Let us know", GREEN_COLOR, e -> {
            new ReportFoundItemFrame(currentUser).setVisible(true);
            dispose();
        }));

        gridPanel.add(createMenuCard("B", "Browse Items",
                "Search lost and found items", ORANGE_COLOR, e -> {
            new BrowseItemsFrame(currentUser).setVisible(true);
            dispose();
        }));

        gridPanel.add(createMenuCard("M", "My Items",
                "Review items you reported", INDIGO_COLOR, e -> {
            new MyItemsFrame(currentUser).setVisible(true);
            dispose();
        }));

        contentPanel.add(sectionLabel, BorderLayout.NORTH);
        contentPanel.add(gridPanel, BorderLayout.CENTER);

        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBackground(new Color(230, 230, 230));
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        JLabel statusLabel = new JLabel("Logged in as: " + currentUser.getEmail());
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        statusLabel.setForeground(new Color(100, 100, 100));
        statusBar.add(statusLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(statusBar, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    private JPanel createMenuCard(String icon, String title, String subtitle,
                                  Color color, java.awt.event.ActionListener action) {
        JPanel card = new JPanel();
        card.setBackground(CARD_COLOR);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 13));
        titleLabel.setForeground(color);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        subtitleLabel.setForeground(new Color(150, 150, 150));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton button = new JButton("Open");
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(action);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitleLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(button);

        return card;
    }
}
