package com.xs.mybaidumapapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.mapapi.SDKInitializer;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-07-13 09:51
 * @email Xs.lin@foxmail.com
 */
public class BaiduMapApplication extends Application {

    public BMapManager mBMapManager;
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        initEngineManager(this);
    }

    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }
        if (!mBMapManager.init(new BaiduMapGeneralListener())) {
            Toast.makeText(context, "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
        }
    }

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    public static class BaiduMapGeneralListener implements MKGeneralListener {
        @Override
        public void onGetPermissionState(int iError) {
            // 非零值表示key验证未通过
            Log.e("", "onGetPermissionState  key=" + iError);

        }
    }
}
