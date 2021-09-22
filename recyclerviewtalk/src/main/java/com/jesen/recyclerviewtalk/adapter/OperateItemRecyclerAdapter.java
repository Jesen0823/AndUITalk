package com.jesen.recyclerviewtalk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jesen.recyclerviewtalk.R;

import java.util.ArrayList;

public class OperateItemRecyclerAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<String> mDatas;
    private OnItemDragListener mOnItemDragListener;

    public OperateItemRecyclerAdapter(Context context, ArrayList<String> datas){
        mContext = context;
        mDatas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new NormalHolder(inflater.inflate(R.layout.oprate_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final NormalHolder normalHolder = (NormalHolder) holder;
        normalHolder.mTv.setText(mDatas.get(position));
        normalHolder.mBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    if (mOnItemDragListener != null) {
                        mOnItemDragListener.onDrag(normalHolder);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class NormalHolder extends RecyclerView.ViewHolder{

        public TextView mTv;
        public ImageView mBtn;

        public NormalHolder(@NonNull View itemView) {
            super(itemView);

            mTv = itemView.findViewById(R.id.item_tv);
            mBtn = itemView.findViewById(R.id.item_drag_img);
            mTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, mTv.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void setOnItemDragListener(OnItemDragListener onItemDragListener) {
        this.mOnItemDragListener = onItemDragListener;
    }
}
