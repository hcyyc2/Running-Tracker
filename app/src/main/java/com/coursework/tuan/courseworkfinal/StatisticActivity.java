package com.coursework.tuan.courseworkfinal;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class StatisticActivity extends AppCompatActivity {

    TextView tvTotalMetres;
    TextView tvTotalTime;
    TextView tvBestMetres;
    TextView tvBestTime;

    TextView tvDistanceThisWeek;
    TextView tvDistanceLastWeek;
    TextView tvActivityThisWeek;
    TextView tvActivityLastWeek;
    TextView tvDistanceThisYear;
    TextView tvDistanceLastYear;
    TextView tvActivityThisYear;
    TextView tvActivityLastYear;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        setTitle("Statistics");

        tvTotalMetres = findViewById(R.id.tvTotalMetres);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        tvBestMetres = findViewById(R.id.tvBestMetres);
        tvBestTime = findViewById(R.id.tvBestTime);
        tvDistanceThisWeek = findViewById(R.id.tvDThisWeek);
        tvDistanceLastWeek = findViewById(R.id.tvDLastWeek);
        tvActivityThisWeek = findViewById(R.id.tvActivityThisWeek);
        tvActivityLastWeek = findViewById(R.id.tvActivityLastWeek);
        tvDistanceThisYear = findViewById(R.id.tvDThisYear);
        tvDistanceLastYear = findViewById(R.id.tvDLastYear);
        tvActivityThisYear = findViewById(R.id.tvActivityThisYear);
        tvActivityLastYear = findViewById(R.id.tvActivityLastYear);


        tvTotalMetres.setText("TOTAL METRES\n0.00");
        tvTotalTime.setText("TOTAL TIME\n00:00:00");
        tvBestMetres.setText("BEST DISTANCE\n0.00");
        tvBestTime.setText("LONGEST DURATION\n00:00:00");



        //call appropriate queries using URI defined in ActivityProviderContract

        Cursor cursor = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI_TOTALMETRE, null, null, null, null);
        cursor.moveToFirst();
        float totalMetre =  cursor.getFloat(0);
        cursor.close();
        tvTotalMetres.setText("TOTAL METRES\n"+totalMetre);

        Cursor cursor1 = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI_BESTDISTANCE, null, null, null, null);
        cursor1.moveToFirst();
        String bestDistance = cursor1.getString(0);
        cursor1.close();
        tvBestMetres.setText("BEST DISTANCE\n"+bestDistance);


        Cursor cursor2 = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI_LONGESTDURATION, null, null, null, null);
        cursor2.moveToFirst();
        long longestDuration = cursor2.getLong(0);
        tvBestTime.setText("LONGEST DURATION\n"+App.secondFormatString(longestDuration));
        cursor2.close();


        Cursor cursor3 = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI_TOTALTIME, null, null, null, null);
        cursor3.moveToFirst();
        long seconds = cursor3.getLong(0);
        tvTotalTime.setText("TOTAL TIME\n"+App.secondFormatString(seconds));
        cursor3.close();


        Cursor cursor4 = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI_TOTALDISTANCE_THISWEEK, null, null, null, null);
        cursor4.moveToFirst();
        String totalDistance_thisweek = cursor4.getString(0);
        tvDistanceThisWeek.setText(totalDistance_thisweek);
        cursor4.close();


        Cursor cursor5 = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI_TOTALDISTANCE_LASTWEEK, null, null, null, null);
        cursor5.moveToFirst();
        String totalDistance_lastweek = cursor5.getString(0);
        tvDistanceLastWeek.setText(totalDistance_lastweek);
        cursor5.close();


        Cursor cursor6 = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI_TOTALDISTANCE_THISYEAR, null, null, null, null);
        cursor6.moveToFirst();
        String totalDistance_thisyear = cursor6.getString(0);
        tvDistanceThisYear.setText(totalDistance_thisyear);
        cursor6.close();


        Cursor cursor7 = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI_TOTALDISTANCE_LASTYEAR, null, null, null, null);
        cursor7.moveToFirst();
        String totalDistance_lastyear = cursor7.getString(0);
        tvDistanceLastYear.setText(totalDistance_lastyear);
        cursor7.close();

        Cursor cursor8 = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI_TOTALACTIVITIES_THISWEEK, null, null, null, null);
        cursor8.moveToFirst();
        String totalActivities_thisweek = cursor8.getString(0);
        tvActivityThisWeek.setText(totalActivities_thisweek);
        cursor8.close();


        Cursor cursor9 = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI_TOTALACTIVITIES_LASTWEEK, null, null, null, null);
        cursor9.moveToFirst();
        String totalActivities_lastweek = cursor9.getString(0);
        tvActivityLastWeek.setText(totalActivities_lastweek);
        cursor9.close();


        Cursor cursor10 = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI_TOTALACTIVITIES_THISYEAR, null, null, null, null);
        cursor10.moveToFirst();
        String totalActivities_thisyear = cursor10.getString(0);
        tvActivityThisYear.setText(totalActivities_thisyear);
        cursor10.close();


        Cursor cursor11 = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI_TOTALACTIVITIES_LASTYEAR, null, null, null, null);
        cursor11.moveToFirst();
        String totalActivities_lastyear = cursor11.getString(0);
        tvActivityLastYear.setText(totalActivities_lastyear);
        cursor11.close();
    }



}
