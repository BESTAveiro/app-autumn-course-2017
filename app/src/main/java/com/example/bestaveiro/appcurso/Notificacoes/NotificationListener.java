package com.example.bestaveiro.appcurso.Notificacoes;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.Date;

/**
 * Created by Ricardo on 19/08/2016.
 */
public class NotificationListener extends NotificationListenerService
{
    String className = "NotificationListener";
    public static boolean hasAccess = false;


    @Override
    public void onNotificationPosted(StatusBarNotification sbn)
    {
        Log.d(className, "onNotificationPosted");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Log.d(className, String.format("package : %s", sbn.getPackageName()));
            if(sbn.getPackageName().equals("com.example.bestaveiro.appcurso"))
            {
                Notification not = sbn.getNotification();
                Log.d(className, String.format("ticker : %s", not.tickerText));
                Bundle extras = not.extras;
                Log.d(className, String.format("title : %s", extras.getString("android.title")));
                Log.d(className, String.format("text : %s", extras.getString("android.text")));

                Notificacao tmp = new Notificacao(new Date(), extras.getString("android.text"), extras.getString("android.title"));
                NotificacoesDB.insert(tmp);
                NotificacoesDB.retrieveAll();
            }
        }
        super.onNotificationPosted(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn)
    {
        Log.d(className, "onNotificationRemoved");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Log.d(className, String.format("package : %s", sbn.getPackageName()));
            if(sbn.getPackageName().equals("com.example.bestaveiro.appcurso"))
            {
                Notification not = sbn.getNotification();
                Log.d(className, String.format("ticker : %s", not.tickerText));
                Bundle extras = not.extras;
                Log.d(className, String.format("title : %s", extras.getString("android.title")));
                Log.d(className, String.format("text : %s", extras.getString("android.text")));
            }
        }
        super.onNotificationRemoved(sbn);
    }

    @Override
    public void onCreate()
    {
        Log.d(className, "onCreate");
        super.onCreate();
    }

    @Override
    public StatusBarNotification[] getActiveNotifications()
    {
        Log.d(className, "getActiveNotifications");
        return super.getActiveNotifications();
    }

    @Override
    public void onListenerConnected()
    {
        Log.d(className, "onListenerConnected");
        super.onListenerConnected();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(className, "onBind");
        hasAccess = true;
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.d(className, "onUnbind");
        hasAccess = false;
        return super.onUnbind(intent);
    }
}
