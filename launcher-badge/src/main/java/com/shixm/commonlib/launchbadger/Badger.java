package com.shixm.commonlib.launchbadger;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

public abstract class Badger {

    /**
     * @param context application context
     * @param notification 更新角标一般都是和发送notification并行的。如果不想发notification只是更新角标，这里传null（小米手机除外）
     * @param notificationId 消息ID
     * @param currentCount 当前消息的数量
     * @param totalCount 当前程序消息的总数量
     */
    public abstract void executeBadge(Context context, Notification notification, int notificationId, int currentCount, int totalCount);

    public abstract List<String> getSupportLaunchers();

    /**
     * 获取当前app 的启动页面activity的classname
     * @param context
     * @return
     */
    protected static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        // To limit the components this Intent will resolve to, by setting an
        // explicit package name.
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // Launcher activity must be found!
        ResolveInfo info = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        // get a ResolveInfo containing ACTION_MAIN, CATEGORY_LAUNCHER
        // if there is no Activity which has filtered by CATEGORY_DEFAULT
        if (info == null) {
            info = packageManager.resolveActivity(intent, 0);
        }
        return info.activityInfo.name;
    }

}
