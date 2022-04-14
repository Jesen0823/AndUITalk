package com.jesen.event_talk.googleevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class MButton extends AppCompatButton {
    public MButton(@NonNull Context context) {
        super(context);
    }

    public MButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        MLog.d("MButton, dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MLog.d("MButton, onTouchEvent");
        // 返回true 我消费掉
        // 返回false,会走到 MLinearLayout的onTouchEvent
        // return false;
        return super.onTouchEvent(event);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        MLog.d("MButton, setOnTouchListener");
        super.setOnTouchListener(l);
    }
}
