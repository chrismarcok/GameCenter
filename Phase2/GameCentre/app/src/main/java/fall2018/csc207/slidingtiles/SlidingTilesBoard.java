package fall2018.csc207.slidingtiles;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import fall2018.csc207.game.GameState;

/**
 * The sliding tiles board.
 */
public class SlidingTilesBoard extends GameState implements Iterable<SlidingTilesTile> {
    /**
     * The tiles on the board in row-major order.
     */
    private List<List<SlidingTilesTile>> tiles;

    /**
     * The current maximum allowed undos. This is decremented when we undo, and incremented when we
     * make a canMove.
     */
    private int allowedUndos = getMaxUndos();

    /**
     * A stack of previous moved tiles. We only keep track of 1 tile's location because we know the
     * other must be blank.
     * <p>
     * This is transient because it cannot be serialized by Java (for some reason).
     */
    private transient Stack<Pair<Integer, Integer>> prevMoves = new Stack<>();


    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param tiles the tiles for the board
     */
    SlidingTilesBoard(List<List<SlidingTilesTile>> tiles) {
        // We start the score at 100, where 100 is a perfect (and unobtainable) score.
        setScore(100);
        this.tiles = tiles;
    }


    /**
     * Get the coordinate pair of the blank tile.
     *
     * @return The coordinate pair of the blank tile. first is row, second is col.
     */
    public Pair<Integer, Integer> findBlankTile() {
        int blankId = this.numTiles();
        Iterator<SlidingTilesTile> iter = this.iterator();

        int blankRow = 0;
        int blankCol = 0;

        // Find the blank tile's row and col
        // For the loops, we assume the board is square.
        for (int rowIndex = 0; rowIndex < getDimensions(); rowIndex++) {
            for (int colIndex = 0; colIndex < getDimensions(); colIndex++) {
                if (iter.hasNext() && iter.next().getId() == blankId) {
                    blankRow = rowIndex;
                    blankCol = colIndex;
                }
            }
        }
        return new Pair<>(blankRow, blankCol);
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
        return tiles.size() * tiles.get(0).size();
    }

    /**
     * Returns the dimensions of the board (assuming the board is square)
     *
     * @return The dimensions of the board.
     */
    private int getDimensions() {
        return tiles.size();
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public SlidingTilesTile getTile(int row, int col) {
        return tiles.get(row).get(col);
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1           the first tile row
     * @param col1           the first tile col
     * @param row2           the second tile row
     * @param col2           the second tile col
     * @param addToPrevMoves If we should add this canMove onto the stack holding previous moves.
     */
    public void swapTiles(int row1, int col1, int row2, int col2, boolean addToPrevMoves) {

        this.score -= 1;
        SlidingTilesTile first = tiles.get(row1).get(col1);
        SlidingTilesTile second = tiles.get(row2).get(col2);
        tiles.get(row1).set(col1, new SlidingTilesTile(second.getId(), second.getBackground()));
        tiles.get(row2).set(col2, new SlidingTilesTile(first.getId(), first.getBackground()));

        // We may not want this canMove to be recorded.
        if (addToPrevMoves) {
            // prevMoves may be null if we load a game.
            //TODO: Fix
            if (prevMoves == null)
                prevMoves = new Stack<>();

            // We want to add the non-blank tile to the stack.
            if (first.getId() == numTiles()) prevMoves.add(new Pair<>(row1, col1));
            else prevMoves.add(new Pair<>(row2, col2));

            // We made another canMove, so we can continue undoing.
            if (allowedUndos < getMaxUndos() && 0 <= allowedUndos) {
                allowedUndos++;
            }
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Returns a string representation of MinesweeperBoard.
     *
     * @return A string representation of MinesweeperBoard.
     */
    @Override
    public String toString() {
        return "MinesweeperBoard{" +
                "tiles=" + tiles.toString() +
                '}';
    }

    /**
     * Returns a iterator for board.
     *
     * @return An iterator for board.
     */
    @NonNull
    @Override
    public Iterator<SlidingTilesTile> iterator() {
        return new BoardIterator();
    }

    /**
     * Revert to a past game state. This call always undos a canMove. To check if we're allowed to
     * undo that canMove, call canUndo().
     */
    @Override
    public void undo() {
        // If allowedUndos is less than 0, then we might be allowing infinite undos.
        if (allowedUndos > 0)
            allowedUndos--;

        Pair<Integer, Integer> prevMove = prevMoves.pop();
        if (prevMove.first != null && prevMove.second != null) {
            int row = prevMove.first;
            int col = prevMove.second;
            Pair<Integer, Integer> blankLocation = findBlankTile();
            if (blankLocation.first != null && blankLocation.second != null)
                swapTiles(row, col, blankLocation.first, blankLocation.second, false);
        }
    }

    /**
     * Determines whether we can undo a canMove.
     *
     * @return Whether we can undo a canMove.
     */
    @Override
    public boolean canUndo() {
        return prevMoves != null && !prevMoves.isEmpty() && allowedUndos != 0;
    }

    /**
     * Determines if the tiles are in Row-Major order.
     *
     * @return True if the tiles are in order, False otherwise.
     */
    public boolean isOver() {
        Iterator<SlidingTilesTile> iter = iterator();
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
        return "Sliding Tiles " + getDimensions() + "x" + getDimensions();
    }

    /**
     * A custom MinesweeperBoard iterator
     */
    private class BoardIterator implements Iterator<SlidingTilesTile> {
        /**
         * index indicates the position of the board
         */
        private int index;

        @Override
        public boolean hasNext() {
            return index != numTiles();
        }

        @Override
        public SlidingTilesTile next() {
            int col = index % getDimensions();
            int row = index / getDimensions();
            index++;

            return getTile(row, col);
        }
    }
}
