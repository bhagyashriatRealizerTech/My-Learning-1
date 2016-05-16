package com.realizer.rapido;

/**
 * Created by Suvarna.Ghoman on 17-08-2015.
 */
public class HolidayListModel {
    private String holidayName="";
    private String holidayDate="";
    private String holidayDescription="";

    public void setHolidayName(String holidayName){this.holidayName = holidayName; }
    public String getHolidayName()
    {
        return  holidayName;
    }

    public void setHolidayDate(String holidayDate)
    {
        this.holidayDate = holidayDate;
    }
    public String getHolidayDate()
    {
        return  holidayDate;
    }

    public void setHolidayDescription(String holidayDescription){this.holidayDescription = holidayDescription; }
    public String getHolidayDescription()
    {
        return  holidayDescription;
    }

}
