package fall2018.csc207.minesweeper;


import fall2018.csc207.game.GameState;

public class Board extends GameState {

    /**
     * The Tiles on the board
     */
    private Tile[][] mineField;

    /**
     *  return the size of the board
     * @return returns the size of the board
     */
    public int getDimensions() {
        return dimensions;
    }

    /**
     * The size of the board
     */
    private int dimensions;
    /**
     * Generate a board given the dimensions and difficulty
     *
     * @param dimensions
     * @param difficulty
     */
    public Board(int dimensions, double difficulty) {
        this.dimensions = dimensions;
        mineField = generateBoard(dimensions, difficulty);
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
     * @return Returns the number of mines on the board
     */
    public int getNumMines(){
        int count = 0;
        for(int i = 0; i < this.mineField.length; i++){
            for(int j = 0; j < this.mineField.length; j++){
                if (this.mineField[i][j].getId() == Tile.BOMB){
                    count++;
                }
            }
        }
        return count;
    }
    public Tile getTile(int row, int col) {
        return mineField[row][col];
    }

    /**
     * Reveals the single tile located at col,row
     * @param col
     * @param row
     */
    public void revealTile(int row, int col){
        mineField[row][col].setrevealed(true);
    }
    /**
     * Reveals surronding blank tiles as well as one layer of number tiles
     * Precondition: mineField[col][row].BLANK_TILE == 0
     * @param col
     * @param row
     */
    public void revealSurrondingBlanks(int row, int col){
         //Reveal the 4 surronding tiles that aren't Bombs, if a blank is revealed reveal the tiles around that blank as well
        if row+1 < dimensions-1 and mineField[row+1][col].getId() != Tile.BOMB and mineField[row+1][col].getrevealed == false{
            revealTile(row+1,col);
            if mineField[row+1][col].getId() == Tile.BLANK_TILE{
                revealSurrondingBlanks(row+1,col);
            }
        }
        if row-1 < dimensions-1 and mineField[row+1][col].getId() != Tile.BOMB and mineField[row-1][col].getrevealed == false{
            revealTile(row-1,col);
            if mineField[row+1][col].getId() == Tile.BLANK_TILE{
                revealSurrondingBlanks(row-1,col);
            }
        }
        if col+1 < dimensions-1 and mineField[row+1][col].getId() != Tile.BOMB and mineField[row][col + 1].getrevealed == false{
            revealTile(row,col+1);
            if mineField[row][col+1].getId() == Tile.BLANK_TILE{
                revealSurrondingBlanks(row,col + 1);
            }
        }
        if col - 1 < dimensions-1 and mineField[row+1][col].getId() != Tile.BOMB and mineField[row][col - 1].getrevealed == false{
            revealTile(row,col-1);
            if mineField[row][col-1].getId() == Tile.BLANK_TILE{
                revealSurrondingBlanks(row,col-1);
            }
        }

    }



}
