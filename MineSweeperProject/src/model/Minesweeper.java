package model;

import model.AbstractMineSweeper;
import model.AbstractTile;
import model.Difficulty;

import java.util.Random;


public class Minesweeper extends AbstractMineSweeper {

    private static int row ,col,explosionCount;
    private static AbstractTile[][] grid;
    private int explosivesPresent;


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
                startNewGame(8,8,10);
                break;

            case MEDIUM:
                startNewGame(16,16,40);
                break;
            case HARD:
                startNewGame(16,30,99);
                break;
        }




    }

    @Override
    public void startNewGame(int row, int col, int explosionCount) {
        this.col=col;
        this.row=row;
        grid = new AbstractTile[row][col];
        this.explosionCount=explosionCount;

        int explosivesPresent =0;

        for(int i = 0; i<row; i++)
        {
            for(int j = 0; j<col; j++)
            {
                grid[i][j] = generateEmptyTile();
            }
        }
        Random rand = new Random();

        while(explosionCount>0)
        {
            int upperBoundX = row-1;
            int upperBoundY = col-1;
            int intRandomX = rand.nextInt(upperBoundX);
            int intRandomY = rand.nextInt(upperBoundY);

            if(!grid[intRandomX][intRandomY].isExplosive())
            {
                grid[intRandomX][intRandomY]=generateExplosiveTile();
                explosionCount--;
            }

        }




    }


    @Override
    public void toggleFlag(int x, int y) {

    }

    @Override
    public AbstractTile getTile(int x, int y) {
        if (x>=0 && y>=0 && x<row && y<col){
            return grid[x][y];
        }
        return null;
    }

    @Override
    public void setWorld(AbstractTile[][] world) {

    }

    @Override
    public void open(int x, int y) {
        if(x >= 0 && y >= 0){
            grid[x][y].open();

        }
    }

    @Override
    public void flag(int x, int y) {
        grid[x][y].flag();
        this.viewNotifier.notifyFlagged(x,y);
    }

    @Override
    public void unflag(int x, int y) {
        grid[x][y].unflag();
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

//    public AbstractTile generateRandomTile()
//    {
//        Random rand = new Random();
//        int upperBound = 2 ;
//        int int_random = rand.nextInt(upperBound);
//
//        if ( int_random == 0) {
//            return generateEmptyTile();
//        }
//        else {
//            explosivesPresent++;
//            return generateExplosiveTile();
//        }
//    }
}
