package fall2018.csc207.minesweeper;

import java.io.Serializable;

import fall2018.csc207.game.BoardController;

/**
 * Controller for Minesweeper.
 */
public class MinesweeperController extends BoardController<Board> implements Serializable {

    /**
     * The dimension of the board
     */
    private int dimensions;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    public MinesweeperController(Board board) {
        super(board);
        this.dimensions = gameState.getDimensions();
    }

    /**
     * Processes user input and updates tile game accordingly.
     *
     * @param position The position of the input.
     */
    @Override
    public void updateGame(int position) {
        int row = position / dimensions;
        int col = position % dimensions;
        Tile curr_tile = gameState.getTile(row, col);
        if (!curr_tile.isFlagged()) {
            gameState.revealTile(row, col);
        }
        //Check for cases where the selected tile is a BOMB or BLANK_TILE
        if (curr_tile.getId() == Tile.BOMB) {
            gameState.endGame(row, col);
        } else if (curr_tile.getId() == Tile.BLANK_TILE && !curr_tile.isFlagged()) {
            gameState.revealSurroundingBlanks(row, col);
        }

    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    @Override
    protected boolean isValidTap(int position) {
        return true;
    }

    /**
     * Flags a Tile
     *
     * @param position the position
     */
    public void flagTile(int position) {
        int row = position / dimensions;
        int col = position % dimensions;
        gameState.flagTile(row, col);
    }
}
