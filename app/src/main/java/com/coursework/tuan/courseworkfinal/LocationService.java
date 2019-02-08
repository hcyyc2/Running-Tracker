package com.coursework.tuan.courseworkfinal;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class LocationService extends Service {


    private final int NOTIFICATION_ID = 123;
    LocationManager locationManager;
    LocationListener locationListener;
    Location currentLocation = null;
    float totalMetres;
    float currentSpeed = 0;
    CountDownTimer timer;
    boolean isTracking;//to know when to disable all buttons on MainActivity
    long seconds;
    float index = 1;
    float avgSpeed =0;


    // Binder interface given to clients
    private final IBinder mBinder = new MyBinder();
    public class MyBinder extends Binder {
        LocationService getService() {
            return LocationService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        //this 'if command' executes when users tap on 'Stop Tracking' button on notification bar
        Boolean isStopTracking =  intent.getBooleanExtra("isTracking",true);
        if(!isStopTracking) {
            stopTracking();
            return Service.START_REDELIVER_INTENT;
        }

        isTracking = true;

        initializeTimer();
        timer.start();

        //create locationListener
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(currentLocation!=null) {

                    //distance between 2 locations
                    float metre = currentLocation.distanceTo(location);

                    //time difference between 2 locations
                    long millis = location.getTime()-currentLocation.getTime();
                    //convert millis to second format
                    float time = TimeUnit.MILLISECONDS.toSeconds(millis);


                    //calculate current speed (m/s)
                    currentSpeed = metre/time;
                    //calculate average speed (m/s)
                    avgSpeed = (avgSpeed+currentSpeed)/index++;
                    //total running metres
                    totalMetres += metre;

                    //send broadcast to display data on UI
                    Intent intent = new Intent();
                    intent.putExtra("metre",roundUp(totalMetres));
                    intent.putExtra("currentSpeed",roundUp(currentSpeed));
                    intent.putExtra("avgSpeed",roundUp(avgSpeed));
                    intent.setAction("metres_speed");
                    sendBroadcast(intent);
                }

                currentLocation = location;

                //create notification then create a foreground service
                startForeground(NOTIFICATION_ID,updatedNotification(totalMetres));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("g53mdp", "onStatusChanged: " + provider + " " + status);
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
        } catch(SecurityException e) {
            Log.d("g53mdp", e.toString());
        }

        return Service.START_STICKY;
    }
    public Notification updatedNotification(float totalMetres){

        Intent notificationIntent = new Intent(LocationService.this,MainActivity.class);
        PendingIntent content = PendingIntent.getActivity(LocationService.this,0, notificationIntent,0);

        //send the pending intent to itself to stop tracking(the 'if command' at line 62).
        Intent broadcastIntent = new Intent(this,LocationService.class);
        broadcastIntent.putExtra("isTracking",false);
        PendingIntent pendingIntent = PendingIntent.getService(this,0,broadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        //create notification
        NotificationCompat.Builder notification = new NotificationCompat.Builder(LocationService.this,App.CHANNEL_ID)
                .setSmallIcon(R.drawable.running)
                .setContentTitle("Running Tracker is tracking your activity")
                .setContentText("You have run "+ roundUp(totalMetres) + " meters so far")
                .setContentIntent(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher,"Stop Tracking",pendingIntent);
       return notification.build();
    }

    public void initializeTimer(){

        final long totalSeconds = 10800; // 3 hours max
        final long intervalSeconds = 1;

        timer = new CountDownTimer(totalSeconds * 1000, intervalSeconds * 1000) {

            public void onTick(long millisUntilFinished) {

                //reverse counting down timer to counting up timer
                seconds = (totalSeconds * 1000 - millisUntilFinished) / 1000;

                //convert to hh:mm:ss format
                String formatString=App.secondFormatString(seconds);


                //send broadcast to display data on UI
                Intent intent = new Intent();
                intent.putExtra("totalTime",formatString);
                intent.setAction("timer");
                sendBroadcast(intent);
            }

            public void onFinish() {
                stopTracking();
            }

        };
    }


    public void stopTracking(){
        isTracking = false;

        //data to send to SaveActivity
        Bundle bundle = new Bundle();
        bundle.putLong("timer", seconds );
        bundle.putString("metre",roundUp(totalMetres));
        bundle.putString("avgSpeed",roundUp(avgSpeed));

        //stop timer
        timer.cancel();

        //remove update of location manager, stop service, stop update notifications
        locationManager.removeUpdates(locationListener);
        stopForeground(true);
        stopSelf();

        //start SaveActivity
        Intent i = new Intent(getApplicationContext(), SaveActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtras(bundle);
        startActivity(i);
    }

    public String getTotalMetres() {
        return roundUp(totalMetres);
    }

    public String getCurrentSpeed() {
        return roundUp(currentSpeed);
    }

    public String getAvgSpeed() {
        return roundUp(avgSpeed);
    }

    //round up float number
    public String roundUp(float num){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(num);
    }
}
