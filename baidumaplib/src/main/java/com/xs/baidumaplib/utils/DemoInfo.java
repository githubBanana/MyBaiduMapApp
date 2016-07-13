package com.xs.baidumaplib.utils;

import android.app.Activity;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-07-13 14:02
 * @email Xs.lin@foxmail.com
 */
public class DemoInfo {

    public int title;

    public int desc;

    public Class<? extends Activity> demoClass;

    public DemoInfo(int title, int desc, Class<? extends Activity> demoClass) {
        this.title = title;
        this.desc = desc;
        this.demoClass = demoClass;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }

    public Class<? extends Activity> getDemoClass() {
        return demoClass;
    }

    public void setDemoClass(Class<? extends Activity> demoClass) {
        this.demoClass = demoClass;
    }
}
