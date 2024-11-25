//package com.CourseDesign.GUI.UserGUI;
//
//
//import com.CourseDesign.DataBase.MovieDBProcess;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.sql.*;
//
//
//
//public class UserPanel extends JFrame {
//    private final JFrame parentFrame;
//
//    public UserPanel(JFrame parentFrame) {
//        this.parentFrame = parentFrame;
//
//        setTitle("普通用户");
//        setSize(400, 300);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        JLabel label = new JLabel("请选择你的操作：", SwingConstants.CENTER);
//        label.setFont(new Font("微软雅黑", Font.BOLD, 16));
//        add(label, BorderLayout.NORTH);
//
//        JPanel buttonPanel = new JPanel();
//        JButton loginButton = new JButton("登录");
//        JButton registerButton = new JButton("注册");
//
//        loginButton.addActionListener(this::openLoginPanel);
//        registerButton.addActionListener(this::openRegisterPanel);
//
//        buttonPanel.add(loginButton);
//        buttonPanel.add(registerButton);
//        add(buttonPanel, BorderLayout.CENTER);
//
//        JButton backButton = new JButton("返回");
//        backButton.addActionListener(e -> {
//            parentFrame.setVisible(true);
//            dispose();
//        });
//        add(backButton, BorderLayout.SOUTH);
//
//        setLocationRelativeTo(null);
//        setVisible(true);
//
//    }
//
//    private void openLoginPanel(ActionEvent e) {
//        JDialog loginDialog = new JDialog(this, "用户登录", true);
//        loginDialog.setLayout(new GridLayout(3, 2));
//
//        JLabel usernameLabel = new JLabel("用户名:");
//        JTextField usernameField = new JTextField();
//        JLabel passwordLabel = new JLabel("密码:");
//        JPasswordField passwordField = new JPasswordField();
//
//        JButton loginButton = new JButton("登录");
//        JButton cancelButton = new JButton("取消");
//
//        // 登录按钮事件
//        loginButton.addActionListener(evt -> {
//            String username = usernameField.getText();
//            String password = new String(passwordField.getPassword());
//
//            MovieDBProcess dbProcess = new MovieDBProcess();
//            Connection conn = null;
//            try {
//                conn = dbProcess.getConnection();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            try {
//                String sql = "SELECT password FROM users WHERE username = ?";
//                try (PreparedStatement pst = conn.prepareStatement(sql)) {
//                    pst.setString(1, username);
//                    ResultSet rs = pst.executeQuery();
//                    if (rs.next()) {
//                        String storedPassword = rs.getString("password");
//                        if (storedPassword.equals(password)) {
//                            JOptionPane.showMessageDialog(loginDialog, "登录成功！");
//                            loginDialog.dispose();
//                        } else {
//                            JOptionPane.showMessageDialog(loginDialog, "密码错误，请重新输入！");
//                        }
//                    } else {
//                        JOptionPane.showMessageDialog(loginDialog, "用户不存在，请注册！");
//                    }
//                }
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(loginDialog, "登录失败，请稍后再试！");
//                ex.printStackTrace();
//            } finally {
//                dbProcess.closeConnection();
//            }
//        });
//
//        // 取消按钮事件
//        cancelButton.addActionListener(evt -> loginDialog.dispose());
//
//        loginDialog.add(usernameLabel);
//        loginDialog.add(usernameField);
//        loginDialog.add(passwordLabel);
//        loginDialog.add(passwordField);
//        loginDialog.add(loginButton);
//        loginDialog.add(cancelButton);
//
//        loginDialog.setSize(300, 150);
//        loginDialog.setLocationRelativeTo(this);
//        loginDialog.setVisible(true);
//    }
//
//    private void openRegisterPanel(ActionEvent e) {
//        JDialog registerDialog = new JDialog(this, "用户注册", true);
//        registerDialog.setLayout(new GridLayout(4, 2));
//
//        JLabel usernameLabel = new JLabel("用户名:");
//        JTextField usernameField = new JTextField();
//        JLabel passwordLabel = new JLabel("密码:");
//        JPasswordField passwordField = new JPasswordField();
//        JLabel confirmPasswordLabel = new JLabel("确认密码:");
//        JPasswordField confirmPasswordField = new JPasswordField();
//
//        JButton registerButton = new JButton("注册");
//        JButton cancelButton = new JButton("取消");
//
//        // 注册按钮事件
//        registerButton.addActionListener(evt -> {
//            String username = usernameField.getText();
//            String password = new String(passwordField.getPassword());
//            String confirmPassword = new String(confirmPasswordField.getPassword());
//
//            if (!password.equals(confirmPassword)) {
//                JOptionPane.showMessageDialog(registerDialog, "密码不匹配，请重新输入！");
//                return;
//            }
//
//            MovieDBProcess dbProcess = new MovieDBProcess();
//            Connection conn = null;
//            try {
//                conn = dbProcess.getConnection();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            try {
//                String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
//                try (PreparedStatement pst = conn.prepareStatement(sql)) {
//                    pst.setString(1, username);
//                    pst.setString(2, password);
//                    pst.executeUpdate();
//                    JOptionPane.showMessageDialog(registerDialog, "注册成功！");
//                }
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(registerDialog, "注册失败，用户名可能已存在！");
//                ex.printStackTrace();
//            } finally {
//                dbProcess.closeConnection();
//            }
//
//            registerDialog.dispose(); // 关闭对话框
//        });
//
//        cancelButton.addActionListener(evt -> registerDialog.dispose());
//
//        registerDialog.add(usernameLabel);
//        registerDialog.add(usernameField);
//        registerDialog.add(passwordLabel);
//        registerDialog.add(passwordField);
//        registerDialog.add(confirmPasswordLabel);
//        registerDialog.add(confirmPasswordField);
//        registerDialog.add(registerButton);
//        registerDialog.add(cancelButton);
//
//        registerDialog.setSize(300, 200);
//        registerDialog.setLocationRelativeTo(this);
//        registerDialog.setVisible(true);
//    }
//
//}

