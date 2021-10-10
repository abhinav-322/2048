package com.raju.game;

import javax.swing.JFrame;
import java.io.File;

public class Start {

    public static void main(String[] args){
        Game game = new Game();

        File f1 = new File(System.getProperty("user.home") + "\\Documents" + "\\2048-SaveGame");
        if(!f1.exists()){
            f1.mkdir();
        }

        JFrame window = new JFrame("2048");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(game);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        game.start();
    }
}
