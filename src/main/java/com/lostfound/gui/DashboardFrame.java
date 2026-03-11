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

    private User currentUser;
    private static final Color PRIMARY_COLOR = new Color(33, 150, 243);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(33, 33, 33);
    private static final Color GREEN_COLOR = new Color(76, 175, 80);
    private static final Color ORANGE_COLOR = new Color(255, 152, 0);
    private static final Color RED_COLOR = new Color(244, 67, 54);

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

        // Header
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

        JLabel nameLabel = new JLabel(currentUser.getName() + " 👋");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setForeground(Color.WHITE);

        headerLeft.add(welcomeLabel);
        headerLeft.add(nameLabel);

        JLabel logoLabel = new JLabel("🔍");
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        logoLabel.setForeground(Color.WHITE);

        headerPanel.add(headerLeft, BorderLayout.WEST);
        headerPanel.add(logoLabel, BorderLayout.EAST);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Section title
        JLabel sectionLabel = new JLabel("What would you like to do?");
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 15));
        sectionLabel.setForeground(TEXT_COLOR);
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(sectionLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Cards row 1
        JPanel row1 = new JPanel(new GridLayout(1, 2, 15, 0));
        row1.setBackground(BACKGROUND_COLOR);
        row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        row1.setAlignmentX(Component.LEFT_ALIGNMENT);

        row1.add(createMenuCard("📦", "Report Lost Item",
                "Lost something? Report it here", PRIMARY_COLOR, e -> {
            new ReportLostItemFrame(currentUser).setVisible(true);
            dispose();
        }));

        row1.add(createMenuCard("✅", "Report Found Item",
                "Found something? Let us know", GREEN_COLOR, e -> {
            new ReportFoundItemFrame(currentUser).setVisible(true);
            dispose();
        }));

        // Cards row 2
        JPanel row2 = new JPanel(new GridLayout(1, 2, 15, 0));
        row2.setBackground(BACKGROUND_COLOR);
        row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        row2.setAlignmentX(Component.LEFT_ALIGNMENT);

        row2.add(createMenuCard("🔎", "Browse Items",
                "Search lost & found items", ORANGE_COLOR, e -> {
            new BrowseItemsFrame(currentUser).setVisible(true);
            dispose();
        }));

        row2.add(createMenuCard("🚪", "Logout",
                "Sign out of your account", RED_COLOR, e -> {
            new LoginFrame().setVisible(true);
            dispose();
        }));

        contentPanel.add(row1);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(row2);

        // Bottom status bar
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

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 13));
        titleLabel.setForeground(color);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        subtitleLabel.setForeground(new Color(150, 150, 150));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btn = new JButton("Open →");
        btn.setFont(new Font("Arial", Font.BOLD, 11));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(action);

        card.add(iconLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitleLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(btn);

        return card;
    }
}