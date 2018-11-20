package fall2018.csc207.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile implements Comparable<Tile>, Serializable {
    /**
     * Contains integer to respective image
     */
    private static Map<Integer, Integer> imageMap(){
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
        map.put(1, R.drawable.tile_1);
        map.put(2, R.drawable.tile_2);
        map.put(3, R.drawable.tile_3);
        map.put(4, R.drawable.tile_4);
        map.put(5, R.drawable.tile_5);
        map.put(6, R.drawable.tile_6);
        map.put(7, R.drawable.tile_7);
        map.put(8, R.drawable.tile_8);
        map.put(9, R.drawable.tile_9);
        map.put(10, R.drawable.tile_10);
        map.put(11, R.drawable.tile_11);
        map.put(12, R.drawable.tile_12);
        map.put(13, R.drawable.tile_13);
        map.put(14, R.drawable.tile_14);
        map.put(15, R.drawable.tile_15);
        map.put(16, R.drawable.tile_16);
        map.put(17, R.drawable.tile_17);
        map.put(18, R.drawable.tile_18);
        map.put(19, R.drawable.tile_19);
        map.put(20, R.drawable.tile_20);
        map.put(21, R.drawable.tile_21);
        map.put(22, R.drawable.tile_22);
        map.put(23, R.drawable.tile_23);
        map.put(24, R.drawable.tile_24);
        map.put(25, R.drawable.tile_25);
        return map;
    }

    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * The unique id.
     */
    private int id;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    public Tile(int id, int background) {
        this.id = id;
        this.background = background;
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId The id of the tile we are setting.
     */
    public Tile(int backgroundId) {
        id = backgroundId + 1;
        // This looks so ugly.
        background = imageMap().get(id);
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
