package fall2018.csc207.twentyfortyeight;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fall2018.csc207.game.GameState;

public class Board extends GameState implements Iterable<TwentyFortyEightTile> {

    private final TwentyFortyEightTile[][] board;
    private int numRows;
    private int numCols;
    static int highest;
    static int score;
    boolean isSearchingForMove;
    private static final int COLS = 4;

    Board(int dimensions) {
        numCols = dimensions;
        numRows = dimensions;
        this.board = new TwentyFortyEightTile[dimensions][dimensions];
        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                this.board[row][col] = new TwentyFortyEightTile(0);
            }
        }
        addTwentyFortyEightTile();
        addTwentyFortyEightTile();
    }

    Board(Iterable<TwentyFortyEightTile> TwentyFortyEightTiles, int dimensions) {
        this.board = new TwentyFortyEightTile[dimensions][dimensions];
        Iterator<TwentyFortyEightTile> iter = TwentyFortyEightTiles.iterator();
        numCols = dimensions;
        numRows = dimensions;
        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                TwentyFortyEightTile x = iter.next();
                this.board[row][col] = x;
            }
        }

    }

    private int numTwentyFortyEightTiles() {
        return numRows * numCols;
    }

    /**
     * 2048 MinesweeperTwentyFortyEightTile logic: https://github.com/bulenkov/2048
     * Credits go to Konstantin Bulenkov
     * <p>
     * Add a new TwentyFortyEightTile at an available spot
     */
    private void addTwentyFortyEightTile() {
        List<TwentyFortyEightTile> list = availableSpace();
        if (!availableSpace().isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
            TwentyFortyEightTile emptyTwentyFortyEightTile = list.get(index);
            emptyTwentyFortyEightTile.setValue(Math.random() < 0.9 ? 2 : 4);
            emptyTwentyFortyEightTile.setBackground(emptyTwentyFortyEightTile.value);
        }
    }

    private List<TwentyFortyEightTile> availableSpace() {
        final List<TwentyFortyEightTile> list = new ArrayList<>(16);

        for (TwentyFortyEightTile[] row : board) {
            for (TwentyFortyEightTile TwentyFortyEightTile : row) {
                if (TwentyFortyEightTile.isEmpty()) {
                    list.add(TwentyFortyEightTile);
                }
            }

        }
        return list;
    }

    /**
     * Merging TwentyFortyEightTile Function: https://rosettacode.org/wiki/2048#Java
     */
    public void move(int countDownFrom, int yIncr, int xIncr) {

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

                TwentyFortyEightTile next = board[nextRow][nextCol];
                TwentyFortyEightTile curTwentyFortyEightTile = board[row][col];

                if (next.isEmpty()) {
                    board[nextRow][nextCol] = curTwentyFortyEightTile;
                    board[row][col] = new TwentyFortyEightTile();
                    row = nextRow;
                    col = nextCol;
                    nextRow += yIncr;
                    nextCol += xIncr;

                } else if (next.canMergeWith(curTwentyFortyEightTile)) {
                    board[nextRow][nextCol].value = curTwentyFortyEightTile.value * 2;
                    board[nextRow][nextCol].setBackground(next.value);
                    board[nextRow][nextCol].setMerged(true);
                    board[row][col] = new TwentyFortyEightTile();
                    break;

                } else {
                    break;
                }
            }
        }
        clearMerged();
        addTwentyFortyEightTile();
        setChanged();
        notifyObservers();
    }

    public void moveUp() {
//        move(0, -1, 0);
        for (int i = 0; i < getDimensions() * getDimensions(); i++) {
            int row = i / getDimensions();
            int col = i % getDimensions();
            if (board[row][col].isEmpty()) {
                continue;
            }
            int nextRow = row - 1;
            while((nextRow >= 0) && (nextRow < getDimensions()) && (col < getDimensions())) {

                TwentyFortyEightTile next = board[nextRow][col];
                TwentyFortyEightTile tile = board[row][col];

                if (next.isEmpty()) {
                    board[nextRow][col] = tile;
                    board[row][col] = new TwentyFortyEightTile();
                    row = nextRow;
                    nextRow += -1;

                } else if (next.canMergeWith(tile)) {
                    board[nextRow][col].value = tile.value * 2;
                    board[nextRow][col].setBackground(next.value);
                    board[nextRow][col].setMerged(true);
                    board[row][col] = new TwentyFortyEightTile();
                    break;
                } else {
                    break;
                }
            }
        }
        clearMerged();
        addTwentyFortyEightTile();
        setChanged();
        notifyObservers();
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
        for (TwentyFortyEightTile[] row : board)
            for (TwentyFortyEightTile tile : row)
                if (!tile.isEmpty())
                    tile.setMerged(false);
    }

    //TODO: Make a function to determine if there are any moves available
    //TODO: Make merge work

//    boolean canMakeMove() {
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
    public Iterator<TwentyFortyEightTile> iterator() {
        return new BoardIterator(board);
    }

    private int getDimensions() {
        return numRows;
    }

    public TwentyFortyEightTile getTwentyFortyEightTile(int row, int col) {
        return board[row][col];
    }

    private class BoardIterator implements Iterator<TwentyFortyEightTile> {

        /**
         * index indicates the position of the board
         */
        private int index;
        private TwentyFortyEightTile[][] array2D;

        private BoardIterator(TwentyFortyEightTile[][] TwentyFortyEightTiles) {
            array2D = TwentyFortyEightTiles;
        }

        @Override
        public boolean hasNext() {
            return index != numTwentyFortyEightTiles();
        }

        @Override
        public TwentyFortyEightTile next() {
            int col = index % numCols;
            int row = index / numCols;
            index++;

            return array2D[row][col];
        }
    }

}
