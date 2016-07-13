package com.xs.baidumaplib.demo2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.xs.baidumaplib.R;

/**
 * @version V1.0 <定位+全景展示>
 * @author: Xs
 * @date: 2016-07-13 16:34
 * @email Xs.lin@foxmail.com
 */
public class PanoramicDemoActivity extends AppCompatActivity
        implements OnGetGeoCoderResultListener,View.OnClickListener{

    private static final String TAG = PanoramicDemoActivity.class.getSimpleName();

    private TextView                mAddress;
    private TextView                mCheckPanoramic;

    private MapView                 mMapView;
    private BaiduMap                mBaiduMap;
    private LocationClient          mLclient;
    private MyLocationListener      mMyLocListener;
    private GeoCoder                mSearch = null;  // 搜索模块，也可去掉地图模块独立使用

    private double longitude,latitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panoramic);

        mAddress = (TextView) findViewById(R.id.mapview_address);
        mCheckPanoramic = (TextView) findViewById(R.id.mapview_pan);
        mCheckPanoramic.setOnClickListener(this);

        /*** 定位初始化*/
        mMapView = (MapView) findViewById(R.id.mv_mapview);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mLclient = new LocationClient(this);

        /*** 初始化*/
        mMyLocListener = new MyLocationListener();
        mLclient.registerLocationListener(mMyLocListener);
        LocationClientOption mLocClientOption = new LocationClientOption();
        mLocClientOption.setOpenGps(true);
        mLocClientOption.setCoorType("bd09ll");
        mLocClientOption.setScanSpan(1000);
        mLclient.setLocOption(mLocClientOption);
        mLclient.start();

    }

    /**
     * 更新地图状态 搜索 解码地址
     */
    private void updateMapStatus() {
        MapStatusUpdate up;
        up = MapStatusUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 17);
        Log.e(TAG, "longitude==" + longitude + "\n" + "latitude=" + latitude);
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(latitude, longitude)));
        mSearch.setOnGetGeoCodeResultListener(this);
        mBaiduMap.setOnMapStatusChangeListener(new MyOnMapStatusChangeListener());
        mBaiduMap.setMapStatus(up);
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        ReverseGeoCodeResult.AddressComponent com = reverseGeoCodeResult.getAddressDetail();
        if (com != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(com.province);
            sb.append(com.city);
            sb.append(com.district);
            sb.append(com.street);
            sb.append(com.streetNumber);
            mAddress.setText(sb.toString());
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.mapview_pan) {
            Intent intent = new Intent(PanoramicDemoActivity.this,PanoramicShowActivity.class);
            intent.putExtra("latitude",latitude);
            intent.putExtra("longitude",longitude);
            startActivity(intent);
        }
    }


    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            LatLng ll = new LatLng(bdLocation.getLatitude(),
                    bdLocation.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            mLclient.stop();
            longitude = bdLocation.getLongitude();
            latitude = bdLocation.getLatitude();
            updateMapStatus();
        }
    }

    class  MyOnMapStatusChangeListener implements BaiduMap.OnMapStatusChangeListener {

        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {
            mAddress.setText("正在解码地址...");
        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {
            latitude = mapStatus.target.latitude;
            longitude = mapStatus.target.longitude;
            mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(mapStatus.target));

        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        if (mLclient.isStarted()) {
            mLclient.stop();
            mLclient = null;
        }
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}
