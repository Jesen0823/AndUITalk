package com.jesen.screen_adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import com.jesen.screen_adapter.ui.UiUtil;
import com.jesen.screen_adapter.ui.ViewCalculateUtil;

public class MainActivity extends AppCompatActivity {

    private TextView tvText3;
    private TextView tvText4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtil.getInstance(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        tvText3 = findViewById(R.id.tvText3);
        tvText4 = findViewById(R.id.tvText4);
        ViewCalculateUtil.setViewLinearLayoutParam(tvText3, 540, 100, 0, 0, 0, 0);
        ViewCalculateUtil.setViewLinearLayoutParam(tvText4, 1080, 100, 0, 0, 0, 0);
        ViewCalculateUtil.setTextSize(tvText3,30);
    }

    // 屏幕切换
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        UiUtil.notityInstance(this);
    }
}