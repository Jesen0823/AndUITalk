package com.jesen.custom_view.ripples;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class RipplesCircleView extends View {
    private RippleAnimationView rippleAnimationView;

    public RipplesCircleView(RippleAnimationView rippleAnimationView){
        this(rippleAnimationView.getContext(),null);
        this.rippleAnimationView = rippleAnimationView;
        this.setVisibility(INVISIBLE);
    }

    public RipplesCircleView(Context context) {
        super(context);
    }

    public RipplesCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RipplesCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = (Math.min(getWidth(),getHeight()))/2;
        canvas.drawCircle(radius,radius, radius-rippleAnimationView.getStrokWidth(),rippleAnimationView.paint);
    }
}









