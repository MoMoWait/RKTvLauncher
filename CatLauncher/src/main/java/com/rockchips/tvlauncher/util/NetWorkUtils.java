package com.rockchips.tvlauncher.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.rockchips.tvlauncher.data.ConstData;

import momo.cn.edu.fjnu.androidutils.data.CommonValues;

/**
 * Created by GaoFei on 2017/6/6.
 */

public class NetWorkUtils {
    public static int getCurrentNetWrokState(){
        ConnectivityManager connectivityManager = (ConnectivityManager) CommonValues.application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null || connectivityManager.getActiveNetworkInfo() == null)
            return ConstData.NetWorkState.NO;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        int networkType = networkInfo.getType();
        if(networkType == ConnectivityManager.TYPE_WIFI)
            return  ConstData.NetWorkState.WIFI;
        else if(networkType == ConnectivityManager.TYPE_ETHERNET)
            return ConstData.NetWorkState.ETHERNET;
        return ConstData.NetWorkState.NO;
    }
}
