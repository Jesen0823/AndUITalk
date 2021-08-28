package com.jesen.wangyi_cloudui.view

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.widget.LinearLayout
import java.lang.Exception


object StatusBarUtil {

    // 设置状态栏透明
    fun setTranslateStateBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            )
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置透明状态栏,这样才能让 ContentView 向上
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    fun setStateBar(activity: Activity, toolBar: View?) {
        setTranslateStateBar(activity)
        val decorView = activity.window.decorView as ViewGroup
        val count = decorView.childCount
        //判断是否已经添加了statusBarView
        if (count > 0 && decorView.getChildAt(count - 1) is StatusBarView) {
            decorView.getChildAt(count - 1).setBackgroundColor(Color.argb(0, 0, 0, 0))
            return
        }
        //        decoeView  View    toolBar  改变 margin
        // 绘制一个和状态栏一样高的矩形
        val statusBarView = StatusBarView(activity)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, getSystemBarHeight(activity)
        )
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(Color.argb(0, 0, 0, 0))
        decorView.addView(statusBarView)
        toolBar?.let {
            val layoutParams = it.layoutParams as MarginLayoutParams
            layoutParams.setMargins(0, getSystemBarHeight(activity), 0, 0)
        }
    }

    // 获取状态栏高度
    fun getSystemBarHeight(context: Context): Int {
        return getValue(context, "com.android.internal.R\$dimen", "system_bar_height", 48)
    }

    private fun getValue(
        context: Context,
        dimeClass: String,
        system_bar_height: String,
        defaultValue: Int
    ): Int {
        // com.android.internal.R$dimen.system_bar_height   状态栏的高度
        try {
            val clz = Class.forName(dimeClass)
            val `object` = clz.newInstance()
            val field = clz.getField(system_bar_height)
            val id = field[`object`].toString().toInt()
            return context.resources.getDimensionPixelSize(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defaultValue
    }
}
