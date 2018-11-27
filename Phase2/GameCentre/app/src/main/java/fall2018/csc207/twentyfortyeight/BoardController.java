package fall2018.csc207.twentyfortyeight;

import android.gesture.Gesture;

import java.io.Serializable;

import fall2018.csc207.game.GameController;

/**
 * Manage the board state by processing taps.
 */
public class BoardController extends GameController<Board> implements Serializable {

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    public BoardController(Board board) {
        super(board);
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
