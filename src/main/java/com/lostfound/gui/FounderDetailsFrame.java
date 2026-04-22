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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import com.lostfound.model.Item;
import com.lostfound.model.User;
import com.lostfound.repository.UserRepository;

public class FounderDetailsFrame extends JFrame {

    private static final Color PRIMARY_COLOR = new Color(33, 150, 243);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(33, 33, 33);

    private final User currentUser;
    private final Item item;
    private final User founder;

    public FounderDetailsFrame(User user, Item item) {
        this.currentUser = user;
        this.item = item;
        this.founder = new UserRepository().getUserById(item.getUserId());
        initUI();
    }

    private void initUI() {
        setTitle("Founder Details");
        setSize(460, 520);
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

        JLabel titleLabel = new JLabel("Founder Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Contact the person who reported this item");
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

        JLabel itemTitleLabel = new JLabel(item.getItemName());
        itemTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        itemTitleLabel.setForeground(PRIMARY_COLOR);
        itemTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel founderNameLabel = createInfoLabel("Founder Name: " + getFounderValue("Not available", "name"));
        JLabel founderEmailLabel = createInfoLabel("Email: " + getFounderValue("Not available", "email"));
        JLabel founderPhoneLabel = createInfoLabel("Phone: " + getFounderValue("Not available", "phone"));
        JLabel locationLabel = createInfoLabel("Location: " + item.getLocation());
        JLabel dateLabel = createInfoLabel("Reported On: " + item.getDateReported());

        JTextArea noteArea = new JTextArea(
            "Your claim has been submitted. Reach out politely and be ready to describe identifying details of the item."
        );
        noteArea.setFont(new Font("Arial", Font.PLAIN, 12));
        noteArea.setForeground(TEXT_COLOR);
        noteArea.setBackground(new Color(245, 249, 255));
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        noteArea.setEditable(false);
        noteArea.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 220, 245), 1, true),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        noteArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        noteArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));

        JButton doneButton = new JButton("Back to Dashboard");
        doneButton.setFont(new Font("Arial", Font.BOLD, 13));
        doneButton.setBackground(PRIMARY_COLOR);
        doneButton.setForeground(Color.WHITE);
        doneButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        doneButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        doneButton.setFocusPainted(false);
        doneButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        doneButton.setOpaque(true);
        doneButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        cardPanel.add(itemTitleLabel);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(founderNameLabel);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(founderEmailLabel);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(founderPhoneLabel);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(locationLabel);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(dateLabel);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(noteArea);
        cardPanel.add(Box.createVerticalStrut(25));
        cardPanel.add(doneButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);

        doneButton.addActionListener(e -> {
            new DashboardFrame(currentUser).setVisible(true);
            dispose();
        });
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private String getFounderValue(String fallback, String field) {
        if (founder == null) {
            return fallback;
        }
        if ("name".equals(field)) {
            return founder.getName();
        }
        if ("email".equals(field)) {
            return founder.getEmail();
        }
        if ("phone".equals(field)) {
            return founder.getPhone();
        }
        return fallback;
    }
}
