package com.realizer.rapido;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class TimeSheetWeeklyDetailsActivity extends Activity {
    String [] projects, activities;
    TextView approveTimeSheetMonday, totalHrsMonday, timeSheetMondayProject, timeSheetMondayActivity,
            approveTimeSheetTuesday, totalHrsTuesday, timeSheetTuesdayProject, timeSheetTuesdayActivity,
            approveTimeSheetWednesday, totalHrsWednesday, timeSheetWednesdayProject, timeSheetWednesdayActivity,
            approveTimeSheetThursday, totalHrsThursday, timeSheetThursdayProject, timeSheetThursdayActivity,
            approveTimeSheetFriday, totalHrsFriday, timeSheetFridayProject, timeSheetFridayActivity;
    Button editTimeSheetMonday, editTimeSheetTuesday, editTimeSheetWednesday, editTimeSheetThursday, editTimeSheetFriday;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_sheet_weekly_details_layout);

        intent=getIntent();

        getControls();
        String [] weekDetailsWithProAct = intent.getStringExtra("AnswerTimeSheetWeek").split("_");
        String [] weekDetails = weekDetailsWithProAct[0].split(",,");
        projects =weekDetailsWithProAct[1].toString().split(",,");
        activities = weekDetailsWithProAct[2].toString().split(",,");
        setControls(weekDetails);

    }


    public void getControls()
    {
        approveTimeSheetMonday=(TextView)findViewById(R.id.txtApproveTimeSheetMonday);
        totalHrsMonday=(TextView)findViewById(R.id.txtTotalHrsMonday);
        timeSheetMondayProject=(TextView)findViewById(R.id.txtTimeSheetMondayProject);
        timeSheetMondayActivity=(TextView)findViewById(R.id.txtTimeSheetMondayActivity);
        approveTimeSheetTuesday=(TextView)findViewById(R.id.txtApproveTimeSheetTuesday);
        totalHrsTuesday=(TextView)findViewById(R.id.txtTotalHrsTuesday);
        timeSheetTuesdayProject=(TextView)findViewById(R.id.txtTimeSheetTuesdayProject);
        timeSheetTuesdayActivity=(TextView)findViewById(R.id.txtTimeSheetTuesdayActivity);
        approveTimeSheetWednesday =(TextView)findViewById(R.id.txtApproveTimeSheetWednesday);
        totalHrsWednesday =(TextView)findViewById(R.id.txtTotalHrsWednesday);
        timeSheetWednesdayProject=(TextView)findViewById(R.id.txtTimeSheetWednesdayProject);
        timeSheetWednesdayActivity=(TextView)findViewById(R.id.txtTimeSheetWednesdayActivity);
        approveTimeSheetThursday =(TextView)findViewById(R.id.txtApproveTimeSheetThursday);
        totalHrsThursday=(TextView)findViewById(R.id.txtTotalHrsThursday);
        timeSheetThursdayProject =(TextView)findViewById(R.id.txtTimeSheetThursdayProject);
        timeSheetThursdayActivity=(TextView)findViewById(R.id.txtTimeSheetThursdayActivity);
        approveTimeSheetFriday =(TextView)findViewById(R.id.txtApproveTimeSheetFriday);
        totalHrsFriday=(TextView)findViewById(R.id.txtTotalHrsFriday);
        timeSheetFridayProject=(TextView)findViewById(R.id.txtTimeSheetFridayProject);
        timeSheetFridayActivity =(TextView)findViewById(R.id.txtTimeSheetFridayActivity);


        editTimeSheetMonday =(Button)findViewById(R.id.btnEditTimeSheetMonday);
        editTimeSheetTuesday=(Button)findViewById(R.id.btnEditTimeSheetTuesday);
        editTimeSheetWednesday=(Button)findViewById(R.id.btnEditTimeSheetWednesday);
        editTimeSheetThursday=(Button)findViewById(R.id.btnEditTimeSheetThursday);
        editTimeSheetFriday=(Button)findViewById(R.id.btnEditTimeSheetFriday);

    }

    public void setControls(String [] weekDetails)
    {
        approveTimeSheetMonday.setText(weekDetails[0]);
        totalHrsMonday.setText(weekDetails[1]);
        timeSheetMondayProject.setText(weekDetails[2]);
        timeSheetMondayActivity.setText(weekDetails[3]);
        approveTimeSheetTuesday.setText(weekDetails[4]);
        totalHrsTuesday.setText(weekDetails[5]);
        timeSheetTuesdayProject.setText(weekDetails[6]);
        timeSheetTuesdayActivity.setText(weekDetails[7]);
        approveTimeSheetWednesday.setText(weekDetails[8]);
        totalHrsWednesday.setText(weekDetails[9]);
        timeSheetWednesdayProject.setText(weekDetails[10]);
        timeSheetWednesdayActivity.setText(weekDetails[11]);
        approveTimeSheetThursday.setText(weekDetails[12]);
        totalHrsThursday.setText(weekDetails[13]);
        timeSheetThursdayProject.setText(weekDetails[14]);
        timeSheetThursdayActivity.setText(weekDetails[15]);
        approveTimeSheetFriday.setText(weekDetails[16]);
        totalHrsFriday.setText(weekDetails[17]);
        timeSheetFridayProject.setText(weekDetails[18]);
        timeSheetFridayActivity.setText(weekDetails[19]);

        if(weekDetails[20].equalsIgnoreCase("Submitted"))
        {
            // show all edit buttons per day
            editTimeSheetMonday.setVisibility(View.VISIBLE);
            editTimeSheetTuesday.setVisibility(View.VISIBLE);
            editTimeSheetWednesday.setVisibility(View.VISIBLE);
            editTimeSheetThursday.setVisibility(View.VISIBLE);
            editTimeSheetFriday.setVisibility(View.VISIBLE);
        }
    }

    public void ShowPopup(final View v)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TimeSheetWeeklyDetailsActivity.this);
        // Set up the input

        LinearLayout linearLayout= new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText totalHrs = new EditText(this);
        final Spinner project = new Spinner(this);
        final Spinner activity = new Spinner(this);

        ArrayAdapter<String> adpProjects = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,projects);
        project.setAdapter(adpProjects);

        ArrayAdapter<String> adpActivities = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, activities);
        activity.setAdapter(adpActivities);
        linearLayout.addView(totalHrs);
        linearLayout.addView(project);
        linearLayout.addView(activity);
        totalHrs.setHint("Enter total hrs");


        // Specify the type of input expected;
        totalHrs.setInputType(InputType.TYPE_CLASS_TEXT );
        alertDialogBuilder.setView(linearLayout);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String dateToEdit = "01-01-2015";
                        String totalHr = totalHrs.getText().toString();
                        String projectName = project.getSelectedItem().toString();
                        String activityName = activity.getSelectedItem().toString();
                        // call to save reason
                        TimeSheetWeeklyDetailsEditAsyncTaskPost obj = new TimeSheetWeeklyDetailsEditAsyncTaskPost(UserGlobalData.getInstance().getEmpId(), dateToEdit,
                                totalHr, projectName, activityName, TimeSheetWeeklyDetailsActivity.this);
                        try {
                            StringBuilder result = obj.execute().get();
                            String empLeaveDetails = result.toString();

                            // output as true or false
                            if (empLeaveDetails.equalsIgnoreCase("true")) {
                                Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_LONG).show();

                                ((Button) v).setText("Pending");
                                v.setEnabled(false);
                            } else
                                Toast.makeText(getApplicationContext(), "Reason not submitted. try again", Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
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

