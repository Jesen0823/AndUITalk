package com.jesen.custom_view.musicalbum.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.viewpager.widget.ViewPager;

import com.jesen.custom_view.R;
import com.jesen.custom_view.musicalbum.model.AudioData;
import com.jesen.custom_view.musicalbum.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

public class AlbumView extends RelativeLayout {

    public static final int DURATION_NEEDLE_ANIM = 500;
    private NeedleAnimStatus needleAnimStatus = NeedleAnimStatus.IN_FAR_END;

    private ImageView mIvNeedle;
    private ViewPager mVpContain;
    private ViewPagerAdapter mViewPagerAdapter;
    private ObjectAnimator mNeedleAnimator;

    private List<View> mAlbumLayouts = new ArrayList<>();
    private List<AudioData> mAudioDatas = new ArrayList<>();
    private List<ObjectAnimator> mAlbumAnimators = new ArrayList<>();

    // ViewPager是否处于偏移状态
    private boolean mViewPagerIsOffset = false;

    // 唱针复位后，是否需要重新偏移到唱片处
    private boolean mIsNeedStartPlayAnim = false;

    private AudioStatus audioStatus = AudioStatus.STOP;
    private IPlayInfo mPlayInfo;

    private int mScreenWidth, mScreenHeight;

    /**
     * 唱针当前所处的状态
     */
    private enum NeedleAnimStatus {
        /*移动时：从唱盘往远处移动*/
        TO_FAR_END,
        /*移动时：从远处往唱盘移动*/
        TO_NEAR_END,
        /*静止时：离开唱盘*/
        IN_FAR_END,
        /*静止时：贴近唱盘*/
        IN_NEAR_END
    }

    /**
     * 音乐当前的状态标记
     */
    public enum AudioStatus {
        PLAY, PAUSE, STOP
    }

    /**
     * AlbumView需要触发的音乐切换状态：播放、暂停、上/下一首、停止
     */
    public enum AudioChangedStatus {
        PLAY, PAUSE, NEXT, LAST, STOP
    }

    public interface IPlayInfo {
        /*用于更新标题栏变化*/
        public void onAudioInfoChanged(String musicName, String musicAuthor);

        /*用于更新背景图片*/
        public void onAudioPicChanged(int musicPicRes);

        /*用于更新音乐播放状态*/
        public void onAudioStatusChanged(AudioChangedStatus audioChangedStatus);
    }

    public void setPlayInfoListener(IPlayInfo listener) {
        this.mPlayInfo = listener;
    }

    public AlbumView(Context context) {
        this(context, null);
    }

