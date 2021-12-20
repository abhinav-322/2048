package com.raju.gui;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
//import com.mysql.jdbc.ResultSetMetaData;

public class DBTest {
//    Connection con = null;
//    public static Connection dbconnect() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/signup_2048","root","aniket123");
//            return conn;
//        }
//        catch(Exception e2) {
//            System.out.println(e2);
//            return null;
//        }
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/signup";
        Connection myConn = DriverManager.getConnection(url, "root", "");

    }





}
