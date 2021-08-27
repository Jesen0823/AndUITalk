package com.jesen.screen_adapter.ui;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class UiUtil {
    //    工具类
    private static UiUtil instance ;
    // ios  设计提供的标准
    public static final float STANDARD_WIDTH=1080f;
    public static final float STANDARD_HEIGHT=1920f;

    //实际设备信息
    public static float displayMetricsWidth;
    public static float displayMetricsHeight;
    public static float systemBarHeight;

    //    applicaiton
    public static UiUtil getInstance(Context context){
        if(instance==null){
            instance=new UiUtil(context);
        }
        return instance;
    }
    public static UiUtil notityInstance(Context context){
        instance=new UiUtil(context);
        return instance;
    }
    //    activity
    public static UiUtil getInstance() {
        if (instance == null) {
            throw new RuntimeException("UiUtil应该先调用含有构造方法进行初始化");
        }
        return instance;
    }
    private UiUtil(Context context) {
        // 计算缩放系数
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        if(displayMetricsWidth==0.0f || displayMetricsHeight==0.0f){
            // 在这里得到设备的真实值
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            // 状态栏高度
            systemBarHeight = getSystemBarHeight(context);

            if(displayMetrics.widthPixels>displayMetrics.heightPixels){
                // 横屏
                this.displayMetricsWidth=(float)(displayMetrics.heightPixels);
                this.displayMetricsHeight=(float)(displayMetrics.widthPixels-systemBarHeight);
            }else {
                // 竖屏
                this.displayMetricsWidth=(float)(displayMetrics.widthPixels);
                this.displayMetricsHeight=(float)(displayMetrics.heightPixels-systemBarHeight);
            }
        }
    }

    // 横向缩放系数
    public float getHorizontalScaleValue(){
        return  ((float)(displayMetricsWidth)) / STANDARD_WIDTH;
    }

    // 竖向缩放系数
    public float getVerticalScaleValue(){
        return ((float)(displayMetricsHeight))/(STANDARD_HEIGHT-systemBarHeight);
    }

    // 获取状态栏高度
    private int getSystemBarHeight(Context context){
        return getValue(context,"com.android.internal.R$dimen","system_bar_height",48);
    }

    public int getWidth(int width) {
        return Math.round((float)width * this.displayMetricsWidth / STANDARD_WIDTH);
    }
    public int getHeight(int height) {
        return Math.round((float)height * this.displayMetricsHeight / (STANDARD_HEIGHT-systemBarHeight));
    }

    private int getValue(Context context, String dimeClass, String system_bar_height, int defaultValue) {
        // com.android.internal.R$dimen.system_bar_height   状态栏的高度
        try {
            Class<?> clz=Class.forName(dimeClass);
            Object object = clz.newInstance();
            Field field=clz.getField(system_bar_height);
            int id=Integer.parseInt(field.get(object).toString());
            return context.getResources().getDimensionPixelSize(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