package com.CourseDesign.GUI.UserGUI;

import com.CourseDesign.DataBase.MovieDBProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class UserPanel extends JFrame {
    private final JFrame parentFrame;

    public UserPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        initializeFrame();
    }

    private void initializeFrame() {
        setTitle("普通用户");
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
        JDialog loginDialog = createDialog("用户登录");
        JPanel loginPanel = createLoginPanel(loginDialog);
        loginDialog.add(loginPanel);
        loginDialog.setVisible(true);
    }

    private JPanel createLoginPanel(JDialog loginDialog) {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("用户名:"));
        panel.add(usernameField);
        panel.add(new JLabel("密码:"));
        panel.add(passwordField);

        JButton loginButton = new JButton("登录");
        JButton cancelButton = new JButton("取消");

        loginButton.addActionListener(evt -> performLogin(loginDialog, usernameField.getText(), new String(passwordField.getPassword())));
        cancelButton.addActionListener(evt -> loginDialog.dispose());

        panel.add(loginButton);
        panel.add(cancelButton);

        return panel;
    }

    private void performLogin(JDialog loginDialog, String username, String password) {
        MovieDBProcess dbProcess = new MovieDBProcess();
        try (Connection conn = dbProcess.getConnection()) {
            String sql = "SELECT password FROM users WHERE username = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    if (storedPassword.equals(password)) {
                        JOptionPane.showMessageDialog(loginDialog, "登录成功！");
                        loginDialog.dispose();
                        // 登录成功，打开 UserMain 界面
                        new UserMain(username); // 传入用户名
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
        JDialog registerDialog = createDialog("用户注册");
        JPanel registerPanel = createRegisterPanel(registerDialog);
        registerDialog.add(registerPanel);
        registerDialog.setVisible(true);
    }

    private JPanel createRegisterPanel(JDialog registerDialog) {
        JPanel panel = new JPanel(new GridLayout(5, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        JTextField emailField = new JTextField();  // 新增电子邮件字段

        panel.add(new JLabel("用户名:"));
        panel.add(usernameField);
        panel.add(new JLabel("电子邮件:"));  // 增加电子邮件标签
        panel.add(emailField);  // 增加电子邮件输入框
        panel.add(new JLabel("密码:"));
        panel.add(passwordField);
        panel.add(new JLabel("确认密码:"));
        panel.add(confirmPasswordField);


        JButton registerButton = new JButton("注册");
        JButton cancelButton = new JButton("取消");

        registerButton.addActionListener(evt -> performRegistration(registerDialog, usernameField.getText(), new String(passwordField.getPassword()), new String(confirmPasswordField.getPassword()), emailField.getText()));
        cancelButton.addActionListener(evt -> registerDialog.dispose());

        panel.add(registerButton);
        panel.add(cancelButton);

        return panel;
    }

    private void performRegistration(JDialog registerDialog, String username, String password, String confirmPassword, String email) {
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(registerDialog, "密码不匹配，请重新输入！");
            return;
        }

        if (email.isEmpty()) {  // 检查电子邮件字段是否为空
            JOptionPane.showMessageDialog(registerDialog, "电子邮件不能为空，请输入！");
            return;
        }

        MovieDBProcess dbProcess = new MovieDBProcess();
        try (Connection conn = dbProcess.getConnection()) {
            String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, username);
                pst.setString(2, password);
                pst.setString(3, email);  // 将电子邮件插入到数据库
                pst.executeUpdate();
                JOptionPane.showMessageDialog(registerDialog, "注册成功！");
                registerDialog.dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(registerDialog, "注册失败，用户名或电子邮件可能已存在！");
            ex.printStackTrace();
        }
    }


    private void performRegistration(JDialog registerDialog, String username, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(registerDialog, "密码不匹配，请重新输入！");
            return;
        }

        MovieDBProcess dbProcess = new MovieDBProcess();
        try (Connection conn = dbProcess.getConnection()) {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, username);
                pst.setString(2, password);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(registerDialog, "注册成功！");
                registerDialog.dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(registerDialog, "注册失败，用户名可能已存在！");
            ex.printStackTrace();
        }
    }

    private JDialog createDialog(String title) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(1, 1));
        return dialog;
    }
}
