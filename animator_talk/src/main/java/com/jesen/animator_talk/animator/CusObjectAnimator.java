package com.jesen.animator_talk.animator;

import android.animation.ObjectAnimator;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * 属性动画自定义
 * */

public class CusObjectAnimator  implements VSYNCManager.AnimationFrameCallback {

    private static final String TAG = "CusObjectAnimator";
    long mStartTime = -1;
    private long mDuration = 0;
    private WeakReference<View> target;
    private float index = 0;
    private TimeInterpolator interpolator;
    CusFloatPropertyValuesHolder mFloatPropertyValuesHolder;
    private float fraction;

    public CusObjectAnimator(View view, String propertyName, float[] values) {
        target = new WeakReference<View>(view);
        mFloatPropertyValuesHolder = new CusFloatPropertyValuesHolder(propertyName,values);
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
    }

    public static CusObjectAnimator ofFloat(View view, String propertyName, float... values) {
        CusObjectAnimator animator = new CusObjectAnimator(view, propertyName,values);


        return animator;
    }

    // 每隔16ms回调一次
    @Override
    public boolean doAnimationFrame(long curTime) {
        float total = mDuration/16;
        if (interpolator !=null){
            // 当前执行进度百分比
            fraction = interpolator.getInterpolation(index++ / total);
        }
        // 结束后重新开始
        if(index >= total){
            index = 0;
        }
        mFloatPropertyValuesHolder.setAnimateValue(target.get(),fraction);
        return false;
    }

    public void start(){
        mFloatPropertyValuesHolder.setSetter(target);
        mStartTime = System.currentTimeMillis();
        VSYNCManager.newInstance().add(this);
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }
}
