// Java program to implement
package com.raju.gui;
import com.raju.game.Game;
import com.raju.game.Leaderboards;
import com.raju.gui.db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login
        extends JFrame
        implements ActionListener {

    // Components of the Form
    private Container c;
    private JLabel title;
    private JLabel username;
    private JTextField tname;
    private JLabel password;
    private JPasswordField pass;
    private JLabel add;
    private JTextArea tadd;
    private JCheckBox term;
    private JButton sub;
    private JButton reset;
    private JTextArea tout;
    private JLabel res;
    private JTextArea resadd;
    Connection con = null;
    private int buttonWidth = 220;
    private int buttonHeight = 60;
    public Login()
    {
        con= db.dbconnect();

        setTitle("Login");
        setBounds(700, 350, 500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setSize(300, 30);
        title.setLocation(200,30);
        c.add(title);

        username = new JLabel("Username");
        username.setFont(new Font("Arial", Font.BOLD, 20));
        username.setSize(100, 20);
        username.setLocation(100, 100);
        c.add(username);

        tname = new JTextField();
        tname.setFont(new Font("Arial", Font.PLAIN, 15));
        tname.setSize(190, 20);
        tname.setLocation(200, 100);
        c.add(tname);

        password = new JLabel("Password");
        password.setFont(new Font("Arial", Font.BOLD, 20));
        password.setSize(100, 20);
        password.setLocation(100, 150);
        c.add(password);

        pass = new JPasswordField();
        pass.setFont(new Font("Arial", Font.PLAIN, 15));
        pass.setSize(150, 20);
        pass.setLocation(200, 150);
        c.add(pass);

        sub = new JButton("Login");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(190, 225);

        sub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String user = tname.getText();
                    String pwd = pass.getText();
                    PreparedStatement pst = con.prepareStatement("select * from signup where Username=? and Password=?;");
                    pst.setString(1, user);
                    pst.setString(2, pwd);
                    ResultSet r=pst.executeQuery();
                    if(r.next()) {
//                        GuiScreen.getInstance().setCurrentPanel("Play");
//                        System.out.println("Login button clicked");
//                        Login login = new Login();
                        GameModePanel d= new GameModePanel();
                        d.setVisible(true);
//                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        setVisible(false);
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "incorrect username or password");
                    }

                }
                catch(Exception e3) {
                    System.out.println(e3);
                }
            }
        });
        c.add(sub);
//        add(login);

        setVisible(true);
    }

    private void add(GuiButton login) {
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == sub) {
            if (term.isSelected()) {
                String data1;
                String data
                        = "Username : "
                        + tname.getText() + "\n"
                        + "Password : "
                        + pass.getPassword() + "\n";
                tout.setEditable(false);
                res.setText("Registration Successfully..");
            }
            else {
                tout.setText("");
                resadd.setText("");
                res.setText("Incorrect username or password");
            }
        }

        else if (e.getSource() == reset) {
            String def = "";
            tname.setText(def);
            tadd.setText(def);
            res.setText(def);
            tout.setText(def);
            term.setSelected(false);
            resadd.setText(def);
        }
    }
}