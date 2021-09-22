package com.jesen.recyclerviewtalk.qqdelete;

import android.graphics.Canvas;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.jesen.recyclerviewtalk.qqdelete.lirary.ItemTouchHelperExtension;

import java.util.ArrayList;

/**
 * 使用ItemTouchHelperExtension的实现代码 实现QQ删除效果
 */

public class QQDeleteTouchHelperCallBack extends ItemTouchHelperExtension.Callback {
    private ArrayList<String> mDatas;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    public QQDeleteTouchHelperCallBack(ArrayList<String> datas, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        mDatas = datas;
        mAdapter = adapter;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = ItemTouchHelper.LEFT;
        int flags = makeMovementFlags(dragFlags, swipeFlags);
        return flags;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        // super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            ((QQDeleteAdapter.NormalHolder) viewHolder).mItemText.setTranslationX((int) dX);
        }
    }
}

