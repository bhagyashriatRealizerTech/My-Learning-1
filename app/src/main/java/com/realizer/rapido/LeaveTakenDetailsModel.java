package com.realizer.rapido;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Suvarna.Ghoman on 07-08-2015.
 */

public class LeaveTakenDetailsModel implements Comparable<LeaveTakenDetailsModel>{
   // private String empId="";
    private String startDate="";
    private String endDate="";
    private String leaveType="";
    private String status="";
    private String resumedate="";


    public void setstartDate(String startDate){this.startDate = startDate; }
    public String getstartDate()
    {
        return  startDate;
    }

    public void setendDatee(String endDate)
    {
        this.endDate = endDate;
    }
    public String getendDate()
    {
        return  endDate;
    }

    public void setleaveType(String leaveType)
    {
        this.leaveType = leaveType;
    }
    public String getleaveType()
    {
        return  leaveType;
    }

    public void setstatus(String status)
    {
        this.status = status;
    }
    public String getstatus()
    {
        return  status;
    }

    public String getResumedate() {
        return resumedate;
    }

    public void setResumedate(String resumedate) {
        this.resumedate = resumedate;
    }

    @Override
    public int compareTo(LeaveTakenDetailsModel another) {
        DateFormat format =  new SimpleDateFormat("M-dd-yyyy");
        try {
            return format.parse(getstartDate()).compareTo(format.parse(another.getstartDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
