package fall2018.csc207.minesweeper;


import fall2018.csc207.game.GameState;

public class Board extends GameState {

    /**
     * The Tiles on the board
     */
    private Tile[][] mineField;

    /**
     * Generate a board given the dimensions and difficulty
     *
     * @param dimensions
     * @param difficulty
     */
    public Board(int dimensions, double difficulty) {
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
     * Generating algorithm: https://introcs.cs.princeton.edu/java/14array/Minesweeper.java.html
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


}
