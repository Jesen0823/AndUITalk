package com.jesen.recyclerviewtalk.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.jesen.recyclerviewtalk.R;
import com.jesen.recyclerviewtalk.adapter.StaggeredRecyclerAdapter;

import java.util.ArrayList;

/**
 * 简单瀑布流布局
 * */
public class StaggeredActivity extends AppCompatActivity {

    private final ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered);

        generateDatas();
        RecyclerView mRecyclerView = findViewById(R.id.stagger_recycler_view);
        //瀑布流布局
        StaggeredGridLayoutManager staggeredManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredManager);

        StaggeredRecyclerAdapter adapter = new StaggeredRecyclerAdapter(this, mDatas);
        mRecyclerView.setAdapter(adapter);
    }

    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }
}