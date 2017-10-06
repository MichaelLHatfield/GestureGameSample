package io.github.iwag.gestureapp;

import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mTextView = (TextView) findViewById(R.id.textView2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        // TODO
        return true;
    }

    public void doStart(View v) {
//        Intent i = new Intent(this, MainActivity.class);
//        startActivity(i);
    }
}
