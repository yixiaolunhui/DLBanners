package com.lib.dlbanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lib.dlbanner.adapter.DLBaseAdapter;
import com.lib.dlbanner.listener.OnBannerPageScrollListener;
import com.lib.dlbanner.listener.OnItemClickListener;
import com.lib.dlbanner.listener.OnItemLongClickListener;
import com.lib.dlbanner.listener.OnItemTouchListener;
import com.lib.dlbanner.transformer.DLPageTransformer;
import com.lib.dlbanner.transformer.DLTransitionEffect;
import com.lib.dlbanner.utils.DLScreenUtils;
import com.lib.dlbanner.utils.DLViewPagerScroller;
import com.lib.dlbanner.view.DLBannerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dl on 2018/3/25.
 */

public class DLBanners<T> extends FrameLayout implements ViewPager.OnPageChangeListener {
    private static String TAG = DLBanners.class.getSimpleName();
    private List<T> mList = new ArrayList<>();
    private Context mContext;
    private boolean mCanLoop;
    private int mDurtion = 3000;
    private boolean mAutoPlay;
    private int mSlectIndicatorRes;
    private int mUnSlectIndicatorRes;
    private int mIndicatorBottomPadding;
    private float mIndicatorZoom;
    private int mIndicatorSize;
    private DLTransitionEffect mTransitionEffect;
    private IndicatorPos mIndicatorLocation;
    private DLBaseAdapter adapter;
    private RelativeLayout mBannerLayout;
    private LinearLayout mIndicatorLayout;
    private View mIndicatorView;
    private DLBannerViewPager mViewPager;
    private int currentPos;
    private int showCount;
    private int mTotalCount = 100;
    private ViewPagerAdapter mAdapter;
    private DLViewPagerScroller mScroller;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemTouchListener mOnItemTouchListener;
    private OnBannerPageScrollListener mOnBannerPageScrollListener;

    public DLBanners(@NonNull Context context) {
        this(context, null);
    }

    public DLBanners(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DLBanners(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.DLBanners, defStyleAttr, 0);
        mCanLoop = typedArray.getBoolean(R.styleable.DLBanners_DL_canLoop, true);
        mDurtion = typedArray.getInteger(R.styleable.DLBanners_DL_durtion, 3000);
        mAutoPlay = typedArray.getBoolean(R.styleable.DLBanners_DL_autoPlay, true);//默认可以自动播放
        mSlectIndicatorRes = typedArray.getResourceId(R.styleable.DLBanners_DL_indicator_unselect, R.drawable.dl_banner_indicator_select);
        mUnSlectIndicatorRes = typedArray.getResourceId(R.styleable.DLBanners_DL_indicator_unselect, R.drawable.dl_banner_indicator_unselect);
        mIndicatorBottomPadding = typedArray.getInt(R.styleable.DLBanners_DL_indicatorBottomPadding, 20);
        mIndicatorSize = typedArray.getInteger(R.styleable.DLBanners_DL_indicator_size, 30);//指示器大小
        mIndicatorZoom = typedArray.getFloat(R.styleable.DLBanners_DL_indicator_select_zoom, 1.0f);//指示器大小
        mTransitionEffect = DLTransitionEffect.values()[typedArray.getInt(R.styleable.DLBanners_DL_transitionEffect, DLTransitionEffect.Default.ordinal())];
        mIndicatorLocation = IndicatorPos.values()[typedArray.getInt(R.styleable.DLBanners_DL_indicatorPos, IndicatorPos.middle.ordinal())];
        typedArray.recycle();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        currentPos = position;
        if (mOnBannerPageScrollListener != null)
            mOnBannerPageScrollListener.onPageSelected(position % showCount);

        if (mIndicatorView == null) {
            int count = mIndicatorLayout.getChildCount();
            for (int i = 0; i < count; i++) {
                View view = mIndicatorLayout.getChildAt(i);
                if (position % showCount == i) {
                    view.setSelected(true);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (mIndicatorSize * mIndicatorZoom), (int) (mIndicatorSize * mIndicatorZoom));
                    params.setMargins(mIndicatorSize, 0, 0, 0);
                    view.setLayoutParams(params);
                    view.setBackgroundResource(mSlectIndicatorRes);

                } else {
                    view.setSelected(false);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorSize, mIndicatorSize);
                    params.setMargins(mIndicatorSize, 0, 0, 0);
                    view.setLayoutParams(params);
                    view.setBackgroundResource(mUnSlectIndicatorRes);

                }
            }
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 获取banner数据数量
     *
     * @return
     */
    public int getBannersCount() {
        return mList == null ? 0 : mList.size();
    }


    public enum IndicatorPos {
        middle,
        right
    }


    public void setAdapter(DLBaseAdapter adapter, List<T> list) {
        this.adapter = adapter;
        if (adapter != null) {
            this.mList = list;
            initBanners();
        }
    }

    private void initBanners() {
        mViewPager.setAdapter(null);
        mIndicatorLayout.removeAllViews();
        if (null == adapter) {
            return;
        }
        showCount = mList.size();
        if (showCount == mList.size()) {

        }
        if (showCount == 1) {
            mViewPager.setScrollEnabled(false);
        } else {
            mViewPager.setScrollEnabled(true);
        }
        for (int i = 0; i < showCount; i++) {
            View view = new View(mContext);
            if (currentPos == i) {
                view.setPressed(true);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (mIndicatorSize * mIndicatorZoom), (int) (mIndicatorSize * mIndicatorZoom));
                params.setMargins(mIndicatorSize, 0, 0, 0);
                view.setLayoutParams(params);
                view.setBackgroundResource(mSlectIndicatorRes);
            } else {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorSize, mIndicatorSize);
                params.setMargins(mIndicatorSize, 0, 0, 0);
                view.setLayoutParams(params);
                view.setBackgroundResource(mUnSlectIndicatorRes);
            }

