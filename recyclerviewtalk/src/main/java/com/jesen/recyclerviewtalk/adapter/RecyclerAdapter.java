package com.jesen.recyclerviewtalk.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jesen.recyclerviewtalk.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "LinearActivity|RecyclerAdapter";
    private final Context mContext;
    private final ArrayList<String> mDatas;
    private int mCreatedHolder=0;

    public RecyclerAdapter(Context context, ArrayList<String> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mCreatedHolder++;
        Log.d(TAG, "onCreateViewHolder num:"+mCreatedHolder);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new NormalHolder(inflater.inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        NormalHolder normalHolder = (NormalHolder) holder;
        normalHolder.mTV.setText(mDatas.get(position));
    }



    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class NormalHolder extends RecyclerView.ViewHolder {
        public TextView mTV;

        public NormalHolder(View itemView) {
            super(itemView);

            mTV = itemView.findViewById(R.id.item_tv);
            mTV.setOnClickListener(v -> Toast.makeText(mContext, mTV.getText(), Toast.LENGTH_SHORT).show());
        }
    }
}
