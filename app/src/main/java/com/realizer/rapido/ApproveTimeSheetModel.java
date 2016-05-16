package com.realizer.rapido;

/**
 * Created by Suvarna.Ghoman on 03-09-2015.
 */
public class ApproveTimeSheetModel {
    private String empId="";
    private String empApproveTimeSheetFirstName="";
    private String empApproveTimeSheetLastName="";
    private String approveTimeSheetWeek="";
    private String approveTimeSheetTotalHrs="";


    public void setEmpId(String _empid)
    {
        this.empId = _empid;
    }
    public String getEmpId()
    {
        return  empId;
    }

    public void setEmpApproveTimeSheetFirstName(String empApproveTimeSheetFirstName){this.empApproveTimeSheetFirstName = empApproveTimeSheetFirstName;  }
    public String getEmpApproveTimeSheetFirstName()
    {
        return  empApproveTimeSheetFirstName;
    }

    public void setEmpApproveTimeSheetLastName(String empApproveTimeSheetLastName)    {
        this.empApproveTimeSheetLastName = empApproveTimeSheetLastName;
    }
    public String getEmpApproveTimeSheetLastName()
    {
        return  empApproveTimeSheetLastName;
    }

    public void setApproveTimeSheetWeek(String approveTimeSheetWeek) {
        this.approveTimeSheetWeek = approveTimeSheetWeek;
    }
    public String getApproveTimeSheetWeek()
    {
        return  approveTimeSheetWeek;
    }

    public void setApproveTimeSheetTotalHrs(String approveTimeSheetTotalHrs) {
        this.approveTimeSheetTotalHrs = approveTimeSheetTotalHrs;
    }
    public String getApproveTimeSheetTotalHrs()
    {
        return  approveTimeSheetTotalHrs;
    }


}
