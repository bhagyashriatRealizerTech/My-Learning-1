package com.realizer.rapido;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;


public class TimeSheetEntryActivity extends Activity {
    Intent intent;
    TextView firstDay, lastDay;
    GridView gvTimeSheet;
    String[] projectList,  activityList;
    LinearLayout root, subLayoutForTimeSheet;
    private List<EditText> editTextList = new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timesheet_entry_layout);
        intent = getIntent();
        String[] allListProAct = intent.getStringExtra("AnswerTimeSheetProAct").toString().split("_");
        projectList = allListProAct[0].toString().split(",,");
        activityList = allListProAct[1].toString().split(",,");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        /*gvTimeSheet = (GridView) findViewById(R.id.gvWeekDays);
        gvTimeSheet.setAdapter(new TimeSheetEntryAdapter(TimeSheetEntryActivity.this,  projectList,activityList));*/
        root = (LinearLayout) findViewById(R.id.rootLinearLayout);
        //Create tablelayout for timesheet entry
        CreateTableLayout();



       firstDay = (TextView) findViewById(R.id.txtFirstDay);
       lastDay = (TextView) findViewById(R.id.txtLastDay);

        GetCurrentWeek();
    }


    // to display current week
   public void GetCurrentWeek()
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String[] days = new String[7];
        for (int i = 0; i < 7; i++)
        {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        firstDay.setText(days[0].toString());
        lastDay.setText( days[6].toString());

    }


    // To display next week
    public void DisplayNextWeek(View v) throws ParseException
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = format.parse(lastDay.getText().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertedDate);
        calendar.add(Calendar.DATE, 1);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        String[] days = new String[7];
        for (int i = 0; i < 7; i++)
        {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        firstDay.setText(days[0].toString() );
        lastDay.setText( days[6].toString());

        //ChangeDatesOfGrid();
    }

    // to display previous week
    public void DisplayPreviousWeek(View v) throws ParseException
    {

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = format.parse(firstDay.getText().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertedDate);
        calendar.add(Calendar.DATE, -7);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        String[] days = new String[7];
        for (int i = 0; i < 7; i++)
        {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        firstDay.setText(days[0].toString() );
        lastDay.setText( days[6].toString());

        //  ChangeDatesOfGrid();

    }

    public void CreateTableLayout()
    {
        subLayoutForTimeSheet = (LinearLayout) findViewById(R.id.addTimeSheet);
        root.removeView(subLayoutForTimeSheet);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(FILL_PARENT, WRAP_CONTENT);
        subLayoutForTimeSheet.setLayoutParams(params);
        subLayoutForTimeSheet.setOrientation(LinearLayout.HORIZONTAL);

        int count = 5;
        subLayoutForTimeSheet.addView(tableLayout(count));
        root.addView(subLayoutForTimeSheet);

    }
    private TableLayout tableLayout(int count) {
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(true);
        int noOfRows = count / 5;
        for (int i = 0; i < noOfRows; i++) {
            int rowId = 5 * i;
            tableLayout.addView(createOneFullRow(rowId));
        }
       /* int individualCells = count % 5;
        tableLayout.addView(createLeftOverCells(individualCells, count));*/
        return tableLayout;
    }

    private TableRow createOneFullRow(int rowId) {
        TableRow tableRow = new TableRow(this);
        tableRow.setPadding(0, 10, 0, 0);
       // for project list

        Spinner spProject = new Spinner(this);
        spProject.setId(Integer.valueOf("5"));

        ArrayAdapter<String> spProjects = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,projectList);
        spProjects.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProject.setAdapter(spProjects);

        tableRow.addView(spProject);
       // for activity list

        Spinner spActivity = new Spinner(this);
        spActivity.setId(Integer.valueOf("6"));

        ArrayAdapter<String> spActivities = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,activityList);
        spActivities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProject.setAdapter(spActivities);
        tableRow.addView(spActivity);

        for (int i = 1; i <= 5; i++) {
            tableRow.addView(editText(String.valueOf(rowId + i)));
        }
        return tableRow;
    }
    private EditText editText(String hint) {
        EditText editText = new EditText(this);
        editText.setId(Integer.valueOf(hint));
        editText.setHint(hint);
        editTextList.add(editText);
        return editText;
    }

    private Spinner spinner(String hint)
    {
        Spinner spinner = new Spinner(this);
        spinner.setId(Integer.valueOf(hint));
        return spinner;
    }
}
