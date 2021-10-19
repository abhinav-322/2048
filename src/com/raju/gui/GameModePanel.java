package com.raju.gui;

import com.raju.game.DrawUtils;
import com.raju.game.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameModePanel extends GuiPanel {

    public static boolean classicboolean = false;
    public static boolean countdownboolean = false;
    public static boolean survivalboolean = false;


    private Font titleFont = Game.main.deriveFont(80f);
    private String title = "GAME MODES";
    private int buttonWidth = 220;
    private int buttonHeight = 60;
    private int spacing = 90;

    public GameModePanel() {
        super();
        GuiButton classicButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 220, buttonWidth, buttonHeight);
        GuiButton countdownButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, classicButton.getY() + spacing, buttonWidth, buttonHeight);
        GuiButton survivalButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, countdownButton.getY() + spacing, buttonWidth, buttonHeight);


        classicButton.setText("Classic");
        countdownButton.setText("Count Down");
        survivalButton.setText("Survival");

        classicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiScreen.getInstance().setCurrentPanel("Difficulty");
                classicboolean = true;
            }
        });

        countdownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiScreen.getInstance().setCurrentPanel("Difficulty");
                countdownboolean = true;
            }
        });

        survivalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiScreen.getInstance().setCurrentPanel("Difficulty");
                survivalboolean = true;
            }
        });

        add(classicButton);
        add(countdownButton);
        add(survivalButton);
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        g.setFont(titleFont);
        g.setColor(Color.black);
        g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2, 150);
    }
}
