package com.ifenglian.commonlib.launcherbadgeutil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ifenglian.commonlib.launchbadger.BadgeUtil;

/**
 * Created by shixm on 2016/10/13.
 */

public class Receiver extends BroadcastReceiver {

    private static final String TAG = "Receiver";
    public static final String ENTER_ACTION = "com.ifenglian.commonlib.launchbadger.ENTER_ACTION";
    public static final String DELETE_ACTION = "com.ifenglian.commonlib.launchbadger.deleteIntent";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Receiver onReceive() ");
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            switch (action) {
                case ENTER_ACTION:
                    BadgeUtil.cancelAll(context, Util.getNotificationManager(context));
                    Intent newIntent;
                    newIntent = new Intent(context, MainActivity.class);
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    newIntent.putExtras(intent.getExtras());
                    context.startActivity(newIntent);
                    break;
                case DELETE_ACTION:
                    int notifyId = intent.getIntExtra(Util.NOTIFY_ID, 0);
                    Log.d(TAG, "notifyId="+notifyId);
                    BadgeUtil.removeMapNotify(notifyId);
                    break;
            }
        }
    }
}
