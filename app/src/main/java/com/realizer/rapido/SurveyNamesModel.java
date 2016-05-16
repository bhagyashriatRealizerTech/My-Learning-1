package com.realizer.rapido;

import android.widget.ImageView;

/**
 * Created by Suvarna.Ghoman on 07-08-2015.
 */
public class SurveyNamesModel {
    private String empId="";
    private String surveyId="";
    private String startDate="";
    private String endDate="";
    private String surveyName;


    public void setsurveyId(String surveyId){this.surveyId = surveyId; }
    public String getsurveyId()
    {
        return  surveyId;
    }

    public void setstartDate(String startDate)
    {
        this.startDate = startDate;
    }
    public String getstartDate()
    {
        return  startDate;
    }

    public void setendDate(String endDate)
    {
        this.endDate = endDate;
    }
    public String getendDate()
    {
        return  endDate;
    }

    public void setsurveyName(String surveyName)
    {
        this.surveyName = surveyName;
    }
    public String getsurveyName()
    {
        return  surveyName;
    }

}

