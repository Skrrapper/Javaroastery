package com.CourseDesign.GUI.AdminGUI;

import com.CourseDesign.DataBase.MovieDBProcess;
import com.CourseDesign.GUI.UserGUI.UserPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminMain extends JFrame {
    private String adminUsername;
    private String adminname;

    public AdminMain(String username) {
        this.adminUsername = username; // 保存管理员用户名
        setTitle("管理员界面");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));

        JButton viewMoviesButton = new JButton("查看所有电影");
        JButton viewActorsButton = new JButton("查看所有演员");
        JButton modifyMoviesButton = new JButton("修改电影");
        JButton modifyActorsButton = new JButton("修改演员");
        JButton searchMovieButton = new JButton("查询电影");
        JButton searchActorButton = new JButton("查询演员");
        JButton viewProfileButton = new JButton("查看个人信息");
        JButton logoutButton = new JButton("退出登录");


        panel.add(viewMoviesButton);
        panel.add(viewActorsButton);
        panel.add(modifyMoviesButton);
        panel.add(modifyActorsButton);
        panel.add(searchMovieButton);
        panel.add(searchActorButton);
        panel.add(viewProfileButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
        panel.add(logoutButton); // 添加退出登录按钮到面板


        // 添加按钮事件监听
        viewMoviesButton.addActionListener(e -> showAllMovies());
        viewActorsButton.addActionListener(e -> showAllActors());
        modifyMoviesButton.addActionListener(e -> modifyMovies());
        modifyActorsButton.addActionListener(e -> modifyActors());
        searchMovieButton.addActionListener(e -> searchMovie());
        searchActorButton.addActionListener(e -> searchActor());
        viewProfileButton.addActionListener(e -> showProfile());
        logoutButton.addActionListener(e -> logout()); // 添加退出登录按钮事件

    }

    // 查看所有电影的逻辑
    private void showAllMovies() {
        MovieDBProcess dbProcess = new MovieDBProcess();
        try (Connection conn = dbProcess.getConnection()) {
            String sql = "SELECT * FROM movies"; // 假设存在movies表
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                StringBuilder movies = new StringBuilder("电影列表:\n");
                while (rs.next()) {
                    // 假设字段为id, title, director, release_year, genre
                    movies.append("名字: ").append(rs.getString("title")).append("\n")
                            .append("简介: ").append(rs.getString("description")).append("\n")
                            .append("上映年份: ").append(rs.getString("release_year")).append("\n")
                            .append("评论: ").append(rs.getString("rating")).append("\n")
                            .append("时长: ").append(rs.getInt("duration")).append("\n")
                            .append("--------------------\n"); // 分隔每部电影的信息
                }
                JOptionPane.showMessageDialog(this, movies.toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "获取电影失败！");
        }
    }


    // 查看所有演员的逻辑
    private void showAllActors() {
        MovieDBProcess dbProcess = new MovieDBProcess();
        try (Connection conn = dbProcess.getConnection()) {
            String sql = "SELECT * FROM actors"; // 假设存在actors表
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                StringBuilder actors = new StringBuilder("演员列表:\n");
                while (rs.next()) {
                    // 假设字段为actor_id, name, works, birthdate, roles
                    actors.append("演员ID: ").append(rs.getInt("actor_id")).append("\n")
                            .append("姓名: ").append(rs.getString("name")).append("\n")
                            .append("作品: ").append(rs.getString("works")).append("\n")
                            .append("出生日期: ").append(rs.getDate("birthdate")).append("\n")
                            .append("角色: ").append(rs.getString("roles")).append("\n")
                            .append("--------------------\n"); // 分隔每个演员的信息
                }
                JOptionPane.showMessageDialog(this, actors.toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "获取演员失败！");
        }
    }

    // 增加和删除电影的逻辑
    private void modifyMovies() {
        String[] options = {"增加电影", "删除电影"};
        int choice = JOptionPane.showOptionDialog(this, "请选择操作:", "修改电影",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        MovieDBProcess dbProcess = new MovieDBProcess();
        try (Connection conn = dbProcess.getConnection()) {
            if (choice == 0) { // 增加电影
                String title = JOptionPane.showInputDialog(this, "请输入电影标题:");
                String release_year = JOptionPane.showInputDialog(this, "请输入发行日期:");
                String description = JOptionPane.showInputDialog(this, "请输入简介:");
                String rating = JOptionPane.showInputDialog(this, "请输入评价:");
                String durationSTR = JOptionPane.showInputDialog(this, "请输入电影时长:");
                int duration = Integer.parseInt(durationSTR); // 处理的年龄

                String sql = "INSERT INTO movies (title, release_year, description,rating,duration) VALUES (?, ?, ?, ?,?)";
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, title);
                    pst.setString(2, release_year);
                    pst.setString(3, description);
                    pst.setString(4, rating);
                    pst.setInt(5, duration);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(this, "电影增加成功！");
                }
            } else if (choice == 1) { // 删除电影
                String title = JOptionPane.showInputDialog(this, "请输入要删除的电影标题:");
                String sql = "DELETE FROM movies WHERE title = ?";
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, title);
                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "电影删除成功！");
                    } else {
                        JOptionPane.showMessageDialog(this, "未找到指定的电影！");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "操作失败！");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "输入的年份无效！");
        }
    }

    // 增加和删除演员的逻辑
    private void modifyActors() {
        String[] options = {"增加演员", "删除演员"};
        int choice = JOptionPane.showOptionDialog(this, "请选择操作:", "修改演员",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        MovieDBProcess dbProcess = new MovieDBProcess();
        try (Connection conn = dbProcess.getConnection()) {
            if (choice == 0) { // 增加演员
                String name = JOptionPane.showInputDialog(this, "请输入演员姓名:");
                String works = JOptionPane.showInputDialog(this, "请输入演出作品:");
                String birthdate = JOptionPane.showInputDialog(this, "请输入演员出生日期:");
String roles = JOptionPane.showInputDialog(this, "请输入出演角色:");

                String sql = "INSERT INTO actors (name, birthdate, works,roles) VALUES (?, ?, ?,?)";
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, name);
                    pst.setString(2, birthdate);
                    pst.setString(3, works);
                    pst.setString(4, roles);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(this, "演员增加成功！");
                }
            } else if (choice == 1) { // 删除演员
                String name = JOptionPane.showInputDialog(this, "请输入要删除的演员姓名:");
                String sql = "DELETE FROM actors WHERE name = ?";
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, name);
                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "演员删除成功！");
                    } else {
                        JOptionPane.showMessageDialog(this, "未找到指定的演员！");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "操作失败！");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "年龄输入无效！");
        }
    }


    // 查询电影的逻辑
    private void searchMovie() {
        String movieName = JOptionPane.showInputDialog(this, "请输入电影名:");
        if (movieName != null && !movieName.isEmpty()) {
            MovieDBProcess dbProcess = new MovieDBProcess();
            try (Connection conn = dbProcess.getConnection()) {
                String sql = "SELECT * FROM movies WHERE title = ?"; // 根据电影名查询
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, movieName); // 设置电影名作为查询参数
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            String movieInfo = "电影名: " + rs.getString("title") + "\n" +
                                    "发行日期: " + rs.getString("release_year") + "\n" +
                                    "简介: " + rs.getString("description") + "\n" +
                                    "评价: " + rs.getString("rating") + "\n" +
                                    "时长: " + rs.getInt("duration") + "分钟"; // 与数据库结构相符
                            JOptionPane.showMessageDialog(this, movieInfo);
                        } else {
                            JOptionPane.showMessageDialog(this, "未找到该电影！");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "查询电影失败！");
            }
        }
    }

    // 查询演员的逻辑
    private void searchActor() {
        String actorName = JOptionPane.showInputDialog(this, "请输入演员名:");
        if (actorName != null && !actorName.isEmpty()) {
            MovieDBProcess dbProcess = new MovieDBProcess();
            try (Connection conn = dbProcess.getConnection()) {
                String sql = "SELECT * FROM actors WHERE name = ?"; // 根据演员名查询
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, actorName); // 设置演员名作为查询参数
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            String actorInfo = "演员名: " + rs.getString("name") + "\n" +
                                    "出生日期: " + rs.getString("birthdate") + "\n" +
                                    "作品: " + rs.getString("works") + "\n" +
                                    "出演角色: " + rs.getString("roles"); // 修正为只查询已定义的字段
                            JOptionPane.showMessageDialog(this, actorInfo);
                        } else {
                            JOptionPane.showMessageDialog(this, "未找到该演员！");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "查询演员失败！");
            }
        }
    }

    // 查看个人信息的逻辑
    // 查看个人信息的逻辑
    private void showProfile() {
        MovieDBProcess dbProcess = new MovieDBProcess();
        try (Connection conn = dbProcess.getConnection()) {
            // 查询当前管理员的信息
            String sql = "SELECT adminUsername, email, password FROM admins WHERE adminUsername = ?"; // 根据用户名查询
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, this.adminUsername); // 使用正确的adminUsername属性
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String adminInfo = "管理员用户名: " + rs.getString("adminUsername") + "\n" +
                                "邮箱: " + rs.getString("email") + "\n" +
                                "密码: " + rs.getString("password");
                        JOptionPane.showMessageDialog(this, adminInfo);
                    } else {
                        JOptionPane.showMessageDialog(this, "未找到管理员信息！");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "获取管理员信息失败！");
        }
    }


    private void logout() {
        // 关闭当前窗口
        this.dispose();
        new AdminPanel(this);

    }
}
