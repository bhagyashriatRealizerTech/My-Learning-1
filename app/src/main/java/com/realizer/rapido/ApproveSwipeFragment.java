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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by Win on 10/21/2015.
 */
public class ApproveSwipeFragment extends Fragment implements View.OnClickListener , AsyncTaskCompleteListener<String> {
    TextView empUserName;
    ListView listSwipeApproveDetails;
    String reasonForSwipeRejection="";
    String commentonapprove="";
    View rootView;
    Bundle b;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.approve_swipe_layout, container, false);

        listSwipeApproveDetails =(ListView)rootView.findViewById(R.id.lstApproveSwipeDetails);

        ArrayList<ApproveSwipeModel> swipeApprove = GetApproveSwipeDetails();
        if(swipeApprove.equals(null))
        {
            Toast.makeText(getActivity(), "No data for Approve Swipe", Toast.LENGTH_SHORT).show();
        }
        else
            listSwipeApproveDetails.setAdapter(new ApproveSwipeAdapter(getActivity(),swipeApprove,ApproveSwipeFragment.this ));

        return rootView;
    }





    public ArrayList<ApproveSwipeModel> GetApproveSwipeDetails()
    {
       b= this.getArguments();
        ArrayList<ApproveSwipeModel> aSwipeDetails = new ArrayList<ApproveSwipeModel>();

        String [] approveSwipeRecords = b.getString("AnswerApproveLeave").toString().split("_");

        for(String approveSwipe : approveSwipeRecords)
        {
            ApproveSwipeModel aSwipe = new ApproveSwipeModel();
            String[] swipe = approveSwipe.split(",,");
            if(swipe.length>1) {
                aSwipe.setEmpId(swipe[0].replace("\"",""));
                aSwipe.setEmpApproveSwipeFirstName(swipe[1]);
                aSwipe.setApproveSwipeDate(sdateChange(swipe[2]));//format date
                aSwipe.setApproveSwipeTime(swipe[5]);
                aSwipe.setApproveSwipeDay("Monday  ");
                aSwipe.setApproveSwipeReason(swipe[6].replace("\"",""));
                aSwipeDetails.add(aSwipe);

            }
            else
            {
                break;
            }


        }
        Collections.sort(aSwipeDetails, Collections.reverseOrder());
        return aSwipeDetails;
    }

    public String sdateChange(String d)
    {
        SimpleDateFormat format = new SimpleDateFormat("M-dd-yyyy hh:mm:ss a");
        String dateString="";

        try {
            Date date = format.parse(d);
            format = new SimpleDateFormat("M-dd-yyyy");
            dateString= format.format(date);
            //Log.d("DATE", dateString.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;

    }

    public void ApproveSwipe(final String empIdForSwipe, final String dateForSwipe) {


        //Show popup
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Set up the input
        final EditText reason = new EditText(getActivity());
        reason.setHint("Enter reason here");

        // Specify the type of input expected;
        reason.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialogBuilder.setView(reason);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        commentonapprove = reason.getText().toString();
                        if (commentonapprove.equals("")) {
                            commentonapprove = "No Comment";
                        }
                        String mgrid = UserGlobalData.getInstance().getEmpId();
                        ApproveSwipeAsyncTaskPost obj = new ApproveSwipeAsyncTaskPost(empIdForSwipe, dateForSwipe, mgrid, "2", commentonapprove, getActivity(),ApproveSwipeFragment.this);
                          obj.execute();

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

    public void RejectSwipe(final String empIdForSwipe, final String dateForSwipe)
    {

        //Show popup
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Set up the input
        final EditText reason = new EditText(getActivity());
        reason.setHint("Enter reason here");

        // Specify the type of input expected;
        reason.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialogBuilder.setView(reason);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        reasonForSwipeRejection = reason.getText().toString();
                        if (reasonForSwipeRejection.equals("")) {
                            Toast.makeText(getActivity(), "Enter reason for rejection", Toast.LENGTH_LONG).show();
                        } else {
                            // call to save reason
                            String mgrid = UserGlobalData.getInstance().getEmpId();

                            ApproveSwipeAsyncTaskPost obj = new ApproveSwipeAsyncTaskPost(empIdForSwipe, dateForSwipe, mgrid, "3", reasonForSwipeRejection, getActivity(),ApproveSwipeFragment.this);
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

       if(pos==2)
       {
           String resultApproveSwipe = result.toString().replace("\"", "");

           if (resultApproveSwipe.equalsIgnoreCase("true")) {
               Toast.makeText(getActivity(), "Approved Swipe", Toast.LENGTH_LONG).show();
               TransparentActivityApproveSwipe fragment = new TransparentActivityApproveSwipe();
               FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.replace(R.id.frame_container, fragment);
               fragmentTransaction.commit();
           } else
               Toast.makeText(getActivity(), "Unable to approve Swipe, try after some time", Toast.LENGTH_LONG).show();
       }
        else if(pos==3)
       {

           String resultApproveSwipe = result.toString().replace("\"", "");

           if (resultApproveSwipe.equalsIgnoreCase("true"))
           {
               Toast.makeText(getActivity(), "Rejected Swipe", Toast.LENGTH_LONG).show();
               TransparentActivityApproveSwipe fragment = new TransparentActivityApproveSwipe();
               FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.replace(R.id.frame_container, fragment);
               fragmentTransaction.commit();
           }
           else
               Toast.makeText(getActivity(), "Unable to reject Swipe, try after some time", Toast.LENGTH_LONG).show();
       }

    }
}
