package com.example.todo.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationPublisher notificationHelper = new NotificationPublisher(context);
        notificationHelper.createNotification();

    }
}
