package com.jesen.custom_view.other.customcompnentview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.jesen.custom_view.R

class CombTitleBar @JvmOverloads constructor(context: Context,
                                             attrs: AttributeSet? = null,
                                             defStyleAttr: Int = 0
) :RelativeLayout(context, attrs, defStyleAttr) {
    private var mLayout:RelativeLayout
    private var mLeftIv:AppCompatImageView
    private var mMidTv:AppCompatTextView
    private var mRightIv:AppCompatImageView

    private var mBackgroundColor = Color.GRAY
    private var mTextColor = Color.WHITE
    private var mIconTint = Color.GRAY
    private var mTitleText = ""

    init {
        LayoutInflater.from(context).inflate(R.layout.combination_title_bar,this,true)

        val typeArray = context.obtainStyledAttributes(attrs,R.styleable.CombTitleBar)
        mTextColor = typeArray.getColor(R.styleable.CombTitleBar_title_color,Color.WHITE)
        mIconTint = typeArray.getColor(R.styleable.CombTitleBar_icon_tint,Color.WHITE)
        mTitleText = typeArray.getString(R.styleable.CombTitleBar_title_text)?:""
        mBackgroundColor = typeArray.getColor(R.styleable.CombTitleBar_title_background_color,Color.GRAY)
        typeArray.recycle()


        mLayout = findViewById(R.id.title_bar_root)
        mLeftIv = findViewById(R.id.iv_left)
        mMidTv = findViewById(R.id.mid_tv)
        mRightIv = findViewById(R.id.iv_right)

        mLayout.setBackgroundColor(mBackgroundColor)
        mLeftIv.setColorFilter(mIconTint)
        mRightIv.setColorFilter(mIconTint)
        mMidTv.setTextColor(mTextColor)
        mMidTv.text = mTitleText
    }

    fun setTitle(title:String){
        mMidTv.text = title
    }

    fun setLeftClickListener(onClickListener: OnClickListener){
        mLeftIv.setOnClickListener(onClickListener)
    }

    fun setRightClickListener(onClickListener: OnClickListener){
        mRightIv.setOnClickListener(onClickListener)
    }



}