package com.xiaohui.parser.impl;

import android.app.Activity;

import com.xiaohui.parser.TaskStatusListener;

/**
 * Created by Administrator on 2016/7/5.
 * 全任务状态监听实现类
 */
public class FullTaskListener<T> implements TaskStatusListener<T> {
   private Activity mActivity;

    public FullTaskListener(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onTaskStart() {
//        if (mActivity instanceof BaseActivity) {
//            ((BaseActivity) mActivity).showLoadingDialog();
//        }
    }

    @Override
    public void onTaskFinish() {
//        if (mActivity instanceof BaseActivity) {
//            ((BaseActivity) mActivity).dismissLoadingDialog();
//        }
    }

    @Override
    public void onTaskSuccess(T response) {

    }

    @Override
    public void onTaskFailure(Exception e) {//出现错误，在此提示出来
//        com.whunf.putaomovieday1.common.util.T.showShort(mActivity,e.toString());
    }
}
