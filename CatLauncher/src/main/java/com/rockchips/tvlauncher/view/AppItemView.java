package com.rockchips.tvlauncher.view;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v17.leanback.widget.ShadowOverlayContainer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.rockchips.tvlauncher.R;
import com.rockchips.tvlauncher.bean.AppItem;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import momo.cn.edu.fjnu.androidutils.utils.SizeUtils;

/**
 * Created by GaoFei on 2017/6/7.
 */

public class AppItemView extends RelativeLayout {

    @ViewInject(R.id.img_app_icon)
    private ImageView mImgAppIcon;
    @ViewInject((R.id.text_app_name))
    private TextView mTextAppName;

    public AppItemView(Context context){
        super(context);
        init(context);
    }
    private void init(Context context){
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_item_app, this, false);
        addView(itemView);
        x.view().inject(this, this);
    }

    public void update(AppItem item){
        mImgAppIcon.setImageDrawable(item.getAppIcon());
        mTextAppName.setText(item.getAppName());
        int roundRadius = SizeUtils.dp2px(10);
        int fillColor = item.getBackColor();
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        setBackground(gd);
    }

}
