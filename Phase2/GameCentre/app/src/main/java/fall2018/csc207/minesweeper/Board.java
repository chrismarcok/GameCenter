package fall2018.csc207.minesweeper;

import java.util.List;

import fall2018.csc207.game.GameState;

public class Board extends GameState {

    private Tile[][] mineField;

    public Board(int dimensions) {
        mineField = new Tile[dimensions][dimensions];

    }
    public Board(int dimensions, List<Tile> tiles) {
        mineField = new Tile[dimensions][dimensions];

    }

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
}
