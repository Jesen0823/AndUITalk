package com.jesen.recyclerviewtalk.custom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class OperateItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    private ArrayList<String> mDatas;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    public OperateItemTouchHelperCallBack(ArrayList<String> datas,
                                          RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        mDatas = datas;
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // dragFlags = 0 则禁止上下拖动
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

        // swipeFlags = 0 禁止左右滑动
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        int flags = makeMovementFlags(dragFlags, swipeFlags);
        return flags;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        Collections.swap(mDatas, fromPosition, toPosition);
        mAdapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mDatas.remove(viewHolder.getAdapterPosition());
        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);

        if (viewHolder == null || viewHolder.itemView == null) {
            return;
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            int bgColor = viewHolder.itemView.getContext().getResources().getColor(android.R.color.holo_orange_light);
            viewHolder.itemView.setBackgroundColor(bgColor);
        } else if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            int bgColor = viewHolder.itemView.getContext().getResources().getColor(android.R.color.holo_blue_bright);
            viewHolder.itemView.setBackgroundColor(bgColor);
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
    }

    /**
     * 禁止长按view拖拽
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    /**
     * 禁止滑动
     *
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }
}
