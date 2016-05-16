package com.realizer.rapido;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Suvarna.Ghoman on 10-09-2015.
 */
public class TimeSheetWeeklyDetailsEditAsyncTaskPost extends AsyncTask<Void, Void,StringBuilder>
{

    ProgressDialog diaglog;
    StringBuilder builder;
    String empId, dateOfMistmatch, totalHr,projectName,activityName;
    Context myContext;

    public TimeSheetWeeklyDetailsEditAsyncTaskPost(String _empId, String _dateToEdit, String _totalHr,String _projectName, String _activityName, Context _context)
    {
        this.empId = _empId;
        this.dateOfMistmatch=_dateToEdit;
        this.totalHr=_totalHr;
        this.projectName=_projectName;
        this.activityName=_activityName;
        this.myContext = _context;
    }

    @Override
    protected StringBuilder doInBackground(Void... params1) {
        builder = new StringBuilder();
// change paramerters and ursl
        String HostUrl="http://192.168.1.101/webapi/api/values/Post";

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(HostUrl);
        try
        {
            List<NameValuePair> params = new LinkedList<NameValuePair>();

            params.add(new BasicNameValuePair("EmpId",String.valueOf(empId)));
            params.add(new BasicNameValuePair("leaveType",String.valueOf(dateOfMistmatch)));
            params.add(new BasicNameValuePair("startDateOfLeave",String.valueOf(totalHr)));
            params.add(new BasicNameValuePair("startDateOfLeave",String.valueOf(totalHr)));
            params.add(new BasicNameValuePair("startDateOfLeave",String.valueOf(totalHr)));

            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setHeader("Accept", "application/json");

            HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entity);

            ResponseHandler<String> handler = new BasicResponseHandler();
            String resultResponse;
            resultResponse=httpClient.execute(httpPost,handler);

            builder.append(resultResponse);


        }
        catch (ClientProtocolException e)
        {
            String src = "ClientProtocolException ";
            e.printStackTrace();
           // Log.e(src, "ClientProtocolException in callWebService(). " + e.getMessage());
            builder.append("ClientProtocolException in callWebService(). " + e.getMessage());
        }
        catch (IOException e)
        {
            String src = "IOException";
            e.printStackTrace();
            //Log.e(src, "IOException in callWebService(). " + e.getMessage());
            builder.append("IOException in callWebService(). " + e.getMessage());
        }
        finally
        {
            httpClient.getConnectionManager().closeExpiredConnections();
            httpClient.getConnectionManager().shutdown();
        }

        return builder;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        diaglog = ProgressDialog.show(myContext,"Addition", "Adding Login Info !");
    }

    @Override
    protected void onPostExecute(StringBuilder stringBuilder) {
        super.onPostExecute(stringBuilder);
        diaglog.dismiss();
    }
}