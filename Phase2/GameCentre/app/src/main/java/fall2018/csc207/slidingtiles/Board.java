package fall2018.csc207.slidingtiles;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import fall2018.csc207.game.GameState;

/**
 * The sliding tiles board.
 */
public class Board extends GameState implements Iterable<Tile> {
    /**
     * The number of rows.
     */
    private int numRows;

    /**
     * The number of columns.
     */
    private int numCols;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * The current maximum allowed undos. This is decremented when we undo, and incremented when we
     * make a move.
     */
    private int allowedUndos = getMaxUndos();

    /**
     * A stack of previous moved tiles. We only keep track of 1 tile's location because we know the
     * other must be blank.
     *
     * This is transient because it cannot be serialized by Java (for some reason).
     */
    private transient Stack<Pair<Integer, Integer>> prevMoves = new Stack<>();


    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles, int dimensions) {
        // We start the score at 100, where 100 is a perfect (and unobtainable) score.
        setScore(100);
        this.tiles = new Tile[dimensions][dimensions];
        Iterator<Tile> iter = tiles.iterator();
        numCols = dimensions;
        numRows = dimensions;
        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                Tile x = iter.next();
                this.tiles[row][col] = x;
            }
        }
        shuffleTiles();
    }

    /**
     * Perform legal swaps a preset number of times to result in a solvable board.
     */
    private void shuffleTiles(){
        int numShuffles = 1000;

        //Find the Blank Tile and a valid tile to swap it with. Swap the tiles.
        for (int i = 0; i < numShuffles; i++){
            Pair<Integer, Integer> blankTile = findBlankTile();
            Pair<Integer, Integer> swapTile = findEmptyTileAdjacent(blankTile.first, blankTile.second, this.numTiles(), true);

            swapTiles(swapTile.first,swapTile.second, blankTile.first, blankTile.second, false);
        }

        // Must offset the score by the number of shuffles made, since each shuffle lowers the
        // score by one.
        this.score += numShuffles;
    }

    /**
     * Get the coordinate pair of the blank tile.
     * @return The coordinate pair of the blank tile.
     */
    private Pair<Integer, Integer> findBlankTile(){
        int blankId = this.numTiles();
        Iterator<Tile> iter = this.iterator();

        int blankRow = 0;
        int blankCol = 0;

        //Find the blank tile's row and col
        for (int rowIndex = 0; rowIndex < this.numRows; rowIndex++) {
            for (int colIndex = 0; colIndex < this.numCols; colIndex++) {
                if (iter.next().getId() == blankId) {
                    blankRow = rowIndex;
                    blankCol = colIndex;
                }
            }
        }
        return new Pair<Integer, Integer>(blankRow, blankCol);
    }

    /**
     * Sets the maximum number of undos. Overriden because we need to reset allowedUndos.
     *
     * @param maxUndos The number of max undos.
     */
    @Override
    public void setMaxUndos(int maxUndos) {
        super.setMaxUndos(maxUndos);
        allowedUndos = maxUndos;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    public int numTiles() {
        return tiles.length * tiles[0].length;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     * @param addToPrevMoves If we should add this move onto the stack holding previous moves.
     */
    public void swapTiles(int row1, int col1, int row2, int col2, boolean addToPrevMoves) {

        this.score -= 1;
        Tile first = tiles[row1][col1];
        Tile second = tiles[row2][col2];
        tiles[row1][col1] = new Tile(second.getId(), second.getBackground());
        tiles[row2][col2] = new Tile(first.getId(), first.getBackground());

        setChanged();
        notifyObservers();

        // We may not want this move to be recorded.
        if (!addToPrevMoves)
            return;

        // prevMoves may be null if we load a game.
        if (prevMoves == null)
            prevMoves = new Stack<>();

        // We want to add the non-blank tile to the stack.
        if (first.getId() == numTiles()) prevMoves.add(new Pair<>(row1, col1));
        else prevMoves.add(new Pair<>(row2, col2));

        // We made another move, so we can continue undoing.
        if (allowedUndos < getMaxUndos() && 0 <= allowedUndos) {
            allowedUndos++;
        }
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator(tiles);
    }

    /**
     * Revert to a past game state. This call always undos a move. To check if we're allowed to
     * undo that move, call canUndo().
     */
    @Override
    public void undo() {
        Pair<Integer, Integer> prevMove = prevMoves.pop();
        int row = prevMove.first;
        int col = prevMove.second;
        Pair<Integer, Integer> blankLocation = findEmptyTileAdjacent(row, col, numTiles(), false);
        swapTiles(row, col, blankLocation.first, blankLocation.second, false);

        // If allowedUndos is less than 0, then we might be allowing infinite undos.
        if (allowedUndos > 0)
            allowedUndos--;
    }

    @Override
    public boolean canUndo() {
        return prevMoves != null && !prevMoves.isEmpty() && allowedUndos != 0;
    }

    /**
     *  Determines if the tiles are in Row-Major order.
     *  @return True if the tiles are in order, False otherwise.
     */
    public boolean isOver() {
        Iterator<Tile> iter = iterator();
        int counter = 1;
        while (iter.hasNext()) {
            int id = iter.next().getId();
            if (counter != id) {
                return false;
            }
            counter++;
        }
        return true;
    }

    @Override
    public String getGameName() {
        return "Sliding Tiles " + numRows + "x" + numCols;
    }

    /**
     * Finds a adjacent blank tile. Returns null if nothing was found.
     *
     * @param row The row of the current tile.
     * @param col The column of the current tile.
     * @param blankId The ID of the blank tile.
     * @param usingBlankTile Whether or not the row and col given are of the blank tile's.
     * @return The blank tile's location, as a row, col coordinate.
     */
    public Pair<Integer, Integer> findEmptyTileAdjacent(int row, int col, int blankId, boolean usingBlankTile) {
        Tile above = row == 0 ? null : getTile(row - 1, col);
        Tile below = row == numCols - 1 ? null : getTile(row + 1, col);
        Tile left = col == 0 ? null : getTile(row, col - 1);
        Tile right = col == numRows - 1 ? null : getTile(row, col + 1);

        if (usingBlankTile){
            ArrayList<Pair<Integer, Integer>> list = new ArrayList<>();
            if (above != null) list.add(new Pair<>(row - 1, col));
            if (below != null) list.add(new Pair<>(row + 1, col));
            if (left != null) list.add(new Pair<>(row, col - 1));
            if (right != null) list.add(new Pair<>(row, col + 1));
            if (list.size() != 0){
                Collections.shuffle(list);
                return list.get(0);
            }
        } else {
            if (above != null && above.getId() == blankId) return new Pair<>(row - 1, col);
            if (below != null && below.getId() == blankId) return new Pair<>(row + 1, col);
            if (left != null && left.getId() == blankId) return new Pair<>(row, col - 1);
            if (right != null && right.getId() == blankId) return new Pair<>(row, col + 1);
        }
        return null;
    }

    /**
     * A custom Board iterator
     */
    private class BoardIterator implements Iterator<Tile> {
        /**
         * index indicates the position of the board
         */
        private int index = 0;
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
