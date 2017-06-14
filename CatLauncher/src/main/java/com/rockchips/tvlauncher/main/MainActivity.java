
package com.rockchips.tvlauncher.main;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import com.rockchips.tvlauncher.R;
import com.rockchips.tvlauncher.app.AppCardPresenter;
import com.rockchips.tvlauncher.app.AppItemPresenter;
import com.rockchips.tvlauncher.bean.AppItem;
import com.rockchips.tvlauncher.bean.FunctionItem;
import com.rockchips.tvlauncher.data.ConstData;
import com.rockchips.tvlauncher.detail.MediaDetailsActivity;
import com.rockchips.tvlauncher.detail.MediaModel;
import com.rockchips.tvlauncher.function.FunctionCardPresenter;
import com.rockchips.tvlauncher.function.FunctionModel;
import com.rockchips.tvlauncher.service.AppLoadTask;

import java.util.ArrayList;
import java.util.List;

import momo.cn.edu.fjnu.androidutils.utils.ToastUtils;

public class MainActivity extends Activity {
    private static final String TAG = "Launcher_MainActivity";
    protected ContentFragment mBrowseFragment;
    private ArrayObjectAdapter mRowsAdapter;
    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;
    private Context mContext;
    private AppLoadTask mAppLoadTask;
    private List<ListRow> mAppRows = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mBrowseFragment = (ContentFragment) getFragmentManager().findFragmentById(R.id.content_fragment);
        mBrowseFragment.setHeadersState(BrowseFragment.HEADERS_DISABLED);
        prepareBackgroundManager();
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        addMyRecommendAppRow();
        addVideoAppRow();
        addMusicAppRow();
        addFunctionRow();
        mBrowseFragment.setAdapter(mRowsAdapter);
        initEvent();
        loadAppItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(mAppLoadTask != null && mAppLoadTask.getStatus() == AsyncTask.Status.RUNNING)
            mAppLoadTask.cancel(true);
        super.onDestroy();
    }

    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(this);
        mBackgroundManager.attach(this.getWindow());
        mMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void initEvent() {
        mBrowseFragment.setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof MediaModel) {
                    MediaModel mediaModel = (MediaModel) item;
                    Intent intent = new Intent(mContext, MediaDetailsActivity.class);
                    intent.putExtra(MediaDetailsActivity.MEDIA, mediaModel);

                    Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity) mContext,
                            ((ImageCardView) itemViewHolder.view).getMainImageView(),
                            MediaDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                    startActivity(intent, bundle);
                } else if (item instanceof AppItem) {
                    AppItem appItem = (AppItem) item;
                    Intent appIntent = new Intent();
                    appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    appIntent.setClassName(appItem.getPkgName(), appItem.getActivityName());
                    if(getPackageManager().resolveActivity(appIntent, 0) == null){
                        //应用程序不存在
                        ToastUtils.showToast(getString(R.string.app_not_installed));
                        return;
                    }
                    startActivity(appIntent);
                } else if (item instanceof FunctionItem) {
                    FunctionItem functionItem = (FunctionItem) item;
                    Intent appIntent = new Intent();
                    appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    appIntent.setClassName(functionItem.getPkgName(), functionItem.getActivityName());
                    if(getPackageManager().resolveActivity(appIntent, 0) == null){
                        //应用程序不存在
                        ToastUtils.showToast(getString(R.string.app_not_installed));
                        return;
                    }
                    startActivity(appIntent);
                }
            }
        });
        mBrowseFragment.setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof MediaModel) {

                    MediaModel mediaModel = (MediaModel) item;
                    int width = mMetrics.widthPixels;
                    int height = mMetrics.heightPixels;
                } else {
                    mBackgroundManager.setBitmap(null);
                }
            }
        });
    }
    private void addHotAppRow(List<AppItem> hotAppItems){
        if(hotAppItems == null || hotAppItems.size() == 0)
            return;
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new AppCardPresenter(true));
        int cardCount = hotAppItems.size();
        for (int i = 0; i < cardCount; i++) {
            listRowAdapter.add(hotAppItems.get(i));
        }
        //HeaderItem header = new HeaderItem(0, headerName);
        mRowsAdapter.add(new ListRow(null, listRowAdapter));
    }

    private void addAppRow(List<AppItem> allAppItems) {
        String headerName;
        ArrayObjectAdapter listRowAdapter;
        if(allAppItems == null || allAppItems.size() == 0)
            return;
        if(mAppRows != null && mAppRows.size() > 1){
            for(ListRow itemRow : mAppRows){
                mRowsAdapter.remove(itemRow);
            }
            mAppRows.clear();
        }
        int appCount = allAppItems.size();
        int appRowCount = (appCount + ConstData.APP_ROW_COUNT - 1) / ConstData.APP_ROW_COUNT;
        for(int row = 0; row < appRowCount - 1; ++row){
            listRowAdapter = new ArrayObjectAdapter(new AppItemPresenter(false));
            for(int col = 0; col < ConstData.APP_ROW_COUNT; ++col){
                listRowAdapter.add(allAppItems.get(row * ConstData.APP_ROW_COUNT + col));
            }
            if(row == 0)
                headerName = getResources().getString(R.string.app_header_app_name);
            else
                headerName = null;
            HeaderItem header = new HeaderItem(row, headerName);
            mAppRows.add(new ListRow(header, listRowAdapter));
            //mRowsAdapter.add(new ListRow(header, listRowAdapter));
        }
        int lastRowCount = appCount % ConstData.APP_ROW_COUNT;
        if(lastRowCount > 0){
            listRowAdapter = new ArrayObjectAdapter(new AppItemPresenter(false));
            for(int col = 0; col < lastRowCount; ++col){
                listRowAdapter.add(allAppItems.get(appCount - lastRowCount + col));
            }
            if(appRowCount == 1)
                headerName = getResources().getString(R.string.app_header_app_name);
            else
                headerName = null;
            HeaderItem header = new HeaderItem(lastRowCount, headerName);
            mAppRows.add(new ListRow(header, listRowAdapter));
            //mRowsAdapter.add(new ListRow(header, listRowAdapter));
        }
        mRowsAdapter.addAll(mRowsAdapter.size() - 1, mAppRows);
    }

    private void addFunctionRow() {
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new FunctionCardPresenter());
        List<FunctionItem> functionItems = FunctionModel.getFunctionItems(this);
        int cardCount = functionItems.size();
        for (int i = 0; i < cardCount; i++) {
            listRowAdapter.add(functionItems.get(i));
        }
        HeaderItem header = new HeaderItem(0, null);
        mRowsAdapter.add(new ListRow(header, listRowAdapter));
    }

    private void addVideoAppRow(){

    }
    private void addMusicAppRow(){

    }
    private void addMyRecommendAppRow(){

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return event.getKeyCode() == KeyEvent.KEYCODE_BACK || super.dispatchKeyEvent(event);
    }

    public void loadAppItems(){
        Log.i(TAG, "loadAppItems");
        if(mAppLoadTask != null && mAppLoadTask.getStatus() == AsyncTask.Status.RUNNING){
            mAppLoadTask.cancel(true);
            mAppLoadTask = null;
        }
        mAppLoadTask = new AppLoadTask(new AppLoadTask.CallBack() {
            @Override
            public void onFinished(List<AppItem> hotAppItems, List<AppItem> allAppItems) {
                //addHotAppRow(hotAppItems);
                addAppRow(allAppItems);
                mRowsAdapter.notifyArrayItemRangeChanged(mRowsAdapter.size() - mAppRows.size() ,mAppRows.size());
                //mBrowseFragment.setAdapter(mRowsAdapter);
            }
        });
        mAppLoadTask.execute();
    }


}
