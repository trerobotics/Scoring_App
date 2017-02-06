package com.example.hseastrobotics.ftc_scorer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hseastrobotics on 2/4/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String TAG = "DbHelper";

    //DATABASE INFO
    public static final String DATABASE_NAME = "GameDatabase";
    public static final int DATABASE_VERSION = 2;

    //table name
    public static final String TABLE_GAMEINFO = "GameInfo";

    //Table columns
    public static final String ID = "_ID";
    public static final String TEAM_NAME = "teamName";
    public static final String AUTONOMOUS_SCORE = "autonomousScore";
    public static final String TELEOP_SCORE = "teleOpScore";

    private static DbHelper mDbHelper;


    public static synchronized DbHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.

        if (mDbHelper == null) {
            mDbHelper = new DbHelper(context.getApplicationContext());
        }
        return mDbHelper;
    }


    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GAMEINFO_TABLE = "CREATE TABLE " + TABLE_GAMEINFO + "(" + ID + " INTEGER PRIMARY KEY, " +
                TEAM_NAME + " TEXT, " +
                AUTONOMOUS_SCORE + " INT, " +
                TELEOP_SCORE + " INT " + ")";
        db.execSQL(CREATE_GAMEINFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMEINFO);

            onCreate(db);
        }
    }
    //insert a game into the database
    public void insertGameInfo (UserData userData)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(TEAM_NAME, userData.TeamName);
            values.put(AUTONOMOUS_SCORE, userData.autoScore);
            values.put(TELEOP_SCORE, userData.teleOpScore);

            db.insertOrThrow(TABLE_GAMEINFO, null, values);
            db.setTransactionSuccessful();
            Log.d(TAG, "Post successful");

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    //fetch a game from the database

    public List<UserData> getAllGames()
    {
        List<UserData> gameDetail = new ArrayList<>();

        String GAME_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_GAMEINFO;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(GAME_DETAIL_SELECT_QUERY, null);

        try {
            if(cursor.moveToFirst()) {
                do {
                    UserData userData = new UserData();
                    userData.TeamName = cursor.getString(cursor.getColumnIndex(TEAM_NAME));
                    userData.autoScore = cursor.getString(cursor.getColumnIndex(AUTONOMOUS_SCORE));
                    userData.teleOpScore = cursor.getString(cursor.getColumnIndex(TELEOP_SCORE));

                    gameDetail.add(userData);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
        }
        return gameDetail;
    }

    //delete single row from database
    void deleteRow(String name)
    {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            db.execSQL("delete from " + TABLE_GAMEINFO + " where teamName = '" + name + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Error while trying to delete  game info");
        } finally {
            db.endTransaction();
        }
    }
}
