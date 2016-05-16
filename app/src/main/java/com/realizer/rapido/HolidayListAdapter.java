package com.realizer.rapido;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Suvarna.Ghoman on 17-08-2015.
 */
public class HolidayListAdapter extends   BaseAdapter  {


        private static ArrayList<HolidayListModel> hList;
        private LayoutInflater mholidayDetails;

        public HolidayListAdapter(Context context,  ArrayList<HolidayListModel> holidaylist) {
            hList = holidaylist;
            mholidayDetails = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return hList.size();
        }

        @Override
        public Object getItem(int position) {
            return hList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mholidayDetails.inflate(R.layout.holiday_details_list_layout, null);
                holder = new ViewHolder();
                holder.holidayDesc = (TextView) convertView.findViewById(R.id.txtHolidayDesc);
                holder.holidayDate = (TextView) convertView.findViewById(R.id.txtHolidayDate);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position % 2 == 0) {
                convertView.setBackgroundColor(Color.parseColor("#87CEFF"));
            } else {
                convertView.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            holder.holidayDesc.setText(hList.get(position).getHolidayDescription());
            holder.holidayDate.setText(hList.get(position).getHolidayDate());

            return convertView;
        }

        static class ViewHolder
        {
            TextView holidayDesc;
            TextView holidayDate;

        }
    }

