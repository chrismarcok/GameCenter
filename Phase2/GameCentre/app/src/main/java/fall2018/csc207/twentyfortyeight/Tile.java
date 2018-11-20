package fall2018.csc207.twentyfortyeight;

import fall2018.csc207.game.GameState;

class Tile extends GameState {

    public int value;

    @Override
    public void undo() {

    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public boolean isOver() {
        return false;
    }

    @Override
    public String getGameName() {
        return null;
    }

    public boolean isEmpty() {
        return false;
    }
}
