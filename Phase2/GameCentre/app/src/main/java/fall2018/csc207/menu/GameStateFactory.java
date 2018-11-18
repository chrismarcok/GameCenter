package fall2018.csc207.menu;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc207.game.GameState;

/**
 * A class used for generating GameStates and other information needed for games from given settings.
 */
public abstract class GameStateFactory {
    /**
     * A list of settings for this GameStateFactory.
     */
    protected final List<Setting> settings = new ArrayList<>();

    /**
     * Gets a list of settings that we can set for this factory.
     */
    public List<Setting> getSettings() {
        return settings;
    }

    /**
     * Adds a setting to this GameStateFactory.
     * Should only be used by child classes to add settings.
     *
     * @param setting The setting to add.
     */
    protected void addToSettings(Setting setting) {
        settings.add(setting);
    }

    /**
     * Generates the gameState with the given settings.
     *
     * @param numUndos The number of undos to allow.
     * @return The generated gameState.
     */
    public abstract GameState getGameState(int numUndos);

    /**
     * Returns the class of the fragment for this game type.
     *
     * @return The class of the fragment for this game type.
     */
    public abstract Class getGameFragmentClass();

    /**
     * Returns a list of game names as it appears on the scoreboard and save file paths.
     *
     * @return List of game names for this game that appear on the scoreboard and save file paths.
     */
    public abstract List<String> getGameNames();

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

        /**
         * Sets the current value.
         *
         * @param index The index of value in possibleValues to set as current.
         */
        public void setCurrentValue(int index) {
            this.currentValue = possibleValues.get(index);
        }

        /**
         * Gets the possible values of this setting.
         *
         * @return A List<String> of possible string values.
         */
        public List<String> getPossibleValues() {
            return possibleValues;
        }

        /**
         * Returns the current value of this setting.
         *
         * @return The current value of this setting.
         */
        public String getCurrentValue() {
            return currentValue;
        }

        /**
         * Returns the index of the current value in the array.
         *
         * @return The index of the current value in the array.
         */
        public int getCurrentValueIndex() {
            return possibleValues.indexOf(currentValue);
        }
    }
}
