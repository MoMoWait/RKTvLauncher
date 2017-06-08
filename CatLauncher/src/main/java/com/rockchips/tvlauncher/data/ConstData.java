package com.rockchips.tvlauncher.data;

import android.graphics.Color;

import momo.cn.edu.fjnu.androidutils.utils.SizeUtils;

/**
 * Created by GaoFei on 2017/6/6.
 */

public class ConstData {
    public static final int COMMON_CARD_WIDTH = SizeUtils.dp2px(255);
    public static final int COMMON_CARD_HEIGHT = SizeUtils.dp2px(150);
    public static final int HOTAPP_CARD_WIDTH = SizeUtils.dp2px(382);
    public static final int HOTAPP_CARD_HEIGHT = SizeUtils.dp2px(224);
    /**
     * APP栏显示个数
     */
    public static final int APP_ROW_COUNT = 10;
    public interface NetWorkState{
        int NO = 0;
        int WIFI = 1;
        int ETHERNET = 2;
    }
    public static final int[] APP_ITEM_BACK_COLORS = { Color.parseColor("#f4ea2a"),
    Color.parseColor("#1afa29"), Color.parseColor("#e26a00"), Color.parseColor("#0459d0"),
    Color.parseColor("#26eefd"), Color.parseColor("#f92812")};
}
