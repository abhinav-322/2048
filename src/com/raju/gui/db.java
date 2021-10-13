package com.raju.gui;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.table.DefaultTableModel;
//import com.mysql.jdbc.ResultSetMetaData;

public class db {
    Connection con = null;
    public static Connection dbconnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/signup_2048","root","aniket123");
            return conn;
        }
        catch(Exception e2) {
            System.out.println(e2);
            return null;
        }
    }

}
