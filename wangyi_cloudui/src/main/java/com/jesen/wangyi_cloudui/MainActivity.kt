package com.jesen.wangyi_cloudui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import android.os.Build.VERSION_CODES.O
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.jesen.wangyi_cloudui.screenadapter.UiUtil
import com.jesen.wangyi_cloudui.view.MiNestedScrollView
import com.jesen.wangyi_cloudui.view.StatusBarUtil
import android.R
import android.widget.RelativeLayout
import java.lang.Exception
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesen.wangyi_cloudui.screenadapter.ViewCalculateUtil


class MainActivity : AppCompatActivity() {
    private lateinit var toolBar: Toolbar
    // 当前滑动距离
    private var slidingDistance = 0
    private lateinit var toolBarBg:ImageView
    private lateinit var recycler:RecyclerView
    private lateinit var headerContainLayout:LinearLayout
    private lateinit var musicLogo:ImageView
    private lateinit var headerImage:ImageView
    private lateinit var miNestedScrollView: MiNestedScrollView
    private lateinit var header_image_item:ImageView
    private lateinit var header_bg:ImageView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        UiUtil.getInstance(this)

        initView()
        notifyData()
        postImage()
        initScroolViewListener()
    }

    private fun initView() {
        toolBar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolBar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolBar.overflowIcon?.setTint(getColor(R.color.white))
        } else {
            toolBar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.more)
        }
        toolBarBg = findViewById(R.id.toolbar_bg);
        header_bg = findViewById(R.id.header_bg);
        recycler = findViewById(R.id.music_recycler);
        miNestedScrollView = findViewById(R.id.nsv_scrollview);
        musicLogo = findViewById(R.id.header_music_log);
         val lv_header_detail = findViewById(R.id.lv_header_detail);
        val rv_header_container = findViewById(R.id.rv_header_container);
        headerContainLayout = findViewById(R.id.lv_header_contail);
        header_image_item = findViewById(R.id.header_image_item);

        ViewCalculateUtil.setViewLayoutParam(toolbar, 1080, 164, 0, 0, 0, 0);
        ViewCalculateUtil.setViewLinearLayoutParam(rv_header_container,1080,770,164,0,0,0);
        ViewCalculateUtil.setViewLayoutParam(toolbar,1080, 164, 0, 0, 0, 0);
        ViewCalculateUtil.setViewLayoutParam(toolbar_bg,1080,164+UIUtils.getInstance().getSystemBarHeight(this),0,0,0,0);
        ViewCalculateUtil.setViewLayoutParam(header_bg, 1080, 850, 0, 0, 0, 0);
        ViewCalculateUtil.setViewLayoutParam(lv_header_detail, 1080, 380, 72, 0, 52, 0);
        ViewCalculateUtil.setViewLinearLayoutParam(header_image_item,380,380);
        ViewCalculateUtil.setViewLayoutParam(header_music_log,60,60,59,0,52,0);

        StatusBarUtil.setStateBar(this,toolBar)
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreatePanelMenu(featureId, menu)
    }

    private fun notifyData() {
        val mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler.setLayoutManager(mLayoutManager)
        // 需加，不然滑动不流畅
        // 需加，不然滑动不流畅
        recycler.isNestedScrollingEnabled = false
        recycler.setHasFixedSize(false)
        val adapter = CloudAdapter(this)
        adapter.notifyDataSetChanged()
        recycler.adapter = adapter
    }

    private fun postImage() {
        Glide.with(this)
            .load(IMAGE_URL_MEDIUM)
            .listener(object : RequestListener<String?, GlideDrawable?>() {
                fun onException(
                    e: Exception,
                    model: String?,
                    target: Target<GlideDrawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.i("tuch", "onException: $e")
                    return false
                }

                fun onResourceReady(
                    resource: GlideDrawable?,
                    model: String?,
                    target: Target<GlideDrawable?>?,
                    isFromMemoryCache: Boolean,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.i("tuch", "onResourceReady: ")
                    return false
                }
            }).override(400, 400)
            .into(header_image_item)

        // "14":模糊度；"3":图片缩放3倍后再进行模糊

        // "14":模糊度；"3":图片缩放3倍后再进行模糊
        Glide.with(this)
            .load(IMAGE_URL_MEDIUM)
            .error(R.drawable.stackblur_default)
            .placeholder(R.drawable.stackblur_default)
            .crossFade(500)
            .bitmapTransform(BlurTransformation(this, 200, 3))
            .into(object : SimpleTarget<GlideDrawable?>() {
                fun onResourceReady(
                    resource: GlideDrawable?,
                    glideAnimation: GlideAnimation<in GlideDrawable?>?
                ) {
                    lv_header_contail.setBackground(resource)
                }
            })
        Glide.with(this).load(IMAGE_URL_MEDIUM)
            .error(R.drawable.stackblur_default)
            .bitmapTransform(BlurTransformation(this, 250, 6)) // 设置高斯模糊
            .listener(object : RequestListener<String?, GlideDrawable?>() {
                //监听加载状态
                fun onException(
                    e: Exception?,
                    model: String?,
                    target: Target<GlideDrawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                fun onResourceReady(
                    resource: GlideDrawable?,
                    model: String?,
                    target: Target<GlideDrawable?>?,
                    isFromMemoryCache: Boolean,
                    isFirstResource: Boolean
                ): Boolean {
                    toolbar.setBackgroundColor(Color.TRANSPARENT)
                    toolbar_bg.setImageAlpha(0)
                    toolbar_bg.setVisibility(View.VISIBLE)
                    return false
                }
            }).into(toolbar_bg)
    }

    private fun initScroolViewListener() {
        slidingDistance = UiUtil.getInstance().getHeight(490)
        miNestedScrollView.setOnScrollListener(object:MiNestedScrollView.ScrollInterface{
            override fun onScrollChange(
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                scrollChangeHeader(scrollY)
            }

        })
    }

    private fun scrollChangeHeader(scrollY: Int) {
        var scrollOf = scrollY
        if (scrollY <0){ scrollOf = 0}

        var alpha = Math.abs(scrollY).toFloat()/slidingDistance
        val drawable = toolBarBg.drawable
        if (scrollOf <= slidingDistance){
            drawable.mutate().alpha = (alpha*255) as Int
            toolBarBg.setImageDrawable(drawable)
        }else{
            drawable.mutate().alpha = 255
            toolBarBg.setImageDrawable(drawable)
        }
    }
}



