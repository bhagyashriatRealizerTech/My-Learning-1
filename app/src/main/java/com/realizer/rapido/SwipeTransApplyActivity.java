package com.realizer.rapido;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by Win on 10/10/2015.
 */
public class SwipeTransApplyActivity extends Fragment implements AsyncTaskCompleteListener<String> {
    StringBuilder result;
    String empId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.transleaveapprove, container, false);
        Calendar c = Calendar.getInstance();
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        empId = UserGlobalData.getInstance().getEmpId();
        SwipeApplyAsyncTaskGet obj = new SwipeApplyAsyncTaskGet(empId, month, getActivity(),SwipeTransApplyActivity.this);
        obj.execute();
        return rootView;
    }



    @Override
    public void onTaskComplete(String result, int pos) {
        String empSwipeDetails = result;
        SwipeApplyFragment fragment = new SwipeApplyFragment();
        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        bundle.putString("AnswerSwipeRegularization",empSwipeDetails);
        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();

    }
}
