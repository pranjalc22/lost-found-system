package com.lostfound.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;
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
    private JComboBox<String> categoryComboBox;
    private JButton searchButton;
    private JButton backButton;
    private List<Item> currentItems;

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

        JLabel subtitleLabel = new JLabel("Click a row to view item image");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(255, 230, 180));

        headerLeft.add(titleLabel);
        headerLeft.add(Box.createVerticalStrut(4));
        headerLeft.add(subtitleLabel);
        headerPanel.add(headerLeft, BorderLayout.WEST);

        // Filter bar
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBackground(CARD_COLOR);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        JLabel filterLabel = new JLabel("Show:");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 13));
        filterLabel.setForeground(TEXT_COLOR);

        filterComboBox = new JComboBox<>(new String[]{"Lost Items", "Found Items", "Recovered Items"});
        filterComboBox.setFont(new Font("Arial", Font.PLAIN, 13));
        filterComboBox.setPreferredSize(new Dimension(150, 35));
        filterComboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 13));
        categoryLabel.setForeground(TEXT_COLOR);

        categoryComboBox = new JComboBox<>(new String[]{
            "All Categories", "Electronics", "Bags", "ID Cards",
            "Clothing", "Keys", "Wallet", "Other"
        });
        categoryComboBox.setFont(new Font("Arial", Font.PLAIN, 13));
        categoryComboBox.setPreferredSize(new Dimension(160, 35));
        categoryComboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
        filterPanel.add(categoryLabel);
        filterPanel.add(categoryComboBox);
        filterPanel.add(searchButton);

        // Table
        String[] columns = {"ID", "Item Name", "Category", "Location", "Date", "Status", "Claim", "Recovered"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return col == 6 || col == 7; }
        };
        itemTable = new JTable(tableModel);
        itemTable.setFont(new Font("Arial", Font.PLAIN, 13));
        itemTable.setRowHeight(35);
        itemTable.setGridColor(new Color(230, 230, 230));
        itemTable.setSelectionBackground(new Color(200, 230, 255));
        itemTable.setShowVerticalLines(false);
        itemTable.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = itemTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(TEXT_COLOR);
        header.setPreferredSize(new Dimension(0, 40));

        itemTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        itemTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        itemTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        itemTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        itemTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        itemTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        itemTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        itemTable.getColumnModel().getColumn(7).setPreferredWidth(130);

        itemTable.getColumnModel().getColumn(6).setCellRenderer(new ActionButtonRenderer(PRIMARY_COLOR));
        itemTable.getColumnModel().getColumn(6).setCellEditor(new ActionButtonEditor("claim"));
        itemTable.getColumnModel().getColumn(7).setCellRenderer(new ActionButtonRenderer(ORANGE_COLOR));
        itemTable.getColumnModel().getColumn(7).setCellEditor(new ActionButtonEditor("recover"));

        // Show image when user clicks a row
        itemTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = itemTable.getSelectedRow();
                int column = itemTable.columnAtPoint(e.getPoint());
                if (row == -1 || currentItems == null) return;
                if (column >= 6) return;

                Item selectedItem = currentItems.get(row);
                String imagePath = selectedItem.getImagePath();

                if (imagePath != null && !imagePath.isEmpty()) {
                    showImageDialog(selectedItem);
                } else {
                    JOptionPane.showMessageDialog(null,
                        "No image uploaded for this item.",
                        selectedItem.getItemName(),
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_COLOR);

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(CARD_COLOR);
        tableCard.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        tableCard.add(filterPanel, BorderLayout.NORTH);
        tableCard.add(scrollPane, BorderLayout.CENTER);

        // Bottom bar
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
        String category = (String) categoryComboBox.getSelectedItem();
        boolean isRecoveredView = "Recovered Items".equals(selected);

        if ("Lost Items".equals(selected)) {
            currentItems = itemController.getItemsByCategory("lost", category);
        } else if ("Found Items".equals(selected)) {
            currentItems = itemController.getItemsByCategory("found", category);
        } else {
            currentItems = itemController.getRecoveredItems();
            if (category != null && !"All Categories".equalsIgnoreCase(category)) {
                currentItems.removeIf(item -> !category.equalsIgnoreCase(item.getCategory()));
            }
        }

        currentItems.forEach(item -> {
            Object[] row = {
                item.getItemId(),
                item.getItemName(),
                item.getCategory(),
                item.getLocation(),
                item.getDateReported(),
                item.getStatus(),
                isRecoveredView ? "" : "Claim",
                isRecoveredView ? "" : (item.getUserId() == currentUser.getUserId() ? "Mark as Recovered" : "")
            };
            tableModel.addRow(row);
        });

        if (currentItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items found!");
        }
    }

    private class ActionButtonRenderer extends JButton implements TableCellRenderer {
        ActionButtonRenderer(Color backgroundColor) {
            setOpaque(true);
            setForeground(Color.WHITE);
            setBackground(backgroundColor);
            setFont(new Font("Arial", Font.BOLD, 11));
            setFocusPainted(false);
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            String text = value == null ? "" : value.toString();
            setText(text);
            setEnabled(!text.isEmpty());
            if (text.isEmpty()) {
                setBackground(CARD_COLOR);
                setBorder(BorderFactory.createEmptyBorder());
            } else {
                setBackground(column == 6 ? PRIMARY_COLOR : ORANGE_COLOR);
                setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(column == 6 ? PRIMARY_COLOR : ORANGE_COLOR, 1, true),
                    BorderFactory.createEmptyBorder(6, 8, 6, 8)
                ));
            }
            return this;
        }
    }

    private class ActionButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private final String actionType;
        private int currentRow;

        ActionButtonEditor(String actionType) {
            super(new JTextField());
            this.actionType = actionType;
            this.button = new JButton();
            button.setOpaque(true);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 11));
            button.addActionListener(e -> handleAction());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                     int row, int column) {
            currentRow = row;
            String text = value == null ? "" : value.toString();
            button.setText(text);
            button.setEnabled(!text.isEmpty());
            button.setBackground("claim".equals(actionType) ? PRIMARY_COLOR : ORANGE_COLOR);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }

        private void handleAction() {
            fireEditingStopped();
            if (currentItems == null || currentRow < 0 || currentRow >= currentItems.size()) {
                return;
            }

            Item selectedItem = currentItems.get(currentRow);
            if ("claim".equals(actionType)) {
                new ClaimItemFrame(currentUser, selectedItem).setVisible(true);
                dispose();
                return;
            }

            if (selectedItem.getUserId() != currentUser.getUserId()) {
                return;
            }

            boolean success = itemController.markItemRecovered(selectedItem.getItemId());
            if (success) {
                JOptionPane.showMessageDialog(BrowseItemsFrame.this,
                    "Item marked as recovered.");
                loadItems();
            } else {
                JOptionPane.showMessageDialog(BrowseItemsFrame.this,
                    "Could not update item status.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Loads image from Cloudinary URL and shows it in a popup dialog
    private void showImageDialog(Item item) {
        try {
            URL imageUrl = new URL(item.getImagePath());
            Image image = ImageIO.read(imageUrl);
            Image scaled = image.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaled);

            JLabel imageLabel = new JLabel(icon);
            JPanel panel = new JPanel(new BorderLayout(0, 8));
            panel.add(new JLabel("📦 " + item.getItemName() + "  |  " +
                item.getCategory() + "  |  " + item.getLocation(),
                JLabel.CENTER), BorderLayout.NORTH);
            panel.add(imageLabel, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(this, panel,
                "Item Image", JOptionPane.PLAIN_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                "Could not load image.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
