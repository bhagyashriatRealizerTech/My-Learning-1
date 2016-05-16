package com.realizer.rapido;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by Win on 10/20/2015.
 */
public class SurveyQuestionFragment extends Fragment implements View.OnClickListener,AsyncTaskCompleteListener<String> {

    TextView surveyNameForQuestion, currentQuestionNo, totalQuestions, question, surveyId;
    RadioButton option1, option2, option3, option4, option5, selectedOption;
    Button setNextQuestion,summary,savedraft;
    RadioGroup rdoOptionGroup;
    StringBuilder result;
    Bundle b;
    int optlength;
    View rootView;
    RadioButton opt[];
    View v;
    SurveyQuestionDBOperations sDBOperation;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.survey_question_layout, container, false);
        controls(rootView);
        setNextQuestion.setOnClickListener(this);
        summary.setOnClickListener(this);
        savedraft.setOnClickListener(this);
        v= rootView;

        b = this.getArguments();

        sDBOperation = new SurveyQuestionDBOperations(getActivity());

        surveyId.setText(b.getString("SurveyId").toString());
        surveyNameForQuestion.setText(b.getString("SurveyName").toString());
        currentQuestionNo.setText(b.getString("SurveyQuestionNo"));
        totalQuestions.setText(b.getString("TotalQuestion"));
        question.setText(b.getString("SurveyQuestion"));
        optlength = Integer.valueOf(b.getString("SurveyOptionLength"));
        opt = new RadioButton[optlength];

        for(int i=0;i<optlength;i++)
        {
            opt[i] = new RadioButton(getActivity());
            opt[i].setText(b.getString("QuestionOption" + (i + 1)));
            opt[i].setTextColor(Color.WHITE);
            opt[i].setTextSize(15);
            opt[i].setId(100+i);
            opt[i].setOnClickListener(this);
            rdoOptionGroup.addView(opt[i],i);
        }


        totalQuestions.setText(b.getString("TotalQuestion"));

        if(currentQuestionNo.getText().toString().equals(totalQuestions.getText().toString()))
            setNextQuestion.setText("Submit");


        return rootView;
    }
    public void controls(View v) {
        surveyNameForQuestion = (TextView)v.findViewById(R.id.txtSrvName);
        totalQuestions = (TextView)v.findViewById(R.id.txtTotalQuestions);
        question = (TextView)v.findViewById(R.id.txtSurveyQuestion);
        currentQuestionNo =(TextView)v.findViewById(R.id.txtCurrentQuestionNo);
        surveyId = (TextView) v.findViewById(R.id.txtSurveyId);
        setNextQuestion =(Button)v.findViewById(R.id.btnSetNextQuestion);
        summary = (Button)v.findViewById(R.id.btnsummary);
        savedraft = (Button)v.findViewById(R.id.btnsavedraft);
        rdoOptionGroup = (RadioGroup) v.findViewById(R.id.rdoGroupOptions);
    }

    public void Submit(View v)
    {
        Toast.makeText(getActivity(), "Survey submitted", Toast.LENGTH_SHORT).show();
    }

    public void SetNextQuestion(View v) {
        String empId = UserGlobalData.getInstance().getEmpId();
        String surveyCode = surveyId.getText().toString();
        String isSaved = null;

        if (setNextQuestion.getText().equals("Next")) {
            Integer srNo = Integer.parseInt(currentQuestionNo.getText().toString()) + 1;


            SurveyQuestionFragment fragment = new SurveyQuestionFragment();
            Bundle bundle = new Bundle();


            // First save answer to SqlLite table


            //selectedOption = (RadioButton) v.findViewById(rdoOptionGroup.getCheckedRadioButtonId());
            if (selectedOption != null) {
                boolean isSavedAnswer = sDBOperation.SaveAnswer(empId, surveyCode, srNo - 1, selectedOption.getText().toString());
                // if saved answer then  save to CB

                if (isSavedAnswer) {
                    int optl = Integer.valueOf(b.getString("SurveyOptionLength"));
                    String optformat[] = new String[optl];
                    String opt="";
                    for(int i=0;i<optl;i++)
                    {
                        optformat[i] = b.getString("QuestionOption" + (i + 1)).replace(" ", "%20").replace("&", "A_N_D");
                        opt =  opt.concat(","+optformat[i]);
                    }


                    //call wcf call for saving
                    //String opt = option1.getText().toString().replace(" ", "%20").replace("&", "A_N_D") + "," + option2.getText().toString().replace(" ", "%20").replace("&", "A_N_D") + "," + option3.getText().toString().replace(" ", "%20").replace("&", "A_N_D");
                    String qst = question.getText().toString().replace(" ", "%20").replace("&", "A_N_D");
                    String srv = surveyCode.replace("\"", "");
                    String sel = selectedOption.getText().toString().replace(" ", "%20").replace("&", "A_N_D");

                    SurveyQuestionSaveAnswerPost obj = new SurveyQuestionSaveAnswerPost(empId, srv, srNo - 1, qst, sel, "true", opt,"false", getActivity());



                    try {
                        result = obj.execute().get();
                        isSaved = result.toString();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    // show next question
                    //isSaved="true";
                    if (isSaved.toString().equalsIgnoreCase("true")) {

                        String[] questionDetails = sDBOperation.GetSurveyQuestions(empId, surveyCode, srNo).split("~");

                        String[] options = questionDetails[3].split(",");


                        bundle.putString("SurveyId", surveyCode);
                        bundle.putString("SurveyName", questionDetails[1]);
                        bundle.putString("SurveyQuestionNo", questionDetails[0]);
                        bundle.putString("SurveyQuestion", questionDetails[2]);
                        bundle.putString("SurveyOptionLength",String.valueOf(options.length));
                        for(int i=0;i<options.length;i++) {
                            bundle.putString("QuestionOption" + (i + 1), options[i]);
                        }
                        bundle.putString("CurrentQuestionNumber", String.valueOf(srNo));
                        bundle.putString("TotalQuestion", totalQuestions.getText().toString());

                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragment.setArguments(bundle);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.frame_container,fragment);
                        fragmentTransaction.commit();
                    } else {
                        Toast.makeText(getActivity(), "Unable to save in CouchBase...., please try after some time ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Unable to save in SqlLite...., please try after some time ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "No option selected ", Toast.LENGTH_SHORT).show();

            }


        } else if (setNextQuestion.getText().equals("Submit")) {
            // Delete particular survey questions from SqlLite
            //String sel = selectedOption.getText().toString();
            //selectedOption = (RadioButton) v.findViewById(rdoOptionGroup.getCheckedRadioButtonId());
            Integer srNo = Integer.parseInt(currentQuestionNo.getText().toString()) + 1;//call for async task
            Toast.makeText(getActivity(), "write code to save to CB WCFCall to update answer...", Toast.LENGTH_LONG).show();
            int optl = Integer.valueOf(b.getString("SurveyOptionLength"));
            String optformat[] = new String[optl];
            String opt="";
            for(int i=0;i<optl;i++)
            {
                optformat[i] = b.getString("QuestionOption" + (i + 1)).replace(" ", "%20").replace("&", "A_N_D");
                opt =  opt.concat(","+optformat[i]);
            }
            //String opt = option1.getText().toString().replace(" ", "%20").replace("&", "A_N_D") + "," + option2.getText().toString().replace(" ", "%20").replace("&", "A_N_D") + "," + option3.getText().toString().replace(" ", "%20").replace("&", "A_N_D");
            String qst = question.getText().toString().replace(" ", "%20").replace("&", "A_N_D");
            String srv = surveyCode.replace("\"", "");
            String sel = selectedOption.getText().toString().replace(" ", "%20").replace("&", "A_N_D");

            SurveyQuestionSaveAnswerPost obj = new SurveyQuestionSaveAnswerPost(empId, srv, srNo - 1, qst, sel, "true", opt,"true", getActivity());



            try {
                result = obj.execute().get();
                isSaved = result.toString();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            boolean isDeleted = sDBOperation.SurveyDelete(empId, surveyCode);
            if (isDeleted) {


                // show survey names list activity
                SurveyNamesAsyncTask obj1 = new SurveyNamesAsyncTask(empId, getActivity(),this);
                try {
                    result = obj1.execute().get();
                    String empSurveyNames = result.toString();

                    SurveyNamesFragment fragment = new SurveyNamesFragment();
                    Bundle bundle = new Bundle();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    bundle.putString("AnswerSurvey", empSurveyNames);
                    fragment.setArguments(bundle);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.frame_container,fragment);
                    fragmentTransaction.commit();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getActivity(), "unable to save delete survey from SqlLite .. please try after some time", Toast.LENGTH_LONG).show();
            }


        }

    }
    public void SurveySaveDraft(View v) {
        // save answer to SqlLite table
        String empId = UserGlobalData.getInstance().getEmpId();
        String isSaved = null;
        if (selectedOption != null) {
            //selectedOption = (RadioButton) v.findViewById(rdoOptionGroup.getCheckedRadioButtonId());
            Integer srNo = Integer.parseInt(currentQuestionNo.getText().toString()) + 1;
            String surveyCode = surveyId.getText().toString();
            boolean isSavedAnswer = sDBOperation.SaveAnswer(empId, surveyCode, srNo - 1, selectedOption.getText().toString());
            if (isSavedAnswer) {
                //call wcf call for saving
                int optl = Integer.valueOf(b.getString("SurveyOptionLength"));
                String optformat[] = new String[optl];
                String opt = "";
                for (int i = 0; i < optl; i++) {
                    optformat[i] = b.getString("QuestionOption" + (i + 1)).replace(" ", "%20").replace("&", "A_N_D");
                    opt = opt.concat("," + optformat[i]);
                }
                //String opt = option1.getText().toString().replace(" ", "%20").replace("&", "A_N_D") + "," + option2.getText().toString().replace(" ", "%20").replace("&", "A_N_D") + "," + option3.getText().toString().replace(" ", "%20").replace("&", "A_N_D");
                String qst = question.getText().toString().replace(" ", "%20").replace("&", "A_N_D");
                String srv = surveyCode.replace("\"", "");
                String sel = selectedOption.getText().toString().replace(" ", "%20").replace("&", "A_N_D");

                SurveyQuestionSaveAnswerPost obj = new SurveyQuestionSaveAnswerPost(empId, srv, srNo - 1, qst, sel, "true", opt, "false", getActivity());



                try {
                    result = obj.execute().get();
                    isSaved = result.toString();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                //set navigation to employee dashboard
                if(isSaved.equals("true")) {
                    Toast.makeText(getActivity(), "Saved to draft Successfully", Toast.LENGTH_SHORT).show();
                    SurveyNamesAsyncTask objsurvey = new SurveyNamesAsyncTask(empId, getActivity(), SurveyQuestionFragment.this);
                    objsurvey.execute();
                }
            } else
                Toast.makeText(getActivity(), "Unable to save answer, please try after some time", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(getActivity(), "No option selected ", Toast.LENGTH_SHORT).show();
        }
    }

    public void ShowSurveySummary(View v)
    {
        String scode = surveyId.getText().toString();

        String summaryDetails =sDBOperation.GetAllTableData(scode);

        SurveySummaryFragment fragment = new SurveySummaryFragment();
        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        bundle.putString("AnswerSurveySummary", summaryDetails);
        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame_container,fragment);
        fragmentTransaction.commit();

    }



    @Override
    public void onClick(View v) {


        for(int i=0;i<optlength;i++)
        {
            selectedOption = (RadioButton) rootView.findViewById(rdoOptionGroup.getCheckedRadioButtonId());

        }

        switch (v.getId())
        {
            case R.id.btnSetNextQuestion:
                SetNextQuestion(v);
                break;
            case R.id.btnsummary:
                ShowSurveySummary(v);
                break;
            case R.id.btnsavedraft:
                SurveySaveDraft(v);
                break;

        }




    }

    @Override
    public void onTaskComplete(String result, int pos) {

        switch (pos)
        {
            case 9:
                String empSurveyNames = result;
                SurveyNamesFragment fragment = new SurveyNamesFragment();
                Bundle bundle = new Bundle();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                bundle.putString("AnswerSurvey", empSurveyNames);
                fragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.frame_container,fragment);
                fragmentTransaction.commit();
                break;

        }



    }
}
