package com.jesen.screen_adapter_liuhai;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * 刘海屏适配
 *
 * 适配要点：
 *  华为， 小米，oppo,volvo等单独适配
 *  1. 判断手机厂商，
 *  2，判断手机是否刘海，
 *  3，设置是否让内容区域延伸进刘海
 *  4，设置控件是否避开刘海区域
 *  5，获取刘海的高度
 * */

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. 设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean hasDisplayCutout = hasDisplayCutout(window);
        if (hasDisplayCutout){
            WindowManager.LayoutParams params = window.getAttributes();
            /**
             *  * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT 全屏模式，内容下移，非全屏不受影响
             *  * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES 允许内容去延伸进刘海区
             *  * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER 不允许内容延伸进刘海区
             */
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            window.setAttributes(params);

            //3.设置成沉浸式
            int flags = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility |= flags; //追加沉浸式设置
            window.getDecorView().setSystemUiVisibility(visibility);
        }

        setContentView(R.layout.activity_main);

        if (hasDisplayCutout) {
            fitLiuHai();
        }

    }

    private void fitLiuHai() {
        // 仅仅单独适配控件
        /*Button button = findViewById(R.id.button);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) button.getLayoutParams();
        layoutParams.topMargin = getDisplayCutoutHeight();
        button.setLayoutParams(layoutParams);*/

        // 布局整体适配
        RelativeLayout layout = findViewById(R.id.container);
        layout.setPadding(layout.getPaddingLeft(), getDisplayCutoutHeight(), layout.getPaddingRight(), layout.getPaddingBottom());

    }

    // 手机系统是否有刘海屏，华为小米等国产另需判断
    private boolean hasDisplayCutout(Window window){
      /*  DisplayCutout displayCutout;
        View rootView = window.getDecorView();
        WindowInsets insets = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
             insets = rootView.getRootWindowInsets();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && insets != null){
            displayCutout = insets.getDisplayCutout();
            if (displayCutout != null){
                if (displayCutout.getBoundingRects() != null
                        && displayCutout.getBoundingRects().size() >0
                && displayCutout.getSafeInsetTop() >0){
                    Log.d("HHHH","Yes, has cutout.");
                    return true;
                }
            }
        }
        return false;*/

        // 模拟器获取不到，直接返回true
        return true;
    }

    // 一般地，系统刘海高度等于状态栏高度
    public int getDisplayCutoutHeight(){
        int resId = getResources().getIdentifier("status_bar_height","dimen","android");
        if (resId >0){
            return getResources().getDimensionPixelSize(resId);
        }
        return 96;
    }
}