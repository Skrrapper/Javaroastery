package com.CourseDesign.Static;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;
import com.CourseDesign.Movie;
import com.CourseDesign.Actor;

public class Information {
    //数据库用户名
    public static String username="root";
    //密码
    public static String password="123456";
    //URL
    public static String DB_URL = "jdbc:mysql://localhost:3306/MovieDB";
    //查询电影名称
    public static String search_moviename="";
    //查询电影列表
    public static ArrayList<Movie> moviearray=new ArrayList<Movie>();
    //查询演员名称
    public static String search_actorname="";
    //查询演员列表
    public static ArrayList<Actor> actorarray=new ArrayList<Actor>();
    //当前管理员
    public static String manager = "";
    //当前用户
    public static String user = "";
    //查询Table列表
    public static ArrayList<String> tablearray = new ArrayList<String>();
}


