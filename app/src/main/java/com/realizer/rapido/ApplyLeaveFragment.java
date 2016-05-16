package com.realizer.rapido;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Win on 10/20/2015.
 */
public class ApplyLeaveFragment extends Fragment implements View.OnClickListener,AsyncTaskCompleteListener<String>{
    private int pYear;
    private int pMonth;
    private int pDay;
    private String leaveCode="";
    StringBuilder result;
    Calendar cal;
    String formattedDate;
    long totalDays;
    long days;
    String[] empBalLeave;
    Double obj;
    private Typeface myFont;
    //double val;
    Date startD, endD;
    int count=0;
    SimpleDateFormat formatter;
    // Declare controls
    Button btnApplyLeave;
    TextView reasonOfLeave, eContact,txtLeaveBal,startDate, endDate, noOfDays, empUserName,rOnDate,address;
    Spinner spLeaveType;
    ImageButton dpStart, dpEnd;
    String isplan="false";
    Intent intent;
    ProgressDialog diaglog;
    CheckBox chkFirstHalfSD, chkSecondHalfSD, chkFirstHalfED, chkSecondHalfED, chkIsPlanned;

    //This integer will uniquely define the dialog to be used for displaying date picker.*/
    static final int DATE_PICKER_START = 0;
    static final int DATE_PICKER_END = 1;

