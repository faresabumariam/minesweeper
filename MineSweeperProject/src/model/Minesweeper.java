package model;

import model.AbstractMineSweeper;
import model.AbstractTile;
import model.Difficulty;

import java.util.Random;


public class Minesweeper extends AbstractMineSweeper {

    private static int row =8,col=8,explosionCount;
    private static AbstractTile[][] grid = new AbstractTile[row][col];


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

    }

    @Override
    public void startNewGame(int row, int col, int explosionCount) {
        this.col=col;
        this.row=row;
        this.explosionCount=explosionCount;

        for(int i = 0; i<row; i++)
            for(int j = 0; j<col; j++)
                grid[i][j] = generateEmptyTile();

    }


    @Override
    public void toggleFlag(int x, int y) {

    }

    @Override
    public AbstractTile getTile(int x, int y) {
        return grid[x][y];
    }

    @Override
    public void setWorld(AbstractTile[][] world) {

    }

    @Override
    public void open(int x, int y) {
        grid[x][y].open();
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

    public AbstractTile generateRandomTile()
    {
        Random rand = new Random();
        int upperBound = 2 ;
        int int_random = rand.nextInt(upperBound);

        if ( int_random == 0)
        {
            return generateEmptyTile();
        }
        else
        {
            return generateExplosiveTile();
        }

    }


}
