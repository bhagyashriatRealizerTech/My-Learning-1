package com.realizer.rapido;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by Suvarna.Ghoman on 31-07-2015.
 */
public class LeaveTakenDetailsAdapter extends BaseAdapter implements SectionIndexer {

    HashMap<String, Integer> mapIndex;
    String[] sections;
    private static ArrayList<LeaveTakenDetailsModel> leaveTakenDetails;
    private LayoutInflater mLeaveTakenDeails;

    public LeaveTakenDetailsAdapter(Context context,  ArrayList<LeaveTakenDetailsModel> lveTakenDetails)
    {
        leaveTakenDetails = lveTakenDetails;
        mLeaveTakenDeails = LayoutInflater.from(context);
        sections = new String[lveTakenDetails.size()];


        this.leaveTakenDetails = lveTakenDetails;
        mapIndex = new LinkedHashMap<String, Integer>();



        Set<String> sectionLetters = mapIndex.keySet();

        // create a list from the set to sort
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);

       // Log.d("sectionList", sectionList.toString());
        Collections.sort(sectionList);

        sections = new String[sectionList.size()];

        sectionList.toArray(sections);

    }
    @Override
    public int getCount() {
        return leaveTakenDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return leaveTakenDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLeaveTakenDeails.inflate(R.layout.leave_taken_list_layout, null);
            holder = new ViewHolder();
            holder.txtLeaveType = (TextView) convertView.findViewById(R.id.txtLeaveType);
            holder.txtStartDate = (TextView) convertView.findViewById(R.id.txtStartDate);
            holder.txtEndDate = (TextView) convertView.findViewById(R.id.txtEndDate);
            holder.txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);
            holder.txtResumeDate = (TextView) convertView.findViewById(R.id.txtresumedate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#87CEFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        holder.txtLeaveType.setText(leaveTakenDetails.get(position).getleaveType());
        holder.txtStartDate.setText(leaveTakenDetails.get(position).getstartDate());
        holder.txtEndDate.setText(leaveTakenDetails.get(position).getResumedate());
        holder.txtResumeDate.setText(leaveTakenDetails.get(position).getendDate());
        holder.txtStatus.setText(leaveTakenDetails.get(position).getstatus());
        if(holder.txtStatus.getText().equals("Approved"))
            holder.txtStatus.setTextColor(Color.GREEN);
        else if(holder.txtStatus.getText().equals("Pending"))
            holder.txtStatus.setTextColor(Color.WHITE);
        else  if(holder.txtStatus.getText().equals("Taken"))
            holder.txtStatus.setTextColor(Color.YELLOW);
        else if(holder.txtStatus.getText().equals("Denied"))
            holder.txtStatus.setTextColor(Color.RED);
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
       // Log.d("section", "" + sectionIndex);
        return mapIndex.get(sections[sectionIndex]);

    }

    @Override
    public int getSectionForPosition(int position) {
       // Log.d("position", "" + position);
        return 0;
    }


    static class ViewHolder
    {
        TextView txtStartDate;
        TextView txtEndDate;
        TextView txtResumeDate;
        TextView txtLeaveType;
        TextView txtStatus;
    }
}
