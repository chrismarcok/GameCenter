package fall2018.csc207.minesweeper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc207.slidingtiles.R;

/**
 * A Tile in minesweeper
 */
public class Tile implements Serializable {
    /**
     * Contains integer to respective image
     */
    private static Map<Integer, Integer> imageMap(){
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
        map.put(BOMB, R.drawable.bomb);
        map.put(BLANK_TILE, R.drawable.blanktile);
        map.put(1, R.drawable.tile_1);
        map.put(2, R.drawable.tile_2);
        map.put(3, R.drawable.tile_3);
        map.put(4, R.drawable.tile_4);
        map.put(5, R.drawable.tile_5);
        map.put(6, R.drawable.tile_6);
        map.put(7, R.drawable.tile_7);
        map.put(8, R.drawable.tile_8);
        return map;
    }
    /**
     * The integer that represents a blank tile
     */
    final static int BLANK_TILE = 0;
    /**
     * The integer that represents a bomb
     */
    final static int BOMB = -1;
    /**
     * The number of adjacent bombs
     */
    private int id;
    /**
     * The background id to find the tile image
     */
    private int background;

    public Tile(int id){
        this.id = id;
        this.background = imageMap().get(id);

    }
    /**
     * Return the tile id.
     *
     * @return the type of Tile
     */
    public int getId() {
        return id;
    }

}
