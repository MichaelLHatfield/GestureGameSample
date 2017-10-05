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

    // TODO implement GestureDetector

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.layoutView);
        mImageView = (ImageView) findViewById(R.id.imageView);

        mResultImageView = (ImageView) findViewById(R.id.resultImageView);

        // TODO Detector

        initIntervalTask();
    }

    // TODO onTouchEvent

    void initIntervalTask() {
        mSeconds = 20;
        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setText(mSeconds + " seconds");

        mScore = 0;
        mTimer = new Timer();
        TimerTask asyncTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO show randomized arrow
                        mSeconds--;
                        if (mTextView != null)
                            mTextView.setText(mSeconds + " seconds");

                    }
                });
            }
        };
        mTimer.schedule(asyncTask, 1000, 1000);

        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ScoreDialog dialog = ScoreDialog.newInstance(mScore);
                dialog.show(getSupportFragmentManager(), "SCORE");
                mTimer.cancel();
            }
        }, 20 * 1000);
    }

}
