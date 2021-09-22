package com.jesen.recyclerviewtalk.snap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.jesen.recyclerviewtalk.R;

import java.util.ArrayList;

/**
 * SnapHelper 的使用
 */
public class SnapHelperActivity extends AppCompatActivity {
    private ArrayList<String> mDatas = new ArrayList<>();

    private String activityType = "normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_helper);

        activityType = getIntent().getStringExtra("snapType");


        generateDatas();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.pager_recycler_view);

        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        SnapHelperAdapter adapter = new SnapHelperAdapter(this, mDatas);
        mRecyclerView.setAdapter(adapter);

        if ("start".equals(activityType)){
            StartSnapHelper startSnapHelper = new StartSnapHelper();
            startSnapHelper.attachToRecyclerView(mRecyclerView);
        }else {
            LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
            linearSnapHelper.attachToRecyclerView(mRecyclerView);
        }
    }

    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }
}