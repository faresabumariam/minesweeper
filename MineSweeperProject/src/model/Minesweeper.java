package model;

import model.AbstractMineSweeper;
import model.AbstractTile;
import model.Difficulty;
import java.util.Random;

import java.util.Random;


public class Minesweeper extends AbstractMineSweeper {

    private static int row, col, explosionCount, flagCount;
    private static AbstractTile[][] grid;
    private boolean firstTileRule=true;
    private Random rd;

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
        this.flagCount = 0;
        grid = new AbstractTile[row][col];
        this.explosionCount = explosionCount;


        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j] = generateEmptyTile();
            }
        }
        Random rand = new Random();

        while (explosionCount > 0) {
            int upperBoundY = row;
            int upperBoundX = col;
            int intRandomY = rand.nextInt(upperBoundY);
            int intRandomX = rand.nextInt(upperBoundX);

            if (!grid[intRandomY][intRandomX].isExplosive()) {
                grid[intRandomY][intRandomX] = generateExplosiveTile();
                explosionCount--;
            }
        }

        this.viewNotifier.notifyNewGame(row, col);
    }


    @Override
    public void toggleFlag(int x, int y) {
        if (grid[x][y].isFlagged()) {
            grid[x][y].unflag();
        }
        else {
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


    @Override
    public void setWorld(AbstractTile[][] world) {

        grid = world;

    }


    @Override
    public void open(int x, int y) {
        if(x< grid[0].length && y< grid.length && x>=0 && y>=0)
        {

            grid[y][x].isOpened();

            boolean check = false;
            if (firstTileRule)
            {
                firstTileRule = false;
                grid[y][x].open();

                if (grid[y][x].isExplosive()) {
                    grid[y][x] = generateEmptyTile();
                    rd = new Random();
                    while (!check) {
                        int randomIndexRows = rd.nextInt(grid.length);
                        int randomIndexColumns = rd.nextInt(grid[0].length);
                        if (randomIndexRows != y && randomIndexColumns != x && !grid[randomIndexRows][randomIndexColumns].isExplosive()) {
                            check = true;
                            grid[randomIndexRows][randomIndexColumns] = new ExplosiveTile();
                            grid[randomIndexRows][randomIndexColumns]=generateExplosiveTile();
                        }

                    }
                }

            }
            else
            {
                grid[y][x].open();
            }


            int counter=0;
            for (int i = x-1; i <= x+1; i++)
            {
                for (int j = y-1; j <= y+1; j++)
                {
                    if(i>=0 && i< grid.length && j>=0 && j<grid[0].length)
                    {
                        if( grid[i][j].isExplosive())
                        {
                            counter +=1;
                        }
                        else{}

                    }
                    else{}
                }
            }
            viewNotifier.notifyOpened(x,y,counter);

            if(grid[x][y].isExplosive())
            {
                viewNotifier.notifyExploded(x,y);
                viewNotifier.notifyGameLost();
            }

        }

        else{}
    }

//        boolean firstOne = true;
//
//        if(firstTileRule)
//        {
//            if(firstOne)
//            {
//                if(grid[y][x].isExplosive() && x >= 0 && y >= 0 && x < grid.length && y < grid[0].length)
//                {
//                    grid[y][x]=generateEmptyTile();
//                    grid[y][x].open();
//                    this.viewNotifier.notifyOpened(x, y, getNearbyExplosives(x,y));
//
//                }
//                firstOne=false;
//            }
//
//            else
//            {
//                if (x >= 0 && y >= 0 && x < grid.length && y < grid[0].length) {
//                    if (grid[y][x].isExplosive() && !grid[y][x].isFlagged()) {
//
//                        this.viewNotifier.notifyExploded(x, y);
//
//                    }
//                    if (!grid[y][x].isExplosive() && !grid[y][x].isFlagged()) {
//                        grid[y][x].open();
//                        this.viewNotifier.notifyOpened(x, y, getNearbyExplosives(x,y));
//                    }
//                }
//
//            }
//        }
//
//        else
//        {
//            if (x >= 0 && y >= 0 && x < grid.length && y < grid[0].length) {
//                if (grid[y][x].isExplosive() && !grid[y][x].isFlagged()) {
//                    grid[y][x].open();
//                    this.viewNotifier.notifyExploded(x, y);
//
//                }
//                if (!grid[y][x].isExplosive() && !grid[y][x].isFlagged()) {
//                    grid[y][x].open();
//                    this.viewNotifier.notifyOpened(x, y, getNearbyExplosives(x,y));
//                }
//            }
//        }
//
//
//    }


    @Override
    public void flag(int x, int y) {

        if (!grid[y][x].isOpened()) {
            grid[y][x].flag();
            this.viewNotifier.notifyFlagged(x, y);
            flagCount++;
            this.viewNotifier.notifyFlagCountChanged(flagCount);
        }
    }

    @Override
    public void unflag(int x, int y) {
        grid[y][x].unflag();
        flagCount--;
        this.viewNotifier.notifyUnflagged(x, y);
        this.viewNotifier.notifyFlagCountChanged(flagCount);
    }

    @Override
    public void deactivateFirstTileRule() {
            firstTileRule=true;
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
        for (int rowNum=startPosY; rowNum<=endPosY; rowNum++) {
            for (int colNum=startPosX; colNum<=endPosX; colNum++) {
                if(getTile(endPosX,endPosY).isExplosive()){
                    countExp++;
                }
            }
        }

        return countExp;
    }

}
