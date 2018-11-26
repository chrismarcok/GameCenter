package fall2018.csc207.minesweeper;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {


    @Test

    public void testGenerateBoard() {
        int[][] test_board = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 1, 1, 1},
                {0, 1, -1, 1}
        };
        Board board = new Board(test_board);
        assert board.getNumMines() == 1;
    }
    @Test
    public void testGenerateBoardAuto() {
        Board new_board = new Board(5, .3);
        assert new_board.getNumMines() <= 13;
        assert new_board.getDimensions() == 5;

    }
    @Test
    public void testRevealTiles(){
        int[][] test_board = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 1, 1, 1},
                {0, 1, -1, 1}
        };
        Board board = new Board(test_board);
        board.revealSurroundingBlanks(0,0);
        int num = 0;
        for (int i = 0; i < board.getDimensions(); i++){
            for(int b = 0; b < board.getDimensions(); b++){
                if (board.getTile(i,b).getrevealed()){
                    num++;
                }
            }
        }
        assert num == 14;
    }


}