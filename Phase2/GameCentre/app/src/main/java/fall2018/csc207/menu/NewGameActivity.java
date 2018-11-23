package fall2018.csc207.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import fall2018.csc207.game.GameFactory;
import fall2018.csc207.game.GameMainActivity;
import fall2018.csc207.game.GameState;
import fall2018.csc207.slidingtiles.R;


/**
 * The menu for new games.
 */
public class NewGameActivity extends AppCompatActivity {

    public static final String GAME_NAME = "NAME";
    public static final String USERNAME = "USERNAME";

    /**
     * The factory we will use for this game.
     */
    private GameFactory gameFactory;
    /**
     * The user that opened this activity.
     */
    private String username;
    /**
     * The name of the game.
     */
    private String gameName;
    /**
     * Determines whether or not unlimited undos are toggled on or not.
     */
    private Switch infUndoSwitch;
    /**
     * The Seekbar for the user choosing the value of undos.
     */
    private SeekBar undoSeekbar;

    /**
     * Called when we create a NewGameActivity.
     *
     * @param savedInstanceState The activity's previously saved state, contained in a bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Intent intent = getIntent();

        gameName = intent.getStringExtra(GAME_NAME);
        username = intent.getStringExtra(USERNAME);
        try {
            Class factoryClass = GameCentreActivity.getFactoryClass(gameName);
            gameFactory = (GameFactory) factoryClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        setupUndoOptions();
        setupSettings();
        Button startButton = findViewById(R.id.startGame);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });
    }

    /**
     * Create the seek bar and update it as its value changes.
     */
    public void setupUndoOptions() {
        undoSeekbar = findViewById(R.id.seekBar);
        infUndoSwitch = findViewById(R.id.infiniteUndo);

        setSeekbarState(!infUndoSwitch.isChecked());
        infUndoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                setSeekbarState(!infUndoSwitch.isChecked());
            }
        });

        undoSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * Called when we make a change to the seekBar's value.
             * @param seekBar The seekbar whose progress has changed.
             * @param progress The value of the SeekBar.
             * @param fromUser True if the change was initiated from the user.
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView text = findViewById(R.id.progress);
                text.setText(String.valueOf(progress));
            }

            /**
             * Called when we touch the seekbar.
             * @param seekBar The seekbar whose progress has been touched.
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /**
             * Called when we stop touching the seekbar.
             * @param seekBar The seekbar who is no longer being touched.
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    /**
     * Enable or disable the seekbar
     *
     * @param state The state we change the seekbar to. Enabled = true.
     */
    public void setSeekbarState(boolean state) {
        final SeekBar seekbar = findViewById(R.id.seekBar);
        seekbar.setEnabled(state);
    }

    /**
     * Initializes the buttons and displays them on activity_new_game.xml
     */
    private void setupSettings() {
        LinearLayout screenView = findViewById(R.id.settings);
        for (final GameFactory.Setting setting : gameFactory.getSettings()) {
            Spinner dropdown = new Spinner(this);
            ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    setting.getPossibleValues());

            dropdown.setAdapter(dropdownAdapter);
            dropdown.setSelection(setting.getCurrentValueIndex());


            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 20, 10, 0);

            screenView.addView(dropdown, lp);

            dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                /**
                 * Called when a spinner item is selected.
                 * @param parentView The view where the selection happened
                 * @param selectedItemView The view within the adapter that was selected
                 * @param position The position of the selected view in the adapter
                 * @param id The row id of the selected item
                 */
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    setting.setCurrentValue(position);
                }

                /**
                 * Called when the selection disappears from view.
                 * @param adapterView The parent that has no selected item.
                 */
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
    }

    /**
     * Starts the game with a state from gameFactory.
     */
    private void startGame() {
        Log.v("Start Game", gameName + " by user " + username);
        GameState state = gameFactory.getGameState(undoSeekbar.getProgress());
        Intent tmp = new Intent(NewGameActivity.this, GameMainActivity.class);
        tmp.putExtra(GameMainActivity.FRAGMENT_CLASS, gameFactory.getGameFragmentClass());
        tmp.putExtra(GameMainActivity.GAME_STATE, state);
        tmp.putExtra(GameMainActivity.USERNAME, username);

        //TODO: Add filename selector
        tmp.putExtra("file", "temp.tmp");
        startActivity(tmp);
    }
}
