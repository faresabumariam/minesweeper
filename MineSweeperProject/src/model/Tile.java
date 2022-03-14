package model;

import notifier.ITileStateNotifier;
import test.TestableTile;

public class Tile extends AbstractTile {
    private boolean visible;

    @Override
    public boolean open() {
        return false;
    }

    @Override
    public void flag() {



    }

    @Override
    public void unflag() {

    }

    @Override
    public boolean isFlagged() {
        return false;
    }

    @Override
    public boolean isExplosive() {
        return false;
    }

    @Override
    public boolean isOpened() {
        return false;
    }

}
