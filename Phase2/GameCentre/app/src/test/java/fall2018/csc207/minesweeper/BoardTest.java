package fall2018.csc207.minesweeper;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void generateBoard() {
        int [][] test_board = new int[][]{
                {0,0,0,0},
                {0,0,0,0},
                {0,1,1,1},
                {0,1,-1,1}
        };
        Board board = new Board(test_board);
        assert board.getNumMines() == 1;
    }

}