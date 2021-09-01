package com.jesen.custom_view.pathmeasure;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.jesen.custom_view.R;

public class PayResultView extends View {

    public static final String TAG = "PayResultView";

    //外圈是否绘制完毕
    private boolean mCircleDrawFinish;

    //由于失败状态的叉是两条线条，需要判断其中一条是否绘制完成
    private boolean mFailDrawHalf;

    //加载状态下的路径测量
    private PathMeasure mPayingMeasure;

    //结果状态下的路径测量
    private PathMeasure mResultMeasure;

    //加载状态路径
    private Path mPayingPath;

    //成功状态路径
    private Path mSuccessPath;

    //失败状态路径
    private Path mFailPath;

    //当前正在绘制的路径
    private Path mSegPath;

    //加载状态绘制路径的起始点
    private float mStartPos, mEndPos;

    //结果状态绘制路径的终点
    private float mCurResultPos = 1;

    //加载状态动画
    private ValueAnimator mPayingAnimator;

    //结果状态动画
    private ValueAnimator mResultAnimator;

    private Paint mPaint;

    /**
     * 绘制的颜色
     */
    private int mColor;

    /**
     * 绘制的线条粗细
     */
    private int mStrokeWidth;

    /**
     * Paying状态每圈动画时长
     */
    private long mPayingDur;

    /**
     * 展示结果状态每圈动画时长
     */
    private long mResultDur;

    /**
     * 当前所处的状态
     */
    private Status mStatus;


    private enum Status {
        //成功
        SUCCESS,
        //失败
        FAIL,
        //加载中
        Paying
    }


    public PayResultView(Context context) {
        this(context, null);
    }

    public PayResultView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PayResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleStyleable(context, attrs, defStyleAttr);
        init();
    }

    private void handleStyleable(Context context, AttributeSet attrs, int defStyle) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PayResultView, defStyle, 0);
        try {
            mColor = ta.getColor(R.styleable.PayResultView_paying_color, Color.BLACK);
            mStrokeWidth = ta.getDimensionPixelSize(R.styleable.PayResultView_paying_stroke, 8);
            mPayingDur = ta.getInteger(R.styleable.PayResultView_paying_dur, 1000);
            mResultDur = ta.getInteger(R.styleable.PayResultView_result_dur, 3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ta.recycle();
        }
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mPayingPath = new Path();
        mSuccessPath = new Path();
        mFailPath = new Path();
        mSegPath = new Path();
        mPayingMeasure = new PathMeasure();
        mResultMeasure = new PathMeasure();

        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);

        initAnim();
    }

    private void initAnim() {
        mResultAnimator = ValueAnimator.ofFloat(1, 4);
        mResultAnimator.setDuration(mResultDur);
        mResultAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurResultPos = (float) valueAnimator.getAnimatedValue();
                if (mCurResultPos >= 2 && !mCircleDrawFinish) {
                    mCircleDrawFinish = true;
                    mCurResultPos = 2;
                }
                if (mCurResultPos >= 3 && !mFailDrawHalf) {
                    mFailDrawHalf = true;
                    mCurResultPos = 3;
                }
                invalidate();
            }
        });

        mPayingAnimator = ValueAnimator.ofFloat(0, 1);
        mPayingAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mPayingAnimator.setDuration(mPayingDur);
        mPayingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (mStatus == Status.SUCCESS || mStatus == Status.FAIL) {
                    Log.d(TAG, "支付Paying完成，生成结果");
                    mPayingAnimator.cancel();
                    mResultAnimator.start();
                    return;
                }
                float curValue = (float) valueAnimator.getAnimatedValue();
                if (curValue > 0.5) {
                    mStartPos = curValue * 2 - 1;
                    mEndPos = curValue;
                } else {
                    mEndPos = curValue;
                    mStartPos = 0;
                }
                invalidate();
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int mWidth = right - left;
        int mHeight = bottom - top;
        int mRadius = mWidth >= mHeight ? mHeight / 2 - mStrokeWidth : mWidth / 2 - mStrokeWidth;
        int centerX = mWidth / 2;
        int centerY = mHeight / 2;
        mPayingPath.addCircle(centerX, centerY, mRadius, Path.Direction.CW);
        //绘制成功会首先绘制外层的圆圈，再绘制里面的勾
        mSuccessPath.addCircle(centerX, centerY, mRadius, Path.Direction.CW);
        mSuccessPath.moveTo(centerX - mRadius / 2, centerY);
        mSuccessPath.lineTo(centerX - mRadius / 6, centerY + mRadius / 2);
        mSuccessPath.lineTo(centerX + mRadius * 2 / 3, centerY - mRadius / 3);
        //绘制失败会首先绘制外层的圆圈，再绘制里面的叉
        mFailPath.addCircle(centerX, centerY, mRadius, Path.Direction.CW);
        mFailPath.moveTo(centerX - mRadius / 2, centerY - mRadius / 2);
        mFailPath.lineTo(centerX + mRadius / 2, centerY + mRadius / 2);
        mFailPath.moveTo(centerX + mRadius / 2, centerY - mRadius / 2);
        mFailPath.lineTo(centerX - mRadius / 2, centerY + mRadius / 2);

        mPayingMeasure.setPath(mPayingPath, false);
    }

    /**
     * 展示结果
     *
     * @param flag true:成功  false:失败
     */
    public void showResult(boolean flag) {
        mResultMeasure.setPath(flag ? mSuccessPath : mFailPath, false);
        mStatus = flag ? Status.SUCCESS : Status.FAIL;
    }

    /**
     * 开始加载
     */
    public void startPaying() {
        mStatus = Status.Paying;
        mPayingAnimator.start();
        mCurResultPos = 1;
        mCircleDrawFinish = false;
        mFailDrawHalf = false;
        Log.d(TAG, "开始展示");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPayingAnimator.cancel();
        mResultAnimator.cancel();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG,"onDraw....");

        if (!(mStatus == Status.SUCCESS || mStatus == Status.FAIL)) {
            drawPaying(canvas);
            return;
        }
        drawResult(canvas);
    }

    private void drawPaying(Canvas canvas) {
        mSegPath.reset();
        mPayingMeasure.getSegment(mStartPos * mPayingMeasure.getLength(), mEndPos * mPayingMeasure.getLength(), mSegPath, true);
        canvas.drawPath(mSegPath, mPaint);
    }

    private void drawResult(Canvas canvas) {
        if (mCurResultPos < 2) {
            mSegPath.reset();
            mResultMeasure.getSegment(0, (mCurResultPos - 1) * mResultMeasure.getLength(), mSegPath, true);
        } else if (mCurResultPos == 2) {
            Log.d(TAG, "结果外圈绘制结束,开始绘制中心部分");
            mResultMeasure.getSegment(0, (mCurResultPos - 1) * mResultMeasure.getLength(), mSegPath, true);
            mResultMeasure.nextContour();
        } else {
            drawResultCenter();
        }
        canvas.drawPath(mSegPath, mPaint);
    }

    private void drawResultCenter() {
        if (mStatus == Status.SUCCESS) {
            mResultMeasure.getSegment(0, (mCurResultPos - 2) * mResultMeasure.getLength(), mSegPath, true);
            return;
        }
        if (mCurResultPos < 3) {
            mResultMeasure.getSegment(0, (mCurResultPos - 2) * mResultMeasure.getLength(), mSegPath, true);
        } else if (mCurResultPos == 3) {
            mResultMeasure.nextContour();
        } else {
            mResultMeasure.getSegment(0, (mCurResultPos - 3) * mResultMeasure.getLength(), mSegPath, true);
        }
    }
}