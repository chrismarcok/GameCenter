package fall2018.csc207.minesweeper;

import org.junit.Test;

public class MinesweeperControllerTest {

    private int [][] generateBoard(){
        return new int[][]{
                {1, -1, 1, 0},
                {0, 2, 1, 1},
                {0, 1, -1, 1},
                {0, 1, 1, 1}
        };
    }

    @Test
    public void updateGameTestBomb(){

        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generateBoard());
        MinesweeperController minesweeperController = new MinesweeperController(minesweeperBoard);

        //Want to hit tile[2][2]
        minesweeperController.updateGame(10);
        assert minesweeperBoard.getNumRevealedTiles() == 14;

        //Hit Bomb at tile[0][1]
        minesweeperController.updateGame(1);
        assert minesweeperBoard.getNumRevealedTiles() == 15;



    }

    @Test
    public void updateGameTestNullTile(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generateBoard());
        MinesweeperController minesweeperController = new MinesweeperController(minesweeperBoard);

        assert minesweeperController.isValidTap(4);
        //Hit tile[2][1]
        minesweeperController.updateGame(4);
        assert  minesweeperBoard.getNumRevealedTiles() == 7;


        minesweeperController.flagTile(3);
    }
}
