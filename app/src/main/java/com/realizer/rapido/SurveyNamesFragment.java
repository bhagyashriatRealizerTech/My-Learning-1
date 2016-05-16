package com.realizer.rapido;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Win on 10/20/2015.
 */
public class SurveyNamesFragment extends Fragment implements View.OnClickListener {

    SurveyQuestionDBOperations sDBOperation;
    StringBuilder result;
    String [] questionDetails;
    String totalQue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.survey_names_layout, container, false);


        ArrayList<SurveyNamesModel> surveyDetails = GetSurveyNames();
        if (surveyDetails.equals(null)) {
            Toast.makeText(getActivity(), "No Survey to Display", Toast.LENGTH_LONG).show();
        }
        else {

            final ListView listSurveyNames = (ListView) rootView.findViewById(R.id.lstSurveyNames);
            listSurveyNames.setAdapter(new SurveyDetailsAdapter(getActivity(), surveyDetails));

            sDBOperation = new SurveyQuestionDBOperations(getActivity());

            //   sDBOperation.DeleteTable();
            listSurveyNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Object o = listSurveyNames.getItemAtPosition(position);
                    SurveyNamesModel surveyObj = (SurveyNamesModel) o;

                    // if survey exist in SqlLite then check for blank answer start question from there
                    // output -> survey = SrNo + "~" + SurveyName + "~" + Question + "~" + OptionLst + "~" + Answer+ "~"+currentQuestion;
                    String survey = sDBOperation.CheckSurveyExistInSqlLite(UserGlobalData.getInstance().getEmpId(), surveyObj.getsurveyId());
                    if (!survey.equals("")) {

                        // Get total number of question in survey
                        totalQue = sDBOperation.GetSurveyQuestionCount(UserGlobalData.getInstance().getEmpId(), surveyObj.getsurveyId().toString());

                        String[] surveyDetails = survey.split("~");


                        // output -> SQ = SrNo+"~"+SurveyName+"~"+Question+"~"+OptionLst + "~" + Answer;


                        String[] options = surveyDetails[3].split(",");

                        SurveyQuestionFragment fragment = new SurveyQuestionFragment();
                        Bundle bundle = new Bundle();
                        //Intent iSurveyQuestion = new Intent(getActivity(), SurveyQuestionActivity.class);
                        bundle.putString("SurveyId", surveyObj.getsurveyId());
                        bundle.putString("SurveyName", surveyDetails[1]);
                        bundle.putString("SurveyQuestionNo", surveyDetails[0]);
                        bundle.putString("SurveyQuestion", surveyDetails[2]);
                        bundle.putString("SurveyOptionLength", String.valueOf(options.length));
                        for(int i=0;i<options.length;i++) {
                            bundle.putString("QuestionOption" + (i + 1), options[i]);
                        }

                        bundle.putString("CurrentQuestionNumber", surveyDetails[5]);
                        bundle.putString("TotalQuestion", surveyDetails[6]);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragment.setArguments(bundle);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.frame_container,fragment);
                        fragmentTransaction.commit();


                    } else {
                        // else it is new survey need to save to SqlLite and start from first questions
                        SurveyQuestionAsyncTaskGet obj = new SurveyQuestionAsyncTaskGet(UserGlobalData.getInstance().getEmpId(), surveyObj.getsurveyId().replace("\"", ""), getActivity());

                        try {
                            result = obj.execute().get();
                            String resultQuestion = result.toString();
                            //Get output as
                   /* String resultQuestion = "1@Is android easy?@ @false~2@ButtonView contain Text@ @false~3@can " +
                            "we create custom views@ @false~4@android & ios are same@ @false~5@Web Api & wcf are same for " +
                            "android@ @false=Strongly agree,agree,Disagree";*/

                            int totalQuestion = 0;

                            String[] resultQuestionWithOption = resultQuestion.replace("\"", "").split("\\^");

                            String option = resultQuestionWithOption[1];

                            String[] questionDetails = resultQuestionWithOption[0].split("~");
                            long inserted = -1;
                            // for saving all questions in sqllite
                            for (String oneQuestion : questionDetails) {

                                String[] qDetails = oneQuestion.split("@");
                                inserted = sDBOperation.SurveyQuestionInsert(UserGlobalData.getInstance().getEmpId(), surveyObj.getsurveyId(),
                                        surveyObj.getsurveyName(), Integer.parseInt(qDetails[0]), qDetails[1], option, qDetails[2]);
                                if (inserted < 0) {
                                    Toast.makeText(getActivity(), "Unable to start survey, sql exception", Toast.LENGTH_LONG).show();
                                    break;
                                }
                                totalQuestion++;
                            }

                            if (inserted >= 0) {
                                questionDetails = sDBOperation.GetSurveyQuestions(UserGlobalData.getInstance().getEmpId(), surveyObj.getsurveyId().toString(), Integer.parseInt("1")).split("~");

                                Log.d("TOT Q", String.valueOf(totalQuestion));
                                String[] options = questionDetails[3].split(",");

                                SurveyQuestionFragment fragment = new SurveyQuestionFragment();
                                Bundle bundle = new Bundle();

                                bundle.putString("SurveyId", surveyObj.getsurveyId());
                                bundle.putString("SurveyName", questionDetails[1]);
                                bundle.putString("SurveyQuestionNo", questionDetails[0]);
                                bundle.putString("SurveyQuestion", questionDetails[2]);
                                bundle.putString("SurveyOptionLength",String.valueOf(options.length));
                                for(int i=0;i<options.length;i++) {
                                    bundle.putString("QuestionOption" + (i + 1), options[i]);
                                }
                                bundle.putString("CurrentQuestionNumber","1");
                                bundle.putString("TotalQuestion", String.valueOf(totalQuestion));

                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragment.setArguments(bundle);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.replace(R.id.frame_container,fragment);
                                fragmentTransaction.commit();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }

        return rootView;
    }

    // Get emp leave details from wcf
    private ArrayList<SurveyNamesModel> GetSurveyNames()
    {
       Bundle b = this.getArguments();
        ArrayList<SurveyNamesModel> results = new ArrayList<>();
        String[]  empSurveyDetails = (b.getString("AnswerSurvey")).toString().split("_");

        //"Survey0,,Trial,,8-20-2015,,8-27-2015"
        for(String surveyDetails :empSurveyDetails) {
            String[] sDetails = surveyDetails.split(",,");

            if (sDetails.length > 1) {
                SurveyNamesModel surveyDetail = new SurveyNamesModel();

                surveyDetail.setsurveyId(sDetails[0]);
                surveyDetail.setsurveyName(sDetails[1]);
                surveyDetail.setstartDate(sDetails[2]);
                surveyDetail.setendDate(sDetails[3].replace("\"", ""));
                results.add(surveyDetail);

            }
            else
            {
                break;

            }

        }

        return results;
    }

    @Override
    public void onClick(View v) {

    }
}
