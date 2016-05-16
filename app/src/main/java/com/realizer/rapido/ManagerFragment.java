package com.realizer.rapido;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by Win on 10/21/2015.
 */
public class ManagerFragment extends Fragment implements View.OnClickListener , AsyncTaskCompleteListener<String>{

    TextView mgrLeaveRequest,mgrSwipeRequest,mgrTimeshetRequest,empUserName, empTimeIn,empApplyleave,empLeaveBal,empLeaveRecord,empAttendance,empSwipeRegularization,empTimesheetEntry,empTimesheetListing,empHolidaylist,empSurvey;

    String empId;
    StringBuilder result ;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.managerdashboardlayout, container, false);
        // Initialize controls
        Controls(rootView);
        mgrLeaveRequest.setOnClickListener(this);
        mgrSwipeRequest.setOnClickListener(this);
        mgrTimeshetRequest.setOnClickListener(this);
        empApplyleave.setOnClickListener(this);
        empLeaveBal.setOnClickListener(this);
        empLeaveRecord.setOnClickListener(this);
        empAttendance.setOnClickListener(this);
        empSwipeRegularization.setOnClickListener(this);
        empTimesheetEntry.setOnClickListener(this);
        empTimesheetListing.setOnClickListener(this);
        empHolidaylist.setOnClickListener(this);
        empSurvey.setOnClickListener(this);
        empTimeIn.setOnClickListener(this);


        if(UserGlobalData.getInstance().getEmpTimeIn().equals(""))
            empTimeIn.setText("--");

        else
            empTimeIn.setText(UserGlobalData.getInstance().getEmpTimeIn());


        empId = UserGlobalData.getInstance().getEmpId();


        return rootView;
    }
    public void Controls(View v)
    {
        mgrLeaveRequest= (TextView)v.findViewById(R.id.txtApproveLeave);
        mgrSwipeRequest = (TextView)v.findViewById(R.id.txtApproveSwipe);
        mgrTimeshetRequest= (TextView)v.findViewById(R.id.txtApproveTimeSheet);
        empApplyleave = (TextView)v.findViewById(R.id.txtApplyLeave);
        empLeaveBal = (TextView)v.findViewById(R.id.txtLeaveBalance);
        empLeaveRecord = (TextView)v.findViewById(R.id.txtLeaveRecords);
        empAttendance= (TextView)v.findViewById(R.id.txtAttendance);
        empSwipeRegularization= (TextView)v.findViewById(R.id.txtSwipeRegularization);
        empTimesheetEntry= (TextView)v.findViewById(R.id.txtTimeSheetEntry);
        empTimesheetListing= (TextView)v.findViewById(R.id.txtTimeSheetListing);
        empHolidaylist= (TextView)v.findViewById(R.id.txtHolidayList);
        empSurvey= (TextView)v.findViewById(R.id.txtSurvey);
        empTimeIn=(TextView)v.findViewById(R.id.txtTimeIn);

    }
    // For approve leave
    public void ApproveLeave(String res) {

            String empLeavesRecords = res;
            // Get Output as
            // String empLeavesRecords = "AP-14,,Suvarna,, Ghoman,,12-5-2015,,17-5-2015_AP-16,,Atharva,, Ghoman,,20-5-2015,,22-5-2015";
            ApproveLeaveFragment fragment = new ApproveLeaveFragment();
            Bundle bundle = new Bundle();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            bundle.putString("AnswerApproveLeave",empLeavesRecords);
            fragment.setArguments(bundle);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.frame_container,fragment);
            fragmentTransaction.commit();
    }

    // for approve swapping
    public void ApproveSwipeRegularization(String res) {

            String empSwipeRecords = res;
            // String empSwipeRecords = "AP-14,,Suvarna,, Ghoman,,12-5-2015,,Monday,,4.30,,Voting_AP-15,,Atharva,, Ghoman,,15-5-2015,,Wednesday,,5.30,,Voting no";
            ApproveSwipeFragment fragment = new ApproveSwipeFragment();
            Bundle bundle = new Bundle();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            bundle.putString("AnswerApproveLeave", empSwipeRecords);
            fragment.setArguments(bundle);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.frame_container,fragment);
            fragmentTransaction.commit();

    }
    // for approve Timesheet
    public void ApproveTimeSheet(View v)
    {
        Toast.makeText(getActivity(), "Approve Timesheet  not Implemented Yet", Toast.LENGTH_SHORT).show();
       /* ApproveTimeSheetAsyncTakGet obj = new ApproveTimeSheetAsyncTakGet(empId, getApplicationContext());
        try {
            result = obj.execute().get();
            String empLeaveDetails = result.toString();*/

       /* String empTimeSheetRecords = "AP-14,,Suvarna,,Ghoman,,12-5-2015,,44.30_AP-15,,Atharva,,Ghoman,,15-5-2015,,45.30";

        Intent iApproveTimeSheet = new Intent(getApplicationContext(),ApproveTimeSheetActivity.class);
        iApproveTimeSheet.putExtra("AnswerApproveTimeSheet",empTimeSheetRecords);
        startActivity(iApproveTimeSheet);*/

        /*}
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
    }

    // for apply leave
    public void ApplyLeave(String res) {
        // Get current leave balance

        String empLeaveDetails = res;
        ApplyLeaveFragment fragment = new ApplyLeaveFragment();
        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        bundle.putString("AnswerLeaveDetails", empLeaveDetails);
        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame_container,fragment);
        fragmentTransaction.commit();


    }

    // for Attendance details month wise
    public void Attendance(String res) {

        String empAttendanceDetails = res;
        AttendanceFragment fragment = new AttendanceFragment();
        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        bundle.putString("AnswerAttendance", empAttendanceDetails);
        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame_container,fragment);
        fragmentTransaction.commit();

    }

    // for swipe regularization
    public void SwipeRegularization(String res)
    {

        String empSwipeDetails = res;
        SwipeApplyFragment fragment = new SwipeApplyFragment();
        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        bundle.putString("AnswerSwipeRegularization",empSwipeDetails);
        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();

    }

    // for leave balance
    public void LeaveBalance(String res)
    {

        String empLeaveDetails = res;
        BalanceLeaveDetailsFragment fragment = new BalanceLeaveDetailsFragment();
        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        bundle.putString("AnswerLeaveDetails",empLeaveDetails);
        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame_container,fragment);
        fragmentTransaction.commit();

    }

    // for Leaves taken Record
    public void LeaveRecords(String res)
    {
        String empLeavesRecords = res;
        LeaveTakenDetailsFragment fragment = new LeaveTakenDetailsFragment();
        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        bundle.putString("AnswerLeavesRecords", empLeavesRecords);
        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();

    }

    // for TimeSheetListing listing
    public void TimeSheetListing(View v)
    {
        Toast.makeText(getActivity(), "Timesheet Listing not Implemented Yet", Toast.LENGTH_SHORT).show();
                  /* Calendar c = Calendar.getInstance();
                   String month = String.valueOf(c.get(Calendar.MONTH) + 1);
                    String year=String.valueOf(c.get(Calendar.YEAR) + 1);
                   TimeSheetListingAsyncTaskGet obj = new TimeSheetListingAsyncTaskGet(empId, month,year, EmployeeActivity.this);
                    try{
                        result= obj.execute().get();
                        String empTimeSheetListing =result.toString();

                        Intent iTimeSheetListing = new Intent(getApplicationContext(),TimeSheetListingActivity.class);
                        iTimeSheetListing.putExtra("AnswerTimeSheetListing", empTimeSheetListing);
                        startActivity(iTimeSheetListing);
                     }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }*/

    }

    // For timesheet entry
    public void TimeSheetEntry(View v)
    {

        Toast.makeText(getActivity(),"Timesheet Entry not Implemented Yet",Toast.LENGTH_SHORT).show();
                    /*//need to get projects and activity list
                    String timeSheetProAct ="Project1,,Project2,,Project3_Activity1,,Activity2,,Activity3,,Activity4";
                     Intent iActivityEntry = new Intent(getApplicationContext(),TimeSheetEntryActivity.class);
                    iActivityEntry.putExtra("AnswerTimeSheetProAct", timeSheetProAct);
                    startActivity(iActivityEntry);*/
    }

    // for Holiday List
    public void HolidayList(String res)
    {

        String holidayList =res;
        HolidayFragment fragment = new HolidayFragment();
        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        bundle.putString("AnswerHolidayList", holidayList);
        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();

    }

    //fOR Survey
    public void SurveyList(String res)
    {
        String empSurveyNames = res;
        SurveyNamesFragment fragment = new SurveyNamesFragment();
        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        bundle.putString("AnswerSurvey", empSurveyNames);
        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame_container,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onClick(View v) {
        Calendar c = Calendar.getInstance();
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        String year1 =String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        switch (v.getId()) {
            case R.id.txtApplyLeave:
                ApplyLeaveAsyncTaskGet objApply = new ApplyLeaveAsyncTaskGet(empId, getActivity(),ManagerFragment.this,1);
                objApply.execute();
                break;
            case R.id.txtLeaveBalance:
                // LeaveBalance(v);
                ApplyLeaveAsyncTaskGet objLvbal = new ApplyLeaveAsyncTaskGet(empId,getActivity(),ManagerFragment.this,2);
                objLvbal.execute();
                break;
            case R.id.txtAttendance:
                int year=(c.get(Calendar.YEAR));
                AttendanceAsyncTaskGet objattendance = new AttendanceAsyncTaskGet(empId, month,String.valueOf(year),getActivity(),ManagerFragment.this);
                objattendance.execute();
                break;
            case R.id.txtSwipeRegularization:
                SwipeApplyAsyncTaskGet objswipeapply = new SwipeApplyAsyncTaskGet(empId, month, getActivity(),ManagerFragment.this);
                objswipeapply.execute();
                break;
            case R.id.txtLeaveRecords:
                LeaveTakenDetailsAsyncTaskGet objleaverecord = new LeaveTakenDetailsAsyncTaskGet(empId,year1 , getActivity(),ManagerFragment.this );
                objleaverecord.execute();
                break;
            case R.id.txtTimeSheetListing:
                TimeSheetListing(v);
                break;
            case R.id.txtTimeSheetEntry:
                TimeSheetEntry(v);
                break;
            case R.id.txtHolidayList:
                HolidayListAsyncTaskGet objholiday = new HolidayListAsyncTaskGet(UserGlobalData.getInstance().geCompanyLocation(),UserGlobalData.getInstance().getCompanyCode(),year1, getActivity(),ManagerFragment.this);
                objholiday.execute();
                break;
            case R.id.txtSurvey:
                SurveyNamesAsyncTask objsurvey = new SurveyNamesAsyncTask(empId, getActivity(),ManagerFragment.this);
                objsurvey.execute();
                break;
            case R.id.txtApproveLeave:
                ApproveLeaveAsyncTaskGet objapprovelv = new ApproveLeaveAsyncTaskGet(empId,getActivity(),ManagerFragment.this);
                objapprovelv.execute();
                break;
            case R.id.txtApproveSwipe:
                ApproveSwipeRegularizationAsyncTakGet objapproveswipe = new ApproveSwipeRegularizationAsyncTakGet(empId, getActivity(),ManagerFragment.this);
                objapproveswipe.execute();
                break;
            case R.id.txtApproveTimeSheet:
                ApproveTimeSheet(v);
                break;

        }



    }

    @Override
    public void onTaskComplete(String result,int pos) {

        switch (pos)
        {
            case 1:
                ApplyLeave(result);
                break;
            case 2:
                LeaveBalance(result);
                break;
            case 3:
                LeaveRecords(result);
                break;
            case 4:
                Attendance(result);
                break;
            case 5:
                SwipeRegularization(result);
                break;
            case 8:
                HolidayList(result);
                break;
            case 9:
                SurveyList(result);
                break;
            case 10:
                ApproveLeave(result);
                break;
            case 11:
                ApproveSwipeRegularization(result);
                break;

        }

    }
}
