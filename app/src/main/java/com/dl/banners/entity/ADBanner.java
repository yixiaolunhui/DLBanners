package com.dl.banners.entity;

/**
 * Created by dl on 2018/3/26.
 */

public class ADBanner {

    public int type;//0图片   1view
    public String imgUrl;

    public boolean isView() {
        return type == 1;
    }
}
