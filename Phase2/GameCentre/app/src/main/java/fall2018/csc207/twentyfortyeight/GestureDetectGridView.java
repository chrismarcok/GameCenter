package fall2018.csc207.twentyfortyeight;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.Toast;

public class GestureDetectGridView extends GridView {

    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private GestureDetector gDetector;

    private boolean mFlingConfirmed;
    private float mTouchX;
    private float mTouchY;
    private BoardController boardManager;

    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr,
                                 int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * Swipe Functions:
     * https://stackoverflow.com/questions/4139288/
     * android-how-to-handle-right-to-left-swipe-gestures
     * Credits go to Mirek Rusin
     * @param context The context of this GridView
     */
    private void init(final Context context) {
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {

                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) >
                                SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                boardManager.moveRight();
                                Toast.makeText(context, "Right",Toast.LENGTH_SHORT).show();
                            } else {
                                boardManager.moveLeft();
                                Toast.makeText(context, "Left",Toast.LENGTH_SHORT).show();
                            }
                            result = true;
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) >
                            SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            boardManager.moveDown();
                            Toast.makeText(context, "Down",Toast.LENGTH_SHORT).show();
                        } else {
                            boardManager.moveUp();
                            Toast.makeText(context, "Up",Toast.LENGTH_SHORT).show();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            performClick();
        }
        return gDetector.onTouchEvent(ev);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void setBoardManager(BoardController boardManager) {
        this.boardManager = boardManager;
    }
}