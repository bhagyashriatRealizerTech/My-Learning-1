package com.realizer.rapido;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 22/08/2015.
 */
public class AttendanceAdapter  extends BaseAdapter {

        private static ArrayList<AttendanceModel> attendanceDetails;
        private LayoutInflater mAttendanceDetails;

        public AttendanceAdapter(Context context,  ArrayList<AttendanceModel> aDetails)
        {
            attendanceDetails = aDetails;
            mAttendanceDetails = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return attendanceDetails.size();
        }

        @Override
        public Object getItem(int position) {
            return attendanceDetails.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mAttendanceDetails.inflate(R.layout.attendance_list_layout, null);
                holder = new ViewHolder();
                holder.txtAttedanceDate = (TextView) convertView.findViewById(R.id.txtAttendanceDate);
                holder.txtAttedanceInOut = (TextView) convertView.findViewById(R.id.txtAttendanceInOutTime);
                holder.txtTotalHrs=(TextView)convertView.findViewById(R.id.txtAttendanceTotalHrs);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position % 2 == 0) {
                convertView.setBackgroundColor(Color.parseColor("#87CEFF"));
            } else {
                convertView.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            holder.txtAttedanceDate.setText(attendanceDetails.get(position).getattendanceDate());
            holder.txtAttedanceInOut.setText(attendanceDetails.get(position).getattendanceInOut());
            holder.txtTotalHrs.setText(attendanceDetails.get(position).getattedanceTotalHrs());
            return convertView;
        }
        static class ViewHolder
        {
            TextView txtAttedanceDate;
            TextView txtAttedanceInOut;
            TextView txtTotalHrs;
        }
    }


