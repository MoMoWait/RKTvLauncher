
package com.rockchips.tvlauncher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.rockchips.tvlauncher.R;
import com.rockchips.tvlauncher.app.AppCardPresenter;
import com.rockchips.tvlauncher.app.AppDataManage;
import com.rockchips.tvlauncher.app.AppModel;
import com.rockchips.tvlauncher.bean.FunctionItem;
import com.rockchips.tvlauncher.data.ConstData;
import com.rockchips.tvlauncher.detail.MediaDetailsActivity;
import com.rockchips.tvlauncher.detail.MediaModel;
import com.rockchips.tvlauncher.function.FunctionCardPresenter;
import com.rockchips.tvlauncher.function.FunctionModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    protected ContentFragment mBrowseFragment;
    private ArrayObjectAdapter rowsAdapter;
    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mBrowseFragment = (ContentFragment) getFragmentManager().findFragmentById(R.id.content_fragment);

        mBrowseFragment.setHeadersState(BrowseFragment.HEADERS_DISABLED);
        //mBrowseFragment.showTitle(false);
        //mBrowseFragment.setBrowseTransitionListener();
        //mBrowseFragment.setTitle(getString(R.string.app_name));
        mBrowseFragment.setBrowseTransitionListener(new BrowseFragment.BrowseTransitionListener(){
            @Override
            public void onHeadersTransitionStart(boolean withHeaders) {
                super.onHeadersTransitionStart(withHeaders);
                findViewById(R.id.browse_title_group).setVisibility(View.GONE);
            }

            @Override
            public void onHeadersTransitionStop(boolean withHeaders) {
                super.onHeadersTransitionStop(withHeaders);
                findViewById(R.id.browse_title_group).setVisibility(View.GONE);
            }
        });
        prepareBackgroundManager();
        buildRowsAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(this);
        mBackgroundManager.attach(this.getWindow());
        mMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void buildRowsAdapter() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        //addPhotoRow();
        //addVideoRow();
        addHotAppRow();
        addAppRow();
        addFunctionRow();
        mBrowseFragment.setAdapter(rowsAdapter);
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
                } else if (item instanceof AppModel) {
                    AppModel appBean = (AppModel) item;
                    Intent launchIntent = mContext.getPackageManager().getLaunchIntentForPackage(
                            appBean.getPackageName());
                    if (launchIntent != null) {
                        mContext.startActivity(launchIntent);
                    }
                } else if (item instanceof FunctionModel) {
                    FunctionModel functionModel = (FunctionModel) item;
                    Intent intent = functionModel.getIntent();
                    if (intent != null) {
                        startActivity(intent);
                    }
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

                    Glide.with(mContext)
                            .load(mediaModel.getImageUrl())
                            .asBitmap()
                            .centerCrop()
                            .into(new SimpleTarget<Bitmap>(width, height) {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    mBackgroundManager.setBitmap(resource);
                                }
                            });
                } else {
                    mBackgroundManager.setBitmap(null);
                }
            }
        });
    }

    private void addPhotoRow() {
        String headerName = getResources().getString(R.string.app_header_photo_name);
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new ImgCardPresenter());

        for (MediaModel mediaModel : MediaModel.getPhotoModels()) {
            listRowAdapter.add(mediaModel);
        }
        HeaderItem header = new HeaderItem(0, headerName);
        rowsAdapter.add(new ListRow(header, listRowAdapter));
    }

    private void addVideoRow() {
        String headerName = getResources().getString(R.string.app_header_video_name);
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new ImgCardPresenter());
        for (MediaModel mediaModel : MediaModel.getVideoModels()) {
            listRowAdapter.add(mediaModel);
        }
        HeaderItem header = new HeaderItem(0, headerName);
        rowsAdapter.add(new ListRow(header, listRowAdapter));
    }
    private void addHotAppRow(){
        //String headerName = getResources().getString(R.string.app_header_app_name);
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new AppCardPresenter(true));

        ArrayList<AppModel> appDataList = new AppDataManage(mContext).getLaunchAppList(true);
        int cardCount = appDataList.size();

        for (int i = 0; i < cardCount; i++) {
            listRowAdapter.add(appDataList.get(i));
        }
        //HeaderItem header = new HeaderItem(0, headerName);
        rowsAdapter.add(new ListRow(null, listRowAdapter));
    }

    private void addAppRow() {
        String headerName;
        ArrayObjectAdapter listRowAdapter;
        ArrayList<AppModel> appDataList = new AppDataManage(mContext).getLaunchAppList(false);
        if(appDataList == null || appDataList.size() == 0)
            return;
        int appCount = appDataList.size();
        int appRowCount = (appCount + ConstData.APP_ROW_COUNT - 1) / ConstData.APP_ROW_COUNT;
        for(int row = 0; row < appRowCount - 1; ++row){
            listRowAdapter = new ArrayObjectAdapter(new AppCardPresenter(false));
            for(int col = 0; col < ConstData.APP_ROW_COUNT; ++col){
                listRowAdapter.add(appDataList.get(row * ConstData.APP_ROW_COUNT + col));
            }
            if(row == 0)
                headerName = getResources().getString(R.string.app_header_app_name);
            else
                headerName = null;
            HeaderItem header = new HeaderItem(row, headerName);
            rowsAdapter.add(new ListRow(header, listRowAdapter));
        }
        int lastRowCount = appCount % ConstData.APP_ROW_COUNT;
        if(lastRowCount > 0){
            listRowAdapter = new ArrayObjectAdapter(new AppCardPresenter(false));
            for(int col = 0; col < lastRowCount; ++col){
                listRowAdapter.add(appDataList.get(appCount - lastRowCount + col));
            }
            if(appRowCount == 1)
                headerName = getResources().getString(R.string.app_header_app_name);
            else
                headerName = null;
            HeaderItem header = new HeaderItem(lastRowCount, headerName);
            rowsAdapter.add(new ListRow(header, listRowAdapter));
        }
    }

    private void addFunctionRow() {
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new FunctionCardPresenter());
        List<FunctionItem> functionItems = FunctionModel.getFunctionItems(this);
        int cardCount = functionItems.size();
        for (int i = 0; i < cardCount; i++) {
            listRowAdapter.add(functionItems.get(i));
        }
        HeaderItem header = new HeaderItem(0, null);
        rowsAdapter.add(new ListRow(header, listRowAdapter));
    }
}
