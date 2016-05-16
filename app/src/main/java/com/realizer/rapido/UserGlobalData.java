package com.realizer.rapido;

import android.app.Application;

/**
 * Created by suvarna.ghoman on 22-07-2015.
 */
public class UserGlobalData  {

    private String  empId;
    private String empFirstName;
    private String empLastName;
    private String empTimeIn;
    private String empTimeOut;
    private String empType;
    private String companyCode;
    private String companyLocation;
    private String reset;

    public String getEmpId() {return empId;    }
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpType() {
        return empType;
    }
    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getEmpFirstName()
    {
        return empFirstName;
    }
    public void setEmpFirstName(String empFirstName) {
        this.empFirstName = empFirstName;
    }

    public String getEmpLastName()
    {
        return empLastName;
    }
    public void setEmpLastName(String empLastName) {
        this.empLastName = empLastName;
    }

    public String getEmpTimeIn()
    {
        return empTimeIn;
    }
    public void setEmpTimeIn(String empTimeIn) {
        this.empTimeIn = empTimeIn;
    }

    public String getEmpTimeOut()
    {
        return empTimeOut;
    }
    public void setEmpTimeOut(String empTimeOut) {
        this.empTimeOut = empTimeOut;
    }

    public String getCompanyCode()
    {
        return companyCode;
    }
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String geCompanyLocation()
    {
        return companyLocation;
    }
    public void setCompanyLocation(String companyLocation) {this.companyLocation = companyLocation;}

    public String getReset() {return reset;    }
    public void setReset(String reset) {
        this.reset = reset;
    }

    private static final UserGlobalData userGlobalData = new UserGlobalData();
    public static UserGlobalData getInstance() {return userGlobalData;}
}
