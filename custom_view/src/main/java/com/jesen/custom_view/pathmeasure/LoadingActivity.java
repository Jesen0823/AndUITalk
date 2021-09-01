package com.jesen.custom_view.pathmeasure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.jesen.custom_view.R;

public class LoadingActivity extends AppCompatActivity {

    private PayResultView payView;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        payView = findViewById(R.id.pay_view);
        payView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag) {
                    payView.startPaying();
                    flag = true;
                }else {
                    payView.showResult(true);
                    flag = false;
                }
            }
        });
    }
}