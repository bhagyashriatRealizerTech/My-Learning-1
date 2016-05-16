package com.realizer.rapido;

/**
 * Created by Suvarna.Ghoman on 11-09-2015.
 */
public class SurveySummaryModel {
    // private String empId="";
    private String surveyId="";
    private String surveyName="";
    private String surveySrNo="";
    private String surveyQuestion="";
    private String surveyAnswer;


    public void setsurveyId(String surveyId){this.surveyId = surveyId; }
    public String getsurveyId()
    {
        return  surveyId;
    }

    public void setsurveyName(String surveyName)
    {
        this.surveyName = surveyName;
    }
    public String getsurveyName()
    {
        return  surveyName;
    }

    public void setsurveySrNo(String surveySrNo)
    {
        this.surveySrNo = surveySrNo;
    }
    public String getsurveySrNo()
    {
        return  surveySrNo;
    }

    public void setsurveyQuestion(String surveyQuestion)
    {
        this.surveyQuestion = surveyQuestion;
    }
    public String getsurveyQuestion()
    {
        return  surveyQuestion;
    }

    public void setsurveyAnswer(String surveyAnswer)
    {
        this.surveyAnswer = surveyAnswer;
    }
    public String getsurveyAnswer()
    {
        return  surveyAnswer;
    }
}

