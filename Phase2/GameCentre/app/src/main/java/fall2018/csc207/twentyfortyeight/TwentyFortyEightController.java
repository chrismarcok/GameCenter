package fall2018.csc207.twentyfortyeight;

import java.io.Serializable;

import fall2018.csc207.game.BoardController;
import fall2018.csc207.game.GameController;

/**
 * Manage the board state by processing taps.
 */
public class TwentyFortyEightController extends BoardController<Board> implements Serializable {

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    public TwentyFortyEightController(Board board) {
        super(board);
    }

    public void moveRight() {
        gameState.moveRight();
        gameState.afterMoveActions(true);
    }
    public void moveLeft() {
        gameState.moveLeft();
        gameState.afterMoveActions(true);
    }
    public void moveUp() {
        gameState.moveUp();
        gameState.afterMoveActions(true);
    }
    public void moveDown() {
        gameState.moveDown();
        gameState.afterMoveActions(true);
    }

    @Override
    protected void updateGame(int position) {

    }

    @Override
    protected boolean isValidTap(int position) {
        return false;
    }
}
