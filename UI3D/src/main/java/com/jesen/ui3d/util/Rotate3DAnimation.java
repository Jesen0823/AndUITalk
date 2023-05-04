package com.jesen.ui3d.util;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3DAnimation extends Animation {
    private final float mFromDegress;
    private final float mEndDegress;
    private float mCenterX, mCenterY;
    private Camera mCamera;

    public Rotate3DAnimation(float fromDegress, float endDegress) {
        this.mFromDegress = fromDegress;
        this.mEndDegress = endDegress;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCenterX = width / 2f;
        mCenterY = height / 2f;
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float degrees = mFromDegress + (mEndDegress - mFromDegress) * interpolatedTime;
        mCamera.save();
        final Matrix matrix = t.getMatrix();
        mCamera.rotateY(degrees);
        mCamera.getMatrix(matrix);
        mCamera.restore();
        // 旋转中心点从左上角移到图片中心
        matrix.preTranslate(-mCenterX, -mCenterY);
        matrix.postTranslate(mCenterX, mCenterY);

        super.applyTransformation(interpolatedTime, t);
    }
}
