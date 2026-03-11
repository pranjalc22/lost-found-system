package com.lostfound.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.lostfound.controller.ItemController;
import com.lostfound.model.Item;
import com.lostfound.model.User;

public class BrowseItemsFrame extends JFrame {

    private User currentUser;
    private ItemController itemController;
    private JTable itemTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterComboBox;
    private JButton searchButton;
    private JButton backButton;

    private static final Color PRIMARY_COLOR = new Color(33, 150, 243);
    private static final Color ORANGE_COLOR = new Color(255, 152, 0);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(33, 33, 33);

    public BrowseItemsFrame(User user) {
        this.currentUser = user;
        this.itemController = new ItemController();
        initUI();
    }

    private void initUI() {
        setTitle("Browse Items");
        setSize(720, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ORANGE_COLOR);
        headerPanel.setPreferredSize(new Dimension(720, 90));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JPanel headerLeft = new JPanel();
        headerLeft.setBackground(ORANGE_COLOR);
        headerLeft.setLayout(new BoxLayout(headerLeft, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("🔎 Browse Items");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Search through all lost & found items");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(255, 230, 180));

        headerLeft.add(titleLabel);
        headerLeft.add(Box.createVerticalStrut(4));
        headerLeft.add(subtitleLabel);
        headerPanel.add(headerLeft, BorderLayout.WEST);

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBackground(CARD_COLOR);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        JLabel filterLabel = new JLabel("Show:");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 13));
        filterLabel.setForeground(TEXT_COLOR);

        filterComboBox = new JComboBox<>(new String[]{"Lost Items", "Found Items"});
        filterComboBox.setFont(new Font("Arial", Font.PLAIN, 13));
        filterComboBox.setPreferredSize(new Dimension(150, 35));
        filterComboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 13));
        searchButton.setBackground(ORANGE_COLOR);
        searchButton.setForeground(Color.WHITE);
        searchButton.setPreferredSize(new Dimension(100, 35));
        searchButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        searchButton.setFocusPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.setOpaque(true);

        filterPanel.add(filterLabel);
        filterPanel.add(filterComboBox);
        filterPanel.add(searchButton);

        // Table
        String[] columns = {"ID", "Item Name", "Category", "Location", "Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        itemTable = new JTable(tableModel);
        itemTable.setFont(new Font("Arial", Font.PLAIN, 13));
        itemTable.setRowHeight(35);
        itemTable.setGridColor(new Color(230, 230, 230));
        itemTable.setSelectionBackground(new Color(200, 230, 255));
        itemTable.setShowVerticalLines(false);
        itemTable.setIntercellSpacing(new Dimension(0, 1));

        // Table header styling
        JTableHeader header = itemTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(TEXT_COLOR);
        header.setPreferredSize(new Dimension(0, 40));

        // Column widths
        itemTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        itemTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        itemTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        itemTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        itemTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        itemTable.getColumnModel().getColumn(5).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_COLOR);

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(CARD_COLOR);
        tableCard.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        tableCard.add(filterPanel, BorderLayout.NORTH);
        tableCard.add(scrollPane, BorderLayout.CENTER);

        // Back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        bottomPanel.setBackground(BACKGROUND_COLOR);

        backButton = new JButton("← Back to Dashboard");
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
        bottomPanel.add(backButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tableCard, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        searchButton.addActionListener(e -> loadItems());
        backButton.addActionListener(e -> {
            new DashboardFrame(currentUser).setVisible(true);
            dispose();
        });

        loadItems();
    }

    private void loadItems() {
        tableModel.setRowCount(0);
        String selected = (String) filterComboBox.getSelectedItem();
        List<Item> items;

        if (selected.equals("Lost Items")) {
            items = itemController.getLostItems();
        } else {
            items = itemController.getFoundItems();
        }

        items.forEach(item -> {
            Object[] row = {
                item.getItemId(),
                item.getItemName(),
                item.getCategory(),
                item.getLocation(),
                item.getDateReported(),
                item.getStatus()
            };
            tableModel.addRow(row);
        });

        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items found!");
        }
    }
}