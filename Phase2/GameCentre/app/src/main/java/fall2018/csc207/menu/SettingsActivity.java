package fall2018.csc207.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import fall2018.csc207.game.GameMainActivity;
import fall2018.csc207.game.GameState;
import fall2018.csc207.slidingtiles.R;


/*
 * The Menu for the Settings
 */
public class SettingsActivity extends AppCompatActivity {
    /*
     * The settings of of this game.
     */
    private Settings setting;

    /*
     * The user that opened this activity.
     */
    private String username;
    private String fileName;
    /**
     * Determines whether or not unlimited undos are toggled on or not.
     */
    private Switch infUndoSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        String gameName = "";
        if (getIntent().getExtras() != null)
            gameName = getIntent().getExtras().getString("game");
        if (getIntent().getExtras() != null)
            username = getIntent().getExtras().getString(GameMainActivity.USERNAME);
        setting = GameCentreActivity.getSettings(gameName);
        fileName = getIntent().getExtras().getString("file");
        setupUndoOptions();
        instantiateButtons(gameName);
    }

    /**
     * Create the seek bar and update it as its value changes.
     */
    public void setupUndoOptions() {
        SeekBar undoSeekbar = findViewById(R.id.seekBar);
        infUndoSwitch = findViewById(R.id.infiniteUndo);
        undoSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView textProgress = findViewById(R.id.progress);
                textProgress.setText(String.valueOf(i));
                setting.setUndos(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        setSeekbarState(!infUndoSwitch.isChecked());
        infUndoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSeekbarState(!infUndoSwitch.isChecked());
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
     * Initializes the buttons and displays them on activity_settings.xml
     *
     * @param gameName The name of the game to create buttons for.
     */
    private void instantiateButtons(final String gameName) {
        for (final String settingName : setting.getSettings().keySet()) {
            Button button = new Button(this);
            button.setText(settingName);
            LinearLayout screenView = findViewById(R.id.settings);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 20, 10, 0);
            screenView.addView(button, lp);

            button.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent tmp = new Intent(SettingsActivity.this, GameMainActivity.class);
                    GameState gotState = setting.getSettings().get(settingName);
                    gotState.setMaxUndos(infUndoSwitch.isChecked() ? -1 : setting.getUndos());

                    tmp.putExtra(GameMainActivity.FRAGMENT_CLASS, GameCentreActivity.gameLibrary.get(gameName));
                    tmp.putExtra(GameMainActivity.GAME_STATE, gotState);
                    tmp.putExtra(GameMainActivity.USERNAME, username);
                    tmp.putExtra("file",fileName);
                    startActivity(tmp);

                    SettingsActivity.this.finish(); // Prevents player from returning to this screen.
                }
            });
        }
    }
}
