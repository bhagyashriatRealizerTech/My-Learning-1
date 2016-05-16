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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ApproveTimeSheetActivity extends Activity {
Intent  intent;
String reasonForRejection="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approve_time_sheet_layout);

        TextView empUserName =(TextView)findViewById(R.id.txtUserName);
        empUserName.setText("Hi " + UserGlobalData.getInstance().getEmpFirstName() + ",");

        ArrayList<ApproveTimeSheetModel> timeSheets = GetTimeSheetsForAppoval();

        final ListView lstTimeListView = (ListView)findViewById(R.id.lstApproveTimeSheet);
        lstTimeListView.setAdapter(new ApproveTimeSheetAdapter(this, timeSheets));

        // Call to show details of record on click;
        lstTimeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lstTimeListView.getItemAtPosition(position);
                ApproveTimeSheetModel obj = (ApproveTimeSheetModel)o;
               /* Toast.makeText(getApplicationContext(), "You start date :12122  " + " " +
                        obj.getstartDate(), Toast.LENGTH_LONG).show();*/

                // call to wcf service to get details of leave taken
                String startDate = obj.getApproveTimeSheetWeek();
                String endDate= obj.getApproveTimeSheetTotalHrs();
                String empId = obj.getEmpId();

                /*LeaveTakenDetailsAsyncTaskPost objLeaveRecordDetails = new LeaveTakenDetailsAsyncTaskPost(empId,startDate,endDate, getApplicationContext());

                try
                {
                    result = objLeaveRecordDetails.execute().get();
                    if(result.equals(""))
                        Toast.makeText(getApplicationContext(), "Server not responding try again",Toast.LENGTH_LONG).show();
                    else
                    String leaveRecord = result.toString();*/

                String timeSheetRecord ="AP-14,,Suvarna,,Ghoman_31-08-2015,,8.30,,Achilles,,Development1,," +
                                        "01-09-2015,,8.45,,Android,,Development2,," +
                                        "02-09-2015,,8.50,,Achilles,,Development3,," +
                                        "03-09-2015,,8.20,,Achilles,,Development4,,"+
                                        "04-09-2015,,9.00,,Android,,Development5";
                Intent iTimeSheetRecordDetails = new Intent(getApplicationContext(),ApproveTimeSheetSingleRecordActivity.class);
                iTimeSheetRecordDetails.putExtra("AnswerTimeSheetRecordDetails",timeSheetRecord );
                startActivity(iTimeSheetRecordDetails);

               /* } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
*/

            }
        });
    }


    public  ArrayList<ApproveTimeSheetModel> GetTimeSheetsForAppoval()
    {
        ArrayList<ApproveTimeSheetModel> aTimeSheets = new ArrayList<>();
        intent = getIntent();
        String [] timeSheetList = intent.getStringExtra("AnswerApproveTimeSheet").toString().split("_");

        for(String timeSheet : timeSheetList)
        {
            String [] timeSheetDetails = timeSheet.split(",,");
            ApproveTimeSheetModel timeSheetModel = new ApproveTimeSheetModel();
            timeSheetModel.setEmpId(timeSheetDetails[0]);
            timeSheetModel.setEmpApproveTimeSheetFirstName(timeSheetDetails[1]);
            timeSheetModel.setEmpApproveTimeSheetLastName(timeSheetDetails[2]);
            timeSheetModel.setApproveTimeSheetWeek(timeSheetDetails[3]);
            timeSheetModel.setApproveTimeSheetTotalHrs(timeSheetDetails[4]);
            aTimeSheets.add(timeSheetModel);
        }
        return aTimeSheets;
    }

    public void ApproveTimeSheet(String empId, String weekDate, String totalHrs)
    {
        /* ApproveTimeSheetAsyncTaskGet obj = new ApproveTimeSheetAsyncTaskGet(empId, weekDate, totalHrs, getApplicationContext());
        try
        {
               StringBuilder result = obj.execute().get();
               String timeSheetApproved = result.toString();
            // output as true or false
            if(timeSheetApproved.equalsIgnoreCase("true")) {*/
        Toast.makeText(getApplicationContext(), "TimeSheet approved", Toast.LENGTH_LONG).show();
           /* } else
                Toast.makeText(getApplicationContext(), "Reason not submitted. try again", Toast.LENGTH_LONG).show();
         }
          catch (InterruptedException e) {
                e.printStackTrace();
         } catch (ExecutionException e) {
                e.printStackTrace();
         }*/

    }

    public void RejectTimeSheet(final String empIdForTimeSheet, final String weekDate, final String totalHrs)
    {
        //Show popup
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ApproveTimeSheetActivity.this);
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
                            ApproveTimeSheetRejectedAsyncTaskPost obj = new ApproveTimeSheetRejectedAsyncTaskPost(empIdForTimeSheet, weekDate, totalHrs, reasonForRejection, getApplicationContext());
                      /*  try {
                            StringBuilder result = obj.execute().get();
                            String isSaved = result.toString();

                            // output as true or false
                            if(isSaved.equalsIgnoreCase("true")){*/
                            Toast.makeText(getApplicationContext(), "TimeSheet rejected", Toast.LENGTH_LONG).show();

                           /*} else
                                Toast.makeText(getApplicationContext(), "Reason not submitted. try again", Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }*/
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_approve_time_sheet, menu);
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
