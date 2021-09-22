package com.jesen.recyclerviewtalk.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.jesen.recyclerviewtalk.R;
import com.jesen.recyclerviewtalk.adapter.RecyclerAdapter;
import com.jesen.recyclerviewtalk.custom.CustomLayoutManagerRecyclered;

import java.util.ArrayList;

public class CustomRecycledLayoutActivity extends AppCompatActivity {

    private ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_recycled_layout);

        generateDatas();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycled_recyclerview);

        mRecyclerView.setLayoutManager(new CustomLayoutManagerRecyclered());

        RecyclerAdapter adapter = new RecyclerAdapter(this, mDatas);
        mRecyclerView.setAdapter(adapter);

    }

    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }
}