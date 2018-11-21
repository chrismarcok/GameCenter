package fall2018.csc207.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fall2018.csc207.slidingtiles.R;

/**
 * The Splash Screen Activity.
 */
public class SplashScreenActivity extends AppCompatActivity {

    /**
     * The number of milliseconds the splash screen lingers. 1000ms = 1s.
     */
    private final int SLEEP_TIMER = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Create a logoLauncher, and run it.
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }

    /**
     * A LogoLauncher.
     */
    private class LogoLauncher extends Thread {

        /**
         * Sleep for SLEEP_TIMER ms and then launch the next activity.
         */
        public void run() {
            try {
                sleep(SLEEP_TIMER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent startIntent;
            startIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(startIntent);

            //destroy activity so back button doesnt work,
            //dont want people to be able to return to this activity!
            SplashScreenActivity.this.finish();
        }
    }
}
