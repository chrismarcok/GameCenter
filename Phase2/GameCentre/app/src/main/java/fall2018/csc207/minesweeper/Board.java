package fall2018.csc207.minesweeper;


import fall2018.csc207.game.GameState;

public class Board extends GameState {

    /**
     * The Tiles on the board
     */
    private Tile[][] mineField;
    /**
     * The size of the board
     */
    private int dimensions;
    private int numrevealedTiles;
    private int numMines = 0;

    /**
     * Generate a board given the dimensions and difficulty
     *
     * @param dimensions the width and height of the board
     * @param difficulty the frequency of bombs
     */

    public Board(int dimensions, double difficulty) {
        setScore(100000);
        this.dimensions = dimensions;
        this.numrevealedTiles = 0;
        mineField = generateBoard(dimensions, difficulty);
    }

    /**
     * Generate a board given the integer 2d array representation
     *
     * @param numRep a 2d array representation of the board
     */
    public Board(int[][] numRep) {
        setScore(100000);
        mineField = new Tile[numRep.length][numRep[0].length];
        for (int i = 0; i < numRep.length; i++) {
            for (int j = 0; j < numRep.length; j++) {
                mineField[i][j] = new Tile(numRep[i][j]);
                if (numRep[i][j] == -1) {
                    numMines++;
                }
            }
        }
        this.dimensions = mineField.length;
        this.numrevealedTiles = 0;

    }

    /**
     * return the size of the board
     *
     * @return returns the size of the board
     */
    public int getDimensions() {
        return dimensions;
    }

    @Override
    public void undo() {
        
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    /**
     * Called when a bomb is clicked. Triggers board reveal
     */
    public void endGame(int row, int col) {
        score = 0;
        revealTile(row, col);
        isOver();
    }

    @Override
    public boolean isOver() {
        if (score == 0) {
            return true;
        }
        for (Tile[] first : mineField) {
            for (Tile tile : first) {
                if (tile.getId() == Tile.BOMB && tile.getrevealed()){
                    return true;
                }
                if (tile.getId() == Tile.BOMB && !tile.isFlagged()) {
                    return false;
                }


            }
        }
        if (numrevealedTiles + getNumMines() <= mineField.length * mineField.length - 1) {
            return false;
        }
        return true;

    }

    /**
     * Returns the name of the game
     *
     * @return minesweeper name
     */
    @Override
    public String getGameName() {
        String name = "Minesweeper ";
        String setting = String.valueOf(this.dimensions) + "x" + String.valueOf(this.dimensions);
        return name + setting;
    }

    /**
     * Generates a minesweeper board
     * Minesweeper Board generating algorithm: https://introcs.cs.princeton.edu/java/14array/Minesweeper.java.html
     * Credits go to Princeton University
     *
     * @param dimensions the width and height of the board
     * @param difficulty the frequency of bombs
     * @return A generated tile board
     */
    private Tile[][] generateBoard(int dimensions, double difficulty) {
        Tile[][] mines = new Tile[dimensions][dimensions];
        int[][] repMines = new int[dimensions + 2][dimensions + 2];
        for (int x = 1; x <= dimensions; x++) {
            for (int y = 1; y <= dimensions; y++) {
                if (difficulty >= Math.random()) {
                    numMines += 1;
                    repMines[x][y] = Tile.BOMB;
                }
            }
        }
        for (int x = 1; x <= dimensions; x++) {
            for (int y = 1; y <= dimensions; y++) {
                int counter = 0;
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (repMines[i][j] == Tile.BOMB) {
                            counter++;
                        }
                    }
                }
                if (repMines[x][y] != Tile.BOMB) {
                    repMines[x][y] = counter;
                }
                mines[x - 1][y - 1] = new Tile(repMines[x][y]);
            }
        }

        return mines;
    }

    /**
     * Return the number of mines
     *
     * @return Returns the number of mines on the board
     */
    public int getNumMines() {
        return numMines;
    }

    public Tile getTile(int row, int col) {
        return mineField[row][col];
    }

    /**
     * Reveals the single tile located at col,row
     *
     * @param col position of the column
     * @param row position of the row
     */
    public void revealTile(int row, int col) {
        mineField[row][col].setrevealed(true);
        mineField[row][col].setFlagged(false);
        numrevealedTiles++;
        setChanged();
        notifyObservers();
    }

    /**
     * Reveals surrounding blank tiles as well as one layer of number tiles
     * Precondition: mineField[col][row].BLANK_TILE == 0
     *
     * @param col position of the column
     * @param row position of the row
     */
    public void revealSurroundingBlanks(int row, int col) {
        //Reveal the 4 surrounding tiles that aren't Bombs, if a blank is revealed reveal the tiles around that blank as well
        // revealTile(row,col);

        if (row + 1 < dimensions && mineField[row + 1][col].getId() != Tile.BOMB && !mineField[row + 1][col].getrevealed()) {
            revealTile(row + 1, col);
            if (mineField[row + 1][col].getId() == Tile.BLANK_TILE) {
                revealSurroundingBlanks(row + 1, col);
            }
        }
        if (row - 1 >= 0 && mineField[row - 1][col].getId() != Tile.BOMB && !mineField[row - 1][col].getrevealed()) {
            revealTile(row - 1, col);
            if (mineField[row - 1][col].getId() == Tile.BLANK_TILE) {
                revealSurroundingBlanks(row - 1, col);
            }
        }
        if (col + 1 < dimensions && mineField[row][col + 1].getId() != Tile.BOMB && !mineField[row][col + 1].getrevealed()) {
            revealTile(row, col + 1);
            if (mineField[row][col + 1].getId() == Tile.BLANK_TILE) {
                revealSurroundingBlanks(row, col + 1);
            }
        }
        if (col - 1 >= 0 && mineField[row][col - 1].getId() != Tile.BOMB && !mineField[row][col - 1].getrevealed()) {
            revealTile(row, col - 1);
            if (mineField[row][col - 1].getId() == Tile.BLANK_TILE) {
                revealSurroundingBlanks(row, col - 1);
            }
        }

    }

    /**
     * Flags a tile
     *
     * @param
     */
    public void flagTile(int row, int col) {

        if (!getTile(row, col).getrevealed()) {
            getTile(row, col).setFlagged(!getTile(row, col).isFlagged());
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Decreases the score by one
     */
    public void decrementScore() {

        setScore(getScore() - 1);
    }

}
