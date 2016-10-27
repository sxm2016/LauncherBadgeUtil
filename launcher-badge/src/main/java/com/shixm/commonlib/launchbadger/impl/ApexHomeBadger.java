package com.shixm.commonlib.launchbadger.impl;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;

import com.shixm.commonlib.launchbadger.Badger;

import java.util.Arrays;
import java.util.List;

public class ApexHomeBadger extends Badger {

    private static final String INTENT_UPDATE_COUNTER = "com.anddoes.launcher.COUNTER_CHANGED";
    private static final String PACKAGENAME = "package";
    private static final String COUNT = "count";
    private static final String CLASS = "class";

    @Override
    public void executeBadge(Context context, Notification notification, int notificationId, int currentCount, int totalCount) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }

        Intent intent = new Intent(INTENT_UPDATE_COUNTER);
        intent.putExtra(PACKAGENAME, context.getPackageName());
        intent.putExtra(COUNT, totalCount);
        intent.putExtra(CLASS, launcherClassName);
        context.sendBroadcast(intent);
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.anddoes.launcher");
    }
}
