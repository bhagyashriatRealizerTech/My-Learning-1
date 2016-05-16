package com.realizer.rapido;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Suvarna.Ghoman on 26-08-2015.
 */
public class TimeSheetListingAdapter extends BaseAdapter {

    private static ArrayList<TimeSheetListingModel> timesheetListing;
    private LayoutInflater mTimesheetListingDisplay;

    public TimeSheetListingAdapter(Context context, ArrayList<TimeSheetListingModel> _timesheetListing) {
        timesheetListing = _timesheetListing;
        mTimesheetListingDisplay = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return timesheetListing.size();
    }

    @Override
    public Object getItem(int position) {
        return timesheetListing.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mTimesheetListingDisplay.inflate(R.layout.timesheet_listing_list_layout, null);
            holder = new ViewHolder();
            holder.timesheetListingStartDate = (TextView) convertView.findViewById(R.id.txtTimeSheetListingSD);
            holder.timesheetListingTotalHrs = (TextView) convertView.findViewById(R.id.txtTimeSheetListingTotalHrs);
            holder.timesheetListingStatus = (TextView) convertView.findViewById(R.id.txtBtnTimesheetStatus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.timesheetListingStartDate.setText(timesheetListing.get(position).gettimesheetStartDate());
        holder.timesheetListingTotalHrs.setText(timesheetListing.get(position).gettotalHours());
        holder.timesheetListingStatus.setText(timesheetListing.get(position).gettimesheetStatus());
        // 2 is approved
        if(timesheetListing.get(position).gettimesheetStatus().toString().equals("Approved"))
        {
           //
            holder.timesheetListingStatus.setTextColor(Color.GREEN);
        }

        // 1 for submitted
        if(timesheetListing.get(position).gettimesheetStatus().toString().equals("Submitted")) {
           // holder.timesheetListingStatus.setText("Submitted");
            holder.timesheetListingStatus.setTextColor(Color.BLUE);
        }
        // 0 for rejected
        if(timesheetListing.get(position).gettimesheetStatus().toString().equals("Rejected"))
        {
            //holder.timesheetListingStatus.setText("Rejected");
            holder.timesheetListingStatus.setTextColor(Color.RED);
        }
        return convertView;

    }

    static class ViewHolder
    {
        TextView timesheetListingStartDate;
        TextView timesheetListingTotalHrs;
        TextView timesheetListingStatus;
    }


}
