package com.lib.dlbanner.adapter;

import android.content.Context;
import android.view.View;

import com.lib.dlbanner.DLBanners;

/**
 * banner适配器
 * Created by dl on 2018/3/25.
 */

public interface DLBaseAdapter<T> {
    View getView(DLBanners lBanners, Context context, int position, T data);
}
