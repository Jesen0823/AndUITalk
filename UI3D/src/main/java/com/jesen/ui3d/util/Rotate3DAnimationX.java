package com.jesen.ui3d.util;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;
/**
 * 升级版：
 * 将0~180度分为两段：0~90时，view与Camera的距离需要变大，90~180时，view与Camera的距离需要变小
 * */
public class Rotate3DAnimationX extends Animation {
    private final float mFromDegress;
    private final float mEndDegress;
    private float mCenterX, mCenterY;
    private Camera mCamera;

    // 升级版 添加两个字段
    private final float mDepthZ = 500;
    private final boolean mReverse;

    public Rotate3DAnimationX(float fromDegress, float endDegress,boolean reverse) {
        this.mFromDegress = fromDegress;
        this.mEndDegress = endDegress;
        this.mReverse = reverse;
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
        float z;
        if (mReverse){
            z=mDepthZ*interpolatedTime;
        }else {
            z = mDepthZ*(1.0f-interpolatedTime);
        }
        mCamera.translate(0.0f,0.0f,z);

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
