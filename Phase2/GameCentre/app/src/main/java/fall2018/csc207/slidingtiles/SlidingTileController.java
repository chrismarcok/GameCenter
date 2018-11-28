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
        Pair<Integer, Integer> blank = gameState.findBlankTile();
        return isTileAdjacent(row, col, blank); // The blank tile is adjacent to our tap
    }

    private boolean isTileAdjacent(int row, int col, Pair<Integer, Integer> tile) {
        return tile.first != null && tile.second != null &&
                Math.abs(tile.first - row + tile.second - col) == 1;

    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    private void touchMove(int position) {
        int row = position / dimensions;
        int col = position % dimensions;

        // tiles is the blank tile, swap by calling MinesweeperBoard's swap method.
        Pair<Integer, Integer> blankCoord = gameState.findBlankTile();

        // We are precisely 1 tile away from the blank tile, which means we're adjacent to it
        if (isTileAdjacent(row, col, blankCoord) &&
                blankCoord.first != null && blankCoord.second != null)
            gameState.swapTiles(blankCoord.first, blankCoord.second, row, col, true);
    }
}
