package fall2018.csc207.game;

import java.io.Serializable;
import java.util.Observable;

/**
 * Represents a single insatnce of a game.
 */
public abstract class GameState extends Observable implements Serializable {

    /**
     * The score of this game state.
     */
    protected int score;

    /**
     * The maximum number of undos the player can have.
     */
    private int maxUndos;

    /**
     * Sets the maximum number of undos.
     * @param maxUndos The number of max undos.
     */
    public void setMaxUndos(int maxUndos){
        this.maxUndos = maxUndos;
    }

    /**
     * Get the number of undos allowed.
     *
     * @return The number of undos allowed.
     */
    public int getMaxUndos() {
        return maxUndos;
    }

    /**
     * Get the score.
     *
     * @return the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Set the score
     *
     * @param score The score.
     */
    protected void setScore(int score) {
        this.score = score;
    }

    /**
     * Revert to a past game state. This call always undos a canMove. To check if we're allowed to
     * undo that canMove, call canUndo().
     */
    public abstract void undo();

    /**
     * Check whether or not it is possible to use the undo function.
     *
     * @return True if it is possible to undo, false otherwise.
     */
    public abstract boolean canUndo();

    /**
     * Determines if the game is over.
     *
     * @return true if game is over, false otherwise.
     */
    public abstract boolean isOver();

    /**
     * Gets the name of the current game.
     * @return The name of the game.
     */
    public abstract String getGameName();
}
