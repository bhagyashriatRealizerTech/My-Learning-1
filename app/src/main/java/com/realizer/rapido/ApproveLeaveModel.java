package com.realizer.rapido;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Suvarna.Ghoman on 02-09-2015.
 */
public class ApproveLeaveModel implements Comparable<ApproveLeaveModel>
{
    private String empId="";
    private String Name="";
    private String empApproveLeaveType="";
    private String empApproveLeaveCount="";
    private String approveStartDate="";
    private String approveEndDate="";

    public void setEmpId(String _empid)
    {
        this.empId = _empid;
    }
    public String getEmpId()
    {
        return  empId;
    }

    public void setEmpApproveLeaveType(String empApproveLeaveType){this.empApproveLeaveType = empApproveLeaveType;  }
    public String getEmpApproveLeaveType()
    {
        return  empApproveLeaveType;
    }

    public void setEmpApproveLeaveCount(String empApproveLeaveCount)    {
        this.empApproveLeaveCount = empApproveLeaveCount;
    }
    public String getEmpApproveLeaveCount()
    {
        return  empApproveLeaveCount;
    }

    public void setApproveStartDate(String approveStartDate) {
        this.approveStartDate = approveStartDate;
    }
    public String getApproveStartDate()
    {
        return  approveStartDate;
    }

    public void setApproveEndDate(String approveEndDate) {
        this.approveEndDate = approveEndDate;
    }
    public String getApproveEndDate()
    {
        return  approveEndDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public int compareTo(ApproveLeaveModel another) {
        DateFormat format =  new SimpleDateFormat("M-dd-yyyy");
        try {
            return format.parse(getApproveStartDate()).compareTo(format.parse(another.getApproveStartDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
