package com.jesen.animator_talk;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.Button;

import com.jesen.animator_talk.animator.CusObjectAnimator;
import com.jesen.animator_talk.animator.LineInterpolator;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn);

        //ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(button,"scaleX",1f,2f);

        button.setOnClickListener(view->{
            CusObjectAnimator cusObjectAnimator = CusObjectAnimator.ofFloat(button,"scaleX",1f,2f);
            cusObjectAnimator.setDuration(3000);
            cusObjectAnimator.setInterpolator(new LineInterpolator());
            cusObjectAnimator.start();
        });
    }
}