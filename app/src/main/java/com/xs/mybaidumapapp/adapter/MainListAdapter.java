package com.xs.mybaidumapapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xs.baidumaplib.utils.DemoInfo;
import com.xs.mybaidumapapp.R;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-07-13 09:54
 * @email Xs.lin@foxmail.com
 */
public class MainListAdapter extends Badapter<DemoInfo> {
    private static final String TAG = "MainListAdapter";

    private Context mContext;
    public MainListAdapter(Context context) {
        this.mContext = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mVh = null;
        if (convertView == null) {
            mVh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_mainlist_layout,null);
            mVh.mTvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            mVh.mTvDesc = (TextView) convertView.findViewById(R.id.tv_desc);
            convertView.setTag(mVh);
        } else {
            mVh = (ViewHolder) convertView.getTag();
        }

        final DemoInfo _demoInfo = getListSource().get(position);
        mVh.mTvTitle.setText(mContext.getString(_demoInfo.getTitle()));
        mVh.mTvDesc.setText(mContext.getString(_demoInfo.getDesc()));

        return convertView;
    }

    class ViewHolder {
        TextView    mTvTitle;
        TextView    mTvDesc;
    }
}
