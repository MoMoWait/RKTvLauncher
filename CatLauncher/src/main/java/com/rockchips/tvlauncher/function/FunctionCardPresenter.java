package com.rockchips.tvlauncher.function;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rockchips.tvlauncher.R;
import com.rockchips.tvlauncher.bean.FunctionItem;
import com.rockchips.tvlauncher.data.ConstData;
import com.rockchips.tvlauncher.view.FunctionItemView;

/**
 * ImageCard Presenter
 *
 * @author jacky
 * @version v1.0
 * @since 16/7/16
 */
public class FunctionCardPresenter extends Presenter {

    private Context mContext;
    private int CARD_WIDTH = ConstData.COMMON_CARD_WIDTH;
    private int CARD_HEIGHT = ConstData.COMMON_CARD_HEIGHT;
    private Drawable mDefaultCardImage;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        FunctionItemView itemView = new FunctionItemView(mContext);
        itemView.setFocusable(true);
        itemView.setFocusableInTouchMode(true);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        FunctionItemView itemView = (FunctionItemView) viewHolder.view;
        FunctionItem functionItem = (FunctionItem) item;
        itemView.setImgRes(functionItem.getImgResID());
        itemView.setTitle(functionItem.getTitle());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

}
