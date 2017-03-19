package com.xiaohui.opensource;

import android.app.Application;

import com.xiaohui.parser.impl.VolleyTask;

/**
 * Created by xiaohui on 2017/1/21.
 */

public class DemoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VolleyTask.init(this);
    }
}
