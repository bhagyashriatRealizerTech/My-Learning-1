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
 * Created by Suvarna.Ghoman on 03-09-2015.
 */
public class ApproveTimeSheetAdapter extends BaseAdapter {

    private static ArrayList<ApproveTimeSheetModel> approveTimeSheet;
    private LayoutInflater mApproveTimeSheet;
    Context context;

    public ApproveTimeSheetAdapter(Context _context,  ArrayList<ApproveTimeSheetModel> _approveTimeSheetDetails)
    {
        approveTimeSheet = _approveTimeSheetDetails;
        this.context = _context;
        mApproveTimeSheet = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return approveTimeSheet.size();
    }

    @Override
    public Object getItem(int position) {
        return approveTimeSheet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null)
        {
            convertView = mApproveTimeSheet.inflate(R.layout.approve_times_sheet_list_layout, null);
            holder = new ViewHolder();
            holder.ApproveFirstName = (TextView) convertView.findViewById(R.id.txtApproveTimeSheetFirstName);
            holder.ApproveLastName = (TextView) convertView.findViewById(R.id.txtApproveTimeSheetLastName);
            holder.ApproveWeekDate=(TextView)convertView.findViewById(R.id.txtApproveTimeSheetDate);
            holder.ApproveTotalHrs=(TextView)convertView.findViewById(R.id.txtApproveTimeSheetTotalHrs);
            holder.ApproveTimeSheet =(ImageView)convertView.findViewById(R.id.imgBtnApproveTimeSheet);
            holder.RejectTimeSheet =(ImageView)convertView.findViewById(R.id.imgBtnRejectTimeSheet);

            holder.ApproveTimeSheet.setTag(position);
            holder.RejectTimeSheet.setTag(position);

            holder.ApproveTimeSheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer position  = (Integer) v.getTag();
                    String weekDate = approveTimeSheet.get(position).getApproveTimeSheetWeek();
                    String totalHrs = approveTimeSheet.get(position).getApproveTimeSheetTotalHrs();
                    String empId =approveTimeSheet.get(position).getEmpId();
                    // call wcf for approve leave
                    if(context instanceof ApproveTimeSheetActivity){
                        ((ApproveTimeSheetActivity)context).ApproveTimeSheet(empId, weekDate,totalHrs);
                    }
                }
            });

            holder.RejectTimeSheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer position  = (Integer) v.getTag();
                    String weekDate = approveTimeSheet.get(position).getApproveTimeSheetWeek();
                    String totalHrs = approveTimeSheet.get(position).getApproveTimeSheetTotalHrs();
                    String empId =approveTimeSheet.get(position).getEmpId();
                    // show popup for comment for leave
                    if(context instanceof ApproveTimeSheetActivity){
                        ((ApproveTimeSheetActivity)context).RejectTimeSheet(empId, weekDate, totalHrs);
                    }

                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ApproveFirstName.setText(approveTimeSheet.get(position).getEmpApproveTimeSheetFirstName());
        holder.ApproveLastName.setText(approveTimeSheet.get(position).getEmpApproveTimeSheetLastName());
        holder.ApproveWeekDate.setText(approveTimeSheet.get(position).getApproveTimeSheetWeek());
        holder.ApproveTotalHrs.setText(approveTimeSheet.get(position).getApproveTimeSheetTotalHrs());



        return convertView;
    }
    static class ViewHolder
    {
        TextView ApproveFirstName;
        TextView ApproveLastName;
        TextView ApproveWeekDate;
        TextView ApproveTotalHrs;
        ImageView ApproveTimeSheet;
        ImageView RejectTimeSheet;
    }

}
