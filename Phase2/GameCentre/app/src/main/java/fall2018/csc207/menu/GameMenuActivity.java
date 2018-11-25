package fall2018.csc207.menu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc207.game.GameFactory;
import fall2018.csc207.game.GameMainActivity;
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
    /**
     * The dialog box used to give instructions the game.
     */
    private Dialog infoDialog;

    /**
     * Called when we create a GameMenuActivity.
     * @param savedInstanceState The activity's previously saved state, contained in a bundle.
     */
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

        TextView newName = findViewById(R.id.game_name);
        newName.setText(gameName);
        newGame();
        loadGame();
        initScoreboard();
        infoDialog = new Dialog(this);
    }


    /**
     * Instantiates the scoreboard button
     */
    private void initScoreboard() {
        CardView scoreBoard = findViewById(R.id.score_board);
        scoreBoard.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent scoreboardIntent = new Intent(GameMenuActivity.this, ScoreboardActivity.class);
                ArrayList<String> gameNames = new ArrayList<>(gameFactory.getGameNames());
                scoreboardIntent.putStringArrayListExtra(ScoreboardActivity.GAME_NAMES, gameNames);
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
        infoDialog.setContentView(R.layout.dialogue_slidingtile_instructions);
        TextView infoTxtClose;
        infoTxtClose = infoDialog.findViewById(R.id.infoTxtCloseInstructions);
        infoTxtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

    /**
     * Instantiates the load game button.
     */
    private void loadGame() {
        CardView loadGame = findViewById(R.id.load_game);
        loadGame.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                final List<String> files = new ArrayList<>();
                final File userRootFolder = new File(getFilesDir(), username);
                for (String gameName : gameFactory.getGameNames()) {
                    File gameSaveFiles = new File(userRootFolder, gameName);
                    gameSaveFiles.mkdirs();
                    for (File saveFile : gameSaveFiles.listFiles()) {
                        files.add(gameName + "/" + saveFile.getName());
                    }
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(GameMenuActivity.this);
                builder.setTitle("Choose save file")
                        .setItems(files.toArray(new String[0]), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                loadFromFile(new File(userRootFolder, files.get(which)));
                            }
                        })
                        .show();
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
     * @param file The file to load a save from.
     */
    private void loadFromFile(File file) {
        Log.v("Loading file", file.toString());
        Serializable gameState;

        // We load the specified file into gameState.
        try {
            InputStream inputStream = new FileInputStream(file);
            ObjectInputStream input = new ObjectInputStream(inputStream);
            gameState = (Serializable) input.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            Toast.makeText(this, "Error loading save file.",
                    Toast.LENGTH_LONG).show();
            Log.e("login activity", "File not found: " + e.toString());
            return;
        }
        Intent main = new Intent(GameMenuActivity.this, GameMainActivity.class);
        main.putExtra(GameMainActivity.FRAGMENT_CLASS, gameFactory.getGameFragmentClass());
        main.putExtra(GameMainActivity.GAME_STATE, gameState);
        main.putExtra(GameMainActivity.USERNAME, username);
        main.putExtra(GameMainActivity.FILE_NAME, file.getName());
        startActivity(main);
    }
}
