package com.rockchips.tvlauncher.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by GaoFei on 2017/6/8.
 */

public class AppItem {
    private String appName;
    private String prvPath;
    private int backColor;
    private Drawable appIcon;
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPrvPath() {
        return prvPath;
    }

    public void setPrvPath(String prvPath) {
        this.prvPath = prvPath;
    }

    public int getBackColor() {
        return backColor;
    }

    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    @Override
    public String toString() {
        return "AppItem{" +
                "appName='" + appName + '\'' +
                ", prvPath='" + prvPath + '\'' +
                ", backColor=" + backColor +
                ", appIcon=" + appIcon +
                '}';
    }
}
