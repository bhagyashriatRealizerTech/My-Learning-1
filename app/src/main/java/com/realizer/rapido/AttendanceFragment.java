package com.realizer.rapido;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
public class AttendanceFragment extends Fragment implements View.OnClickListener,AsyncTaskCompleteListener<String> {
    TextView empName;
    ImageView sortOrder;
    Spinner spMonths;
    View v;
    String[] months = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November","December"};
    StringBuilder result;
    String [] attendancDetails;
    ArrayList<AttendanceModel> attendance;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.attendance_layout, container, false);
        v =rootView;


        sortOrder =(ImageView)rootView.findViewById(R.id.imgOrder);
        sortOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeOrder(rootView);

            }
        });
        Bundle b = this.getArguments();
        // to get output string
        // AnswerLeaveDetails ="CL,Casual Leave,2_SL,Sick Leave,10"
        attendancDetails = (b.getString("AnswerAttendance")).toString().split("_");
        spMonths =(Spinner)rootView.findViewById(R.id.spMonthsForAttendance);
        ArrayAdapter<String> monthsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,months);
        monthsAdapter.setDropDownViewResource(R.layout.apply_leave_spiner);
        spMonths.setAdapter(monthsAdapter);

        Calendar c2 =Calendar.getInstance();
        int month = c2.get(Calendar.MONTH);
        spMonths.setSelection(month);
        final ListView listAttendance = (ListView) rootView.findViewById(R.id.attendanceList);
        String[] leaveDetails = attendancDetails[0].split(",,");

        if(leaveDetails.length>1) {
            attendance = GetAttendanceList(attendancDetails, "Ascending");
            listAttendance.setAdapter(new AttendanceAdapter(getActivity(),attendance));
        }
        else
        listAttendance.setAdapter(null);
            Toast.makeText(getActivity(), "No Attendance data for This month", Toast.LENGTH_SHORT).show();

        spMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String month = String.valueOf(spMonths.getSelectedItemPosition()+1);
                Calendar c3 =Calendar.getInstance();
                int year=c3.get(Calendar.YEAR);
                final String empId = UserGlobalData.getInstance().getEmpId();

                AttendanceAsyncTaskGet obj = new AttendanceAsyncTaskGet(empId, month,String.valueOf(year), getActivity(),AttendanceFragment.this);
                obj.execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return rootView;
    }
    // Get emp leave details from wcf
    private ArrayList<AttendanceModel> GetAttendanceList(String [] attendancDetails, String order)
    {
        ArrayList<AttendanceModel> results = new ArrayList<>();
        if(order.equals("Ascending"))
        {
            String[] leave = attendancDetails[0].split(",,");

        if(leave.length>1) {

            for (int i = 0; i < attendancDetails.length; i++) {
                String[] leaveDetails = attendancDetails[i].split(",,");
                AttendanceModel aDetails = new AttendanceModel();
                aDetails.setattendanceDate(leaveDetails[0].replace("\"", ""));
                aDetails.setattendanceInOut(leaveDetails[1] + "-" + leaveDetails[2]);
                aDetails.setattedanceTotalHrs(leaveDetails[3].replace("\"", ""));
                results.add(aDetails);
            }
            Collections.sort(results);
        }
        else
            Toast.makeText(getActivity(), "No Attendance data for This month", Toast.LENGTH_SHORT).show();
        }
        else {
            String[] leave = attendancDetails[0].split(",,");

            if(leave.length>1)
            {
                for (int i = attendancDetails.length - 1; i >= 0; i--)
                {
                    String[] leaveDetails = attendancDetails[i].split(",,");
                    AttendanceModel aDetails = new AttendanceModel();
                    aDetails.setattendanceDate(leaveDetails[0].replace("\"", ""));
                    aDetails.setattendanceInOut(leaveDetails[1] + "-" + leaveDetails[2]);
                    aDetails.setattedanceTotalHrs(leaveDetails[3].replace("\"", ""));
                    results.add(aDetails);
                }
                Collections.sort(results, Collections.reverseOrder());
            }
            else

                Toast.makeText(getActivity(), "No Attendance data for This month", Toast.LENGTH_SHORT).show();

        }
        return results;
    }

    public void ChangeOrder(View v)
    {
        String backgroundImageName = (String) sortOrder.getTag();
        if(backgroundImageName.equals("Ascending")) {
            attendance = GetAttendanceList(attendancDetails, "Descending");
            sortOrder.setImageResource(R.drawable.uparrow);
            sortOrder.setTag("Descending");
        }
        else
        {
            attendance = GetAttendanceList(attendancDetails, "Ascending");
            sortOrder.setImageResource(R.drawable.downarrow);
            sortOrder.setTag("Ascending");
        }
        final ListView listAttendance = (ListView) v.findViewById(R.id.attendanceList);
        listAttendance.setAdapter(new AttendanceAdapter(getActivity(),attendance));
        listAttendance.deferNotifyDataSetChanged();

    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTaskComplete(String result, int pos) {
        final ListView listAttendance = (ListView) v.findViewById(R.id.attendanceList);
        String aDetails = result;
        attendancDetails = (aDetails.split("_"));
        String[] leave = attendancDetails[0].split(",,");

        if(leave.length>1)
       {
            attendance = GetAttendanceList(attendancDetails, "Ascending");
            listAttendance.setAdapter(new AttendanceAdapter(getActivity(), attendance));
            listAttendance.deferNotifyDataSetChanged();
        }
        else
        listAttendance.setAdapter(null);
            Toast.makeText(getActivity(),"No Attendance data for This month",Toast.LENGTH_SHORT).show();

    }
}
