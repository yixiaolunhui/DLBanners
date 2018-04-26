package com.lib.dlbanner.listener;

import android.view.View;

import com.lib.dlbanner.DLBanners;

/**
 * @author zwl
 * @describe TODO
 * @date on 2018/4/26
 */
public interface OnItemLongClickListener<T> {
    void onItemLongClick(DLBanners dlBanners, View view, int position, T data);
}
