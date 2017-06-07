
package com.rockchips.tvlauncher.function;

import android.content.Context;
import android.content.Intent;

import com.rockchips.tvlauncher.R;
import com.rockchips.tvlauncher.app.AppUninstall;
import com.rockchips.tvlauncher.bean.FunctionItem;

import java.util.ArrayList;
import java.util.List;

public class FunctionModel {

    private int icon;
    private String id;
    private String name;
    private Intent mIntent;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public void setIntent(Intent intent) {
        mIntent = intent;
    }

    public static List<FunctionModel> getFunctionList(Context context) {
        List<FunctionModel> functionModels = new ArrayList<>();

        FunctionModel appUninstall = new FunctionModel();
        appUninstall.setName("应用卸载");
        appUninstall.setIcon(R.drawable.ic_app_uninstall);
        appUninstall.setIntent(new Intent(context, AppUninstall.class));

        functionModels.add(appUninstall);

        return functionModels;
    }

    public static List<FunctionItem> getFunctionItems(Context context) {
        List<FunctionItem> functionItems = new ArrayList<>();
        FunctionItem uninstallItem = new FunctionItem();
        uninstallItem.setTitle("卸载");
        uninstallItem.setImgResID(R.drawable.ic_app_uninstall);
        functionItems.add(uninstallItem);
        FunctionItem settingsItem = new FunctionItem();
        settingsItem.setTitle("设置");
        settingsItem.setImgResID(R.drawable.ic_settings_settings);
        functionItems.add(settingsItem);
        return functionItems;
    }
}
