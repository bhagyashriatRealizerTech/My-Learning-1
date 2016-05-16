package com.realizer.rapido;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Win on 10/20/2015.
 */
public class BalanceLeaveDetailsFragment extends Fragment implements View.OnClickListener{
    TextView empUserName;
    private int pYear;
    TextView displayyear;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.leave_balance_layout, container, false);

        ArrayList<BalanceLeaveDetailsModel> empLeavesBal = GetBalanceLeaveDetailsModel();

        displayyear=(TextView)rootView.findViewById(R.id.displayyear);
        final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        displayyear.setText("Leave Balance for " + pYear);

        final ListView listViewLeaveBalance = (ListView) rootView.findViewById(R.id.lstLeaveBalance);
        listViewLeaveBalance.setAdapter(new BalanceLeaveDetailsAdapter(getActivity(), empLeavesBal));
        return rootView;
    }
    // Get emp leave details from wcf
    private ArrayList<BalanceLeaveDetailsModel> GetBalanceLeaveDetailsModel()
    {
       Bundle b =getArguments();

        // to get output string
        // AnswerLeaveDetails ="CL,Casual Leave,2_SL,Sick Leave,10"
        String [] empLeaveDetails = (b.getString("AnswerLeaveDetails")).toString().split("_");
        ArrayList<BalanceLeaveDetailsModel> results = new ArrayList<>();

        int counter=0;
        for(String empleave :empLeaveDetails) {
            String [] leaveDetails = empleave.split(",,");
            counter++;
            if(counter>1) {
                BalanceLeaveDetailsModel eLeaveDetailsDetails = new BalanceLeaveDetailsModel();
                eLeaveDetailsDetails.setleaveCatagory(leaveDetails[0].replaceAll("\"", ""));
                eLeaveDetailsDetails.setleaveBalance(leaveDetails[2].replaceAll("\"", ""));
                eLeaveDetailsDetails.setAnnualAllocation(leaveDetails[3].replaceAll("\"", ""));
                eLeaveDetailsDetails.setAccuredLeaves(leaveDetails[4].replaceAll("\"", ""));
                eLeaveDetailsDetails.setLeavesTaken(leaveDetails[5].replaceAll("\"", ""));
                results.add(eLeaveDetailsDetails);
            }

        }
        return results;
    }


    @Override
    public void onClick(View v) {

    }
}
