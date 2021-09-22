package com.jesen.recyclerviewtalk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jesen.recyclerviewtalk.R;

import java.util.ArrayList;

public class CoverFlowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mDatas;
    private int mCreatedHolder = 0;
    private int[] mPics = {R.drawable.item1, R.drawable.item2, R.drawable.item3, R.drawable.item4,
            R.drawable.item5, R.drawable.item6};

    public CoverFlowAdapter(Context context, ArrayList<String> datas) {
        mContext = context;
        mDatas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mCreatedHolder++;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new NormalHolder(inflater.inflate(R.layout.item_coverflow, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NormalHolder normalHolder = (NormalHolder) holder;
        normalHolder.mTv.setText(mDatas.get(position));
        normalHolder.mImg.setImageDrawable(mContext.getResources().getDrawable(mPics[position%mPics.length]));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class NormalHolder extends RecyclerView.ViewHolder {
        public TextView mTv;
        public ImageView mImg;

        public NormalHolder(@NonNull View itemView) {
            super(itemView);

            mTv = itemView.findViewById(R.id.text);
            mTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, mTv.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            mImg = itemView.findViewById(R.id.img);
            mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, mTv.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
