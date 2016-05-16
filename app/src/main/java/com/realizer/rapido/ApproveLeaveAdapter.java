package com.realizer.rapido;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Suvarna.Ghoman on 02-09-2015.
 */
public class ApproveLeaveAdapter extends BaseAdapter {

    private static ArrayList<ApproveLeaveModel> approveLeaves;
    private LayoutInflater mApproveLeave;
    Context context;
    ListView lst;
    ApproveLeaveFragment frag;

    public ApproveLeaveAdapter(Context _context,  ArrayList<ApproveLeaveModel> _approveLeaveDetails,ListView lst,ApproveLeaveFragment frag)
    {
        approveLeaves = _approveLeaveDetails;
        this.context = _context;
        this.frag = frag;
        mApproveLeave = LayoutInflater.from(context);
        this.lst =lst;
    }
    @Override
    public int getCount() {
        return approveLeaves.size();
    }

    @Override
    public Object getItem(int position) {
        return approveLeaves.get(position);
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
            convertView = mApproveLeave.inflate(R.layout.approve_leave_list_layout, null);
            holder = new ViewHolder();
            holder.ApproveFName = (TextView) convertView.findViewById(R.id.txtApprovefname);
            holder.ApproveFirstName = (TextView) convertView.findViewById(R.id.txtApproveFirstName);
            holder.ApproveLastName = (TextView) convertView.findViewById(R.id.txtApproveLastName);
            holder.ApproveStartDate=(TextView)convertView.findViewById(R.id.txtApproveStartDate);
            holder.ApproveEndDate=(TextView)convertView.findViewById(R.id.txtApproveEndDate);
            holder.ApproveLeave =(Button)convertView.findViewById(R.id.imgBtnApproveLeave);
            holder.RejectLeave =(Button)convertView.findViewById(R.id.imgBtnRejectLeave);

            holder.ApproveLeave.setTag(position);
            holder.RejectLeave.setTag(position);

            holder.ApproveLeave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Integer position  = (Integer) v.getTag();
                    String SD = holder.ApproveStartDate.getText().toString();
                    String ED = holder.ApproveEndDate.getText().toString();
                    String LeaveType  = holder.ApproveFirstName.getText().toString();
                    String empId ="";

                    for(int i=0;i<approveLeaves.size();i++)
                    {
                       // Log.d("StartDateApp",""+i+" - "+approveLeaves.get(i).getApproveStartDate()+"-"+approveLeaves.get(i).getApproveEndDate());
                        if(SD.equals(approveLeaves.get(i).getApproveStartDate()) && ED.equals(approveLeaves.get(i).getApproveEndDate().replace("\"","")))
                        {
                            empId = approveLeaves.get(i).getEmpId();
                            break;
                        }
                    }
                    // call wcf for approve leave
                    if(frag instanceof ApproveLeaveFragment){
                        frag.ApproveLeave(empId, SD, ED, LeaveType);
                    }
                }
            });

            holder.RejectLeave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String SD = holder.ApproveStartDate.getText().toString();
                    String ED = holder.ApproveEndDate.getText().toString();
                    String LeaveType  = holder.ApproveFirstName.getText().toString();
                    String empId ="";

                    for(int i=0;i<approveLeaves.size();i++)
                    {
                        if(SD.equals(approveLeaves.get(i).getApproveStartDate()) && ED.equals(approveLeaves.get(i).getApproveEndDate().replace("\"","")))
                        {
                            empId = approveLeaves.get(i).getEmpId();
                            break;
                        }
                    }
                    // show popup for comment for leave
                    if(frag instanceof ApproveLeaveFragment){
                        frag.RejectLeave(empId, SD, ED, LeaveType);
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

        holder.ApproveFName.setText(approveLeaves.get(position).getName().replace("\"",""));
        holder.ApproveFirstName.setText(approveLeaves.get(position).getEmpApproveLeaveType());
        holder.ApproveLastName.setText(approveLeaves.get(position).getEmpApproveLeaveCount());
        holder.ApproveStartDate.setText(approveLeaves.get(position).getApproveStartDate());
        holder.ApproveEndDate.setText(approveLeaves.get(position).getApproveEndDate().replace("\"",""));



        return convertView;
    }
    static class ViewHolder
    {
        TextView ApproveFName;
        TextView ApproveFirstName;
        TextView ApproveLastName;
        TextView ApproveStartDate;
        TextView ApproveEndDate;
        Button ApproveLeave;
        Button RejectLeave;
    }

}
