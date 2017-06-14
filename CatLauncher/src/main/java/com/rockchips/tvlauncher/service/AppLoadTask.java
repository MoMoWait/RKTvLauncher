package com.rockchips.tvlauncher.service;

import android.content.pm.LauncherApps;
import android.os.AsyncTask;

import com.rockchips.tvlauncher.app.AppDataManage;
import com.rockchips.tvlauncher.bean.AppItem;

import java.util.ArrayList;
import java.util.List;

import momo.cn.edu.fjnu.androidutils.data.CommonValues;

/**
 * Created by GaoFei on 2017/6/12.
 */

public class AppLoadTask extends AsyncTask<Object,Object,List<AppItem>> {

    public interface CallBack{
        void onFinished(List<AppItem> hotAppItems, List<AppItem> allAppItems);
    }

    private CallBack mCallback;
    private List<AppItem> mHotAppItems;
    private List<AppItem> mAllAppItems;
    public AppLoadTask(CallBack callBack){
        mCallback = callBack;
    }
    @Override
    protected List<AppItem> doInBackground(Object... params) {
        AppDataManage appDataManage = new AppDataManage(CommonValues.application);
        mHotAppItems = appDataManage.getLaunchAppItems(true);
        mAllAppItems = appDataManage.getLaunchAppItems(false);
        return null;
    }

    @Override
    protected void onPostExecute(List<AppItem> appItems) {
        mCallback.onFinished(mHotAppItems, mAllAppItems);
    }
}
