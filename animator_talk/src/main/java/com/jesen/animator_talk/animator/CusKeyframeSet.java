package com.jesen.animator_talk.animator;

import android.animation.FloatEvaluator;
import android.animation.TypeEvaluator;

import java.util.Arrays;
import java.util.List;

/**
 * 分解关键帧，管理关键帧
 * */

public class CusKeyframeSet {
    // 类型估值器
    TypeEvaluator mEvaluator;
    CusFloatKeyframe mFirstKeyframe;
    List<CusFloatKeyframe> mKeyframes;
    private CusFloatKeyframe nextKeyFrame;

    public CusKeyframeSet(CusFloatKeyframe...keyframes){
        mKeyframes = Arrays.asList(keyframes);
        mFirstKeyframe = keyframes[0];
        mEvaluator = new FloatEvaluator();
    }

    public static CusKeyframeSet ofFloat(float[] values) {
        int numKeyframes = values.length;
        CusFloatKeyframe keyframes[] = new CusFloatKeyframe[numKeyframes];
        keyframes[0] = new CusFloatKeyframe(0, values[0]);
        for (int i = 1; i < numKeyframes; ++i) {
            keyframes[i] = new CusFloatKeyframe(i/(numKeyframes-1),values[i]);
        }
        return new CusKeyframeSet(keyframes);
    }

    public Object getValue(float fraction) {
        CusFloatKeyframe preKeyframe = mFirstKeyframe;
        for (int i = 1; i < mKeyframes.size(); ++i) {
            nextKeyFrame = mKeyframes.get(i);
            if (fraction < nextKeyFrame.getFraction()) {
                return mEvaluator.evaluate(fraction, preKeyframe.getValue(), nextKeyFrame.getValue());
            }
            preKeyframe = nextKeyFrame;
        }
    return null;
    }
}
