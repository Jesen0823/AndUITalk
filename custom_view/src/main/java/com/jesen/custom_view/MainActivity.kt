package com.jesen.custom_view

import android.os.Bundle
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    lateinit var fatingBar :SimpleRatingBar
    lateinit var newScoreSelector:SimpleRatingBar
    lateinit var myScoreTitle:TextView
    private var score = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fatingBar = findViewById(R.id.video_srb_new_score_current_scorer)
        myScoreTitle = findViewById(R.id.video_tv_new_score_my_score)

        val videoScore: Float = BigDecimal("8")
            .setScale(0, BigDecimal.ROUND_HALF_UP)
            .divide(BigDecimal(2), 1, BigDecimal.ROUND_HALF_UP)
            .toFloat()

        fatingBar.setRating(videoScore)

        newScoreSelector = findViewById(R.id.video_srb_new_score_selector)
        newScoreSelector.setStarSize(40F, Dimension.DP)
        newScoreSelector.setBottomArray(R.array.video_score_bottom_array)
        if (!StringUtils.isEmpty("4")) {
            score = BigDecimal("4")
                .setScale(0, BigDecimal.ROUND_HALF_UP)
                .divide(BigDecimal(2), 1, BigDecimal.ROUND_HALF_UP)
                .toFloat()
        }
        newScoreSelector.rating = score
        newScoreSelector.setOnRatingBarChangeListener { simpleRatingBar, rating, fromUser ->
            score = rating
            ToastUtils.showShort("评分：$score")
        }
    }
}