package com.CourseDesign.GUI;

import com.CourseDesign.GUI.AdminGUI.AdminPanel;
import com.CourseDesign.GUI.UserGUI.UserPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("主界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("请选择用户类型", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
        titleLabel.setForeground(Color.BLACK); // 设置字体颜色
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton userButton = new JButton("普通用户");
        JButton adminButton = new JButton("管理员");

        userButton.addActionListener(this::openUserPanel);
        adminButton.addActionListener(this::openAdminPanel);

        buttonPanel.add(userButton);
        buttonPanel.add(adminButton);
        add(buttonPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openUserPanel(ActionEvent e) {
        new UserPanel(this);
        setVisible(false);
    }

    private void openAdminPanel(ActionEvent e) {
        new AdminPanel(this);
        setVisible(false);
    }
}
