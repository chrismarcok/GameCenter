package fall2018.csc207.minesweeper;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {


    public int [][] generate4x4Board(){
        int[][] test_board = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 1, 1, 1},
                {0, 1, -1, 1}
        };
        return test_board;
    }

    public void testGenerateBoard() {
        Board board = new Board(generate4x4Board());
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
        Board board = new Board(generate4x4Board());
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
    @Test
    public void testisOver(){
        Board board = new Board(generate4x4Board());
        board.revealSurroundingBlanks(0,0);
        board.revealTile(3,2);
        assert board.isOver() == true;
    }
    @Test
    public void testisOverWithFlag(){
        Board board = new Board(generate4x4Board());
        board.revealSurroundingBlanks(0,0);
        board.revealTile(3,3);
        board.flagTile(3,2);
        assert board.isOver() == true;
    }
    @Test
    public void testGetName(){
        Board board = new Board(generate4x4Board());
        assert board.getGameName().equals("Minesweeper 4x4");
    }
    @Test
    public void testDecrementScore(){
        Board board = new Board(generate4x4Board());
        board.decrementScore();
        board.decrementScore();
        assert board.getScore() == (100000-2);
    }

}