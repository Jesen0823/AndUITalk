package com.jesen.ui3d.view;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ClockViewGroup extends LinearLayout {
    private static final float MAX_ROTATE_DEGREE = 20;

    private int mCenterX, mCenterY;
    private float mCanvasRotateX, mCanvasRotateY;
    private final Matrix mMatrix = new Matrix();
    private final Camera mCamera = new Camera();
    private ValueAnimator mSteadyAnim;

    public ClockViewGroup(Context context) {
        super(context);
    }

    public ClockViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ClockViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mMatrix.reset();

        mCamera.save();
        mCamera.rotateX(mCanvasRotateX);
        mCamera.rotateY(mCanvasRotateY);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.preTranslate(-mCenterX, -mCenterY);
        mMatrix.postTranslate(mCenterX, mCenterY);

        canvas.save();
        canvas.setMatrix(mMatrix);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                cancelSteadyAnimIfNeed();
                rotateCanvasWhenMove(x, y);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                rotateCanvasWhenMove(x, y);
                return true;
            }
            case MotionEvent.ACTION_UP: {
                startNewSteadyAnim();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void startNewSteadyAnim() {
        final String propertyNameRotateX = "mCanvasRotateX";
        final String propertyNameRotateY = "mCanvasRotateY";
        PropertyValuesHolder valuesHolderX = PropertyValuesHolder.ofFloat(propertyNameRotateX, mCanvasRotateX, 0);
        PropertyValuesHolder valuesHolderY = PropertyValuesHolder.ofFloat(propertyNameRotateY, mCanvasRotateY, 0);
        mSteadyAnim = ValueAnimator.ofPropertyValuesHolder(valuesHolderX, valuesHolderY);
        mSteadyAnim.setDuration(1000);
        mSteadyAnim.setInterpolator(new BounceInterpolator());
        mSteadyAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                mCanvasRotateX = (float) animation.getAnimatedValue(propertyNameRotateX);
                mCanvasRotateY = (float) animation.getAnimatedValue(propertyNameRotateY);
                postInvalidate();
            }
        });
        mSteadyAnim.start();
    }

    private void rotateCanvasWhenMove(float x, float y) {
        float dx = x - mCenterX;
        float dy = y - mCenterY;
        float percentX = dx / (getWidth() / 2f);
        float percentY = dy / (getHeight() / 2f);
        if (percentX > 1f) {
            percentX = 1f;
        } else if (percentX < -1f) {
            percentX = -1f;
        }
        if (percentY > 1f) {
            percentY = 1f;
        } else if (percentY < -1f) {
            percentY = -1f;
        }
        mCanvasRotateX = -MAX_ROTATE_DEGREE * percentY;
        mCanvasRotateY = MAX_ROTATE_DEGREE * percentX;
    }

    private void cancelSteadyAnimIfNeed() {
        if (mSteadyAnim != null && (mSteadyAnim.isStarted() || mSteadyAnim.isRunning())) {
            mSteadyAnim.cancel();
        }
    }
}
