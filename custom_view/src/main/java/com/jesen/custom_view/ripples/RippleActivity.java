package com.jesen.custom_view.ripples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.jesen.custom_view.R;
import com.jesen.custom_view.ui_adapter.UiUtil;
import com.jesen.custom_view.ui_adapter.ViewCalculateUtil;

public class RippleActivity extends AppCompatActivity {

    private ImageView startBtn;
    private RippleAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtil.getInstance(this);
        setContentView(R.layout.activity_ripple);

        startBtn = findViewById(R.id.start_do_btn);
        ViewCalculateUtil.setViewLayoutParam(startBtn,300,300,0,0,0,0);
        animationView = findViewById(R.id.layout_RippleAnimation);

        startBtn.setOnClickListener(view ->{
            if (animationView.isAnimatorRunning()){
                animationView.stopRippleAnimation();
            }else {
                animationView.startRippleAnimation();
            }
        });
    }
}