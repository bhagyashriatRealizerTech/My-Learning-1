package com.realizer.rapido;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by Win on 10/21/2015.
 */
public class MainActivityManager extends Activity implements AsyncTaskCompleteListener<String>{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Fragment fragment;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    String empId;

    StringBuilder result;

    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        empId = UserGlobalData.getInstance().getEmpId();

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_mngitems);

        // nav drawer icons from resources

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();
        // nav drawer icons from resources
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_mgricons);

        // adding nav drawer items to array

        //navDrawerItems
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6],navMenuIcons.getResourceId(6, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7],navMenuIcons.getResourceId(7, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[8],navMenuIcons.getResourceId(8, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[9],navMenuIcons.getResourceId(9, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], navMenuIcons.getResourceId(10, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[11],navMenuIcons.getResourceId(11, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[12],navMenuIcons.getResourceId(12, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[13],navMenuIcons.getResourceId(13, -1)));




        // Recycle the typed array

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),	navDrawerItems);

        mDrawerList.setAdapter(adapter);


        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.rapido, // nav drawer open - description for accessibility
                R.string.rapido // nav drawer close - description for accessibility
        ) {

            public void onDrawerClosed(View view)
            {
                getActionBar().setTitle("Rapido ESS\t\t\t\t\t\t\tHi,"+UserGlobalData.getInstance().getEmpFirstName());
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView)
            {
                getActionBar().setTitle("Rapido ESS\t\t\t\t\t\t\tHi,"+UserGlobalData.getInstance().getEmpFirstName());
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null)
        {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    @Override
    public void onTaskComplete(String result,int pos) {

    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements	ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);

        return super.onPrepareOptionsMenu(menu);
    }


    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position)
    {
        // update the main content by replacing fragments
        Fragment fragment = null;

        switch (position)
        {
            case 0:
                fragment = new ManagerFragment();
                break;
            case 1:
                fragment = ApproveLeave();
                break;
            case 2:
                fragment = ApproveSwipeRegularization();
                break;
            case 3:
                ApproveTimeSheet();
                break;

            case 4:
                fragment = ApplyLeave();
                break;
            case 5:
                fragment = LeaveBalance();
                break;
            case 6:
                fragment = LeaveRecords();
                break;
            case 7:
                fragment = Attendance();
                break;
            case 8:
                fragment = SwipeRegularization();
                break;
            case 9:
                TimeSheetEntry();
                break;
            case 10:
                TimeSheetListing();
                break;
            case 11:
                fragment = HolidayList();
                break;

            case 12:
                fragment = SurveyList();
                break;

            case 13:
                System.exit(0);
                break;

            default:
                break;
        }

        if (fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        }
        else
        {
            // error in creating fragment
            //Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title)
    {
        mTitle = title;
        getActionBar().setTitle("Rapido ESS\t\t\t\t\t\t\tHi,"+UserGlobalData.getInstance().getEmpFirstName());
    }


    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    // For approve leave
    public Fragment ApproveLeave() {
        ApproveLeaveAsyncTaskGet obj = new ApproveLeaveAsyncTaskGet(empId,MainActivityManager.this,this);
        try {
            result = obj.execute().get();
            String empLeavesRecords = result.toString();

            // Get Output as
            // String empLeavesRecords = "AP-14,,Suvarna,, Ghoman,,12-5-2015,,17-5-2015_AP-16,,Atharva,, Ghoman,,20-5-2015,,22-5-2015";


            fragment = new ApproveLeaveFragment();
            Bundle bundle = new Bundle();
            bundle.putString("AnswerApproveLeave",empLeavesRecords);
            fragment.setArguments(bundle);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    // for approve swapping
    public Fragment ApproveSwipeRegularization() {
        ApproveSwipeRegularizationAsyncTakGet obj = new ApproveSwipeRegularizationAsyncTakGet(empId, MainActivityManager.this,this);
        try {
            result = obj.execute().get();
            String empSwipeRecords = result.toString();
            // String empSwipeRecords = "AP-14,,Suvarna,, Ghoman,,12-5-2015,,Monday,,4.30,,Voting_AP-15,,Atharva,, Ghoman,,15-5-2015,,Wednesday,,5.30,,Voting no";

            fragment = new ApproveSwipeFragment();
            Bundle bundle = new Bundle();
            bundle.putString("AnswerApproveLeave", empSwipeRecords);
            fragment.setArguments(bundle);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return fragment;

    }


    // for approve Timesheet
    public void ApproveTimeSheet()
    {
        Toast.makeText(MainActivityManager.this, "Approve Timesheet  not Implemented Yet", Toast.LENGTH_SHORT).show();
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

    //For Apply Leave
    public Fragment ApplyLeave() {
        // Get current leave balance
        ApplyLeaveAsyncTaskGet obj = new ApplyLeaveAsyncTaskGet(empId, MainActivityManager.this,this,1);
        try {
            result = obj.execute().get();
            String empLeaveDetails = result.toString();
            fragment = new ApplyLeaveFragment();
            Bundle bundle = new Bundle();
            bundle.putString("AnswerLeaveDetails", empLeaveDetails);
            fragment.setArguments(bundle);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    // for Attendance details month wise
    public Fragment Attendance() {
        Calendar c = Calendar.getInstance();
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        // String month = "7";
        int year=(c.get(Calendar.YEAR));
        AttendanceAsyncTaskGet obj = new AttendanceAsyncTaskGet(empId, month,String.valueOf(year),MainActivityManager.this,this);
        try {
            result = obj.execute().get();
            String empAttendanceDetails = result.toString();
            fragment = new AttendanceFragment();
            Bundle bundle = new Bundle();
            bundle.putString("AnswerAttendance", empAttendanceDetails);
            fragment.setArguments(bundle);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    // for leave balance
    public Fragment LeaveBalance()
    {
        ApplyLeaveAsyncTaskGet obj = new ApplyLeaveAsyncTaskGet(empId,MainActivityManager.this,this,2);
        try {
            result = obj.execute().get();
            String empLeaveDetails = result.toString();
            fragment = new BalanceLeaveDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("AnswerLeaveDetails",empLeaveDetails);
            fragment.setArguments(bundle);

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return fragment;
    }
    // for Leaves taken Record
    public Fragment LeaveRecords()
    {
        String year =String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        LeaveTakenDetailsAsyncTaskGet obj = new LeaveTakenDetailsAsyncTaskGet(empId,year , MainActivityManager.this,this );
        try
        {
            result = obj.execute().get();
            String empLeavesRecords = result.toString();
            fragment = new LeaveTakenDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("AnswerLeavesRecords", empLeavesRecords);
            fragment.setArguments(bundle);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return fragment;
    }
    // for swipe regularization
    public Fragment SwipeRegularization()
    {
        Calendar c = Calendar.getInstance();
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        SwipeApplyAsyncTaskGet obj = new SwipeApplyAsyncTaskGet(empId, month, MainActivityManager.this,this);
        try {
            result = obj.execute().get();
            String empSwipeDetails = result.toString();
            fragment = new SwipeApplyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("AnswerSwipeRegularization",empSwipeDetails);
            fragment.setArguments(bundle);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return fragment;
    }
    // for Holiday List
    public Fragment HolidayList()
    {
        String year =String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        HolidayListAsyncTaskGet obj = new HolidayListAsyncTaskGet(UserGlobalData.getInstance().geCompanyLocation(),UserGlobalData.getInstance().getCompanyCode(),year, MainActivityManager.this,this);
        try{
            result= obj.execute().get();
            String holidayList =result.toString();
            fragment = new HolidayFragment();
            Bundle bundle = new Bundle();
            bundle.putString("AnswerHolidayList", holidayList);
            fragment.setArguments(bundle);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return fragment;
    }
    //fOR Survey
    public Fragment SurveyList()
    {

        SurveyNamesAsyncTask obj = new SurveyNamesAsyncTask(empId, MainActivityManager.this,this);
        try {
            result = obj.execute().get();
            String empSurveyNames = result.toString();

            fragment = new SurveyNamesFragment();
            Bundle bundle = new Bundle();
            bundle.putString("AnswerSurvey", empSurveyNames);
            fragment.setArguments(bundle);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return fragment;

    }

    // for TimeSheetListing listing
    public void TimeSheetListing()
    {
        Toast.makeText(MainActivityManager.this, "Approve Timesheet  not Implemented Yet", Toast.LENGTH_SHORT).show();
                     /* Calendar c = Calendar.getInstance();
                   String month = String.valueOf(c.get(Calendar.MONTH) + 1);
                    String year=String.valueOf(c.get(Calendar.YEAR) + 1);
                   TimeSheetListingAsyncTaskGet obj = new TimeSheetListingAsyncTaskGet(empId, month,year, ManagerActivity.this);
                    try{
                        result= obj.execute().get();
                        String empTimeSheetListing =result.toString();

                        Intent iTimeSheetListing = new Intent(ManagerActivity.this,TimeSheetListingActivity.class);
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
    public void TimeSheetEntry()
    {
        Toast.makeText(MainActivityManager.this, "Approve Timesheet  not Implemented Yet", Toast.LENGTH_SHORT).show();

        // Get output as
        /*String empTimesheetRecords ="1/6/2015,,7/6/2014,,45,,Android Chat1,,Approved,," +
                "8/6/2014,,14/6/2014,,44,,Android Chat2,,Approved,,\"" +
                "15/6/2014,,21/6/2014,,43,,Android Chat3,,Approved,,\"" +
                "22/6/2014,,28/6/2014,,42,,Android Chat4,,Approved,,\"" +
                "29/6/2014,,30/6/2014,,41,,Android Chat5,,Approved,,\"" +
                "1/7/2014,,4/7/2014,,40,,Android Chat6,,Pending,,\"" +
                "6/7/2014,,12/7/2014,,40,,Android Chat7,,Pending\"";
        Intent iBalLeave = new Intent(getApplicationContext(),TimeSheetDisplayActivity.class);
        iBalLeave.putExtra("AnswerTimesheetRecords", empTimesheetRecords);
        startActivity(iBalLeave);*/
    }
}
