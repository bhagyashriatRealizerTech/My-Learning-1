package com.realizer.rapido;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

/**
 * Created by Win on 10/20/2015.
 */
public class SwipeApplyFragment extends Fragment implements View.OnClickListener, AsyncTaskCompleteListener<String> {
    TextView empUserName;
    Button btnApply;
    String reasonForMismatch;
    SwipeApplyRegularizationAdapter swipeadapeter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.swipe_regularization_apply_layout, container, false);
      /*  empUserName = (TextView)rootView.findViewById(R.id.txtUserName);
        empUserName.setText("Hi " + UserGlobalData.getInstance().getEmpFirstName() + ",");*/

        ArrayList<SwipeApplyRegularizationModel> swipeDetails = getSwipeDetails();

        final ListView lstSwipeList = (ListView) rootView.findViewById(R.id.lstSwipeDetails);
        if(swipeDetails.equals(null))
        {
            Toast.makeText(getActivity(), "No data Available for Swipe", Toast.LENGTH_SHORT).show();
        }
        else
            lstSwipeList.setAdapter(new SwipeApplyRegularizationAdapter(getActivity(), swipeDetails,SwipeApplyFragment.this));
        return rootView;
    }
    public  ArrayList<SwipeApplyRegularizationModel> getSwipeDetails()
    {
        Bundle b = this.getArguments();
        String allSwipeDetails = b.getString("AnswerSwipeRegularization").toString();
        String[] swipeDetails = allSwipeDetails.split("_");
        ArrayList<SwipeApplyRegularizationModel> swipeMismatchDetails= new ArrayList<>();

        for(String swipeMismatch : swipeDetails)
        {

            String [] swipe = swipeMismatch.toString().split(",,");
            if(swipe.length>1) {
                SwipeApplyRegularizationModel swipeModel = new SwipeApplyRegularizationModel();
                //for(int i=1;i<swipe.length; ) {
                swipeModel.setdateForSwipe(swipe[0].replace("\"", ""));
                swipeModel.setdayForSwipe(swipe[1]);
                swipeModel.settimeInSwipe(swipe[2].replace("\"", ""));
                swipeModel.settimeOutSwipe(swipe[3].replace("\"", ""));
                swipeModel.setTotaltimeSwipe(swipe[4].replace("\"", ""));
                swipeModel.setStatus(swipe[5].replace("\"", ""));
                swipeMismatchDetails.add(swipeModel);
            }
            else
                break;

        }

        return swipeMismatchDetails;
    }

    // display give reason alert
    public void GiveReason(final View v,final String startDate,final String endDate)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Set up the input
        final EditText reason = new EditText(getActivity());
        reason.setHint("Enter reason here");

        // Specify the type of input expected;
        reason.setInputType(InputType.TYPE_CLASS_TEXT );
        alertDialogBuilder.setView(reason);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        reasonForMismatch = reason.getText().toString();
                        final String empId=UserGlobalData.getInstance().getEmpId();

                        if (reasonForMismatch.equals("")) {
                            Toast.makeText(getActivity(), "Enter reason here", Toast.LENGTH_LONG).show();
                        } else {
                            SwipeApplyAsyncTaskPost obj = new SwipeApplyAsyncTaskPost(empId,startDate,endDate, reasonForMismatch, getActivity(),SwipeApplyFragment.this);
                            obj.execute();

                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTaskComplete(String result, int pos) {
        String empLeaveDetails = result;
        if (empLeaveDetails.equals("true")) {
            Toast.makeText(getActivity(), "Reason submitted", Toast.LENGTH_LONG).show();
            SwipeTransApplyActivity fragment = new SwipeTransApplyActivity();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
        } else
            Toast.makeText(getActivity(), "Reason not submitted. try again", Toast.LENGTH_LONG).show();

    }
}
