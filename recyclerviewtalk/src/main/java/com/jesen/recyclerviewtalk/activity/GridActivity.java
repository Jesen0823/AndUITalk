package com.jesen.recyclerviewtalk.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jesen.recyclerviewtalk.R;
import com.jesen.recyclerviewtalk.adapter.RecyclerAdapter;

import java.util.ArrayList;

/**
 * 普通网格布局
 * */
public class GridActivity extends AppCompatActivity {
    private final ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        generateDatas();
        RecyclerView mRecyclerView = findViewById(R.id.grid_recycler_view);

        //如果是横向滚动，后面的数值表示的是几行，如果是竖向滚动，后面的数值表示的是几列
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        //gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        RecyclerAdapter adapter = new RecyclerAdapter(this, mDatas);
        mRecyclerView.setAdapter(adapter);
    }

    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }
}