package com.cypoem.idea.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.LoginActivity;
import com.cypoem.idea.activity.MainActivity;


/**
 * <pre>
 *     author : dell
 *     time   : 2017/05/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class NotificationOperation {
    private static NotificationManager manager=null;
    private static int requestCode=0;

    private static void showNotification(Context context, String showContent, int showType){
        Intent intent;
        if(UserInfoTools.getIsLogin(context)){
            intent=new Intent(context, MainActivity.class);
        }else {
            intent=new Intent(context, LoginActivity.class);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        requestCode++;
        Notification n=getNotification(context,showContent,showType,pendingIntent);
        NotificationManager nm=getNotificationManager(context);
        nm.notify(showType,n);
    }
    private static Notification getNotification(Context context, String showContent, int showType, PendingIntent pendingIntent){
        Notification.Builder builder=new Notification.Builder(context)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setContentText(showContent)
                .setDefaults(Notification.DEFAULT_ALL);
        String title="";
        if (showType==1){
            title="";
        }else if (showType==2){
            title="";
        }
        builder.setContentTitle(title);
        return builder.build();
    }
    private static NotificationManager getNotificationManager(Context context){
        if (manager==null){
            manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }


}
