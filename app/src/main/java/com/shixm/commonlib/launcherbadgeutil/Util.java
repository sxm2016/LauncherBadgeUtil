package com.shixm.commonlib.launcherbadgeutil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.shixm.commonlib.launchbadger.BadgeUtil;

/**
 * Created by shixm on 2016/10/13.
 */
public class Util {
    private static final String TAG = "Util";
    public static final String NOTIFY_ID = "notify_id";
    private static NotificationManager notificationManager;

    public static void showNotification(Context context, int notifyID, int currentCount, int totalCount) {
        Log.d(TAG, "showNotification sameId = " + notifyID);
        String notificationTitle = "LauncherBadgeUtil";
        try {
            int defaults = Notification.DEFAULT_ALL;
            CharSequence notificationTicker = "notificationTicker_notifyid=" + notifyID;
            CharSequence notificationContent = "notificationContent_notifyid=" + notifyID;
            Intent enterIntent = new Intent(Receiver.ENTER_ACTION);
            enterIntent.setClass(context, Receiver.class);
            PendingIntent enterPendingIntent = PendingIntent.getBroadcast(context,
                    0, enterIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Intent deleteIntent = new Intent(Receiver.DELETE_ACTION);
            deleteIntent.setClass(context, Receiver.class);
            deleteIntent.putExtra(NOTIFY_ID, notifyID);
            PendingIntent deletePendingIntent = PendingIntent.getBroadcast(
                    context, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationContent)
                    .setTicker(notificationTicker)
                    .setAutoCancel(true).setDefaults(defaults)
                    .setContentIntent(enterPendingIntent)
                    .setDeleteIntent(deletePendingIntent);

            Notification notification = builder.build();
            Log.d(TAG, "notifyID=" + notifyID + " showNotification currentCount=" + currentCount+" totalCount="+totalCount);
            BadgeUtil.initBadgeNotification(getNotificationManager(context), notification, notifyID, context, currentCount, totalCount);
            getNotificationManager(context).notify(notifyID, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static NotificationManager getNotificationManager(Context context) {
        if (notificationManager == null) {
            synchronized (Util.class) {
                if (notificationManager == null) {
                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                }
            }
        }
        return notificationManager;
    }
}
