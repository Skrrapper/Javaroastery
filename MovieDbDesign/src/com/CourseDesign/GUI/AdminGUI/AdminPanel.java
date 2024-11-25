package com.CourseDesign.GUI.AdminGUI;

import com.CourseDesign.DataBase.MovieDBProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class AdminPanel extends JFrame {
    private final JFrame parentFrame;

    public AdminPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        initializeFrame();
    }

    private void initializeFrame() {
        setTitle("管理员");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = createLabel("请选择你的操作：", 16);
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.CENTER);

        JButton backButton = createBackButton();
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JLabel createLabel(String text, int fontSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("微软雅黑", Font.BOLD, fontSize));
        return label;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("登录");
        JButton registerButton = new JButton("注册");

        loginButton.addActionListener(this::openLoginPanel);
        registerButton.addActionListener(this::openRegisterPanel);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        return buttonPanel;
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("返回");
        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });
        return backButton;
    }

    private void openLoginPanel(ActionEvent e) {
        JDialog loginDialog = createDialog("管理员登录");
        JPanel panel = createLoginPanel(loginDialog);
        loginDialog.add(panel);
        loginDialog.setVisible(true);
    }

    private JPanel createLoginPanel(JDialog loginDialog) {
        JPanel panel = new JPanel(null);

        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("登录");
        loginButton.setBounds(10, 90, 80, 25);
        loginButton.addActionListener(e -> performLogin(loginDialog, userText.getText(), new String(passwordText.getPassword())));
        panel.add(loginButton);

        return panel;
    }

    private void performLogin(JDialog loginDialog, String username, String password) {
        MovieDBProcess dbProcess = new MovieDBProcess();
        try (Connection conn = dbProcess.getConnection()) {
            String sql = "SELECT password FROM admins WHERE adminUsername = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    if (storedPassword.equals(password)) { // 请注意：真实应用中应使用哈希比较
                        JOptionPane.showMessageDialog(loginDialog, "登录成功！");
                        loginDialog.dispose();
                        new AdminMain(username); // 登录成功后打开AdminMain
                    } else {
                        JOptionPane.showMessageDialog(loginDialog, "密码错误，请重新输入！");
                    }
                } else {
                    JOptionPane.showMessageDialog(loginDialog, "用户不存在，请注册！");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(loginDialog, "登录失败，请稍后再试！");
            ex.printStackTrace();
        }
    }


    private void openRegisterPanel(ActionEvent e) {
        JDialog registerDialog = createDialog("管理员注册");
        JPanel panel = createRegisterPanel(registerDialog);
        registerDialog.add(panel);
        registerDialog.setVisible(true);
    }

    private JPanel createRegisterPanel(JDialog registerDialog) {
        JPanel panel = new JPanel(null);

        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel emailLabel = new JLabel("电子邮件:");
        emailLabel.setBounds(10, 50, 80, 25);
        panel.add(emailLabel);

        JTextField emailText = new JTextField(20);
        emailText.setBounds(100, 50, 165, 25);
        panel.add(emailText);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(10, 80, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 80, 165, 25);
        panel.add(passwordText);

        JLabel confirmPasswordLabel = new JLabel("确认密码:");
        confirmPasswordLabel.setBounds(10, 110, 80, 25);
        panel.add(confirmPasswordLabel);

        JPasswordField confirmPasswordText = new JPasswordField(20);
        confirmPasswordText.setBounds(100, 110, 165, 25);
        panel.add(confirmPasswordText);

        JButton registerButton = new JButton("注册");
        registerButton.setBounds(10, 140, 80, 25);
        registerButton.addActionListener(e -> performRegistration(registerDialog, userText.getText(), emailText.getText(), new String(passwordText.getPassword()), new String(confirmPasswordText.getPassword())));
        panel.add(registerButton);

        return panel;
    }

    private void performRegistration(JDialog registerDialog, String username, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(registerDialog, "密码不匹配，请重新输入！");
            return;
        }

        MovieDBProcess dbProcess = new MovieDBProcess();
        try (Connection conn = dbProcess.getConnection()) {
            String sql = "INSERT INTO admins (adminUsername, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, username);
                pst.setString(2, email);
                pst.setString(3, password); // 请注意：真实应用中应使用密码哈希
                pst.executeUpdate();
                JOptionPane.showMessageDialog(registerDialog, "注册成功！");
                registerDialog.dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(registerDialog, "注册失败，用户名或电子邮件可能已存在！");
            ex.printStackTrace();
        }
    }

    private JDialog createDialog(String title) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(300, 250);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(this);
        return dialog;
    }
}