    public AlbumView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlbumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = DisplayUtil.getScreenWidth(context);
        mScreenHeight = DisplayUtil.getScreenHeight(context);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        initAlbumBackground();
        initViewPager();
        initNeedle();
        initObjectAnimator();
    }

    private void initAlbumBackground() {
        ImageView mAlbumBackground = (ImageView) findViewById(R.id.ivAlbumBackground);
        mAlbumBackground.setImageDrawable(getAlbumBackgroundDrawable());

        int marginTop = (int) (DisplayUtil.SCALE_DISC_MARGIN_TOP * mScreenHeight);
        LayoutParams layoutParams = (LayoutParams) mAlbumBackground.getLayoutParams();
        layoutParams.setMargins(0, marginTop, 0, 0);

        mAlbumBackground.setLayoutParams(layoutParams);
    }

    /*得到唱盘背后半透明的圆形背景*/
    private Drawable getAlbumBackgroundDrawable() {
        int discSize = (int) (mScreenWidth * DisplayUtil.SCALE_DISC_SIZE);
        Bitmap bitmapDisc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R
                .drawable.ic_disc_blackground), discSize, discSize, false);
        RoundedBitmapDrawable roundDiscDrawable
                = RoundedBitmapDrawableFactory.create(getResources(), bitmapDisc);
        return roundDiscDrawable;
    }

    private void initViewPager() {
        mViewPagerAdapter = new ViewPagerAdapter(mAlbumLayouts);
        mVpContain = findViewById(R.id.vpAlbumContain);
        mVpContain.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mVpContain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int lastPositionOffsetPixels = 0;
            int currentItem = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 左滑
                if (lastPositionOffsetPixels > positionOffsetPixels) {
                    if (positionOffset < 0.5) {
                        notifyAudioInfoChanged(position);
                    } else {
                        notifyAudioInfoChanged(mVpContain.getCurrentItem());
                    }
                } else if (lastPositionOffsetPixels < positionOffsetPixels) {  // 右滑
                    if (positionOffset > 0.5) {
                        notifyAudioInfoChanged(position + 1);
                    } else {
                        notifyAudioInfoChanged(position);
                    }
                }
                lastPositionOffsetPixels = positionOffsetPixels;
            }

            @Override
            public void onPageSelected(int position) {
                resetOtherAlbumAnim(position);
                notifyAudioPicChanged(position);
                if (position > currentItem) {
                    notifyAudioStatusChanged(AudioChangedStatus.NEXT);
                } else {
                    notifyAudioStatusChanged(AudioChangedStatus.LAST);
                }
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                doWithAnimOnPageScroll(state);
            }
        });
        mVpContain.setAdapter(mViewPagerAdapter);

        LayoutParams layoutParams = (LayoutParams) mVpContain.getLayoutParams();
        int marginTop = (int) (DisplayUtil.SCALE_DISC_MARGIN_TOP * mScreenHeight);
        layoutParams.setMargins(0, marginTop, 0, 0);
        mVpContain.setLayoutParams(layoutParams);
    }

    private void initNeedle() {
        mIvNeedle = findViewById(R.id.ivNeedle);
        int needleWidth = (int) (DisplayUtil.SCALE_NEEDLE_WIDTH * mScreenWidth);
        int needleHeight = (int) (DisplayUtil.SCALE_NEEDLE_HEIGHT * mScreenHeight);
        // 设置唱针外边距为负数，隐藏一小部分
        int marginTop = (int) (DisplayUtil.SCALE_NEEDLE_MARGIN_TOP * mScreenHeight);
        int marginLeft = (int) (DisplayUtil.SCALE_NEEDLE_MARGIN_LEFT * mScreenWidth);

        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_needle);
        Bitmap bitmap = Bitmap.createScaledBitmap(originBitmap, needleWidth, needleHeight, false);

        LayoutParams layoutParams = (LayoutParams) mIvNeedle.getLayoutParams();
        layoutParams.setMargins(marginLeft, marginTop, 0, 0);

        int pivotX = (int) (DisplayUtil.SCALE_NEEDLE_PIVOT_X * mScreenWidth);
        int pivotY = (int) (DisplayUtil.SCALE_NEEDLE_PIVOT_Y * mScreenWidth);

        mIvNeedle.setPivotX(pivotX);
        mIvNeedle.setPivotY(pivotY);
        mIvNeedle.setRotation(DisplayUtil.ROTATION_INIT_NEEDLE);
        mIvNeedle.setImageBitmap(bitmap);
        mIvNeedle.setLayoutParams(layoutParams);
    }


    private void initObjectAnimator() {
        mNeedleAnimator = ObjectAnimator.ofFloat(mIvNeedle, View.ROTATION, DisplayUtil
                .ROTATION_INIT_NEEDLE, 0);
        mNeedleAnimator.setDuration(DURATION_NEEDLE_ANIM);
        mNeedleAnimator.setInterpolator(new AccelerateInterpolator());
        mNeedleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                /**
                 * 根据动画开始前NeedleAnimatorStatus的状态，
                 * 即可得出动画进行时NeedleAnimatorStatus的状态
                 * */
                if (needleAnimStatus == NeedleAnimStatus.IN_FAR_END) {
                    needleAnimStatus = NeedleAnimStatus.TO_NEAR_END;
                } else if (needleAnimStatus == NeedleAnimStatus.IN_NEAR_END) {
                    needleAnimStatus = NeedleAnimStatus.TO_FAR_END;
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (needleAnimStatus == NeedleAnimStatus.TO_NEAR_END) {
                    needleAnimStatus = NeedleAnimStatus.IN_NEAR_END;
                    int index = mVpContain.getCurrentItem();
                    playDiscAnimator(index);
                    audioStatus = AudioStatus.PLAY;
                } else if (needleAnimStatus == NeedleAnimStatus.TO_FAR_END) {
                    needleAnimStatus = NeedleAnimStatus.IN_FAR_END;
                    if (audioStatus == AudioStatus.STOP) {
                        mIsNeedStartPlayAnim = true;
                    }
                }

                if (mIsNeedStartPlayAnim) {
                    mIsNeedStartPlayAnim = false;
                    /**
                     * 只有在ViewPager不处于偏移状态时，才开始唱盘旋转动画
                     * */
                    if (!mViewPagerIsOffset) {
                        /*延时500ms*/
                        AlbumView.this.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                playAnimator();
                            }
                        }, 50);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    public void notifyAudioInfoChanged(int position) {
        if (mPlayInfo != null) {
            AudioData audioData = mAudioDatas.get(position);
            mPlayInfo.onAudioInfoChanged(audioData.getAudioName(), audioData.getAudioAuthor());
        }
    }

    public void notifyAudioPicChanged(int position) {
        if (mPlayInfo != null) {
            AudioData audioData = mAudioDatas.get(position);
            mPlayInfo.onAudioPicChanged(audioData.getAudioPicRes());
        }
    }

    public void notifyAudioStatusChanged(AudioChangedStatus status) {
        if (mPlayInfo != null) {
            mPlayInfo.onAudioStatusChanged(status);
        }
    }

    /**
     * 取消其他页面的动画，并将图片旋转角度复原
     */
    private void resetOtherAlbumAnim(int position) {
        for (int i = 0; i < mAlbumLayouts.size(); i++) {
            if (position == i) continue;
            mAlbumAnimators.get(position).cancel();
            ImageView imageView = mAlbumLayouts.get(i).findViewById(R.id.ivAlbum);
            imageView.setRotation(0);
        }
    }

    private void doWithAnimOnPageScroll(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
            case ViewPager.SCROLL_STATE_SETTLING:
                mViewPagerIsOffset = false;
                if (audioStatus == AudioStatus.PLAY) {
                    playAnimator();
                }
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                mViewPagerIsOffset = true;
                pauseAnimator();
                break;
            default:
        }
    }

    /**
     * 得到唱盘图片
     * 唱盘图片由空心圆盘及音乐专辑图片“合成”得到
     */
    private Drawable getAlbumDrawable(int musicPicRes) {
        int discSize = (int) (mScreenWidth * DisplayUtil.SCALE_DISC_SIZE);
        int musicPicSize = (int) (mScreenWidth * DisplayUtil.SCALE_MUSIC_PIC_SIZE);

        Bitmap bitmapDisc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R
                .drawable.ic_disc), discSize, discSize, false);
        Bitmap bitmapMusicPic = getAudioPicBitmap(musicPicSize, musicPicRes);
        BitmapDrawable albumDrawable = new BitmapDrawable(bitmapDisc);
        RoundedBitmapDrawable roundMusicDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), bitmapMusicPic);

        //抗锯齿
        albumDrawable.setAntiAlias(true);
        roundMusicDrawable.setAntiAlias(true);

        Drawable[] drawables = new Drawable[2];
        drawables[0] = roundMusicDrawable;
        drawables[1] = albumDrawable;

        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        int musicPicMargin = (int) ((DisplayUtil.SCALE_DISC_SIZE - DisplayUtil
                .SCALE_MUSIC_PIC_SIZE) * mScreenWidth / 2);
        //调整专辑图片的四周边距，让其显示在正中
        layerDrawable.setLayerInset(0, musicPicMargin, musicPicMargin, musicPicMargin,
                musicPicMargin);
        return layerDrawable;
    }

    private Bitmap getAudioPicBitmap(int audioPicSize, int audioPicRes) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), audioPicRes, options);
        int imageWidth = options.outWidth;

        int sample = imageWidth / audioPicSize;
        int dstSample = 1;
        if (sample > dstSample) {
            dstSample = sample;
        }
        options.inJustDecodeBounds = false;
        //设置图片采样率
        options.inSampleSize = dstSample;
        //设置图片解码格式
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                audioPicRes, options), audioPicSize, audioPicSize, true);
    }

    public void setAudioDataList(List<AudioData> musicDataList) {
        if (musicDataList.isEmpty()) return;

        mAlbumLayouts.clear();
        mAudioDatas.clear();
        mAlbumAnimators.clear();
        mAudioDatas.addAll(musicDataList);

        int i = 0;
        for (AudioData musicData : mAudioDatas) {
            View discLayout = LayoutInflater.from(getContext()).inflate(R.layout.layout_album,
                    mVpContain, false);
            ImageView disc = (ImageView) discLayout.findViewById(R.id.ivAlbum);
            disc.setImageDrawable(getAlbumDrawable(musicData.getAudioPicRes()));

            mAlbumAnimators.add(getDiscObjectAnimator(disc));
            mAlbumLayouts.add(discLayout);
        }
        mViewPagerAdapter.notifyDataSetChanged();

        AudioData audioData = mAudioDatas.get(0);
        if (mPlayInfo != null) {
            mPlayInfo.onAudioInfoChanged(audioData.getAudioName(), audioData.getAudioAuthor());
            mPlayInfo.onAudioPicChanged(audioData.getAudioPicRes());
        }
    }

    private ObjectAnimator getDiscObjectAnimator(ImageView disc) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(disc, View.ROTATION, 0, 360);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setDuration(20 * 1000);
        objectAnimator.setInterpolator(new LinearInterpolator());

        return objectAnimator;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /*播放动画*/
    private void playAnimator() {
        /*唱针处于远端时，直接播放动画*/
        if (needleAnimStatus == NeedleAnimStatus.IN_FAR_END) {
            mNeedleAnimator.start();
        }
        /*唱针处于往远端移动时，设置标记，等动画结束后再播放动画*/
        else if (needleAnimStatus == NeedleAnimStatus.TO_FAR_END) {
            mIsNeedStartPlayAnim = true;
        }
    }

    /*暂停动画*/
    private void pauseAnimator() {
        /*播放时暂停动画*/
        if (needleAnimStatus == NeedleAnimStatus.IN_NEAR_END) {
            int index = mVpContain.getCurrentItem();
            pauseDiscAnimatior(index);
        }
        /*唱针往唱盘移动时暂停动画*/
        else if (needleAnimStatus == NeedleAnimStatus.TO_NEAR_END) {
            mNeedleAnimator.reverse();
            /**
             * 若动画在没结束时执行reverse方法，则不会执行监听器的onStart方法，此时需要手动设置
             * */
            needleAnimStatus = NeedleAnimStatus.TO_FAR_END;
        }
        /**
         * 动画可能执行多次，只有音乐处于停止 / 暂停状态时，才执行暂停命令
         * */
        if (audioStatus == AudioStatus.STOP) {
            notifyAudioStatusChanged(AudioChangedStatus.STOP);
        } else if (audioStatus == AudioStatus.PAUSE) {
            notifyAudioStatusChanged(AudioChangedStatus.PAUSE);
        }
    }

    /*播放唱盘动画*/
    private void playDiscAnimator(int index) {
        ObjectAnimator objectAnimator = mAlbumAnimators.get(index);
        if (objectAnimator.isPaused()) {
            objectAnimator.resume();
        } else {
            objectAnimator.start();
        }
        /**
         * 唱盘动画可能执行多次，只有不是音乐不在播放状态，在回调执行播放
         * */
        if (audioStatus != AudioStatus.PLAY) {
            notifyAudioStatusChanged(AudioChangedStatus.PLAY);
        }
    }

    /*暂停唱盘动画*/
    private void pauseDiscAnimatior(int index) {
        ObjectAnimator objectAnimator = mAlbumAnimators.get(index);
        objectAnimator.pause();
        mNeedleAnimator.reverse();
    }

    private void play() {
        playAnimator();
    }

    private void pause() {
        audioStatus = AudioStatus.PAUSE;
        pauseAnimator();
    }

    public void stop() {
        audioStatus = AudioStatus.STOP;
        pauseAnimator();
    }

    public void playOrPause() {
        if (audioStatus == AudioStatus.PLAY) {
            pause();
        } else {
            play();
        }
    }

    public void next() {
        int currentItem = mVpContain.getCurrentItem();
        if (currentItem == mAudioDatas.size() - 1) {
            Toast.makeText(getContext(), "已经到达最后一首", Toast.LENGTH_SHORT).show();
        } else {
            selectMusicWithButton();
            mVpContain.setCurrentItem(currentItem + 1, true);
        }
    }

    public void last() {
        int currentItem = mVpContain.getCurrentItem();
        if (currentItem == 0) {
            Toast.makeText(getContext(), "已经到达第一首", Toast.LENGTH_SHORT).show();
        } else {
            selectMusicWithButton();
            mVpContain.setCurrentItem(currentItem - 1, true);
        }
    }

    public boolean isPlaying() {
        return audioStatus == AudioStatus.PLAY;
    }

    private void selectMusicWithButton() {
        if (audioStatus == AudioStatus.PLAY) {
            mIsNeedStartPlayAnim = true;
            pauseAnimator();
        } else if (audioStatus == AudioStatus.PAUSE) {
            play();
        }
    }
}