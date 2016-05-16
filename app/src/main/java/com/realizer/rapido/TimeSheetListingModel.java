package com.realizer.rapido;

/**
 * Created by Suvarna.Ghoman on 26-08-2015.
 */
public class TimeSheetListingModel {

    private String timesheetStatus="";
    private String timesheetStartDate="";
    private String timesheetEndtDate="";
    private String totalHours;
    private String projectcode;


    public void settimesheetStatus(String _timesheetStatus){this.timesheetStatus = _timesheetStatus; }
    public String gettimesheetStatus()    {        return  timesheetStatus;    }

    public void settimesheetStartDate(String _timesheetStartDate){this.timesheetStartDate = _timesheetStartDate; }
    public String gettimesheetStartDate()    {        return  timesheetStartDate;    }

    public void settotalHours(String _totalHours){this.totalHours = _totalHours; }
    public String gettotalHours()    {        return  totalHours;    }

    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }

    public void settimesheetEndDate(String timesheetEndtDate){this.timesheetEndtDate = timesheetEndtDate; }
    public String gettimesheetEndtDate()    {        return  timesheetEndtDate;    }
}
