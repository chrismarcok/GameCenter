package fall2018.csc207.menu;

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

        int userScore = 0;
        username = getIntent().getStringExtra(GameMainActivity.USERNAME);

        //Create the LayoutManager
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.gameCentreRecyclerView);
        gameCardAdapter = new GameCardAdapter(gameCardItemList, username);
        recyclerView.setAdapter(gameCardAdapter);
        recyclerView.setLayoutManager(mLayoutManager);

        //Get a reference to the Database, load the highscore.
        ScoreboardDBHandler db = new ScoreboardDBHandler(this, null);
        ArrayList<ScoreboardEntry> score = db.fetchTileScores(username);

        username = getIntent().getStringExtra(USERNAME);
        //Change the highscore if there is one.
        if (score.size() == 1){
            userScore = score.get(0).getScore();
        }

        createGameCards(userScore);
    }

    /*
     * Create the GameCardItems.
     *
     * @param userScore the highscore of the GameCardItem.
     */
    private void createGameCards(int userScore) {
        //Add Games to library
        gameLibrary.put("Sliding Tiles", SlidingTilesFragment.class);
        settingsLibrary.put("Sliding Tiles", TileSettings.class);

        // For each game in the game library make a new GameCardItem
        for (HashMap.Entry<String, Class> entry : gameLibrary.entrySet()) {
            GameCardItem newGame = new GameCardItem(entry.getKey(), userScore, getDrawable(R.drawable.sliding_tiles));
            gameCardItemList.add(newGame);
        }

        gameCardAdapter.notifyDataSetChanged();
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
