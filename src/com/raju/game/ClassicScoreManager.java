package com.raju.game;

import java.io.*;


public class ClassicScoreManager {

    //Current Scores
    private int classiccurrentScore;
    private int classiccurrentTopScore;
    private long time;
    private long startingTime;
    private long bestTime;

    //File
    private String classicfilePath;
    private String tempc = "TEMPC.tmp";
    private ClassicGameBoard gcBoard;

    //Boolean
    private boolean newGame;
    private int[] classicboard = new int[ClassicGameBoard.ROWS * ClassicGameBoard.COLS];

    public ClassicScoreManager(ClassicGameBoard gcBoard){
        this.gcBoard = gcBoard;
        classicfilePath = new File("").getAbsolutePath();
    }

    public void reset(){
        File f = new File(classicfilePath, tempc);
        if(f.isFile()){
            f.delete();
        }

        newGame = true;
        startingTime = 0;
        classiccurrentScore = 0;
        time = 0;
    }

    private void createFile(){
        FileWriter output = null;
        newGame = true;

        try{

            File f = new File(classicfilePath, tempc);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("" + 0);
            writer.newLine();
            writer.write("" + 0);
            writer.newLine();
            writer.write("" + 0);
            writer.newLine();
            writer.write("" + 0);
            writer.newLine();

            for(int row = 0 ; row < ClassicGameBoard.ROWS ; row++){
                for(int col = 0 ; col < ClassicGameBoard.COLS ; col++){
                    if(row == ClassicGameBoard.ROWS - 1 && col == ClassicGameBoard.COLS - 1){
                        writer.write("" + 0);
                    }
                    else{
                        writer.write(0 + "-");
                    }
                }
            }
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveGame(){
        FileWriter output = null;
        if(newGame) newGame = false;

        try {

            File f = new File(classicfilePath, tempc);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("" + classiccurrentScore);
            writer.newLine();
            writer.write("" + classiccurrentTopScore);
            writer.newLine();
            writer.write("" + time);
            writer.newLine();
            writer.write("" + bestTime);
            writer.newLine();

            for(int row = 0 ; row < ClassicGameBoard.ROWS ; row++){
                for(int col = 0 ; col < ClassicGameBoard.COLS ; col++){
                    int location = row * ClassicGameBoard.COLS + col;
                    Tile tile = gcBoard.getBoard()[row][col];
                    this.classicboard[location] = tile != null ? tile.getValue() : 0;

                    if(row == ClassicGameBoard.ROWS - 1 && col == ClassicGameBoard.COLS - 1){
                        writer.write("" + classicboard[location]);
                    }
                    else{
                        writer.write(classicboard[location] + "-");
                    }
                }
            }
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadGame(){
        try{

            File f = new File(classicfilePath, tempc);

            if(!f.isFile()){
                createFile();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            classiccurrentScore = Integer.parseInt(reader.readLine());
            classiccurrentTopScore = Integer.parseInt(reader.readLine());
            time = Long.parseLong(reader.readLine());
            startingTime = time;
            bestTime = Long.parseLong(reader.readLine());
            String[] board = reader.readLine().split("-");
            for(int i = 0 ; i < board.length ; i++){
                this.classicboard[i] = Integer.parseInt(board[i]);
            }

            reader.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getClassiccurrentScore() {
        return classiccurrentScore;
    }

    public void setClassiccurrentScore(int classiccurrentScore) {
        this.classiccurrentScore = classiccurrentScore;
    }

    public int getClassiccurrentTopScore() {
        return classiccurrentTopScore;
    }

    public void setClassiccurrentTopScore(int classiccurrentTopScore) {
        this.classiccurrentTopScore = classiccurrentTopScore;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time + startingTime;
    }

    public long getBestTime() {
        return bestTime;
    }

    public void setBestTime(long bestTime) {
        this.bestTime = bestTime;
    }

    public int[] getBoard() {
        return classicboard;
    }

    public boolean newGame() {
        return newGame;
    }
}
