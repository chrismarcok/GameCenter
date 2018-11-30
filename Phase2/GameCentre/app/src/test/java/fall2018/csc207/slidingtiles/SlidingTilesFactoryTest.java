package fall2018.csc207.slidingtiles;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import fall2018.csc207.game.GameFactory;

import static org.junit.Assert.fail;


public class SlidingTilesFactoryTest {
    private SlidingTilesFactory stFactory;

    @Before
    public void setupBefore() {
        stFactory = new SlidingTilesFactory();
    }

    @Test
    public void testCorrectFragmentClass() {
        assert stFactory.getGameFragmentClass().equals(SlidingTilesFragment.class);
    }

    @Test
    public void testGameNames() {
        List<String> gameNames = stFactory.getGameNames();
        List<String> expectedNames = Arrays.asList("Sliding Tiles 3x3", "Sliding Tiles 4x4",
                "Sliding Tiles 5x5");
        assert gameNames.equals(expectedNames);
    }

    @Test
    public void testCorrectDefaultSettings() {
        List<GameFactory.Setting> settings = stFactory.getSettings();
        assert settings.size() == 1;

        GameFactory.Setting boardSize = settings.get(0);
        List<String> possibleValues = boardSize.getPossibleValues();
        List<String> expectedPossibleVals = Arrays.asList("3x3", "4x4", "5x5");

        assert boardSize.getName().equals("Sliding Tiles Size");
        assert possibleValues.equals(expectedPossibleVals);

        assert boardSize.getCurrentValue().equals("3x3");
        assert boardSize.getCurrentValueIndex() == 0;
    }

    @Test
    public void testChangeSettings() {
        List<GameFactory.Setting> settings = stFactory.getSettings();
        GameFactory.Setting boardSize = settings.get(0);

        boardSize.setCurrentValue(2);
        assert boardSize.getCurrentValue().equals("5x5");
        assert boardSize.getCurrentValueIndex() == 2;

        boardSize.setCurrentValue(1);
        assert boardSize.getCurrentValue().equals("4x4");
        assert boardSize.getCurrentValueIndex() == 1;

        boardSize.setCurrentValue(0);
        assert boardSize.getCurrentValue().equals("3x3");
        assert boardSize.getCurrentValueIndex() == 0;

        boardSize.setCurrentValue(2);
        boardSize.setCurrentValue(2);
        assert boardSize.getCurrentValue().equals("5x5");
        assert boardSize.getCurrentValueIndex() == 2;
    }

    @Test
    public void testChangeInvalidSettings() {
        List<GameFactory.Setting> settings = stFactory.getSettings();
        GameFactory.Setting boardSize = settings.get(0);
        try{
            boardSize.setCurrentValue(-1);
            fail();
        } catch (IndexOutOfBoundsException ignored){

        }

        try {
            boardSize.setCurrentValue(4);
            fail();
        } catch (IndexOutOfBoundsException ignored){

        }
    }

    @Test
    public void testDefaultIs3x3(){
        assert stFactory.getGameState(1).numTiles() == 3*3;
    }

    @Test
    public void testGet3x3(){
        GameFactory.Setting boardSize = stFactory.getSettings().get(0);
        boardSize.setCurrentValue(0);
        SlidingTilesBoard state = stFactory.getGameState(1);
        assert state.numTiles() == 3*3;
        assert state.getMaxUndos() == 1;

        state = stFactory.getGameState(5);
        assert state.numTiles() == 3*3;
        assert state.getMaxUndos() == 5;

    }

    @Test
    public void testGet4x4(){
        GameFactory.Setting boardSize = stFactory.getSettings().get(0);
        boardSize.setCurrentValue(1);
        SlidingTilesBoard state = stFactory.getGameState(1);
        assert state.numTiles() == 4*4;
        assert state.getMaxUndos() == 1;

        state = stFactory.getGameState(5);
        assert state.numTiles() == 4*4;
        assert state.getMaxUndos() == 5;
    }

    @Test
    public void testGet5x5(){
        GameFactory.Setting boardSize = stFactory.getSettings().get(0);
        boardSize.setCurrentValue(2);
        SlidingTilesBoard state = stFactory.getGameState(1);
        assert state.numTiles() == 5*5;
        assert state.getMaxUndos() == 1;

        state = stFactory.getGameState(5);
        assert state.numTiles() == 5*5;
        assert state.getMaxUndos() == 5;
    }

    @Test
    public void testGetStateGivesDifferentStates(){
        assert stFactory.getGameState(0) != stFactory.getGameState(0);
    }
}
