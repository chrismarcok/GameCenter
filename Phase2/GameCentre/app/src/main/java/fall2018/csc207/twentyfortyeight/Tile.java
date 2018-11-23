package fall2018.csc207.twentyfortyeight;

import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;

import java.util.HashMap;
import java.util.Map;

import fall2018.csc207.game.GameState;
import fall2018.csc207.slidingtiles.R;

class Tile extends GameState {
    private static Map<Integer, Integer> colorMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(2, R.color.two);
        map.put(4, R.color.four);
        map.put(8, R.color.eight);
        map.put(16, R.color.sixteen);
        map.put(32, R.color.thirtyTwo);
        map.put(64, R.color.sixtyFour);
        map.put(128, R.color.oneTwentyEight);
        map.put(256, R.color.twoFiftySix);
        map.put(512, R.color.fiveTwelve);
        map.put(1024, R.color.tenTwentyFour);
        map.put(2048, R.color.twentyFourtyEight);
        map.put(0, R.color.gridColor);
        return map;
    }

    public int value;
    private int background;

    public Tile() {
        this.value = 0;
        this.background = colorMap().get(0);
    }

    public Tile(int value) {
        this.value = value;
        this.background = colorMap().get(value);
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
        this.background = colorMap().get(value);
    }
}
