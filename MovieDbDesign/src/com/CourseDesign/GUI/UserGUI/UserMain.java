package com.CourseDesign.GUI.UserGUI;

import com.CourseDesign.DataBase.MovieDBProcess;
import com.CourseDesign.GUI.AdminGUI.AdminPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserMain extends JFrame {
    private String username;

    public UserMain(String username) {
        this.username = username; // 保存用户名
        setTitle("用户界面");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1)); // 更改为8行以适应新按钮

        JButton viewMoviesButton = new JButton("查看所有电影");
        JButton viewActorsButton = new JButton("查看所有演员");
        JButton viewUserInfoButton = new JButton("查看个人信息");
        JButton searchMovieButton = new JButton("根据电影名查询电影");
        JButton searchActorButton = new JButton("根据演员名查询演员");
        JButton logoutButton = new JButton("退出登录");


        // 添加按钮事件
        viewMoviesButton.addActionListener(e -> showAllMovies());
        viewActorsButton.addActionListener(e -> showAllActors());
        viewUserInfoButton.addActionListener(e -> viewUserInfo()); // 添加个人信息按钮事件
        searchMovieButton.addActionListener(e -> searchMovieByName());
        searchActorButton.addActionListener(e -> searchActorByName());
        logoutButton.addActionListener(e -> logout()); // 添加退出登录按钮事件

        panel.add(viewMoviesButton);
        panel.add(viewActorsButton);
        panel.add(viewUserInfoButton); // 添加个人信息按钮到面板
        panel.add(searchMovieButton);
        panel.add(searchActorButton);
        panel.add(logoutButton); // 添加退出登录按钮到面板
        add(panel);

        setLocationRelativeTo(null);
        setVisible(true);
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

    private void viewUserInfo() {
        MovieDBProcess dbProcess = new MovieDBProcess();
        try (Connection conn = dbProcess.getConnection()) {
            // 假设有一个用户表，查询当前用户的信息
            String sql = "SELECT username, email, password FROM users WHERE username = ?"; // 根据用户名查询
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, this.username); // 设置当前用户名作为查询参数
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String userInfo = "用户名: " + rs.getString("username") + "\n" +
                                "邮箱: " + rs.getString("email") + "\n" +
                                "密码: " + rs.getString("password"); // 假设有username, email, password字段
                        JOptionPane.showMessageDialog(this, userInfo);
                    } else {
                        JOptionPane.showMessageDialog(this, "未找到用户信息！");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "获取个人信息失败！");
        }
    }

    private void searchMovieByName() {
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
                                    "导演: " + rs.getString("director") + "\n" +
                                    "年份: " + rs.getString("year"); // 假设有title, director, year字段
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

    private void searchActorByName() {
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
                                    "作品: " + rs.getString("works") + "\n" + "出演角色" + rs.getString("roles"); // 假设有name, birthdate, nationality字段
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

    private void logout() {
        // 关闭当前窗口
        this.dispose();
        new UserPanel(this);

    }
}
