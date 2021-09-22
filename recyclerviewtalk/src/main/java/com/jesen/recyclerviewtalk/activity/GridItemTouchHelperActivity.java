package com.jesen.recyclerviewtalk.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.jesen.recyclerviewtalk.R;
import com.jesen.recyclerviewtalk.adapter.RecyclerAdapter;
import com.jesen.recyclerviewtalk.custom.MyItemTouchHelperCallBack;

import java.util.ArrayList;

/**
 * 网格布局的拖拽处理
 * */
public class GridItemTouchHelperActivity extends AppCompatActivity {

    private ArrayList<String> mDatas = new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);

        generateData();
        RecyclerView mRecyclerView = findViewById(R.id.linear_recycler_view);

        // 网格布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,5);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        // 添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        RecyclerAdapter adapter = new RecyclerAdapter(this,mDatas);
        mRecyclerView.setAdapter(adapter);

        mItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallBack(mDatas,adapter));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void generateData() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第"+i+"个Item");
        }
    }
}