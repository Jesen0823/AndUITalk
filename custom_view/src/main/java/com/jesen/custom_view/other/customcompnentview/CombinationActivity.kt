package com.jesen.custom_view.other.customcompnentview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.jesen.custom_view.R

class CombinationActivity : AppCompatActivity() {

    private lateinit var titleBar:CombTitleBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combination)

        titleBar = findViewById(R.id.title_bar)

        titleBar.setLeftClickListener(View.OnClickListener {
            ToastUtils.showShort("back")
        })
        titleBar.setRightClickListener(View.OnClickListener {
            ToastUtils.showShort("filter")
        })
    }
}