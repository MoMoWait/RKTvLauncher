package com.rockchips.tvlauncher.main;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.rockchips.tvlauncher.R;
import com.rockchips.tvlauncher.data.ConstData;
import com.rockchips.tvlauncher.util.NetWorkUtils;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.os.Handler;

/**
 * Created by GaoFei on 2017/6/6.
 */

public class HeaderFragment extends Fragment{
    private static final String TAG = "HeaderFragment";
    @ViewInject(R.id.text_time)
    private TextView mTextTime;
    @ViewInject(R.id.img_wifi)
    private ImageView mImgWifi;
    @ViewInject(R.id.img_ethernet)
    private ImageView mImgEthernet;

    private View mView;
    private Handler mTimeUpdateHandler;
    private TimeUpdateTask mTimeUpdateTask;
    private NetWorkChangeReceiver mNetChangeReceiver;
    private IntentFilter mNetChangeFilter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_header, container, false);
        x.view().inject(this, mView);
        return  mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView(){
        mTextTime.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50);
    }

    @Override
    public void onResume() {
        super.onResume();
        startTimeUpdate();
        startNetWorkListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimeUpdate();
        stopNetWorkListener();
    }
    private void startTimeUpdate(){
        mTextTime.setText(getCurrentTime());
        if(mTimeUpdateHandler == null)
            mTimeUpdateHandler = new Handler();
        if(mTimeUpdateTask == null)
            mTimeUpdateTask = new TimeUpdateTask();
        mTimeUpdateHandler.postDelayed(mTimeUpdateTask, 30000);

    }
    private void stopTimeUpdate(){
        mTimeUpdateHandler.removeCallbacks(mTimeUpdateTask);
        mTimeUpdateTask = null;
        mTimeUpdateHandler = null;
    }
    private void startNetWorkListener(){
        updateNetWorkState();
        if(mNetChangeReceiver == null)
            mNetChangeReceiver = new NetWorkChangeReceiver();
        if(mNetChangeFilter == null)
            mNetChangeFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(mNetChangeReceiver, mNetChangeFilter);
    }
    private void stopNetWorkListener(){
        getActivity().unregisterReceiver(mNetChangeReceiver);
    }
    private String getCurrentTime(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
        return dateFormat.format(now);
    }
    private void updateNetWorkState(){
        int networkState = NetWorkUtils.getCurrentNetWrokState();
        Log.i(TAG, "updateNetWorkState->networkState");
        if(networkState == ConstData.NetWorkState.NO){
            mImgEthernet.setVisibility(View.GONE);
            mImgWifi.setVisibility(View.GONE);
        }else if(networkState == ConstData.NetWorkState.WIFI){
            mImgWifi.setVisibility(View.VISIBLE);
            mImgEthernet.setVisibility(View.GONE);
        }else if(networkState == ConstData.NetWorkState.ETHERNET){
            mImgWifi.setVisibility(View.GONE);
            mImgEthernet.setVisibility(View.VISIBLE);
        }
    }
    class TimeUpdateTask implements Runnable{
        @Override
        public void run() {
            mTextTime.setText(getCurrentTime());
            mTimeUpdateHandler.postDelayed(this, 30000);
        }
    }
    class NetWorkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "NetWorkChangeReceiver->onReceive");
            updateNetWorkState();
        }
    }
}
