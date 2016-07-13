package com.xs.baidumaplib.demo2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baidu.lbsapi.panoramaview.PanoramaView;
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

    }
}
