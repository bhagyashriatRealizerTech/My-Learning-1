package com.realizer.rapido;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Win on 10/20/2015.
 */
public class LeaveTakenRecordFragment extends Fragment implements View.OnClickListener {
    TextView userName, leaveManagerComment, leaveRecordNoOfDays, leaveRecordStartDate, leaveRecordEndDate,  leaveRecordReason, leaveRecordAddress, leaveRecordContact, leaveRecordResumeOn,LeaveRecordLeaveType;
    CheckBox chkLeaveRecordIsPlanned, chkLeaveRecordMHSD,chkLeaveRecordAHSD, chkLeaveRecordMHED, chkLeaveRecordAHED;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.leave_taken_record_layout, container, false);
        controls(rootView);

        setControls();
        getValues();
        return rootView;
    }
    public  void controls(View v)
    {

        LeaveRecordLeaveType=(TextView)v.findViewById(R.id.txtLeaveRecordLeaveType);
        leaveRecordNoOfDays=(TextView)v.findViewById(R.id.txtLeaveRecordNoOfDays);
        leaveRecordStartDate=(TextView)v.findViewById(R.id.txtLeaveRecordStartDate);
        leaveRecordEndDate=(TextView)v.findViewById(R.id.txtLeaveRecordEndDate);
        leaveRecordReason=(TextView)v.findViewById(R.id.txtLeaveRecordReason);
        leaveRecordAddress=(TextView)v.findViewById(R.id.txtLeaveRecordAddress);
        leaveRecordContact=(TextView)v.findViewById(R.id.txtLeaveRecordContact);
        leaveRecordResumeOn =(TextView)v.findViewById(R.id.txtLeaveRecordResumeOn);
        leaveManagerComment = (TextView)v.findViewById(R.id.txtLeaveComment);

        chkLeaveRecordIsPlanned=(CheckBox)v.findViewById(R.id.chkLeaveRecordIsPlanned);
        chkLeaveRecordMHSD= (CheckBox)v.findViewById(R.id.chkLeaveRecordMHSD);
        chkLeaveRecordAHSD=(CheckBox)v.findViewById(R.id.chkLeaveRecordAHSD);
        chkLeaveRecordMHED=(CheckBox)v.findViewById(R.id.chkLeaveRecordMHED);
        chkLeaveRecordAHED=(CheckBox)v.findViewById(R.id.chkLeaveRecordAHED);

        chkLeaveRecordIsPlanned.setEnabled(false);
        chkLeaveRecordMHSD.setEnabled(false);
        chkLeaveRecordAHSD.setEnabled(false);
        chkLeaveRecordMHED.setEnabled(false);
        chkLeaveRecordAHED.setEnabled(false);
    }

    public void  getValues()
    {

    }

    public void  setControls()
    {

        //"CL-Desc,,7-6-2015,,7-7-2015,,Approved_PL-Desc,,7-1-2015,,7-3-2015,,Approved_Casual Leave-Desc,,8-27-2015,,8-28-2015,,Pending_CasualLeave-Desc,,2-9-2015,,3-9-2015,,Pending"
        Bundle b = this.getArguments();
        String leaveRecord =b.getString("AnswerLeaveRecordDetails" );
        String [] empLeaveDetails = leaveRecord.split("_");


        for (String leaveRecord1 : empLeaveDetails) {
            String[] leaveRecordDetails = leaveRecord1.split(",,");
            for(int i=0;i<leaveRecordDetails.length-1;i++ ) {
                LeaveRecordLeaveType.setText(leaveRecordDetails[0].replaceAll("\"",""));
                leaveRecordStartDate.setText(leaveRecordDetails[2]);
                leaveRecordEndDate.setText(leaveRecordDetails[5]);
                leaveRecordNoOfDays.setText(leaveRecordDetails[8]);
                leaveRecordResumeOn.setText(leaveRecordDetails[9]);
                leaveRecordReason.setText(leaveRecordDetails[10]);
                leaveManagerComment.setText(leaveRecordDetails[10]);
                leaveRecordAddress.setText(leaveRecordDetails[11]);
                leaveRecordContact.setText(leaveRecordDetails[12].replaceAll("\"",""));

                if (leaveRecordDetails[1].toString().equalsIgnoreCase("true"))
                    chkLeaveRecordIsPlanned.setChecked(true);
                else
                    chkLeaveRecordIsPlanned.setChecked(false);

                if (leaveRecordDetails[3].toString().equalsIgnoreCase("true"))
                    chkLeaveRecordMHSD.setChecked(true);
                else
                    chkLeaveRecordMHSD.setChecked(false);


                if (leaveRecordDetails[4].toString().equalsIgnoreCase("true"))
                    chkLeaveRecordAHSD.setChecked(true);
                else
                    chkLeaveRecordAHSD.setChecked(false);

                if (leaveRecordDetails[6].toString().equalsIgnoreCase("true"))
                    chkLeaveRecordMHED.setChecked(true);
                else
                    chkLeaveRecordMHED.setChecked(false);

                if (leaveRecordDetails[7].toString().equalsIgnoreCase("true"))
                    chkLeaveRecordAHED.setChecked(true);
                else
                    chkLeaveRecordAHED.setChecked(false);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}
