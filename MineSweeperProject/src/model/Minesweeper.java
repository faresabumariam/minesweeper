package model;

import model.AbstractMineSweeper;
import model.AbstractTile;
import model.Difficulty;

import java.util.Random;


public class Minesweeper extends AbstractMineSweeper {

    private static int row, col, explosionCount, flagCount;
    private static AbstractTile[][] grid;

    @Override
    public int getWidth() {
        return col;
    }

    @Override
    public int getHeight() {
        return row;
    }

    @Override
    public void startNewGame(Difficulty level) {
        switch (level) {
            case EASY:
                startNewGame(8, 8, 10);
                break;

            case MEDIUM:
                startNewGame(16, 16, 40);
                break;
            case HARD:
                startNewGame(16, 30, 99);
                break;
        }
    }

    @Override
    public void startNewGame(int row, int col, int explosionCount) {
        this.col = col;
        this.row = row;
        grid = new AbstractTile[row][col];
        this.explosionCount = explosionCount;


        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j] = generateEmptyTile();
            }
        }
        Random rand = new Random();

        while (explosionCount > 0) {
            int upperBoundX = row;
            int upperBoundY = col;
            int intRandomX = rand.nextInt(upperBoundX);
            int intRandomY = rand.nextInt(upperBoundY);

            if (!grid[intRandomX][intRandomY].isExplosive()) {
                grid[intRandomX][intRandomY] = generateExplosiveTile();
                explosionCount--;
            }
        }

        this.viewNotifier.notifyNewGame(row, col);
    }


    @Override
    public void toggleFlag(int x, int y) {
        if (grid[x][y].isFlagged()) {
            grid[x][y].unflag();
        } else {
            grid[x][y].flag();
        }

    }

    @Override
    public AbstractTile getTile(int x, int y) {

        try {
            return grid[y][x];
        } catch (IndexOutOfBoundsException e) {
            return null;

        }
    }

//
//        if (x >= 0 && y >= 0 && x < row && y < col) {
//            return grid[x][y];
//        }
//        return null;


    @Override
    public void setWorld(AbstractTile[][] world) {

        grid = world;

    }


    @Override
    public void open(int x, int y) {

        if (x >= 0 && y >= 0 && x < grid.length && y < grid[0].length) {
            if (grid[x][y].isExplosive() && !grid[x][y].isFlagged()) {
                grid[x][y].open();

            }
            if (!grid[x][y].isExplosive() && !grid[x][y].isFlagged()) {
                grid[x][y].open();
            }
        }
    }

//        if (x >= 0 && y >= 0 && x < row && y < col) {
//            grid[x][y].open();
//        }
//
//        if (grid[x][y].isExplosive()) {
//            this.viewNotifier.notifyExploded(x, y);
//        } else {
//            this.viewNotifier.notifyOpened(x, y, 2);
//        }



    @Override
    public void flag(int x, int y) {

        if (!grid[x][y].isOpened()) {
            grid[x][y].flag();
            this.viewNotifier.notifyFlagged(x, y);
        }
    }

    @Override
    public void unflag(int x, int y) {
        grid[x][y].unflag();
        this.viewNotifier.notifyUnflagged(x, y);
    }

    @Override
    public void deactivateFirstTileRule() {


    }

    @Override
    public AbstractTile generateEmptyTile() {
        NonExplosiveTile newTile = new NonExplosiveTile();
        return newTile;
    }

    @Override
    public AbstractTile generateExplosiveTile() {
        ExplosiveTile newTile = new ExplosiveTile();
        return newTile;
    }



    public int getNearbyExplosives(int thisPosX, int thisPosY){

        int countExp =0;
        int MIN_X = 0, MIN_Y =0;
        int MAX_X = col - 1, MAX_Y = row - 1;

        int startPosX = (thisPosX - 1 < MIN_X) ? thisPosX : thisPosX-1;
        int startPosY = (thisPosY - 1 < MIN_Y) ? thisPosY : thisPosY-1;
        int endPosX =   (thisPosX + 1 > MAX_X) ? thisPosX : thisPosX+1;
        int endPosY =   (thisPosY + 1 > MAX_Y) ? thisPosY : thisPosY+1;


// See how many are alive
        for (int rowNum=startPosX; rowNum<=endPosX; rowNum++) {
            for (int colNum=startPosY; colNum<=endPosY; colNum++) {
                if(getTile(endPosX,endPosY).isExplosive()){
                    countExp++;
                }
            }
        }

        return countExp;
    }

}
