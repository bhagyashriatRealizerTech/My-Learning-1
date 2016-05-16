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
 * Created by Suvarna.Ghoman on 31-07-2015.
 */
public class BalanceLeaveDetailsAdapter extends BaseAdapter {

    private static ArrayList<BalanceLeaveDetailsModel> balanceLeaveDetails;
    private LayoutInflater mBalLeaveDetails;

    public BalanceLeaveDetailsAdapter(Context context,  ArrayList<BalanceLeaveDetailsModel> balLeaveDetails)
    {
        balanceLeaveDetails = balLeaveDetails;
        mBalLeaveDetails = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return balanceLeaveDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return balanceLeaveDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mBalLeaveDetails.inflate(R.layout.leave_balance_list_layout, null);
            holder = new ViewHolder();
            holder.txtLeaveCategory = (TextView) convertView.findViewById(R.id.txtLeaveCategory);
            holder.txtLeaveBal = (TextView) convertView.findViewById(R.id.txtLeaveBal);
            holder.txtAccuredLeaves = (TextView) convertView.findViewById(R.id.txtAccuredLeaves);
            holder.txtLeavesTaken = (TextView) convertView.findViewById(R.id.txtLeavesTaken);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#87CEFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        holder.txtLeaveCategory.setText(balanceLeaveDetails.get(position).getleaveCatagory());
        holder.txtLeaveBal.setText(balanceLeaveDetails.get(position).getleaveBalance());
        holder.txtAccuredLeaves.setText(balanceLeaveDetails.get(position).getAccuredLeaves());
        holder.txtLeavesTaken.setText(balanceLeaveDetails.get(position).getLeavesTaken());

        return convertView;
    }
    static class ViewHolder
    {
        TextView txtLeaveCategory;
        TextView txtLeaveBal;
        TextView txtAccuredLeaves;
        TextView txtLeavesTaken;
    }
}
