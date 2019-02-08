package com.coursework.tuan.courseworkfinal;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class App extends Application {
    public static final String CHANNEL_ID = "channel_running_tracker";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    public void createNotificationChannel(){
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Running Tracker Service Channel";
            String description = "Final Coursework G53MDP";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            notificationManager.createNotificationChannel(channel);
        }
    }

    //this function is used for converting seconds to hh:mm:ss format
    public static String secondFormatString(long seconds){
        long s = seconds % 60;
        long h = seconds / 60;
        long m = h % 60;
        h = h / 60;
        return String.format("%02d", h) + ":" + String.format("%02d", m) + ":" + String.format("%02d", s);
    }

    //this function is used for converting date in "String" data type to "Date" data type
    public static java.util.Date convertStringToDate(String dateString){
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
