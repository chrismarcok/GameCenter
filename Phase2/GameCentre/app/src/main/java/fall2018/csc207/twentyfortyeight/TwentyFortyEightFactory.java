package fall2018.csc207.twentyfortyeight;

import java.util.Arrays;
import java.util.List;

import fall2018.csc207.game.GameFactory;
import fall2018.csc207.game.GameState;

public class TwentyFortyEightFactory extends GameFactory{

    public TwentyFortyEightFactory() {
        addToSettings(new Setting(
                "TwentyFortyEight Size",
                Arrays.asList("3x3", "4x4", "5x5", "6x6"),
                "4x4"
        ));
    }

    @Override
    public GameState getGameState(int numUndos) {
        TwentyFortyEightBoard board;
        switch (settings.get(0).getCurrentValue()) { //There should only be 1 thing in settings
            case "3x3":
                board = new TwentyFortyEightBoard(3);
                break;
            case "4x4":
                board = new TwentyFortyEightBoard(4);
                break;
            case "5x5":
                board = new TwentyFortyEightBoard(5);
                break;
            case "6x6":
                board = new TwentyFortyEightBoard(6);
                break;
            default:
                throw new IllegalStateException("This factory's settings is in an illegal state!");
        }

        board.setMaxUndos(numUndos);
        return board;
    }

    @Override
    public Class getGameFragmentClass() {
        return TwentyFortyEightFragment.class;
    }

    @Override
    public List<String> getGameNames() {
        return Arrays.asList("2048");
    }
}
