package fall2018.csc207.twentyfortyeight;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fall2018.csc207.game.GameState;

public class Board extends GameState implements Iterable<Tile> {

    private final Tile[][] board;
    private int numRows;
    private int numCols;
    private static final int COLS = 4;

    Board(int dimensions) {
        numCols = dimensions;
        numRows = dimensions;
        this.board = new Tile[dimensions][dimensions];
        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                this.board[row][col] = new Tile(0);
            }
        }
        addTile();
        addTile();
    }

    Board(Iterable<Tile> tiles, int dimensions) {
        this.board = new Tile[dimensions][dimensions];
        Iterator<Tile> iter = tiles.iterator();
        numCols = dimensions;
        numRows = dimensions;
        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                Tile x = iter.next();
                this.board[row][col] = x;
            }
        }

    }

    private int numTiles() {
        return numRows * numCols;
    }

    /**
     * 2048 MinesweeperTile logic: https://github.com/bulenkov/2048
     * Credits go to Konstantin Bulenkov
     * <p>
     * Add a new tile at an available spot
     */
    private void addTile() {
        List<Tile> list = availableSpace();
        if (!availableSpace().isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
            Tile emptyTile = list.get(index);
            emptyTile.value = Math.random() < 0.9 ? 2 : 4;
            emptyTile.setBackground(emptyTile.value);
        }
    }

    private List<Tile> availableSpace() {
        final List<Tile> list = new ArrayList<>(16);

        for (Tile[] row : board) {
            for (Tile tile : row) {
                if (tile.isEmpty()) {
                    list.add(tile);
                }
            }

        }
        return list;
    }

    /**
     * Merging tile Function: https://rosettacode.org/wiki/2048#Java
     */
    private boolean canMove(int countDownFrom, int yIncr, int xIncr) {
        for (int i = 0; i < getDimensions() * getDimensions(); i++) {
            int j = Math.abs(countDownFrom - i);
            int r = j / getDimensions();
            int c = j % getDimensions();

            if (board[r][c].isEmpty()) {
                continue;
            }
            int nextR = r + yIncr;
            int nextC = c + xIncr;

            while(nextR >= 0 && nextR < getDimensions() && nextC >= 0 && nextC < getDimensions()) {

                Tile next = board[nextR][nextC];
                Tile curr = board[r][c];

                if (next.isEmpty()) {

                    board[nextR][nextC] = curr;
                    board[r][c] = new Tile();
                    r = nextR;
                    c = nextC;
                    nextR += yIncr;
                    nextC += xIncr;

                } else {
                    break;
                }
            }
        }
        setChanged();
        notifyObservers();
        return true;
    }

    boolean canMoveUp() {
        return canMove(0, -1, 0);
    }

    boolean canMoveDown() {
        return canMove(getDimensions() * getDimensions() - 1, 1, 0);
    }

    boolean canMoveLeft() {
        return canMove(0, 0, -1);
    }

    boolean canMoveRight() {
        return canMove(getDimensions() * getDimensions() - 1, 0, 1);
    }

    void clearMerged() {
        for (Tile[] row : board)
            for (Tile tile : row)
                if (tile != null)
                    tile.setMerged(false);
    }

    boolean hasMovesAvailable() {
        return canMoveUp() || canMoveDown() || canMoveLeft() || canMoveRight();
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

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator(board);
    }

    private int getDimensions() {
        return numRows;
    }

    public Tile getTile(int row, int col) {
        return board[row][col];
    }


    private class BoardIterator implements Iterator<Tile> {

        /**
         * index indicates the position of the board
         */
        private int index;
        private Tile[][] array2D;

        private BoardIterator(Tile[][] tiles) {
            array2D = tiles;
        }

        @Override
        public boolean hasNext() {
            return index != numTiles();
        }

        @Override
        public Tile next() {
            int col = index % numCols;
            int row = index / numCols;
            index++;

            return array2D[row][col];
        }
    }

}