    public ApplyLeaveFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.apply_leave_layout, container, false);
        Controls(rootView);
        btnApplyLeave.setOnClickListener(this);
        chkIsPlanned.setOnClickListener(this);
        chkFirstHalfSD.setOnClickListener(this);
        chkSecondHalfSD.setOnClickListener(this);
        chkFirstHalfED.setOnClickListener(this);
        chkSecondHalfED.setOnClickListener(this);


        spLeaveType.setSelection(0);

        //set focus on spinner
        spLeaveType.setFocusable(true);
        spLeaveType.setFocusableInTouchMode(true);

        Bundle b = this.getArguments();

        String applyLeaveDetails =b.getString("AnswerLeaveDetails");
        // Split by underscore
        String [] empLeaveDetails = applyLeaveDetails.split("_");
        ArrayList<String> leaveTypes = new ArrayList<String>();
        int counter=0;
        for (String leaveType : empLeaveDetails) {
            //split by comma

            String [] leaveDetails =leaveType.toString().split(",,");
            counter++;
            for(int i=1;i<leaveDetails.length-1;i++) {
                if(counter>1)
                    leaveTypes.add(leaveDetails[0].replace("\"", ""));
                //Log.d("Leave types=",leaveTypes.toString());
                break;
            }
        }
        // Fill spinner for leave types
        FillLeavesTypes(leaveTypes,rootView);
        // Display the current date in the TextView
        updateDisplayForStartDate();
        updateDisplayForEndDate();
        CheckDate();
        //to change the Current leave balance as spinner value changes
        DisplayCurrentLeaveBal(empLeaveDetails);


        return rootView;
    }
    //Callback received when the user "picks" a date in the dialog */
    private DatePickerDialog.OnDateSetListener dpStartDateListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {

            pMonth = monthOfYear;
            pDay = dayOfMonth;
            pYear = year;
            updateDisplayForStartDate();
            CheckDate();
        }
    };

    private DatePickerDialog.OnDateSetListener dpEndDateListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {
            pMonth = monthOfYear;
            pDay = dayOfMonth;
            pYear = year;
            updateDisplayForEndDate();
            CheckDate();
        }
    };

    //Updates the date in the TextView (Month is 0 based so add 1)
    private void updateDisplayForStartDate()
    {
        startDate.setText(new StringBuilder()
                .append(pMonth + 1).append("-")
                .append(pDay).append("-")
                .append(pYear));
    }

    private void updateDisplayForEndDate()
    {
        endDate.setText(new StringBuilder()
                .append(pMonth + 1).append("-")
                .append(pDay).append("-")
                .append(pYear));
    }

    public void CheckDate()
    {
        try {
            formatter= new SimpleDateFormat("MM-dd-yyyy");
            String startdate = startDate.getText().toString();
            Date date1 = formatter.parse(startdate);
            String enddate = endDate.getText().toString();
            Date date2 = formatter.parse(enddate);
            Calendar c = Calendar.getInstance();
            c.setTime(date2);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if(dayOfWeek>5) {
                c.add(Calendar.DATE, 3);
                formattedDate= formatter.format(c.getTime());
                rOnDate.setText(formattedDate);
            }
            else
            {
                c.add(Calendar.DATE,1);
                formattedDate = formatter.format(c.getTime());
                rOnDate.setText(formattedDate);
            }
            if(date2.compareTo(date1)<0) {
                Toast.makeText(getActivity(), "Selected dates are invalid!!!", Toast.LENGTH_SHORT).show();
                startDate.setText((cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + "-"
                        + cal.get(Calendar.YEAR));
                endDate.setText((cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + "-"
                        + cal.get(Calendar.YEAR));
            }
            else
            {
                totalDays = (date2.getTime() - date1.getTime());
                days = (TimeUnit.MILLISECONDS.toDays(totalDays));
                noOfDays.setText(Long.toString(days));
            }

        }
        catch (ParseException e1){
            e1.printStackTrace();
        }
    }


    // initialize controls
    public void Controls(View v)
    {
        // Capture View elements
        startDate = (TextView) v.findViewById(R.id.txtLeaveStartDate);
        endDate =(TextView)v.findViewById(R.id.txtLeaveEndDate);
        noOfDays = (TextView)v.findViewById(R.id.txtCurrentLeavesApplied);
        dpStart = (ImageButton) v.findViewById(R.id.dpStartDate);
        dpEnd =(ImageButton)v.findViewById(R.id.dpEndDate);
        btnApplyLeave= (Button) v.findViewById(R.id.btnApplyLeave);
        reasonOfLeave=(TextView)v.findViewById(R.id.edtReasonForLeave);
        eContact=(TextView)v.findViewById(R.id.edtEmergencyContact);
        spLeaveType =(Spinner)v.findViewById(R.id.spLeaveType);
        txtLeaveBal=(TextView)v.findViewById(R.id.txtLeaveBal);
        rOnDate = (TextView)v.findViewById(R.id.txtResumeOnDate);
        address =(TextView)v.findViewById(R.id.edtAddressOnLeave);

        // Listener for click event of the button
        dpStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //showDialog(DATE_PICKER_START);
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =
                        new DatePickerDialog(getActivity(), dpStartDateListener, mYear, mMonth, mDay);
                dialog.show();
            }});
        dpEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //showDialog(DATE_PICKER_END);
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =
                        new DatePickerDialog(getActivity(), dpEndDateListener, mYear, mMonth, mDay);
                dialog.show();
            } });

        cal = Calendar.getInstance();
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);
        pYear = cal.get(Calendar.YEAR);

        chkFirstHalfSD = (CheckBox)v.findViewById(R.id.chkStartDateMH);
        chkSecondHalfSD = (CheckBox)v.findViewById(R.id.chkStartDateAH);
        chkFirstHalfED = (CheckBox)v.findViewById(R.id.chkEndDateMH);
        chkSecondHalfED = (CheckBox)v.findViewById(R.id.chkEndDateAH);
        chkIsPlanned  = (CheckBox)v.findViewById(R.id.chkIsPlanned);
    }

    public Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case DATE_PICKER_START:
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(getActivity(), dpStartDateListener, mMonth, mDay,mYear);


            case DATE_PICKER_END:
                Calendar c1 = Calendar.getInstance();
                int mYear1 = c1.get(Calendar.YEAR);
                int mMonth1 = c1.get(Calendar.MONTH);
                int mDay1 = c1.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(getActivity(), dpEndDateListener, mMonth1, mDay1,mYear1);
        }
        return null;
    }
    //For validation of selected dates need to delcheck on which event it should be called
    public void ShowApproveLeave(View v) throws ParseException
    {

        count++;

            if (Integer.parseInt(noOfDays.getText().toString()) > Integer.parseInt(txtLeaveBal.getText().toString())) {

                Toast.makeText(getActivity(), "Your leave balance is less so unable to apply.", Toast.LENGTH_SHORT).show();
                count=0;
            } else if (reasonOfLeave.getText().toString().trim().equals("")) {

                Toast.makeText(getActivity(), "Enter reason for leave...", Toast.LENGTH_SHORT).show();
                count=0;
            } else if (address.getText().toString().trim().equals("")) {

                Toast.makeText(getActivity(), "Enter address on leave", Toast.LENGTH_SHORT).show();
                count=0;
            } else if (eContact.getText().toString().trim().equals("")) {

                Toast.makeText(getActivity(), "Enter emergency contact number...", Toast.LENGTH_SHORT).show();
                count=0;
            } else if (eContact.getText().toString().length() != 10) {

                Toast.makeText(getActivity(), "Enter valid 10 digit mobile number", Toast.LENGTH_SHORT).show();
                count=0;
            } else   if(count==1) {

                int newbal = Integer.parseInt(txtLeaveBal.getText().toString());
                //Log.d("New balance is", "" + newbal);
                String empId = UserGlobalData.getInstance().getEmpId();
                String leaveType = spLeaveType.getSelectedItem().toString();
                String isPlanned = String.valueOf(chkIsPlanned.isChecked());
                String startDateOfLeave = startDate.getText().toString();
                String firstHalfSD = String.valueOf(chkFirstHalfSD.isChecked());
                String secondHalfSD = String.valueOf(chkSecondHalfSD.isChecked());
                String endDateOfLeave = endDate.getText().toString();
                String firstHalfED = String.valueOf(chkFirstHalfED.isChecked());
                String secondHalfED = String.valueOf(chkSecondHalfED.isChecked());
                String resumeOnDate = rOnDate.getText().toString();
                String totalLeaves = noOfDays.getText().toString();
                String reasonForLeave = reasonOfLeave.getText().toString().replace(" ","%20");
                String emergencyContact = eContact.getText().toString();
                String Address = address.getText().toString().replace(" ","%20");

                int newbal1 = Integer.parseInt(noOfDays.getText().toString());
                int newBalValue = newbal - newbal1;
                txtLeaveBal.setText(empBalLeave[2]);
                ApplyLeaveAsyncTaskPost obj = new ApplyLeaveAsyncTaskPost(getActivity(), empId, leaveType, isPlanned, startDateOfLeave, firstHalfSD, secondHalfSD,
                        endDateOfLeave, firstHalfED, secondHalfED, resumeOnDate, totalLeaves,
                        reasonForLeave, emergencyContact, Address,this);
                    obj.execute();

            }


        else if(count>1)
        {
            Toast.makeText(getActivity(), "You can not applied leave twice...", Toast.LENGTH_LONG).show();
        }
    }

    // Fill leave types
    public void FillLeavesTypes(ArrayList leaveTypes,View v)
    {
        // set adapter
        Spinner spinner = (Spinner) v.findViewById(R.id.spLeaveType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, leaveTypes);
        adapter.setDropDownViewResource(R.layout.apply_leave_spiner);
        for(int i=1;i<adapter.toString().length()-1;) {
            spinner.setAdapter(adapter);
            break;
        }
        spinner.setSelection(0);
        //  txtLeaveBal.setText(leaveTypes.get(1).toString());
    }


    // To display current leave balance as spinner value changes
    public void DisplayCurrentLeaveBal(final String[] empLeaveDetails)
    {
        spLeaveType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

                for (String empLeaveBal : empLeaveDetails) {
                    empBalLeave = empLeaveBal.toString().split(",,");

                    String leavetype=spLeaveType.getSelectedItem().toString();

                    if(empBalLeave.length == 6) {
                        if (spLeaveType.getSelectedItem().toString().equals(empBalLeave[0])) {
                            txtLeaveBal.setText(empBalLeave[2]);
                            break;
                        }
                    }

                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    // To delcheck both delcheck boxes for Half day should not be checked for start Date
    public  void CheckFirstOrSecondHalf(View v) {
        if (chkFirstHalfSD.isChecked() && chkSecondHalfSD.isChecked()) {
            Toast.makeText(getActivity(), "Select morning half or afternoon half day", Toast.LENGTH_LONG).show();
            chkFirstHalfSD.setChecked(false);
            chkSecondHalfSD.setChecked(false);
            noOfDays.setText("0");
        } else if (chkFirstHalfED.isChecked() && chkSecondHalfED.isChecked()) {
            Toast.makeText(getActivity(), "Select morning half or afternoon half day", Toast.LENGTH_LONG).show();
            chkFirstHalfED.setChecked(false);
            chkSecondHalfED.setChecked(false);
            noOfDays.setText("0");
        }
        else if (chkFirstHalfSD.isChecked() && chkFirstHalfED.isChecked()) {
            if (startDate.getText().equals(endDate.getText())) {

                Toast.makeText(getActivity(), "Select any one morning half/Afternoon half from StartDate", Toast.LENGTH_LONG).show();
                chkFirstHalfSD.setChecked(false);
                chkFirstHalfED.setChecked(false);
                noOfDays.setText("0");

            }

        } else if (chkSecondHalfED.isChecked() && chkFirstHalfED.isChecked()) {
            if (startDate.getText().equals(endDate.getText())) {

                Toast.makeText(getActivity(), "Select any one morning half/Afternoon half from StartDate", Toast.LENGTH_LONG).show();
                chkSecondHalfED.setChecked(false);
                chkFirstHalfED.setChecked(false);
                noOfDays.setText("0");

            }

        }
        else if (chkSecondHalfSD.isChecked() && chkSecondHalfED.isChecked()) {
            if (startDate.getText().equals(endDate.getText())) {

                Toast.makeText(getActivity(), "Select morning half or afternoon half day", Toast.LENGTH_LONG).show();
                chkSecondHalfSD.setChecked(false);
                chkSecondHalfED.setChecked(false);
                noOfDays.setText("0");

            }

        }
        else if (chkFirstHalfSD.isChecked() && chkSecondHalfED.isChecked()) {
            if (startDate.getText().equals(endDate.getText())) {

                Toast.makeText(getActivity(), "Select morning half or afternoon half day", Toast.LENGTH_LONG).show();
                chkFirstHalfSD.setChecked(false);
                chkSecondHalfED.setChecked(false);
                noOfDays.setText("0");

            }

        }
        else if (chkFirstHalfED.isChecked() && chkSecondHalfSD.isChecked()) {
            if (startDate.getText().equals(endDate.getText())) {

                Toast.makeText(getActivity(), "Select morning half or afternoon half day", Toast.LENGTH_LONG).show();
                chkFirstHalfED.setChecked(false);
                chkSecondHalfSD.setChecked(false);
                noOfDays.setText("0");

            }
        }else if(chkFirstHalfED.isChecked()||chkFirstHalfSD.isChecked()||chkSecondHalfED.isChecked()||chkSecondHalfSD.isChecked()) {
            if (startDate.getText().equals(endDate.getText()))
                noOfDays.setText("0.5");
        }else if(!chkFirstHalfED.isChecked()&&!chkFirstHalfSD.isChecked()&&!chkSecondHalfED.isChecked()&&!chkSecondHalfSD.isChecked()) {
            noOfDays.setText("0");
        }
        else if (chkFirstHalfSD.isChecked() && chkFirstHalfED.isChecked()) {
            if (!
                    startDate.getText().equals(endDate.getText())) {

                Toast.makeText(getActivity(), "Select any one morning half/Afternoon half from StartDate", Toast.LENGTH_LONG).show();
                chkFirstHalfSD.setChecked(false);
                chkFirstHalfED.setChecked(false);
                noOfDays.setText("0");

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnApplyLeave:
                try {
                    ShowApproveLeave(v);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.chkStartDateMH:
                CheckFirstOrSecondHalf(v);
                break;

            case R.id.chkStartDateAH:
                CheckFirstOrSecondHalf(v);
                break;

            case R.id.chkEndDateMH:
                CheckFirstOrSecondHalf(v);
                break;

            case R.id.chkEndDateAH:
                CheckFirstOrSecondHalf(v);
                break;
            case R.id.chkIsPlanned:
                boolean checked = ((CheckBox) v).isChecked();
                isplan = String.valueOf(checked);
                break;


        }
    }

    @Override
    public void onTaskComplete(String result, int pos) {
        if (result.equalsIgnoreCase("true"))
            // Need to display alert dialog
            Toast.makeText(getActivity(), "Leave applied", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), "Server not responding please try again after sometime.", Toast.LENGTH_SHORT).show();

    }
}
