package com.raju.gui;

import com.raju.game.DrawUtils;
import com.raju.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainMenuPanel extends GuiPanel{

    private Font titleFont = Game.main.deriveFont(100f);
    private Font creatorFont = Game.main.deriveFont(18f);
    private String title = "2048";
    private String creator = "By Raju";
    private int buttonWidth = 220;
    private int buttonHeight = 60;
    private int spacing = 90;
    Connection con = null;
    boolean found = false;
    boolean fndtab = false;

    public MainMenuPanel(){
        super();
        GuiButton playButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 220, buttonWidth, buttonHeight);
        GuiButton leaderboardButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, playButton.getY() + spacing, buttonWidth, buttonHeight);
        GuiButton signUp = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, leaderboardButton.getY() + spacing, buttonWidth, buttonHeight);
        GuiButton login = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, signUp.getY() + spacing, buttonWidth, buttonHeight);
        GuiButton quitButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, login.getY() + spacing, buttonWidth, buttonHeight);
        GuiButton dbbutton = new GuiButton(Game.WIDTH  - buttonWidth , Game.HEIGHT - buttonHeight , buttonWidth, buttonHeight);

        con= db.dbconnect();
        playButton.setText("Play");
        leaderboardButton.setText("Leaderboard");
        signUp.setText("Signup");
        login.setText("Login");
        quitButton.setText("Quit");
        dbbutton.setText("Developer Options");


        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiScreen.getInstance().setCurrentPanel("GameMode");
            }
        });

        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiScreen.getInstance().setCurrentPanel("Leaderboard");
            }
        });

        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFrame f = new MyFrame();
                f.show();
            }
        });

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login log = new Login();
                log.show();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

//        dbbutton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    PreparedStatement sd= con.prepareStatement("show databases;");
//                    ResultSet rs=sd.executeQuery();
//
//                    while(rs.next()) {
//
//                        if(rs.getString(1).equals("signup_2048")) {
//                            JOptionPane.showMessageDialog(null, "database already exists, using it");
//                            found = true;
//                            break;
//                        }
//                    }
//                    if(!found) {
//                        PreparedStatement psd= con.prepareStatement("create database signup_2048;");
//                        psd.execute();
//                    }
//
//                    PreparedStatement psu= con.prepareStatement("use signup_2048;");
//                    psu.execute();
//                    PreparedStatement st=con.prepareStatement("show tables;");
//                    ResultSet rst=st.executeQuery();
//                    while(rst.next()) {
//
//                        if(rst.getString(1).equals("signup3")) {
//                            JOptionPane.showMessageDialog(null, "table already exists, using it");
//                            fndtab = true;
//                            break;
//                        }
//                    }
//                    if(!fndtab) {
//                        PreparedStatement pst=con.prepareStatement("create table signup3(Username varchar(255) not null,Email varchar(255) primary key not null,Password varchar(255) not null);");
//                        pst.executeUpdate();
//                    }
//
////
//
//
//                    JOptionPane.showMessageDialog(null, "database and table created");
//
//                }
//                catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });

        add(playButton);
        add(leaderboardButton);
        add(signUp);
        add(quitButton);
//        add(dbbutton);
        add(login);

    }

    @Override
    public void render(Graphics2D g){
        super.render(g);
        g.setFont(titleFont);
        g.setColor(Color.black);
        g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2, 150);
        g.setFont(creatorFont);
        g.drawString(creator, 20, Game.HEIGHT - 10);
    }




}
