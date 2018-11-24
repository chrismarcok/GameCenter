package fall2018.csc207.minesweeper;


import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import fall2018.csc207.game.GameFragment;
import fall2018.csc207.slidingtiles.R;

public class MinesweeperFragment extends GameFragment<Board, BoardManager> {

    /**
     * The buttons (tiles) that can be clicked on to be moved.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Grid View and calculated column height and width based on device size.
     */
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;
    private int dimensions = 0;
    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * Called by Android when we want to create this view.
     *
     * @param inflater           Converts layout XML into a View.
     * @param container          The container for the View.
     * @param savedInstanceState A previously saved instance, if available.
     * @return The created View.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_minesweeper, container, false);
        createTileButtons(thisView.getContext());

        // Add View to activity
        gridView = thisView.findViewById(R.id.grid);
        gridView.setNumColumns(dimensions);
        gridView.setBoardManager(gameManager);


        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        MinesweeperFragment.columnWidth = displayWidth / dimensions;
                        MinesweeperFragment.columnHeight = displayHeight / dimensions;

                        display();
                    }
                });

        return thisView;
    }

    /**
     * Called by Android when this fragment is created. This is called before onCreateView,
     * so make sure no graphic initialization is done here!
     *
     * @param savedInstanceState A previously saved instance of this activity, if available.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Pulls the type of BoardManager to be initialized
        gameManager = new BoardManager(this.state);
        dimensions = state.getDimensions();
        state = gameManager.getGameState();
        state.addObserver(this);
        starTimer();
    }

    /**
     * Starts the timer for the score
     */
    public void starTimer(){
        Timer T = new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                state.decrementScore();
            }
        }, 0, 1000);
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = gameManager.getGameState();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                Button tmp = new Button(context);
                if (board.getTile(row,col).isFlagged()){
                    tmp.setBackgroundResource(R.drawable.flag);
                }
                else if (!board.getTile(row, col).getrevealed()) {
                    tmp.setBackgroundResource(R.drawable.btile);
                }
                else {
                    tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                }
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = gameManager.getGameState();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / dimensions;
            int col = nextPos % dimensions;
            if (board.getTile(row,col).isFlagged()){
                b.setBackgroundResource(R.drawable.flag);
            }
            else if (!board.getTile(row, col).getrevealed()) {
                b.setBackgroundResource(R.drawable.btile);
            }
            else {
                b.setBackgroundResource(board.getTile(row, col).getBackground());
            }
            nextPos++;
        }
    }

    /**
     * Called whenever an observed object is updated.
     *
     * @param o   The updated object.
     * @param arg An argument sent by the object.
     */
    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}