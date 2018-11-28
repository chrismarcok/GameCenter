package fall2018.csc207.twentyfortyeight;

import java.io.Serializable;

import fall2018.csc207.game.GameController;

/**
 * Manage the board state by processing taps.
 */
class BoardController extends GameController<Board> implements Serializable {

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    public BoardController(Board board) {
        super(board);
    }

    public void moveRight() {
        gameState.canMoveRight();
    }
    public void moveLeft() {
        gameState.canMoveLeft();
    }
    public void moveUp() {
        gameState.canMoveUp();
    }
    public void moveDown() {
        gameState.canMoveDown();
    }
}
