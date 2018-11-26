package fall2018.csc207.twentyfortyeight;

import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;

import java.util.HashMap;
import java.util.Map;

import fall2018.csc207.game.GameState;
import fall2018.csc207.slidingtiles.R;

class Tile extends GameState {
//    private static Map<Integer, Integer> colorMap;
//
//    static {
//        colorMap = new HashMap<>();
//        colorMap.put(1, R.color.two);
//        colorMap.put(2, R.color.four);
//        colorMap.put(3, R.color.eight);
//        colorMap.put(6, R.color.sixteen);
//        colorMap.put(12, R.color.thirtyTwo);
//        colorMap.put(24, R.color.sixtyFour);
//        colorMap.put(48, R.color.oneTwentyEight);
//        colorMap.put(96, R.color.twoFiftySix);
//        colorMap.put(292, R.color.fiveTwelve);
//        colorMap.put(584, R.color.tenTwentyFour);
//        colorMap.put(, R.color.twentyFourtyEight);
//        colorMap.put(0, R.color.gridColor);

//    }

    public int value;
    private int background;
    private boolean merged;

    public Tile() {
        this.value = 0;
//        this.background = colorMap().get(0);
    }

    public Tile(int value) {
        this.value = value;
//        this.background = colorMap().get(value);
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
        this.background = R.color.two;
    }

    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }
}
