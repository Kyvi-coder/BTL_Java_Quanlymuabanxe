package com.carmanagement.gui;

import com.carmanagement.entity.AuditLog;
import com.carmanagement.service.AuditLogService;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AuditLogPanel extends JPanel {
    private final DefaultTableModel model;
    private final AuditLogService service = new AuditLogService();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public AuditLogPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel top = new JPanel(new BorderLayout());
        JLabel title = new JLabel("LỊCH SỬ THAO TÁC");
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> refreshData());

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.add(btnRefresh);

        top.add(title, BorderLayout.WEST);
        top.add(right, BorderLayout.EAST);

        String[] cols = {"Thời gian", "Nhân viên", "Vai trò", "Hành động", "Đối tượng", "Mã đối tượng", "Chi tiết"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(28);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshData();
    }

    public void refreshData() {
        model.setRowCount(0);
        List<AuditLog> logs = service.getAllLogs();
        for (AuditLog log : logs) {
            model.addRow(new Object[]{
                    log.getCreatedAt() != null ? log.getCreatedAt().format(formatter) : "",
                    log.getActorName(),
                    log.getActorRole(),
                    log.getActionType(),
                    log.getTargetType(),
                    log.getTargetId(),
                    log.getDetails()
            });
        }
    }
}
