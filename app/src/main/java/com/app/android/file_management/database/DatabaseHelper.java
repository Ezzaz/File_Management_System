package com.app.android.file_management.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {
    //---database info---//
    public static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "todoapp.db";

    //--- database table info---//
    public static final String TABLE_LOGIN ="login";
    public static final String TABLE_CATEGORY ="category";
    public static final String TABLE_TASK ="task";

    //--- login table column info---//
    public static final String COL_USER_NAME ="user_name";
    public static final String COL_PASS ="pass";

    //--- category table column info---//
    public static final String COL_CAT_ID ="cat_id";
    public static final String COL_CAT_NAME ="cat_name";

    //--- category table column info---//
    public static final String COL_ID ="id";
    public static final String COL_TITLE ="title";
    public static final String COL_DESP ="desp";
    public static final String COL_TIME ="time";
    public static final String COL_DATE ="date";
    public static final String COL_CATEGORY ="cat";
    public static final String COL_IS_COMPLETE ="is_complete";
    public static final String COL_PATH="path";


    //---Constractor for this class---//
    public DatabaseHelper(Context context ) {
        super(context,DATABASE_NAME ,null, DATABASE_VERSION);
    }


}
