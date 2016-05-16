package com.realizer.rapido;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class TimeSheetDisplayActivity extends Activity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_sheet_display_layout);

        intent=getIntent();
        ArrayList<TimeSheetDisplayModel> timesheetDisplay = GetTimeSheetDisplayDetails();

        final ListView listViewTimesheetDisplay = (ListView) findViewById(R.id.lstTimesheetDisplay);
        listViewTimesheetDisplay.setAdapter(new TimeSheetDisplayAdapter(this, timesheetDisplay));

    }

    public ArrayList<TimeSheetDisplayModel> GetTimeSheetDisplayDetails()
    {
        ArrayList<TimeSheetDisplayModel> result = new ArrayList<TimeSheetDisplayModel>();
        String sample = intent.getStringExtra("AnswerTimesheetRecords");
        String [] empTimesheetDisplayDetails =  sample.split(",,");

        for(int i =0;i<empTimesheetDisplayDetails.length-1;i++)
        {
            TimeSheetDisplayModel timesheetDisplay = new TimeSheetDisplayModel();
            timesheetDisplay.settimesheetStartDate(empTimesheetDisplayDetails[i]);
            timesheetDisplay.settimesheetEndDate(empTimesheetDisplayDetails[++i]);
            timesheetDisplay.settimesheetProjectName(empTimesheetDisplayDetails[++i]);
            timesheetDisplay.settimesheetStatus(empTimesheetDisplayDetails[++i]);
            timesheetDisplay.settotalHours(empTimesheetDisplayDetails[++i]);
            result.add(timesheetDisplay);
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_sheet_display, menu);
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
