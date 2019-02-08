package com.coursework.tuan.courseworkfinal;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    //define 2 Broadcast Receivers
    class DataReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String totalMetres = intent.getStringExtra("metre");
            String currentSpeed= intent.getStringExtra("currentSpeed");
            String avgSpeed = intent.getStringExtra("avgSpeed");

            tvMetres.setText(totalMetres);
            tvCurrentSpeed.setText(currentSpeed);
            tvAvgSpeed.setText(avgSpeed);

        }
    }
    class TimerReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String totalTime = intent.getStringExtra("totalTime");
            tvTimer.setText(totalTime);
        }
    }

    //define variables
    LocationService lService;
    Button startBtn;
    Button stopBtn;
    Button btnDetails;
    TextView tvMetres;
    TextView tvCurrentSpeed;
    TextView tvAvgSpeed;
    TextView tvTimer;

    DataReceiver dataReceiver;
    TimerReceiver timerReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //change background color
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.BLACK);

        //ask for users' location permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        startBtn = findViewById(R.id.playBtn);
        stopBtn = findViewById(R.id.stopBtn);
        btnDetails=findViewById(R.id.btnDetails);
        tvMetres = findViewById(R.id.tvMetres);
        tvCurrentSpeed = findViewById(R.id.tvCurrentSpeed);
        tvAvgSpeed = findViewById(R.id.tvAvgSpeed);
        tvTimer = findViewById(R.id.tvTimer);

        //register 2 broadcast receiver
        dataReceiver = new DataReceiver();
        registerReceiver(dataReceiver,new IntentFilter("metres_speed"));

        timerReceiver = new TimerReceiver();
        registerReceiver(timerReceiver,new IntentFilter("timer"));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {//bind to service if permission is granted
            bindService(new Intent(this, LocationService.class), mConnection, Context.BIND_AUTO_CREATE);
        } else {
            //if not, disable buttons
            startBtn.setEnabled(false);
            stopBtn.setEnabled(false);
            btnDetails.setEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(lService!=null){

        }
    }

    @Override
    protected void onDestroy() {
        if (mConnection != null) {
            unbindService(mConnection);
            mConnection = null;
        }
        unregisterReceiver(dataReceiver);
        unregisterReceiver(timerReceiver);
        super.onDestroy();
    }

    //onClick "Stop" button
    public void stopTracking(View view){
        if(lService.isTracking) {
            stopBtn.setEnabled(false);
            startBtn.setEnabled(false);
            lService.stopTracking();
        }
    }

    // onClick "Play" button
    public void startTracking(View view){

        if(!lService.isTracking) {
            Intent intent = new Intent(getBaseContext(), LocationService.class);
            startService(intent);
        }
    }

    // onClick "listing" icon
    public void viewDetails(View view){
        Intent intent = new Intent(getBaseContext(),DetailActivity.class);
        startActivity(intent);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            //bind to LocationService
            LocationService.MyBinder binder = (LocationService.MyBinder) service;
            lService = binder.getService();

            //update current logging data after rebinding to the Service (e.g: by clicking on notification bar to open MainActivity)
            tvMetres.setText(lService.getTotalMetres().equals("0")?"0.00":lService.getTotalMetres());
            tvCurrentSpeed.setText(lService.getCurrentSpeed().equals("0")?"0.00":lService.getCurrentSpeed());
            tvAvgSpeed.setText(lService.getAvgSpeed().equals("0")?"0.00":lService.getAvgSpeed());

        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            lService = null;
        }
    };
}
