package com.jesen.recyclerviewtalk.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.jesen.recyclerviewtalk.R;
import com.jesen.recyclerviewtalk.adapter.CoverFlowAdapter;
import com.jesen.recyclerviewtalk.custom.CoverFlowLayoutManager;

import java.util.ArrayList;

/**
 * 画廊效果按默认绘制顺序
 * */
public class CoverFlowActivity extends AppCompatActivity {
    private ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_flow);

        boolean is3D = getIntent().getBooleanExtra("style", false);

        generateDatas();
        RecyclerView mRecyclerView = findViewById(R.id.cover_recyclerview);
        mRecyclerView.setLayoutManager(new CoverFlowLayoutManager(is3D));

        CoverFlowAdapter adapter = new CoverFlowAdapter(this, mDatas);
        mRecyclerView.setAdapter(adapter);
    }

    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }
}