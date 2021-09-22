package com.jesen.recyclerviewtalk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jesen.recyclerviewtalk.R;

import java.util.ArrayList;

public class StaggeredRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mDatas;
    private ArrayList<Integer> mHeights = new ArrayList<>();

    public StaggeredRecyclerAdapter(Context context, ArrayList<String> datas) {
        mContext = context;
        mDatas = datas;

        if (mDatas.size() > 0) {
            for (int i = 0; i < mDatas.size(); i++) {
                mHeights.add(getRandomHeight());
            }
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new NormalHolder(inflater.inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NormalHolder normalHolder = (NormalHolder) holder;
        normalHolder.mTv.setText(mDatas.get(position));

        ViewGroup.LayoutParams lp = normalHolder.mTv.getLayoutParams();
        lp.height = mHeights.get(position);
        normalHolder.mTv.setLayoutParams(lp);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private int getRandomHeight(){
        int randomHeight = 0;
        do {
            randomHeight = (int)(Math.random() * 300);
        }while (randomHeight ==0);
        return randomHeight;
    }

    public class NormalHolder extends RecyclerView.ViewHolder{
        public TextView mTv;

        public NormalHolder(View itemView){
            super(itemView);

            mTv = itemView.findViewById(R.id.item_tv);
            mTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, mTv.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
