package com.example.bestaveiro.appcurso.Messaging;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Ricardo on 19/07/2016.
 */
public class HandleMessages extends FirebaseMessagingService
{
    static String className = "HandleMessages";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        Log.d(className, "mensagem recebida");
    }
}
