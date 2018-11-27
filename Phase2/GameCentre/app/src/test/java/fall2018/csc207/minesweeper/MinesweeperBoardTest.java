package fall2018.csc207.minesweeper;

import org.junit.Test;

public class MinesweeperBoardTest {


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
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        assert minesweeperBoard.getNumMines() == 1;
    }
    @Test
    public void testGenerateBoardAuto() {
        MinesweeperBoard new_Minesweeper_board = new MinesweeperBoard(5, .3);
        assert new_Minesweeper_board.getNumMines() <= 13;
        assert new_Minesweeper_board.getDimensions() == 5;

    }
    @Test
    public void testRevealTiles(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        minesweeperBoard.revealSurroundingBlanks(0,0);
        int num = 0;
        for (int i = 0; i < minesweeperBoard.getDimensions(); i++){
            for(int b = 0; b < minesweeperBoard.getDimensions(); b++){
                if (minesweeperBoard.getTile(i,b).getrevealed()){
                    num++;
                }
            }
        }
        assert num == 14;
    }
    @Test
    public void testisOver(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        minesweeperBoard.revealSurroundingBlanks(0,0);
        minesweeperBoard.revealTile(3,2);
        assert minesweeperBoard.isOver() == true;
    }
    @Test
    public void testisOverWithFlag(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        minesweeperBoard.revealSurroundingBlanks(0,0);
        minesweeperBoard.revealTile(3,3);
        minesweeperBoard.flagTile(3,2);
        assert minesweeperBoard.isOver() == true;
    }
    @Test
    public void testGetName(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        assert minesweeperBoard.getGameName().equals("Minesweeper 4x4");
    }
    @Test
    public void testDecrementScore(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        minesweeperBoard.decrementScore();
        minesweeperBoard.decrementScore();
        assert minesweeperBoard.getScore() == (100000-2);
    }

}