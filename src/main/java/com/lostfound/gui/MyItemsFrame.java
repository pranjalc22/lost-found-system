package com.lostfound.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.lostfound.controller.ItemController;
import com.lostfound.model.Item;
import com.lostfound.model.User;
import com.lostfound.repository.ItemRepository;

public class MyItemsFrame extends JFrame {

    private static final Color PRIMARY_COLOR = new Color(33, 150, 243);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(33, 33, 33);
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private static final Color DANGER_COLOR = new Color(211, 47, 47);

    private final User currentUser;
    private final ItemController itemController;
    private final ItemRepository itemRepository;

    private JTable itemTable;
    private DefaultTableModel tableModel;
    private List<Item> currentItems;

    public MyItemsFrame(User user) {
        this.currentUser = user;
        this.itemController = new ItemController();
        this.itemRepository = new ItemRepository();
        initUI();
    }

    private void initUI() {
        setTitle("My Reported Items");
        setSize(820, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(820, 95));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));

        JPanel headerLeft = new JPanel();
        headerLeft.setBackground(PRIMARY_COLOR);
        headerLeft.setLayout(new BoxLayout(headerLeft, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("📋 My Reported Items");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Items you have reported");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(200, 230, 255));

        headerLeft.add(titleLabel);
        headerLeft.add(Box.createVerticalStrut(4));
        headerLeft.add(subtitleLabel);
        headerPanel.add(headerLeft, BorderLayout.WEST);

        String[] columns = {"ID", "Item Name", "Category", "Status", "Recovered", "Claims", "Action"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 6;
            }
        };

        itemTable = new JTable(tableModel);
        itemTable.setFont(new Font("Arial", Font.PLAIN, 13));
        itemTable.setRowHeight(38);
        itemTable.setGridColor(new Color(230, 230, 230));
        itemTable.setSelectionBackground(new Color(200, 230, 255));
        itemTable.setShowVerticalLines(false);
        itemTable.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = itemTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(TEXT_COLOR);
        header.setPreferredSize(new Dimension(0, 40));

        itemTable.getColumnModel().getColumn(0).setPreferredWidth(45);
        itemTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        itemTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        itemTable.getColumnModel().getColumn(3).setPreferredWidth(90);
        itemTable.getColumnModel().getColumn(4).setPreferredWidth(90);
        itemTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        itemTable.getColumnModel().getColumn(6).setPreferredWidth(140);

        itemTable.getColumnModel().getColumn(5).setCellRenderer(new ActionButtonRenderer(PRIMARY_COLOR));
        itemTable.getColumnModel().getColumn(5).setCellEditor(new ActionButtonEditor("claims"));
        itemTable.getColumnModel().getColumn(6).setCellRenderer(new ActionButtonRenderer(SUCCESS_COLOR));
        itemTable.getColumnModel().getColumn(6).setCellEditor(new ActionButtonEditor("recover"));

        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_COLOR);

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(CARD_COLOR);
        tableCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        tableCard.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        bottomPanel.setBackground(BACKGROUND_COLOR);

        JButton backButton = new JButton("← Back to Dashboard");
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
        backButton.addActionListener(e -> {
            new DashboardFrame(currentUser).setVisible(true);
            dispose();
        });

        bottomPanel.add(backButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tableCard, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);

        loadItems();
    }

    private void loadItems() {
        currentItems = itemController.getItemsByUserId(currentUser.getUserId());
        tableModel.setRowCount(0);

        for (Item item : currentItems) {
            tableModel.addRow(new Object[] {
                item.getItemId(),
                item.getItemName(),
                item.getCategory(),
                item.getStatus(),
                item.isRecovered() ? "Yes" : "No",
                "View Claims",
                item.isRecovered() ? "Undo Recovered" : "Mark Recovered"
            });
        }

        if (currentItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You have not reported any items yet.");
        }
    }

    private void showClaims(int itemId) {
        List<Object[]> claims = itemRepository.getClaimsByItemId(itemId);
        if (claims.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No claims yet.");
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (Object[] claim : claims) {
            builder.append("Claimer: ").append(claim[0]).append("\n")
                   .append("Message: ").append(claim[1]).append("\n")
                   .append("Date: ").append(claim[2]).append("\n")
                   .append("Status: ").append(claim[3]).append("\n\n");
        }

        JOptionPane.showMessageDialog(this, builder.toString().trim(), "Claims", JOptionPane.INFORMATION_MESSAGE);
    }

    private class ActionButtonRenderer extends JButton implements TableCellRenderer {
        private final Color enabledColor;

        ActionButtonRenderer(Color enabledColor) {
            this.enabledColor = enabledColor;
            setOpaque(true);
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 11));
            setFocusPainted(false);
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            String text = value == null ? "" : value.toString();
            setText(text);
            setEnabled(true);
            Color buttonColor = enabledColor;
            if (column == 6 && "Yes".equals(table.getValueAt(row, 4))) {
                buttonColor = DANGER_COLOR;
            }
            setBackground(buttonColor);
            setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(buttonColor, 1, true),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
            ));
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
            button.setEnabled(true);
            if ("claims".equals(actionType)) {
                button.setBackground(PRIMARY_COLOR);
            } else {
                button.setBackground("Yes".equals(table.getValueAt(row, 4)) ? DANGER_COLOR : SUCCESS_COLOR);
            }
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
            if ("claims".equals(actionType)) {
                showClaims(selectedItem.getItemId());
                return;
            }

            if (!selectedItem.isRecovered()) {
                boolean success = itemController.markItemRecovered(selectedItem.getItemId());
                if (success) {
                    JOptionPane.showMessageDialog(MyItemsFrame.this, "Item marked as recovered!");
                    loadItems();
                } else {
                    JOptionPane.showMessageDialog(MyItemsFrame.this,
                        "Failed to mark item as recovered.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                MyItemsFrame.this,
                "Are you sure you want to undo this?",
                "Confirm Undo",
                JOptionPane.YES_NO_OPTION
            );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            boolean success = itemController.markItemAsLost(selectedItem.getItemId());
            if (success) {
                JOptionPane.showMessageDialog(MyItemsFrame.this, "Item marked as lost again!");
                loadItems();
            } else {
                JOptionPane.showMessageDialog(MyItemsFrame.this,
                    "Failed to update item status.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
