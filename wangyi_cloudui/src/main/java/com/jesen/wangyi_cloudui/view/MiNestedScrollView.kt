package com.jesen.wangyi_cloudui.view

import android.content.Context
import androidx.core.widget.NestedScrollView

class MiNestedScrollView @JvmOverloads constructor(context: Context): NestedScrollView(context) {

    private  var scrollListener: ScrollInterface? = null


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        scrollListener?.onScrollChange(l,t,oldl,oldt)
        super.onScrollChanged(l, t, oldl, oldt)
    }

    fun setOnScrollListener(o: ScrollInterface){
        this.scrollListener = o
    }

    interface ScrollInterface{
        fun onScrollChange(scrollX:Int, scrollY:Int,oldScrollX:Int, oldScrollY:Int)
    }
}