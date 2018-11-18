package fall2018.csc207.slidingtiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fall2018.csc207.game.GameFactory;

/*
 * Represents the different settings for tiles
 */
public class SlidingTilesFactory extends GameFactory {
    /*
     * The settings for the tile game.
     */
    public SlidingTilesFactory() {
        addToSettings(new Setting(
                "Board Size",
                Arrays.asList("3x3", "4x4", "5x5"),
                "3x3"));
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

    @Override
    public Board getGameState(int numUndos) {
        Board board;
        switch (settings.get(0).getCurrentValue()) { //There should only be 1 thing in settings anyways
            case "3x3":
                board = new Board(generateBoard(3), 3);
                break;
            case "4x4":
                board = new Board(generateBoard(4), 4);
                break;
            case "5x5":
                board = new Board(generateBoard(5), 5);
                break;
            default:
                throw new IllegalStateException("This factory's settings is in an illegal state!");
        }
        board.setMaxUndos(numUndos);
        return board;
    }

    @Override
    public Class getGameFragmentClass() {
        return SlidingTilesFragment.class;
    }

    @Override
    public List<String> getGameNames() {
        return Arrays.asList("Sliding Tiles 3x3", "Sliding Tiles 4x4", "Sliding Tiles 5x5");
    }
}
