package com.lib.dlbanner.transformer;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by dl on 2018/3/25.
 */

public class DLZoomPageTransformer extends DLPageTransformer {

    private float mMinScale = 0.80f;
    private float mMinAlpha = 1.0f;

    public DLZoomPageTransformer() {
    }

    public DLZoomPageTransformer(float minAlpha, float minScale) {
        setMinAlpha(minAlpha);
        setMinScale(minScale);
    }


    @Override
    public void onScrollInvisible(View view, float position) {
        ViewHelper.setAlpha(view, 0);
    }

    @Override
    public void onScrollLeft(View view, float position) {
        float scale = Math.max(mMinScale, 1 + position);
        float vertMargin = view.getHeight() * (1 - scale) / 2;
        float horzMargin = view.getWidth() * (1 - scale) / 2;
        ViewHelper.setTranslationX(view, horzMargin - vertMargin / 2);
        ViewHelper.setScaleX(view, scale);
        ViewHelper.setScaleY(view, scale);
        ViewHelper.setAlpha(view, mMinAlpha + (scale - mMinScale) / (1 - mMinScale) * (1 - mMinAlpha));
    }

    @Override
    public void onScrollRight(View view, float position) {
        float scale = Math.max(mMinScale, 1 - position);
        float vertMargin = view.getHeight() * (1 - scale) / 2;
        float horzMargin = view.getWidth() * (1 - scale) / 2;
        ViewHelper.setTranslationX(view, -horzMargin + vertMargin / 2);
        ViewHelper.setScaleX(view, scale);
        ViewHelper.setScaleY(view, scale);
        ViewHelper.setAlpha(view, mMinAlpha + (scale - mMinScale) / (1 - mMinScale) * (1 - mMinAlpha));
    }

    /**
     * 设置最小透明度
     *
     * @param minAlpha
     */
    public void setMinAlpha(float minAlpha) {
        if (minAlpha >= 0.6f && minAlpha <= 1.0f) {
            mMinAlpha = minAlpha;
        }
    }

    /**
     * 设置最小缩放值
     *
     * @param minScale
     */
    public void setMinScale(float minScale) {
        if (minScale >= 0.6f && minScale <= 1.0f) {
            mMinScale = minScale;
        }
    }

}
