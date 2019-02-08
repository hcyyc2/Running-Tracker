package com.coursework.tuan.courseworkfinal;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SaveActivity extends AppCompatActivity {

    TextView tvDate;
    TextView tvTime;
    TextView tvMetres;
    TextView tvAvgSpeed;
    EditText eTName;

    //these variable keeps data that are passed from LocationService
    long timer;
    String metres;
    String avgSpeed;


    java.util.Date currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        setTitle("Review & Save");

        eTName = findViewById(R.id.eTName);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvMetres = findViewById(R.id.tvMetres);
        tvAvgSpeed = findViewById(R.id.tvAvgSpeed);

        //data that are sent from LocationService
        Bundle bundle = getIntent().getExtras();
        timer = bundle.getLong("timer");
        metres = bundle.getString("metre");
        avgSpeed = bundle.getString("avgSpeed");


        //current date and time
        currentTime = Calendar.getInstance().getTime();


        tvDate.setText(new SimpleDateFormat("EEEE, dd/MM/yyyy, hh:mm a").format(currentTime));
        tvTime.setText(App.secondFormatString(timer));
        tvMetres.setText(metres);
        tvAvgSpeed.setText(avgSpeed);
    }



    //create a custom 'Garbage bin' button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //users click on 'Garbage bin' button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.deleteBtn) {
            createDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveActivity(View view){
        Uri mNewUri;

        //create new contentValues
        ContentValues mNewValues = new ContentValues();
        mNewValues.put(ActivityProviderContract.NAME, TextUtils.isEmpty(eTName.getText()) ? "Testing Activity" : eTName.getText().toString().trim());
        mNewValues.put(ActivityProviderContract.TIME, timer);
        mNewValues.put(ActivityProviderContract.METRE, metres);
        mNewValues.put(ActivityProviderContract.SPEED, avgSpeed);
        mNewValues.put(ActivityProviderContract.DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime));

        //call insert query
        mNewUri = getContentResolver().insert(ActivityProviderContract.ACTIVITY_URI, mNewValues);
        long id = ContentUris.parseId(mNewUri);
        if(id!=0){
            Toast.makeText(this,"New Activity Added",Toast.LENGTH_LONG).show();
        }

        startNewMainActivity();

    }
    public void createDialog(){
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this activity?")
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        startNewMainActivity();
                    }})
                .setNegativeButton("CANCEL", null).create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogs) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
            }
        });
        dialog.show();
    }

    // newly create MainActivity
    public void startNewMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_NO_HISTORY);
        finish();
        startActivity(intent);
    }

    //do nothing when "back" button is pressed
    @Override
    public void onBackPressed() {
        //disabled
    }
}
