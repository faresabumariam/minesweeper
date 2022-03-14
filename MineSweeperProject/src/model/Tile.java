package model;

import notifier.ITileStateNotifier;
import test.TestableTile;

public class Tile extends AbstractTile{
    private boolean visible;
    private boolean flag;


    public Tile()
    {
        visible=false;
    }

    @Override
    public boolean open() {
        visible = true;
        return visible;
    }

    @Override
    public void flag() {
        flag = true;
    }

    @Override
    public void unflag() {
        flag = false;
    }

    @Override
    public boolean isFlagged() {
        return flag;
    }

    @Override
    public boolean isOpened() {
        return visible;
    }

    @Override
    public boolean isExplosive() {
        return false;
    }

    @Override
    public void setTileNotifier(ITileStateNotifier notifier) {

    }
}
