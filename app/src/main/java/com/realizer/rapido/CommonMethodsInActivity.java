package com.realizer.rapido;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by Suvarna.Ghoman on 04-09-2015.
 */
public class CommonMethodsInActivity extends Activity {

    String reasonForRejection;
    public void ApproveLeave(String empId,String SD,String ED)
    {
       /* ApproveLeaveAsyncTaskGet obj = new ApproveLeaveAsyncTaskGet(empId,SD, ED, getApplicationContext());
        try
        {
               StringBuilder result = obj.execute().get();
               String applyLeave = result.toString();
            // output as true or false
            if(applyLeave.equalsIgnoreCase("true")) {*/
        Toast.makeText(getApplicationContext(), "Leave approved", Toast.LENGTH_LONG).show();
           /* } else
                Toast.makeText(getApplicationContext(), "Reason not submitted. try again", Toast.LENGTH_LONG).show();
         }
          catch (InterruptedException e) {
                e.printStackTrace();
         } catch (ExecutionException e) {
                e.printStackTrace();
         }*/
    }

   /* public void RejectLeave( final String empId, final String SD, final String ED) {
        //Show popup
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ApproveLeaveActivity.this);
        // Set up the input
        final EditText reason = new EditText(this);
        reason.setHint("Enter reason here");

        // Specify the type of input expected;
        reason.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialogBuilder.setView(reason);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        reasonForRejection = reason.getText().toString();
                        if (reasonForRejection.equals("")) {
                            Toast.makeText(getApplicationContext(),"Enter reason for rejection", Toast.LENGTH_LONG).show();
                        } else {
                            // call to save reason
                            ApproveLeaveRejectedAsyncTaskPost obj = new ApproveLeaveRejectedAsyncTaskPost(empId, SD, ED, reasonForRejection, getApplicationContext());
                     try {
                            StringBuilder result = obj.execute().get();
                            String reasonSubmitted = result.toString();

                            // output as true or false
                            if(reasonSubmitted.equalsIgnoreCase("true")){
                            Toast.makeText(getApplicationContext(), "Leave rejected", Toast.LENGTH_LONG).show();

                          } else
                                Toast.makeText(getApplicationContext(), "Reason not submitted. try again", Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
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
    }*/
}
