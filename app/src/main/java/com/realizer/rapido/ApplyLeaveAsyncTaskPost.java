package com.realizer.rapido;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jenny on 7/13/2015.
 */
public class ApplyLeaveAsyncTaskPost extends AsyncTask<Void, Void,StringBuilder>
{

    ProgressDialog diaglog;
    StringBuilder builder;
    String empId, leaveType, isPlanned, startDateOfLeave, firstHalfSD, secondHalfSD, endDateOfLeave, firstHalfED, secondHalfED, resumeOnDate, totalLeaves, reasonForLeave, emergencyContact, Address;
    Context myContext;
    private AsyncTaskCompleteListener<String> callback;

    public ApplyLeaveAsyncTaskPost(Context _context, String _empId, String _leaveType, String _isPlanned, String _startDateOfLeave,String _firstHalfSD,
                                   String _secondHalfSD,String _endDateOfLeave,String _firstHalfED,String _secondHalfED,String _resumeOnDate,
                                   String _totalLeaves, String _reasonForLeave, String _emergencyContact, String _Address,AsyncTaskCompleteListener<String> cb)

    {
        this.myContext = _context;
        this.empId = _empId;
        this.leaveType=_leaveType;
        this.isPlanned=_isPlanned;
        this.startDateOfLeave=_startDateOfLeave;
        this.firstHalfSD=_firstHalfSD;
        this.secondHalfSD=_secondHalfSD;
        this.endDateOfLeave=_endDateOfLeave;
        this.firstHalfED=_firstHalfED;
        this.secondHalfED=_secondHalfED;
        this.resumeOnDate=_resumeOnDate;
        this.totalLeaves=  _totalLeaves;
        this.reasonForLeave=_reasonForLeave;
        this.emergencyContact=_emergencyContact;
        this.Address=_Address;
        this.callback = cb;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        diaglog = ProgressDialog.show(myContext,"", "Applying Leave...");
    }

    @Override
    protected StringBuilder doInBackground(Void... params1) {
       builder = new StringBuilder();
//http://104.217.254.180/RestWCF/svcEmp.svc/ApplyLeave/AP-14/PL/true/9-15-2015/false/false/9-16-2015/false/false/09-17-2015/1/picnic/9503994311/goa
        String HostUrl="http://104.217.254.180/RestWCF/svcEmp.svc/ApplyLeave/"+empId +"/"+ leaveType+"/" + isPlanned+"/"
                + startDateOfLeave+"/" + firstHalfSD+"/" + secondHalfSD+"/" + endDateOfLeave+"/" + firstHalfED+"/" + secondHalfED+"/"
                + resumeOnDate+"/" + totalLeaves+"/" + reasonForLeave+"/" + emergencyContact+"/" + Address;
        Log.d("Host url is=",HostUrl);
        HttpGet httpGet = new HttpGet(HostUrl);
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
                    builder.append(line);
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
            diaglog.dismiss();
        }
        return builder;
    }



    @Override
    protected void onPostExecute(StringBuilder stringBuilder) {
        super.onPostExecute(stringBuilder);
        diaglog.dismiss();
        callback.onTaskComplete(stringBuilder.toString(),0);
    }
}