package com.xs.mybaidumapapp;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-07-13 09:51
 * @email Xs.lin@foxmail.com
 */
public class BaiduMapApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
