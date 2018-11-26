package fall2018.csc207.slidingtiles;

import android.support.v4.util.Pair;

import java.io.Serializable;

import fall2018.csc207.game.BoardController;

/**
 * Manage the board state by processing taps.
 */
public class SlidingTileController extends BoardController<Board> implements Serializable {

    /**
     * The dimension of the board
     */
    private int dimensions;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    public SlidingTileController(Board board) {
        super(board);
        dimensions = (int)Math.sqrt(this.gameState.numTiles());
    }

    /**
     * Processes user input and updates tile game accordingly.
     * @param position The position of the input.
     */
    @Override
    public void updateGame(int position) {
        touchMove(position);
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    @Override
    protected boolean isValidTap(int position) {
        int row = position / dimensions;
        int col = position % dimensions;
        int blankId = gameState.numTiles();
        return gameState.findEmptyTileAdjacent(row, col, blankId, false) != null;
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    private void touchMove(int position) {

        int row = position / dimensions;
        int col = position % dimensions;
        int blankId = gameState.numTiles();

        // tiles is the blank tile, swap by calling Board's swap method.
        Pair<Integer, Integer> blankCoord = gameState.findEmptyTileAdjacent(row, col, blankId, false);
        if (blankCoord != null)
            gameState.swapTiles(blankCoord.first, blankCoord.second, row, col, true);
    }
}
