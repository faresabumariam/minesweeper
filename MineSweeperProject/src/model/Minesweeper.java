package model;

import model.AbstractMineSweeper;
import model.AbstractTile;
import model.Difficulty;


public class Minesweeper extends AbstractMineSweeper {

    private static int row =2,col=2,explosionCount;
    private static int[][] grid = new int[row][col];


    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void startNewGame(Difficulty level) {

    }

    @Override
    public void startNewGame(int row, int col, int explosionCount) {
        this.col=col;
        this.row=row;
        this.explosionCount=explosionCount;

    }


    public static void main(String[] args) {
        for(int i = 0; i<row; i++)
            for(int j = 0; j<col; j++)
                grid[i][j] = 0;

        for(int i = 0; i<row; i++)
        {
            for(int j = 0; j<col; j++)
            {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }    }



    @Override
    public void toggleFlag(int x, int y) {

    }

    @Override
    public AbstractTile getTile(int x, int y) {
        return null;
    }

    @Override
    public void setWorld(AbstractTile[][] world) {

    }

    @Override
    public void open(int x, int y) {

    }

    @Override
    public void flag(int x, int y) {

    }

    @Override
    public void unflag(int x, int y) {

    }

    @Override
    public void deactivateFirstTileRule() {

    }

    @Override
    public AbstractTile generateEmptyTile() {
        return null;
    }

    @Override
    public AbstractTile generateExplosiveTile() {
        return null;
    }

}
