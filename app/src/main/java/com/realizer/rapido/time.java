package com.realizer.rapido;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class time extends ActionBarActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.timesheet_listing_layout);

            intent = getIntent();

            ArrayList<TimeSheetListingModel> timeSheetListingModels = GetTimeSheetListing();

            final ListView lstTimeSheetListing = (ListView)findViewById(R.id.lstTimeSheetListing);
            lstTimeSheetListing.setAdapter(new TimeSheetListingAdapter(this, timeSheetListingModels));
        }

        public ArrayList<TimeSheetListingModel> GetTimeSheetListing()
        {
            ArrayList<TimeSheetListingModel> timesheetListing = new ArrayList<TimeSheetListingModel>();
            String [] lstTListing = intent.getStringExtra("AnswerTimesheetListing").split("_");

            for( String tLising : lstTListing) {
                String [] tListingDetails = tLising.split(",,");
                TimeSheetListingModel tListModel = new TimeSheetListingModel();
                tListModel.settimesheetStartDate(tListingDetails[0]);
                tListModel.settotalHours(tListingDetails[1]);
                tListModel.settimesheetStatus(tListingDetails[2]);
                timesheetListing.add(tListModel);
            }

            return timesheetListing;

        }
}
