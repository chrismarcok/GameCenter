package fall2018.csc207.minesweeper;

import java.io.Serializable;

import fall2018.csc207.game.GameManager;

public class BoardManager extends GameManager<Board> implements Serializable {

    /**
     * The dimension of the board
     */
    private int dimensions;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    public BoardManager(Board board) {
        super(board);
    }

    /**
     * Processes user input and updates tile game accordingly.
     * @param position The position of the input.
     */
    public void updateGame(int position) {
        int row = position / dimensions;
        int col = position % dimensions;
        curr_tile = gamestate.getTile(row,col);
        gamestate.revealTile(row,col);

         //Check for cases where the selected tile is a BOMB or BLANK_TILE
        if curr_tile.getId() == gamestate.Tile.BOMB{
            //END GAME
        }
        else if curr_tile.getId() == gamestate.Tile.BLANK_TILE{
            gamestate.revealBlankTiles(row,col);
        }

    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    protected boolean isValidTap(int position) {
        return true;
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    private void touchMove(int position) {

        int row = position / dimensions;
        int col = position % dimensions;
    }
}
