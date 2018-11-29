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
        determineScore();
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

    /**
     * Returns the number of tiles in this board
     * @return the numer of tiles
     */
    private int numTiles() {
        return numRows * numCols;
    }

    /**
     * 2048 MinesweeperTile logic: https://github.com/bulenkov/2048
     * Credits go to Konstantin Bulenkov
     *
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

    /**
     * Initializs a list of empty tiles  that are currently on the board
     *
     * @return a list of tiles
     */
    private List<Tile> availableSpace() {
        final List<Tile> list = new ArrayList<>(getDimensions());

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
        afterMoveActions();
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
        afterMoveActions();

    }
    /**
     * Shifts all tiles left and merges simliar tiles in sequence
     */
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

    /**
     * Shifts all tiles right and merges simliar tiles in sequence
     */
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
    private void swapWithZero(Tile root, Tile zero) {
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
    private void merge(Tile root, Tile merger) {
        root.value = root.value + merger.value;
        merger.value = 0;
        merger.setMerged(true);
        root.setMerged(true);
        root.setBackground(root.value);
        merger.setBackground(merger.value);
        determineScore();
    }

    private void determineScore(){
        int currentScore = 0;
        int dimension = board.length;
       for (int i = 0; i < dimension; i++){
           for (int j = 0; j < dimension; j ++){
                currentScore += board[i][j].getValue();
           }
       }
       this.score = currentScore;
    }

    /**
     * Clears all merge flgs after a move
     */
    private void clearMerged() {
        for (Tile[] row : board)
            for (Tile tile : row)
                if (!tile.isEmpty())
                    tile.setMerged(false);
    }

    //TODO: Make a function to determine if there are any moves available

//    boolean movesAvailable() {
//        return moveUp() || moveDown() || moveLeft() || moveRight();
//    }

    private void afterMoveActions() {
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
        return numRows*numCols;
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
