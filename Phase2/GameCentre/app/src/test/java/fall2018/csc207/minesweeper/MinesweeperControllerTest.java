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
    public void updateGameTest(){

        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generateBoard());
        MinesweeperController minesweeperController = new MinesweeperController(minesweeperBoard);

        //Want to hit tile[3][3]
        minesweeperController.updateGame(12);
        assert minesweeperBoard.getNumRevealedTiles() == 14;

        //Hit Bomb
        minesweeperController.updateGame(12);
        assert minesweeperBoard.getNumRevealedTiles() == 15;

        MinesweeperBoard minesweeperBoard2 = new MinesweeperBoard(generateBoard());
        MinesweeperController minesweeperController2 = new MinesweeperController(minesweeperBoard);

        assert minesweeperController2.isValidTap(3) == true;
        //Hit tile[2][1]
        minesweeperController2.updateGame(8);
        assert  minesweeperBoard.getNumRevealedTiles() == 7;

    }
}
