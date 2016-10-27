package com.shixm.commonlib.launchbadger.impl;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.shixm.commonlib.launchbadger.Badger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class XiaomiHomeBadger extends Badger {
    public static final String TAG = "XiaomiHomeBadger";
    @Override
    public synchronized void executeBadge(Context context, Notification notification, int notificationId, int currentCount, int totalCount) {
        boolean isMiUIV6 = true;
        try {
            Class miuiNotificationClass = Class.forName("android.app.MiuiNotification");
            Object miuiNotification = miuiNotificationClass.newInstance();
            Field field = miuiNotification.getClass().getDeclaredField("messageCount");
            Log.d(TAG, "notificationId="+notificationId+" currentCount="+currentCount);
            field.setAccessible(true);
            field.set(miuiNotification, currentCount);
            field = notification.getClass().getField("extraNotification");
            field.setAccessible(true);
            field.set(notification, miuiNotification);
        } catch (Exception e) {
            Log.d(TAG, "e="+e.getMessage());
            isMiUIV6 = false;
            e.printStackTrace();
        }
        if (!isMiUIV6) {
            Log.d(TAG, "isMiUIV6="+isMiUIV6);
            ComponentName sComponentName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent();
            Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
            localIntent.putExtra("android.intent.extra.update_application_component_name", sComponentName.getPackageName() + "/"+ sComponentName.getClassName());
            localIntent.putExtra("android.intent.extra.update_application_message_text",currentCount);
            context.sendBroadcast(localIntent);
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "com.miui.miuilite",
                "com.miui.home",
                "com.miui.miuihome",
                "com.miui.miuihome2",
                "com.miui.mihome",
                "com.miui.mihome2"
        );
    }
}
