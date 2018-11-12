package fall2018.csc207.menu;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import fall2018.csc207.database.ScoreboardDBHandler;
import fall2018.csc207.game.GameMainActivity;
import fall2018.csc207.game.GameState;
import fall2018.csc207.menu.scoreboard.ScoreboardActivity;
import fall2018.csc207.slidingtiles.R;

//TODO: Implement some type of saving/loading functionality

/**
 * The Initial activity for instantiating Games
 */
public class GameMenuActivity extends AppCompatActivity {

    /**
     * The user that's playing
     */
    private String username;

    /**
     * Name of game that's being played
     */
    private String game;
    private String fileName;
    Dialog tilesInfoDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        //Get name of user and the game.
        username = getIntent().getStringExtra(GameMainActivity.USERNAME);
        game = getIntent().getStringExtra("game");

        //The filename will be unique for the player and game.
        fileName = username + game + ".bin";
        fileName = fileName.replaceAll("\\s", "");
        TextView newName = findViewById(R.id.game_name);
        newName.setText(game);
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
                Settings setting = GameCentreActivity.getSettings(game);
                ArrayList<String> settingsList = new ArrayList<>(setting.getSettings().keySet());
                scoreboardIntent.putStringArrayListExtra("GAME_NAMES", settingsList);
                startActivity(scoreboardIntent);
            }
        });
    }

    public void showSlidingTileInfo(View v){
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
     * Instantiates the load game button
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
     * Instantiates the new game button
     */
    private void newGame() {
        CardView newGame = findViewById(R.id.new_game);
        newGame.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button_clicked", "new Game");
                Intent intent = new Intent(GameMenuActivity.this, SettingsActivity.class);
                intent.putExtra(GameMainActivity.USERNAME, username);
                intent.putExtra("game", game);
                intent.putExtra("file", fileName);
                startActivity(intent);
            }
        });
    }

    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                GameState gameState = (GameState) input.readObject();
                inputStream.close();

                Intent tmp = new Intent(GameMenuActivity.this, GameMainActivity.class);
                tmp.putExtra(GameMainActivity.FRAGMENT_CLASS, GameCentreActivity.gameLibrary.get(game));
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
