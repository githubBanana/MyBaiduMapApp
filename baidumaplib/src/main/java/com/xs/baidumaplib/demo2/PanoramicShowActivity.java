package com.xs.baidumaplib.demo2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.lbsapi.panoramaview.PanoramaViewListener;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.xs.baidumaplib.R;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-07-13 18:19
 * @email Xs.lin@foxmail.com
 */
public class PanoramicShowActivity extends AppCompatActivity {
    private static final String TAG = "PanoramicShowActivity";

    private PanoramaView mPanoramaView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panoramic_show);
        mPanoramaView = (PanoramaView) findViewById(R.id.panorama);
        mPanoramaView.setShowTopoLink(true);
        mPanoramaView.setPanorama(getIntent().getDoubleExtra("longitude",0.0),getIntent().getDoubleExtra("latitude",0.0));
        mPanoramaView.setPanoramaViewListener(new PanoramaViewListener() {
            @Override
            public void onDescriptionLoadEnd(String s) {

            }

            @Override
            public void onLoadPanoramaBegin() {

            }

            @Override
            public void onLoadPanoramaEnd(String s) {

            }

            @Override
            public void onLoadPanoramaError(String s) {

            }

            @Override
            public void onMessage(String s, int i) {

            }

            @Override
            public void onCustomMarkerClick(String s) {

            }
        });


        BaiduMapPoiSearch.openBaiduMapPanoShow(getIntent().getStringExtra("uid"), this); // 天安门

    }

    @Override
    protected void onResume() {
        mPanoramaView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mPanoramaView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mPanoramaView.destroy();
        super.onDestroy();
    }
}
