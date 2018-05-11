package com.example.sqhan.artwork.permission;

import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

/**
 * @author yfchu
 * @date 2016/11/21
 */

public class NotificationPermissionUtils {

    public static boolean areNotificationsEnabled(Context context) {
        if (context == null) {
            return true;
        }
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }

}
