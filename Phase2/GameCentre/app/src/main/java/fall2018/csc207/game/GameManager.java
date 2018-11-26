package fall2018.csc207.game;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Steven on 2018-10-30.
 * Manages all the logic of a game while changing game data through interacting with gamestates interface
 */

public abstract class GameManager<T extends GameState> implements GestureDetector.OnGestureListener {

    /**
     * The model for the game.
     */
    public T gameState;

    /**
     * The GameManager.
     * @param gameState The gameState of this game.
     */
    public GameManager(T gameState) {
        this.gameState = gameState;
    }

    /**
     * Getter function for GameState retrieval.
     * @return The GameState.
     */
    public T getGameState(){
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

    /**
     * Called whenever a tap starts.
     * @param e The motionevent that triggered this function.
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    /**
     * Called when we have a down motionevent, but the finger has not moved yet.
     * @param e The motionevent that triggered this function.
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * Called whenever a tap ends.
     * @param e The motionevent that triggered this function.
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * Called when a scroll happens.
     * @param e1 The first down event.
     * @param e2 The move motion event that triggered this call.
     * @param distanceX The distance that we have moved our finger since e1.
     * @param distanceY The distance that we have moved our finger since e1.
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    /**
     * Called when a long press occurs.
     * @param e The motionevent that triggered this function.
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * Called whenever a fling happens.
     * @param e1 The first down event that started the fling.
     * @param e2 The current event that triggered this fling.
     * @param velocityX The x velocity of the fling.
     * @param velocityY The y velocity of the fling.
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
