package fall2018.csc207.twentyfortyeight;

import java.util.Arrays;
import java.util.List;

import fall2018.csc207.game.GameFactory;
import fall2018.csc207.game.GameState;

public class TwentyFortyEightFactory extends GameFactory{

    public TwentyFortyEightFactory() {
        addToSettings(new GameFactory.Setting(
                "Difficulty",
                Arrays.asList("Easy", "Medium", "Hard", "MEGA-HARD!!!!"),
                "Easy"
        ));
    }

    @Override
    public GameState getGameState(int numUndos) {
        return null;
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
