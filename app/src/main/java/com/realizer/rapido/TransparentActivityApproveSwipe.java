package com.realizer.rapido;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by Win on 10/10/2015.
 */
public class TransparentActivityApproveSwipe extends Fragment implements AsyncTaskCompleteListener<String> {

    String empId;
    StringBuilder result ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.transprnt, container, false);
        Calendar c = Calendar.getInstance();
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        empId = UserGlobalData.getInstance().getEmpId();
        ApproveSwipeRegularizationAsyncTakGet obj = new ApproveSwipeRegularizationAsyncTakGet(empId, getActivity(),this);
        obj.execute();
        return rootView;
    }



    @Override
    public void onTaskComplete(String result, int pos) {
        String empSwipeRecords = result.toString();
        // String empSwipeRecords = "AP-14,,Suvarna,, Ghoman,,12-5-2015,,Monday,,4.30,,Voting_AP-15,,Atharva,, Ghoman,,15-5-2015,,Wednesday,,5.30,,Voting no";

        ApproveSwipeFragment fragment = new ApproveSwipeFragment();
        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        bundle.putString("AnswerApproveLeave",empSwipeRecords);
        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();

    }

}
