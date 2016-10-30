package com.hackaton.hevre.clientapplication.DB;

import com.hackaton.hevre.clientapplication.DB.BusinessDBTool;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton implementing access to database.
 */
public class DatabaseAccess {

    /*constants */
    private static final String DBNAME = "businesses.db";

    /* static members */
    private static DatabaseAccess instance = null;

    /* data members */
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    /* C-tor */
    /**
     * Default C-tor
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /* static methods */
    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /* methods */
    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * finalize method to verify proper closing of databse connection.
     */
    protected void finalize() {
        this.close();
        System.err.println("DatabaseAccess instance hasn't been closed properly:"
                + "'close' method hasn't been called or some error occurred during the close process");
    }

    /**
     *  retrieving all businesses from the database in the range around the given center point coordinates (lon, lat)
     *  @param lon the center point longitude.
     *  @param lat the center point latitude.
     *  @param radius the range to expand around the center point. units in km.
     *  @return list of the names of the relevant busineses.
     */
    public ArrayList<String> getBusinessesInRange(double lon, double lat, double radius) {

        /* retrieve Cursor object with the database query results */
        String query = BusinessDBTool.getBusinessesInRangeQuery(lon, lat, radius);
//        System.out.println(query);
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        ArrayList<String> results = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            results.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return results;
    }
}