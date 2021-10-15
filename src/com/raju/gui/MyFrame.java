// Java program to implement
package com.raju.gui;
import com.raju.gui.db;

import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class MyFrame
        extends JFrame
        implements ActionListener {

    // Components of the Form
    private Container c;
    private JLabel title;
    private JLabel username;
    private JTextField tname;
    private JLabel email;
    private JTextField tmail;
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
    public MyFrame()
    {
        con= db.dbconnect();

        setTitle("Registration Form");
        setBounds(700, 350, 500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Registration Form");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setSize(300, 30);
        title.setLocation(100,30);
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

        email = new JLabel("Email");
        email.setFont(new Font("Arial", Font.BOLD, 20));
        email.setSize(100, 20);
        email.setLocation(100, 150);
        c.add(email);

        tmail = new JTextField();
        tmail.setFont(new Font("Arial", Font.PLAIN, 15));
        tmail.setSize(150, 20);
        tmail.setLocation(200, 150);
        c.add(tmail);

        password = new JLabel("Password");
        password.setFont(new Font("Arial", Font.BOLD, 20));
        password.setSize(100, 20);
        password.setLocation(100, 200);
        c.add(password);

        pass = new JPasswordField();
        pass.setFont(new Font("Arial", Font.PLAIN, 15));
        pass.setSize(150, 20);
        pass.setLocation(200, 200);
        c.add(pass);

        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(150, 250);
        sub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String uname = tname.getText();
                    String mail = tmail.getText();
                    String passwordd = pass.getText();

                    PreparedStatement pst= con.prepareStatement("insert into signup(Username,Email,Password) values(?,?,?);");
                    pst.setString(1, uname);
                    pst.setString(2, mail);
                    pst.setString(3, passwordd);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "data added");
                }
                catch(Exception e1) {
                    System.out.println(e1);
                }

            }
        });
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(270, 250);
        reset.addActionListener(this);
        c.add(reset);

        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 20));
        res.setSize(500, 25);
        res.setLocation(100, 500);
        c.add(res);

        setVisible(true);
    }

    // method actionPerformed()
    // to get the action performed
    // by the user and act accordingly
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == sub) {
            if (term.isSelected()) {
                String data1;
                String data
                        = "Username : "
                        + tname.getText() + "\n"
                        + "Email : "
                        + tmail.getText() + "\n"
                        + "Password : "
                        + pass.getPassword() + "\n";
                tout.setEditable(false);
                res.setText("Registration Successfully..");
            }
            else {
                tout.setText("");
                resadd.setText("");
                res.setText("Please accept the"
                        + " terms & conditions..");
            }
        }

        else if (e.getSource() == reset) {
            String def = "";
            tname.setText(def);
            tadd.setText(def);
            tmail.setText(def);
            res.setText(def);
            tout.setText(def);
            term.setSelected(false);
            resadd.setText(def);
        }
    }

//    public void paint(Graphics g) {
//        g.setColor(Color.BLACK);
////        g.drawString("Applet background example", 0, 50);
//    }
}