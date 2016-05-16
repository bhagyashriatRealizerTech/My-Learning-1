package com.realizer.rapido;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.ParseException;

/**
 * Created by suvarna.ghoman on 09-09-2015.
 */
public class TimeSheetEntryAdapter extends BaseAdapter {
    private Context mContext;
    private String[] projectList, activityList;
    private String[] weekDays = new String[6];
    private LayoutInflater mTimeSheetEntry;

    public TimeSheetEntryAdapter(Context _context, String[] _projectList, String[] _activitiesList) {
        mContext = _context;
        projectList=_projectList;
        activityList=_activitiesList;
        mTimeSheetEntry = LayoutInflater.from(_context);
    }

    public int getCount() {
        return weekDays.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;

        if (convertView == null) {

            // get layout from mobile.xml

            view = mTimeSheetEntry.inflate(R.layout.timesheet_entry_gridview_layout, null);
            holder = new ViewHolder();
            // set value into TextView
            holder.spProjectNames =(Spinner)view.findViewById(R.id.spProjects);
            holder.spProjectActivities =(Spinner)view.findViewById(R.id.spActivities);
            holder.edtMonday = (EditText) view.findViewById(R.id.edtMondayTimeEnty);
            holder.edtTuesday = (EditText) view.findViewById(R.id.edtTuesDayTimeEnty);
            holder.edtWednesday = (EditText) view.findViewById(R.id.edtWednesDayTimeEnty);
            holder.edtThursday = (EditText) view.findViewById(R.id.edtThursDayTimeEnty);
            holder.edtFriday = (EditText) view.findViewById(R.id.edtFriDayTimeEnty);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
            holder.edtMonday.setText("Monday",TextView.BufferType.EDITABLE);
            holder.edtTuesday.setText("Tuesday",TextView.BufferType.EDITABLE);
            holder.edtWednesday.setText("Wednesday",TextView.BufferType.EDITABLE);
            holder.edtThursday.setText("Thursday",TextView.BufferType.EDITABLE);
            holder.edtFriday.setText("Friday",TextView.BufferType.EDITABLE);


            // set string array to spinners

            ArrayAdapter<String> adtProjects = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item,projectList);
            adtProjects.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spProjectNames.setAdapter(adtProjects);

            ArrayAdapter<String> adtActivities = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item,activityList);
            adtProjects.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spProjectActivities.setAdapter(adtActivities);

        return view;
    }
    public class ViewHolder {
        Spinner spProjectNames;
        Spinner spProjectActivities;
        EditText edtMonday;
        EditText edtTuesday;
        EditText edtWednesday;
        EditText edtThursday;
        EditText edtFriday;
    }
}
