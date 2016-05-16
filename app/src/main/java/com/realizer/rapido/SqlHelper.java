package com.realizer.rapido;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by jenny on 8/11/2015.
 */
public class SqlHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RapidoHRMS";
    private static final int DATABASE_VERSION =1;
    public static final String TABLE_NAME = "SurveyQuestions";
    Context mycontext;

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mycontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SurveyQuestions(EmpId TEXT,SurveyCode TEXT, SurveyName TEXT,SrNo Integer, Question TEXT, OptionLst TEXT,Answer TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists " + TABLE_NAME);
        onCreate(db);

    }
}
