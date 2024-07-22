package com.example.smsreadsend;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class App extends Application {
    public static final String CHANNEL_1_ID="channel1";
    public static final String CHANNEL_2_ID="channel2";
    public static final String CHANNEL_3_ID="channel3";
    public static final String CHANNEL_ID="channel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        NotificationChannel channel =new NotificationChannel(
                CHANNEL_ID,
                "FG",
                NotificationManager.IMPORTANCE_HIGH
        );
        channel.setDescription("This is about FG");
        NotificationChannel channel1 =new NotificationChannel(
                CHANNEL_1_ID,
                "Contacts",
                NotificationManager.IMPORTANCE_HIGH
        );
        channel1.setDescription("This is about Contacts");
        NotificationChannel channel2 =new NotificationChannel(
                CHANNEL_2_ID,
                "Location",
                NotificationManager.IMPORTANCE_HIGH
        );
        channel2.setDescription("This is about Location");
        NotificationChannel channel3 =new NotificationChannel(
                CHANNEL_3_ID,
                "Lock",
                NotificationManager.IMPORTANCE_HIGH
        );
        channel2.setDescription("This is about Locking");
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
        manager.createNotificationChannel(channel1);
        manager.createNotificationChannel(channel2);
        manager.createNotificationChannel(channel3);
    }
}
