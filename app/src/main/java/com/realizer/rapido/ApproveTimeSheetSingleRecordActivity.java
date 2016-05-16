package com.realizer.rapido;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ApproveTimeSheetSingleRecordActivity extends ActionBarActivity {
   TextView empUserName,approveTimeSheetFirstName , approveTimeSheetLastName ,approveTimeSheetMonday, totalHrsMonday,
            timeSheetMondayProject, timeSheetMondayActivity, 		approveTimeSheetTuesday, totalHrsTuesday ,
            timeSheetTuesdayProject, timeSheetTuesdayActivity, approveTimeSheetWednesday, totalHrsWednesday,
            timeSheetWednesdayProject, timeSheetWednesdayActivity, approveTimeSheetThursday, totalHrsThursday ,
            timeSheetThursdayProject,timeSheetThursdayActivity,  approveTimeSheetFriday, totalHrsFriday,
            timeSheetFridayProject, timeSheetFridayActivity;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approve_time_sheet_single_record_layout);

        getControls();

        setControls();
    }

    public  void getControls()
    {
        empUserName =(TextView)findViewById(R.id.txtUserName);
        approveTimeSheetFirstName =(TextView)findViewById(R.id.txtApproveTimeSheetFirstName);
        approveTimeSheetLastName=(TextView)findViewById(R.id.txtApproveTimeSheetLastName);

        approveTimeSheetMonday=(TextView)findViewById(R.id.txtApproveTimeSheetMonday);
        totalHrsMonday=(TextView)findViewById(R.id.txtTotalHrsMonday);
        timeSheetMondayProject=(TextView)findViewById(R.id.txtTimeSheetMondayProject);
        timeSheetMondayActivity=(TextView)findViewById(R.id.txtTimeSheetMondayActivity);

        approveTimeSheetTuesday =(TextView)findViewById(R.id.txtApproveTimeSheetTuesday);
        totalHrsTuesday=(TextView)findViewById(R.id.txtTotalHrsTuesday);
        timeSheetTuesdayProject=(TextView)findViewById(R.id.txtTimeSheetTuesdayProject);
        timeSheetTuesdayActivity=(TextView)findViewById(R.id.txtTimeSheetTuesdayActivity);
        approveTimeSheetWednesday=(TextView)findViewById(R.id.txtApproveTimeSheetWednesday);
        totalHrsWednesday=(TextView)findViewById(R.id.txtTotalHrsWednesday);
        timeSheetWednesdayProject=(TextView)findViewById(R.id.txtTimeSheetWednesdayProject);
        timeSheetWednesdayActivity=(TextView)findViewById(R.id.txtTimeSheetWednesdayActivity);
        approveTimeSheetThursday =(TextView)findViewById(R.id.txtApproveTimeSheetThursday);
        totalHrsThursday =(TextView)findViewById(R.id.txtTotalHrsThursday);
        timeSheetThursdayProject=(TextView)findViewById(R.id.txtTimeSheetThursdayProject);
        timeSheetThursdayActivity=(TextView)findViewById(R.id.txtTimeSheetThursdayActivity);
        approveTimeSheetFriday =(TextView)findViewById(R.id.txtApproveTimeSheetFriday);
        totalHrsFriday=(TextView)findViewById(R.id.txtTotalHrsFriday);
        timeSheetFridayProject =(TextView)findViewById(R.id.txtTimeSheetFridayProject);
        timeSheetFridayActivity=(TextView)findViewById(R.id.txtTimeSheetFridayActivity);
    }

    public void setControls()
    {
        // set logged user name
        empUserName.setText("Hi "+UserGlobalData.getInstance().getEmpFirstName()+",");

        intent = getIntent();
        String [] timeSheetWithEmp = intent.getStringExtra("AnswerTimeSheetRecordDetails").toString().split("_");


        // setting emp FirstName & LastName for TimeSheet approve
        String [] empDetails = timeSheetWithEmp[0].split(",,");
        approveTimeSheetFirstName.setText(empDetails[1]);
        approveTimeSheetLastName.setText(empDetails[2]);


       // setting time sheet details

        String [] timeSheetDetails =timeSheetWithEmp[1].split(",,");

        approveTimeSheetMonday.setText(timeSheetDetails[0]);
        totalHrsMonday.setText(timeSheetDetails[1]);
        timeSheetMondayProject.setText(timeSheetDetails[2]);
        timeSheetMondayActivity.setText(timeSheetDetails[3]);
        approveTimeSheetTuesday.setText(timeSheetDetails[4]);
        totalHrsTuesday.setText(timeSheetDetails[5]);
        timeSheetTuesdayProject.setText(timeSheetDetails[6]);
        timeSheetTuesdayActivity.setText(timeSheetDetails[7]);
        approveTimeSheetWednesday.setText(timeSheetDetails[8]);
        totalHrsWednesday.setText(timeSheetDetails[9]);
        timeSheetWednesdayProject.setText(timeSheetDetails[10]);
        timeSheetWednesdayActivity.setText(timeSheetDetails[11]);
        approveTimeSheetThursday.setText(timeSheetDetails[12]);
        totalHrsThursday.setText(timeSheetDetails[13]);
        timeSheetThursdayProject.setText(timeSheetDetails[14]);
        timeSheetThursdayActivity.setText(timeSheetDetails[15]);
        approveTimeSheetFriday.setText(timeSheetDetails[16]);
        totalHrsFriday.setText(timeSheetDetails[17]);
        timeSheetFridayProject.setText(timeSheetDetails[18]);
        timeSheetFridayActivity.setText(timeSheetDetails[19]);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_approve_time_sheet_single_record, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
