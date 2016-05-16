package com.realizer.rapido;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.ExecutionException;

/**
 * Created by Win on 10/10/2015.
 */
public class TransparentLeaveApprove extends Activity implements AsyncTaskCompleteListener<String> {
    String empId;
    StringBuilder result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transleaveapprove);

        empId = UserGlobalData.getInstance().getEmpId();
        ApproveLeaveAsyncTaskGet obj = new ApproveLeaveAsyncTaskGet(empId,TransparentLeaveApprove.this,this);
        try {
            result = obj.execute().get();
            String empLeavesRecords = result.toString();


            ApproveLeaveFragment fragment = new ApproveLeaveFragment();
            Bundle bundle = new Bundle();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            bundle.putString("AnswerApproveLeave",empLeavesRecords);
            fragment.setArguments(bundle);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onTaskComplete(String result, int pos) {

    }
}
