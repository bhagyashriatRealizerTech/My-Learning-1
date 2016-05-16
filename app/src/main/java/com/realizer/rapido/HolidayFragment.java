package com.realizer.rapido;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Win on 10/20/2015.
 */
public class HolidayFragment extends Fragment implements View.OnClickListener {
    private int pYear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.holiday_layout, container, false);

        TextView displayyear=(TextView)rootView.findViewById(R.id.displayyear);
        final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        displayyear.setText("Holiday list for " + pYear);

        ArrayList<HolidayListModel> holidays = GetHolidayList();
        final ListView listHoliday = (ListView) rootView.findViewById(R.id.lstHolidayList);
        listHoliday.setAdapter(new HolidayListAdapter(getActivity(), holidays));

        return rootView;
    }
    private ArrayList<HolidayListModel> GetHolidayList()
    {
       Bundle b = this.getArguments();
        //"Independence day,,8-15-2015,,indepence day_independence,,8-15-2015,,desc"
        String [] holidayList = (b.getString("AnswerHolidayList")).toString().split("_");
        ArrayList<HolidayListModel> results = new ArrayList<>();

        for(String holiday : holidayList)
        {
            String [] hDay = holiday.toString().split(",,");

            HolidayListModel hDetail = new HolidayListModel();
            for(int i=1;i<hDay.length-1;i++) {

                hDetail.setHolidayDescription(hDay[0].replaceAll("\"", ""));
                hDetail.setHolidayDate(hDay[1]);
                results.add(hDetail);
            }

        }
        return results;
    }

    @Override
    public void onClick(View v) {

    }
}
