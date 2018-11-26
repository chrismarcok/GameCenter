package fall2018.csc207.game;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Steven on 2018-10-30.
 * Manages all the logic of a game while changing game data through interacting with gamestates interface
 */

public abstract class GameManager<T extends GameState> {

    /**
     * The model for the game.
     */
    public T gameState;

    /**
     * The GameManager.
     *
     * @param gameState The gameState of this game.
     */
    public GameManager(T gameState) {
        this.gameState = gameState;
    }

    /**
     * Getter function for GameState retrieval.
     *
     * @return The GameState.
     */
    public T getGameState() {
        return this.gameState;
    }

    /**
     * Processes the user input given my GestureDetect/MovementController and follows game logic
     * accordingly to update the Model(gameState).
     * Note: This should be the only method that outside classes interact with.
     *
     * @param position The position that was tapped.
     */
    public void processTap(int position) {
        if (isValidTap(position)) {
            updateGame(position);
        }
    }

    /**
     * Determines whether the given tap is valid for the game
     *
     * @return Returns true if and only if the tap is valid.
     */
    abstract protected boolean isValidTap(int position);

    /**
     * Calls subfunctions to update the gameState depending on position.
     *
     * @param position The position on the grid.
     */
    abstract protected void updateGame(int position);
}
