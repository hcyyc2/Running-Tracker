package com.coursework.tuan.courseworkfinal;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class ActivityProvider extends ContentProvider {

    private DBHelper dbHelper = null;


    //use UriMatcher to call appropriate queries
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ActivityProviderContract.AUTHORITY, "totalMetres", 1);
        uriMatcher.addURI(ActivityProviderContract.AUTHORITY, "bestDistance", 2);
        uriMatcher.addURI(ActivityProviderContract.AUTHORITY, "longestDuration", 3);
        uriMatcher.addURI(ActivityProviderContract.AUTHORITY, "totalTime", 4);
        uriMatcher.addURI(ActivityProviderContract.AUTHORITY, "totalDistance/thisWeek", 5);
        uriMatcher.addURI(ActivityProviderContract.AUTHORITY, "totalDistance/lastWeek", 6);
        uriMatcher.addURI(ActivityProviderContract.AUTHORITY, "totalDistance/thisYear", 7);
        uriMatcher.addURI(ActivityProviderContract.AUTHORITY, "totalDistance/lastYear", 8);
        uriMatcher.addURI(ActivityProviderContract.AUTHORITY, "totalActivities/thisWeek", 9);
        uriMatcher.addURI(ActivityProviderContract.AUTHORITY, "totalActivities/lastWeek", 10);
        uriMatcher.addURI(ActivityProviderContract.AUTHORITY, "totalActivities/thisYear", 11);
        uriMatcher.addURI(ActivityProviderContract.AUTHORITY, "totalActivities/lastYear", 12);
    }


    @Override
    public boolean onCreate() {
        this.dbHelper = new DBHelper(this.getContext(), "activityDB", null, 7);
        return false;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case 1://total metre
                return db.rawQuery("SELECT sum("+ActivityProviderContract.METRE+") FROM activities", null);
            case 2://best distance
                return db.rawQuery("SELECT max("+ActivityProviderContract.METRE+") FROM activities", null);
            case 3://longest Duration
                return db.rawQuery("SELECT max("+ActivityProviderContract.TIME+") FROM activities", null);
            case 4://total time
                return db.rawQuery("SELECT sum("+ActivityProviderContract.TIME+") FROM activities", null);
            case 5://distance this week
                return db.rawQuery("SELECT sum("+ActivityProviderContract.METRE+") FROM activities WHERE "+ActivityProviderContract.DATE+" >= DATE('now', 'weekday 0', '-7 days')",null);
            case 6://distance last week
                return db.rawQuery("SELECT sum("+ActivityProviderContract.METRE+") FROM activities WHERE "+ ActivityProviderContract.DATE+" BETWEEN DATE('now', '-6 days') AND DATE('now', 'localtime')",null);
            case 7://distance this year
                return db.rawQuery("SELECT sum("+ActivityProviderContract.METRE+") FROM activities AS a WHERE strftime('%Y',a.aDate) = strftime('%Y','now')",null);
            case 8://distance last year
                return db.rawQuery("SELECT sum("+ActivityProviderContract.METRE+") FROM activities AS a WHERE strftime('%Y',a.aDate) = strftime('%Y','now','-1 years')",null);
            case 9://activities this week
                return db.rawQuery("SELECT count(*) FROM activities WHERE "+ActivityProviderContract.DATE+" >= DATE('now', 'weekday 0', '-7 days')",null);
            case 10://activities last week
                return db.rawQuery("SELECT count(*) FROM activities WHERE "+ ActivityProviderContract.DATE+" BETWEEN DATE('now', '-6 days') AND DATE('now', 'localtime')",null);
            case 11://activities this year
                return db.rawQuery("SELECT count(*) FROM activities AS a WHERE strftime('%Y',a.aDate) = strftime('%Y','now')",null);
            case 12://activities last year
                return db.rawQuery("SELECT count(*) FROM activities AS a WHERE strftime('%Y',a.aDate) = strftime('%Y','now','-1 years')",null);
        }

        return db.query("activities", projection, selection, selectionArgs, null, null, sortOrder);
    }


    @Override
    public String getType(Uri uri) {
        String contentType;

        if (uri.getLastPathSegment()==null) {
            contentType = ActivityProviderContract.CONTENT_TYPE_MULTIPLE;
        } else {
            contentType = ActivityProviderContract.CONTENT_TYPE_SINGLE;
        }

        return contentType;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert("activities", null, values);
        db.close();
        Uri nu = ContentUris.withAppendedId(uri, id);
        getContext().getContentResolver().notifyChange(nu, null);
        return nu;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int i = db.delete("activities",selection,null);
        db.close();
        return i;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int id = db.update("activities",values,selection,null);
        db.close();
        return id;
    }
}
