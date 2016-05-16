package com.realizer.rapido;

/**
 * Created by Suvarna.Ghoman on 14-08-2015.
 */
public class SurveyQuestionModel { private String empId="";
    private String surveyId="";
    private String questionNo ="";
    private String surveyName="";
    private String answer="";
    private String totalQuestions="";
    private String surveyQuestion="";
    private String option1="";
    private String option2="";
    private String option3="";
    private String option4="";
    private String option5="";
    private String selectedOption="";
    private String currentQuestionNo="";
    private String isReview;

    public void setempId(String empId){this.empId = empId; }
    public String getempId()
    {
        return  empId;
    }

    public void setquestionNo(String questionNo){this.questionNo = questionNo; }
    public String getquestionNo()
    {
        return  questionNo;
    }

    public void setsurveyId(String surveyId){this.surveyId = surveyId; }
    public String getsurveyId()
    {
        return  surveyId;
    }

    public void setsurveyName(String surveyName){this.surveyName = surveyName; }
    public String getsurveyName()
    {
        return  surveyName;
    }

    public void setanswer(String answer){this.answer = answer; }
    public String getanswer()
    {
        return  answer;
    }

    public String settotalQuestions(String totalQuestions)
    {
        return this.totalQuestions = totalQuestions;
    }
    public String gettotalQuestions()
    {
        return  totalQuestions;
    }

    public void setsurveyQuestion(String surveyQuestion)
    {
        this.surveyQuestion = surveyQuestion;
    }
    public String getsurveyQuestion()
    {
        return  surveyQuestion;
    }

    public void setoption1(String option1){this.option1 = option1; }
    public String getoption1()
    {
        return  option1;
    }

    public void setoption2(String option2){this.option2 = option2; }
    public String getoption2()
    {
        return  option2;
    }

    public void setoption3(String option3){this.option3 = option3; }
    public String getoption3()
    {
        return  option3;
    }

    public void setoption4(String option4){this.option4 = option4; }
    public String getoption4()
    {
        return  option4;
    }

    public void setoption5(String option5){this.option5 = option5; }
    public String getoption5()
    {
        return  option5;
    }

    public void setselectedOption(String selectedOption){this.selectedOption = selectedOption; }
    public String getselectedOption()
    {
        return  selectedOption;
    }

    public void setcurrentQuestionNo(String currentQuestionNo){this.currentQuestionNo = currentQuestionNo; }
    public String getcurrentQuestionNo()
    {
        return  currentQuestionNo;
    }

    public void setIsReview(String isReview){this.isReview = isReview; }
    public String getIsReview()
    {
        return  isReview;
    }
}

