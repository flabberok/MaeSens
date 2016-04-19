package by.maesens.android.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import by.maesens.android.R;
import by.maesens.android.ui.activity.BaseActivity;

/**
 * Created by Никита on 13.04.2016.
 */
public class MaesensGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.d("MaesensGcmListener", "onMessageReceived");

        String messageTitle = data.getString("title") + " от " + data.getString("user");
        String messageBody = data.getString("message");

        Intent i = new Intent(this, BaseActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(0, notification);
    }
}
