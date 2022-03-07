package model;

import notifier.ITileStateNotifier;
import test.TestableTile;

public class Tile implements TestableTile {
    private boolean visible;


    public Tile()
    {
        visible=false;
    }

    @Override
    public boolean open() {
        return visible;
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
    public void setTileNotifier(ITileStateNotifier notifier) {

    }
}
