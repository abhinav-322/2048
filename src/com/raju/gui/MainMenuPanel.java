package com.raju.gui;

import com.raju.game.DrawUtils;
import com.raju.game.Game;
import com.raju.game.MyFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends GuiPanel{

    private Font titleFont = Game.main.deriveFont(100f);
    private Font creatorFont = Game.main.deriveFont(18f);
    private String title = "2048";
    private String creator = "By Raju";
    private int buttonWidth = 220;
    private int buttonHeight = 60;
    private int spacing = 90;

    public MainMenuPanel(){
        super();
        GuiButton playButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 220, buttonWidth, buttonHeight);
        GuiButton leaderboardButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, playButton.getY() + spacing, buttonWidth, buttonHeight);
        GuiButton signUp = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, leaderboardButton.getY() + spacing, buttonWidth, buttonHeight);
        GuiButton quitButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, signUp.getY() + spacing, buttonWidth, buttonHeight);

        playButton.setText("Play");
        leaderboardButton.setText("Leaderboard");
        signUp.setText("Signup");
        quitButton.setText("Quit");

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiScreen.getInstance().setCurrentPanel("Play");
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

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(playButton);
        add(leaderboardButton);
        add(signUp);
        add(quitButton);
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
