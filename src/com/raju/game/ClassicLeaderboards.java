package com.raju.game;

import java.io.*;
import java.util.ArrayList;

public class ClassicLeaderboards{

    private static ClassicLeaderboards lcBoard;
    private String classicfilePath;
    private String classichighScores;

    private ArrayList<Integer> classictopScores;
    private ArrayList<Integer> topTiles;
    private ArrayList<Long> topTimes;

    private ClassicLeaderboards(){
        classicfilePath = new File("").getAbsolutePath();
        classichighScores = "ClassicScores";

        classictopScores = new ArrayList<Integer>();
        topTiles = new ArrayList<Integer>();
        topTimes = new ArrayList<Long>();
    }

    public static ClassicLeaderboards getInstance(){
        if(lcBoard == null){
            lcBoard = new ClassicLeaderboards();
        }
        return lcBoard;
    }

    public void addScore(int score){
        for(int i = 0 ; i < classictopScores.size() ; i++){
            if(score >= classictopScores.get(i)){
                classictopScores.add(i, score);
                classictopScores.remove(classictopScores.size() - 1);
                return;
            }
        }
    }

    public void addTile(int tileValue){
        for(int i = 0 ; i < topTiles.size() ; i++){
            if(tileValue >= topTiles.get(i)){
                topTiles.add(i, tileValue);
                topTiles.remove(topTiles.size() - 1);
                return;
            }
        }
    }

    public void addTime(long millis){
        for(int i = 0 ; i < topTimes.size() ; i++){
            if(millis <= topTimes.get(i)){
                topTimes.add(i, millis);
                topTimes.remove(topTimes.size() - 1);
                return;
            }
        }
    }

    public void loadClassicScores(){
        try {

            File f = new File(classicfilePath, classichighScores);
            if(!f.isFile()){
                createSaveData();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

            classictopScores.clear();
            topTiles.clear();
            topTimes.clear();

            String[] classicscores = reader.readLine().split("-");
            String[] tiles = reader.readLine().split("-");
            String[] times = reader.readLine().split("-");

            for(int i = 0 ; i < classicscores.length ; i++){
                classictopScores.add(Integer.parseInt(classicscores[i]));
            }
            for(int i = 0 ; i < tiles.length ; i++){
                topTiles.add(Integer.parseInt(tiles[i]));
            }
            for(int i = 0 ; i < times.length ; i++){
                topTimes.add(Long.parseLong(times[i]));
            }
            reader.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void saveClassicScores(){
        FileWriter output = null;

        try{

            File f = new File(classicfilePath, classichighScores);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);

            writer.write(classictopScores.get(0) + "-" + classictopScores.get(1) + "-" + classictopScores.get(2) + "-" + classictopScores.get(3) + "-" + classictopScores.get(4));
            writer.newLine();
            writer.write(topTiles.get(0) + "-" + topTiles.get(1) + "-" + topTiles.get(2) + "-" + topTiles.get(3) + "-" + topTiles.get(4));
            writer.newLine();
            writer.write(topTimes.get(0) + "-" + topTimes.get(1) + "-" + topTimes.get(2) + "-" + topTimes.get(3) + "-" + topTimes.get(4));
            writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void createSaveData(){
        FileWriter output = null;

        try{

            File f = new File(classicfilePath, classichighScores);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);

            writer.write("0-0-0-0-0");
            writer.newLine();
            writer.write("0-0-0-0-0");
            writer.newLine();
            writer.write(Integer.MAX_VALUE + "-" + Integer.MAX_VALUE + "-" + Integer.MAX_VALUE + "-" + Integer.MAX_VALUE + "-" + Integer.MAX_VALUE);
            writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getClassichighScore(){
        return classictopScores.get(0);
    }

    public long getFastestTime(){
        return topTimes.get(0);
    }

    public int getHighTile(){
        return topTiles.get(0);
    }

    public ArrayList<Integer> getClassictopScores() {
        return classictopScores;
    }

    public ArrayList<Integer> getTopTiles() {
        return topTiles;
    }

    public ArrayList<Long> getTopTimes() {
        return topTimes;
    }
}
