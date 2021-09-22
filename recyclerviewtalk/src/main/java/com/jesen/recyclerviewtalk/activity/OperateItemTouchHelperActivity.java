package com.jesen.recyclerviewtalk.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.jesen.recyclerviewtalk.R;
import com.jesen.recyclerviewtalk.adapter.OnItemDragListener;
import com.jesen.recyclerviewtalk.adapter.OperateItemRecyclerAdapter;
import com.jesen.recyclerviewtalk.custom.OperateItemTouchHelperCallBack;

import java.util.ArrayList;

public class OperateItemTouchHelperActivity extends AppCompatActivity {

    private ArrayList<String> mDatas = new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);

        generateData();
        RecyclerView mRecyclerView = findViewById(R.id.linear_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        OperateItemRecyclerAdapter adapter =  new OperateItemRecyclerAdapter(this,mDatas);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onDrag(OperateItemRecyclerAdapter.NormalHolder normalHolder) {
                //mItemTouchHelper.startDrag(normalHolder);
                mItemTouchHelper.startSwipe(normalHolder);
            }
        });

        mItemTouchHelper = new ItemTouchHelper(new OperateItemTouchHelperCallBack(mDatas, adapter));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void generateData() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第"+i+"个Item");
        }
    }
}