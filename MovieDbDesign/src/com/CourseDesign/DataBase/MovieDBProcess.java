package com.CourseDesign.DataBase;

import com.CourseDesign.Movie;
import com.CourseDesign.Static.Information;

import java.sql.*;

public class MovieDBProcess {
    //连接数据库
    Connection connection = null;
    ResultSet rs = null;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/MovieDB";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
    //构造方法
    public MovieDBProcess() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Database connected successfully!");
            if (connection != null) {
                System.out.println("连接不成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //用户
    //1.用户注册
    public void regist_User(String user, String email, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("your_database_url", "username", "password"); // 替换为你的数据库连接信息

            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            System.out.println("用户注册成功！");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("用户注册失败", e);
        } finally {
            // 释放资源
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //2.用户登录
    public boolean login_User(String user, String password) throws SQLException {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String sql = "SELECT * FROM users WHERE username =? AND password =?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("用户登录成功！");
                return true;
            } else {
                System.out.println("用户登录失败！");
                return false;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //3.更改用户密码
    public static void changeUserInformation(String user, String password, String newpassword) throws SQLException {
        Connection conn = null; // 数据库连接
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 查询用户的当前密码
            String sqlQuery = "SELECT password FROM users WHERE username = ?";
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, user);
            rs = pstmt.executeQuery();

            // 验证旧密码
            if (rs.next()) {
                String currentPassword = rs.getString("password");
                if (currentPassword.equals(password)) {
                    // 旧密码正确，更新密码
                    String sqlUpdate = "UPDATE users SET password = ? WHERE username = ?";
                    pstmt = conn.prepareStatement(sqlUpdate);
                    pstmt.setString(1, newpassword);
                    pstmt.setString(2, user);
                    pstmt.executeUpdate();
                    System.out.println("密码更新成功");
                } else {
                    System.out.println("旧密码不正确");
                }
            } else {
                System.out.println("用户不存在");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 将异常抛出
        } finally {
            // 关闭资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //管理员
    //1.管理员注册
    public void regist_Manager(String manager, String email, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("your_database_url", "username", "password"); // 替换为你的数据库连接信息

            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, manager);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            System.out.println("管理员注册成功！");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("管理员注册失败", e);
        } finally {
            // 释放资源
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //2.管理员登录
    public boolean login_Manager(String manager, String password) throws SQLException {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String sql = "SELECT * FROM users WHERE username =? AND password =?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, manager);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("管理员登录成功！");
                return true;
            } else {
                System.out.println("管理员登录失败！");
                return false;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //3.更改管理员密码
    public static void changeManagerInformation(String manager, String password, String newpassword) throws SQLException {
        Connection conn = null; // 数据库连接
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 查询用户的当前密码
            String sqlQuery = "SELECT password FROM users WHERE username = ?";
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, manager);
            rs = pstmt.executeQuery();

            // 验证旧密码
            if (rs.next()) {
                String currentPassword = rs.getString("password");
                if (currentPassword.equals(password)) {
                    // 旧密码正确，更新密码
                    String sqlUpdate = "UPDATE users SET password = ? WHERE username = ?";
                    pstmt = conn.prepareStatement(sqlUpdate);
                    pstmt.setString(1, newpassword);
                    pstmt.setString(2, manager);
                    pstmt.executeUpdate();
                    System.out.println("密码更新成功");
                } else {
                    System.out.println("旧密码不正确");
                }
            } else {
                System.out.println("管理员不存在");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 将异常抛出
        } finally {
            // 关闭资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //电影
    //1.添加电影
    public void addMovie(String title, int releaseYear, String description, double rating, int duration) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Statement stmt = connection.createStatement();
        String sql = "INSERT INTO movies (title, release_year, description, rating, duration) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, releaseYear);
            pstmt.setString(3, description);
            pstmt.setDouble(4, rating);
            pstmt.setInt(5, duration);
            pstmt.executeUpdate();
            System.out.println("Movie added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //2.删除电影
    public void deleteMovie(String title) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Statement stmt = connection.createStatement();
        String sql = "DELETE FROM movies WHERE title = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.executeUpdate();
            System.out.println("Movie deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //3.查询电影(根据名字)
    public void search_MovieTitle(String title, int releaseYear, String description, double rating, int duration) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM movies WHERE title = ?";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Movie movie = new Movie();
                movie.movie_id = rs.getString(1);
                movie.title = rs.getString(2);
                movie.realseyear = rs.getString(3);
                movie.description = rs.getString(4);
                movie.rating = rs.getString(5);
                movie.duration = rs.getInt(6);
                Information.moviearray.add(movie);
                rs.close();
                stmt.close();
                connection.close();
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


//演员
//演员
//1.添加演员
public void addActor(String actor_id, String first_name, String works, String roles,String birth_date) throws SQLException {
    String sql = "INSERT INTO actors (actor_id, name, works, roles,birthdate) VALUES (?, ?, ?, ?,?)";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, actor_id);
        pstmt.setString(2, first_name);
        pstmt.setString(3, works);
        pstmt.setString(4, roles);
        pstmt.setString(5, birth_date);
        pstmt.executeUpdate();
        System.out.println("Actor added successfully.");
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Failed to add actor", e);
    }
}

    //2.删除演员
    public void deleteActor(String first_name) throws SQLException {
        String sql = "DELETE FROM actors WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, first_name);
            pstmt.executeUpdate();
            System.out.println("Actor deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to delete actor", e);
        }
    }

    //3.连接演员出演电影
    public void linkActorToMovie(String actorName, String movieTitle) throws SQLException {
        String sql = "INSERT INTO movie_actors (movie_id, actor_id) SELECT movie_id, actor_id FROM movies m, actors a WHERE m.title = ? AND a.name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, movieTitle);
            pstmt.setString(2, actorName);
            pstmt.executeUpdate();
            System.out.println("Actor linked to movie successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to link actor to movie", e);
        }
    }

    //4.查询演员出演电影的情况
    public void fetchActorMovies(String actorName) throws SQLException {
        String sql = "SELECT m.title, m.release_year FROM movies m " +
                "INNER JOIN movie_actors ma ON movie_id = ma.movie_id " +
                "INNER JOIN actors a ON ma.actor_id = actor_id WHERE actor_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, actorName);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Movies featuring actor " + actorName + ":");
            while (rs.next()) {
                String movieTitle = rs.getString("title");
                int releaseYear = rs.getInt("release_year");
                System.out.println("- " + movieTitle + " (" + releaseYear + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to fetch movies for actor", e);
        }
    }



    //新建用户数据库
    public static void create_newCustomer(String user) {
        try {
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动-newCustomer");

            String url = Information.DB_URL;    //JDBC的URL
            Connection conn;

            conn = DriverManager.getConnection(url, Information.username, Information.password);
            Statement stmt = conn.createStatement(); //创建Statement对象
            System.out.println("成功连接到数据库-newCustomer");

            String sql = "CREATE TABLE " + user + "Customer (number VARCHAR(255) primary key,classname VARCHAR(255),name VARCHAR(255),dateoff VARCHAR(255));";    //要执行的SQL

            PreparedStatement stmts = conn.prepareStatement(sql);
            stmts.executeUpdate();

            stmts.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static class DatabaseUtil {
        private static final String URL = "jdbc:mysql://localhost:3306/your_database_name"; // 替换为你的数据库名
        private static final String USERNAME = "your_username"; // 替换为你的数据库用户名
        private static final String PASSWORD = "your_password"; // 替换为你的数据库密码

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
    }
}
