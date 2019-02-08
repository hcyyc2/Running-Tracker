package com.coursework.tuan.courseworkfinal;

import android.net.Uri;

public class ActivityProviderContract {

    public static final String AUTHORITY = "com.coursework.tuan.courseworkfinal.ActivityProvider";



    public static final Uri ACTIVITY_URI = Uri.parse("content://"+AUTHORITY+"/activities");


    public static final Uri ACTIVITY_URI_TOTALMETRE = Uri.parse("content://"+AUTHORITY+"/totalMetres");
    public static final Uri ACTIVITY_URI_BESTDISTANCE = Uri.parse("content://"+AUTHORITY+"/bestDistance");
    public static final Uri ACTIVITY_URI_LONGESTDURATION = Uri.parse("content://"+AUTHORITY+"/longestDuration");
    public static final Uri ACTIVITY_URI_TOTALTIME = Uri.parse("content://"+AUTHORITY+"/totalTime");


    public static final Uri ACTIVITY_URI_TOTALDISTANCE_THISWEEK = Uri.parse("content://"+AUTHORITY+"/totalDistance/thisWeek");
    public static final Uri ACTIVITY_URI_TOTALDISTANCE_LASTWEEK = Uri.parse("content://"+AUTHORITY+"/totalDistance/lastWeek");
    public static final Uri ACTIVITY_URI_TOTALDISTANCE_THISYEAR = Uri.parse("content://"+AUTHORITY+"/totalDistance/thisYear");
    public static final Uri ACTIVITY_URI_TOTALDISTANCE_LASTYEAR = Uri.parse("content://"+AUTHORITY+"/totalDistance/lastYear");

    public static final Uri ACTIVITY_URI_TOTALACTIVITIES_THISWEEK = Uri.parse("content://"+AUTHORITY+"/totalActivities/thisWeek");
    public static final Uri ACTIVITY_URI_TOTALACTIVITIES_LASTWEEK = Uri.parse("content://"+AUTHORITY+"/totalActivities/lastWeek");
    public static final Uri ACTIVITY_URI_TOTALACTIVITIES_THISYEAR = Uri.parse("content://"+AUTHORITY+"/totalActivities/thisYear");
    public static final Uri ACTIVITY_URI_TOTALACTIVITIES_LASTYEAR = Uri.parse("content://"+AUTHORITY+"/totalActivities/lastYear");


    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String TIME = "time";
    public static final String METRE = "metres";
    public static final String SPEED = "speed";
    public static final String DATE = "aDate";

    public static final String CONTENT_TYPE_SINGLE = "vnd.android.cursor.item/ActivityProvider.data.text";
    public static final String CONTENT_TYPE_MULTIPLE = "vnd.android.cursor.dir/ActivityProvider.data.text";

}
