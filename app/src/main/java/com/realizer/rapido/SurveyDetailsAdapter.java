package com.realizer.rapido;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Suvarna.Ghoman on 07-08-2015.
 */
    public class SurveyDetailsAdapter extends BaseAdapter {

        private static ArrayList<SurveyNamesModel> surveyDetailsModels;
        private LayoutInflater mSurveyDetails;

        public SurveyDetailsAdapter(Context context, ArrayList<SurveyNamesModel> surveyDetails) {
            surveyDetailsModels = surveyDetails;
            mSurveyDetails = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return surveyDetailsModels.size();
        }

        @Override
        public Object getItem(int position) {
            return surveyDetailsModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mSurveyDetails.inflate(R.layout.survey_names_list_layout, null);
                holder = new ViewHolder();
                holder.img=(ImageView)convertView.findViewById(R.id.imgSurvey);
                holder.txtStartDate = (TextView) convertView.findViewById(R.id.txtStartDateSurvey);
                holder.txtEndDate = (TextView) convertView.findViewById(R.id.txtEndDateSurvey);
                holder.txtSurveyName = (TextView) convertView.findViewById(R.id.txtSurveyName);
                // for image need to add

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position % 2 == 0) {
                convertView.setBackgroundColor(Color.parseColor("#87CEFF"));
            } else {
                convertView.setBackgroundColor(Color.parseColor("#ffffff"));
            }


            holder.txtEndDate.setText(surveyDetailsModels.get(position).getendDate());
            holder.txtStartDate.setText(surveyDetailsModels.get(position).getstartDate());
            holder.txtSurveyName.setText(surveyDetailsModels.get(position).getsurveyName());
            return convertView;
        }

        static class ViewHolder {

            ImageView img;
            TextView txtEndDate;
            TextView txtStartDate;
            TextView txtSurveyName;
        }
    }


