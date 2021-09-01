package com.jesen.custom_view.taobaovlay.my;

import android.view.View;

import java.util.List;

public abstract class MarqueeAdapter<T> {

    protected List<T> dataList;
    private OnDataChangedListener mOnDataChangedListener;

    public MarqueeAdapter() {

    }

    public void setData(List<T> datas) {
        this.dataList = datas;
        notifyDataChanged();
    }

    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public abstract View onCreateView(MarqueeView parent);

    public abstract void onBindView(View parent, View view, int position);

    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        mOnDataChangedListener = onDataChangedListener;
    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null) {
            mOnDataChangedListener.onChanged();
        }
    }

    public interface OnDataChangedListener {
        void onChanged();
    }
}
