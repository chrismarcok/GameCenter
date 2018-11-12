package fall2018.csc207.menu;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import fall2018.csc207.database.UserDBHandler;
import fall2018.csc207.game.GameMainActivity;
import fall2018.csc207.slidingtiles.R;

/**
 * The Login Activity.
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * Dialog Boxes for Sign-up
     */
    Dialog signupDialog;
    Dialog infoDialog;

    /**
     * Get a reference to the username Database.
     */
    final UserDBHandler userDB = new UserDBHandler(this, UserDBHandler.DATABASE_NAME,
            null, UserDBHandler.DATABASE_VERSION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
         * The EditTexts and Buttons that are within the activity.
         */
        final EditText usernameEditText = findViewById(R.id.usernameEditText);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        CardView loginButton = findViewById(R.id.loginButton);
        CardView signupButton = findViewById(R.id.signupButton);
        signupDialog = new Dialog(this);

        /*
         * When Login button is clicked, check to see if the username/password match that which
         * is in the database. If so, login and start the next activity. If not, reject the login.
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(usernameEditText, passwordEditText);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupDialogue();
            }
        });
    }

    /**
     * Attempt to login the user.
     *
     * @param usernameEditText The field which holds the user's username.
     * @param passwordEditText The field which holds the user's password.
     */
    private void loginUser(EditText usernameEditText, EditText passwordEditText) {
        HashMap<String, String> userMap = userDB.fetchDatabaseEntries();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        String mapPass = userMap.get(username);

        //Check if mapPass is null first! dont want to compare null to password.
        if (mapPass != null && mapPass.equals(password)) {
            Toast.makeText(getApplicationContext(), "Welcome, " + username, Toast.LENGTH_SHORT).show();
            Intent changeToStartingActivityIntent = new Intent(LoginActivity.this, GameCentreActivity.class);
            changeToStartingActivityIntent.putExtra(GameMainActivity.USERNAME, username);
            startActivity(changeToStartingActivityIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Could not log in.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Show the Signup Dialog Box
     */
    private void showSignupDialogue(){
        TextView txtClose;
        CardView submitButton;
        final EditText usernameSignupEditText;
        final EditText passwordSignupEditText;

        signupDialog.setContentView(R.layout.dialogue_signup);

        txtClose = signupDialog.findViewById(R.id.txtclose);
        submitButton = signupDialog.findViewById(R.id.submitButton);
        usernameSignupEditText = signupDialog.findViewById(R.id.usernameSignupEditText);
        passwordSignupEditText = signupDialog.findViewById(R.id.passwordSignupEditText);
        infoDialog = new Dialog(this);

        /*
         * When the signup button is clicked, check to see if the username is already in the database.
         * If not, add the username/password combo to the database. If so, reject the username.
         */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(usernameSignupEditText, passwordSignupEditText);
            }
        });

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupDialog.dismiss();
            }
        });
        signupDialog.show();
    }

    /**
     * Attempt to create the user.
     *
     * @param usernameSignupEditText The field which holds the user's username.
     * @param passwordSignupEditText The field which holds the user's password.
     */
    private void createUser(EditText usernameSignupEditText, EditText passwordSignupEditText) {
        HashMap<String, String> userMap = userDB.fetchDatabaseEntries();
        String username = usernameSignupEditText.getText().toString();
        String password = passwordSignupEditText.getText().toString();

        String mapPass = userMap.get(username);

        // Don't want empty username or pass
        if (username.equals("") || password.equals("")){
            Toast.makeText(getApplicationContext(), "One or more fields are empty!", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() <= 5){
            Toast.makeText(getApplicationContext(), "Passwords must be at least 6 characters.", Toast.LENGTH_SHORT).show();
        }
        //mapPass is null if the username is not a valid key in the hashmap!
        else if (mapPass == null){
            userDB.addUser(username, password);
            Toast.makeText(getApplicationContext(), "Created user " + username, Toast.LENGTH_SHORT).show();
            signupDialog.dismiss();
        }
        else{
            Toast.makeText(getApplicationContext(), "username taken.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Show the Signup Info Dialog Box
     *
     * @param v The View.
     */
    public void showInfo(View v){
        infoDialog.setContentView(R.layout.dialogue_info);
        TextView infoTxtClose;
        infoTxtClose = infoDialog.findViewById(R.id.infoTxtClose);
        infoTxtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }
}
