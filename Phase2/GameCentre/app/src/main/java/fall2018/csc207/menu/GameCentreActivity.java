package fall2018.csc207.menu;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fall2018.csc207.database.ScoreboardDBHandler;
import fall2018.csc207.game.GameMainActivity;
import fall2018.csc207.menu.gameCard.GameCardAdapter;
import fall2018.csc207.menu.gameCard.GameCardItem;
import fall2018.csc207.menu.scoreboard.ScoreboardEntry;
import fall2018.csc207.slidingtiles.SlidingTilesFragment;
import fall2018.csc207.slidingtiles.R;
import fall2018.csc207.slidingtiles.TileSettings;
import fall2018.csc207.sudoku.SudokuFragment;
import fall2018.csc207.sudoku.SudokuSettings;
import fall2018.csc207.twentyfortyeight.TwentyFortyEightFragment;
import fall2018.csc207.twentyfortyeight.TwentyFortyEightSettings;

/**
 * The Game Centre Activity class
 */
public class GameCentreActivity extends AppCompatActivity {
    public static final String USERNAME = "USERNAME";
    /*
     * A map from the name of the game to the game's class
     */
    public static final HashMap<String, Class> gameLibrary = new HashMap<>();

    /*
     * A map from the name of the game to the game's settings
     */
    public static final HashMap<String, Class> settingsLibrary = new HashMap<>();

    /*
     * The user's username
     */
    public String username;
    /*
     * Game Card's adapter that will notify the recyclerview if any item is added
     */
    protected GameCardAdapter gameCardAdapter;
    /*
     * List of all the games in a GameCardItem format
     */
    private List<GameCardItem> gameCardItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_centre);

        username = getIntent().getStringExtra(USERNAME);
        //Create the LayoutManager
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.gameCentreRecyclerView);
        gameCardAdapter = new GameCardAdapter(gameCardItemList, username);
        recyclerView.setAdapter(gameCardAdapter);
        recyclerView.setLayoutManager(mLayoutManager);

        createGameCards();
    }

    /*
     * Create the GameCardItems.
     *
     * @param userScore the highscore of the GameCardItem.
     */
    private void createGameCards() {
        addGamesToLibrary();
        HashMap<String, Drawable> pictureMap = getCardDrawables();
        HashMap<String, Integer> userHighScores = new HashMap<>();

        //Set the default highscores to 0.
        for (HashMap.Entry<String, Class> entry : gameLibrary.entrySet()){
            userHighScores.put(entry.getKey(), 0);
        }

        //Get a reference to the Database, load the highscores.
        ScoreboardDBHandler db = new ScoreboardDBHandler(this, null);
        ArrayList<ScoreboardEntry> userHighScoreList = db.fetchUserHighScores(username);

        //Fill the highscores of the different games
        for (ScoreboardEntry entry : userHighScoreList){
            // If the name is Sliding Tiles 3x3, 4z4 etc change it to just SlidingTiles.
            if (entry.getGame().contains("Sliding Tiles"))
                entry.setGame("Sliding Tiles");
            userHighScores.put(entry.getGame(), entry.getScore());
        }

        // For each game in the game library make a new GameCardItem
        for (HashMap.Entry<String, Class> entry : gameLibrary.entrySet()) {
            String gameName = entry.getKey();
            GameCardItem newGame = new GameCardItem(gameName, userHighScores.get(gameName), pictureMap.get(gameName));
            gameCardItemList.add(newGame);
        }

        gameCardAdapter.notifyDataSetChanged();


    }

    /**
     * Get a hashmap of the game's names and their corresponding pictures.
     * @return The hashmap of the game's names and their corresponding pictures.
     */
    private HashMap<String, Drawable> getCardDrawables() {
        HashMap<String, Drawable> pictureMap = new HashMap<>();
        Drawable slidingTiles = getDrawable(R.drawable.sliding_tiles);
        Drawable twentyFortyEight = getDrawable(R.drawable.twentyfortyeight);
        Drawable sudoku = getDrawable(R.drawable.sudoku);
        pictureMap.put("Sliding Tiles", slidingTiles);
        pictureMap.put("2048", twentyFortyEight);
        pictureMap.put("Sudoku", sudoku);
        return pictureMap;
    }

    /**
     * Adds the games to the game library and setting library.
     */
    private void addGamesToLibrary() {
        gameLibrary.put("Sliding Tiles", SlidingTilesFragment.class);
        settingsLibrary.put("Sliding Tiles", TileSettings.class);
        gameLibrary.put("Sudoku", SudokuFragment.class);
        settingsLibrary.put("Sudoku", SudokuSettings.class);
        gameLibrary.put("2048", TwentyFortyEightFragment.class);
        settingsLibrary.put("2048", TwentyFortyEightSettings.class);
    }

    /**
     *
     * @param gameName The name of the game.
     * @return Returns an instantiated object of Setting
     */
    public static Settings getSettings(String gameName) {
        Class b = GameCentreActivity.settingsLibrary.get(gameName);
        Object setting = new Object();
        try {
            setting = b.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return ((Settings) setting);
    }


}
