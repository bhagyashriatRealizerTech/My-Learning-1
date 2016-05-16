package com.realizer.rapido;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Suvarna.Ghoman on 11-08-2015.
 */
public class TimeSheetDisplayAdapter extends BaseAdapter {

    private static ArrayList<TimeSheetDisplayModel> timesheetDisplayModels;
    private LayoutInflater mTimesheetDisplay;

    public TimeSheetDisplayAdapter(Context context, ArrayList<TimeSheetDisplayModel> _timesheetDisplayModels) {
        timesheetDisplayModels = _timesheetDisplayModels;
        mTimesheetDisplay = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return timesheetDisplayModels.size();
    }

    @Override
    public Object getItem(int position) {
        return timesheetDisplayModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
           /* long s= getItemId(position);*/
        if (convertView == null) {
            convertView = mTimesheetDisplay.inflate(R.layout.time_sheet_display_layout, null);
            holder = new ViewHolder();
            holder.txtTimesheetStartDate = (TextView) convertView.findViewById(R.id.txtTimesheetStartDate);
            holder.txtTimesheetEndDate = (TextView) convertView.findViewById(R.id.txtTimesheetEndDate);
            holder.txtTimesheetProjectName = (TextView) convertView.findViewById(R.id.txtTimesheetProjectName);
            holder.txtTimesheetTotalHrs = (TextView) convertView.findViewById(R.id.txtTimesheetTotalHrs);
            holder.txtTimesheetStatus = (TextView) convertView.findViewById(R.id.txtTimesheetStatus);

            // for image need to add

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTimesheetStartDate.setText(timesheetDisplayModels.get(position).gettimesheetStartDate());
        holder.txtTimesheetEndDate.setText(timesheetDisplayModels.get(position).gettimesheetEndDate());
        holder.txtTimesheetProjectName.setText(timesheetDisplayModels.get(position).gettimesheetProjectName());
        holder.txtTimesheetTotalHrs.setText(timesheetDisplayModels.get(position).gettotalHours());
        holder.txtTimesheetStatus.setText(timesheetDisplayModels.get(position).gettimesheetStatus());
        return convertView;
    }

    static class ViewHolder {

        TextView txtTimesheetStartDate;
        TextView txtTimesheetEndDate;
        TextView txtTimesheetProjectName;
        TextView txtTimesheetTotalHrs;
        TextView txtTimesheetStatus;
    }
}
