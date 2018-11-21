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
                "Difficulty",
                Arrays.asList("Easy", "Medium", "Hard", "MEGA-HARD!!!!"),
                "Easy"
        ));
    }

    /**
     * Return a gamestate
     * @param numUndos The number of undos to allow.
     * @return a new GameState of type Board
     */
    @Override
    public GameState getGameState(int numUndos) {
        Board board;
        switch (settings.get(0).getCurrentValue()) { //There should only be 1 thing in settings anyways
            case "Easy":
                board = new Board(4, .1);
                break;
            case "Medium":
                board = new Board(8, .2);
                break;
            case "Hard":
                board = new Board(10, .2);
                break;
            case "MEGA-HARD!!!!":
                board = new Board(15, .5);
                break;
            default:
                throw new IllegalStateException("This factory's settings is in an illegal state!");
        }
        board.setMaxUndos(numUndos);
        return board;
    }

    @Override
    public Class getGameFragmentClass() {
        return MinesweeperFragment.class;
    }

    @Override
    public List<String> getGameNames() {
        return Arrays.asList("Minesweeper Easy", "Minesweeper Medium","Minesweeper Hard","Minesweeper Impossible");
    }
}
