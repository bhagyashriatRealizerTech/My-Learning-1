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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Suvarna.Ghoman on 10-08-2015.
 */


public class SwipeApplyAsyncTaskPost extends AsyncTask<Void, Void,StringBuilder>
{

    ProgressDialog diaglog;
    StringBuilder builder;
    String empId, startDate,endDate, reasonForMismatch;
    Context myContext;
    private AsyncTaskCompleteListener<String> callback;

    public SwipeApplyAsyncTaskPost(String _empId, String _startDate,String _endDate, String _reasonForMismatch, Context _context,AsyncTaskCompleteListener<String> cb)
    {
        this.empId = _empId;
        this.startDate=_startDate;
        this.endDate=_endDate;
        this.reasonForMismatch=_reasonForMismatch;
        this.myContext = _context;
        this.callback = cb;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        diaglog=ProgressDialog.show(myContext,"","Apllying Swipe Mismatch ...");
    }
    @Override
    protected StringBuilder doInBackground(Void... params1) {
        builder = new StringBuilder();

        String HostUrl="http://104.217.254.180/RestWCF/svcEmp.svc/AddToSMRecordQueue/"+empId+"/"+startDate+"/"+endDate+"/"+reasonForMismatch;
        // AddToSMRecordQueue(string UserId, string STDateStLst, string STDateETLst, string _ReasonLst); Date should be separated by , (comma) and reason by $
        HttpClient client = new DefaultHttpClient();
        try
        {
            HttpGet httpGet = new HttpGet(HostUrl);
            BasicResponseHandler responseHandler = new BasicResponseHandler();
            String responseBody = client.execute(httpGet, responseHandler);
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

