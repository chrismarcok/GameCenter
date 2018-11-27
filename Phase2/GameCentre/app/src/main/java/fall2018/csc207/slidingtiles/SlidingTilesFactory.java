package fall2018.csc207.slidingtiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import fall2018.csc207.game.GameFactory;

/**
 * Represents the different settings for tiles
 */
public class SlidingTilesFactory extends GameFactory {
    /**
     * The settings for the tile game.
     */
    public SlidingTilesFactory() {
        addToSettings(new Setting(
                "MinesweeperBoard Size",
                Arrays.asList("3x3", "4x4", "5x5"),
                "3x3"));
    }

    /**
     * Make a Sliding Tiles board.
     *
     * @param dimensions The dimension of the square board.
     * @return A list of the tiles of this board.
     */
    private List<List<Tile>> generateBoard(int dimensions) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = dimensions * dimensions;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }

        //Remove the last tile, add a blank tile.
        tiles.remove(numTiles - 1);
        tiles.add(new Tile(numTiles, R.drawable.blanktile));
        shuffleTiles(tiles);

        return to2DArray(dimensions, tiles);
    }

    /**
     * Shuffles tiles. This will always generate a solved board, because we perform an even number
     * of swaps.
     * <p>
     * This mutates tiles.
     * Source: https://www.jstor.org/stable/2369492
     *
     * @param tiles The tiles to shuffle
     */
    private void shuffleTiles(List<Tile> tiles) {
        int numTiles = tiles.size();
        Random rng = new Random();

        int numSwaps = rng.nextInt(numTiles) * 2;
        for (int i = 0; i < numSwaps; i++) {
            int ind1 = rng.nextInt(numTiles - 1);
            int ind2 = rng.nextInt(numTiles - 1);

            if (ind1 == numTiles - 1) // We shouldn't swap the last tile
                ind1--;
            if (ind2 == numTiles - 1) // We shouldn't swap the last tile
                ind2--;
            if (ind1 == ind2) // Ensures we never swap the same 2 tiles
                ind1 += ind1 > 0 ? -1 : 1;

            Collections.swap(tiles, ind1, ind2);
        }
    }

    /**
     * Converts tiles to a 2D list. Assumes tiles.size() == dim.
     *
     * @param dim   The dimension of the 2D array (where width = length = dim).
     * @param tiles The array to generate a 2D array from.
     */
    private List<List<Tile>> to2DArray(int dim, List<Tile> tiles) {
        List<List<Tile>> returnList = new ArrayList<>();
        for (int i = 0; i < dim; i++) {
            returnList.add(new ArrayList<Tile>());
            for (int j = 0; j < dim; j++) {
                returnList.get(i).add(tiles.get(i * dim + j));
            }
        }
        return returnList;
    }

    /**
     * Get a new GameState of setting's dimensions.
     *
     * @param numUndos The number of undos to allow.
     * @return The new GameState.
     */
    @Override
    public Board getGameState(int numUndos) {
        Board board;
        switch (settings.get(0).getCurrentValue()) { //There should only be 1 thing in settings anyways
            case "3x3":
                board = new Board(generateBoard(3));
                break;
            case "4x4":
                board = new Board(generateBoard(4));
                break;
            case "5x5":
                board = new Board(generateBoard(5));
                break;
            default:
                throw new IllegalStateException("This factory's settings is in an illegal state!");
        }
        board.setMaxUndos(numUndos);
        return board;
    }

    /**
     * Get the game's fragment class.
     *
     * @return The game's fragment class.
     */
    @Override
    public Class getGameFragmentClass() {
        return SlidingTilesFragment.class;
    }

    /**
     * Get the names of the different sliding tiles variations.
     *
     * @return The names of the variations.
     */
    @Override
    public List<String> getGameNames() {
        return Arrays.asList("Sliding Tiles 3x3", "Sliding Tiles 4x4", "Sliding Tiles 5x5");
    }
}
