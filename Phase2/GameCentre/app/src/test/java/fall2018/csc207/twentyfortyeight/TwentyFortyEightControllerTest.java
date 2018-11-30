package fall2018.csc207.twentyfortyeight;

import java.util.ArrayList;

public class TwentyFortyEightControllerTest {
    private TwentyFortyEightController board;


    private ArrayList<TwentyFortyEightTile> generateBoard() {
        ArrayList<TwentyFortyEightTile> tiles = new ArrayList<>(16);

        for (int row = 0; row != 4; row++) {
            for (int col = 0; col != 4; col++) {
                TwentyFortyEightTile newTile = new TwentyFortyEightTile(0);
                tiles.add(newTile);
            }
        }
        return tiles;
    }
}
