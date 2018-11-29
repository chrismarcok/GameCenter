package fall2018.csc207.minesweeper;

import java.util.Arrays;
import java.util.List;

import fall2018.csc207.game.GameFactory;
import fall2018.csc207.game.GameState;

public class MinesweeperFactory extends GameFactory {
    /**
     * The settings for the Minesweeper game.
     */
    public MinesweeperFactory() {
        addToSettings(new Setting(
                "Board Size",
                Arrays.asList("5x5", "8x8", "13x13", "20x20"),
                "5x5"
        ));
    }

    /**
     * Return a gamestate
     * @param numUndos The number of undos to allow.
     * @return a new GameState of type MinesweeperBoard
     */
    @Override
    public GameState getGameState(int numUndos) {
        MinesweeperBoard minesweeperBoard;
        switch (settings.get(0).getCurrentValue()) { //There should only be 1 thing in settings
            case "5x5":
                minesweeperBoard = new MinesweeperBoard(5, .2);
                break;
            case "8x8":
                minesweeperBoard = new MinesweeperBoard(8, .2);
                break;
            case "13x13":
                minesweeperBoard = new MinesweeperBoard(13, .2);
                break;
            case "20x20":
                minesweeperBoard = new MinesweeperBoard(20, .4);
                break;
            default:
                throw new IllegalStateException("This factory's settings is in an illegal state!");
        }
        minesweeperBoard.setMaxUndos(numUndos);
        return minesweeperBoard;
    }

    @Override
    public Class getGameFragmentClass() {
        return MinesweeperFragment.class;
    }

    @Override
    public List<String> getGameNames() {
        return Arrays.asList("Minesweeper 5x5", "Minesweeper 8x8","Minesweeper 13x13",
                "Minesweeper 20x20");
    }
}
