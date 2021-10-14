package com.raju.gui;

import com.raju.game.DrawUtils;
import com.raju.game.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifficultyPanel extends GuiPanel {

    private Font titleFont = Game.main.deriveFont(80f);
    private String title = "DIFFICULTY";
    private int buttonWidth = 220;
    private int buttonHeight = 60;
    private int spacing = 90;

    public DifficultyPanel() {
        super();
        GuiButton EasyButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 220, buttonWidth, buttonHeight);
        GuiButton MediumButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, EasyButton.getY() + spacing, buttonWidth, buttonHeight);
        GuiButton HardButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, MediumButton.getY() + spacing, buttonWidth, buttonHeight);


        EasyButton.setText("Easy");
        MediumButton.setText("Count Down");
        HardButton.setText("Survival");

        EasyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiScreen.getInstance().setCurrentPanel("Play");
            }
        });

        MediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiScreen.getInstance().setCurrentPanel("Play");
            }
        });

        HardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiScreen.getInstance().setCurrentPanel("Play");
            }
        });

        add(EasyButton);
        add(MediumButton);
        add(HardButton);
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        g.setFont(titleFont);
        g.setColor(Color.black);
        g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2, 150);
    }
}
