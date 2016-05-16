package com.realizer.rapido;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Suvarna.Ghoman on 01-09-2015.
 */
public class SwipeApplyRegularizationAdapter extends BaseAdapter {

    private static ArrayList<SwipeApplyRegularizationModel> sApply;
    private LayoutInflater sApplyDetail;
    SwipeApplyFragment frag;
    Context context;

    public SwipeApplyRegularizationAdapter(Context _context, ArrayList<SwipeApplyRegularizationModel> sApplyDetails,SwipeApplyFragment frag) {
        sApply = sApplyDetails;
        this.context =_context;
        this.frag = frag;
       sApplyDetail = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sApply.size();
    }

    @Override
    public Object getItem(int position) {
        return sApply.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
           /* long s= getItemId(position);*/
        if (convertView == null) {
            convertView = sApplyDetail.inflate(R.layout.swipe_apply_regularization_list_layout, null);
            holder = new ViewHolder();

            holder.txtSwipeDay = (TextView) convertView.findViewById(R.id.txtDayForSwipe);
            holder.txtSwipeDate = (TextView) convertView.findViewById(R.id.txtSwipeDate);
            holder.txtSwipeTimeIn = (TextView) convertView.findViewById(R.id.txtSwipeTimeIn);
            holder.applySwipe=(Button) convertView.findViewById(R.id.btnApplySwipe);
            holder.txtSwipeTimeOut = (TextView) convertView.findViewById(R.id.txtSwipeTimeOut);
            holder.txtSwipeTotal = (TextView) convertView.findViewById(R.id.txtSwipeTotal);
            final String swipeDate = holder.txtSwipeDate.toString();
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#87CEFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        //    holder.img = (surveyDetailsModels.get(position).gesSurveyImg());
        holder.txtSwipeDate.setText(sApply.get(position).getdateForSwipe());
        holder.txtSwipeDay.setText(sApply.get(position).getdayForSwipe());
        holder.txtSwipeTimeIn.setText(sApply.get(position).gettimeInSwipe());
        holder.txtSwipeTimeOut.setText(sApply.get(position).gettimeOutSwipe());
        holder.txtSwipeTotal.setText(sApply.get(position).getTotaltimeSwipe());

        if(sApply.get(position).getStatus().equals("SwipeMistmach"))

        {
            holder.applySwipe.setText("Apply");
            holder.applySwipe.setTextColor(Color.WHITE);
            holder.applySwipe.setEnabled(true);
        }
        else if(sApply.get(position).getStatus().equals("SwipeMismatchApplied"))

        {
            holder.applySwipe.setText("Pending");
            holder.applySwipe.setTextColor(Color.WHITE);
            holder.applySwipe.setEnabled(false);
        }
        else if(sApply.get(position).getStatus().equals("SwipeMismatchApproved"))
        {

            holder.applySwipe.setText("Approved");
            holder.applySwipe.setTextColor(Color.GREEN);
            holder.applySwipe.setEnabled(false);
        }
        else if (sApply.get(position).getStatus().equals("SwipeMismatchRejected"))
        {
            holder.applySwipe.setText("Rejected");
            holder.applySwipe.setTextColor(Color.RED);
            holder.applySwipe.setEnabled(false);
        }




        holder.applySwipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(frag instanceof SwipeApplyFragment) {


                    String swipedate = holder.txtSwipeDate.getText().toString();

                    frag.GiveReason(v,swipedate,swipedate );
                }
            }

        });
        return convertView;
    }

    static class ViewHolder {
        TextView txtSwipeDay;
        TextView txtSwipeDate;
        TextView txtSwipeTimeIn;
        TextView txtSwipeTimeOut;
        TextView txtSwipeTotal;
        Button applySwipe;
    }
}
