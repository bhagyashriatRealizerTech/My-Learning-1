package com.realizer.rapido;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Suvarna.Ghoman on 02-09-2015.
 */
public class ApproveSwipeModel implements Comparable<ApproveSwipeModel>
{
    private String empId="";
    private String empApproveSwipeFirstName="";
    private String empApproveSwipeLastName="";
    private String approveSwipeDay="";
    private String approveSwipeDate="";
    private String approveSwipeTime="";
    private String approveSwipeReason="";

    public void setEmpId(String _empid)
    {
        this.empId = _empid;
    }
    public String getEmpId()
    {
        return  empId;
    }

    public void setEmpApproveSwipeFirstName(String empApproveSwipeFirstName){this.empApproveSwipeFirstName = empApproveSwipeFirstName;  }
    public String getEmpApproveSwipeFirstName()
    {
        return  empApproveSwipeFirstName;
    }

    public void setEmpApproveSwipeLastName(String empApproveSwipeLastName)    {
        this.empApproveSwipeLastName = empApproveSwipeLastName;
    }
    public String getEmpApproveSwipeLastName()
    {
        return  empApproveSwipeLastName;
    }

    public void setApproveSwipeDay(String approveSwipeDay) {
        this.approveSwipeDay = approveSwipeDay;
    }
    public String getApproveSwipeDay()
    {
        return  approveSwipeDay;
    }

    public void setApproveSwipeDate(String approveSwipeDate) {
        this.approveSwipeDate = approveSwipeDate;
    }
    public String getApproveSwipeDate()
    {
        return  approveSwipeDate;
    }

    public void setApproveSwipeTime(String approveSwipeTime) {
        this.approveSwipeTime = approveSwipeTime;
    }
    public String getApproveSwipeTime()
    {
        return  approveSwipeTime;
    }

    public void setApproveSwipeReason(String approveSwipeReason) {
        this.approveSwipeReason = approveSwipeReason;
    }
    public String getapproveSwipeReason()
    {
        return  approveSwipeReason;
    }


    @Override
    public int compareTo(ApproveSwipeModel another) {
        DateFormat format =  new SimpleDateFormat("M-dd-yyyy");
        try {
            return format.parse(getApproveSwipeDate()).compareTo(format.parse(another.getApproveSwipeDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}