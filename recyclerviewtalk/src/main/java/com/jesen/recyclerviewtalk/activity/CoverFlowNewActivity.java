package com.jesen.recyclerviewtalk.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.jesen.recyclerviewtalk.R;
import com.jesen.recyclerviewtalk.adapter.CoverFlowAdapter;
import com.jesen.recyclerviewtalk.custom.CoverFlowLayoutManager;
import com.jesen.recyclerviewtalk.custom.RecyclerCoverFlowView;

import java.util.ArrayList;

/**
 * 画廊效果，通过自定义Recyclerview重写了绘制顺序
 * 代码与 CoverFlowActivity 相同，只不过使用了自定义 RecyclerView,即 RecyclerCoverFlowView
 * */
public class CoverFlowNewActivity extends AppCompatActivity {

    private ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_flow_new);

        boolean is3D = getIntent().getBooleanExtra("style", false);

        generateDatas();
        RecyclerCoverFlowView mRecyclerView = findViewById(R.id.cover_recyclerview_new);
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