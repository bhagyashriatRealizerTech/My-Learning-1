package com.realizer.rapido;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 8/13/2015.
 */
public class SurveyQuestionDBOperations {
    SQLiteDatabase db;
    Context context;
    ArrayList<String> item_info = new ArrayList<String>();

    public SurveyQuestionDBOperations(Context context) {
        this.context = context;
        SQLiteOpenHelper myHelper = new SqlHelper(this.context);
        this.db = myHelper.getWritableDatabase();
    }

    public long SurveyQuestionInsert(String EmpId, String SurveyCode, String SurveyName, int SrNo, String Question,
                                     String OptionLst, String Answer) {
        // SQLiteDatabase db = SqlHelper.DATABASE_NAME.getgetWritableDatabase();
        ContentValues conV = new ContentValues();
        conV.put("EmpId", EmpId);
        conV.put("SurveyCode", SurveyCode);
        conV.put("SurveyName", SurveyName);
        conV.put("SrNo", SrNo);
        conV.put("Question", Question);
        conV.put("OptionLst", OptionLst);
        conV.put("Answer", Answer);


        long newRowInserted = db.insert(SqlHelper.TABLE_NAME, null, conV);


        return newRowInserted;
    }

    // just for checking all data in survey table
    public String GetAllTableData(String scode) {
        Cursor c = db.rawQuery("SELECT * FROM SurveyQuestions ", null);
        String SQ = "";
        int cnt = 1;
        if (c != null) {
            if (c.moveToFirst()) {
                System.out.print("while moving  - C != null");
                do {
                    if(c.getString(c.getColumnIndex("SurveyCode")).equals(scode)) {
                        String SrNo = c.getString(c.getColumnIndex("SrNo"));
                        String SurveyName = c.getString(c.getColumnIndex("SurveyName"));
                        String SurveyCode = c.getString(c.getColumnIndex("SurveyCode"));
                        String Question = c.getString(c.getColumnIndex("Question"));
                        String OptionLst = c.getString(c.getColumnIndex("OptionLst"));
                        String Answer = c.getString(c.getColumnIndex("Answer"));
                        String EmpId = c.getString(c.getColumnIndex("EmpId"));


                        if (Answer.equals(""))
                            Answer = " ";

                        if (SQ.equals(""))
                            SQ = SrNo + "~" + SurveyCode + "~" + SurveyName + "~" + Question + "~" + Answer;
                        else
                            SQ = SQ + "_" + SrNo + "~" + SurveyCode + "~" + SurveyName + "~" + Question + "~" + Answer;
                    }

                }
                while (c.moveToNext());
            }
        } else {
            mToast("Table as No contain");
        }
        //c.close();
        //dbClose(db);
        return SQ;
 }

    public boolean SaveAnswer(String empId, String surveyCode,Integer srNo,String answer)
    {
        boolean saveAns = false;

       ContentValues cv = new ContentValues();
        cv.put("Answer",answer);
        String where ="EmpId="+ empId + " AND SurveyCode=" + surveyCode + " AND SrNo=" + srNo;

        int isUpdated =db.update(SqlHelper.TABLE_NAME, cv,"EmpId=? and SurveyCode= ? and SrNo= ?", new String[]{empId, surveyCode, srNo.toString()});

        if(isUpdated==1)
               {
            saveAns =true;
        }
        return saveAns;
    }

    public void DeleteTable() {
        db.execSQL("delete from "+SqlHelper.TABLE_NAME);
    }

    public boolean SurveyDelete(String empId,String surveyCode)
    {
        boolean isDeleted=false;
        int deleted = db.delete(SqlHelper.TABLE_NAME, "EmpId=? and SurveyCode= ?", new String[]{empId, surveyCode.toString()});
        if(deleted >= 1)
        {
            isDeleted=true;
        }
       return isDeleted;
    }

    public String GetSurveyQuestions(String EmpId,String SurveyCode,int SrNo)
    {
        item_info.clear();
        String where = "WHERE EmpId='"+ EmpId + "' AND SurveyCode= '" + SurveyCode+"' SrNo='"+String.valueOf(SrNo)+"'" ;
        Cursor c = db.rawQuery("SELECT * FROM SurveyQuestions ",null);
        String SQ = "";


        if(c!= null)
        {
            if(c.moveToFirst())
            {
                System.out.print("while moving  - C != null");
                do
                {
                    if((c.getString(c.getColumnIndex("EmpId")).equals(EmpId) )&&
                            (c.getString(c.getColumnIndex("SurveyCode")).equals(SurveyCode)) &&
                            (c.getString(c.getColumnIndex("SrNo")).equals(String.valueOf(SrNo))))
                    {
                        String SurveyName = c.getString(c.getColumnIndex("SurveyName"));
                        String Question =c.getString(c.getColumnIndex("Question"));
                        String OptionLst= c.getString(c.getColumnIndex("OptionLst"));
                        String Answer= c.getString(c.getColumnIndex("Answer"));

                        SQ = SrNo+"~"+SurveyName+"~"+Question+"~"+OptionLst + "~" + Answer;
                        break;
                    }
                }
                while(c.moveToNext());
            }
        }
        else
        {
            mToast("Table as No contain");
        }

        return SQ ;
    }

    public String CheckSurveyExistInSqlLite(String EmpId,String SurveyCode)
    {
        String SQ = "";
        String where="where EmpId='"+EmpId+"' AND SurveyCode='"+SurveyCode+"'";
        Cursor c = db.rawQuery("SELECT * FROM SurveyQuestions "+where,null);
        int currentQuestion = 0;
        if(c!=null)
        {
            int toqstn = c.getCount();
               while (c.moveToNext()) {
                    if (((c.getString(c.getColumnIndex("EmpId"))).equals(EmpId)) &&
                            ((c.getString(c.getColumnIndex("SurveyCode")).equals(SurveyCode))))
                   {
                        String SurveyName = c.getString(c.getColumnIndex("SurveyName"));
                        String Question = c.getString(c.getColumnIndex("Question"));
                        String OptionLst = c.getString(c.getColumnIndex("OptionLst"));
                        String Answer = c.getString(c.getColumnIndex("Answer"));
                        String SrNo = c.getString(c.getColumnIndex("SrNo"));
                        currentQuestion++;
                        if (Answer.equals("")) {
                            SQ = SrNo + "~" + SurveyName + "~" + Question + "~" + OptionLst + "~" + Answer + "~" + currentQuestion + "~" + String.valueOf(toqstn);
                            break;
                        }
                    }
                 }
        }

        return SQ;
    }

    public String GetSurveyQuestionCount(String EmpId,String SurveyCode)
    {
        String SQ = "";
        Cursor c = db.rawQuery("SELECT * FROM SurveyQuestions ",null);
        int totalQuestions = 0;
        if(c!=null) {
            while (c.moveToNext()) {
                if ((c.getString(c.getColumnIndex("EmpId")).equals(EmpId)) &&
                        ((c.getString(c.getColumnIndex("SurveyCode")).equals(SurveyCode))) ){
                    totalQuestions++;
                }

            }
        }
        SQ = String.valueOf(totalQuestions);

        return SQ;
    }


    private void mToast(String text)
    {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

    }
    public void dbClose(SQLiteDatabase db)
    {
        db.close();
    }



}
