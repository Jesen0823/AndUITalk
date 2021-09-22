package com.jesen.recyclerviewtalk.custom;

import android.content.Context;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
/**
 * 为了改变展示Item的顺序，需要重写 getChildDrawingOrder()
 * 所以需要自定义继承 RecyclerView
 * */
public class RecyclerCoverFlowView extends RecyclerView {
    public RecyclerCoverFlowView(@NonNull Context context) {
        super(context);
        init();
    }

    public RecyclerCoverFlowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerCoverFlowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //开启绘制顺序重新排序
        setChildrenDrawingOrderEnabled(true);
    }

    /**
     * 获取LayoutManger，并强制转换为CoverFlowLayoutManger
     */
    public CoverFlowLayoutManager getCoverFlowLayout(){
        return (CoverFlowLayoutManager) getLayoutManager();
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int center = getCoverFlowLayout().getCenterPosition()
                - getCoverFlowLayout().getFirstVisiblePosition(); //计算正在显示的所有Item的中间位置
        int order;

        if (i == center) {
            order = childCount - 1;
        } else if (i > center) {
            order = center + childCount - 1 - i;
        } else {
            order = i;
        }
        return order;
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        //缩小滚动距离
        int flingX = (int) (velocityX * 0.40f);
        CoverFlowLayoutManager manger = getCoverFlowLayout();
        double distance = getSplineFlingDistance(flingX);
        double newDistance = manger.calculateDistance(velocityX,distance);
        int fixVelocityX = getVelocity(newDistance);
        if (velocityX > 0) {
            flingX = fixVelocityX;
        } else {
            flingX = -fixVelocityX;
        }
        return super.fling(flingX, velocityY);
    }


    /**
     * 根据松手后的滑动速度计算出fling的距离
     *
     * @param velocity
     * @return
     */
    private double getSplineFlingDistance(int velocity) {
        final double l = getSplineDeceleration(velocity);
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return mFlingFriction * getPhysicalCoeff() * Math.exp(DECELERATION_RATE / decelMinusOne * l);
    }

    /**
     * 根据距离计算出速度
     *
     * @param distance
     * @return
     */
    private int getVelocity(double distance) {
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        double aecel = Math.log(distance / (mFlingFriction * mPhysicalCoeff)) * decelMinusOne / DECELERATION_RATE;
        return Math.abs((int) (Math.exp(aecel) * (mFlingFriction * mPhysicalCoeff) / INFLEXION));
    }

    /**
     * --------------flling辅助类---------------
     */
    private static final float INFLEXION = 0.35f; // Tension lines cross at (INFLEXION, 1)
    private float mFlingFriction = ViewConfiguration.getScrollFriction();
    private static float DECELERATION_RATE = (float) (Math.log(0.78) / Math.log(0.9));
    private float mPhysicalCoeff = 0;

    private double getSplineDeceleration(int velocity) {
        final float ppi = this.getResources().getDisplayMetrics().density * 160.0f;
        float mPhysicalCoeff = SensorManager.GRAVITY_EARTH // g (m/s^2)
                * 39.37f // inch/meter
                * ppi
                * 0.84f; // look and feel tuning


        return Math.log(INFLEXION * Math.abs(velocity) / (mFlingFriction * mPhysicalCoeff));
    }

    private float getPhysicalCoeff() {
        if (mPhysicalCoeff == 0) {
            final float ppi = this.getResources().getDisplayMetrics().density * 160.0f;
            mPhysicalCoeff = SensorManager.GRAVITY_EARTH // g (m/s^2)
                    * 39.37f // inch/meter
                    * ppi
                    * 0.84f; // look and feel tuning
        }
        return mPhysicalCoeff;
    }
}
