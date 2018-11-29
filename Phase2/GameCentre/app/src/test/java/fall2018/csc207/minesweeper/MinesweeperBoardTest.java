package fall2018.csc207.minesweeper;

import org.junit.Test;

/*
    Test Class for all public methods in MinesweeperBoard
 */
public class MinesweeperBoardTest {


    private int [][] generate4x4Board(){
        return new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 1, 1, 1},
                {0, 1, -1, 1}
        };
    }

    private int [][] generate4x4Board2(){
        return new int[][]{
                {0, 0, 0, 0},
                {0, 1, 1, 1},
                {0, 1, -1, 1},
                {0, 1, 1, 1}
        };
    }

    @Test
    public void testGenerateBoard() {
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        assert minesweeperBoard.getNumMines() == 1;
    }
    @Test
    public void testGenerateBoardAuto() {
        MinesweeperBoard newMinesweeperBoard = new MinesweeperBoard(5, .3);
        assert newMinesweeperBoard.getNumMines() <= 13;
        assert newMinesweeperBoard.getDimensions() == 5;

    }
    @Test
    public void testRevealTiles(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        minesweeperBoard.revealSurroundingBlanks(0,0);
        int num = 0;
        for (int i = 0; i < minesweeperBoard.getDimensions(); i++){
            for(int b = 0; b < minesweeperBoard.getDimensions(); b++){
                if (minesweeperBoard.getTile(i,b).isRevealed()){
                    num++;
                }
            }
        }
        assert num == 14;
    }

    @Test
    public void testendGame(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        minesweeperBoard.endGame(4,3);
        assert minesweeperBoard.getNumRevealedTiles() == 1;

    }

    @Test
    public void testisOver(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        minesweeperBoard.revealSurroundingBlanks(0,0);
        minesweeperBoard.revealTile(3,2);
        assert minesweeperBoard.isOver();
    }
    @Test
    public void testisOverWithFlag(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        minesweeperBoard.revealSurroundingBlanks(0,0);
        minesweeperBoard.revealTile(3,3);
        minesweeperBoard.flagTile(3,2);
        assert minesweeperBoard.isOver();
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

    @Test
    public void testdeleteBomb(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board2());
        minesweeperBoard.deleteBomb(3, 3);

        assert minesweeperBoard.getNumMines() == 0;

    }


}