            mIndicatorLayout.addView(view);
        }

        setCanLoop(mCanLoop);
        //设置滚动速率
        setScrollDurtion(2000);

        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        stopTimerTask();
                        break;
                    case MotionEvent.ACTION_UP:
                        startTimerTask();
                        break;
                    default:

                        break;
                }
                return false;
            }
        });
        startTimerTask();
    }

    private void setScrollDurtion(int i) {
        if (i >= 0) {
            mScroller = new DLViewPagerScroller(mContext);
            mScroller.setScrollDuration(i);
            mScroller.initViewPagerScroll(mViewPager);
        }
    }

    private void setCanLoop(boolean mCanLoop) {
        this.mCanLoop = mCanLoop;
        mAdapter = new ViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(mContext).inflate(R.layout.dl_banners, null);
        mBannerLayout = view.findViewById(R.id.banner_layout);
        mViewPager = view.findViewById(R.id.banner_viewpager);
        mIndicatorLayout = view.findViewById(R.id.banner_indicator);

        mIndicatorSize = DLScreenUtils.dip2px(mContext, mIndicatorSize);
        mViewPager.addOnPageChangeListener(this);
        setIndicatorBottomPadding();
        mViewPager.setPageTransformer(true, DLPageTransformer.getPageTransformer(mTransitionEffect));
        //设置指示器的位置
        setIndicatorLocation(mIndicatorLocation);
        this.mViewPager.setOnViewPagerTouchEventListener(new DLBannerViewPager.OnViewPagerTouchEvent() {
            @Override
            public void onTouchDown() {
                stopTimerTask();
            }

            @Override
            public void onTouchUp() {
                startTimerTask();
            }
        });
        addView(view);
    }

    /**
     * 开始滚动任务
     */
    public void startTimerTask() {
        stopTimerTask();
        if (mAutoPlay) {
            if (mList.size() > 1) {
                mHandler.sendEmptyMessageDelayed(START_WHAT, mDurtion);
            }
        }
    }

    /**
     * 停止滚动任务
     */
    public void stopTimerTask() {
        if (mHandler != null) {
            mHandler.removeMessages(START_WHAT);
        }
    }


    /**
     * 退出时清除所有消息
     */
    public void clearTimerTask() {
        mHandler.removeCallbacksAndMessages(null);
    }

    private final static int START_WHAT = 100;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_WHAT:
                    int allCount = 0;
                    if (mCanLoop) {
                        allCount = mTotalCount;
                    } else {
                        allCount = mList.size();

                    }
                    currentPos = (currentPos + 1) % allCount;
                    if (currentPos == mTotalCount - 1) {//最后一个过渡到第一个不需要动画
                        mViewPager.setCurrentItem(showCount - 1, false);
                    } else {
                        mViewPager.setCurrentItem(currentPos);
                    }
                    this.sendEmptyMessageDelayed(START_WHAT, mDurtion);
                    break;
            }
        }
    };

    /**
     * 设置指示器离底部的位置
     */
    private void setIndicatorBottomPadding() {
        mIndicatorLayout.setPadding(0, 0, 0, DLScreenUtils.dip2px(mContext, mIndicatorBottomPadding));
    }

    /**
     * 设置指示器位置
     *
     * @param mIndicatorLocation
     */
    private void setIndicatorLocation(IndicatorPos mIndicatorLocation) {
        if (mIndicatorLocation == IndicatorPos.middle) {
            mIndicatorLayout.setGravity(Gravity.CENTER);
        } else {
            mIndicatorLayout.setGravity(Gravity.CENTER | Gravity.RIGHT);
        }
    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mCanLoop) {
                return mTotalCount;
            } else {
                return mList.size();
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= showCount;
            final View view = adapter.getView(DLBanners.this, mContext, position, mList.get(position));
            final int finalPosition = position;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(DLBanners.this, v, finalPosition, mList.get(finalPosition));
                }
            });
            view.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemLongClickListener != null)
                        mOnItemLongClickListener.onItemLongClick(DLBanners.this, view, finalPosition, mList.get(finalPosition));
                    return false;
                }
            });
            view.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mOnItemTouchListener != null)
                        return mOnItemTouchListener.onItemTouch(DLBanners.this, view, event, finalPosition, mList.get(finalPosition));
                    return false;
                }

            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
            if (mCanLoop) {
                int position = mViewPager.getCurrentItem();
                if (position == 0) {
                    position = showCount;
                    mViewPager.setCurrentItem(position, false);
                } else if (position == mTotalCount - 1) {
                    position = showCount - 1;
                    mViewPager.setCurrentItem(position, false);
                }
            }

        }
    }

    /**
     * 自定义游标view
     *
     * @param view
     */
    public void setIndicatorView(View view) {
        this.mIndicatorView = view;
        mIndicatorLayout.removeAllViews();
        mIndicatorLayout.addView(mIndicatorView);
    }

    public void setOffscreenPageLimit(int page) {
        mViewPager.setOffscreenPageLimit(page);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public void setOnItemTouchListener(OnItemTouchListener listener) {
        this.mOnItemTouchListener = listener;
    }

    public void setOnBannerPageScrollListener(OnBannerPageScrollListener listener) {
        this.mOnBannerPageScrollListener = listener;
    }

}
