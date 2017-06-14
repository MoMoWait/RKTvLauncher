package com.rockchips.tvlauncher.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rockchips.tvlauncher.R;
import com.rockchips.tvlauncher.bean.AppItem;
import com.rockchips.tvlauncher.data.ConstData;

import momo.cn.edu.fjnu.androidutils.utils.SizeUtils;

/**
 * ImageCard Presenter
 *
 * @author jacky
 * @version v1.0
 * @since 16/7/16
 */
public class AppCardPresenter extends Presenter {

    private Context mContext;
    private int mCardWidth;
    private int mCardHeight;
    private Drawable mDefaultCardImage;
    private boolean mIsHotApp;
    public AppCardPresenter(boolean isHotApp){
       mIsHotApp = isHotApp;
        if(!isHotApp){
            mCardWidth = ConstData.COMMON_CARD_WIDTH;
            mCardHeight = ConstData.COMMON_CARD_HEIGHT;
        }else{
            mCardWidth = ConstData.HOTAPP_CARD_WIDTH;
            mCardHeight = ConstData.HOTAPP_CARD_HEIGHT;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        mDefaultCardImage = mContext.getResources().getDrawable(R.drawable.pic_default);
        ImageCardView cardView = new ImageCardView(mContext) {
            @Override
            public void setSelected(boolean selected) {
                int selected_background = mContext.getResources().getColor(R.color.detail_background);
                int default_background = mContext.getResources().getColor(R.color.default_background);
                int color = selected ? selected_background : default_background;
                findViewById(R.id.info_field).setBackgroundColor(color);
                super.setSelected(selected);
            }
        };
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setMainImageDimensions(mCardWidth, mCardHeight);
        AppItem appBean = (AppItem) item;
        cardView.setMainImageScaleType(ImageView.ScaleType.CENTER_INSIDE);
        cardView.getMainImageView().setImageDrawable(appBean.getAppIcon());
        cardView.setTitleText(appBean.getAppName());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }
}
