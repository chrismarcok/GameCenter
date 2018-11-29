package fall2018.csc207.twentyfortyeight;

import java.io.Serializable;

import fall2018.csc207.game.BoardController;

/**
 * Manage the board state by processing taps.
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
