package model;

import model.AbstractMineSweeper;
import model.AbstractTile;
import model.Difficulty;

import java.util.Random;


public class Minesweeper extends AbstractMineSweeper {

    private static int row, col, explosionCount,flagCount;
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
            int upperBoundX = row - 1;
            int upperBoundY = col - 1;
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
        if (x >= 0 && y >= 0 && x < row && y < col) {
            return grid[x][y];
        }
        return null;
    }

    @Override
    public void setWorld(AbstractTile[][] world) {

        if (world.length == grid.length && world[0].length == grid[0].length
        ) {
            for (int i = 0; i < world.length; i++) {
                for (int j = 0; j < world[i].length; j++) {
                    grid[i][j] = world[i][j];
                }
            }


        }


    }

    @Override
    public void open(int x, int y) {
        if (x >= 0 && y >= 0 && x < row && y < col) {
            grid[x][y].open();
        }

        if(grid[x][y].isExplosive())
        {
            this.viewNotifier.notifyExploded(x, y);
        }

        else
        {
            this.viewNotifier.notifyOpened(x,y,getNearbyExplosives(x,y));
        }

    }

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
        this.viewNotifier.notifyUnflagged(x,y);
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

    public int getNearbyExplosives(int x, int y)
    {
        int count = 0;

        if(getTile(x-1,y-1).isExplosive())
        {
            count++;
        }

        if(getTile(x,y-1).isExplosive())
        {
            count++;
        }

        if(getTile(x+1,y-1).isExplosive())
        {
            count++;
        }

        if(getTile(x-1,y).isExplosive())
        {
            count++;
        }

        if(getTile(x+1,y).isExplosive())
        {
            count++;
        }

        if(getTile(x-1,y+1).isExplosive())
        {
            count++;
        }

        if(getTile(x,y+1).isExplosive())
        {
            count++;
        }

        if(getTile(x+1,y+1).isExplosive())
        {
            count++;
        }

        return count;
    }
}
