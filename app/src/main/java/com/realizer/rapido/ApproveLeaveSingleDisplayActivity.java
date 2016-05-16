package com.realizer.rapido;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ApproveLeaveSingleDisplayActivity extends CommonMethodsInActivity {
    TextView empIdForApprove, empUserName, leaveRecordType, leaveRecordNoOfDays, leaveRecordStartDate, leaveRecordEndDate,  leaveRecordReason, leaveRecordAddress, leaveRecordContact, leaveRecordResumeOn, firstName, lastName;
    CheckBox chkLeaveRecordIsPlanned, chkLeaveRecordMHSD,chkLeaveRecordAHSD, chkLeaveRecordMHED, chkLeaveRecordAHED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approve_leave_single_display_layout);

        controls();

        empUserName.setText("Hi "+UserGlobalData.getInstance().getEmpFirstName()+",");

        Intent intent = getIntent();
        String[] displayApproveLeaveDetails = intent.getStringExtra("ApproveLeaveSingleDisplay").toString().split(",,");

        setControls(displayApproveLeaveDetails);


    }

    public  void controls()
    {
        empIdForApprove = (TextView)findViewById(R.id.txtEmpId);
        empUserName = (TextView)findViewById(R.id.txtUserName);
        firstName=(TextView)findViewById(R.id.txtApproveFirstName);
        lastName =(TextView)findViewById(R.id.txtApproveLastName);
        leaveRecordType=(TextView)findViewById(R.id.txtApproveLeaveRecordType);
        leaveRecordNoOfDays=(TextView)findViewById(R.id.txtApproveLeaveRecordNoOfDays);
        leaveRecordStartDate=(TextView)findViewById(R.id.txtApproveLeaveRecordStartDate);
        leaveRecordEndDate=(TextView)findViewById(R.id.txtApproveLeaveRecordEndDate);
        leaveRecordReason=(TextView)findViewById(R.id.txtApproveLeaveRecordReason);
        leaveRecordAddress=(TextView)findViewById(R.id.txtApproveLeaveRecordAddress);
        leaveRecordContact=(TextView)findViewById(R.id.txtApproveLeaveRecordContact);
        leaveRecordResumeOn =(TextView)findViewById(R.id.txtApproveLeaveRecordResumeOn);

        chkLeaveRecordIsPlanned=(CheckBox)findViewById(R.id.chkApproveLeaveRecordIsPlanned);
        chkLeaveRecordMHSD= (CheckBox)findViewById(R.id.chkApproveLeaveRecordMHSD);
        chkLeaveRecordAHSD=(CheckBox)findViewById(R.id.chkApproveLeaveRecordAHSD);
        chkLeaveRecordMHED=(CheckBox)findViewById(R.id.chkApproveLeaveRecordMHED);
        chkLeaveRecordAHED=(CheckBox)findViewById(R.id.chkApproveLeaveRecordAHED);

        chkLeaveRecordIsPlanned.setEnabled(false);
        chkLeaveRecordMHSD.setEnabled(false);
        chkLeaveRecordAHSD.setEnabled(false);
        chkLeaveRecordMHED.setEnabled(false);
        chkLeaveRecordAHED.setEnabled(false);
    }


    public void  setControls(String[] displayApplyLeave)
    {
        //"AP_14,,Krishna,,Ghoman,,Casual Leave,,true,,10-7-2015,,false,,true,,12-7-2015,,true,,false,,3,,13-7-2015,,Picnic,,Pune,,9503994311"
        empIdForApprove.setText(displayApplyLeave[0]);
        firstName.setText(displayApplyLeave[1]);
        lastName.setText(displayApplyLeave[2]);
        leaveRecordType.setText(displayApplyLeave[3]);
        leaveRecordStartDate.setText(displayApplyLeave[5]);
        leaveRecordEndDate.setText(displayApplyLeave[8]);
        leaveRecordNoOfDays.setText(displayApplyLeave[11]);
        leaveRecordResumeOn.setText(displayApplyLeave[12]);
        leaveRecordReason.setText(displayApplyLeave[13]);
        leaveRecordAddress.setText(displayApplyLeave[14]);
        leaveRecordContact.setText(displayApplyLeave[15]);

        if(displayApplyLeave[4].equalsIgnoreCase("true"))
            chkLeaveRecordIsPlanned.setChecked(true);
        if(displayApplyLeave[6].equalsIgnoreCase("true"))
            chkLeaveRecordMHSD.setChecked(true);
        if(displayApplyLeave[7].equalsIgnoreCase("true"))
            chkLeaveRecordAHSD.setChecked(true);
        if(displayApplyLeave[9].equalsIgnoreCase("true"))
            chkLeaveRecordMHED.setChecked(true);
        if(displayApplyLeave[10].equalsIgnoreCase("true"))
            chkLeaveRecordAHED.setChecked(true);

    }
    public void ApproveSingleLeave(View  v)
    {
        Toast.makeText(getApplicationContext(), "Leave approved", Toast.LENGTH_LONG).show();

    }

    public void RejectSingleLeave(View v)
    {

        //Show popup
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ApproveLeaveSingleDisplayActivity.this);
        // Set up the input
        final EditText reason = new EditText(this);
        reason.setHint("Enter reason here");

        // Specify the type of input expected;
        reason.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialogBuilder.setView(reason);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        reasonForRejection = reason.getText().toString();
                        if (reasonForRejection.equals("")) {
                            Toast.makeText(getApplicationContext(),"Enter reason for rejection", Toast.LENGTH_LONG).show();
                        } else {
                            // call to save reason

                            Toast.makeText(getApplicationContext(), "Leave rejected", Toast.LENGTH_LONG).show();


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

}
