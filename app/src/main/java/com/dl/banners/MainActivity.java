package com.dl.banners;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dl.banners.adapter.MyAdapter;
import com.dl.banners.entity.ADBanner;
import com.lib.dlbanner.DLBanners;
import com.lib.dlbanner.listener.OnBannerPageScrollListener;
import com.lib.dlbanner.listener.OnItemClickListener;
import com.lib.dlbanner.listener.OnItemLongClickListener;
import com.lib.dlbanner.listener.OnItemTouchListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private List<ADBanner> bannersList = new ArrayList<>();
    private DLBanners banners,banners2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBanner();
        banners = findViewById(R.id.banners);
        banners2 = findViewById(R.id.banners2);
        banners.setAdapter(new MyAdapter(MainActivity.this), bannersList);
        banners2.setAdapter(new MyAdapter(MainActivity.this), bannersList);
        //事件处理
        addLinstener();
        //自定义游标
        setIndicatorView();

    }

    private void addLinstener() {
        banners.setOnItemClickListener(new OnItemClickListener<ADBanner>() {
            @Override
            public void onItemClick(DLBanners dlBanners, View view, int position, ADBanner data) {
                Log.e(TAG, "onItemClick:" + position);
                Toast.makeText(MainActivity.this, "点击：" + data.imgUrl, Toast.LENGTH_SHORT).show();
            }
        });
        banners.setOnItemLongClickListener(new OnItemLongClickListener<ADBanner>() {
            @Override
            public void onItemLongClick(DLBanners dlBanners, View view, int position, ADBanner data) {
                Log.e(TAG, "onItemLongClick:" + position);
                Toast.makeText(MainActivity.this, "长按：" + data.imgUrl, Toast.LENGTH_SHORT).show();
            }
        });
        banners.setOnItemTouchListener(new OnItemTouchListener<ADBanner>() {
            @Override
            public boolean onItemTouch(DLBanners dlBanners, View view, MotionEvent event, int position, ADBanner data) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    Log.e(TAG, "onItemTouch:" + position);
                return false;
            }
        });

    }

    /**
     * 自定义游标
     */
    private void setIndicatorView() {
        final TextView textView = new TextView(this);
        textView.setTextSize(16);
        textView.setTextColor(Color.RED);
        textView.setText("1/" + banners2.getBannersCount());
        textView.setPadding(0,0,20,0);
        banners2.setIndicatorView(textView);
        banners2.setOnBannerPageScrollListener(new OnBannerPageScrollListener() {
            @Override
            public void onPageSelected(int position) {
                textView.setText((position + 1) + "/" + banners2.getBannersCount());
            }
        });
    }

    private void initBanner() {
        ADBanner adBanner = new ADBanner();
        adBanner.type = 1;
        adBanner.imgUrl = "http://img4.imgtn.bdimg.com/it/u=2251229544,2329383524&fm=200&gp=0.jpg";
        ADBanner adBanner2 = new ADBanner();
        adBanner2.type = 0;
        adBanner2.imgUrl = "http://app.fmeimei.com/fmm/images/goods/01510758884099.jpg";
        ADBanner adBanner3 = new ADBanner();
        adBanner3.type = 0;
        adBanner3.imgUrl = "http://img1.imgtn.bdimg.com/it/u=245648742,601019179&fm=200&gp=0.jpg";
        ADBanner adBanner4 = new ADBanner();
        adBanner4.type = 0;
        adBanner4.imgUrl = "http://img0.imgtn.bdimg.com/it/u=2712906761,1041513721&fm=200&gp=0.jpg";
        bannersList.add(adBanner);
        bannersList.add(adBanner2);
        bannersList.add(adBanner3);
        bannersList.add(adBanner4);
    }


    @Override
    protected void onStart() {
        super.onStart();
        banners.startTimerTask();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banners.stopTimerTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        banners.clearTimerTask();
    }
}
