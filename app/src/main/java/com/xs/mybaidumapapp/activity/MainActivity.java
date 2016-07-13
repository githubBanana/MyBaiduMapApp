package com.xs.mybaidumapapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.xs.baidumaplib.demo.cloud.CloudSearchDemo;
import com.xs.baidumaplib.demo.map.BaseMapDemo;
import com.xs.baidumaplib.demo.map.FavoriteDemo;
import com.xs.baidumaplib.demo.map.GeometryDemo;
import com.xs.baidumaplib.demo.map.HeatMapDemo;
import com.xs.baidumaplib.demo.map.IndoorMapDemo;
import com.xs.baidumaplib.demo.map.LayersDemo;
import com.xs.baidumaplib.demo.map.LocationDemo;
import com.xs.baidumaplib.demo.map.MapControlDemo;
import com.xs.baidumaplib.demo.map.MapFragmentDemo;
import com.xs.baidumaplib.demo.map.MarkerClusterDemo;
import com.xs.baidumaplib.demo.map.MultiMapViewDemo;
import com.xs.baidumaplib.demo.map.OfflineDemo;
import com.xs.baidumaplib.demo.map.OpenglDemo;
import com.xs.baidumaplib.demo.map.OverlayDemo;
import com.xs.baidumaplib.demo.map.TextureMapViewDemo;
import com.xs.baidumaplib.demo.map.TileOverlayDemo;
import com.xs.baidumaplib.demo.map.UISettingDemo;
import com.xs.baidumaplib.demo.radar.RadarDemo;
import com.xs.baidumaplib.demo.search.BusLineSearchDemo;
import com.xs.baidumaplib.demo.search.DistrictSearchDemo;
import com.xs.baidumaplib.demo.search.GeoCoderDemo;
import com.xs.baidumaplib.demo.search.IndoorSearchDemo;
import com.xs.baidumaplib.demo.search.PoiSearchDemo;
import com.xs.baidumaplib.demo.search.RoutePlanDemo;
import com.xs.baidumaplib.demo.search.ShareDemo;
import com.xs.baidumaplib.demo.util.OpenBaiduMap;
import com.xs.baidumaplib.demo2.PanoramicDemoActivity;
import com.xs.baidumaplib.utils.DemoInfo;
import com.xs.mybaidumapapp.R;
import com.xs.mybaidumapapp.adapter.MainListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-07-13 09:50
 * @email Xs.lin@foxmail.com
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ListView                mListView;
    private MapBroadCastReceiver    mReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.lv_listview);
        MainListAdapter mAdapter = new MainListAdapter(this);

        final View mHead = LayoutInflater.from(this).inflate(R.layout.head_listview,null);

        mListView.addHeaderView(mHead,null,false);
        mListView.setAdapter(mAdapter);
        mAdapter.addAll(getSource());

        //广播接收
        initBroadCastReceive();
        //item监听
        itemOnListenClick();
    }

    private void initBroadCastReceive() {
        mReceiver = new MapBroadCastReceiver();
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        registerReceiver(mReceiver,iFilter);
    }

    private List<DemoInfo> getSource() {
        List<DemoInfo> mList = new ArrayList<>();
        for (int i = 0; i < DEMOS.length; i++) {
            mList.add(DEMOS[i]);
        }
        return mList;
    }


    class MapBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, final Intent intent) {
            final String s = intent.getAction();
            final TextView text = (TextView) findViewById(R.id.tv_head);
            text.setTextColor(Color.RED);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                        text.setText(R.string.verifiy_fail + intent.getIntExtra
                                (SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE, 0)
                                +  R.string.verifiy_fail2);
                    } else if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
                        text.setText(R.string.verifiy_success);
                        text.setTextColor(Color.GREEN);
                    } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                        text.setText(R.string.network_error);
                    }
                }
            });

        }
    }

    /**
     * listview点击监听事件
     */
    private void itemOnListenClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DEMOS[position - 1].getDemoClass());
                startActivity(intent);
            }
        });
    }

    private static final DemoInfo[] DEMOS = {
            new DemoInfo(R.string.demo_title_basemap,
                    R.string.demo_desc_basemap, BaseMapDemo.class),
            new DemoInfo(R.string.demo_title_map_fragment,
                    R.string.demo_desc_map_fragment, MapFragmentDemo.class),
            new DemoInfo(R.string.demo_title_layers, R.string.demo_desc_layers,
                    LayersDemo.class),
            new DemoInfo(R.string.demo_title_multimap,
                    R.string.demo_desc_multimap, MultiMapViewDemo.class),
            new DemoInfo(R.string.demo_title_control,
                    R.string.demo_desc_control, MapControlDemo.class),
            new DemoInfo(R.string.demo_title_ui, R.string.demo_desc_ui,
                    UISettingDemo.class),
            new DemoInfo(R.string.demo_title_location,
                    R.string.demo_desc_location, LocationDemo.class),
            new DemoInfo(R.string.demo_title_geometry,
                    R.string.demo_desc_geometry, GeometryDemo.class),
            new DemoInfo(R.string.demo_title_overlay,
                    R.string.demo_desc_overlay, OverlayDemo.class),
            new DemoInfo(R.string.demo_title_heatmap, R.string.demo_desc_heatmap,
                    HeatMapDemo.class),
            new DemoInfo(R.string.demo_title_geocode,
                    R.string.demo_desc_geocode, GeoCoderDemo.class),
            new DemoInfo(R.string.demo_title_poi, R.string.demo_desc_poi,
                    PoiSearchDemo.class),
            new DemoInfo(R.string.demo_title_route, R.string.demo_desc_route,
                    RoutePlanDemo.class),
            new DemoInfo(R.string.demo_title_districsearch,
                    R.string.demo_desc_districsearch,
                    DistrictSearchDemo.class),
            new DemoInfo(R.string.demo_title_bus, R.string.demo_desc_bus,
                    BusLineSearchDemo.class),
            new DemoInfo(R.string.demo_title_share, R.string.demo_desc_share,
                    ShareDemo.class),
            new DemoInfo(R.string.demo_title_offline,
                    R.string.demo_desc_offline, OfflineDemo.class),
            new DemoInfo(R.string.demo_title_radar,
                    R.string.demo_desc_radar, RadarDemo.class),
            new DemoInfo(R.string.demo_title_open_baidumap, R.string.demo_desc_open_baidumap,
                    OpenBaiduMap.class),
            new DemoInfo(R.string.demo_title_favorite,
                    R.string.demo_desc_favorite, FavoriteDemo.class),
            new DemoInfo(R.string.demo_title_cloud, R.string.demo_desc_cloud,
                    CloudSearchDemo.class),
            new DemoInfo(R.string.demo_title_opengl, R.string.demo_desc_opengl,
                    OpenglDemo.class),
            new DemoInfo(R.string.demo_title_cluster, R.string.demo_desc_cluster, MarkerClusterDemo.class),
            new DemoInfo(R.string.demo_title_tileoverlay, R.string.demo_desc_tileoverlay,
                    TileOverlayDemo.class),
            new DemoInfo(R.string.demo_desc_texturemapview, R.string.demo_desc_texturemapview,
                    TextureMapViewDemo.class),
            new DemoInfo(R.string.demo_title_indoor, R.string.demo_desc_indoor, IndoorMapDemo.class),
            new DemoInfo(R.string.demo_title_indoorsearch, R.string.demo_desc_indoorsearch, IndoorSearchDemo.class),
            new DemoInfo(R.string.location_and_panoramic,R.string.location_and_panoramic, PanoramicDemoActivity.class)
    };


}
