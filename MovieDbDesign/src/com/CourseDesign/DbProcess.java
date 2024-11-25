//package com.CourseDesign;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class DbProcess{
//    Connection connection = null;
//    ResultSet rs = null;
//
//    //mysql?????url
//    String userMySql="root";
//    String passwordMySql="123456";
//    String urlMySql = "jdbc:mysql://localhost:3306/InfoDb?user="
//            +userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=gbk";
//
//    //sqlserver?????url
//    //String urlSqlServer = "jdbc:sqlserver://localhost:1433;integratedSecurity=true;DatabaseName=InfoDb";
//
//    public DbProcess() {
//        try {
//            //mysql?????????????????????
//            Class.forName("com.mysql.jdbc.Driver");
//            System.out.println("mysql???????????????");
//
//            //sqlserver?????????????????????
//            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            //System.out.println("sqlserver???????????????");
//
//        }
//        catch(ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void connect(){
//        try{
//            //mysql?????
//            connection = DriverManager.getConnection(urlMySql);
//
//            //sqlserver?????
//            //connection = DriverManager.getConnection(urlSqlServer);
//
//
//            if(connection!=null){
//                System.out.println("???????????");
//            }
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void disconnect(){
//        try{
//            if(connection != null){
//                connection.close();
//                connection = null;
//            }
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//
//    public ResultSet executeQuery(String sql) {
//        try {
//            System.out.println("executeQuery(). sql = " + sql);
//
//            PreparedStatement pstm = connection.prepareStatement(sql);
//            // ??в??
//            rs = pstm.executeQuery();
//        }
//        catch(SQLException ex) {
//            ex.printStackTrace();
//        }
//        return rs;
//    }
//
//    //????
//    //executeUpdate ????????????????????????????????????????????
//    //executeUpdate??????? INSERT??UPDATE ?? DELETE ???
//    //??? SQL DDL????????????????????? CREATE TABLE ?? DROP TABLE??
//
//    //???????????????????
//    public int executeUpdate(String sql) {
//        int count = 0;
//        connect();
//        try {
//            Statement stmt = connection.createStatement();
//            count = stmt.executeUpdate(sql);
//        }
//        catch(SQLException ex) {
//            System.err.println(ex.getMessage());
//        }
//        disconnect();
//        return count;
//    }
//}
//
//
