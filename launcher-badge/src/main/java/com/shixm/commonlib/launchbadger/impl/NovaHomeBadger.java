package com.shixm.commonlib.launchbadger.impl;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.shixm.commonlib.launchbadger.Badger;

import java.util.Arrays;
import java.util.List;

public class NovaHomeBadger extends Badger {

    private static final String CONTENT_URI = "content://com.teslacoilsw.notifier/unread_count";
    private static final String COUNT = "count";
    private static final String TAG = "tag";

    @Override
    public void executeBadge(Context context, Notification notification, int notificationId, int currentCount, int totalCount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG, context.getPackageName() + "/" +
                getLauncherClassName(context));
        contentValues.put(COUNT, totalCount);
        try {
            context.getContentResolver().insert(Uri.parse(CONTENT_URI),
                    contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.teslacoilsw.launcher");
    }
}
