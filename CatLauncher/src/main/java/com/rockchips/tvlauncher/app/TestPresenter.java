package com.rockchips.tvlauncher.app;

/**
 * Created by GaoFei on 2017/6/8.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.ShadowOverlayContainer;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rockchips.tvlauncher.data.ConstData;

/**
 * ImageCard Presenter
 *
 * @author jacky
 * @version v1.0
 * @since 16/7/16
 */
public class TestPresenter extends Presenter {

    private Context mContext;
    private int CARD_WIDTH = ConstData.COMMON_CARD_WIDTH;
    private int CARD_HEIGHT = ConstData.COMMON_CARD_HEIGHT;
    private Drawable mDefaultCardImage;
    private static final String TAG = "TestPresenter";
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        TextView textView = new TextView(parent.getContext());
        return new ViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
       TextView textView = (TextView) viewHolder.view;
        ViewGroup pView = (ViewGroup) textView.getParent();
        ShadowOverlayContainer container = (ShadowOverlayContainer)pView;
        pView.setBackgroundColor(Color.TRANSPARENT);
        Log.i(TAG, "onBindViewHolder->" + container.getShadowType());
        textView.setText(item.toString());

    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }
}
