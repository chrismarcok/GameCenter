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
    static int highest;
    static int score;
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
            emptyTile.setValue(Math.random() < 0.9 ? 2 : 4);
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
    private void move(int countDownFrom, int yIncr, int xIncr) {

        for (int i = 0; i < getDimensions() * getDimensions(); i++) {
            int startMergeFrom = Math.abs(countDownFrom - i);
            int row = startMergeFrom / getDimensions();
            int col = startMergeFrom % getDimensions();

            if (board[row][col].isEmpty()) {
                continue;
            }
            int nextRow = row + yIncr;
            int nextCol = col + xIncr;

            while(nextRow >= 0 && nextRow < getDimensions() && nextCol >= 0 && nextCol < getDimensions()) {

                Tile next = board[nextRow][nextCol];
                Tile curTile = board[row][col];

                if (next.isEmpty()) {
                    board[nextRow][nextCol] = curTile;
                    board[row][col] = new Tile();
                    row = nextRow;
                    col = nextCol;
                    nextRow += yIncr;
                    nextCol += xIncr;

                } else if (next.canMergeWith(curTile)) {
                    board[nextRow][nextCol].value = curTile.value * 2;
                    board[nextRow][nextCol].setBackground(next.value);
                    board[nextRow][nextCol].setMerged(true);
                    board[row][col] = new Tile();
                    break;
                } else {
                    break;
                }
            }
        }
        clearMerged();
        addTile();
        setChanged();
        notifyObservers();
    }

    public void moveUp() {
        move(0, -1, 0);
    }

    public void moveDown() {
        move(getDimensions() * getDimensions() - 1, 1, 0);
    }

    public void moveLeft() {
        move(0, 0, -1);
    }

    public void moveRight() {
        move(getDimensions() * getDimensions() - 1, 0, 1);
    }

    private void clearMerged() {
        for (Tile[] row : board)
            for (Tile tile : row)
                if (!tile.isEmpty())
                    tile.setMerged(false);
    }

    //TODO: Make a function to determine if there are any moves available
    //TODO: Make merge work

//    boolean movesAvailable() {
//        return moveUp() || moveDown() || moveLeft() || moveRight();
//    }


    @Override
    public void undo() {
        //TODO: Make undo function
    }

    @Override
    public boolean canUndo() {
        //TODO: Determine if can undo
        return false;
    }

    @Override
    public boolean isOver() {
        //TODO: return moves available == 0 or similar
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
