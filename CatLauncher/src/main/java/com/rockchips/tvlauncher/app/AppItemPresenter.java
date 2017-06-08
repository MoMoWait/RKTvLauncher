package com.rockchips.tvlauncher.app;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.ViewGroup;
import com.rockchips.tvlauncher.bean.AppItem;
import com.rockchips.tvlauncher.view.AppItemView;
/**
 * Created by GaoFei on 2017/6/8.
 */

public class AppItemPresenter extends Presenter {
    private static final String TAG = "AppItemPresenter";
    private Context mContext;
    public AppItemPresenter(boolean isHotApp){

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        //Log.i(TAG, "onCreateViewHolder->stackTrace:" + Log.getStackTraceString(new Throwable()));
        mContext = parent.getContext();
        AppItemView itemView = new AppItemView(mContext);
        itemView.setBackground(new ColorDrawable(Color.TRANSPARENT));
        itemView.setFocusable(true);
        itemView.setFocusableInTouchMode(true);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
       // Log.i(TAG, "onBindViewHolder->stackTrace:" + Log.getStackTraceString(new Throwable()));
        //Log.i(TAG, "onBindViewHolder->item:" + item);
        AppItemView appItemView = (AppItemView) viewHolder.view;
        AppItem appItem = (AppItem) item;
        appItemView.update(appItem);
        //ShadowOverlayContainer container = (ShadowOverlayContainer) appItemView.getParent();
        /*try{
            Field field = container.getClass().getDeclaredField("mOverlayColor");
            field.setAccessible(true);
            Object overlayColor = field.get(container);
            Log.i(TAG, "overlayColor:" + overlayColor);
        }catch (Exception e){
            e.printStackTrace();
        }*/


        //pView.setBackground(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        Log.i(TAG, "onUnbindViewHolder");
       /* ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);*/
    }


}
