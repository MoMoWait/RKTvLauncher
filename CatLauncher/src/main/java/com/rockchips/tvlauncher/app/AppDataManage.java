
package com.rockchips.tvlauncher.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;

import com.rockchips.tvlauncher.bean.AppItem;
import com.rockchips.tvlauncher.data.ConstData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class AppDataManage {

    private final Context mContext;

    public AppDataManage(Context context) {
        mContext = context;
    }
    private static final List<String> HOT_APPS = Arrays.asList("com.rockchips.mediacenter", "org.xbmc.rkmc");
    private static final List<String> FILTER_APPS = Arrays.asList("com.rockchips.tvlauncher", "com.android.tv.settings");
    public ArrayList<AppModel> getLaunchAppList(boolean isHot) {
        PackageManager localPackageManager = mContext.getPackageManager();
        Intent localIntent = new Intent("android.intent.action.MAIN");
        localIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> localList = localPackageManager.queryIntentActivities(localIntent, 0);
        ArrayList<AppModel> localArrayList = null;
        Iterator<ResolveInfo> localIterator = null;
        localArrayList = new ArrayList<>();
        if (localList.size() != 0) {
            localIterator = localList.iterator();
        }
        while (true) {
            if (!localIterator.hasNext())
                break;
            ResolveInfo localResolveInfo =  localIterator.next();
            if(isHot && !HOT_APPS.contains(localResolveInfo.activityInfo.packageName))
                continue;
            if(FILTER_APPS.contains(localResolveInfo.activityInfo.packageName))
                continue;
            AppModel localAppBean = new AppModel();
            localAppBean.setIcon(localResolveInfo.activityInfo.loadIcon(localPackageManager));
            localAppBean.setName(localResolveInfo.activityInfo.loadLabel(localPackageManager).toString());
            localAppBean.setPackageName(localResolveInfo.activityInfo.packageName);
            localAppBean.setDataDir(localResolveInfo.activityInfo.applicationInfo.publicSourceDir);
            localAppBean.setLauncherName(localResolveInfo.activityInfo.name);
            String pkgName = localResolveInfo.activityInfo.packageName;
            PackageInfo mPackageInfo;
            try {
                mPackageInfo = mContext.getPackageManager().getPackageInfo(pkgName, 0);
                if ((mPackageInfo.applicationInfo.flags & mPackageInfo.applicationInfo.FLAG_SYSTEM) > 0) {// 系统预装
                    localAppBean.setSysApp(true);
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            if (!localAppBean.getPackageName().equals("com.jacky.launcher")) {
                localArrayList.add(localAppBean);
            }
        }
        return localArrayList;
    }

    public ArrayList<AppItem> getLaunchAppItems(boolean isHot) {
        PackageManager localPackageManager = mContext.getPackageManager();
        Intent localIntent = new Intent("android.intent.action.MAIN");
        localIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> localList = localPackageManager.queryIntentActivities(localIntent, 0);
        ArrayList<AppItem> localArrayList = null;
        Iterator<ResolveInfo> localIterator = null;
        localArrayList = new ArrayList<>();
        Random random = new Random();
        if (localList.size() != 0) {
            localIterator = localList.iterator();
        }
        if(localIterator == null)
            return localArrayList;
        while (true) {
            if (!localIterator.hasNext())
                break;
            ResolveInfo localResolveInfo =  localIterator.next();
            if(isHot && !HOT_APPS.contains(localResolveInfo.activityInfo.packageName))
                continue;
            if(FILTER_APPS.contains(localResolveInfo.activityInfo.packageName))
                continue;
            AppItem appItem = new AppItem();
            appItem.setAppIcon(localResolveInfo.activityInfo.loadIcon(localPackageManager));
            appItem.setAppName(localResolveInfo.activityInfo.loadLabel(localPackageManager).toString());
            appItem.setBackColor(ConstData.APP_ITEM_BACK_COLORS[random.nextInt(ConstData.APP_ITEM_BACK_COLORS.length)]);
            appItem.setPkgName(localResolveInfo.activityInfo.packageName);
            appItem.setActivityName(localResolveInfo.activityInfo.name);
            localArrayList.add(appItem);
        }
        return localArrayList;
    }

    public ArrayList<AppModel> getHotLauncherAppList() {
        PackageManager localPackageManager = mContext.getPackageManager();
        Intent localIntent = new Intent("android.intent.action.MAIN");
        localIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> localList = localPackageManager.queryIntentActivities(localIntent, 0);
        ArrayList<AppModel> localArrayList = null;
        Iterator<ResolveInfo> localIterator = null;
        localArrayList = new ArrayList<>();
        if (localList.size() != 0) {
            localIterator = localList.iterator();
        }
        while (true) {
            if (!localIterator.hasNext())
                break;
            ResolveInfo localResolveInfo = (ResolveInfo) localIterator.next();
            AppModel localAppBean = new AppModel();
            localAppBean.setIcon(localResolveInfo.activityInfo.loadIcon(localPackageManager));
            localAppBean.setName(localResolveInfo.activityInfo.loadLabel(localPackageManager).toString());
            localAppBean.setPackageName(localResolveInfo.activityInfo.packageName);
            localAppBean.setDataDir(localResolveInfo.activityInfo.applicationInfo.publicSourceDir);
            localAppBean.setLauncherName(localResolveInfo.activityInfo.name);
            String pkgName = localResolveInfo.activityInfo.packageName;
            PackageInfo mPackageInfo;
            try {
                mPackageInfo = mContext.getPackageManager().getPackageInfo(pkgName, 0);
                if ((mPackageInfo.applicationInfo.flags & mPackageInfo.applicationInfo.FLAG_SYSTEM) > 0) {// 系统预装
                    localAppBean.setSysApp(true);
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            if (!localAppBean.getPackageName().equals("com.jacky.launcher")) {
                localArrayList.add(localAppBean);
            }
        }
        return localArrayList;
    }

    public ArrayList<AppModel> getUninstallAppList() {
        PackageManager localPackageManager = mContext.getPackageManager();
        Intent localIntent = new Intent("android.intent.action.MAIN");
        localIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> localList = localPackageManager.queryIntentActivities(localIntent, 0);
        ArrayList<AppModel> localArrayList = null;
        Iterator<ResolveInfo> localIterator = null;
        if (localList != null) {
            localArrayList = new ArrayList<>();
            localIterator = localList.iterator();
        }
        while (true) {
            if (!localIterator.hasNext())
                break;
            ResolveInfo localResolveInfo = (ResolveInfo) localIterator.next();
            AppModel localAppBean = new AppModel();
            localAppBean.setIcon(localResolveInfo.activityInfo.loadIcon(localPackageManager));
            localAppBean.setName(localResolveInfo.activityInfo.loadLabel(localPackageManager).toString());
            localAppBean.setPackageName(localResolveInfo.activityInfo.packageName);
            localAppBean.setDataDir(localResolveInfo.activityInfo.applicationInfo.publicSourceDir);
            String pkgName = localResolveInfo.activityInfo.packageName;
            PackageInfo mPackageInfo;
            try {
                mPackageInfo = mContext.getPackageManager().getPackageInfo(pkgName, 0);
                if ((mPackageInfo.applicationInfo.flags & mPackageInfo.applicationInfo.FLAG_SYSTEM) > 0) {// 系统预装
                    localAppBean.setSysApp(true);
                } else {
                    localArrayList.add(localAppBean);
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return localArrayList;
    }

    public ArrayList<AppModel> getAutoRunAppList() {
        PackageManager localPackageManager = mContext.getPackageManager();
        Intent localIntent = new Intent("android.intent.action.MAIN");
        localIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> localList = localPackageManager.queryIntentActivities(localIntent, 0);
        ArrayList<AppModel> localArrayList = null;
        Iterator<ResolveInfo> localIterator = null;
        if (localList != null) {
            localArrayList = new ArrayList<>();
            localIterator = localList.iterator();
        }

        while (true) {
            if (!localIterator.hasNext())
                break;
            ResolveInfo localResolveInfo = localIterator.next();
            AppModel localAppBean = new AppModel();
            localAppBean.setIcon(localResolveInfo.activityInfo.loadIcon(localPackageManager));
            localAppBean.setName(localResolveInfo.activityInfo.loadLabel(localPackageManager).toString());
            localAppBean.setPackageName(localResolveInfo.activityInfo.packageName);
            localAppBean.setDataDir(localResolveInfo.activityInfo.applicationInfo.publicSourceDir);
            String pkgName = localResolveInfo.activityInfo.packageName;
            String permission = "android.permission.RECEIVE_BOOT_COMPLETED";
            try {
                PackageInfo mPackageInfo = mContext.getPackageManager().getPackageInfo(pkgName, 0);
                if ((PackageManager.PERMISSION_GRANTED == localPackageManager.checkPermission(permission, pkgName))
                        && !((mPackageInfo.applicationInfo.flags & mPackageInfo.applicationInfo.FLAG_SYSTEM) > 0)) {
                    localArrayList.add(localAppBean);
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return localArrayList;
    }
}
