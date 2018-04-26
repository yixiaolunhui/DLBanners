package com.lib.dlbanner.listener;

import android.view.View;

import com.lib.dlbanner.DLBanners;

/**
 * @author zwl
 * @describe TODO
 * @date on 2018/4/26
 */
public interface OnItemClickListener<T> {
    void onItemClick(DLBanners dlBanners, View view, int position, T data);
}
