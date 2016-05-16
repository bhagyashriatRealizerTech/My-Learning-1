package com.realizer.rapido;

/**
 * Created by Suvarna.Ghoman on 31-07-2015.
 */
public class BalanceLeaveDetailsModel {
    private String empId="";
    private String leaveCatagory="";
    private String leaveBalance="";
    private String AnnualAllocation="";
    private String AccuredLeaves="";
    private String LeavesTaken="";

    public void setempId(String _empid)
    {
        this.empId = _empid;
    }
    public String getEmpId()
    {
        return  empId;
    }

    public void setleaveCatagory(String leaveCatagory)
    {
        this.leaveCatagory = leaveCatagory;
    }
    public String getleaveCatagory()
    {
        return  leaveCatagory;
    }

    public void setleaveBalance(String leaveBalance)
    {
        this.leaveBalance = leaveBalance;
    }
    public String getleaveBalance()
    {
        return  leaveBalance;
    }

    public void setAnnualAllocation(String AnnualAllocation)
    {
        this.AnnualAllocation = AnnualAllocation;
    }
    public String getAnnualAllocation()
    {
        return  AnnualAllocation;
    }

    public void setAccuredLeaves(String AccuredLeaves)
    {
        this.AccuredLeaves = AccuredLeaves;
    }
    public String getAccuredLeaves()
    {
        return  AccuredLeaves;
    }

    public void setLeavesTaken(String LeavesTaken)
    {
        this.LeavesTaken = LeavesTaken;
    }
    public String getLeavesTaken()
    {
        return  LeavesTaken;
    }
}
