package fall2018.csc207.twentyfortyeight;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fall2018.csc207.game.GameState;

public class Board extends GameState implements Iterable<Tile> {

    private static final int COLS = 4;
    static int highest;
    static int score;
    private final Tile[][] board;
    private int numRows;
    private int numCols;

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
        afterMoveActions();
    }

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
        afterMoveActions();

    }

    public void moveLeft() {
        for (int index = 0; index < board.length; index++) {
            Tile[] row = board[index];
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
        afterMoveActions();
    }

    public void moveRight() {
        for (int index = 0; index < board.length; index++) {
            Tile[] row = board[index];
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
        afterMoveActions();
    }

    /**
     * Swaps the zero tile with the root tile
     *
     * @param root tile with value
     * @param zero tile with no value
     */
    public void swapWithZero(Tile root, Tile zero) {
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
    public void merge(Tile root, Tile merger) {
        root.value = root.value + merger.value;
        merger.value = 0;
        merger.setMerged(true);
        root.setMerged(true);
        root.setBackground(root.value);
        merger.setBackground(merger.value);
    }

    /**
     * Clears all merge flgs after a move
     */
    public void clearMerged() {
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

    public void afterMoveActions() {
        clearMerged();
        setChanged();
        notifyObservers();
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
        //TODO: return moves available == 0 or similar
        return false;
    }

    @Override
    public String getGameName() {
        return "2048";
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
