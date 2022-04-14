package com.jesen.event_talk.googleevent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.jesen.event_talk.R;

public class MlinearActivity extends AppCompatActivity {

    private MButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_linearlayout);

        MLog.d("MlinearActivity, onCreate");
        button = findViewById(R.id.testBtn);

        button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean isUse = false; // 默认false
                // 如果返回true则消费掉，不再走View的onTouchEvent
                // 如果默认返回false，则会走到onclick
                MLog.d("MlinearActivity, button, onTouch:"+isUse);
                return isUse;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MLog.d("MlinearActivity, button, onClick");
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        MLog.d("MlinearActivity, dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }
}