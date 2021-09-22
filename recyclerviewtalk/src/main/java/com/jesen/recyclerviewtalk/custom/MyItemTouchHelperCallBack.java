package com.jesen.recyclerviewtalk.custom;

import android.graphics.Canvas;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * ItemTouchHelper实现recyclerview的滑动与拖拽
 */
public class MyItemTouchHelperCallBack extends ItemTouchHelper.Callback {
    private static final String TAG = "MyTouchHelperCallBack";

    private ArrayList<String> mDatas;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    public MyItemTouchHelperCallBack(ArrayList<String> datas, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        mDatas = datas;
        mAdapter = adapter;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // 如果你不想上下拖动，可以将 dragFlags = 0
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

        // 如果你不想左右滑动，可以将 swipeFlags = 0
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        //最终的动作标识（flags）必须要用makeMovementFlags()方法生成
        int flags = makeMovementFlags(dragFlags, swipeFlags);
        return flags;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        Collections.swap(mDatas, fromPosition, toPosition);
        mAdapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mDatas.remove(viewHolder.getAdapterPosition());
        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        Log.d(TAG, "onSwiped direction:" + direction);
    }

    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);

        if (viewHolder == null || viewHolder.itemView == null) {
            return;
        }

        //不管是拖拽或是侧滑，背景色都要变化
        if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
            /*int bgColor = viewHolder.itemView.getContext().getResources().getColor(android.R.color.holo_green_dark);
            viewHolder.itemView.setBackgroundColor(bgColor);*/
        } else if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            int bgColor = viewHolder.itemView.getContext().getResources().getColor(android.R.color.holo_orange_light);
            viewHolder.itemView.setBackgroundColor(bgColor);
        } else if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            int bgColor = viewHolder.itemView.getContext().getResources().getColor(android.R.color.holo_blue_bright);
            viewHolder.itemView.setBackgroundColor(bgColor);
        }
        Log.d(TAG, "onSelectedChanged actionState:" + actionState);
    }

    @Override
    public void onChildDraw(Canvas c,
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            float dX,
                            float dY,
                            int actionState,
                            boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        Log.d(TAG, "dx:" + dX + "  dy:" + dY);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //滑动时改变Item的透明度
            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setScaleX(alpha);
        }
    }

    /**
     * 当item的交互动画结束时触发
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(android.R.color.holo_green_dark));
        viewHolder.itemView.setAlpha(1);
        viewHolder.itemView.setScaleX(1);
    }
}
