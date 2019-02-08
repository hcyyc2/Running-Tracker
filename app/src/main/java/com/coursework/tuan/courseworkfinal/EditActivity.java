package com.coursework.tuan.courseworkfinal;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class EditActivity extends AppCompatActivity {

    TextView tvDate;
    TextView tvTime;
    TextView tvMetres;
    TextView tvAvgSpeed;
    EditText eTName;


    String dateString;//date in String format


    String id;
    long timer;
    String metres;
    String avgSpeed;


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

        Bundle bundle = getIntent().getExtras();


        //get data sent from DetailActivity
        String name = bundle.getString("name");
        metres = bundle.getString("metres");
        avgSpeed = bundle.getString("avgSpeed");
        timer = bundle.getLong("timer");
        dateString = bundle.getString("date");
        id = bundle.getString("itemId");

        tvDate.setText(new SimpleDateFormat("EEEE, dd/MM/yyyy, hh:mm a").format(App.convertStringToDate(dateString)));
        tvTime.setText(App.secondFormatString(timer));
        tvMetres.setText(metres);
        tvAvgSpeed.setText(avgSpeed);

        eTName.setText(name);
        eTName.setHint(name);
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
        //create new contentValues
        ContentValues mNewValues = new ContentValues();
        mNewValues.put(ActivityProviderContract.NAME, TextUtils.isEmpty(eTName.getText()) ? eTName.getHint().toString().trim() : eTName.getText().toString().trim());
        mNewValues.put(ActivityProviderContract.TIME, timer);
        mNewValues.put(ActivityProviderContract.METRE, metres);
        mNewValues.put(ActivityProviderContract.SPEED, avgSpeed);
        mNewValues.put(ActivityProviderContract.DATE, dateString);

        String whereClause = ActivityProviderContract._ID + "=" + id;

        //call update query
        int i = getContentResolver().update(ActivityProviderContract.ACTIVITY_URI, mNewValues,whereClause,null);
        if(i!=0){
            Toast.makeText(this,"Activity Updated",Toast.LENGTH_LONG).show();
        }
        finish();

    }

    public void createDialog(){
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this activity?")
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String whereClause = ActivityProviderContract._ID + "=" + id;
                        //call delete function
                        int i = getContentResolver().delete(ActivityProviderContract.ACTIVITY_URI,whereClause,null);
                        if(i!=0){
                            Toast.makeText(getApplicationContext(),"Activity Deleted",Toast.LENGTH_LONG).show();
                        }
                        finish();
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


}
