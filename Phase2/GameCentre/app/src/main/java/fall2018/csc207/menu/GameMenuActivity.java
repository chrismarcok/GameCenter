package fall2018.csc207.menu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import fall2018.csc207.game.GameFactory;
import fall2018.csc207.game.GameMainActivity;
import fall2018.csc207.game.GameState;
import fall2018.csc207.menu.scoreboard.ScoreboardActivity;
import fall2018.csc207.slidingtiles.R;

/**
 * The Initial activity for instantiating Games
 */
public class GameMenuActivity extends AppCompatActivity {

    /**
     * The user that's currently playing.
     */
    private String username;
    /**
     * The GameFactory for this game.
     */
    private GameFactory gameFactory;
    /**
     * The name of the game that we're playing. We should be able to use this to retrieve the game factory.
     */
    private String gameName;
    //TODO: Probably remove this?
    private String fileName;
    private Dialog tilesInfoDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        Intent intent = getIntent();

        //Get name of user and the game.
        username = intent.getStringExtra(GameMainActivity.USERNAME);
        gameName = intent.getStringExtra("game");
        try {
            gameFactory = (GameFactory) GameCentreActivity.getFactoryClass(gameName).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        //The filename will be unique for the player and game.
        // TODO: Get filename from dialog box
        fileName = username + "temp" + ".bin";
        fileName = fileName.replaceAll("\\s", "");
        TextView newName = findViewById(R.id.game_name);
        newName.setText(gameName);
        newGame();
        loadGame();
        initScoreboard();
        tilesInfoDialog = new Dialog(this);
    }


    /**
     * Instantiates the scoreboard button
     */
    private void initScoreboard() {
        CardView scoreBoard = findViewById(R.id.score_board);


        scoreBoard.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button_clicked", "score Board");

                Intent scoreboardIntent = new Intent(GameMenuActivity.this, ScoreboardActivity.class);
                ArrayList<String> gameNames = new ArrayList<>(gameFactory.getGameNames());
                scoreboardIntent.putStringArrayListExtra("GAME_NAMES", gameNames);
                startActivity(scoreboardIntent);
            }
        });
    }

    /**
     * Shows information on SlidingTiles.
     *
     * @param v The given view from the onClick method.
     */
    public void showSlidingTileInfo(View v) {
        //TODO: Set this dynamically?
        tilesInfoDialog.setContentView(R.layout.dialogue_slidingtile_instructions);
        TextView infoTxtClose;
        infoTxtClose = tilesInfoDialog.findViewById(R.id.infoTxtCloseInstructions);
        infoTxtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tilesInfoDialog.dismiss();
            }
        });
        tilesInfoDialog.show();
    }

    /**
     * Instantiates the load game button.
     */
    private void loadGame() {
        CardView loadGame = findViewById(R.id.load_game);
        loadGame.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button_clicked", "load Game");
                loadFromFile(fileName);
            }
        });
    }

    /**
     * Instantiates the new game button.
     */
    private void newGame() {
        CardView newGame = findViewById(R.id.new_game);
        newGame.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button_clicked", "new Game");
                Intent intent = new Intent(GameMenuActivity.this, NewGameActivity.class);
                intent.putExtra(NewGameActivity.USERNAME, username);
                intent.putExtra(NewGameActivity.GAME_NAME, gameName);
                startActivity(intent);
            }
        });
    }

    /**
     * Loads a game from a file.
     * @param fileName The name of the file to load it from.
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                GameState gameState = (GameState) input.readObject();
                inputStream.close();

                Intent tmp = new Intent(GameMenuActivity.this, GameMainActivity.class);
                tmp.putExtra(GameMainActivity.FRAGMENT_CLASS, gameFactory.getGameFragmentClass());
                tmp.putExtra(GameMainActivity.GAME_STATE, gameState);
                tmp.putExtra(GameMainActivity.USERNAME, username);
                tmp.putExtra("file", fileName);
                startActivity(tmp);
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "No Previously Saved Games",
                    Toast.LENGTH_LONG).show();
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }
}
