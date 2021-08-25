package com.jesen.path_basi.pathmeasure;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class PathTraceView  extends View {

    private Path mDstPath = new Path();
    private PathMeasure mPathMeasure;
    private Paint mPaint;
    private Paint mTextPaint;
    private float mEndDistance;
    private boolean isPayFinish = false;

    public PathTraceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(48);
        mTextPaint.setColor(Color.BLACK);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("TAG","---onDraw");
        canvas.clipRect(0,0,316,384);
        mPaint.setColor(Color.parseColor("#00A8F1"));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(150,150,105,mPaint);


        mPaint.setColor(Color.parseColor("#FFFFFB"));
        mPaint.setStyle(Paint.Style.STROKE);
        mDstPath.reset();
        mDstPath.moveTo(100,145);
        mPathMeasure.getSegment(0, mEndDistance, mDstPath,false);
        canvas.drawPath(mDstPath,mPaint);
        if(isPayFinish) {
            canvas.drawText("支付成功", 30.f, 330, mTextPaint);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.d("TAG","---onSizeChanged,mEndDistance = "+mEndDistance);
        Path drawPath = new Path();
        drawPath.moveTo(100,145);
        drawPath.lineTo(140,184);
        drawPath.lineTo(214,100);

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(drawPath,false);
        float length = mPathMeasure.getLength();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //动画的形式改变终点的距离
                mEndDistance = (float) animation.getAnimatedValue() * length;
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isPayFinish = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.start();

    }
}
