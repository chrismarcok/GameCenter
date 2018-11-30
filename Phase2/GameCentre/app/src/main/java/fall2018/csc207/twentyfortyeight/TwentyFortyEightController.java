package fall2018.csc207.twentyfortyeight;

import java.io.Serializable;

import fall2018.csc207.game.BoardController;

/**
 * Manage the board state by processing swipes.
 */
public class TwentyFortyEightController extends BoardController<TwentyFortyEightBoard> implements Serializable {

    /**
     * Manage a twentyFortyEightBoard that has been pre-populated.
     *
     * @param twentyFortyEightBoard the twentyFortyEightBoard
     */
    public TwentyFortyEightController(TwentyFortyEightBoard twentyFortyEightBoard) {
        super(twentyFortyEightBoard);
    }

    /**
     * Moves all the tiles right
     */
    public void moveRight() {
        gameState.moveRight();
        gameState.afterMoveActions(true);
    }

    /**
     * Moves all the tiles left
     */
    public void moveLeft() {
        gameState.moveLeft();
        gameState.afterMoveActions(true);
    }

    /**
     * Moves all the tiles up
     */
    public void moveUp() {
        gameState.moveUp();
        gameState.afterMoveActions(true);
    }

    /**
     * Moves all the tiles down
     */
    public void moveDown() {
        gameState.moveDown();
        gameState.afterMoveActions(true);
    }

    /**
     * Gesture not used in 2048
     * @param position The position on the grid.
     */
    @Override
    protected void updateGame(int position) {

    }

    /**
     * Gesture not used in 2048
     * @param position of the tap
     * @return false
     */
    @Override
    protected boolean isValidTap(int position) {
        return false;
    }
}
