package fall2018.csc207.twentyfortyeight;

import java.util.ArrayList;
import org.junit.Test;

public class TwentyFortyEightBoardTest {


    private ArrayList<TwentyFortyEightTile> populateEmptyBoard(int dimensions){
        ArrayList<TwentyFortyEightTile> tiles = new ArrayList<TwentyFortyEightTile>(16);

        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                TwentyFortyEightTile newTile = new TwentyFortyEightTile(0);
                tiles.add(newTile);
            }
        }
        return tiles;
    }

    @Test
    public void contructorTest(){
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(4);
        assert twentyFortyEightBoard.getNumActiveTiles() == 2;
    }

    @Test
    public void contructorGivenTilesTest(){
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(4);
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 4);
        assert twentyFortyEightBoard.getNumActiveTiles() == 0;
    }

    @Test
    public void moveLeftTest(){
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(4);
        tiles.set(0,new TwentyFortyEightTile(2));
        tiles.set(1,new TwentyFortyEightTile(2));
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 4);
        assert  twentyFortyEightBoard.getNumActiveTiles() == 2;
        twentyFortyEightBoard.moveLeft();
        assert  twentyFortyEightBoard.getNumActiveTiles() == 1;
    }

    @Test
    public void moveRightTest(){
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(4);
        tiles.set(0,new TwentyFortyEightTile(4));
        tiles.set(3,new TwentyFortyEightTile(4));
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 4);
        assert  twentyFortyEightBoard.getNumActiveTiles() == 2;
        twentyFortyEightBoard.moveRight();
        assert  twentyFortyEightBoard.getNumActiveTiles() == 1;
    }

    @Test
    public void moveUpTest(){
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(4);
        tiles.set(0,new TwentyFortyEightTile(8));
        tiles.set(4,new TwentyFortyEightTile(8));
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 4);
        assert  twentyFortyEightBoard.getNumActiveTiles() == 2;
        twentyFortyEightBoard.moveUp();
        assert  twentyFortyEightBoard.getNumActiveTiles() == 1;
    }

    @Test
    public void moveDownTest(){
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(4);
        tiles.set(0,new TwentyFortyEightTile(64));
        tiles.set(12,new TwentyFortyEightTile(64));
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 4);
        assert  twentyFortyEightBoard.getNumActiveTiles() == 2;
        twentyFortyEightBoard.moveDown();
        assert  twentyFortyEightBoard.getNumActiveTiles() == 1;
    }

    @Test
    public void isOverTest(){
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(2);
        tiles.set(0,new TwentyFortyEightTile(4));
        tiles.set(1,new TwentyFortyEightTile(16));
        tiles.set(2,new TwentyFortyEightTile(32));
        tiles.set(3,new TwentyFortyEightTile(64));
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 2);

        assert  twentyFortyEightBoard.getNumActiveTiles() == 4;
        assert  twentyFortyEightBoard.isOver();
    }
}
