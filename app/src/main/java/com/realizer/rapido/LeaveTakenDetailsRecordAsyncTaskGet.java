package com.realizer.rapido;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

/**
 * Created by Suvarna.Ghoman on 31-08-2015.
 */
public class LeaveTakenDetailsRecordAsyncTaskGet extends AsyncTask<Void, Void,StringBuilder>
{
    //Declare controls
    ProgressDialog dialog;

    // Declare Variables
    StringBuilder resultLeavesTaken;
    String empId;
    String startDate;
    String endDate;
    Context myContext;
    private AsyncTaskCompleteListener<String> callback;

    public LeaveTakenDetailsRecordAsyncTaskGet(String _empId, String _startDate, String _endDate, Context myContext,AsyncTaskCompleteListener<String> cb) {
        this.empId = _empId;
        this.startDate=_startDate;
        this.endDate=_endDate;
        this.myContext = myContext;
        this.callback = cb;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog=ProgressDialog.show(myContext,"","Loading data ...");
    }

    @Override
    protected StringBuilder doInBackground(Void... params) {
        resultLeavesTaken = new StringBuilder();

        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        // Url to get leave details
        String my="http://104.217.254.180/RestWCF/svcEmp.svc/getEmpLeaveDtls/"+empId + "/" +startDate +"/" + endDate;

        HttpGet httpGet = new HttpGet(my);
        HttpClient client = new DefaultHttpClient();
        try
        {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();

            int statusCode = statusLine.getStatusCode();
            if(statusCode == 200)
            {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while((line=reader.readLine()) != null)
                {
                    resultLeavesTaken.append(line);
                }
            }
            else
            {
               // Log.e("Error", "Failed to Login");
            }
        }
        catch(ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            client.getConnectionManager().closeExpiredConnections();
            client.getConnectionManager().shutdown();
        }
        return resultLeavesTaken;
    }

    @Override
    protected void onPostExecute(StringBuilder stringBuilder) {
        super.onPostExecute(stringBuilder);
        dialog.dismiss();
        callback.onTaskComplete(stringBuilder.toString(),1);
    }

}