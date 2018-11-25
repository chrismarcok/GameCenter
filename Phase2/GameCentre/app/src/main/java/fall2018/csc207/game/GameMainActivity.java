package fall2018.csc207.game;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc207.database.ScoreboardDBHandler;
import fall2018.csc207.menu.scoreboard.ScoreboardEntry;
import fall2018.csc207.slidingtiles.R;

/**
 * The Activity that holds a game fragment. This gives us a toolbar, undo etc. that will be
 * consistent between all games.
 */
public class GameMainActivity extends AppCompatActivity implements Observer {

    /**
     * A Class<GameFragment> that the game will use as its main view.
     */
    public static final String FRAGMENT_CLASS = "FRAGMENT";
    /**
     * The GameState associated with the game.
     */
    public static final String GAME_STATE = "STATE";
    /**
     * The username of the current user playing the game.
     */
    public static final String USERNAME = "USERNAME";
    /**
     * The name of the file that we're using to save the game.
     */
    public static final String FILE_NAME = "FILE";

    /**
     * The GameState.
     */
    private GameState state;

    /**
     * Display of the current score.
     */
    private TextView currentScore;

    /**
     * The undo button.
     */
    private Button undo;

    /**
     * The user's username, as stored in the database.
     */
    private String username;

    /**
     * The name of the file used for saving/loading.
     */
    private String fileName;

    /**
     * Called by Android when the Activity is being made.
     *
     * @param savedInstanceState A previously saved instance of the Activity, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        Intent intent = getIntent();
        TextView title = findViewById(R.id.gameTitle);

        state = (GameState) intent.getSerializableExtra(GAME_STATE);
        state.addObserver(this);

        title.setText(state.getGameName());

        username = intent.getStringExtra(GameMainActivity.USERNAME);
        fileName = intent.getStringExtra(FILE_NAME);

        setupFragment();
        setupButtons();
        setupScores();
    }

    /**
     * Creates the fragment with the given FRAGMENT_CLASS and GAME_STATE.
     */
    private void setupFragment() {
        Intent intent = getIntent();
        Class fragmentClass = (Class) intent.getSerializableExtra(FRAGMENT_CLASS);
        GameFragment gameFragment;
        try {
            gameFragment = (GameFragment) fragmentClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(GameFragment.GAME_STATE, intent.getSerializableExtra(GAME_STATE));
        gameFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, gameFragment)
                .commitNow();
    }

    /**
     * Setups the 3 buttons found in this Activity: Back, Save and Undo.
     */
    private void setupButtons() {
        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        undo = findViewById(R.id.undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state.canUndo()) {
                    state.undo();
                } else
                    Toast.makeText(getApplicationContext(), "Cannot Undo!", Toast.LENGTH_SHORT).show();
            }
        });

        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGame(fileName);
            }
        });
    }

    /**
     * Automatically save every 5 rounds.
     *
     * @param file The name of the file to be saved.
     */
    public void autoSave(String file) {
        if (state.getScore() % 5 == 0) {
            saveGame(file);
        }
    }

    //TODO: Encapsulate saving and loading into a class

    /**
     * Write the GameState to a file.
     *
     * @param fileName The name of the file.
     */
    public void saveGame(String fileName) {
        File saveDir = new File(
                getFilesDir(), String.format("%s/%s", username, state.getGameName()));
        try {
            // Makes the directories, if they don't exist. If they do then this does nothing.
            saveDir.mkdirs();
            File savePath = new File(saveDir, fileName);
            FileOutputStream fos = new FileOutputStream(savePath);
            ObjectOutput oos = new ObjectOutputStream(fos);

            oos.writeObject(state);
            oos.close();
            Toast.makeText(this, "Game saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "An error occurred while saving.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Setups the displayed scores.
     */
    private void setupScores() {
        currentScore = findViewById(R.id.currScore);
        TextView maxScore = findViewById(R.id.maxScore);

        // This lets us instantly detect a solved game (for a lucky player), and updates the score.
        update(state, null);

        int userScore = 0;
        ScoreboardDBHandler db = new ScoreboardDBHandler(this, null);
        Iterable<ScoreboardEntry> userHighScoreList = db.fetchUserHighScores(username);
        for (ScoreboardEntry entry : userHighScoreList) {
            if (entry.getGame().equals(state.getGameName())) {
                userScore = entry.getScore();
                break;
            }
        }
        maxScore.setText(String.valueOf(userScore));
    }

    /**
     * Called when a observed object invokes an update.
     *
     * @param o   The observed object.
     * @param arg The sent argument.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GameState) {
            GameState state = (GameState) o;
            autoSave(fileName);

            currentScore.setText(String.valueOf(state.getScore()));
            setUndoState(state.canUndo());
            if (state.isOver())
                endGame();
        }
    }

    /**
     * Enables or disables the undo button.
     *
     * @param state If true, the button is enabled. If false, the button is disabled.
     */
    private void setUndoState(boolean state) {
        undo.setEnabled(state);
        if (state)
            undo.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        else
            undo.setTextColor(ContextCompat.getColor(this, R.color.lightgray));
    }

    /**
     * End the game. Add the score to the Scoreboard.
     */
    private void endGame() {
        ScoreboardDBHandler db = new ScoreboardDBHandler(this, null);
        String game = state.getGameName();
        db.addEntry(username, state.getScore(), game);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("You win!")
                .setMessage("Your final score is: " + state.getScore())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        alertBuilder.create().show();
    }
}
