package model;

import model.AbstractMineSweeper;
import model.AbstractTile;
import model.Difficulty;

import java.util.Random;

public class Minesweeper extends AbstractMineSweeper {

    private static int row, col, explosionCount, flagCount;
    private static AbstractTile[][] grid;
    private boolean firstTileRule;
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
        firstTileRule = true;
        this.viewNotifier.notifyNewGame(row, col);
    }


    @Override
    public void toggleFlag(int x, int y) {
        if (grid[y][x].isFlagged()) {
            unflag(x, y);
        } else {
            flag(x, y);
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
        if (x < grid[0].length && y < grid.length && x >= 0 && y >= 0) {

            if (firstTileRule) {
                deactivateFirstTileRule(); // not first time anymore after this
                grid[y][x] = generateEmptyTile(); // first tile is empty
                getTile(x, y).open();


                // everything else empty for now
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        if (!(j == y && i == x)) {
                            grid[j][i] = generateEmptyTile();
                        }
                    }
                }

                Random rand = new Random();
                // setting random bombs
                while (explosionCount > 0) {
                    int upperBoundY = row-1;
                    int upperBoundX = col-1;
                    int intRandomY = rand.nextInt(upperBoundY);
                    int intRandomX = rand.nextInt(upperBoundX);

                    if (!(upperBoundX == x && upperBoundY == y)) {
                        grid[intRandomY][intRandomX] = generateExplosiveTile();
                        System.out.println(intRandomX + " " + intRandomY + " ");
                        explosionCount--;
                    }
                }


            } else {

                if (!grid[y][x].isFlagged() && !grid[y][x].isOpened()) {
                    grid[y][x].open();
                }

            }

            if (getTile(x, y).isExplosive()) {
                viewNotifier.notifyExploded(x, y);
                viewNotifier.notifyGameLost();
                return;
            }

            else {
                //get number of nearby bombs
                int counter = 0;
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (i >= 0 && i < grid[0].length && j >= 0 && j < grid.length) {
                            if (getTile(i, j).isExplosive()) {
                                counter += 1;
                            }
                        }
                    }
                }

                // open nearby empty tiles
                if (counter == 0) {
                    for (int i = x - 1; i <= x + 1; i++) {
                        for (int j = y - 1; j <= y + 1; j++) {
                            if (i >= 0 && i < grid[0].length && j >= 0 && j < grid.length) {
                                if (!getTile(i, j).isOpened()) {
                                    open(i, j);
                                }
                            }
                        }
                    }

                }
                viewNotifier.notifyOpened(x, y, counter);
            }


        }
    }

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
        firstTileRule = false;
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


}
