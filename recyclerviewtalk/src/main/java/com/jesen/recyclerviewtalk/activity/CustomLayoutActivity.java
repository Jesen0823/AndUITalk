package com.jesen.recyclerviewtalk.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.jesen.recyclerviewtalk.R;
import com.jesen.recyclerviewtalk.adapter.RecyclerAdapter;
import com.jesen.recyclerviewtalk.custom.CustomLayoutManager;

import java.util.ArrayList;

public class CustomLayoutActivity extends AppCompatActivity {

    private final ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_recycled_layout);

        generateDatas();
        RecyclerView mRecyclerView = findViewById(R.id.recycled_recyclerview);

        mRecyclerView.setLayoutManager(new CustomLayoutManager());

        RecyclerAdapter adapter = new RecyclerAdapter(this, mDatas);
        mRecyclerView.setAdapter(adapter);
    }

    private void generateDatas() {
        for (int i = 0; i < 100; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }
}