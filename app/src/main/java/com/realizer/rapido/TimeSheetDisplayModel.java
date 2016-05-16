package com.realizer.rapido;


import java.util.ArrayList;

/**
 * Created by Suvarna.Ghoman on 11-08-2015.
 */
public class TimeSheetDisplayModel {
    private String timesheetProjectName="";
    private String timesheetStatus="";
    private String timesheetStartDate="";
    private String timesheetEndDate="";
    private String totalHours;


    public void settimesheetProjectName(String _timesheetProjectName){this.timesheetProjectName = _timesheetProjectName; }
    public String gettimesheetProjectName()    {        return  timesheetProjectName;    }

    public void settimesheetStatus(String _timesheetStatus){this.timesheetStatus = _timesheetStatus; }
    public String gettimesheetStatus()    {        return  timesheetStatus;    }

    public void settimesheetStartDate(String _timesheetStartDate){this.timesheetStartDate = _timesheetStartDate; }
    public String gettimesheetStartDate()    {        return  timesheetStartDate;    }

    public void settimesheetEndDate(String _timesheetEndDate){this.timesheetEndDate = _timesheetEndDate; }
    public String gettimesheetEndDate()    {        return  timesheetEndDate;    }

    public void settotalHours(String _totalHours){this.totalHours = _totalHours; }
    public String gettotalHours()    {        return  totalHours;    }


}
