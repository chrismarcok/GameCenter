package fall2018.csc207.slidingtiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import fall2018.csc207.game.GameState;
import fall2018.csc207.menu.Settings;

/*
 * Represents the different settings for tiles
 */
public class TileSettings extends Settings {

    /*
     * The settings for the tile game.
     */
    public TileSettings() {
        HashMap<String, GameState> settings = new HashMap<>();
        this.setGameName("Sliding Tiles");
        Board threeByThree = new Board(generateBoard(3), 3);
        Board fourByFour = new Board(generateBoard(4), 4);
        Board fiveByFive = new Board(generateBoard(5), 5);

        //The names of the differnet Sliding Tiles difficulties. Used in DBHandler.
        settings.put("Sliding Tiles 3x3", threeByThree);
        settings.put("Sliding Tiles 4x4", fourByFour);
        settings.put("Sliding Tiles 5x5", fiveByFive);
        this.setSettings(settings);
    }

    /*
     * Make a Sliding Tiles board.
     *
     * @param dimensions The dimension of the square board.
     *
     * @return A list of the tiles of this board.
     */
    private List<Tile> generateBoard(int dimensions) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = dimensions * dimensions;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }

        //Remove the last tile, add a blank tile.
        tiles.remove(numTiles - 1);
        tiles.add(new Tile(numTiles, R.drawable.blanktile));
        return tiles;
    }

}
