package com.xs.mybaidumapapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-07-13 09:54
 * @email Xs.lin@foxmail.com
 */
public abstract class Badapter<T> extends BaseAdapter {

    protected List<T> mList;
    protected Badapter() {
        mList = new ArrayList<T>();
    }

    public boolean addAll(Collection<T> collection) {
        mList.clear();
        if (mList.addAll(collection)) {
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    public List<T> getListSource() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
