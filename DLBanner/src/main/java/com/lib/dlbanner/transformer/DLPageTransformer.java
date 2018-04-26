package com.lib.dlbanner.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by dl on 2018/3/25.
 */

public abstract class DLPageTransformer implements ViewPager.PageTransformer {

    /**
     * @param page
     * @param position -1 ：当前Page刚好滑出屏幕左侧
     *                 0 ：当前界面位于屏幕中心的时候
     *                 1 ：当前Page刚好滑出屏幕右侧
     */
    @Override
    public void transformPage(View page, float position) {
        /**
         * [-Infinity,-1)
         * On the left of the screen
         */
        if (position < -1.0f) {
            onScrollInvisible(page, position);
        }
        /**
         * [-1,0]
         * Use the default slide transition when moving to the left page
         */
        else if (position <= 0.0f) {
            onScrollLeft(page, position);
        }
        /**
         *  (0,1]
         */
        else if (position <= 1.0f) {
            onScrollRight(page, position);
        }
        /**
         *  (1,+Infinity]
         * This page is way off-screen to the right.
         */
        else {
            onScrollInvisible(page, position);
        }
    }

    public abstract void onScrollInvisible(View view, float position);

    /**
     * 向左滑动
     * @param view
     * @param position
     */
    public abstract void onScrollLeft(View view, float position);

    /**
     * 向右滑动
     * @param view
     * @param position
     */
    public abstract void onScrollRight(View view, float position);

    public static DLPageTransformer getPageTransformer(DLTransitionEffect effect) {
        switch (effect) {
            case Default:
                return new DLDefaultPageTransformer();
            case Zoom:
                return new DLZoomPageTransformer();
            default:
                return new DLDefaultPageTransformer();
        }
    }

}
