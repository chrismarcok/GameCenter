package fall2018.csc207.twentyfortyeight;

import java.util.HashMap;
import java.util.Map;

import fall2018.csc207.game.GameState;
import fall2018.csc207.slidingtiles.R;

class Tile extends GameState {

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

    public int value;
    private int background;
    private boolean merged;

    public Tile() {
        this.value = 0;
        this.background = colorMap.get(0);
    }

    public Tile(int value) {
        this.value = value;
        this.background = colorMap.get(value);
    }

    public boolean isEmpty() {
        return value == 0;
    }

    public int getBackground() {
        return this.background;
    }

    @Override
    public void undo() {

    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public boolean isOver() {
        return false;
    }

    @Override
    public String getGameName() {
        return null;
    }

    public void setBackground(int value) {
        this.background = colorMap.get(value);
    }

    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    public boolean canMergeWith(Tile curr) {
        return this.value == curr.value;
    }

    public void mergeWith(Tile curr) {

    }
}
