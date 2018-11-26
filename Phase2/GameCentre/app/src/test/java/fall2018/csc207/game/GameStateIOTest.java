package fall2018.csc207.slidingtiles;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import fall2018.csc207.game.GameFactory;
import fall2018.csc207.game.GameState;
import fall2018.csc207.game.GameStateIO;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;


public class GameStateIOTest {
    private Context context = ApplicationProvider.getApplicationContext();

    @Before
    public void setupBefore() {
        File filesDir = context.getFilesDir();

        // We delete all the files currently in the files folder
        for (File file : filesDir.listFiles()) {
            file.delete();
        }
    }

    @Test
    public void testIsValidUnusedFileName() throws IOException {
        GameStateIO test = new GameStateIO("User", "test game", context.getFilesDir());
        assert test.isValidUnusedFileName("some file name");
        assert test.isValidUnusedFileName("some alphanumeric name 999 ");

        assert !test.isValidUnusedFileName("\t not accepted string");
        assert !test.isValidUnusedFileName("");

        test.saveState(getSlidingTilesState(), "some file name");
        assert !test.isValidUnusedFileName("some file name");
    }

    /**
     * Returns a random sliding tiles state.
     *
     * @return A random sliding tiles state.
     */
    private GameState getSlidingTilesState() {
        SlidingTilesFactory factory = new SlidingTilesFactory();
        return factory.getGameState(1);
    }
}
