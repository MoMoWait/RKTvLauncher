package com.rockchips.tvlauncher.main;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.util.Log;

/**
 *
 * Created by GaoFei on 2017/6/6.
 */

public class ContentFragment extends BrowseFragment{
    private static final String TAG = "ContentFragment";
    private AppChangedReceiver mChangedReceiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "ContentFragment->onCreate");
        mChangedReceiver = new AppChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
    /*    filter = new IntentFilter();
        filter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
        filter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);*/
        getActivity().registerReceiver(mChangedReceiver, filter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mChangedReceiver);
        super.onDestroy();
    }

    class AppChangedReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "AppChangeReceiver->intent->action:" + intent.getAction());
            MainActivity activity = (MainActivity) getActivity();
            activity.loadAppItems();
        }
    }
}
