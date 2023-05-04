package com.jesen.ui3d.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jesen.ui3d.R;

public class CameraImageView extends androidx.appcompat.widget.AppCompatImageView {
    private Bitmap mBitmap;
    private Paint mPaint;
    private final Camera mCamera = new Camera();
    private final Matrix matrix = new Matrix();
    private int mProgress;

    public CameraImageView(@NonNull Context context) {
        super(context);
        init();
    }

    public CameraImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test2);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCamera.save();
        canvas.save();

        mPaint.setAlpha(100);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        mCamera.rotateY(mProgress);
        mCamera.applyToCanvas(canvas);
        mCamera.restore();
        super.onDraw(canvas);
        canvas.restore();
    }

    public void setProgress(int progress) {
        mProgress = progress;
        postInvalidate();
    }
}
