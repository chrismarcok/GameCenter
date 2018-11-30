package fall2018.csc207.twentyfortyeight;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fall2018.csc207.game.GameState;

public class TwentyFortyEightBoard extends GameState implements Iterable<TwentyFortyEightTile> {

    private final TwentyFortyEightTile[][] board;
    private int numRows;
    private int numCols;
    private int numActiveTiles;

    TwentyFortyEightBoard(int dimensions) {
        this.numCols = dimensions;
        this.numRows = dimensions;
        this.board = new TwentyFortyEightTile[numRows][numCols];
        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                this.board[row][col] = new TwentyFortyEightTile(0);
            }
        }
        addTile();
        addTile();
        determineScore();
    }

    TwentyFortyEightBoard(Iterable<TwentyFortyEightTile> tiles, int dimensions) {
        this.board = new TwentyFortyEightTile[dimensions][dimensions];
        Iterator<TwentyFortyEightTile> iter = tiles.iterator();
        numCols = dimensions;
        numRows = dimensions;
        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                TwentyFortyEightTile x = iter.next();
                this.board[row][col] = x;
            }
        }
    }

    /**
     * Returns the number of tiles in this board
     *
     * @return the numer of tiles
     */
    private int numTiles() {
        return numRows * numCols;
    }

    /**
     * Returns the number of non-empty tiles in this board
     *
     * @return the number of non-empty tiles
     */
    public int getNumActiveTiles() {
        return numActiveTiles;
    }

    /**
     * 2048 MinesweeperTile logic: https://github.com/bulenkov/2048
     * Credits go to Konstantin Bulenkov
     * <p>
     * Add a new tile at an available spot
     */
    private void addTile() {
        List<TwentyFortyEightTile> list = availableSpace();
        if (!availableSpace().isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
            TwentyFortyEightTile emptyTile = list.get(index);
            emptyTile.setValue(Math.random() < 0.9 ? 2 : 4);
            emptyTile.setBackground(emptyTile.value);
            numActiveTiles++;
        }
    }

    /**
     * Initializs a list of empty tiles  that are currently on the board
     *
     * @return a list of tiles
     */
    private List<TwentyFortyEightTile> availableSpace() {
        final List<TwentyFortyEightTile> list = new ArrayList<>(getDimensions());

        for (TwentyFortyEightTile[] row : board) {
            for (TwentyFortyEightTile twentyFortyEightTile : row) {
                if (twentyFortyEightTile.isEmpty()) {
                    list.add(twentyFortyEightTile);
                }
            }
        }
        return list;
    }

    /**
     * Shifts all tiles up and merges simliar tiles in sequence
     */
    public void moveUp() {
        for (int index = 0; index < board.length; index++) {
            for (int b = 0; b < board.length; b++) {
                boolean seen = false;
                for (int i = b + 1; i != board.length; i++) {
                    if (board[b][index].value == 0 && board[i][index].value != 0) {
                        swapWithZero(board[i][index], board[b][index]);
                    } else {
                        if (board[b][index].value != board[i][index].value && board[i][index].value != 0) {
                            seen = true;
                        }
                        if (board[i][index].canMergeWith(board[b][index]) && !seen) {
                            merge(board[b][index], board[i][index]);
                        }
                    }
                }
            }
        }

    }

    /**
     * Shifts all tiles down and merges simliar tiles in sequence
     */
    public void moveDown() {
        for (int index = 0; index < board.length; index++) {
            for (int b = board.length - 1; b >= 1; b--) {
                boolean seen = false;
                for (int i = b - 1; i >= 0; i--) {
                    if (board[b][index].value == 0 && board[i][index].value != 0) {
                        swapWithZero(board[i][index], board[b][index]);
                    } else {
                        if (board[b][index].value != board[i][index].value && board[i][index].value != 0) {
                            seen = true;
                        }
                        if (board[i][index].canMergeWith(board[b][index]) && !seen) {
                            merge(board[b][index], board[i][index]);
                        }
                    }
                }
            }
        }


    }

    /**
     * Shifts all tiles left and merges simliar tiles in sequence
     */
    public void moveLeft() {
        for (int index = 0; index < board.length; index++) {
            TwentyFortyEightTile[] row = board[index];
            for (int b = 0; b < board.length; b++) {
                boolean seen = false;
                for (int i = b + 1; i != board.length; i++) {
                    if (row[b].value == 0 && row[i].value != 0) {
                        swapWithZero(row[i], row[b]);
                    } else {
                        if (row[b].value != row[i].value && row[i].value != 0) {
                            seen = true;
                        }
                        if (row[i].canMergeWith(row[b]) && !seen) {
                            merge(row[b], row[i]);
                        }
                    }
                }
            }
        }

    }

    /**
     * Shifts all tiles right and merges simliar tiles in sequence
     */
    public void moveRight() {
        for (int index = 0; index < board.length; index++) {
            TwentyFortyEightTile[] row = board[index];
            for (int b = board.length - 1; b >= 1; b--) {
                boolean seen = false;
                for (int i = b - 1; i >= 0; i--) {
                    if (row[b].value == 0 && row[i].value != 0) {
                        swapWithZero(row[i], row[b]);
                    } else {
                        if (row[b].value != row[i].value && row[i].value != 0) {
                            seen = true;
                        }
                        if (row[i].canMergeWith(row[b]) && !seen) {
                            merge(row[b], row[i]);
                        }
                    }
                }
            }
        }

    }

    /**
     * Swaps the zero tile with the root tile
     *
     * @param root tile with value
     * @param zero tile with no value
     */
    private void swapWithZero(TwentyFortyEightTile root, TwentyFortyEightTile zero) {
        zero.value = root.value;
        root.value = 0;
        zero.setBackground(zero.value);
        root.setBackground(root.value);

    }

    /**
     * Merges the tiles root and merger by takinng merger,
     * adding it to root and setting merger to zero
     *
     * @param root   The destination of the tile
     * @param merger The merger
     */
    private void merge(TwentyFortyEightTile root, TwentyFortyEightTile merger) {
        root.value = root.value + merger.value;
        merger.value = 0;
        merger.setMerged(true);
        root.setMerged(true);
        root.setBackground(root.value);
        merger.setBackground(merger.value);
        determineScore();
        numActiveTiles--;
    }

    private void determineScore() {
        int currentScore = 0;
        int dimension = board.length;
        for (TwentyFortyEightTile[] aBoard : board) {
            for (int j = 0; j < dimension; j++) {
                currentScore += aBoard[j].getValue();
            }
        }
        this.score = currentScore;
    }

    /**
     * Clears all merge flags after a move
     */
    private void clearMerged() {
        for (TwentyFortyEightTile[] row : board)
            for (TwentyFortyEightTile twentyFortyEightTile : row)
                if (!twentyFortyEightTile.isEmpty())
                    twentyFortyEightTile.setMerged(false);
    }

    /**
     * Performs post move actions to prepare for next move and updates the board.
     * @param shouldNotifyObs Should update the board or not.
     */
    public void afterMoveActions(boolean shouldNotifyObs) {
        clearMerged();
        setChanged();
        if (shouldNotifyObs) {
            notifyObservers();
        }
        addTile();
    }


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
        if (availableSpace().size() != 0) {
            return false;
        } else {
            for (int i = 0; i < numCols; i++) {
                for (int j = 0; j < numRows; j++) {
                    if (hasMergableNeighbour(i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if the tile has a mergeable neighbour.
     * @param row row
     * @param col column
     * @return Whether tile can merge with a neighbour
     */
    private boolean hasMergableNeighbour(int row, int col) {
        int thisVal = board[row][col].getValue();
        if (row - 1 >= 0 && board[row - 1][col].getValue() == thisVal) {
            return true;
        } else if (col - 1 >= 0 && board[row][col - 1].getValue() == thisVal) {
            return true;
        } else if (row + 1 < numRows && board[row + 1][col].getValue() == thisVal) {
            return true;
        }
        return (col + 1 < numCols && board[row][col + 1].getValue() == thisVal);
    }

    @Override
    public String getGameName() {
        return "2048";
    }

    @NonNull
    @Override
    public Iterator<TwentyFortyEightTile> iterator() {
        return new BoardIterator(board);
    }

    private int getDimensions() {
        return numRows * numCols;
    }

    public TwentyFortyEightTile getTile(int row, int col) {
        return board[row][col];
    }

    private class BoardIterator implements Iterator<TwentyFortyEightTile> {

        /**
         * index indicates the position of the board
         */
        private int index;
        private TwentyFortyEightTile[][] array2D;

        private BoardIterator(TwentyFortyEightTile[][] tiles) {
            array2D = tiles;
        }

        @Override
        public boolean hasNext() {
            return index != numTiles();
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
