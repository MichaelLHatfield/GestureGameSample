package io.github.iwag.gestureapp;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {


    private ImageView mImageView;
    private ImageView mResultImageView;
    private int mDirection;
    private GestureDetectorCompat mDetector;
    private TextView mTextView;
    private Integer mScore;
    private Timer mTimer;
    private int mSeconds;

    class MyGesture implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        int SWIPE_MIN_DISTANCE = 200;

        int calcDirection(MotionEvent event1, MotionEvent event2) {
            Log.d("gesture", "dir=" + mDirection + " diff=" + (event1.getX() - event2.getX()) + " " + (event1.getY() - event2.getY()));
            if (event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE && Math.abs(event1.getY() - event2.getY()) < 100) {
                return 0;
            } else if (event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE && Math.abs(event1.getY() - event2.getY()) < 100) {
                return 1;
            } else if (event2.getY() - event1.getY() > SWIPE_MIN_DISTANCE && Math.abs(event1.getX() - event2.getX()) < 100) {
                return 2;
            } else if (event1.getY() - event2.getY() > SWIPE_MIN_DISTANCE && Math.abs(event1.getX() - event2.getX()) < 100) {
                return 3;
            }

            return -1;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.i("gesture", "mdir=" + mDirection + "dir=" + calcDirection(event1, event2));
            if (calcDirection(event1, event2) == mDirection) {
                mScore += 100;
                mResultImageView.setImageResource(R.drawable.ok_woman);
            } else {
                mScore -= 50;
                mResultImageView.setImageResource(R.drawable.cross_woman);
            }

            return true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.layoutView);
        mImageView = (ImageView) findViewById(R.id.imageView);
        initIntervalTask();

        mResultImageView = (ImageView) findViewById(R.id.resultImageView);

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        mScore = 0;
        mTextView = (TextView) findViewById(R.id.textView);
        mSeconds = 20;
        mTextView.setText(mSeconds + " seconds");

        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ScoreDialog dialog = ScoreDialog.newInstance(mScore);
                dialog.show(getSupportFragmentManager(), "SCORE");
                mTimer.cancel();
            }
        }, mSeconds * 1000);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    void initIntervalTask() {
        final Random random = new Random();
        mTimer = new Timer();
        TimerTask asyncTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //mTextView.setText("");
                        mImageView.setImageDrawable(null);
                        mResultImageView.setImageResource(R.drawable.woman);
                        mDirection = random.nextInt(4);
                        switch (mDirection) {
                            case 0:
                                mImageView.setImageResource(R.drawable.right_arrow);
                                break;
                            case 1:
                                mImageView.setImageResource(R.drawable.left_arrow);
                                break;
                            case 2:
                                mImageView.setImageResource(R.drawable.down_arrow);
                                break;
                            case 3:
                                mImageView.setImageResource(R.drawable.up_arrow);
                                break;
                        }
                        mSeconds--;

                        if (mTextView != null)
                            mTextView.setText(mSeconds + " seconds");

                    }
                });
            }
        };
        mTimer.schedule(asyncTask, 1000, 1000);
    }

}
