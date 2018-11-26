package fall2018.csc207.twentyfortyeight;

import android.gesture.Gesture;

import java.io.Serializable;

import fall2018.csc207.game.GameController;

/**
 * Manage the board state by processing taps.
 */
public class BoardController extends GameController<Board> implements Serializable {

    /**
     * The dimension of the board
     */
    private int dimensions;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    public BoardController(Board board) {
        super(board);
        dimensions = (int)Math.sqrt(this.gameState.getDimensions());
    }

    /**
     * Processes user input and updates tile game accordingly.
     * @param position The input guesture.
     */
    protected void updateGame(int position) {

    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    protected boolean isValidTap(int position) {
//        int row = position / dimensions;
//        int col = position % dimensions;
//        int blankId = gameState.numTiles();
//        return gameState.findEmptyTileAdjacent(row, col, blankId, false) != null;
        return true;
    }


    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param gesture the position
     */
    private void touchMove(Gesture gesture) {

//        int row = position / dimensions;
//        int col = position % dimensions;
//        int blankId = gameState.numTiles();
//
//        // tiles is the blank tile, swap by calling Board's swap method.
//        Pair<Integer, Integer> blankCoord = gameState.findEmptyTileAdjacent(row, col, blankId, false);
//        if (blankCoord != null)
//            gameState.swapTiles(blankCoord.first, blankCoord.second, row, col, true);
    }

    public void moveRight() {
        gameState.moveRight();
    }
    public void moveLeft() {
        gameState.moveLeft();
    }
    public void moveUp() {
        gameState.moveUp();
    }
    public void moveDown() {
        gameState.moveDown();
    }
}
