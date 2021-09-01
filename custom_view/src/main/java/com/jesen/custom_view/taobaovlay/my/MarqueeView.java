package com.jesen.custom_view.taobaovlay.my;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewFlipper;

import androidx.annotation.Dimension;
import androidx.annotation.Nullable;

import com.jesen.custom_view.R;

import java.util.List;

public class MarqueeView extends ViewFlipper implements MarqueeAdapter.OnDataChangedListener{

    private int mvAnimDuration;
    private String mvDirection="bottom_to_top";
    private int mvInterval=3000;
    private boolean mvSingleLine= true;
    private int mvTextColor;
    private int mvTextSize;
    private int itemCount = 1;

    private MarqueeAdapter marqueeAdapter;
    private int mInAnim = R.anim.anim_marquee_in;
    private int mOutAnim = R.anim.anim_marquee_out;

    public MarqueeView(Context context) {
        super(context);
    }

    public MarqueeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MarqueeView);
        mvAnimDuration = a.getInt(R.styleable.MarqueeView_mvAnimDuration,1000);
        mvInterval = a.getInt(R.styleable.MarqueeView_mvInterval,3000);
        mvSingleLine = a.getBoolean(R.styleable.MarqueeView_mvSingleLine,true);
        mvDirection = a.getString(R.styleable.MarqueeView_mvDirection);
        mvTextColor =a.getColor(R.styleable.MarqueeView_mvTextColor,Color.BLACK);
        mvTextSize =a.getInteger(R.styleable.MarqueeView_mvTextSize,12);

        a.recycle();

        initView();
    }

    private void initView() {

    }

    public void startWithList(List<String> strings, int inAnim, int outAnim){
        this.mInAnim = inAnim;
        this.mOutAnim = outAnim;

    }

    @Override
    public void onChanged() {

    }
}
