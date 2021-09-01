package com.jesen.custom_view.pathmeasure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 自定义加载动画
 * */
public class PathLoadingView extends View {
    private Path mPath;
    private Paint mPaint;
    private float mLength;
    private Path dstPath;
    private float mAnimatorValue;
    private PathMeasure pathMeasure;


    public PathLoadingView(Context context) {
        this(context, null);
    }

    public PathLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#FF22EE"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10f);

        mPath = new Path();
        mPath.addCircle(300f, 300f, 100f, Path.Direction.CW);

        pathMeasure = new PathMeasure(mPath, true);
        mLength = pathMeasure.getLength();
        dstPath = new Path();

        // 属性动画
        final ValueAnimator va = ValueAnimator.ofFloat(0, 1);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        va.setDuration(2000);
        // 无限循环
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        dstPath.reset();
        float distance = mLength * mAnimatorValue;
        float start = (float) (distance - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLength));
        pathMeasure.getSegment(start, distance, dstPath, true);
        canvas.drawPath(dstPath, mPaint);
    }
}











