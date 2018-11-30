package fall2018.csc207.twentyfortyeight;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import fall2018.csc207.slidingtiles.R;

/**
 * A TwentyFortEightTile in 2048
 */
class TwentyFortyEightTile implements Serializable {
    /**
     * Contains integer to respective image
     */
    private static Map<Integer, Integer> colorMap;
    static {
        colorMap = new HashMap<>();
        colorMap.put(2, R.color.two);
        colorMap.put(4, R.color.four);
        colorMap.put(8, R.color.eight);
        colorMap.put(16, R.color.sixteen);
        colorMap.put(32, R.color.thirtyTwo);
        colorMap.put(64, R.color.sixtyFour);
        colorMap.put(128, R.color.oneTwentyEight);
        colorMap.put(256, R.color.twoFiftySix);
        colorMap.put(512, R.color.fiveTwelve);
        colorMap.put(1024, R.color.tenTwentyFour);
        colorMap.put(2048, R.color.twentyFourtyEight);
        colorMap.put(0, R.color.gridColor);
    }

    /**
     * The numerical value of the tile
     */
    public int value;
    /**
     * The background of the tile
     */
    private int background;
    /**
     * If the tile has been merged
     */
    private boolean merged;

    /**
     * Represetnts a tile on the 2048 board
     * @param value the numerical value
     */
    public TwentyFortyEightTile(int value) {
        this.value = value;
        this.background = colorMap.get(value);
    }

    public TwentyFortyEightTile() {
        this.value = 0;
        this.background = colorMap.get(value);
    }

    /**
     * Checks if the value is 0
     * @return true if value is 0, false otherwise
     */
    public boolean isEmpty() {
        return value == 0;
    }

    /**
     * Gets the background image of the Tile
     * @return the background image
     */
    public int getBackground() {
        return this.background;
    }

    /**
     * Changed the background to the value
     * @param value the image
     */
    public void setBackground(int value) {
        this.background = colorMap.get(value);
    }

    /**
     * Checks if Tile is Merged
     * @return true if merged is true, false otherwise
     */
    public boolean isMerged() {
        return merged;
    }

    /**
     * Sets the value of merged
     * @param merged merged true or false
     */
    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    /**
     * Checks if Tile can merge with another tile
     * @param curr another tile
     * @return true if it can merge, false otherwise
     */

    public boolean canMergeWith(TwentyFortyEightTile curr) {
        return this.value == curr.value && !this.isMerged() && !curr.isMerged();
    }

    /**
     * Returs the value of the Board
     * @return the value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Sets the value of the Tile
     * @param value the desired value
     */
    public void setValue(int value) {
        this.value = value;
        this.background = colorMap.get(value);
    }
}
