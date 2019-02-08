package com.coursework.tuan.courseworkfinal;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class CustomAdapter extends SimpleCursorAdapter {
    
    private LayoutInflater inflater;
    private int layout;


    public CustomAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.layout=layout;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout,null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        TextView name = view.findViewById(R.id.tvName);
        TextView duration = view.findViewById(R.id.tvDuration);
        TextView metres = view.findViewById(R.id.tvMetres);
        TextView speed = view.findViewById(R.id.tvAvgSpeed);
        TextView date = view.findViewById(R.id.tvDate);

        int name_index = cursor.getColumnIndexOrThrow(ActivityProviderContract.NAME);
        int duration_index = cursor.getColumnIndexOrThrow(ActivityProviderContract.TIME);
        int mile_index = cursor.getColumnIndexOrThrow(ActivityProviderContract.METRE);
        int speed_index = cursor.getColumnIndexOrThrow(ActivityProviderContract.SPEED);
        int date_index = cursor.getColumnIndexOrThrow(ActivityProviderContract.DATE);

        name.setText(cursor.getString(name_index));
        duration.setText(App.secondFormatString(cursor.getLong(duration_index)));
        metres.setText(cursor.getString(mile_index));
        speed.setText(cursor.getString(speed_index));
        date.setText(new SimpleDateFormat("EEEE, dd/MM/yyyy, hh:mm a").format(App.convertStringToDate(cursor.getString(date_index))));



    }
}
