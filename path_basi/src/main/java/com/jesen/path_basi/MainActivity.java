package com.jesen.path_basi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jesen.path_basi.path.PathView;
import com.jesen.path_basi.pathmeasure.PathMeasureView;
import com.jesen.path_basi.pathmeasure.PathTraceView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new PathView(this));
        //setContentView(R.layout.activity_main);
        //setContentView(new PathMeasureView(this));
        setContentView(new PathTraceView(this,null));
    }
}