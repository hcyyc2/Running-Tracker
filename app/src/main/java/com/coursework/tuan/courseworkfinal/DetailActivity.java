package com.coursework.tuan.courseworkfinal;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DetailActivity extends AppCompatActivity {

    CustomAdapter dataAdapter;
    ListView listView;
    String[] projection;
    int[] colResIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Activities");
        listView = findViewById(R.id.listView);

        projection = new String[] {
                ActivityProviderContract.NAME,
                ActivityProviderContract.TIME,
                ActivityProviderContract.METRE,
                ActivityProviderContract.SPEED,
                ActivityProviderContract.DATE,
                ActivityProviderContract._ID
        };

        colResIds = new int[] {
                R.id.tvName,
                R.id.tvDuration,
                R.id.tvMetres,
                R.id.tvAvgSpeed,
                R.id.tvDate
        };

        Cursor cursor = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI, projection, null, null, "_ID desc");
        dataAdapter = new CustomAdapter(
                this,
                R.layout.item_layout,
                cursor,
                projection,
                colResIds,
                0);

        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                //get running details
                String itemId = cursor.getString(cursor.getColumnIndex(ActivityProviderContract._ID));
                long time = cursor.getLong(cursor.getColumnIndex(ActivityProviderContract.TIME));
                String metres = cursor.getString(cursor.getColumnIndex(ActivityProviderContract.METRE));
                String avgSpeed = cursor.getString(cursor.getColumnIndex(ActivityProviderContract.SPEED));
                String date = cursor.getString(cursor.getColumnIndex(ActivityProviderContract.DATE));
                String name = cursor.getString(cursor.getColumnIndex(ActivityProviderContract.NAME));


                //items to be passed to EditActivity
                Bundle bundle = new Bundle();
                bundle.putString("itemId",itemId);
                bundle.putLong("timer",time);
                bundle.putString("metres",metres);
                bundle.putString("avgSpeed",avgSpeed);
                bundle.putString("date",date);
                bundle.putString("name",name);


                Intent intent = new Intent(view.getContext(), EditActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    //create a custom 'statistics' button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu_2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //users click on 'statistics' button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.statisticBtn) {
            Intent intent = new Intent(getBaseContext(),StatisticActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        //refresh ListView
        Cursor cursor = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI, projection, null, null, "aDate desc");
        dataAdapter = new CustomAdapter(
                this,
                R.layout.item_layout,
                cursor,
                projection,
                colResIds,
                0);

        listView.setAdapter(dataAdapter);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        //newly created MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_NO_HISTORY);
        finish();
        startActivity(intent);
    }
}
