package com.dl.banners.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dl.banners.R;
import com.dl.banners.entity.ADBanner;
import com.lib.dlbanner.DLBanners;
import com.lib.dlbanner.adapter.DLBaseAdapter;

public class MyAdapter implements DLBaseAdapter<ADBanner> {
    private Context mContext;

    public MyAdapter(Context context) {
        mContext = context;
    }


    @Override
    public View getView(DLBanners lBanners, Context context, int position, ADBanner data) {
        if (data.isView()) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_view_banner, null);
            return view;
        } else {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_img_banner, null);
            final ImageView imageView = view.findViewById(R.id.id_image);
            Glide.with(mContext).load(data.imgUrl).into(imageView);
            return view;
        }

    }
}
