package com.lib.dlbanner.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by dl on 2018/3/25.
 */

public class DLBannerViewPager extends DLViewPager {

    public DLBannerViewPager(Context context) {
        super(context);
    }

    public DLBannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (listener != null) {
                    listener.onTouchDown();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (listener != null) {
                    listener.onTouchUp();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private OnViewPagerTouchEvent listener;

    public void setOnViewPagerTouchEventListener(OnViewPagerTouchEvent l) {
        listener = l;
    }

    public interface OnViewPagerTouchEvent {
        void onTouchDown();

        void onTouchUp();
    }
}
