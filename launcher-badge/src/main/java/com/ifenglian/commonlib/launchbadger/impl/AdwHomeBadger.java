package com.ifenglian.commonlib.launchbadger.impl;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;

import com.ifenglian.commonlib.launchbadger.Badger;

import java.util.Arrays;
import java.util.List;

public class AdwHomeBadger extends Badger {

    public static final String INTENT_UPDATE_COUNTER = "org.adw.launcher.counter.SEND";
    public static final String PACKAGENAME = "PNAME";
    public static final String COUNT = "COUNT";

    @Override
    public void executeBadge(Context context, Notification notification, int notificationId, int currentCount, int totalCount) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }

        Intent intent = new Intent(INTENT_UPDATE_COUNTER);
        intent.putExtra(PACKAGENAME, context.getPackageName());
        intent.putExtra(COUNT, totalCount);
        context.sendBroadcast(intent);
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "org.adw.launcher",
                "org.adwfreak.launcher"
        );
    }
}
