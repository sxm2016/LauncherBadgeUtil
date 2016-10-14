package com.ifenglian.commonlib.launchbadger.impl;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;

import com.ifenglian.commonlib.launchbadger.Badger;

import java.util.Arrays;
import java.util.List;

public class SamsungHomeBadger extends Badger {

    @Override
    public void executeBadge(Context context, Notification notification, int notificationId, int currentCount, int totalCount) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", totalCount);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "com.sec.android.app.launcher",
                "com.sec.android.app.twlauncher"
        );
    }
}
