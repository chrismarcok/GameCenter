package fall2018.csc207.minesweeper;

import java.io.Serializable;

import fall2018.csc207.game.BoardController;

/**
 * Controller for Minesweeper.
 */
public class MinesweeperController extends BoardController<MinesweeperBoard> implements Serializable {

    /**
     * The dimension of the board
     */
    private int dimensions;

    /**
     * Manage a minesweeperBoard that has been pre-populated.
     *
     * @param minesweeperBoard the minesweeperBoard
     */
    public MinesweeperController(MinesweeperBoard minesweeperBoard) {
        super(minesweeperBoard);
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
        MinesweeperTile currTile = gameState.getTile(row, col);
        //Check for cases where the selected tile is a BOMB or BLANK_TILE
        if (currTile.getId() == MinesweeperTile.BOMB && gameState.getNumRevealedTiles() == 0){
            gameState.deleteBomb(row,col);
            gameState.revealSurroundingBlanks(row, col);
        }
        else if (currTile.getId() == MinesweeperTile.BOMB) {
            gameState.endGame(row, col);
        }
        else if (currTile.getId() == MinesweeperTile.BLANK_TILE && !currTile.isFlagged()) {
            gameState.revealSurroundingBlanks(row, col);
        }

        if (!currTile.isFlagged()) {
            gameState.revealTile(row, col);
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
     * Flags a MinesweeperTile
     *
     * @param position the position
     */
    public void flagTile(int position) {
        int row = position / dimensions;
        int col = position % dimensions;
        gameState.flagTile(row, col);
    }
}
