package com.realizer.rapido;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

/**
 * Created by Win on 10/21/2015.
 */
public class ApproveLeaveFragment extends Fragment implements View.OnClickListener,AsyncTaskCompleteListener<String> {

    View rootView;
    String empId ="";
    String reasonForRejection;
    Bundle b;
    ArrayList<ApproveLeaveModel> empLeaves;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.approve_leave_layout, container, false);
        empId = UserGlobalData.getInstance().getEmpId();
        // To get employee applied leave details
        empLeaves = GetEmployeeLeaveDetails();

        final ListView listApproveLeave = (ListView) rootView.findViewById(R.id.lstApproveLeave);
        ApproveLeaveAdapter adt= new ApproveLeaveAdapter(getActivity(), empLeaves,listApproveLeave,ApproveLeaveFragment.this);
        adt.notifyDataSetChanged();
        listApproveLeave.setAdapter(adt);
        listApproveLeave.setOnItemClickListener(onItemClickListener);
        return rootView;
    }

    private ListView.OnItemClickListener onItemClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            // Wcf call to display leave applied in details
            String empIdForApplyLeave = empLeaves.get(position).getEmpId();
            ApproveLeaveSingleRecordAsyncTaskGet obj = new ApproveLeaveSingleRecordAsyncTaskGet(empIdForApplyLeave, getActivity());
            try{
                StringBuilder result = obj.execute().get();
               // Log.d("Result for approve=", result.toString());
                String resultApproveLeaveDetail = result.toString();
                // Get output as
                // String resultApproveLeaveDetail="AP-14,,Krishna,,Ghoman,,Casual Leave,,true,,10-7-2015,,false,,true,,12-7-2015,,true,,false,,3,,13-7-2015,,Picnic,,Pune,,9503994311";
                Intent iApproveLeaveSingle = new Intent(getActivity(), ApproveLeaveSingleDisplayActivity.class);
                iApproveLeaveSingle.putExtra("ApproveLeaveSingleDisplay",resultApproveLeaveDetail);
                startActivity(iApproveLeaveSingle);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    };
    private View.OnClickListener approveButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View parentRow = (View) v.getParent();
            ListView listView = (ListView) parentRow.getParent();
            final int position = listView.getPositionForView(parentRow);
            String e = (String) v.getTag();
            Toast.makeText(getActivity(), "Approve" + position + "  " + e, Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener rejectButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View parentRow = (View) v.getParent();
            ListView listView = (ListView) parentRow.getParent();
            final int position = listView.getPositionForView(parentRow);
            String e = (String) v.getTag();
            Toast.makeText(getActivity(),"Reject"+position +"  "+e,Toast.LENGTH_LONG).show();
        }
    };


    // Get emp leave details from wcf This will be changed
    private ArrayList<ApproveLeaveModel>  GetEmployeeLeaveDetails()
    {

        b= this.getArguments();
        String [] approveLeaves = b.getString("AnswerApproveLeave").toString().split("_");
        ArrayList<ApproveLeaveModel>  approveLeaveList = new ArrayList<>();
        //Log.d("approveLeaves=",approveLeaves.toString());
        for(String aLeave : approveLeaves) {

            String [] leave = aLeave.toString().split(",,");
            ApproveLeaveModel aLeaves = new ApproveLeaveModel();
            //Pune Emp,,CL,,1,,7-6-2015,,7-7-2015

            aLeaves.setEmpId(leave[0]);
            aLeaves.setName(leave[1]);
            aLeaves.setEmpApproveLeaveType(leave[2]);
            aLeaves.setEmpApproveLeaveCount(leave[3]);
            aLeaves.setApproveStartDate(leave[4]);
            aLeaves.setApproveEndDate(leave[5]);
            approveLeaveList.add(aLeaves);

        }
        Collections.sort(approveLeaveList, Collections.reverseOrder());
        return approveLeaveList;
    }

    // Approve Leave call wcf
    public void ApproveLeave(String empId,String SD,String ED,String LeaveType)
    {

        String MgrsId = UserGlobalData.getInstance().getEmpId();
        String Status = "1";
        ApproveLeaveRejectedAsyncTaskPost obj = new ApproveLeaveRejectedAsyncTaskPost(empId, SD, ED, "Test",LeaveType,Status,MgrsId,getActivity(),ApproveLeaveFragment.this);
       obj.execute();

    }

    public void RejectLeave( final String empId, final String SD, final String ED,final String leaveType) {
        //Show popup
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Set up the input
        final EditText reason = new EditText(getActivity());
        reason.setHint("Enter reason here");

        // Specify the type of input expected;
        reason.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialogBuilder.setView(reason);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        reasonForRejection = reason.getText().toString().replace(" ","%20");
                        if (reasonForRejection.equals("")) {
                            Toast.makeText(getActivity(),"Enter reason for rejection", Toast.LENGTH_SHORT).show();
                        } else {
                            // call to save reason
                            String MgrsId = UserGlobalData.getInstance().getEmpId();
                            String Status = "-2";
                            ApproveLeaveRejectedAsyncTaskPost obj = new ApproveLeaveRejectedAsyncTaskPost(empId, SD, ED, reasonForRejection,leaveType,Status,MgrsId,getActivity(),ApproveLeaveFragment.this);
                            obj.execute();

                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    //display Reason alert box if leave is rejected
    public void GiveReason(View v)
    {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.inputdialogbox, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(),"Reason submitted", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTaskComplete(String result, int pos) {
        if(pos==1) {
            String applyLeave = result.replace("\"", "");
            // output as true or false
            if (applyLeave.equalsIgnoreCase("true")) {
                Toast.makeText(getActivity(), "Leave approved", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getActivity(), "Reason not submitted. try again", Toast.LENGTH_SHORT).show();
            }
        }

        else if(pos==2)
        {
            String reasonSubmitted = result.toString().replace("\"","");

            // output as true or false
            if(reasonSubmitted.equalsIgnoreCase("true")){
                Toast.makeText(getActivity(), "Leave rejected", Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(getActivity(), "Reason not submitted. try again", Toast.LENGTH_SHORT).show();
        }

    }
}
