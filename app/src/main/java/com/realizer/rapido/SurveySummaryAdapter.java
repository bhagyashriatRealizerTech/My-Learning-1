package com.realizer.rapido;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Suvarna.Ghoman on 11-09-2015.
 */
public class SurveySummaryAdapter extends BaseAdapter {

    private static ArrayList<SurveySummaryModel> sSummaryDetails;
    private LayoutInflater sSummary;
    Context context;

    public SurveySummaryAdapter(Context _context, ArrayList<SurveySummaryModel> _sSummaryDetails) {
        sSummaryDetails = _sSummaryDetails;
        this.context =_context;
        sSummary = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sSummaryDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return sSummaryDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = sSummary.inflate(R.layout.survey_summary_list_layout, null);
            holder = new ViewHolder();

            holder.txtSummarySrNo = (TextView) convertView.findViewById(R.id.txtSurveySummarySrNo);
            holder.txtSummaryQue = (TextView) convertView.findViewById(R.id.txtSurveySummaryQue);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#87CEFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        holder.txtSummarySrNo.setText(sSummaryDetails.get(position).getsurveySrNo());
        holder.txtSummaryQue.setText(sSummaryDetails.get(position).getsurveyQuestion()+"\n Ans: "+sSummaryDetails.get(position).getsurveyAnswer());

        return convertView;
    }

    static class ViewHolder {
        TextView txtSummarySrNo;
        TextView txtSummaryQue;


    }
}
