package com.jesen.animator_talk.animator;

import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 用来设置动画具体的属性和值，关键帧
 * */
public class CusFloatPropertyValuesHolder {
    // 属性名
    String mPropertyName;
    // float
    Class mValueType;
    CusKeyframeSet mKeyframes;
    Method mSetter = null;

    public CusFloatPropertyValuesHolder(String propertyName, float...values){
        this.mPropertyName = propertyName;
        mValueType = float.class;
        mKeyframes = CusKeyframeSet.ofFloat(values);
    }

    public void setSetter(WeakReference<View> target) {
        char firstChar = Character.toUpperCase(mPropertyName.charAt(0));
        String rest = mPropertyName.substring(1);
        String methodName = new StringBuilder("set").append(firstChar).append(rest).toString();
        try {
            mSetter = View.class.getMethod(methodName,float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setAnimateValue(View target, float fraction) {
        // 传入百分比，返回具体的值
        Object value = mKeyframes.getValue(fraction);
        try {
            mSetter.invoke(target,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
