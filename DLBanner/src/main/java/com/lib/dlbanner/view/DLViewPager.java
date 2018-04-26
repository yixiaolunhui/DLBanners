package com.lib.dlbanner.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by dl on 2018/3/25.
 */

public class DLViewPager extends ViewPager {
    private boolean isScrollEnabled = true;

    public DLViewPager(Context context) {
        super(context);
    }

    public DLViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isScrollEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isScrollEnabled && super.onInterceptTouchEvent(event);

    }

    /**
     * 设置是否可以滚动
     *
     * @param enabled
     */
    public void setScrollEnabled(boolean enabled) {
        this.isScrollEnabled = enabled;
    }
}
