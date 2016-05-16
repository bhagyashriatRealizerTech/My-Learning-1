package com.realizer.rapido;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Suvarna.Ghoman on 02-09-2015.
 */
public class ApproveSwipeAdapter extends BaseAdapter {

    private static ArrayList<ApproveSwipeModel> approveSwipes;
    private LayoutInflater mApproveLeave;
    Context context;
    ApproveSwipeFragment frag;

    public ApproveSwipeAdapter(Context _context,  ArrayList<ApproveSwipeModel> _approveSwipeDetails,ApproveSwipeFragment frag)
    {
        approveSwipes = _approveSwipeDetails;
        this.context = _context;
        this.frag = frag;
        mApproveLeave = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return approveSwipes.size();
    }

    @Override
    public Object getItem(int position) {
        return approveSwipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null)
        {
            convertView = mApproveLeave.inflate(R.layout.approve_swipe_details_list_layout, null);
            holder = new ViewHolder();
            holder.ApproveSwipeFirstName = (TextView) convertView.findViewById(R.id.txtApproveSwipeFirstName);
           // holder.ApproveSwipeLastName = (TextView) convertView.findViewById(R.id.txtApproveSwipeLastName);
            holder.ApproveSwipeDay=(TextView)convertView.findViewById(R.id.txtApproveDayForSwipe);
            holder.ApproveSwipeDate=(TextView)convertView.findViewById(R.id.txtApproveSwipeDate);
            holder.ApproveSwipeTime =(TextView)convertView.findViewById(R.id.txtApproveSwipeTime);
            holder.ApproveSwipeReason =(TextView)convertView.findViewById(R.id.txtSwipeMismatchReason);
            holder.ApproveSwipe=(Button)convertView.findViewById(R.id.btnApproveSwipe);
            holder.RejectSwipe=(Button)convertView.findViewById(R.id.btnRejectSwipe);

            holder.ApproveSwipe.setTag(position);
            holder.RejectSwipe.setTag(position);

            holder.ApproveSwipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dateForSwipe = holder.ApproveSwipeDate.getText().toString();
                    String empId="";
                    for(int i=0;i<approveSwipes.size();i++) {

                            empId = approveSwipes.get(i).getEmpId().replace("\"", "");
                            break;

                    }
                    // call wcf for approve leave
                    if(frag instanceof ApproveSwipeFragment){
                        frag.ApproveSwipe(empId, dateForSwipe);
                    }
                }
            });

            holder.RejectSwipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dateForSwipe = holder.ApproveSwipeDate.getText().toString();
                    String empId="";
                    for(int i=0;i<approveSwipes.size();i++) {
                        empId = approveSwipes.get(i).getEmpId().replace("\"", "");
                        break;
                    }
                    // show popup for comment for leave
                    if(frag instanceof ApproveSwipeFragment){
                        frag.RejectSwipe(empId, dateForSwipe);
                    }

                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#87CEFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        holder.ApproveSwipeFirstName.setText(approveSwipes.get(position).getEmpApproveSwipeFirstName());
       // holder.ApproveSwipeLastName.setText(approveSwipes.get(position).getEmpApproveSwipeLastName());
        holder.ApproveSwipeDay.setText(approveSwipes.get(position).getApproveSwipeDay());
        holder.ApproveSwipeDate.setText(approveSwipes.get(position).getApproveSwipeDate());
        holder.ApproveSwipeTime.setText(approveSwipes.get(position).getApproveSwipeTime());
        holder.ApproveSwipeReason.setText(approveSwipes.get(position).getapproveSwipeReason());


        return convertView;
    }
    static class ViewHolder
    {
        TextView ApproveSwipeFirstName;
        //TextView ApproveSwipeLastName;
        TextView ApproveSwipeDay;
        TextView ApproveSwipeDate;
        TextView ApproveSwipeTime;
        TextView ApproveSwipeReason;
        Button ApproveSwipe;
        Button RejectSwipe;
    }

}
