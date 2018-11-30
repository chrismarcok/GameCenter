package fall2018.csc207.game;

import java.io.Serializable;

/**
 * A (row, col) coordinate pair.
 * <p>
 * We use this class instead of a Pair<Integer, Integer> because Pair isn't serializable.
 */
public class CoordinatePair implements Serializable {
    private final int row;
    private final int col;

    public CoordinatePair(int row, int y) {
        this.row = row;
        this.col = y;
    }

    /**
     * Gets the row value of this coordinate pair.
     *
     * @return The row value of this coordinate pair.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the col value of this coordinate pair.
     *
     * @return The col value of this coordinate pair.
     */
    public int getCol() {
        return col;
    }
}
