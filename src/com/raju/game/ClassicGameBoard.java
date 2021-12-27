package com.raju.game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

public class ClassicGameBoard {

    public static final int ROWS = 4;
    public static final int COLS = 4;

    private final int startingTiles = 2;
//    private Tile[][] board;
    private Tile[][] classicboard;

    private boolean dead;
    public static boolean won;

    private BufferedImage classicgameBoard;
    private BufferedImage finalBoard;
    private int x;
    private int y;

    private static int SPACING = 10;
    public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;
    public static int BOARD_ARC_WIDTH = 0;
    public static int BOARD_ARC_HEIGHT = 0;

    private long elapsedMS;
    private long startTime;

    private boolean hasStarted;
    private int saveCount;

    //Saving
    private String saveDataPath;
    private String fileName = "SaveData";

    private ClassicScoreManager classicscores;
    private ClassicLeaderboards lcBoard;

    public ClassicGameBoard(int x, int y){
        this.x = x;
        this.y = y;
//        board = new Tile[ROWS][COLS];
        classicboard = new Tile[ROWS][COLS];
        classicgameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);

        createBoardImage();

        lcBoard = ClassicLeaderboards.getInstance();
        lcBoard.loadClassicScores();
        classicscores = new ClassicScoreManager(this);
        classicscores.loadGame();
        classicscores.setBestTime(lcBoard.getFastestTime());
        classicscores.setClassiccurrentTopScore(lcBoard.getClassichighScore());
        if(classicscores.newGame()){
            start();
            classicscores.saveGame();
        }
        else{
            for(int i = 0 ; i < classicscores.getBoard().length ; i++){
                if(classicscores.getBoard()[i] == 0) continue;
                spawn(i / ROWS, i % COLS, classicscores.getBoard()[i]);
            }

            dead = checkDead();
            won = checkWon();
        }
    }

    public void reset(){
        classicboard = new Tile[ROWS][COLS];
//        classicboard = new Tile[ROWS][COLS];
        start();
        classicscores.saveGame();
        dead = false;
        won = false;
        hasStarted = false;
        startTime = System.nanoTime();
        elapsedMS = 0;
        saveCount = 0;
    }

    private void createBoardImage(){
        Graphics2D g = (Graphics2D) classicgameBoard.getGraphics();
        g.setColor(new Color(0x776e65));
        g.fillRoundRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT, BOARD_ARC_WIDTH, BOARD_ARC_HEIGHT);
        g.setColor(new Color(0xbbada0));

        for(int row = 0 ; row < ROWS ; row++){
            for(int col = 0 ; col < COLS ; col++){
                int x = SPACING + SPACING * col + Tile.WIDTH * col;
                int y = SPACING + SPACING * row + Tile.HEIGHT * row;
                g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);
            }
        }
        g.dispose();
    }

    private void start(){
        for(int i=0 ; i < startingTiles ; i++){
            spawnRandom();
        }
    }

    private void spawn(int row, int col, int value){
        classicboard[row][col] = new Tile(value, getTileX(col), getTileY(row));
    }

    private void spawnRandom(){
        Random random = new Random();
        boolean notValid = true;

        while(notValid){
            int row = random.nextInt(ROWS);
            int col = random.nextInt(COLS);
            Tile classiccurrent = classicboard[row][col];
            if(classiccurrent == null){
                int value = random.nextInt(10) < 8 ? 128 : 256;
                Tile tile = new Tile(value, getTileX(col), getTileY(row));
                classicboard[row][col] = tile;
                notValid = false;
            }
        }
    }

    public int getTileX(int col){
        return SPACING + col * Tile.WIDTH + col * SPACING;
    }

    public int getTileY(int row){
        return SPACING + row * Tile.HEIGHT + row * SPACING;
    }

    public void render(Graphics2D g){
        Graphics2D g2d = (Graphics2D) finalBoard.getGraphics();
        g2d.drawImage(classicgameBoard, 0, 0, null);

        for(int row = 0; row < ROWS ; row++){
            for(int col = 0 ; col < COLS ; col++){
                Tile classiccurrent = classicboard[row][col];
                if(classiccurrent == null) continue;
                classiccurrent.render(g2d);
            }
        }

        g.drawImage(finalBoard, x, y, null);
        g2d.dispose();
    }

    public void update(){
        saveCount++;
        if(saveCount >= 120){
            saveCount = 0;
            classicscores.saveGame();
        }
        if(!won && !dead){
            if(hasStarted){
                elapsedMS = (System.nanoTime() - startTime) / 1000000;
                classicscores.setTime(elapsedMS);
            }
            else {
                startTime = System.nanoTime();
            }
        }

        checkKeys();

        if(classicscores.getClassiccurrentScore() >= classicscores.getClassiccurrentTopScore()){
            classicscores.setClassiccurrentTopScore(classicscores.getClassiccurrentScore());
        }

        for(int row = 0 ; row < ROWS ; row++){
            for(int col = 0 ; col < COLS ; col++){
                Tile classiccurrent = classicboard[row][col];
                if(classiccurrent == null) continue;
                classiccurrent.update();
                resetPosition(classiccurrent, row, col);
                if(classiccurrent.getValue() == 2048){
                    setWon(true);
                }
            }
        }
    }

    private void resetPosition(Tile classiccurrent, int row, int col){
        if(classiccurrent == null) return;

        int x = getTileX(col);
        int y = getTileY(row);

        int distX = classiccurrent.getX() - x;
        int distY = classiccurrent.getY() - y;

        if(Math.abs(distX) < Tile.SLIDE_SPEED){
            classiccurrent.setX(classiccurrent.getX() - distX);
        }

        if(Math.abs(distY) < Tile.SLIDE_SPEED){
            classiccurrent.setY(classiccurrent.getY() - distY);
        }

        if(distX < 0){
            classiccurrent.setX(classiccurrent.getX() + Tile.SLIDE_SPEED);
        }
        if(distY < 0){
            classiccurrent.setY(classiccurrent.getY() + Tile.SLIDE_SPEED);
        }
        if(distX > 0){
            classiccurrent.setX(classiccurrent.getX() - Tile.SLIDE_SPEED);
        }
        if(distY > 0){
            classiccurrent.setY(classiccurrent.getY() - Tile.SLIDE_SPEED);
        }
    }

    private boolean move(int row, int col, int horizontalDirection, int verticalDirection, Direction dir){
        boolean canMove = false;

        Tile classiccurrent = classicboard[row][col];
        if(classiccurrent == null) return false;
        boolean move = true;
        int newCol = col;
        int newRow = row;
        while(move){
            newCol += horizontalDirection;
            newRow += verticalDirection;
            if(checkOutOfBounds(dir, newRow, newCol)) break;
            if(classicboard[newRow][newCol] == null){
                classicboard[newRow][newCol] = classiccurrent;
                classicboard[newRow - verticalDirection][newCol - horizontalDirection] = null;
                classicboard[newRow][newCol].setSlideTo(new Point(newRow, newCol));
                canMove = true;
            }
            else if(classicboard[newRow][newCol].getValue() == classiccurrent.getValue() && classicboard[newRow][newCol].canCombine()){
                classicboard[newRow][newCol].setCanCombine(false);
                classicboard[newRow][newCol].setValue(classicboard[newRow][newCol].getValue() * 2);
                canMove = true;
                classicboard[newRow - verticalDirection][newCol - horizontalDirection] = null;
                classicboard[newRow][newCol].setSlideTo(new Point(newRow, newCol));
                classicboard[newRow][newCol].setCombineAnimation(true);
                classicscores.setClassiccurrentScore(classicscores.getClassiccurrentScore() + classicboard[newRow][newCol].getValue());
            }
            else{
                move = false;
            }
        }

        return canMove;
    }

    private boolean checkOutOfBounds(Direction dir, int row, int col) {
        if(dir == Direction.LEFT){
            return col < 0;
        }
        else if(dir == Direction.RIGHT){
            return col > COLS - 1;
        }
        else if(dir == Direction.UP){
            return row < 0;
        }
        else if(dir == Direction.DOWN){
            return row > ROWS - 1;
        }
        return false;
    }

    private void moveTiles(Direction dir){
        boolean canMove = false;
        int horizontalDirection = 0;
        int verticalDirection = 0;

        if(dir == Direction.LEFT){
            horizontalDirection = -1;
            for(int row = 0 ; row < ROWS ; row++){
                for(int col = 0 ; col < COLS ; col++){
                    if(!canMove){
                        canMove = move(row, col, horizontalDirection, verticalDirection, dir);
                    }
                    else move(row, col, horizontalDirection, verticalDirection, dir);
                }
            }
        }

        else if(dir == Direction.RIGHT){
            horizontalDirection = 1;
            for(int row = 0 ; row < ROWS ; row++){
                for(int col = COLS - 1 ; col >= 0 ; col--){
                    if(!canMove){
                        canMove = move(row, col, horizontalDirection, verticalDirection, dir);
                    }
                    else move(row, col, horizontalDirection, verticalDirection, dir);
                }
            }
        }

        else if(dir == Direction.UP){
            verticalDirection = -1;
            for(int row = 0 ; row < ROWS ; row++){
                for(int col = 0 ; col < COLS ; col++){
                    if(!canMove){
                        canMove = move(row, col, horizontalDirection, verticalDirection, dir);
                    }
                    else move(row, col, horizontalDirection, verticalDirection, dir);
                }
            }
        }

        else if(dir == Direction.DOWN){
            verticalDirection = 1;
            for(int row = ROWS - 1 ; row >= 0 ; row--){
                for(int col = 0 ; col < COLS ; col++){
                    if(!canMove){
                        canMove = move(row, col, horizontalDirection, verticalDirection, dir);
                    }
                    else move(row, col, horizontalDirection, verticalDirection, dir);
                }
            }
        }

        else {
            System.out.println(dir + " is not a valid direction.");
        }

        for(int row = 0 ; row < ROWS ; row++){
            for(int col = 0 ; col<COLS ; col++){
                Tile classiccurrent = classicboard[row][col];
                if(classiccurrent == null) continue;
                classiccurrent.setCanCombine(true);
            }
        }

        if(canMove){
            spawnRandom();
            setDead(checkDead());
        }
    }

    private boolean checkDead(){
        for(int row = 0 ; row < ROWS ; row++){
            for(int col = 0 ; col < COLS ; col++){
                if(classicboard[row][col] == null) return false;
                boolean canCombine = checkSurroundingTiles(row, col, classicboard[row][col]);
                if(canCombine){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkWon(){
        for(int row = 0 ; row < ROWS ; row++){
            for(int col = 0 ; col < COLS ; col++){
                if(classicboard[row][col] == null) continue;
                if(classicboard[row][col].getValue() == 2048) return true;
            }
        }
        return false;
    }

    private boolean checkSurroundingTiles(int row, int col, Tile classiccurrent){
        if(row > 0){
            Tile check = classicboard[row - 1][col];
            if(check == null) return true;
            if(classiccurrent.getValue() == check.getValue()) return true;
        }
        if(row < ROWS - 1){
            Tile check = classicboard[row + 1][col];
            if(check == null) return true;
            if(classiccurrent.getValue() == check.getValue()) return true;
        }
        if(col > 0){
            Tile check = classicboard[row][col - 1];
            if(check == null) return true;
            if(classiccurrent.getValue() == check.getValue()) return true;
        }
        if(col < COLS - 1){
            Tile check = classicboard[row][col + 1];
            if(check == null) return true;
            if(classiccurrent.getValue() == check.getValue()) return true;
        }
        return false;
    }

    private void checkKeys(){
        if(Keyboard.typed(KeyEvent.VK_LEFT)){
            moveTiles(Direction.LEFT);
            if(!hasStarted) hasStarted = !dead;
        }
        if(Keyboard.typed(KeyEvent.VK_RIGHT)){
            moveTiles(Direction.RIGHT);
            if(!hasStarted) hasStarted = !dead;
        }
        if(Keyboard.typed(KeyEvent.VK_UP)){
            moveTiles(Direction.UP);
            if(!hasStarted) hasStarted = !dead;
        }
        if(Keyboard.typed(KeyEvent.VK_DOWN)){
            moveTiles(Direction.DOWN);
            if(!hasStarted) hasStarted = !dead;
        }
    }

    public int getHighestTileValue(){
        int value = 2;
        for(int row = 0 ; row < ROWS ; row++){
            for(int col = 0 ; col < COLS ; col++){
                if(classicboard[row][col] == null) continue;
                if(classicboard[row][col].getValue() > value){
                    value = classicboard[row][col].getValue();
                }
            }
        }
        return value;
    }

    public boolean isDead(){
        return dead;
    }

    public void setDead(boolean dead){
        if(!this.dead && dead){
            lcBoard.addTile(getHighestTileValue());
            lcBoard.addScore(classicscores.getClassiccurrentScore());
            lcBoard.saveClassicScores();
        }
        this.dead = dead;
    }

    public boolean isWon(){
        return won;
    }

    public void setWon(boolean won){
        if(!this.won && won){
            lcBoard.addTime(classicscores.getTime());
            lcBoard.saveClassicScores();
        }
        this.won = won;
    }

    public ClassicScoreManager getScores(){
        return classicscores;
    }

    public Tile[][] getBoard(){
        return classicboard;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
