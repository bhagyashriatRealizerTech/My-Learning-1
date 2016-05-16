package com.realizer.rapido;

/**
 * Created by admin on 22/08/2015.
 */
public class AttendanceModel implements Comparable<AttendanceModel>{

        private String empId="";
        private String attendanceDate="";
        private String attendanceInOut="";
        private String attedanceTotalHrs="";

        public void setempId(String _empid)
        {
            this.empId = _empid;
        }
        public String getEmpId()
        {
            return  empId;
        }

        public void setattendanceDate(String attendanceDate)
        {
            this.attendanceDate = attendanceDate;
        }
        public String getattendanceDate()
        {
            return  attendanceDate;
        }

        public void setattendanceInOut(String attendanceInOut)
        {
            this.attendanceInOut = attendanceInOut;
        }
        public String getattendanceInOut()
        {
            return  attendanceInOut;
        }

    public void setattedanceTotalHrs(String attedanceTotalHrs)
    {
        this.attedanceTotalHrs = attedanceTotalHrs;
    }
    public String getattedanceTotalHrs()
    {
        return  attedanceTotalHrs;
    }


    @Override
    public int compareTo(AttendanceModel another) {

        return Integer.valueOf(getattendanceDate()).compareTo(Integer.valueOf(another.getattendanceDate()));
    }

}


