package com.lib.dlbanner.listener;

import android.view.MotionEvent;
import android.view.View;

import com.lib.dlbanner.DLBanners;

/**
 * @author zwl
 * @describe TODO
 * @date on 2018/4/26
 */
public interface OnItemTouchListener<T> {
    boolean onItemTouch(DLBanners dlBanners, View view, MotionEvent event, int position, T data);
}
