package com.jesen.canvas_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jesen.canvas_api.split.SplitView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new TransformView(this));
        setContentView(new SplitView(this));

    }
}