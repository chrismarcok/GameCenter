package fall2018.csc207.twentyfortyeight;

import java.util.List;

import fall2018.csc207.game.GameFactory;
import fall2018.csc207.game.GameState;

public class TwentyFortyEightFactory extends GameFactory{

    public TwentyFortyEightFactory() {

    }

    @Override
    public GameState getGameState(int numUndos) {
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(4);
        twentyFortyEightBoard.setMaxUndos(numUndos);
        return twentyFortyEightBoard;
    }

    @Override
    public Class getGameFragmentClass() {
        return TwentyFortyEightFragment.class;
    }

    @Override
    public List<String> getGameNames() {
        return null;
    }
}
