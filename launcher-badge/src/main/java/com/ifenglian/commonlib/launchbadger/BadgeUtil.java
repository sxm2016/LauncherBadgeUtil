package com.ifenglian.commonlib.launchbadger;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BadgeUtil {

    private static final String TAG = BadgeUtil.class.getSimpleName();
    private static Map<Integer, Notification> mapNotify = new LinkedHashMap<>();
    private static Badger badger;

    public synchronized static void initBadgeNotification(NotificationManager notificationManager, Notification notification, int notifyID, Context context, int currentCount, int totalCount) {
        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            BadgeUtil.addElement(notifyID, notification);
            List<Integer> lst = new ArrayList<>();
            for (Map.Entry<Integer, Notification> entry : mapNotify.entrySet()) {
                int key = entry.getKey();
                if (key != notifyID) {
                    Notification value = entry.getValue();
                    initBadgeNotification(value, key, context.getApplicationContext(), 0, 0);
                    notificationManager.notify(key, value);
                    lst.add(key);
                }
            }
            for (Integer i:lst) {
                mapNotify.remove(i);
            }
        }
        BadgeUtil.initBadgeNotification(notification, notifyID, context.getApplicationContext(), currentCount, totalCount);
    }

    private static void initBadgeNotification(Notification notification, int notifyID, Context applicationContext, int currentCount, int totalCount) {
        String currentHomePackage = getLauncherName(applicationContext);
        Log.d(TAG, "currentHomePackage:" + currentHomePackage);
        if (badger == null)
            badger = BadgerType.getBadgerByLauncherName(currentHomePackage);
        currentCount = currentCount>99?99:currentCount;
        totalCount = totalCount>999?999:totalCount;
        badger.executeBadge(applicationContext, notification, notifyID, currentCount, totalCount);
    }

    /**
     * 清空消息通知和桌面图标角标
     * @param notificationManager
     * @param context
     */
    public static void cancelAll(final Context context, final NotificationManager notificationManager) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cancel(notificationManager, context, null, null);
            }
        }, 100);
    }

    /**
     * 清空消息通知和桌面图标角标
     * 调用该方法，需要满足如下条件：
     * 1、只能获取消息总数
     * 2、服务与app在不同的进程
     * 3、该方法在主进程中调用
     * @param notificationManager
     * @param context applicationContext
     * @param action 消息删除的动作
     * @param clazz 广播接收者
     */
    public static void cancelAll(final Context context, final NotificationManager notificationManager, final String action, final Class<?> clazz) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cancel(notificationManager, context, action, clazz);
            }
        }, 100);
    }

    private static void cancel(NotificationManager notificationManager, Context context, String action, Class<?> clazz) {
        notificationManager.cancelAll();
        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            mapNotify.clear();
            if (clazz != null && action != null) {
                Intent intent = new Intent(action);
                intent.setClass(context, clazz);
                context.sendBroadcast(intent);
            }
        } else {
            // 三星S6手机需要reset一下badge
            resetBadgeCount(context);
        }
    }

    /**
     * 重置、清除Badge未读显示数<br/>
     *
     * @param context
     */
    private static void resetBadgeCount(Context context) {
        initBadgeNotification(null, 0, context, 0, 0);
    }

    /**
     * 获取手机的launcher name
     *
     * @param context
     * @return
     */
    private static String getLauncherName(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo != null)
            return resolveInfo.activityInfo.packageName;
        else
            return "";
    }

    /**
     * 添加（如果map中有改key，则删除该key对应的元素，并重新添加）
     * @param key
     */
    private static void addElement(Integer key, Notification notification) {
        if (mapNotify.containsKey(key)) {
            if (getLastKey() != key) {
                mapNotify.remove(key);
            }
        }
        mapNotify.put(key, notification);
    }

    /**
     * 得到最后一个key
     * map的长度为0时候，返回0
     * @return
     */
    private synchronized static int getLastKey() {
        Set<Integer> set = mapNotify.keySet();
        if (set.size() == 0) {
            return 0;
        }
        return (int) set.toArray()[set.size() - 1];
    }

    public static Map<Integer, Notification> getMapNotify() {
        return mapNotify;
    }

    public static Notification removeMapNotify(Integer notifyId) {
        if (mapNotify.containsKey(notifyId)) {
            return mapNotify.remove(notifyId);
        }
        return null;
    }
}
