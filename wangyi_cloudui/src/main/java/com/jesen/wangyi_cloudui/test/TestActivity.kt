package com.jesen.wangyi_cloudui.test

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import android.os.Build.VERSION_CODES.O
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.jesen.wangyi_cloudui.R


class TestActivity : AppCompatActivity() {
    private lateinit var toolBar: Toolbar

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = getColor(R.color.purple_200);

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_test)

        initView()
    }

    private fun initView() {
        toolBar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolBar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolBar.overflowIcon?.setTint(getColor(R.color.white))
        } else {
            toolBar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.more)
        }
        val root = findViewById<ViewGroup>(android.R.id.content)
        root.getChildAt(O)?.fitsSystemWindows = true
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreatePanelMenu(featureId, menu)
    }
}