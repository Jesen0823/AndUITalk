package com.jesen.ui3d.util

import android.content.Context
import android.content.Intent
import android.util.Log

object Utils {

    inline fun <reified T> simpleStartActivity(context: Context, block: Intent.() -> Unit) {
        Log.d("3D", "simpleStartActivity, activity name :${T::class.java.simpleName}")
        val intent = Intent(context, T::class.java)
        intent.block()
        context.startActivity(intent)
    }
}