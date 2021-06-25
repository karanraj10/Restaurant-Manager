package com.project.restaurantmanager.Controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.project.restaurantmanager.Model.MainActivity;
import com.project.restaurantmanager.R;

public class FirebaseMessageService extends FirebaseMessagingService {
    public FirebaseMessageService() {
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        SharedPreferencesHandler sharedPreferencesHandler = new SharedPreferencesHandler(getApplicationContext());
        sharedPreferencesHandler.setToken(s);

        Log.d("firebasetoken", "onNewToken: "+s);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

            Intent intent = null;

            intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("s",true);

            PendingIntent pending=PendingIntent.getActivity(getApplicationContext(),0,intent, PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationChannel channel = new NotificationChannel("ID", "CHANNEL1", NotificationManager.IMPORTANCE_DEFAULT);

            Notification.Builder builder = new Notification.Builder(getApplicationContext())
                    .setChannelId("ID")
                    .setSmallIcon(R.mipmap.icon)
                    .setContentIntent(pending)
                    .setAutoCancel(true)
                    .setOngoing(false)
                    .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.notification))
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setContentTitle(remoteMessage.getNotification().getTitle());


            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            Notification notification = builder.build();
            channel.setSound(Uri.parse("android.resource://"
                    + getApplicationContext().getPackageName() + "/" + R.raw.notification), audioAttributes);

            notification.flags = Notification.FLAG_AUTO_CANCEL;

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            manager.notify(1, notification);

    }

    public static void removeTopic(String user,String id)
    {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(user+id);
    }
}
