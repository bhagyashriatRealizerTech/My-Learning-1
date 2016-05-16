package com.realizer.rapido;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

/**
 * Created by Win on 10/20/2015.
 */
public class LeaveTakenDetailsFragment extends Fragment implements View.OnClickListener,AsyncTaskCompleteListener<String> {
    Spinner spYear;
    StringBuilder result;
    TextView empUserName;
    String startDate, endDate;
    ListView listViewLeavesTaken;
    //String
    ArrayList<LeaveTakenDetailsModel> empLeavesTaken;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.leave_taken_details_layout, container, false);
        spYear =(Spinner) rootView.findViewById(R.id.spYearsForLeaveRecords);
       /* empUserName =(TextView)rootView.findViewById(R.id.txtUserName);

        empUserName.setText("Hi "+ UserGlobalData.getInstance().getEmpFirstName() +",");*/
        final String empId = UserGlobalData.getInstance().getEmpId();
        listViewLeavesTaken = (ListView) rootView.findViewById(R.id.lstViewLeavesTaken);
        Bundle b = this.getArguments();
        String s = b.getString("AnswerLeavesRecords").toString();
        empLeavesTaken = GetLeaveTakenDetails(s);
        if(empLeavesTaken.equals(null))
        {
            Toast.makeText(getActivity(), "No data Available for Leaves", Toast.LENGTH_SHORT).show();
        }
        else {

            listViewLeavesTaken.setAdapter(new LeaveTakenDetailsAdapter(getActivity(), empLeavesTaken));
        }

        // Call to show details of record on click;
        listViewLeavesTaken.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listViewLeavesTaken.getItemAtPosition(position);
                LeaveTakenDetailsModel obj = (LeaveTakenDetailsModel)o;

                // call to wcf service to get details of leave taken
                String startDate = obj.getstartDate();
                String endDate= obj.getendDate();
                String empId = UserGlobalData.getInstance().getEmpId();


                LeaveTakenDetailsRecordAsyncTaskGet objLeaveRecordDetails = new LeaveTakenDetailsRecordAsyncTaskGet(empId,startDate,endDate, getActivity(),LeaveTakenDetailsFragment.this);
                objLeaveRecordDetails.execute();


            }
        });

        FillSpinnerWithYear();

        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String year = spYear.getSelectedItem().toString();

                LeaveTakenDetailsAsyncTaskGet obj = new LeaveTakenDetailsAsyncTaskGet(empId, year, getActivity(),LeaveTakenDetailsFragment.this);
                obj.execute();


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }
    public  void FillSpinnerWithYear()
    {
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String [] years = new String[10];
        int j =-9;
        for(int i=0;i<10;i++) {
            years[i] = String.valueOf(Integer.parseInt(year)+ j);
            j++;
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner,years);
        yearAdapter.setDropDownViewResource(R.layout.apply_leave_spiner);
        spYear.setAdapter(yearAdapter);
        // to set default value to current year
        spYear.setSelection(9);

    }
    private ArrayList<LeaveTakenDetailsModel> GetLeaveTakenDetails(String empLeaveRecordsByYear)
    {
        String leavesDetails ;
        if(empLeaveRecordsByYear.equals(""))
        {
            Bundle b = this.getArguments();
            leavesDetails =(b.getString("AnswerLeavesRecords")).toString();
        }
        else
        {
            leavesDetails = empLeaveRecordsByYear;
        }

        String [] empLeaveRecords =  leavesDetails.split("_");
        ArrayList<LeaveTakenDetailsModel> results = new ArrayList<>();

        for(String leaveTaken : empLeaveRecords)
        {
            String [] lTaken = leaveTaken.toString().split(",,");
            if(lTaken.length>1) {

                LeaveTakenDetailsModel eLeaveDetailsDetails = new LeaveTakenDetailsModel();
                eLeaveDetailsDetails.setleaveType(lTaken[0].replaceAll("\"", ""));
                eLeaveDetailsDetails.setstartDate(lTaken[1].replaceAll("\"", ""));
                eLeaveDetailsDetails.setendDatee(lTaken[2].replaceAll("\"", ""));
                eLeaveDetailsDetails.setResumedate(lTaken[3].replaceAll("\"", ""));
                eLeaveDetailsDetails.setstatus(lTaken[4].replaceAll("\"", ""));

                results.add(eLeaveDetailsDetails);

            }
            else
                break;

        }
        Collections.sort(results, Collections.reverseOrder());
        return results;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTaskComplete(String result, int pos) {

        switch (pos)
        {
            case 1:
                String leaveRecord = "";
                if(result.equals(""))
                    Toast.makeText(getActivity(), "Server not responding try again",Toast.LENGTH_LONG).show();
                else
                    leaveRecord = result;
                Bundle bundle = new Bundle();
                LeaveTakenRecordFragment fragment = new LeaveTakenRecordFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                bundle.putString("AnswerLeaveRecordDetails",leaveRecord);
                fragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.commit();

                break;
            case 3:
                String eLeavesRecordsByYears = result.toString();
                empLeavesTaken = GetLeaveTakenDetails(eLeavesRecordsByYears);
                listViewLeavesTaken.setAdapter(null);
                listViewLeavesTaken.setAdapter(new LeaveTakenDetailsAdapter(getActivity(), empLeavesTaken));
                break;

        }

    }
}
