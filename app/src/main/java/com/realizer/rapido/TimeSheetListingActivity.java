package com.realizer.rapido;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;


public class TimeSheetListingActivity extends Activity {
    Intent intent;


    String[] months = {"January", "February", "March", "April", "May", "June", "July","August", "September", "October", "November","December"};
    Spinner spTimeSheetListing;
    ImageView sortOrder;
    String[] eTimeSheetListing;
    ArrayList<TimeSheetListingModel> timeSheetListingModels;
    StringBuilder result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timesheet_listing_layout);

        intent = getIntent();
        TextView empUserName = (TextView) findViewById(R.id.txtUserName);
        empUserName.setText("Hi " + UserGlobalData.getInstance().getEmpFirstName() + ",");
        sortOrder =(ImageView)findViewById(R.id.imgOrderTimeSheet);

        spTimeSheetListing = (Spinner) findViewById(R.id.spTimeSheetListing);
        ArrayAdapter<String> monthsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, months);
        spTimeSheetListing.setAdapter(monthsAdapter);

        Calendar c2 = Calendar.getInstance();
        int month = c2.get(Calendar.MONTH);
        spTimeSheetListing.setSelection(month);

        final ListView lstTimeSheetListing = (ListView) findViewById(R.id.lstTimeSheetListing);


        lstTimeSheetListing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lstTimeSheetListing.getItemAtPosition(position);

                TimeSheetListingModel surveyObj = (TimeSheetListingModel)o;
                String empid = UserGlobalData.getInstance().getEmpId();
                String projectcode = surveyObj.getProjectcode();
                String strdate = surveyObj.gettimesheetStartDate();
                String enddate = "7-31-2015";


                TimeSheetDetailsAsyncTaskGet obj = new TimeSheetDetailsAsyncTaskGet(empid,projectcode,strdate,enddate,TimeSheetListingActivity.this);

                try {
                    result = obj.execute().get();
                    String weeklyTime = result.toString();
                    // Get output as*/
                    Intent iTimeSheetWeekly = new Intent(getApplicationContext(), TimeSheetWeeklyDetailsActivity.class);
                    iTimeSheetWeekly.putExtra("AnswerTimeSheetWeek",weeklyTime);
                    startActivity(iTimeSheetWeekly);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });
        final String empId = UserGlobalData.getInstance().getEmpId();

        spTimeSheetListing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                                                     {
                                                         @Override
                                                         public void onItemSelected (AdapterView < ? > parent, View view,int position,
                                                                                     long id){
                                                             String month = String.valueOf(spTimeSheetListing.getSelectedItemPosition()+1);
                                                             final String empId = UserGlobalData.getInstance().getEmpId();
                                                             Calendar c = Calendar.getInstance();
                                                             int year = c.get(Calendar.YEAR);


                                                             TimeSheetListingAsyncTaskGet obj =new TimeSheetListingAsyncTaskGet(empId,month,String.valueOf(year),TimeSheetListingActivity.this);
                                                             try {
                                                                 result = obj.execute().get();
                                                                 String empTimeSheetListing = result.toString();
                                                                 eTimeSheetListing = (empTimeSheetListing.split("_"));
                                                                 String[] tListingDetails = eTimeSheetListing[0].split(",,");
                                                                 if(tListingDetails.length>1) {
                                                                     timeSheetListingModels = GetTimeSheetListing(eTimeSheetListing, "Ascending");
                                                                     lstTimeSheetListing.setAdapter(new TimeSheetListingAdapter(TimeSheetListingActivity.this, timeSheetListingModels));
                                                                     lstTimeSheetListing.deferNotifyDataSetChanged();

                                                                 }
                                                                 else
                                                                 {
                                                                     Toast.makeText(TimeSheetListingActivity.this,"No TimeSheetListing for This Month",Toast.LENGTH_SHORT).show();
                                                                 }


                                                             }
                                                             catch (InterruptedException e) {
                                                                 e.printStackTrace();
                                                             } catch (ExecutionException e) {
                                                                 e.printStackTrace();
                                                             }
                                                         }
                                                         @Override
                                                         public void onNothingSelected (AdapterView < ? > parent){
                                                         }
                                                     }
        );
    }

    public ArrayList<TimeSheetListingModel> GetTimeSheetListing(String []eTimeSheetListing, String order)
    {
        ArrayList<TimeSheetListingModel> timeSheetListing = new ArrayList<TimeSheetListingModel>();

        if(order.equals("Ascending")) {
            for(int i=0;i<eTimeSheetListing.length;i++) {
                String[] tListingDetails = eTimeSheetListing[i].split(",,");

                TimeSheetListingModel tListModel = new TimeSheetListingModel();
                tListModel.settimesheetStartDate(tListingDetails[0].replace("\"",""));
                tListModel.settimesheetEndDate(tListingDetails[1]);
                tListModel.settotalHours(tListingDetails[2]);
                tListModel.settimesheetStatus(tListingDetails[3]);
                tListModel.setProjectcode(tListingDetails[4]);
                timeSheetListing.add(tListModel);
            }
        }
        else
        {
            for(int i=eTimeSheetListing.length-1;i>=0;i--)
            {
                String[] tListingDetails = eTimeSheetListing[i].split(",,");

                TimeSheetListingModel tListModel = new TimeSheetListingModel();
                tListModel.settimesheetStartDate(tListingDetails[0].replace("\"",""));
                tListModel.settimesheetEndDate(tListingDetails[1]);
                tListModel.settotalHours(tListingDetails[2]);
                tListModel.settimesheetStatus(tListingDetails[3]);
                tListModel.setProjectcode(tListingDetails[4]);
                timeSheetListing.add(tListModel);
            }
        }
        return timeSheetListing;
    }

    public void ChangeOrder(View v)
    {
        String backgroundImageName = (String) sortOrder.getTag();

        if(backgroundImageName.equals("Ascending")) {
            timeSheetListingModels = GetTimeSheetListing(eTimeSheetListing, "Descending");
            sortOrder.setImageResource(R.drawable.ascending);
            sortOrder.setTag("Descending");
        }
        else
        {
            timeSheetListingModels = GetTimeSheetListing(eTimeSheetListing, "Ascending");
            sortOrder.setImageResource(R.drawable.ascending);
            sortOrder.setTag("Ascending");
        }
        final ListView listTimeSheetListing= (ListView) findViewById(R.id.lstTimeSheetListing);
        listTimeSheetListing.setAdapter(new TimeSheetListingAdapter(this, timeSheetListingModels));
        listTimeSheetListing.deferNotifyDataSetChanged();
    }
}
