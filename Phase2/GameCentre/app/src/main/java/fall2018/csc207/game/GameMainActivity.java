package fall2018.csc207.game;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
     * Keys used in Intents.
     */
    public static final String FRAGMENT_CLASS = "FRAGMENT";
    public static final String GAME_STATE = "STATE";
    public static final String USERNAME = "USERNAME";

    /*
     * The GameState.
     */
    private GameState state;
    private TextView currentScore;
    /*
     * The user's username, as stored in the database.
     */
    private String username;
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

        state = (GameState) intent.getSerializableExtra(GAME_STATE);
        state.addObserver(this);

        TextView title = findViewById(R.id.gameTitle);
        title.setText(state.getGameName());

        if (getIntent().getExtras() != null)
            username = getIntent().getExtras().getString(GameMainActivity.USERNAME);

        if (getIntent().getExtras().getString("file") != null){
            fileName = getIntent().getExtras().getString("file");
        }
        else{ fileName = "temp.bin";}
        SetupFragment();
        SetupButtons();
        SetupScores();
    }

    /**
     * Creates the fragment with the given FRAGMENT_CLASS and GAME_STATE.
     */
    private void SetupFragment() {
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
    private void SetupButtons() {
        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button undo = findViewById(R.id.undo);
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

    /*
     * Automatically save every 5 rounds.
     *
     * @param file The name of the file to be saved.
     */
    public void autoSave(String file){
        if (state.getScore() % 5 == 0){
            saveGame(file);
        }
    }

    /*
     * Write the gamestate to a file.
     *
     * @param fileName The name of the file.
     */
    public void saveGame(String fileName){
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(state);
            oos.close();
            Toast.makeText(this, "Game saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setups the displayed scores.
     */
    private void SetupScores() {
        currentScore = findViewById(R.id.currScore);
        TextView maxScore = findViewById(R.id.maxScore);

        // This lets us instantly detect a solved game (for a lucky player), and updates the score.
        update(state, null);

        int userScore = 0;
        ScoreboardDBHandler db = new ScoreboardDBHandler(this, null);
        ArrayList<ScoreboardEntry> score = db.fetchTileScores(username);
        if (score.size() == 1){
            userScore = score.get(0).getScore();
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
        autoSave(fileName);
        currentScore.setText(String.valueOf(((GameState) o).getScore()));
        if (state.isOver())
            endGame();
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
