package com.jesen.custom_view.customrecyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jesen.custom_view.R;

public class RecyclerViewActivity extends AppCompatActivity {
    private static final String TAG = "wangyi";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        recyclerView = findViewById(R.id.table);
        recyclerView.setAdapter(new RecyclerView.Adapter() {

            @Override
            public View onCreateViewHodler(int position, View convertView, ViewGroup parent) {
                convertView = getLayoutInflater().inflate(R.layout.item_table, parent, false);
                TextView textView = (TextView) convertView.findViewById(R.id.text1);
                textView.setText("网易课堂 " + position);
                Log.i(TAG, "onCreateViewHodler: " + convertView.hashCode());
                return convertView;
            }

            @Override
            public View onBinderViewHodler(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) convertView.findViewById(R.id.text1);
                textView.setText("网易课堂 " + position);
                Log.i(TAG, "onBinderViewHodler: " + convertView.hashCode());
                return convertView;
            }

            @Override
            public int getItemViewType(int row) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public int getCount() {
                return 30;
            }

            @Override
            public int getHeight(int index) {
                return 100;
            }
        });
    }
}
