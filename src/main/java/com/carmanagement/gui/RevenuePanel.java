package com.carmanagement.gui;

import com.carmanagement.entity.Invoice;
import com.carmanagement.service.RevenueService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RevenuePanel extends JPanel {
    private final JComboBox<Integer> cbMonth;
    private final JComboBox<Integer> cbYear;
    private final JLabel lblCars;
    private final JLabel lblRevenue;
    private final JTable table;
    private final DefaultTableModel model;
    private final RevenueService service = new RevenueService();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public RevenuePanel() {
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(16, 16, 16, 16));

        cbMonth = new JComboBox<>();
        cbYear = new JComboBox<>();
        lblCars = createSummaryValue("0");
        lblRevenue = createSummaryValue("0 VND");

        String[] cols = {"Mã hóa đơn", "Mã khách", "Mã nhân viên", "Ngày lập", "Tổng tiền", "Thanh toán"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);

        add(topPanel(), BorderLayout.NORTH);
        add(centerPanel(), BorderLayout.CENTER);

        loadData();
    }

    private JPanel topPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        for (int i = 1; i <= 12; i++) {
            cbMonth.addItem(i);
        }

        int currentYear = Year.now().getValue();
        for (int i = currentYear - 5; i <= currentYear; i++) {
            cbYear.addItem(i);
        }

        cbMonth.setSelectedItem(LocalDate.now().getMonthValue());
        cbYear.setSelectedItem(currentYear);

        JButton btnLoad = new JButton("Thống kê");
        btnLoad.addActionListener(e -> loadData());

        panel.add(new JLabel("Tháng:"));
        panel.add(cbMonth);
        panel.add(new JLabel("Năm:"));
        panel.add(cbYear);
        panel.add(btnLoad);
        return panel;
    }

    private JPanel centerPanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));

        JPanel summaryPanel = new JPanel(new GridLayout(1, 2, 12, 12));
        summaryPanel.add(createSummaryCard("Tổng xe đã bán", lblCars, new Color(232, 244, 255)));
        summaryPanel.add(createSummaryCard("Tổng doanh thu", lblRevenue, new Color(247, 240, 225)));

        table.setRowHeight(28);
        table.getTableHeader().setReorderingAllowed(false);

        JPanel tablePanel = new JPanel(new BorderLayout(8, 8));
        JLabel title = new JLabel("Danh sách hóa đơn trong kỳ");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        tablePanel.add(title, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        panel.add(summaryPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSummaryCard(String title, JLabel valueLabel, Color background) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(background);
        panel.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        return panel;
    }

    private JLabel createSummaryValue(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        return label;
    }

    private void loadData() {
        int month = (int) cbMonth.getSelectedItem();
        int year = (int) cbYear.getSelectedItem();

        int cars = service.getTotalCarsSold(month, year);
        double revenue = service.getTotalRevenue(month, year);
        List<Invoice> invoices = service.getInvoicesByMonthYear(month, year);

        lblCars.setText(String.valueOf(cars));
        lblRevenue.setText(String.format("%,.0f VND", revenue));

        model.setRowCount(0);
        for (Invoice invoice : invoices) {
            model.addRow(new Object[]{
                    invoice.getId_invoice(),
                    invoice.getId_customer(),
                    invoice.getId_employee(),
                    invoice.getDate_invoice() != null ? invoice.getDate_invoice().format(formatter) : "",
                    String.format("%,d", invoice.getTotal_amount_invoice() != null ? invoice.getTotal_amount_invoice() : 0),
                    invoice.getPayment()
            });
        }
    }
}
