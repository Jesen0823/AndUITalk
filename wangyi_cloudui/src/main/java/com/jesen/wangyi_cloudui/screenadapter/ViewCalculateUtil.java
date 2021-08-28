package com.jesen.wangyi_cloudui.screenadapter;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 缩放工具类的方式
 * 缺点;要对每一个界面元素赋值，任务量大
 */
public class ViewCalculateUtil {

    /**
     * 设置元素的LayoutParams为缩放后的尺寸
     */
    public static void setViewLayoutParam(View view, int width, int height, int topMargin,
                                          int bottomMargin, int lefMargin, int rightMargin) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams != null) {
            // 如果是指定大小，则直接缩放宽度
            if (width != RelativeLayout.LayoutParams.MATCH_PARENT
                    && width != RelativeLayout.LayoutParams.WRAP_CONTENT
                    && width != RelativeLayout.LayoutParams.FILL_PARENT) {
                layoutParams.width = UiUtil.getInstance().getWidth(width);
            } else {
                layoutParams.width = width;
            }
            if (height != RelativeLayout.LayoutParams.MATCH_PARENT
                    && height != RelativeLayout.LayoutParams.WRAP_CONTENT
                    && height != RelativeLayout.LayoutParams.FILL_PARENT) {
                layoutParams.height = UiUtil.getInstance().getHeight(height);
            } else {
                layoutParams.height = height;
            }

            layoutParams.topMargin = UiUtil.getInstance().getHeight(topMargin);
            layoutParams.bottomMargin = UiUtil.getInstance().getHeight(bottomMargin);
            layoutParams.leftMargin = UiUtil.getInstance().getWidth(lefMargin);
            layoutParams.rightMargin = UiUtil.getInstance().getWidth(rightMargin);
            view.setLayoutParams(layoutParams);
        }
    }

    public static void setTextSize(TextView view, int size) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, UiUtil.getInstance().getHeight(size));
    }

    public static void setViewLinearLayoutParam(View view, int width, int height) {

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (width != RelativeLayout.LayoutParams.MATCH_PARENT
                && width != RelativeLayout.LayoutParams.WRAP_CONTENT
                && width != RelativeLayout.LayoutParams.FILL_PARENT) {
            layoutParams.width = UiUtil.getInstance().getWidth(width);
        } else {
            layoutParams.width = width;
        }
        if (height != RelativeLayout.LayoutParams.MATCH_PARENT
                && height != RelativeLayout.LayoutParams.WRAP_CONTENT
                && height != RelativeLayout.LayoutParams.FILL_PARENT) {
            layoutParams.height = UiUtil.getInstance().getHeight(height);
        } else {
            layoutParams.height = height;
        }

        view.setLayoutParams(layoutParams);
    }

    public static void setViewGroupLayoutParam(View view, int width, int height) {

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (width != RelativeLayout.LayoutParams.MATCH_PARENT
                && width != RelativeLayout.LayoutParams.WRAP_CONTENT
                && width != RelativeLayout.LayoutParams.FILL_PARENT) {
            layoutParams.width = UiUtil.getInstance().getWidth(width);
        } else {
            layoutParams.width = width;
        }
        if (height != RelativeLayout.LayoutParams.MATCH_PARENT
                && height != RelativeLayout.LayoutParams.WRAP_CONTENT
                && height != RelativeLayout.LayoutParams.FILL_PARENT) {
            layoutParams.height = UiUtil.getInstance().getHeight(height);
        } else {
            layoutParams.height = height;
        }
        view.setLayoutParams(layoutParams);
    }

    /**
     * 设置LinearLayout中 view的高度宽度
     *
     * @param view
     * @param width
     * @param height
     */
    public static void setViewLinearLayoutParam(View view, int width, int height, int topMargin,
                                                int bottomMargin, int lefMargin, int rightMargin) {

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (width != RelativeLayout.LayoutParams.MATCH_PARENT
                && width != RelativeLayout.LayoutParams.WRAP_CONTENT
                && width != RelativeLayout.LayoutParams.FILL_PARENT) {
            layoutParams.width = UiUtil.getInstance().getWidth(width);
        } else {
            layoutParams.width = width;
        }
        if (height != RelativeLayout.LayoutParams.MATCH_PARENT
                && height != RelativeLayout.LayoutParams.WRAP_CONTENT
                && height != RelativeLayout.LayoutParams.FILL_PARENT) {
            layoutParams.height = UiUtil.getInstance().getHeight(height);
        } else {
            layoutParams.height = height;
        }

        layoutParams.topMargin = UiUtil.getInstance().getHeight(topMargin);
        layoutParams.bottomMargin = UiUtil.getInstance().getHeight(bottomMargin);
        layoutParams.leftMargin = UiUtil.getInstance().getWidth(lefMargin);
        layoutParams.rightMargin = UiUtil.getInstance().getWidth(rightMargin);
        view.setLayoutParams(layoutParams);
    }
}
