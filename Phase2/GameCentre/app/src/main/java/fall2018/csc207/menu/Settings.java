package fall2018.csc207.menu;

import java.util.HashMap;

import fall2018.csc207.game.GameState;

/*
 * An Abstract Settings Class that holds different kinds of Game States
 */
public abstract class Settings {
    /**
     * Hashmap that takes the key as the name then the gamestate as a value
     */
    private HashMap<String, GameState> settings = new HashMap<>();

    /**
     * The maximum number of allowed undos.
     */
    private int undos;

    /**
     * The name of the game.
     */
    private String gameName;

    /**
     * A map from the game's name to the game's settings.
     */
    public HashMap<String, GameState> getSettings() {
        return settings;
    }

    /**
     * Set the settings of the game.
     *
     * @param settings The settings.
     */
    public void setSettings(HashMap<String, GameState> settings) {
        this.settings = settings;
    }

    /**
     * Set the name of the game.
     *
     * @param gameName the name of the game.
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Gets the maximum allowed number of undos.
     * @return The maximum allowed number of undos.
     */
    public int getUndos() {
        return undos;
    }

    /**
     * Sets the maximum allowed number of undos.
     * @param undos The maximum allowed number of undos.
     */
    public void setUndos(int undos) {
        this.undos = undos;
    }
}
