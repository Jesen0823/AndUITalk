package com.jesen.recyclerviewtalk.qqdelete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jesen.recyclerviewtalk.R;
import com.jesen.recyclerviewtalk.qqdelete.lirary.Extension;

import java.util.ArrayList;

public class QQDeleteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   private Context mContext;
   private ArrayList<String> mDatas;
   private OnBtnClickListener mOnBtnClickListener;

   public QQDeleteAdapter(Context context, ArrayList<String> datas){
       mContext = context;
       mDatas = datas;
   }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new NormalHolder(inflater.inflate(R.layout.qq_delete_item_layout, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final NormalHolder normalHolder = (NormalHolder) holder;
        normalHolder.mItemText.setText(mDatas.get(position));
        if (mOnBtnClickListener != null){
            normalHolder.mDeleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnBtnClickListener.onDelete(normalHolder);
                }
            });

            normalHolder.mRefreshTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnBtnClickListener.onRefresh(normalHolder);
                }
            });
        }
    }

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        this.mOnBtnClickListener = onBtnClickListener;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 使用ItemTouchHelperExtension的实现代码
     */
    public class NormalHolder extends RecyclerView.ViewHolder implements Extension {
        public TextView mRefreshTv;
        public TextView mDeleteTv;
        public TextView mItemText;
        public LinearLayout mActionRoot;

        public NormalHolder(View itemView) {
            super(itemView);
            mItemText = (TextView)itemView.findViewById(R.id.operate_tv);
            mDeleteTv = (TextView)itemView.findViewById(R.id.operate_delete);
            mRefreshTv = (TextView)itemView.findViewById(R.id.operate_refresh);
            mActionRoot = (LinearLayout)itemView.findViewById(R.id.view_list_repo_action_container);
        }

        @Override
        public float getActionWidth() {
            return mActionRoot.getWidth();
        }
    }
}
