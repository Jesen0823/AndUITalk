package com.jesen.animator_talk.animator;

/**
 * 关键帧，保存某一刻的状态
 * 动画任务会被分解成关键帧
 * */

public class CusFloatKeyframe {
    // 当前百分比
    float mFraction;
    Class mValueType;
    // 变化的属性值
    float mValue;
    public  CusFloatKeyframe(float fraction, float value) {
        mFraction = fraction;
        mValue = value;
        mValueType = float.class;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float mValue) {
        this.mValue = mValue;
    }

    public float getFraction() {
        return mFraction;
    }
}
