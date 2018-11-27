package fall2018.csc207.twentyfortyeight;

import java.util.List;

import fall2018.csc207.game.GameFactory;
import fall2018.csc207.game.GameState;

public class TwentyFortyEightFactory extends GameFactory{

    public TwentyFortyEightFactory() {

    }

    @Override
    public GameState getGameState(int numUndos) {
        Board board = new Board(4);
        board.setMaxUndos(numUndos);
        return board;
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
