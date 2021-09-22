package com.jesen.recyclerviewtalk.qqdelete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.jesen.recyclerviewtalk.R;
import com.jesen.recyclerviewtalk.qqdelete.lirary.ItemTouchHelperExtension;

import java.util.ArrayList;

/**
 * 用 ItemTouchHelper 实现QQ删除效果
 * */

public class QQDeleteActivity extends AppCompatActivity {

    private ArrayList<String> mDatas = new ArrayList<>();
    private ItemTouchHelperExtension mItemTouchHelperExtension;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqdelete);

        generateDatas();
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        final QQDeleteAdapter adapter = new QQDeleteAdapter(this, mDatas);
        adapter.setOnBtnClickListener(new OnBtnClickListener() {
            @Override
            public void onDelete(QQDeleteAdapter.NormalHolder holder) {
                Toast.makeText(QQDeleteActivity.this,"点击delete",Toast.LENGTH_SHORT).show();
                /**
                 * 使用ItemTouchHelperExtension实现
                 */
                mDatas.remove(holder);
                adapter.notifyItemRemoved(holder.getAdapterPosition());
            }

            @Override
            public void onRefresh(QQDeleteAdapter.NormalHolder holder) {
                Toast.makeText(QQDeleteActivity.this,"点击refresh",Toast.LENGTH_SHORT).show();
                /**
                 * 使用ItemTouchHelperExtension实现
                 */
                mItemTouchHelperExtension.closeOpened();
            }
        });
        recyclerView.setAdapter(adapter);

        mItemTouchHelperExtension = new ItemTouchHelperExtension(new QQDeleteTouchHelperCallBack(mDatas,adapter));
        mItemTouchHelperExtension.attachToRecyclerView(recyclerView);
    }

    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }
}