package com.jesen.event_talk.googleevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MLinearLayout extends LinearLayout {


    public MLinearLayout(Context context) {
        super(context);
    }

    public MLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        MLog.d("MLinearLayout, onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
        // 1. 如果返回true 则表示拦截，不会给子View，会走自己的onTouchEvent
        //return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        MLog.d("MLinearLayout, dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MLog.d("MLinearLayout, onTouchEvent");
        //return true;
        // 如果返回false，则会回到Activity的dispatchTouchEvent
        // 如果返回true,则会回到Activity的dispatchTouchEven,再回到本类的dispatchTouchEvent，onTouchEvent
        return super.onTouchEvent(event);
    }
}
