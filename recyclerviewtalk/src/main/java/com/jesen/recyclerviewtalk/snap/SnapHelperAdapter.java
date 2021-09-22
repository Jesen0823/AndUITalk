package com.jesen.recyclerviewtalk.snap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jesen.recyclerviewtalk.R;

import java.util.ArrayList;

public class SnapHelperAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<String> mDatas;
    private boolean isFull = false;
    private int [] mPics = {R.drawable.item1,R.drawable.item2,R.drawable.item3,R.drawable.item4,
            R.drawable.item5,R.drawable.item6};

    // 是否全屏，仅仅在PagerSnapActivity中用到
    public void setFull(boolean isFull){
        this.isFull = isFull;
    }

    public SnapHelperAdapter(Context context, ArrayList<String> datas) {
        mContext = context;
        mDatas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (isFull){
            return new NormalHolder(inflater.inflate(R.layout.snap_helper_vertical_item_full_layout,parent,false));
        }else {
            return new NormalHolder(inflater.inflate(R.layout.snap_helper_horizontal_item_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final NormalHolder normalHolder = (NormalHolder) holder;
        normalHolder.mIv.setImageDrawable(mContext.getResources().getDrawable(mPics[position%mPics.length]));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class NormalHolder extends RecyclerView.ViewHolder{

        public ImageView mIv;

        public NormalHolder(@NonNull View itemView) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }
}










