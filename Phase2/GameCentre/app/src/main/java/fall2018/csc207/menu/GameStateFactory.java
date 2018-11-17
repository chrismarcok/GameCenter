package fall2018.csc207.menu;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc207.game.GameState;

/**
 * A class used for generating GameStates from given settings.
 */
public abstract class GameStateFactory<T extends GameState> {
    /**
     * Hashmap that takes the key as the name then the gamestate as a value
     */
    protected final List<Setting> settings = new ArrayList<>();

    /**
     * Gets a list of settings.
     */
    public List<Setting> getSettings() {
        return settings;
    }

    protected void addToSettings(Setting setting) {
        settings.add(setting);
    }

    /**
     * Generates the gameState with the given settings.
     *
     * @param numUndos The number of undos to allow.
     * @param path     Where the state will be saved.
     * @return The generated gameState.
     */
    public abstract T getGameState(int numUndos, Path path);

    /**
     * Returns the ID of the fragment for this game type.
     *
     * @return The ID of the fragment for this game type.
     */
    public abstract int getGameFragmentID();

    public class Setting {
        private final String name;
        private final List<String> possibleValues;
        private String currentValue;

        public Setting(String name, List<String> possibleValues, String defaultValue) {
            this.name = name;
            this.possibleValues = possibleValues;
            this.currentValue = defaultValue;
        }

        public String getName() {
            return name;
        }

        public void setCurrentValue(String currentValue) {
            this.currentValue = currentValue;
        }

        public List<String> getPossibleValues() {
            return possibleValues;
        }

        public String getCurrentValue() {
            return currentValue;
        }
    }
}
