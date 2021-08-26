package com.jesen.animator_talk_splash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Constructor;

public class ParallaxLayoutInflater extends LayoutInflater {

    private static final String TAG = "ParallaxLayoutInflater";

    private ParallaxFragment fragment;

    // 需要通过监听Factory去拿到的属性
    int[] attrIds = {
            R.attr.a_in,
            R.attr.a_out,
            R.attr.x_in,
            R.attr.x_out,
            R.attr.y_in,
            R.attr.y_out
    };

    public ParallaxLayoutInflater(LayoutInflater original, Context newContext, ParallaxFragment fragment) {
        super(original, newContext);
        this.fragment = fragment;
        setFactory2(new ParallaxFactory(this));
    }

    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
    }

    // 自定义LayoutInflater必须实现该方法
    @Override
    public LayoutInflater cloneInContext(Context context) {
        return new ParallaxLayoutInflater(this, context,fragment);
    }

    // 为了监听LayoutInflater对布局解析过程
    class ParallaxFactory implements Factory2{

        private LayoutInflater inflater;
        private final String[] sClassPrefix = {
                "android.widget.",
                "android.view."
        };

        public ParallaxFactory(ParallaxLayoutInflater parallaxLayoutInflater) {
            this.inflater = parallaxLayoutInflater;
        }

        @SuppressLint("ResourceType")
        @Nullable
        @Override
        public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attributeSet) {
            Log.d(TAG,"---onCreateView，name:"+name);
            View view = null;
            view = createMyView(name,context,attributeSet);
            if (view != null){
                TypedArray a = context.obtainStyledAttributes(attributeSet,attrIds);
                if (a != null && a.length() > 0) {
                    //获取自定义属性的值
                    ParallaxViewTag tag = new ParallaxViewTag();
                    tag.alphaIn = a.getFloat(0, 0f);
                    tag.alphaOut = a.getFloat(1, 0f);
                    tag.xIn = a.getFloat(2, 0f);
                    tag.xOut = a.getFloat(3, 0f);
                    tag.yIn = a.getFloat(4, 0f);
                    tag.yOut = a.getFloat(5, 0f);
                    view.setTag(R.id.parallax_view_tag,tag);
                }
                fragment.getParallaxViews().add(view);
                a.recycle();
            }
            return view;
        }

        private View createMyView(String name, Context context, AttributeSet attributeSet) {
            if (name.contains(".")){
                // 是自定义控件，非系统提供的控件
                return generateView(name,null,context,attributeSet);
            }else {
                // 系统控件
                // 目的：获取系统控件的自定义属性
                for (String prefix : sClassPrefix) {
                    View view = generateView(name, prefix, context, attributeSet);
                    if (view != null) {
                        return view;
                    }
                }
            }
            return null;
        }


        // 通过反射生成一个View对象
        private View generateView(String name,String prefix, Context context, AttributeSet attributeSet){
            try {
                return inflater.createView(name, prefix,attributeSet);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull String s, @NonNull Context context, @NonNull AttributeSet attributeSet) {
            return null;
        }
    }
}
