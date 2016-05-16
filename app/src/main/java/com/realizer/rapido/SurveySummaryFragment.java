package com.realizer.rapido;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Win on 10/20/2015.
 */
public class SurveySummaryFragment extends Fragment implements View.OnClickListener {
    Bundle b;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.survey_summary_layout, container, false);
        ArrayList<SurveySummaryModel> surveyAllQuestions = ShowSummary();
        b =this.getArguments();

        ListView lstSurvey =  (ListView) rootView.findViewById(R.id.lstSurveySummary);
        lstSurvey.setAdapter(new SurveySummaryAdapter(getActivity(), surveyAllQuestions));
        return rootView;
    }
    public  ArrayList<SurveySummaryModel> ShowSummary()
    {
        Bundle b =this.getArguments();
        String[] questionList = b.getString("AnswerSurveySummary").split("_");

        ArrayList<SurveySummaryModel> surveyQuestions = new ArrayList<>();

        for(String questionDetails : questionList )
        {
            String [] question = questionDetails.split("~");

            SurveySummaryModel que = new SurveySummaryModel();
            que.setsurveySrNo(question[0]);
            que.setsurveyQuestion(question[3]);
            que.setsurveyAnswer(question[4]);
            surveyQuestions.add(que);
        }

        return surveyQuestions;
    }

    @Override
    public void onClick(View v) {

    }
}
