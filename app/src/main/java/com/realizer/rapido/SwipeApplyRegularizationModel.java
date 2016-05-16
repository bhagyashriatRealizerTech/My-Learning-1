package com.realizer.rapido;

/**
 * Created by Suvarna.Ghoman on 01-0z9-2015.
 */
public class SwipeApplyRegularizationModel {
    private String empId="";
    private String dateForSwipe="";
    private String dayForSwipe="";
    private String timeInSwipe="";
    private String timeOutSwipe="";
    private String totalTimeSwipe="";
    private String Status="";


    public void setdateForSwipe(String dateForSwipe){this.dateForSwipe = dateForSwipe; }
    public String getdateForSwipe()
    {
        return  dateForSwipe;
    }

    public void setdayForSwipe(String dayForSwipe)
    {
        this.dayForSwipe = dayForSwipe;
    }
    public String getdayForSwipe()
    {
        return  dayForSwipe;
    }

    public void settimeOutSwipe(String timeOutSwipe)
    {
        this.timeOutSwipe = timeOutSwipe;
    }
    public String gettimeOutSwipe()
    {
        return  timeOutSwipe;
    }

    public void settimeInSwipe(String timeInSwipe)
    {
        this.timeInSwipe = timeInSwipe;
    }
    public String gettimeInSwipe()
    {
        return  timeInSwipe;
    }

    public void setTotaltimeSwipe(String totalTimeSwipe)
    {
        this.totalTimeSwipe = totalTimeSwipe;
    }
    public String getTotaltimeSwipe()
    {
        return  totalTimeSwipe;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
