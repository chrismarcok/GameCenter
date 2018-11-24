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
     * @param dimensions
     * @param difficulty
     */

    public Board(int dimensions, double difficulty) {
        setScore(100000);
        this.dimensions = dimensions;
        this.numrevealedTiles = 0;
        mineField = generateBoard(dimensions, difficulty);
        print_board();
    }

    /**
     * Generate a board given the integer 2d array representation
     *
     * @param numRep
     */
    public Board(int[][] numRep) {
        mineField = new Tile[numRep.length][numRep[0].length];
        for (int i = 0; i < numRep.length; i++) {
            for (int j = 0; j < numRep.length; j++) {
                mineField[i][j] = new Tile(numRep[i][j]);
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
    public void endGame(){
        for (int i = 0; i < mineField.length; i++){
            for(int b = 0; b < mineField.length; b++){
                revealTile(i,b);
            }
        }
    }
    @Override
    public boolean isOver() {
        if (numrevealedTiles+getNumMines() <= mineField.length*mineField.length-1) {
            return false;
        }
        for(int i = 0; i < mineField.length; i++){
            for(int b = 0; b < mineField.length; b++){
                if (mineField[i][b].getId() == Tile.BOMB && !mineField[i][b].isFlagged()){
                    return false;
                }
            }
        }
        return true;

    }

    @Override
    public String getGameName() {
        return "MineSweeper";
    }

    /**
     * Generates a minesweeper board
     * Minesweeper Board generating algorithm: https://introcs.cs.princeton.edu/java/14array/Minesweeper.java.html
     * Credits go to Princeton University
     *
     * @param dimensions
     * @param difficulty
     * @return A generated tile board
     */
    public Tile[][] generateBoard(int dimensions, double difficulty) {
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
     * @param col
     * @param row
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
     * @param col
     * @param row
     */
    public void revealSurroundingBlanks(int row, int col) {
        //Reveal the 4 surrounding tiles that aren't Bombs, if a blank is revealed reveal the tiles around that blank as well
        // revealTile(row,col);

        if (row + 1 < dimensions && mineField[row + 1][col].getId() != Tile.BOMB && mineField[row + 1][col].getrevealed() == false) {
            revealTile(row + 1, col);
            if (mineField[row + 1][col].getId() == Tile.BLANK_TILE) {
                revealSurroundingBlanks(row + 1, col);
            }
        }
        if (row - 1 >= 0 && mineField[row - 1][col].getId() != Tile.BOMB && mineField[row - 1][col].getrevealed() == false) {
            revealTile(row - 1, col);
            if (mineField[row - 1][col].getId() == Tile.BLANK_TILE) {
                revealSurroundingBlanks(row - 1, col);
            }
        }
        if (col + 1 < dimensions && mineField[row][col + 1].getId() != Tile.BOMB && mineField[row][col + 1].getrevealed() == false) {
            revealTile(row, col + 1);
            if (mineField[row][col + 1].getId() == Tile.BLANK_TILE) {
                revealSurroundingBlanks(row, col + 1);
            }
        }
        if (col - 1 >= 0 && mineField[row][col - 1].getId() != Tile.BOMB && mineField[row][col - 1].getrevealed() == false) {
            revealTile(row, col - 1);
            if (mineField[row][col - 1].getId() == Tile.BLANK_TILE) {
                revealSurroundingBlanks(row, col - 1);
            }
        }

    }

    /**
     * Flags a tile
     * @param tile represents a tile
     */
    public void flagTile(Tile tile) {
        if (!tile.getrevealed()) {
            tile.setFlagged(!tile.isFlagged());
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Testing method for printing out the board
     */
    public void print_board() {
        for (int i = 0; i < mineField.length; i++) {
            for (int b = 0; b < mineField.length; b++) {
                System.out.print(mineField[i][b] + "  ");
            }
            System.out.println();
        }
    }
    public void decrementScore(){
        setScore(getScore()-1);
    }



}
